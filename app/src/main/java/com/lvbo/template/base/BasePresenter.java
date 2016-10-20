package com.lvbo.template.base;

import de.greenrobot.event.EventBus;

/**
 * Created by lvbo on 16/10/20.
 */

public class BasePresenter {

    public void register(){
        EventBus.getDefault().register(this);
    }


    public void unRegister(){
        EventBus.getDefault().unregister(this);
    }

}
