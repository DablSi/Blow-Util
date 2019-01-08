package com.example.ducks.blow_util;

import android.content.Intent;

import android.os.AsyncTask;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    ImageView imv;
    ImageView imv1;
    ImageView imv2;
    ImageView imv3;
    ImageView imv4;

    int k=0;

    public void animcandle(final ImageView imv) {
        NewThread newThread = new NewThread();
        newThread.execute();

        final Runnable showable=new Runnable() {
            @Override
            public void run() {
                if (k == 4) k = 0;
                if(k==0)
                    imv.setImageResource(R.drawable.fire1);
                if(k==1)
                    imv.setImageResource(R.drawable.fire2);
                if(k==2)
                    imv.setImageResource(R.drawable.fire3);
                if(k==3)
                    imv.setImageResource(R.drawable.fire4);
                k++;
            }
        };
        final Handler h=new Handler();

        timer = new Timer();
        timer.schedule( new TimerTask() {

            @Override
            public void run() {

                h.postDelayed(showable,0);

            }

        },0,100);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewThread newThread = new NewThread();
        newThread.execute();
        imv=(ImageView) findViewById(R.id.fire1);
        imv1=(ImageView) findViewById(R.id.fire2);
        imv2=(ImageView) findViewById(R.id.fire3);
        imv3=(ImageView) findViewById(R.id.fire4);
        imv4=(ImageView) findViewById(R.id.fire5);
        animcandle(imv);
        animcandle(imv1);
        animcandle(imv2);
        animcandle(imv3);
        animcandle(imv4);
    }

    class NewThread extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            startService(new Intent(MainActivity.this, NotificationService.class));
            return null;
        }
    }
}