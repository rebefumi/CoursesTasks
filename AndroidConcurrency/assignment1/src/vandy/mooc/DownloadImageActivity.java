package vandy.mooc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * An Activity that downloads an image, stores it in a local file on the local
 * device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
	/**
	 * Debugging tag used by the Android logger.
	 */
	private final String TAG = getClass().getSimpleName();

	/**
	 * Hook method called when a new instance of Activity is created. One time
	 * initialization code goes here, e.g., UI layout and some class scope
	 * variable initialization.
	 * 
	 * @param savedInstanceState
	 *            object that contains saved state information.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Always call super class for necessary
		// initialization/implementation.
		// @@ TODO -- you fill in here.
		super.onCreate(savedInstanceState);

		// Get the URL associated with the Intent data.
		// @@ TODO -- you fill in here.
		final Uri uri = Uri.parse(getIntent().getDataString());
		final Uri localUri;
		// Download the image in the background, create an Intent that
		// contains the path to the image file, and set this as the
		// result of the Activity.

		// @@ TODO -- you fill in here using the Android "HaMeR"
		// concurrency framework. Note that the finish() method
		// should be called in the UI thread, whereas the other
		// methods should be called in the background thread. See
		// http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
		// for more discussion about this topic.

		Thread mThread = new Thread(new Runnable() {
			public void run() {
				Uri mUri = DownloadUtils.downloadImage(getApplicationContext(), uri);
				threadMsg(mUri);
				((Activity) DownloadImageActivity.this).runOnUiThread(new Runnable() {
					public void run() {
						finish();
					}
				});
			}

			private void threadMsg(Uri msg) {
				if (!msg.equals(null) && !msg.equals("")) {
					Message msgObj = handler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("localUri", msg.toString());
					msgObj.setData(b);
					handler.sendMessage(msgObj);
				}
			}

			private final Handler handler = new Handler() {
				public void handleMessage(Message msg) {
					String aResponse = msg.getData().getString("localUri");
					if (null != aResponse) {
						Log.d(TAG, msg.toString());
						Intent data = new Intent();
						data.putExtra("uri", aResponse);
						setResult(RESULT_OK, data);
					} else {
						Log.d(TAG, msg.toString());
						Toast.makeText(getBaseContext(), "Error to download the image", Toast.LENGTH_SHORT).show();
					}
				}
			};

		});
		// Start Thread
		mThread.start();
	}
}
