package com.lvbo.template.module.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;


import com.lvbo.template.R;
import com.lvbo.template.base.BaseFragment;
import com.lvbo.template.base.BasePresenter;
import com.lvbo.template.common.Utils.LanguageUtils;
import com.lvbo.template.common.Utils.SPUtils;
import com.lvbo.template.common.widget.CustomTextView;
import com.lvbo.template.constant.Constant;

import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ==================================================================
 * Copyright (C) 2016 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 16/9/5 19:55
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 16/9/5 19:55  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class LanguageSettingFragment extends BaseFragment {

    @Bind(R.id.tv_setting_lang_sc)
    CustomTextView mLangScImg;

    @Bind(R.id.tv_setting_lang_tc)
    CustomTextView mLangTcImg;

    @Bind(R.id.tv_setting_lang_en)
    CustomTextView mLangEnImg;

    @OnClick(R.id.rl_setting_lang_sc)
    void selectLangSc(){
        mLangScImg.setVisibility(View.VISIBLE);
        mLangTcImg.setVisibility(View.INVISIBLE);
        mLangEnImg.setVisibility(View.INVISIBLE);

        LanguageUtils.setupLanguage(getActivity().getBaseContext(), Constant.LANG_SC);

        refreshViews();
    }

    @OnClick(R.id.rl_setting_lang_tc)
    void selectLangTc(){
        mLangScImg.setVisibility(View.INVISIBLE);
        mLangTcImg.setVisibility(View.VISIBLE);
        mLangEnImg.setVisibility(View.INVISIBLE);

        LanguageUtils.setupLanguage(getActivity().getBaseContext(), Constant.LANG_TC);

        refreshViews();
    }

    @OnClick(R.id.rl_setting_lang_en)
    void selectLangEn(){
        mLangScImg.setVisibility(View.INVISIBLE);
        mLangTcImg.setVisibility(View.INVISIBLE);
        mLangEnImg.setVisibility(View.VISIBLE);

        LanguageUtils.setupLanguage(getActivity().getBaseContext(), Constant.LANG_EN);

        refreshViews();
    }

    /*******************************************************************************************************/

    public static LanguageSettingFragment getInstance(){
        return new LanguageSettingFragment();
    }

    @Override
    public void setUp(View contentView) {
        showView(leftBack);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setTitle("Language setting");

        String localeType = (String) SPUtils.get(getActivity(), Constant.KEY_APP_LANGUAGE, "");
        if(TextUtils.isEmpty(localeType)) {
            Locale locale = Locale.getDefault();
            String lang = locale.getLanguage();
            String country = locale.getCountry();

            if("zh".equals(lang)){
                if("CN".equals(country)){
                    localeType = Constant.LANG_SC;
                }else {
                    localeType = Constant.LANG_TC;
                }
            }else if("en".equals(lang)){
                localeType = Constant.LANG_EN;
            }

        }

        if(Constant.LANG_SC.equals(localeType)){
            mLangScImg.setVisibility(View.VISIBLE);
            mLangTcImg.setVisibility(View.INVISIBLE);
            mLangEnImg.setVisibility(View.INVISIBLE);
        }else if(Constant.LANG_TC.equals(localeType)){
            mLangScImg.setVisibility(View.INVISIBLE);
            mLangTcImg.setVisibility(View.VISIBLE);
            mLangEnImg.setVisibility(View.INVISIBLE);
        }else if(Constant.LANG_EN.equals(localeType)){
            mLangScImg.setVisibility(View.INVISIBLE);
            mLangTcImg.setVisibility(View.INVISIBLE);
            mLangEnImg.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.language_setting_fmt;
    }


    private void refreshViews(){
        setTitle("Language setting");

    }

}
