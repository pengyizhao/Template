package com.lvbo.template.module.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.android.gms.gcm.GcmListenerService;
import com.lvbo.template.MainActivity;
import com.lvbo.template.R;

/**
 * ==================================================================
 * Copyright (C) 2016 Mtelnet All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/4/2 19:14
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class MyListenerService extends GcmListenerService {

    private static int pendingIntentIndex = 0;
    private static int notificationId = 0;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        sendNotification(data);
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param extras GCM message received.
     */
    private void sendNotification(Bundle extras) {System.out.println("-------gcm message:"+extras.toString());
        String title = extras.getString("title");
        String message = extras.getString("MSG");

        if(TextUtils.isEmpty(title)){
            title = getString(R.string.app_name);
        }

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);


        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, pendingIntentIndex /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        pendingIntentIndex++;

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(MPFApplication.getApplication().getResources(), R.mipmap.mpfa_app_icon))
                .setWhen(System.currentTimeMillis())
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = notificationBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        try {
            notification.contentView.setImageViewResource(android.R.id.icon, R.mipmap.ic_launcher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationManager.notify(notificationId /* ID of notification */, notification);
        notificationId++;
    }

    /*
    public void createNotification(Context context, Bundle extras) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String appName = getAppName(this);

        // Intent notificationIntent = new Intent(this,
        // PushHandlerActivity.class);
        Intent notificationIntent = new Intent(this, CordovaApp.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("pushBundle", extras);

        PendingIntent contentIntent = PendingIntent.getActivity(this, pendingIntentIndex++, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        int defaults = Notification.DEFAULT_ALL;

        if (extras.getString("defaults") != null) {
            try {
                defaults = Integer.parseInt(extras.getString("defaults"));
            } catch (NumberFormatException e) {
            }
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setDefaults(defaults)
                .setSmallIcon(context.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(extras.getString("title"))
                .setTicker(extras.getString("title"))
                .setContentIntent(contentIntent).setAutoCancel(true);

        String message = extras.getString("MSG");
        if (message != null) {
            mBuilder.setContentText(message);
        } else {
            mBuilder.setContentText("<missing message content>");
        }

        String msgcnt = extras.getString("msgcnt");
        if (msgcnt != null) {
            mBuilder.setNumber(Integer.parseInt(msgcnt));
        }

        int notId = notificationId++;
        if(BuildConfig.DEBUG){
            Log.i(TAG, "extras: " + extras.toString());
            Log.i(TAG, "Notification ID: " + extras.getString("notId"));
        }

        try {
            notId = Integer.parseInt(extras.getString("notId"));
        } catch (NumberFormatException e) {
            if(BuildConfig.DEBUG)
                Log.e(TAG,"Number format exception - Error parsing Notification ID: " + e.getMessage());
        } catch (Exception e) {
            if(BuildConfig.DEBUG)
                Log.e(TAG, "Number format exception - Error parsing Notification ID" + e.getMessage());
        }

        mNotificationManager.notify((String) appName, notId, mBuilder.build());
    }*/
}
