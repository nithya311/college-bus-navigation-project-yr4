package com.example.eotrainee.bustracking;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
TextView met;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_main );

        met = (TextView)findViewById( R.id.metel );
        String ss = "fonts/metel.ttf";
        Typeface tc = Typeface.createFromAsset( getAssets(),ss );
        met.setTypeface( tc );
         new Handler(  ).postDelayed( new Runnable() {
             @Override
             public void run() {
                 Intent i = new Intent( MainActivity.this,OptionActivity.class );
                 startActivity( i );
             }
         },5000);

    }
}
