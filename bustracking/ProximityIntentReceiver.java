package com.example.eotrainee.bustracking;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by EOTrainee on 3/17/2017.
 */

public class ProximityIntentReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1000;
SharedPreferences sp;

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent) {
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean entering = intent.getBooleanExtra(key, false);
        if (entering) {
            sp= context.getSharedPreferences( "range",Context.MODE_PRIVATE );
            String sr = "1";
            sp.edit().putString( "range1",sr ).commit();
            Log.d(getClass().getSimpleName(), "entering");

            Toast.makeText( context,"enter",Toast.LENGTH_SHORT ).show();
        }else {
            Log.d(getClass().getSimpleName(), "exiting");
            String sr = "0";
            sp.edit().putString( "range1",sr ).commit();
            Toast.makeText( context,"enter",Toast.LENGTH_SHORT ).show();
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, Distance.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notification1 = new Notification( R.drawable.bus,"alert",System.currentTimeMillis() );

   /*  Notification notification = createNotification();*/
      /* notification.setLatestEventInfo(context, "Proximity Alert!", "You are near your point of interest.", pendingIntent);
*/
 /*  notificationManager.notify(NOTIFICATION_ID, notification1);*/
        Notification.Builder builder = new Notification.Builder(context);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder.setSmallIcon(R.drawable.bus)
          .setSound(Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.horn))
      //Here is FILE_NAME is the name of file that you want to play


        .setContentTitle("Bus is near")
                .setDefaults( Notification.DEFAULT_SOUND )
                .setDefaults( Notification.DEFAULT_VIBRATE )

           .setContentIntent(pendingIntent);

        Notification notification = builder.getNotification();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private Notification createNotification() {
        Notification notification = new Notification();
        notification.icon = R.drawable.bus;
        notification.when = System.currentTimeMillis();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.ledARGB = Color.WHITE;
        notification.ledOnMS = 1500;
        notification.ledOffMS = 1500;
        return notification;
    }
}
