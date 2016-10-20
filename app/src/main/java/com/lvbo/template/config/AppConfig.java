package com.lvbo.template.config;

/**
 * Created by lvbo on 16/9/2.
 */
public class AppConfig {

    public static boolean MODEL_DEBUG=true;//true的时候就可以打印http 的log
    public static String TOKEN="";//登陆成功返回的
    public static String PUSH_TOKEN="";//绑定GCM时需要的, 注册GCM时Google返回的token
    public static String FACEBOOK_ID;//登录时需要的Facebook的access  token
    public static String LANGUAGE="en";//登录时需要的语言
    public static final String GCM_SENDER_ID="";//

    public static int TUTORIAL_COUNT;//引导页count



}
