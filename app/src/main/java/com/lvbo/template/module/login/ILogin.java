package com.lvbo.template.module.login;


/**
 * Created by lvbo on 16/9/27.
 */

public interface ILogin {
    interface view {
        String getEmailAddress();
        String getPassword();
        void success();
    }
    interface presenter{

    }
}
