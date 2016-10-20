/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lvbo.template.module.fcm;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.lvbo.template.common.Utils.SPUtils;
import com.lvbo.template.config.AppConfig;
import com.lvbo.template.constant.Constant;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).

        String token = FirebaseInstanceId.getInstance().getToken();
        AppConfig.PUSH_TOKEN = token;
        SPUtils.put(this, Constant.KEY_PUSH_TOKEN, token);

        System.out.println("PUSH_TOKEN: " + token);

        if(!TextUtils.isEmpty(AppConfig.TOKEN)) {
            sendRegistrationToServer(token);//将token上传给服务端
        }

    }
    // [END refresh_token]

    private void sendRegistrationToServer(String token) {

       /* HttpResult<EnablePushResult> callBack=new HttpResult<EnablePushResult>() {
            @Override
            public void success(EnablePushResult result) {

                if(result.getResult().getReturnCode() == 1) {
                    SPUtils.put(MyFirebaseInstanceIDService.this, Constant.KEY_DAILY_PUSH_ON, true);
                }
            }

            @Override
            public void fail(String message) {

            }
        };

        WebServiceModel.getInstance().enablePush(callBack, AppConfig.TOKEN, token, AppConfig.DEVICE_TYPE, AppConfig.LANGUAGE);*/
    }
}
