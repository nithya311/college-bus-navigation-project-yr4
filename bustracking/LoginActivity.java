package com.example.eotrainee.bustracking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Hashtable;
import java.util.Map;

import static com.example.eotrainee.bustracking.R.id.busfrom;
import static com.example.eotrainee.bustracking.R.id.busroot;
import static com.example.eotrainee.bustracking.R.id.busto;

public class LoginActivity extends AppCompatActivity {
EditText username,drivermname;
    Button busstartt;
    private static final String REGISTER_URL = "http://192.168.1.20/busproject1/loginuser.php";
    private String KEY_NAME = "busnumber";
    private String KEY_USERNAME = "drivername";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        username = (EditText)findViewById( R.id.loginusername );
        drivermname = (EditText)findViewById( R.id.loginpass );
        busstartt = (Button)findViewById( R.id.loginButton );
        sp = getSharedPreferences( "driver", Context.MODE_PRIVATE );
        busstartt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        } );

    }
    private void LoginUser(){
        //Showing the progress dialog
        final  String busno1 = username.getText().toString();
        final  String drivarname1 = drivermname.getText().toString();

        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        if(s.equalsIgnoreCase( "success" )){
                            String number  = busno1.toString();
                            sp.edit().putString( "username",number ).commit();

Intent i=  new Intent( LoginActivity.this,BusProfile.class );
                            i.putExtra( "busno", number);
                            startActivity( i );
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed" , Toast.LENGTH_LONG).show();
                        }
                        //Showing toast message of the response
                        Toast.makeText(LoginActivity.this, s , Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(LoginActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
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
