package com.rebecagarcia.momaui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends FragmentActivity {

	private DialogFragment mDialog;
	static private final String URL = "http://www.moma.org";
	private SeekBar animationColor;
	private LinearLayout content;
	private ColorDrawable white = new ColorDrawable(0xFFFFFFFF);
	private ColorDrawable gray = new ColorDrawable (0xFFd3d3d3);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeValues();

		animationColor
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					int progress = 0;

					public void onProgressChanged(SeekBar animationColor,
							int progresValue, boolean fromUser) {
						int progresAbs = Math.abs((progresValue-progress));
						int inc = (progresAbs > 1)?10*progresAbs:10;
						if (progresValue > progress){
							getColouredView (content, 1, inc);
						}else{
							getColouredView (content,-1, inc);
						}
						progress = progresValue;
						
					}

					public void onStartTrackingTouch(SeekBar animationColor) {
					}

					public void onStopTrackingTouch(SeekBar animationColor) {;
					}

				});
	}

	public void getColouredView (LinearLayout layout, int progres, int increment) {
		int count = layout.getChildCount();
		for (int i = 0; i < count; i++) {
			View auxView = layout.getChildAt(i);
			if (auxView instanceof LinearLayout) {
				LinearLayout rectangleAux = (LinearLayout) auxView;
				 if(rectangleAux.getBackground() == null){
					 getColouredView (rectangleAux, progres, increment);
				 }else{
					 int oldColor =getColorLayout(rectangleAux);
					 if (oldColor != white.getColor() && oldColor != gray.getColor()){
						 int newColor = createNewColor (oldColor, progres, increment);
						 rectangleAux.setBackgroundColor(newColor);
					 }
				 }
			}
		}
	}

	public int createNewColor(int color, int progress, int increment){
		float[] hsvColor = new float[3];
		Color.colorToHSV(color, hsvColor);
		if(progress>0){
			hsvColor[0]+=increment;
		}else{
			hsvColor[0]-=increment;
		}
		return Color.HSVToColor(hsvColor);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.more_info) {
			mDialog = AlertDialogFragment.newInstance();
			mDialog.show(getFragmentManager(), "Alert");
		}
		return super.onOptionsItemSelected(item);
	}

	private void initializeValues() {
		animationColor = (SeekBar) findViewById(R.id.seekColor);
		content = (LinearLayout) findViewById(R.id.content);
	}

	private int getColorLayout(LinearLayout layout) {
		int color = ((ColorDrawable) layout.getBackground()).getColor();
		return color;
	}

	public static class AlertDialogFragment extends DialogFragment {

		public static AlertDialogFragment newInstance() {
			return new AlertDialogFragment();
		}

		// Build AlertDialog using AlertDialog.Builder
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
					.setMessage(
							"Inspired by the works of Piet Mondrian and Ben Nicholson.\n Click below to learn more!")
					.setCancelable(false)
					.setNegativeButton("Not Now",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// Toast.makeText(getApplicationContext(),
									// "View More was cancelled!",
									// Toast.LENGTH_LONG).show();
								}
							})
					.setPositiveButton("Visit MOMA",
							new DialogInterface.OnClickListener() {
								public void onClick(
										final DialogInterface dialog, int id) {
									Intent urlIntent = new Intent(
											Intent.ACTION_VIEW, Uri.parse(URL));
									startActivity(urlIntent);
								}
							}).create();
		}
	}
}
