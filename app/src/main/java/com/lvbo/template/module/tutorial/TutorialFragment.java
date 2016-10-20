package com.lvbo.template.module.tutorial;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lvbo.template.R;
import com.lvbo.template.base.BaseFragment;
import com.lvbo.template.base.BasePresenter;
import com.lvbo.template.config.AppConfig;

import butterknife.Bind;

public class TutorialFragment extends BaseFragment {

    @Bind(R.id.tutorial_viewpager)
    ViewPager tutorialViewpager;
    @Bind(R.id.tutorial_dot_panel)
    LinearLayout tutorialDotPanel;
    @Bind(R.id.txt_descript)
    TextView txtDescript;

    TutorialPagerAdapter adapter;
    public static TutorialFragment getInstance() {
        TutorialFragment homeFragment = new TutorialFragment();
        return homeFragment;
    }


    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.tutorial_activity_layout;
    }

    @Override
    public void setUp(View contentView) {


        setToolBarTransparent(true);
        hiddenView(leftBack);
        hiddenView(centerTxt);
        hiddenView(centerImg);
        showView(rightMen);
        rightMen.setTypeface(getBaseActivity(), getString(R.string.Apex_New_Medium));
        rightMen.setText(getResources().getString(R.string.skip));
        rightMen.setTextColor(getResources().getColor(R.color.color_black));

        adapter=new TutorialPagerAdapter(getChildFragmentManager());

        tutorialViewpager.setAdapter(adapter);
        tutorialViewpager.setOnPageChangeListener(new TutorialOnPageChangeListener(tutorialDotPanel, txtDescript));

        AppConfig.TUTORIAL_COUNT=adapter.getCount();
        TutorialPresenter.initTutorialPanel(tutorialDotPanel, getActivity(), AppConfig.TUTORIAL_COUNT);
    }


}
