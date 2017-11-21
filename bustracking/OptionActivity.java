package com.example.eotrainee.bustracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_option );
    }

    public void student(View v){
        Intent i  =  new Intent( OptionActivity.this,Main2Activity.class );
        startActivity( i );
    }
    public void bus(View v){
        Intent i  =  new Intent( OptionActivity.this,MenuActivity.class );
        startActivity( i );
    }
    public void alert(View v){
        Intent i  =  new Intent( OptionActivity.this,AlertPriority.class );
        startActivity( i );
    }
}
