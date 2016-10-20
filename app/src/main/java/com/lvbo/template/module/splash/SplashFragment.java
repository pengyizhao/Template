package com.lvbo.template.module.splash;

import android.text.TextUtils;
import android.view.View;


import com.lvbo.template.R;
import com.lvbo.template.base.BaseFragment;
import com.lvbo.template.base.BasePresenter;
import com.lvbo.template.common.Utils.SPUtils;
import com.lvbo.template.config.AppConfig;
import com.lvbo.template.constant.Constant;
import com.lvbo.template.module.login.ILogin;
import com.lvbo.template.module.login.LoginPresenter;
import com.lvbo.template.module.tutorial.TutorialFragment;


/**
 * Created by lvbo on 16/9/2.
 */
public class SplashFragment extends BaseFragment implements ILogin.view {

    String userName;
    String password;

    LoginPresenter presenter;

    public static SplashFragment getInstance() {
        SplashFragment splashFragment = new SplashFragment();
        return splashFragment;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter=new LoginPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.splash;
    }



    @Override
    public void setUp(View contentView) {
//        hiddenView(topToolBar);
        setUnLock();

        setToolBarTransparent(false);
        gotoNextFragment();

        presenter.login();
    }

    protected void leftBackListener() {
        openMenu();
    }


    /**
     * app版本检测
     */
    private void checkUpdate() {

//        showProgressDialog();

        /*HttpResult<VersionResult> callBack = new HttpResult<VersionResult>() {

            @Override
            public void success(VersionResult versionResult) {

                hideProgressDialog();

                if (versionResult.getResult().getReturnCode() == 1) {
                    String appVersion = VersionManagementUtil.getVersion(getBaseActivity());
                    String androidHardVersion = versionResult.getVersion().getAndroidHardVersion();
                    String androidVersion = versionResult.getVersion().getAndroidVersion();

                    if ((VersionManagementUtil.versionCompare(androidHardVersion, appVersion) > 0) && (VersionManagementUtil.versionCompare(androidHardVersion, androidVersion) > 0)) {
                        showVersionUpdateDialog(true, versionResult.getVersion().getAndroidDownloadLink());
                    } else if (VersionManagementUtil.versionCompare(androidVersion, appVersion) > 0) {
                        showVersionUpdateDialog(false, versionResult.getVersion().getAndroidDownloadLink());
                    } else {
                        gotoNextFragment();
                    }
                }

            }

            @Override
            public void fail(String message) {
                hideProgressDialog();
            }
        };

        WebServiceModel.getInstance().getVersion(callBack);*/
    }

    /**
     * 判断是否第一次打开应用,非第一次打开则处理登陆的情况
     */
    private void gotoNextFragment(){
        contentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getBaseActivity() == null) {
                    return;
                }
                boolean isFirst = (boolean) SPUtils.get(getBaseActivity(), Constant.First_OPEN_APP, true);
                if (isFirst) {
                    SPUtils.put(getBaseActivity(),Constant.First_OPEN_APP,false);
//                    replaceFragmentWithBackStack(TutorialFragment.getInstance(Constant.SHOW_TOOL_BAR, -1));
                    addFragmentToTop(TutorialFragment.getInstance());
                } else {

                    //判断是否有账号可以登录
                    userName= (String) SPUtils.get(getBaseActivity(),Constant.KEY_USERNAME,"");
                    password= (String) SPUtils.get(getBaseActivity(),Constant.KEY_PASSWORD,"");
                   String facebookId= (String) SPUtils.get(getBaseActivity(),Constant.KEY_FACEBOOK_ID,"");

                    if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                        LoginPresenter presenter=new LoginPresenter(SplashFragment.this);
                        presenter.login();
                    } else if(!TextUtils.isEmpty(facebookId)){
                        AppConfig.FACEBOOK_ID=facebookId;
                        LoginPresenter presenter=new LoginPresenter(SplashFragment.this);
                        presenter.loginWithFacebook();
                    }else {

//                        addFragmentToTop(MemberLoginFragment.getInstance());
                    }

                }
            }
        }, 500);
    }

    @Override
    public String getEmailAddress() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }



    @Override
    public void success() {

    }
}
