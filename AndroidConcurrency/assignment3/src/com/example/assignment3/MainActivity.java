package com.example.assignment3;

import java.io.File;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int DOWNLOAD_IMAGE_REQUEST = 1;
	private static final int FILTER_IMAGE_REQUEST = 2;
	
	private EditText mUrlEditText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mUrlEditText = (EditText) findViewById(R.id.url);
	}

    protected Uri getUrl() {
        Uri url = null;
        
        // Get the text the user typed in the edit text (if anything).
        url = Uri.parse(mUrlEditText.getText().toString());
        
        String uri = url.toString();
        if (uri != null && !uri.equals("")){
        	if (URLUtil.isValidUrl(uri)){
                return url;
            }else{
            	Toast.makeText(this,
                        "Invalid URL",
                        Toast.LENGTH_SHORT).show();
            	return null;
            }
        }else {
            Toast.makeText(this,
                           "Invalid URL",
                           Toast.LENGTH_SHORT).show();
            return null;
        } 
    }
    
    public void downloadImage(View view) {
        try {
            // Hide the keyboard.
            hideKeyboard(this, mUrlEditText.getWindowToken());
            Intent mIntent = new Intent(this, DownloadImageActivity.class);
            mIntent.putExtra("uri", getUrl().toString());
            startActivityForResult(mIntent, DOWNLOAD_IMAGE_REQUEST);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == DOWNLOAD_IMAGE_REQUEST) {
                Intent mIntent = new Intent(this, FilterImageActivity.class);
                mIntent.putExtra("uri", data.getStringExtra("uri"));
                startActivityForResult(mIntent, FILTER_IMAGE_REQUEST);
            	
            }else if (requestCode == FILTER_IMAGE_REQUEST){
            	Intent mIntent = new Intent();
            	mIntent.setAction(android.content.Intent.ACTION_VIEW);
            	mIntent.setDataAndType(Uri.fromFile(new File(data.getStringExtra("uri"))), "image/*");
            	startActivity(mIntent);
            }
        }else if (resultCode == RESULT_CANCELED) {
        	Toast.makeText(this, "Error downloading image", Toast.LENGTH_LONG).show();
        }
    }    

    
    /**
     * This method is used to hide a keyboard after a user has
     * finished typing the url.
     */
    public void hideKeyboard(Activity activity, IBinder windowToken) {
	InputMethodManager mgr =
	(InputMethodManager) activity.getSystemService
	(Context.INPUT_METHOD_SERVICE);
	mgr.hideSoftInputFromWindow(windowToken,
	                   0);
	}
}
