package com.example.dailyselfie;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class DailySelfieFramed extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle myImageExtras = getIntent().getExtras();
		
		
		String myImage = myImageExtras.getString("uriImage");	
		setContentView(R.layout.daily_selfie_frame);

		Uri myUri = Uri.parse(myImage);
		((ImageView)findViewById(R.id.selfie_frame)).setImageURI(myUri);
	}
}
