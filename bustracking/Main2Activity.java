package com.example.eotrainee.bustracking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.eotrainee.bustracking.R.id.map;

public class Main2Activity extends AppCompatActivity {
Spinner sp1;
    ArrayList<String> personList;
    private String TAG = Main2Activity.class.getSimpleName();

    private static String url;
    private static final String TAG_PRODUCTIDS ="busnumber";
    String ss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );
        sp1 = (Spinner)findViewById( R.id.spinner );
        personList = new ArrayList<String>();
        url = "http://192.168.1.20/busproject1/viewadsAll.php";
       new getallsoil().execute();

    }
    class getallsoil extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(Main2Activity.this);
            dialog.setMessage("Please wait");
            dialog.setCancelable(false);
            dialog.show();
        }
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute( result );
            if (dialog.isShowing())
                dialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ArrayAdapter<String> adapter = new ArrayAdapter<String>( Main2Activity.this, android.R.layout.simple_dropdown_item_1line, personList);



            sp1.setAdapter( adapter );
            sp1.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     ss = (String) parent.getItemAtPosition( position );
                    Toast.makeText( Main2Activity.this,""+ss,Toast.LENGTH_SHORT ).show();
                 /*   Intent i =  new Intent( Main2Activity.this,Distance.class );
                    i.putExtra( "bus",ss );
                    startActivity( i );*/


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            } );
        }        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);





                        String productids = c.getString(TAG_PRODUCTIDS);






                        // Phone node is JSON Object




                        personList.add(productids);



                        // adding contact to contact list



                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

    }
    public void map(View v){

        String sss =  ss.toString();
        Intent i =  new Intent( Main2Activity.this,Distance.class );
        i.putExtra( "bus",sss );
        startActivity( i );
        Toast.makeText( getApplicationContext(),sss,Toast.LENGTH_SHORT ).show();
    }
}
