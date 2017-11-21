package com.example.eotrainee.bustracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu );

    }
    public void login(View v){
i = new Intent( MenuActivity.this,LoginActivity.class );
        startActivity( i );

    }
    public void register(View v){
        i = new Intent( MenuActivity.this,RegisterActivity.class );
        startActivity( i );

    }
}
