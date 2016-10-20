package com.lvbo.template.module.tutorial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import com.lvbo.template.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TutorialDot extends LinearLayout {
    @Bind(R.id.enabled)
    View enabled;
    @Bind(R.id.disabled)
    View disabled;

    public TutorialDot(Context context) {
        super(context);
        init(context);
    }

    public TutorialDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TutorialDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context ctx) {
        LayoutInflater.from(ctx).inflate(R.layout.tutorial_dot_layout, this, true);
        ButterKnife.bind(this);
    }

    public void enable() {
        enabled.setVisibility(View.VISIBLE);
        disabled.setVisibility(View.GONE);
    }


    public void disable() {
        enabled.setVisibility(View.GONE);
        disabled.setVisibility(View.VISIBLE);
    }
}
