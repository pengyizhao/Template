package com.lvbo.template.network;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

/**
 * Created by lvbo on 16/7/1.
 */
public class OkHttpsFactory implements ModelLoaderFactory<GlideUrl,InputStream> {
    @Override
    public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
        return new OkHttpUrlLoader();
    }

    @Override
    public void teardown() {

    }
}
