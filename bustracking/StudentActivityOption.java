package com.example.eotrainee.bustracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StudentActivityOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_option );
    }
    public void studentregister(View v){
        Intent i = new Intent( StudentActivityOption.this,StudentRegisterPage.class );
        startActivity( i );

    }
    public void studentlogin(View v){
        Intent i = new Intent( StudentActivityOption.this,StudentLoginPage.class );
        startActivity( i );
    }

}
