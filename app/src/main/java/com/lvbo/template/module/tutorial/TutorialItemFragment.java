package com.lvbo.template.module.tutorial;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvbo.template.R;
import com.lvbo.template.base.BaseFragment;
import com.lvbo.template.base.BasePresenter;

/**
 * Created by chrisyu on 4/3/16.
 */
public class TutorialItemFragment extends BaseFragment {

    private static final String PAGE = "page";
    private int mPage;







    public static TutorialItemFragment newInstance(int index) {
        TutorialItemFragment frag = new TutorialItemFragment();
        Bundle b = new Bundle();
        b.putInt(PAGE, index);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.tutorial_fragment_layout;
    }

    @Override
    public void setUp(View contentView) {
        setToolBarTransparent(true);
        hiddenView(topToolBar);
        ImageView tutorialImage= (ImageView) contentView.findViewById(R.id.img_tutorial_item);
        mPage = getArguments().getInt(PAGE);

        Glide.with(this).load(mPage).into(tutorialImage);
    }


}
