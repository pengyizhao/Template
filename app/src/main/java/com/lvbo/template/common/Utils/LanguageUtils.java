package com.lvbo.template.common.Utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import com.lvbo.template.config.AppConfig;
import com.lvbo.template.constant.Constant;

import java.util.Locale;

/**
 * ==================================================================
 * Copyright (C) 2016 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 16/9/7 14:53
 * @description ${todo}
 * <p>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 16/9/7 14:53  Drew.Chiang       v1.0.0          create
 * <p>
 * ==================================================================
 */
public class LanguageUtils {

    public static void switchLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Locale.setDefault(locale);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(config.locale);
        }
        resources.updateConfiguration(config, dm);
    }

    public static void setupLanguage(Context context, String locale){

        if(TextUtils.isEmpty(locale)){
            return;
        }
        if(locale.equals(Constant.LANG_SC)){
            switchLanguage(context, Locale.SIMPLIFIED_CHINESE);
        }else if(locale.equals(Constant.LANG_TC)){
            switchLanguage(context, Locale.TRADITIONAL_CHINESE);
        }else{
            switchLanguage(context, Locale.ENGLISH);
        }

        AppConfig.LANGUAGE = locale;
        SPUtils.put(context, Constant.KEY_APP_LANGUAGE, locale);

    }

}
