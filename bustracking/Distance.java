package com.example.eotrainee.bustracking;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.duration;
import static android.R.attr.name;
import static com.example.eotrainee.bustracking.R.id.add;
import static com.example.eotrainee.bustracking.R.id.map;

public class Distance extends FragmentActivity implements GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMarkerClickListener {

    public static final String DATA_URL = "http://192.168.1.20/busproject1/nearme.php?busnumber=";
    private GoogleMap mMap;
    public static final String JSON_ARRAY = "result";
    public static final String KEY_LATITUDE = "Buscurrentlat";
    public static final String KEY_LOGITUDE = "buscurrentlogi";
    public static final String KEY_BUS = "busnumber";
    ProgressDialog loading;
    double vc;
    String name;
    double address;
    double lati;
    double logi;
    String busno;
    Circle myCircle;
    Handler mHandler = new Handler();
    SharedPreferences sp;
    PendingIntent pendingIntent;
    SharedPreferences sharedPreferences;
    int locationCount = 0;
    private static final long POINT_RADIUS = 100; // in Meters
    private static final long PROX_ALERT_EXPIRATION = -1; // It will never expire
    private static final String PROX_ALERT_INTENT = "com.example.eotrainee.bustracking";
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_distance );
        busno = getIntent().getStringExtra( "bus" );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( map );

        mMap = mapFragment.getMap();

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
        mMap.setMyLocationEnabled( true );

        final SharedPreferences sharedPreferences;
        new Thread( new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    try {
                        Thread.sleep( 11000 );
                        mHandler.post( new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                // Write your code here to update the UI.
                                sp= getSharedPreferences( "range", Context.MODE_PRIVATE );
                             String sd =    sp.getString( "range1","" );
                                /*if(sd.equals( "1" )){
                                    Toast.makeText( getApplicationContext(),"Reached",Toast.LENGTH_SHORT ).show();
                                }
                                else{

                                }*/
                                getData();


                            }
                        } );
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        } ).start();
        locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );

// Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences( "location", 0 );

// Getting number of locations already stored
        locationCount = sharedPreferences.getInt( "locationCount", 0 );

// Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString( "zoom", "0" );


        mMap.setOnMyLocationChangeListener( this );
       /* vc = 12.900;
        address = 89.0000;*/

        /*mapFragment.getMapAsync( this );
*/
      /*  final int[] count = {100};*/ //Declare as inatance variable
  /*      LatLng Chennqi = new LatLng( 13.900, 79.000 );
        mMap.addMarker( new MarkerOptions().position( Chennqi ).title( name ).icon( BitmapDescriptorFactory.fromResource( R.drawable.bus ) ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( Chennqi ) );

        Circle circle = mMap.addCircle( new CircleOptions()
                .center( Chennqi )
                .radius( 1000 )
                .strokeColor( Color.TRANSPARENT )
                .fillColor( Color.BLUE ) );*/




    }



        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */


    private void getData() {
        String id = busno;
        if (id.equals( "" )) {
            Toast.makeText( this, "Please enter an id", Toast.LENGTH_LONG ).show();
            return;
        }
        loading = ProgressDialog.show( this, "Please wait...", "Fetching...", false, false );

        String url = DATA_URL + id.toString();

        StringRequest stringRequest = new StringRequest( url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
            /*    showJSON( response );*/
                name = "";


                try {
                    JSONObject jsonObject = new JSONObject( response );
                    JSONArray result = jsonObject.getJSONArray( JSON_ARRAY );
                    JSONObject collegeData = result.getJSONObject( 0 );

                    vc = collegeData.getDouble( KEY_LATITUDE );
                    address = collegeData.getDouble( KEY_LOGITUDE );
                    name = collegeData.getString( KEY_BUS );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                init( vc, address );
                addProximityAlert();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( Distance.this, error.getMessage().toString(), Toast.LENGTH_LONG ).show();
                    }
                } );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );

    }

  /*  private void showJSON(String response) {


*//*        textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);*//*

    }*/

    private void init(double vc, double address) {
        mMap.clear();
        LatLng newLatLng = new LatLng( vc, address );

        MarkerOptions markerOptions = new MarkerOptions()
                .position( newLatLng )
                .title( newLatLng.toString() )
                .icon( BitmapDescriptorFactory.fromResource( R.drawable.bus ) );
        CircleOptions circleOptions1 = new CircleOptions()
                .center( newLatLng )   //set center
                .radius( 200 );   //set radius in meters
        // Fill color of the circle
        circleOptions1.fillColor( 0x30ff0000 );

        // Border width of the circle
        circleOptions1.strokeWidth( 2 )
                .strokeColor( Color.BLACK )
                .strokeWidth( 2 );

        myCircle = mMap.addCircle( circleOptions1 );
        mMap.addMarker( markerOptions );

        // This intent will call the activity ProximityActivity
   /*     Intent proximityIntent = new Intent( Distance.this,ProximityActivity.class );

        // Creating a pending intent which will be invoked by LocationManager when the specified region is
        // entered or exited
        pendingIntent = PendingIntent.getActivity( getBaseContext(), 0, proximityIntent, Intent.FLAG_ACTIVITY_NEW_TASK );

        // Setting proximity alert
        // The pending intent will be invoked when the device enters or exits the region 20 meters
        // away from the marked point
        // The -1 indicates that, the monitor will not be expired
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
        locationManager.addProximityAlert( vc, address, 100, -1, pendingIntent );

        *//** Opening the editor object to write data to sharedPreferences *//*
        SharedPreferences.Editor editor = sharedPreferences.edit();

        *//** Storing the latitude of the current location to the shared preferences *//*
        editor.putString("lat", Double.toString(vc));

        *//** Storing the longitude of the current location to the shared preferences *//*
        editor.putString("lng", Double.toString(address));

        *//** Storing the zoom level to the shared preferences *//*
        editor.putString("zoom",  Double.toString(address));

        *//** Saving the values stored in the shared preferences *//*
        editor.commit();*/

        Toast.makeText(getBaseContext(), "Proximity Alert is added", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onMyLocationChange(Location location) {


        LatLng locLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        double accuracy = location.getAccuracy();

        if(myCircle == null){
            CircleOptions circleOptions = new CircleOptions()
                    .center(locLatLng)   //set center
                    .radius(accuracy)   //set radius in meters
                    .fillColor(Color.RED)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(5);

            myCircle = mMap.addCircle(circleOptions);
        }else{
            myCircle.setCenter(locLatLng);
            myCircle.setRadius(accuracy);
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLng(locLatLng));

    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        //Make the marker bounce
        final Handler handler = new Handler();

        final long startTime = SystemClock.uptimeMillis();
        final long duration = 2000;

        Projection proj = mMap.getProjection();
        final LatLng markerLatLng = marker.getPosition();
        Point startPoint = proj.toScreenLocation(markerLatLng);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });

        //return false; //have not consumed the event
        return true; //have consumed the event
    }
    private void addProximityAlert() {

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
                vc, // the latitude of the central point of the alert region
                address, // the longitude of the central point of the alert region
                POINT_RADIUS, // the radius of the central point of the alert region, in meters
                PROX_ALERT_EXPIRATION, // time for this proximity alert, in milliseconds, or -1 to indicate no                           expiration
                proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
        );

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        registerReceiver(new ProximityIntentReceiver(), filter);
        Toast.makeText(getApplicationContext(),"Alert Added",Toast.LENGTH_SHORT).show();
    }
}



