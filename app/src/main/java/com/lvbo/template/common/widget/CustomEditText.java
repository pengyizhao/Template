package com.lvbo.template.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.lvbo.template.R;

/**
 * 此类主要是为了设置自定义（assets文件中的字体）字体
 * Created by lvbo on 16/6/27.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFontFamily(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontFamily(context, attrs);
    }

    public CustomEditText(Context context) {
        super(context);

    }

    public void setTypeface(Context context,String fontType) {
        if (!TextUtils.isEmpty(fontType)){

            if(fontType.equals(context.getResources().getString(R.string.Apex_New_Bold))) {
                super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Apex_New_Bold.otf"));
            } else if(fontType.equals(context.getResources().getString(R.string.Apex_New_Medium))){
                super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Apex_New_Medium.otf"));
            } else if(fontType.equals(context.getResources().getString(R.string.Arial_Bold))){
                super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial_Bold.ttf"));
            } else if(fontType.equals(context.getResources().getString(R.string.Arial))){
                super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial.ttf"));
            }else if(fontType.equals(context.getResources().getString(R.string.fontello))){
                super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontello.ttf"));
            }
        }
    }

    private void setFontFamily(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFontType);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomFontType_txtFontType:
                    String fontType = a.getString(attr);
                    if(fontType.equals(context.getResources().getString(R.string.Apex_New_Bold))) {
                        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Apex_New_Bold.otf"));
                    } else if(fontType.equals(context.getResources().getString(R.string.Apex_New_Medium))){
                        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Apex_New_Medium.otf"));
                    } else if(fontType.equals(context.getResources().getString(R.string.Arial_Bold))){
                        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial_Bold.ttf"));
                    } else if(fontType.equals(context.getResources().getString(R.string.Arial))){
                        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Arial.ttf"));
                    }else if(fontType.equals(context.getResources().getString(R.string.fontello))){
                        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/fontello.ttf"));
                    }

                    break;

            }
        }
        if (N==0) {
            super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Apex_New_Medium.otf"));
        }
    }
}