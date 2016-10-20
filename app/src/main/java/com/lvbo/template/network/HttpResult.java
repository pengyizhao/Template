package com.lvbo.template.network;

/**
 * Created by lvbo on 16/7/20.
 */
public interface HttpResult<T> {
     void success(T t);
     void fail(String message);
}
