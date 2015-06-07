package com.example.dailyselfie;

import java.io.File;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DailySelfieAdapter extends CursorAdapter{

	private static final String APP_DIR = "";
	private static LayoutInflater sLayoutInflater = null;
	private Context mContext;
	private String mBitmapStoragePath;

	public DailySelfieAdapter(Context context, Cursor cursor, int photos) {
		super(context, cursor, photos);

		mContext = context;
		sLayoutInflater = LayoutInflater.from(mContext);

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				String root = mContext.getExternalFilesDir(null)
						.getCanonicalPath();
				if (null != root) {
					File bitmapStorageDir = new File(root, APP_DIR);
					bitmapStorageDir.mkdirs();
					mBitmapStoragePath = bitmapStorageDir.getCanonicalPath();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static class ViewHolder {
		ImageView selfie;
		TextView name;
		TextView date;
		TextView uri;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		String date = cursor.getString(cursor.getColumnIndex(ContentProviderDaily.DATE));
     	String[] myDate =  date.split("_");
     	String year = myDate[0].substring(0, 4);
     	String month = myDate[0].substring(4, 6);
     	String day = myDate[0].substring(6);
     	
		holder.selfie.setImageBitmap(getBitmapFromFile(cursor.getString(cursor
				.getColumnIndex(ContentProviderDaily.URL))));
		holder.name.setText(context.getString(R.string.name_selfie)
				+ " " + cursor.getString(cursor
						.getColumnIndex(ContentProviderDaily.NAME)) + ".jpg");
		holder.date.setText(context.getString(R.string.date_selfie)
				+ " " + day + "/" + month + "/" + year);
		holder.uri.setText(cursor.getString(cursor
						.getColumnIndex(ContentProviderDaily.URL)));
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View newView;
		ViewHolder holder = new ViewHolder();

		newView = sLayoutInflater.inflate(R.layout.daily_selfie_view, parent, false);
		holder.selfie = (ImageView) newView.findViewById(R.id.selfie);
		holder.name = (TextView) newView.findViewById(R.id.name_selfie);
		holder.date = (TextView) newView.findViewById(R.id.date_selfie);
		holder.uri = (TextView) newView.findViewById(R.id.uri_selfie);
		newView.setTag(holder);

		return newView;
	}
	
	private Bitmap getBitmapFromFile(String filePath) {
		return BitmapFactory.decodeFile(filePath.replace("file:" , ""));
	}
	
	public void add(DailySelfie listItem) {

		ContentValues values = new ContentValues();
		values.put(ContentProviderDaily.URL, listItem.getmSelfieUrl());
		values.put(ContentProviderDaily.NAME, listItem.getmSelfieName());
		values.put(ContentProviderDaily.DATE, listItem.getmSelfieDate());
		
		mContext.getContentResolver().insert(ContentProviderDaily.CONTENT_URI, values);
		mContext.getContentResolver().notifyChange(ContentProviderDaily.CONTENT_URI, null);

	}
	
}
