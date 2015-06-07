package com.example.assignment3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class FilterImageActivity extends Activity {

		private final static String TAG = "Filter";
		 
		private String uri;
		private Bundle mSavedState;
		private ImageFilter mImageFilter;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			Bundle extras = getIntent().getExtras();
			if (extras != null) {
			   uri = extras.getString("uri");
			   Log.d(TAG, "onCreate Filter Called");
			   mImageFilter = new ImageFilter();
			   mImageFilter.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
			}
		}
		
		   @Override
		    protected void onSaveInstanceState(Bundle outState) {
		        super.onSaveInstanceState(outState);
		        final ImageFilter task = mImageFilter;
		        if (task != null) {
		        	task.cancel(true);
		        	mImageFilter = null;
		        }
		        mSavedState = outState;
		    }
		   
		   @Override
		   protected void onRestoreInstanceState(Bundle savedInstanceState) {
		        super.onRestoreInstanceState(savedInstanceState);
		        mImageFilter = (ImageFilter) new ImageFilter().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
		        
		        mSavedState = null;
		    }
		
		private class ImageFilter extends AsyncTask<String, Void, Uri> {

			@Override
			protected Uri doInBackground(String... params) {
				Log.d(TAG, "doInBackground Filter Called");
				return Utils.grayScaleFilter(getApplicationContext(), Uri.parse(params[0]));
			}

			 @Override
			protected void onPreExecute() {
				Log.d(TAG, "onPreExecute Filter Called");
			}

		
			protected void onPostExecute(Uri result) {
				Log.d(TAG, "onPostExecute Filter Called");
				Intent data = new Intent();
				data.putExtra("uri", result.toString());
				setResult(RESULT_OK, data);
				finish();

			}

		}

	}
