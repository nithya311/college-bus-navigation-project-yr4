package com.example.eotraining.bustracking;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AlertPriority extends AppCompatActivity {
    private static final long POINT_RADIUS = 100; 
    private static final long PROX_ALERT_EXPIRATION = -1;
    private static final String PROX_ALERT_INTENT = "com.example.eotraining.bustracking";
    private LocationManager locationManager;
    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private Button addAlertButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_alert_priority );

        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        latitudeEditText = (EditText) findViewById( R.id.point_latitude );
        longitudeEditText = (EditText) findViewById( R.id.point_longitude );
        addAlertButton = (Button) findViewById( R.id.add_alert_button );

        addAlertButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                addProximityAlert();
            }
        } );

    }

    private void addProximityAlert() {
        double latitude = Double.parseDouble( latitudeEditText.getText().toString() );
        double longitude = Double.parseDouble( longitudeEditText.getText().toString() );
        Intent intent = new Intent( PROX_ALERT_INTENT );
        PendingIntent proximityIntent = PendingIntent.getBroadcast( this, 0, intent, 0 );
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        locationManager.addProximityAlert(
                latitude, // the latitude of the central point of the alert region
                longitude, // the longitude of the central point of the alert region
                POINT_RADIUS, // the radius of the central point of the alert region, in meters
                PROX_ALERT_EXPIRATION, // time for this proximity alert, in milliseconds, or -1 to indicate no                           expiration
                proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
        );

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        registerReceiver(new ProximityIntentReceiver(), filter);
        Toast.makeText(getApplicationContext(),"Alert Added",Toast.LENGTH_SHORT).show();
    }
}
