package com.lvbo.template.module.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.lvbo.template.common.Utils.SPUtils;
import com.lvbo.template.config.AppConfig;
import com.lvbo.template.constant.Constant;

import java.io.IOException;

/**
 * ==================================================================
 * Copyright (C) 2016 Mtelnet All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/4/2 20:54
 * @description ${todo}
 * <p/>
 * ==================================================================
 */

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String action = intent.getStringExtra(Constant.KEY_REGISTER_ACTION);
        if(Constant.ACTION_REGISTER_GCM.equals(action)) {
            try {
                // [START register_for_gcm]
                // Initially this call goes out to the network to retrieve the token, subsequent calls
                // are local.
                // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
                // See https://developers.google.com/cloud-messaging/android/start for details on this file.
                // [START get_token]
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(AppConfig.GCM_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                // [END get_token]
                Log.i(TAG, "GCM Registration Token: " + token);

                AppConfig.PUSH_TOKEN = token;
                // TODO: Implement this method to send any registration to your app's servers

                sendRegistrationToServer(token);

                // Subscribe to topic channels
                subscribeTopics(token);

                // You should store a boolean that indicates whether the generated token has been
                // sent to your server. If the boolean is false, send the token to your server,
                // otherwise your server should have already received the token.
//            PreferenceUtils.shareInstance().putBoolean(Constants.SENT_TOKEN_TO_SERVER, true).commit();
                // [END register_for_gcm]
            } catch (Exception e) {
                Log.d(TAG, "Failed to complete token refresh", e);
                // If an exception happens while fetching the new token or updating our registration data
                // on a third-party server, this ensures that we'll attempt the update at a later time.
//            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
            }
            // Notify UI that registration has completed, so the progress indicator can be hidden.
            Intent registrationComplete = new Intent(Constant.REGISTRATION_COMPLETE);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        }else if(Constant.ACTION_UNREGISTER_GCM.equals(action)){
//            String pushToken = PreferenceUtils.shareInstance().getString(Constants.KEY_PUSH_TOKEN, "");

            try {
                if(!TextUtils.isEmpty(AppConfig.PUSH_TOKEN)) {
                    unsubscribeTopics(AppConfig.PUSH_TOKEN);

                    AppConfig.PUSH_TOKEN = "";

//                    PreferenceUtils.shareInstance().putBoolean(Constants.UNREGISTED_TOKEN_TO_SERVER, false).commit();
//                    PreferenceUtils.shareInstance().putBoolean(Constants.KEY_IS_PUSH_TURNED_OFF_BY_USER, true).commit();
//                    if(Account.currentAccount() != null){
//                        sendUnRegistrationToServer(pushToken);
//                    }
                    sendUnRegistrationToServer(AppConfig.PUSH_TOKEN);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        boolean isSent = (Boolean) SPUtils.get(getApplicationContext(),Constant.SENT_TOKEN_TO_SERVER, new Boolean(false));
        if(isSent){
            return;
        }

        // Add custom implementation, as needed.
        enablePromotionPush();

    }

    private void sendUnRegistrationToServer(String token) {
        boolean isSent = (Boolean) SPUtils.get(getApplicationContext(),Constant.UNREGISTED_TOKEN_TO_SERVER, false);
        if(isSent){
            return;
        }

        // Add custom implementation, as needed.
        disablePromotionPush();
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

    private void unsubscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.unsubscribe(token, "/topics/" + topic);
        }
    }


    private void enablePromotionPush(){
        SPUtils.put(RegistrationIntentService.this, Constant.KEY_PROMOTION_PUSH_ON, true);

        /*HttpResult<EnablePushResult> callBack=new HttpResult<EnablePushResult>() {
            @Override
            public void success(EnablePushResult result) {

                if(result.getResult().getReturnCode() == 1) {
                    SPUtils.put(RegistrationIntentService.this, Constant.KEY_PROMOTION_PUSH_ON, true);
                    AppConfig.PUSH_STATUS=true;
                }
            }

            @Override
            public void fail(String message) {

            }
        };
        WebServiceModel.getInstance().enablePush(callBack, AppConfig.TOKEN, AppConfig.PUSH_TOKEN, AppConfig.DEVICE_TYPE, AppConfig.LANGUAGE);*/

    }


    private void disablePromotionPush(){

        /*HttpResult<DisablePushResult> callBack=new HttpResult<DisablePushResult>() {
            @Override
            public void success(DisablePushResult result) {

                if(result.getResult().getReturnCode() == 1) {
                    SPUtils.put(RegistrationIntentService.this, Constant.KEY_PROMOTION_PUSH_ON, false);
                    AppConfig.PUSH_STATUS=false;
                }
            }

            @Override
            public void fail(String message) {

            }
        };
        WebServiceModel.getInstance().disablePush(callBack, AppConfig.TOKEN, AppConfig.PUSH_TOKEN, AppConfig.DEVICE_TYPE);*/

    }
}
