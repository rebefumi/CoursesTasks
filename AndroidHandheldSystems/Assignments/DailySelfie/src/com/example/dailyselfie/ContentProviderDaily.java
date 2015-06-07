package com.example.dailyselfie;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ContentProviderDaily  extends ContentProvider{

	public static final String PROVIDER_NAME = "com.example.dailyselfie";
	public static final Uri BASE_URI = Uri.parse("content://" + PROVIDER_NAME + "/selfies");
	public static final String SELFIES_TABLE_NAME = "selfies";
	public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, SELFIES_TABLE_NAME);
	
	static final String _ID = "_id";
	static final String NAME = "name";
	static final String DATE = "date";
	static final String URL = "url";	
	
	private DatabaseHelper mDbHelper;
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "selfiesDaily";


	private static final String CREATE_LOCATION_TABLE = " CREATE TABLE " 
			+ SELFIES_TABLE_NAME + " ("
			+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NAME + " TEXT NOT NULL, "
			+ DATE + " TEXT NOT NULL, " 
			+ URL + " TEXT NOT NULL );";
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_LOCATION_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "
					+ SELFIES_TABLE_NAME);
			onCreate(db);
		}
	}	

	@Override
	public boolean onCreate() {
		mDbHelper = new DatabaseHelper(getContext());
		return (mDbHelper != null);
	}
	
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		long rowID = mDbHelper.getWritableDatabase().insert(SELFIES_TABLE_NAME, "", values);
		if (rowID > 0) {
			Uri fullUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(fullUri, null);
			return fullUri;
		}
		throw new SQLException("Failed to add record into" + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int rowsDeleted = mDbHelper.getWritableDatabase().delete(SELFIES_TABLE_NAME, null, null);
		getContext().getContentResolver().notifyChange(CONTENT_URI, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(SELFIES_TABLE_NAME);
	
		Cursor cursor = qb.query(mDbHelper.getWritableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
	
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
	
		return cursor;
	}
	
}
