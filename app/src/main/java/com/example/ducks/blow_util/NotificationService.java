package com.example.ducks.blow_util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

public class NotificationService extends Service {

    private mBatInfoReceiver myBatInfoReceiver;

    private class mBatInfoReceiver extends BroadcastReceiver {

        int temp = 0;

        float get_temp(){
            return (float) (Float.valueOf(temp) / 10.0);
        }

        @Override
        public void onReceive(Context arg0, Intent intent) {
            temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            if(myBatInfoReceiver.get_temp() >= 28.0) {
                task(true);
            }
        }

    };
    int count=0;
    public NotificationService() {
    }
    public void onCreate() {
        super.onCreate();
        myBatInfoReceiver = new mBatInfoReceiver();

        this.registerReceiver(this.myBatInfoReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        task(false);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();

    }

    public IBinder onBind(Intent intent) {

        return null;
    }
    public void task(boolean itstime){
        int idhot=1;
        int idfaster=2;
        int idburnin=3;

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (itstime) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.diamka).setTicker("Внимание!")
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.diamka)).setContentTitle("BlowUtil")
                        .setContentText("ГАРЯЧО").setContentInfo("ГОРИМ").setNumber(++count).setContentIntent(resultPendingIntent).setColor(Color.RED);

                Notification notification = builder.build();
                notification.defaults = Notification.DEFAULT_ALL;

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(idhot, notification);
            }
            itstime = false;
    }
}