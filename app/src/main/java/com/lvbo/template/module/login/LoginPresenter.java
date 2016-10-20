package com.lvbo.template.module.login;


import android.util.Log;

import com.lvbo.template.base.BasePresenter;
import com.lvbo.template.config.AppConfig;
import com.lvbo.template.entity.BaseResult;
import com.lvbo.template.network.WebServiceModel;

/**
 * Created by lvbo on 16/9/27.
 */

public class LoginPresenter extends BasePresenter {

    private ILogin.view v;


    public LoginPresenter(ILogin.view v){
        this.v=v;
    }

    public void login(){


        if (null==v) return;
        WebServiceModel.getInstance().login("","", "","",AppConfig.LANGUAGE);
        /*String username=v.getEmailAddress();
        String password=v.getPassword();
        //帐号不为空,则用帐号登录,FacebookID不为空则用FacebookID登录
        if ((!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) || !TextUtils.isEmpty(AppConfig.FACEBOOK_ID)) {
            WebServiceModel.getInstance().login(username,password, AppConfig.PUSH_TOKEN,AppConfig.FACEBOOK_ID,AppConfig.LANGUAGE);
        } else {
            return;
        }*/

    }

    public void loginWithFacebook() {
        /*HttpResult<LoginResult> callBack=new HttpResult<com.mtel.columbia.entity.LoginResult>() {
            @Override
            public void success(com.mtel.columbia.entity.LoginResult loginResult) {
                Log.i("login",loginResult.toString());
                if (null==v) return;
                v.hideProgress();
                if (loginResult.getResult().getReturnCode() == 1) {
                    AppConfig.LOGIN_WITH_FACEBOOK = true;
                    AppConfig.FACEBOOK_PROFILE_IMAGE = "http://graph.facebook.com/" + AppConfig.FACEBOOK_ID + "/picture?type=large";
                    AppConfig.TOKEN=loginResult.getMember().getToken();
                    v.success();
                } else {
                    v.fail(loginResult.getResult().getReturnText());
                }

            }

            @Override
            public void fail(String message) {
                if (null==v) return;
                v.hideProgress();
                v.fail(message);
            }
        };
        if (null==v) return;
        v.showProgress();
        WebServiceModel.getInstance().login(callBack,null,null, AppConfig.PUSH_TOKEN,AppConfig.FACEBOOK_ID,"en");*/
    }

    /*public void getFaceBookInfo( AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                new GraphRequest.GraphJSONObjectCallback() {

                    //當RESPONSE回來的時候
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //讀出姓名 ID FB個人頁面連結

                        AppConfig.FACEBOOK_ID = object.optString("id");
                        AppConfig.FACEBOOK_PROFILE_IMAGE = "http://graph.facebook.com/" + AppConfig.FACEBOOK_ID + "/picture?type=large";
                        String facebook_name = object.optString("name");

                        //拿到token后去请求公司服务端的API获取用户信息.
                        loginWithFacebook();
                        //拿到token后去请求公司服务端的API获取用户信息.

                    }
                });

        //包入你想要得到的資料 送出request
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();
    }*/



    public void onEventMainThread(BaseResult br){
        Log.i("","");

    }
}
