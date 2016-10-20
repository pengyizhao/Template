package com.lvbo.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.lvbo.template.MainActivity;
import com.lvbo.template.MyApplication;
import com.lvbo.template.R;
import com.lvbo.template.common.Utils.ScreenUtils;
import com.lvbo.template.common.widget.CustomTextView;
import com.lvbo.template.entity.ErrorMessage;
import com.lvbo.template.entity.HiddenDialog;
import com.lvbo.template.entity.RequestFail;
import com.lvbo.template.entity.ShowDialog;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Created by lvbo on 16/9/2.
 */
public abstract class BaseFragment extends Fragment {

    protected RelativeLayout contentView;
    protected CustomTextView leftBack;
    protected ImageView centerImg;
    protected CustomTextView centerTxt;
    protected CustomTextView rightMen;

    protected RelativeLayout topToolBar;
    protected LinearLayout subContainer;

    protected Tracker mTracker;

    protected  BasePresenter basePresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Google Analytics  Google分析
        MyApplication application = (MyApplication) getBaseActivity().getApplication();
        mTracker = application.getDefaultTracker();

        basePresenter=getPresenter();
        if (null!=basePresenter) {
            basePresenter.register();
        }

        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = (RelativeLayout) inflater.inflate(R.layout.base_fmt, container, false);
            subContainer= (LinearLayout) contentView.findViewById(R.id.sub_container);

            View bodyView = getBodyView(inflater);
            subContainer.addView(bodyView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ButterKnife.bind(this, contentView);

            init();
            setUp(contentView);
        }

        if (null == contentView) {
            ButterKnife.bind(this, contentView);
        }





        return contentView;
    }
    public abstract BasePresenter getPresenter();

    @Override
    public void onResume() {
        super.onResume();
        //Google Analytics  Google分析
        mTracker.setScreenName(getName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    protected String getName(){
        return getClass().getSimpleName();
    }

    public View getBodyView(LayoutInflater inflater) {
        return inflateLayout(inflater);
    }

    public abstract int getLayoutId();
    public abstract void setUp(View contentView);



    protected View inflateLayout(LayoutInflater inflater) {
        View v = inflater.inflate(getLayoutId(), null, false);
        return v;
    }

    public void setToolBarPaddingTop(int top) {
        if (topToolBar != null){
         ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) topToolBar.getLayoutParams();
            params.topMargin=top;
        }

    }

    protected void init() {
        leftBack = (CustomTextView) contentView.findViewById(R.id.img_left_back);
        centerImg = (ImageView) contentView.findViewById(R.id.img_center);
        centerTxt = (CustomTextView) contentView.findViewById(R.id.txt_center);//默认是显示文字
        rightMen = (CustomTextView) contentView.findViewById(R.id.img_right);
        topToolBar = (RelativeLayout) contentView.findViewById(R.id.top_tool_bar);

        leftBack.setOnClickListener(listener);
        rightMen.setOnClickListener(listener);
        centerImg.setOnClickListener(listener);
        centerTxt.setOnClickListener(listener);

        setLock();
        setToolBarTransparent(false);
        setToolBarPaddingTop(ScreenUtils.getStatusBarHeight(getBaseActivity()));
    }

    protected void showView(View view) {
        if (null != view) {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void hiddenView(View view) {
        if (null != view) {
            view.setVisibility(View.GONE);
        }
    }

    protected void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            centerTxt.setText(title);
        }
    }

    public void setToolBarTransparent(boolean isTransparent){
        if (isTransparent) {
            topToolBar.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.transparent));
            subContainer.setPadding(0, 0, 0, 0);

        } else {
            topToolBar.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.white));
//            int tooBarHeight= ScreenUtils.dpToPx(48,getBaseActivity());
            int tooBarHeight= ScreenUtils.dip2px(getBaseActivity(),48);
            int statusHeight=ScreenUtils.getStatusBarHeight(getBaseActivity());
            subContainer.setPadding(0, tooBarHeight+statusHeight, 0, 0);
        }

    }


    /**
     * 显示加载进度条
     */
    public void showProgressDialog() {
        if (getBaseActivity()!=null) {
            getBaseActivity().showProgressDialog();
        }

    }

    /**
     * 隐藏加载进度条
     */
    public void hideProgressDialog() {
        if (getBaseActivity()!=null) {
            getBaseActivity().hideProgressDialog();
        }

    }



    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.img_left_back:
                    leftBackListener();
                    break;
                case R.id.img_center:
                    centerBtnListener();
                    break;
                case R.id.img_right:
                    rightBtnListener();
                    break;
            }
        }
    };

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void replaceFragmentWithBackStack(BaseFragment fragment) {
        getBaseActivity().replaceFragmentWithBackStack(fragment);
    }

    public void replaceFragment(BaseFragment fragment) {
        getBaseActivity().replaceFragment(fragment);
    }
    public void addFragmentToTop(BaseFragment fragment) {
        getBaseActivity().addFragmentToTop(fragment);
    }
    /**
     * 默认是返回 如果有不同的处理情况,可以在子类里重写
     */
    protected void leftBackListener() {
        getBaseActivity().onBackPressed();
    }

    /**
     * 默认是显示
     */
    protected void centerBtnListener() {

    }

    /**
     * 默认不处理, 如果有不同的处理情况,可以在子类里重写
     */
    protected void rightBtnListener() {

    }


    /**
     * 不能滑动
     */
    public void setLock() {
        ((MainActivity) getActivity()).setLock();
    }

    /**
     * 能滑动
     */
    public void setUnLock() {
        ((MainActivity) getActivity()).setUnLock();
    }


    /**
     * 不能滑动
     */
    public void openMenu() {
        ((MainActivity) getActivity()).openMenu();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        if (null!=basePresenter) {
            basePresenter.unRegister();
        }

        EventBus.getDefault().unregister(this);
    }

    public void showToask(String message){
        Toast.makeText(getBaseActivity(),message,Toast.LENGTH_LONG).show();
    }

    public void showToask(int messageId){
        Toast.makeText(getBaseActivity(),messageId,Toast.LENGTH_LONG).show();
    }


    public void onEventMainThread(RequestFail fail){
        hideProgressDialog();
    }

    public void onEventMainThread(ErrorMessage message){
        hideProgressDialog();
    }

    public void onEventMainThread(ShowDialog message){
        showProgressDialog();
    }

    public void onEventMainThread(HiddenDialog message){
        hideProgressDialog();
    }

}
