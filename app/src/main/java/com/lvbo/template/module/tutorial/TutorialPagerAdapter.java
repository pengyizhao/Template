package com.lvbo.template.module.tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lvbo.template.R;


/**
 * Created by chrisyu on 4/3/16.
 */
public class TutorialPagerAdapter extends FragmentStatePagerAdapter {
    private int[] pages = {R.mipmap.ic_launcher
            , R.mipmap.ic_launcher
            , R.mipmap.ic_launcher
            , R.mipmap.ic_launcher};
    public TutorialPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TutorialItemFragment.newInstance(pages[position]);
    }

    @Override
    public int getCount() {
        return pages.length;
    }
}