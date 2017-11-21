package com.example.eotrainee.bustracking;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.R.attr.bitmap;

public class RegisterActivity extends AppCompatActivity {
EditText busno,drivarname,busroot,busfrom,busfirst,bustwo,busthee,busfour,busfive,busto;
    private static final String REGISTER_URL = "http://192.168.1.20/busproject1/userreg1.php";
    private String KEY_NAME = "busnumber";
    private String KEY_USERNAME = "drivername";
    private String KEY_PASS = "busroot";
    private String KEY_PHONE = "busfrom";
    private String KEY_AREA = "busfirst";
    private String KEY_TYPE = "bussecond";
    private String KEY_CODE = "busthird";
    private String KEY_DIS= "busfour";
    private String KEY_LATI= "busfive";
    private String KEY_LOGI= "busto";

    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        busno = (EditText)findViewById( R.id.busnumber );
        drivarname = (EditText)findViewById( R.id.busdrivername );
        busroot = (EditText)findViewById( R.id.busroot );
        busfrom = (EditText)findViewById( R.id.busfrom );
        busfirst = (EditText)findViewById( R.id.busstop1 );
        bustwo = (EditText)findViewById( R.id.busstop2 );
        busthee = (EditText)findViewById( R.id.busstop3 );
        busfour = (EditText)findViewById( R.id.buststop4 );
        busfive = (EditText)findViewById( R.id.busstop5 );
        busto = (EditText)findViewById( R.id.busto );


        register = (Button)findViewById( R.id.busregister );


        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

                /*String busno1 = busno.getText().toString();
                String drivarname1 = drivarname.getText().toString();
                String busroot1 =busroot.getText().toString();
                String busfrom1 = busfrom.getText().toString();
                String busfirst1= busfirst.getText().toString();
                String bustwo1 = bustwo.getText().toString();
                String busthree1 = busthee.getText().toString();

                String busfour1 = busfour.getText().toString();
                String busfive1 =  busfive.getText().toString();
                String busto1 = busto.getText().toString();

                if(busno1.toString().equals( "" )&&drivarname1.toString().equals( "" )&&busroot1.toString().equals( "" )&&busfrom1.toString().equals( "" )&&busfirst1.toString().equals( "" )&&busthree1.toString().equals( "" )&&busfour1.toString().equals( "" )&&busfive1.toString().equals( "" )&&busto1.toString().equals( "" )&&bustwo1.toString().equals( "" )){
                    Toast.makeText( getApplicationContext(),"Fill All Field",Toast.LENGTH_SHORT ).show();
                }else{
                    RegisterBus(busno1,drivarname1,busroot1,busfrom1,busfirst1,bustwo1,busthree1,busfour1,busfive1,busto1);
                    Toast.makeText( getApplicationContext(),"Successs",Toast.LENGTH_SHORT ).show();
                }*/

            }
        } );

    }

   /* private void RegisterBus(String busno1, String drivarname1, String busroot1, String busfrom1, String busfirst1, String bustwo1, String busthree1, String busfour1, String busfive1, String busto1) {

        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("busnumber",params[0]);
                data.put("drivername",params[1]);
                data.put("busroot",params[2]);
                data.put("busfrom",params[3]);
                data.put("busfirst",params[4]);
                data.put("bussecond",params[5]);
                data.put("busthird",params[6]);
                data.put("busfour",params[7]);
                data.put("busfive",params[8]);
                data.put("busto",params[9]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }

        }

        RegisterUser ru = new RegisterUser();
        ru.execute(busno1,drivarname1,busroot1,busfrom1,busfirst1,bustwo1,busthree1,busfour1,busfive1,busto1);

    }*/
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void uploadImage(){
        //Showing the progress dialog
      final  String busno1 = busno.getText().toString();
        final  String drivarname1 = drivarname.getText().toString();
        final  String busroot1 =busroot.getText().toString();
        final   String busfrom1 = busfrom.getText().toString();
        final   String busfirst1= busfirst.getText().toString();
        final  String bustwo1 = bustwo.getText().toString();
        final   String busthree1 = busthee.getText().toString();

        final  String busfour1 = busfour.getText().toString();
        final String busfive1 =  busfive.getText().toString();
        final  String busto1 = busto.getText().toString();
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(RegisterActivity.this, s , Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(RegisterActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String




                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put(KEY_NAME, busno1);
                params.put(KEY_USERNAME, drivarname1);
                params.put(KEY_PASS, busroot1);
                params.put(KEY_PHONE, busfrom1);
                params.put(KEY_AREA, busfirst1);
                params.put(KEY_TYPE, bustwo1);
                params.put(KEY_CODE, busthree1);
                params.put(KEY_DIS, busfour1);
                params.put(KEY_LATI, busfive1);
                params.put(KEY_LOGI, busto1);




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
