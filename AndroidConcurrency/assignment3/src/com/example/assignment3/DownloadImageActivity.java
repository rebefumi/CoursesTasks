package com.example.assignment3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


public class DownloadImageActivity extends Activity {
	
	private final static String TAG = "Download";
	 
	private String uri;
	private Bundle mSavedState;
	private ImageDownloader mImageDownloader;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		   uri = extras.getString("uri");
		   Log.d(TAG, "onCreate Called");
		   mImageDownloader =  new ImageDownloader();
		   mImageDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
		}
	}
	
	   @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        final ImageDownloader task = mImageDownloader;
	        if (task != null) {
	        	task.cancel(true);
	            mImageDownloader = null;
	        }
	        mSavedState = outState;
	    }
	   
	   @Override
	   protected void onRestoreInstanceState(Bundle savedInstanceState) {
	        super.onRestoreInstanceState(savedInstanceState);
	        mImageDownloader = (ImageDownloader) new ImageDownloader().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
	        
	        mSavedState = null;
	    }

	
	private class ImageDownloader extends AsyncTask<String, Void, Uri> {

		@Override
		protected Uri doInBackground(String... params) {
			 Log.d(TAG, "doInBackground Called");
			return Utils.downloadImage(getApplicationContext(), Uri.parse(params[0]));
		}

		 @Override
		protected void onPreExecute() {
			Log.d(TAG,"onPreExecute Called");			
		}

	
		protected void onPostExecute(Uri result) {
			Log.d(TAG, "onPostExecute Called");
			Intent data = new Intent();
			
			if (result != null){
				data.putExtra("uri", result.toString());
				setResult(RESULT_OK, data);
			}else{
				setResult(RESULT_CANCELED, null);
			}
			finish();

		}
	}

}