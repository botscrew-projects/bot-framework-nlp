package com.botscrew.nlpclient.interceptor;

public interface NlpInterceptor<E extends NlpAction> {
    void onAction(E e);
}
