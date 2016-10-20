package com.lvbo.template.network;


import android.util.Log;


import com.lvbo.template.entity.BaseResult;
import com.lvbo.template.entity.ErrorMessage;
import com.lvbo.template.entity.RequestFail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;
import rx.Subscriber;

/**
 * Created by lvbo on 16/7/21.
 */
public class UIObserver<T> extends Subscriber<T> {


    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        Log.e("Http Observer error",e.getMessage());
        EventBus.getDefault().post(new ErrorMessage(e.getMessage()));

    }


    @Override
    public void onNext(T t) {

        /**
         * 此处要根据自己API返回的json的格式做调整
         */
        Class clazz = t.getClass();
        try {
            Method med1=clazz.getMethod("getResult", new Class[0]);
            BaseResult br= (BaseResult) med1.invoke(t, new Object[0]);
            EventBus.getDefault().post(br);
            if (br.getReturnCode() == 1) {
                EventBus.getDefault().post(br);
            } else {
                EventBus.getDefault().post(new RequestFail("xxx"));
            }
            return;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new ErrorMessage(e.getMessage()));
        }

        EventBus.getDefault().post(new RequestFail("xxxx"));

    }
}
