package com.example.eotrainee.bustracking;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;



public class ProximityActivity extends Activity {

	String notificationTitle;
	String notificationContent;
	String tickerMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate( savedInstanceState );

		boolean proximity_entering = getIntent().getBooleanExtra( LocationManager.KEY_PROXIMITY_ENTERING, true );

		if (proximity_entering) {
			Toast.makeText( getBaseContext(), "Entering the region", Toast.LENGTH_LONG ).show();
			notificationTitle = "Proximity - Entry";
			notificationContent = "Entered the region";
			tickerMessage = "Entered the region";
		} else {
			Toast.makeText( getBaseContext(), "Exiting the region", Toast.LENGTH_LONG ).show();
			notificationTitle = "Proximity - Exit";
			notificationContent = "Exited the region";
			tickerMessage = "Exited the region";
		}

		Intent notificationIntent = new Intent( getApplicationContext(), NotificationView.class );
		notificationIntent.putExtra( "content", notificationContent );

		/** This is needed to make this intent different from its previous intents */
		notificationIntent.setData( Uri.parse( "tel:/" + (int) System.currentTimeMillis() ) );

		/** Creating different tasks for each notification. See the flag Intent.FLAG_ACTIVITY_NEW_TASK */
		PendingIntent pendingIntent = PendingIntent.getActivity( getApplicationContext(), 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK );

		/** Getting the System service NotificationManager */
		NotificationManager nManager = (NotificationManager) getApplicationContext().getSystemService( Context.NOTIFICATION_SERVICE );

		/** Configuring notification builder to create a notification */
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( getApplicationContext() )
				.setWhen( System.currentTimeMillis() )
				.setContentText( notificationContent )
				.setContentTitle( notificationTitle )
				.setSmallIcon( R.drawable.school )
				.setAutoCancel( true )
				.setTicker( tickerMessage )
				.setContentIntent( pendingIntent );

		/** Creating a notification from the notification builder */
		Notification notification = notificationBuilder.build();

		/** Sending the notification to system.
		 * The first argument ensures that each notification is having a unique id
		 * If two notifications share same notification id, then the last notification replaces the first notification
		 * */
		nManager.notify( (int) System.currentTimeMillis(), notification );

		/** Finishes the execution of this activity */
		finish();
	}
}