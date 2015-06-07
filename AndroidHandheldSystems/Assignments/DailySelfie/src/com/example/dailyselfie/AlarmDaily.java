package com.example.dailyselfie;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AlarmDaily extends BroadcastReceiver{
	private final CharSequence tickerText = "Are you busy now?";
	private final CharSequence contentTitle = "Daily Selfie";
	private final CharSequence contentText = "Time for another selfie";
	private final long[] mVibratePattern = { 0, 200, 200, 300 };
	private static final int MY_NOTIFICATION_ID = 1;
	
	@Override
    public void onReceive(Context context, Intent intent)
     {       
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		Notification.Builder notificationBuilder = new Notification.Builder(context)
				.setTicker(tickerText)
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setAutoCancel(true).setContentTitle(contentTitle)
				.setContentText(contentText).setContentIntent(contentIntent)
				.setVibrate(mVibratePattern);
		
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(MY_NOTIFICATION_ID,
				notificationBuilder.build());

     }
}
