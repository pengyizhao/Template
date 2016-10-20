package com.lvbo.template.common.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.lvbo.template.constant.Constant;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lvbo on 16/9/6.
 */
public class AppUtil {
    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 隐藏输入键盘
     * @param mContext
     */
    public static void hiddenInput(Activity mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            try {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
            } catch (Exception e) {
                e.printStackTrace();
            }

//            ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }


    }


    public static boolean checkPlayServices(Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }

    public static void openBrowser(Context mContext,String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        mContext.startActivity(intent);
    }

    public static String getDecimalFormat(String txtlong){
        //设置mileage
        DecimalFormat format=new DecimalFormat("###,###");
        String mileag=format.format(Long.parseLong(txtlong));
        return mileag;
    }

    public static String getDefaultAppLanguage(){
            Locale locale = Locale.getDefault();
            String lang = locale.getLanguage();
            String country = locale.getCountry();

            if ("zh".equals(lang)) {
                if ("CN".equals(country)) {
                    return Constant.LANG_SC;
                } else {
                    return Constant.LANG_TC;
                }
            } else if ("en".equals(lang)) {
                return Constant.LANG_EN;
            }
            return Constant.LANG_SC;
    }
}
