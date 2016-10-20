package com.lvbo.template.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.lvbo.template.MyApplication;
import com.lvbo.template.common.Utils.AppUtil;
import com.lvbo.template.common.Utils.LanguageUtils;
import com.lvbo.template.common.Utils.SPUtils;
import com.lvbo.template.config.AppConfig;
import com.lvbo.template.constant.Constant;

import butterknife.ButterKnife;

/**
 * Created by lvbo on 16/9/2.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Tracker mTracker;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        setupAppLanguage();
        setupPushToken();

        View contentView = getContentView();
        setContentView(contentView);
        ButterKnife.bind(this);
        setUp(contentView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName(getName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    protected String getName(){
        return getClass().getSimpleName();
    }

    public View inflateLayout() {
        View v = getLayoutInflater().inflate(getLayoutId(), null, false);
        return v;
    }

    private void setupAppLanguage(){
        String locale = (String) SPUtils.get(BaseActivity.this, Constant.KEY_APP_LANGUAGE, AppUtil.getDefaultAppLanguage());
        if(!TextUtils.isEmpty(locale)) {
            AppConfig.LANGUAGE = locale;
            LanguageUtils.setupLanguage(getBaseContext(), locale);
        }

    }

    private void setupPushToken(){
        String pushToken = (String) SPUtils.get(BaseActivity.this, Constant.KEY_PUSH_TOKEN, "");
        AppConfig.PUSH_TOKEN = pushToken;
    }

    /**
     * 获取获取contentView
     *
     * @return
     */
    public  View getContentView(){
        View v = getLayoutInflater().inflate(getLayoutId(), null, false);
        return v;
    }

    /**
     * 获取layout的id
     *
     * @return
     */
    public abstract int getLayoutId();


    public abstract int getReplaceId();

    /**
     * 界面初始化
     *
     * @param ContentView
     * @return
     */
    public abstract void setUp(View ContentView);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    ProgressDialog progressDialog;

    /**
     * 显示加载进度条
     */
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", "", true);
        } else {
            progressDialog.show();
        }
    }

    /**
     * 隐藏加载进度条
     */
    public void hideProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    /**
     * 显示fragment,并添加到返回栈
     *
     * @param fragment 将要显示的fragment
     */
    public void replaceFragmentWithBackStack(BaseFragment fragment) {
        fragmentManager.beginTransaction()
                .replace(getReplaceId(), fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    /**
     * 显示指定的fragment,不添加到返回栈
     *
     * @param fragment 将要显示的fragment
     */
    public void replaceFragment(BaseFragment fragment) {

        //如果当前显示的fragment已经是要显示的fragment,则不做任何处理
        if (getCurrentFragment() != null && getCurrentFragment().getClass().toString().equals(fragment.getClass().toString())) {
            return;
        }

        fragmentManager.beginTransaction()
                .replace(getReplaceId(), fragment)
                .commitAllowingStateLoss();
    }

    /**
     * 获取当前content显示的fragment
     *
     * @return
     */
    public BaseFragment getCurrentFragment() {
        return (BaseFragment) fragmentManager.findFragmentById(getReplaceId());
    }

    public void addFragmentToTop(BaseFragment mFragment) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
//                mFragmentManager.popBackStack();
                try {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    fragmentManager.executePendingTransactions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        replaceFragment(mFragment);
    }

    @Override
    public void onBackPressed() {

        /**将返回事件交给fragment处理**/
        BaseFragment currentFragment = getCurrentFragment();

        if (fragmentManager.getBackStackEntryCount() < 1) {

            super.onBackPressed();
        } else {

            if (currentFragment instanceof OnBackPressedListener) {
                ((OnBackPressedListener) currentFragment).onBackPressed();
            }
            super.onBackPressed();
        }

    }
}
