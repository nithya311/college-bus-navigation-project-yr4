package com.example.eotrainee.bustracking;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

import static android.R.attr.onClick;
import static com.example.eotrainee.bustracking.R.id.busfive;
import static com.example.eotrainee.bustracking.R.id.busfour;
import static com.example.eotrainee.bustracking.R.id.busfrom;
import static com.example.eotrainee.bustracking.R.id.busno;
import static com.example.eotrainee.bustracking.R.id.busroot;
import static com.example.eotrainee.bustracking.R.id.busto;
import static com.example.eotrainee.bustracking.R.id.bustwo;

public class Main3Activity extends AppCompatActivity {
EditText ubusno,udrivarname,ubusroot,ubusfrom,ubusfirst,ubustwo,ubusthee,ubusfour,ubusfive,ubusto;
    String dubusno,dudrivarname,dubusroot,dubusfrom,dubusfirst,dubustwo,dubusthee,dubusfour,dubusfive,dubusto;
    private static final String REGISTER_URL = "http://192.168.1.20/busproject1/updatedriver.php";
    private String KEY_BUSNUMBER = "busnumber";
    private String KEY_DRIVERNAME = "drivername";
    private String KEY_BUSROOT = "busroot";
    private String KEY_BUSFROM = "busfrom";
    private String KEY_BUSFIRST = "busfirst";
    private String KEY_BUSSECOND = "bussecond";
    private String KEY_BUSTHIRD = "busthird";
    private String KEY_BUSFOURTH = "busfour";
    private String KEY_BUSFIVE = "busfive";
    private String KEY_BUSTO = "busto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main3 );
        ubusno = (EditText)findViewById( R.id.ubusnumber );
        udrivarname = (EditText)findViewById( R.id.ubusdrivername );
        ubusroot = (EditText)findViewById( R.id.ubusroot );
        ubusfrom = (EditText)findViewById( R.id.ubusfrom );
        ubusfirst = (EditText)findViewById( R.id.ubusstop1 );
        ubustwo = (EditText)findViewById( R.id.ubusstop2 );
        ubusthee = (EditText)findViewById( R.id.ubusstop3 );
        ubusfour = (EditText)findViewById( R.id.ubuststop4 );
        ubusfive = (EditText)findViewById( R.id.ubusstop5 );
        ubusto = (EditText)findViewById( R.id.ubusto );
        dubusno = getIntent().getStringExtra( "bsno" );
        dudrivarname= getIntent().getStringExtra( "dvnumber" );
        dubusroot= getIntent().getStringExtra( "bsroot" );
        dubusfrom= getIntent().getStringExtra( "busfrm" );
        dubusfirst= getIntent().getStringExtra( "b1" );
        dubustwo= getIntent().getStringExtra( "b2" );
        dubusthee= getIntent().getStringExtra( "b3" );
        dubusfour= getIntent().getStringExtra( "b4" );
        dubusfive= getIntent().getStringExtra( "b5" );
        dubusto= getIntent().getStringExtra( "bto" );
        ubusno.setText(dubusno  );
        udrivarname.setText(  dudrivarname);
        ubusroot.setText( dubusroot );
        ubusfrom.setText( dubusfrom );
        ubusfirst.setText( dubusfirst );
        ubustwo.setText( dubustwo );
        ubusthee.setText( dubusthee );
        ubusfour.setText( dubusfour );
        ubusfive.setText( dubusfive );
        ubusto.setText( dubusto );


    }
    public void update(View v){
        UpdateUser();

    }
    private void UpdateUser(){
        //Showing the progress dialog
        final  String busno12 =ubusno.getText().toString();
        final  String driver1 =udrivarname.getText().toString();
        final  String root1 =ubusroot.getText().toString();
        final  String from1 =ubusfrom.getText().toString();
        final  String first1 =ubusfirst.getText().toString();
        final  String second1 =ubustwo.getText().toString();
        final  String third1 =ubusthee.getText().toString();
        final  String four1 =ubusfour.getText().toString();
        final  String five1 =ubusfive.getText().toString();
        final  String to1 =ubusto.getText().toString();



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
                        Toast.makeText(Main3Activity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String




                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put(KEY_BUSNUMBER, busno12);
                params.put(KEY_DRIVERNAME, driver1);
                params.put(KEY_BUSROOT, root1);
                params.put(KEY_BUSFROM, from1);
                params.put(KEY_BUSFIRST, first1);
                params.put(KEY_BUSSECOND, second1);
                params.put(KEY_BUSTHIRD, third1);
                params.put(KEY_BUSFOURTH, four1);
                params.put(KEY_BUSFIVE, five1);
                params.put(KEY_BUSTO, to1);





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
