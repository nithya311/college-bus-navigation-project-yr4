package com.example.eotrainee.bustracking;

import java.util.Hashtable;
import java.util.Map;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by EOTrainee on 4/3/2017.
 */

public class CustomTimerTask extends TimerTask {

    private static final String REGISTER_URL = "http://192.168.1.20/busproject1/updatebus.php";
    private String KEY_BUSNUMBER12 = "busnumber";
    private String KEY_BUSLATITUDE = "Buscurrentlat";
    private String KEY_BUSLOGITUDE = "buscurrentlogi";
    double latitude;
    double longitude;
    private Context context;
    String names;
    SharedPreferences sp;

    private Handler mHandler = new Handler();
    public CustomTimerTask(Context con) {
        this.context = con;


    }


    // check if GPS enabled


    @Override
    public void run() {
        new Thread(new Runnable() {

            public void run() {

                mHandler.post(new Runnable() {
                    public void run() {

                        sp = context.getSharedPreferences( "driver", Context.MODE_PRIVATE );

                    names = sp.getString( "username","no vales" );
                        GPSTracker gps = new GPSTracker( context );
                        if(gps.canGetLocation()){

                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();



                            // \n is for new line
                            Toast.makeText(context.getApplicationContext(), "Your Location is - \nLat: "
                                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                        }else{
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gps.showSettingsAlert();
                        }
                        Toast.makeText(context, "DISPLAY"+latitude +" "+longitude , Toast.LENGTH_SHORT).show();

                            //Showing the progress dialog
                            final  String busno1 =names.toString();
                            final  String drivarname1 = ""+latitude;
                            final  String driverlogit = ""+longitude;



                            StringRequest stringRequest = new StringRequest( Request.Method.POST, REGISTER_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {
                                            //Disimissing the progress dialog

                                            Toast.makeText( context.getApplicationContext(),s,Toast.LENGTH_SHORT ).show();
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            //Dismissing the progress dialog


                                            //Showing toast
                                            Toast.makeText(context.getApplicationContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    //Converting Bitmap to String




                                    //Creating parameters
                                    Map<String,String> params = new Hashtable<String, String>();

                                    //Adding parameters

                                    params.put(KEY_BUSNUMBER12, busno1);
                                    params.put(KEY_BUSLATITUDE, drivarname1);
                                    params.put(KEY_BUSLOGITUDE, driverlogit);




                                    //returning parameters
                                    return params;
                                }
                            };

                            //Creating a Request Queue
                            RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                            //Adding request to the queue
                            requestQueue.add(stringRequest);


                    }
                });
            }
        }).start();

    }

}