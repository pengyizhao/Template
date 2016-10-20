package com.lvbo.template.network;


import com.lvbo.template.constant.Constant;
import com.lvbo.template.entity.HiddenDialog;
import com.lvbo.template.entity.LoginResult;
import com.lvbo.template.entity.ShowDialog;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lvbo on 16/7/19.
 */
public class WebServiceModel {
    private static WebServiceModel mWebServiceModel;

    private APISercive mAPISercive;

    public static WebServiceModel getInstance() {
        if (null == mWebServiceModel) {
            mWebServiceModel = new WebServiceModel();
        }

        return mWebServiceModel;
    }

    private WebServiceModel() {
        OkHttpClient.Builder okHttpClientBuilder = OKHttpClientBuilderHelper.getBuilder();
        OkHttpClient okHttpClient = okHttpClientBuilder.build();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(Constant.DOMAINS)
                        .client(okHttpClient)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())//添加这个就可以返回String
//                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());//
        Retrofit retrofit = builder.build();

        mAPISercive = retrofit.create(APISercive.class);

    }

    private void doObservable(final Observable<LoginResult> mObservable, HttpResult result) {
       /* mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UIObserver<>(result));*/
    }


    private void doRequest(final boolean showDialog, final Observable mObservable) {
        showDialog(showDialog)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String v) {
                        return mObservable;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Object, Observable<?>>() {
                    @Override
                    public Observable<?> call(Object o) {
                        return hiddenDialog(showDialog, o);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UIObserver<>());
    }

    private Observable<String> showDialog(final boolean showDialog) {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                /**
                 * 这里可以发eventBus,也可以调用接口 来实现hiddenDialog
                 */

                if (showDialog) {
                    EventBus.getDefault().post(new ShowDialog());
                }

                subscriber.onNext("");
                subscriber.onCompleted();
            }
        });
    }


    private Observable<Object> hiddenDialog(final boolean showDialog, final Object o) {

        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

                /**
                 * 这里可以发eventBus,也可以调用接口 来实现hiddenDialog
                 */

                if (showDialog) {
                    EventBus.getDefault().post(new HiddenDialog());
                }


                subscriber.onNext(o);
                subscriber.onCompleted();
            }
        });
    }


    /********************************* 分割线以下是API***********************************/


    public void login( String username, String password, String push_token, String fb_id, String lang) {
        doRequest(true, mAPISercive.login(username, password, push_token, "aos", fb_id, lang));

    }


}
