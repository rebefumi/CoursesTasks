package com.example.dailyselfie;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements LoaderCallbacks<Cursor> {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
	private Uri fileUri;
	private static final long INITIAL_ALARM_DELAY = 2 * 60 * 1000L;
	private static final long JITTER = 2 * 60 * 1000L;

	private static String mCurrentPhotoName;
	private static Uri mCurrentPhotoUri;

	private DailySelfieAdapter mCursorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// View
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query(ContentProviderDaily.CONTENT_URI,
				null, null, null, null);
		mCursorAdapter = new DailySelfieAdapter(this, cursor, 0);
		setListAdapter(mCursorAdapter);

		getLoaderManager().initLoader(0, null, this);

		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						DailySelfieFramed.class);
				String selfieName = ((TextView) view
						.findViewById(R.id.uri_selfie)).getText().toString();
				intent.putExtra("uriImage", selfieName);
				startActivity(intent);
			}
		});

		// Alarm set
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(MainActivity.this, AlarmDaily.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				MainActivity.this, 0, intent, 0);
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY, JITTER,
				pendingIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_camera) {
			startCamera();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void startCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = createMediaFileUri();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

		mediaScanIntent.setData(mCurrentPhotoUri);
		this.sendBroadcast(mediaScanIntent);
	}

	private static Uri createMediaFileUri() {

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyDailySelfies");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;

		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ timeStamp + ".jpg");
		mCurrentPhotoName = timeStamp;
		mCurrentPhotoUri = Uri.fromFile(mediaFile);
		return mCurrentPhotoUri;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				galleryAddPic();
				addImageContent();

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				Toast.makeText(this, R.string.capture_cancelled,
						Toast.LENGTH_LONG).show();
			} else {
				// Image capture failed, advise user
				Toast.makeText(this, R.string.capture_failed, Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	public void addImageContent() {
		DailySelfie selfie = new DailySelfie(mCurrentPhotoUri.toString(),
				mCurrentPhotoName, mCurrentPhotoName);
		mCursorAdapter.add(selfie);
	}

	// LoaderCallback methods
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {

		// TODO - Create a new CursorLoader and return it
		return new CursorLoader(getApplicationContext(),
				ContentProviderDaily.CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> newLoader, Cursor newCursor) {

		// TODO - Swap in the newCursor
		mCursorAdapter.swapCursor(newCursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> newLoader) {

		// TODO - swap in a null Cursor
		mCursorAdapter.swapCursor(null);

	}

}
