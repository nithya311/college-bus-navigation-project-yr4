package com.example.eotrainee.bustracking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import static com.example.eotrainee.bustracking.R.id.busno;


public class BusProfile extends AppCompatActivity {
Button start;
    private static final String REGISTER_URL = "http://192.168.1.20/busproject1/updatebus.php";
    private static final String REGISTER_URL1 = "http://192.168.1.20/busproject1/deletedriver.php";
    private String KEY_BUSNUMBER = "busnumber";
    private String KEY_BUSLATITUDE = "Buscurrentlat";
    private String KEY_BUSLOGITUDE = "buscurrentlogi";
    private String KEY_BUSNUMBER1 = "busnumber";



    String data;
    ProgressDialog loading;
    SharedPreferences sp;
    String ss;
    private Handler mHandler = new Handler();
TextView bsno,dvnumber,bsroot,busfrm,b1,b2,b3,b4,b5,bto;
    String bsno1,dvnumber1,bsroot1,busfrm1,b11,b21,b31,b41,b51,bto1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bus_profile );
        bsno =(TextView)findViewById( busno );
        dvnumber=(TextView)findViewById( R.id.drivername );
        bsroot=(TextView)findViewById( R.id.busroot );
        busfrm=(TextView)findViewById( R.id.busfrom );
        b1=(TextView)findViewById( R.id.busone );
        b2=(TextView)findViewById( R.id.bustwo );
        b3=(TextView)findViewById( R.id.busthree );
        b4=(TextView)findViewById( R.id.busfour );
        b5=(TextView)findViewById( R.id.busfive );
        bto=(TextView)findViewById( R.id.busto );

data = getIntent().getStringExtra( "busno" );
        sp = getSharedPreferences( "driver", Context.MODE_PRIVATE );
     ss = sp.getString( "username","no value" );
        Toast.makeText( getApplicationContext(),"Inteent--"+data,Toast.LENGTH_SHORT ).show();
        Toast.makeText( getApplicationContext(),"shared--"+ss,Toast.LENGTH_SHORT ).show();
        start =(Button)findViewById( R.id.busstartdriver );
        start.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*UpdateUser();*/
                startService(new Intent(BusProfile.this , Sample_service.class));

            }
        } );
        getData();
    }

    private void getData() {
        String id = ss.toString();
        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL+id.toString();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BusProfile.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String busno="";
        String busdriver="";
        String bsroot1= "";
        String bsfrom1= "";
        String bs1= "";
        String bs2= "";
        String bs3= "";
        String bs4= "";
        String bs5= "";
        String bsto= "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);


            busno = collegeData.getString(Config.KEY_BUSNUMBER1);
            busdriver = collegeData.getString(Config.KEY_DRIVERNAME);
            bsroot1 = collegeData.getString(Config.KEY_BUSROOT);
            bsfrom1 = collegeData.getString(Config.KEY_BUSFROM);
            bs1 = collegeData.getString(Config.KEY_BUSFIRST);
            bs2 = collegeData.getString(Config.KEY_BUSSECOND);
            bs3 = collegeData.getString(Config.KEY_BUSTHIRD);
            bs4 = collegeData.getString(Config.KEY_BUSFOUR1);
            bs5 = collegeData.getString(Config.KEY_BUSFIVE1);
            bsto = collegeData.getString(Config.KEY_BUSTO1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        bsno.setText( busno );
        dvnumber.setText( busdriver );
        bsroot.setText( bsroot1 );
        busfrm.setText( bsfrom1 );
        b1.setText( bs1 );
        b2.setText(  bs2);
        b3.setText( bs3 );
        b4.setText( bs4 );
        b5.setText(bs5);
        bto.setText(bsto);






/*        textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);*/

    }
    private void UpdateUser(){
        //Showing the progress dialog
        final  String busno1 ="aas";
        final  String drivarname1 = "12132";
        final  String driverlogit = "1223443";


        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                      Toast.makeText( getApplicationContext(),s,Toast.LENGTH_SHORT ).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(BusProfile.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String




                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put(KEY_BUSNUMBER, busno1);
                params.put(KEY_BUSLATITUDE, drivarname1);
                params.put(KEY_BUSLOGITUDE, driverlogit);




                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    public void stop(View v){

        stopService(new Intent(BusProfile.this , Sample_service.class));
    }

public void update(View v){

    Intent i =  new Intent( BusProfile.this,Main3Activity.class );

    i.putExtra("bsno",  bsno.getText().toString() );
    i.putExtra( "dvnumber",  dvnumber.getText().toString());
    i.putExtra("bsroot",bsroot.getText().toString()  );
    i.putExtra( "busfrm",busfrm.getText().toString() );
    i.putExtra( "b1" ,b1.getText().toString());
    i.putExtra( "b2",b2.getText().toString() );
    i.putExtra("b3",b3.getText().toString());
    i.putExtra( "b4",b4.getText().toString() );
    i.putExtra( "b5",b5.getText().toString() );
    i.putExtra( "bto",bto.getText().toString() );
    startActivity( i );
}
    public void delete(View v){
        DeleteUser();
    }
    private void DeleteUser(){
        //Showing the progress dialog
        final  String busno123 =ss.toString();



        final ProgressDialog loading = ProgressDialog.show(this,"Deleting...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, REGISTER_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Toast.makeText( getApplicationContext(),s,Toast.LENGTH_SHORT ).show();
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(BusProfile.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String




                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put(KEY_BUSNUMBER1, busno123);





                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
