package com.example.eotrainee.bustracking;

import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by EOTrainee on 4/3/2017.
 */
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
public class Sample_service extends Service{

    Timer timer = new Timer();
    TimerTask updateProfile = new CustomTimerTask(Sample_service.this);

    public void onCreate() {

        super.onCreate();

        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        timer.scheduleAtFixedRate(updateProfile, 0, 10000);

    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        timer.cancel();
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

}
