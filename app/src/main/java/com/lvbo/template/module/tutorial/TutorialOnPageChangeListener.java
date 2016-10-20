package com.lvbo.template.module.tutorial;

import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvbo.template.R;
import com.lvbo.template.config.AppConfig;


public class TutorialOnPageChangeListener implements ViewPager.OnPageChangeListener {
    Button skip_btn;
    LinearLayout tutorialPanel;
    TextView txtDescript;
    private int[] pagesStr = {R.string.app_name
            , R.string.app_name
            , R.string.app_name
            , R.string.app_name};

    public TutorialOnPageChangeListener(LinearLayout tutorialPanel,TextView txtDescript) {
//        this.skip_btn = tutorialFragment.skip;
        this.tutorialPanel = tutorialPanel;
        this.txtDescript=txtDescript;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        if(position == AppUtils.tutorial_page_count - 1)
//            skip_btn.setText(tutorialPanel.getContext().getString(R.string.finish));
//        else
//            skip_btn.setText(tutorialPanel.getContext().getString(R.string.btn_next));
        TutorialPresenter.redrawDotPanel(tutorialPanel, position, AppConfig.TUTORIAL_COUNT);
        if (null !=txtDescript) {
            txtDescript.setText(pagesStr[position]);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
