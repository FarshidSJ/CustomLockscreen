package com.farshid.customlockscreen.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.farshid.customlockscreen.Activity.LockScreenActivity;
import com.farshid.customlockscreen.R;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";

    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String ACTION_PAUSE = "ACTION_PAUSE";

    public static final String ACTION_PLAY = "ACTION_PLAY";

//    MainActivity.ScreenStateListener screenStateListener;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
//        screenStateListener = new MainActivity.ScreenStateListener();
        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
//        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, screenStateFilter);
        // do your jobs here
//        createNotificationChannel();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForeground(startId,getNotification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public Notification getNotification() {
        String channel;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            channel = createChannel();
        else {
            channel = "";
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel).setSmallIcon(R.drawable.ic_launcher).setContentTitle("CustomLockScreen is running");
        Notification notification = mBuilder
                .setPriority(Notification.PRIORITY_LOW)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();


        return notification;
    }

    @NonNull
    @TargetApi(26)
    private synchronized String createChannel() {
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        String name = "CustomLockScreen is running ";
        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(NOTIF_CHANNEL_ID, name, importance);

        mChannel.enableLights(true);
        mChannel.setLightColor(Color.BLUE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
        } else {
            stopSelf();
        }
        return NOTIF_CHANNEL_ID;
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Broadcast RECEIVED" );
            Intent mIntent = new Intent(context, LockScreenActivity.class);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }
    };

}
