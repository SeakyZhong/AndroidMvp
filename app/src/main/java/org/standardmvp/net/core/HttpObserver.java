package org.standardmvp.net.core;

import android.text.TextUtils;


import org.standardmvp.net.exception.ApiException;
import org.standardmvp.net.exception.ExceptionEngine;
import org.standardmvp.net.function.RxActionFunction;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class HttpObserver<T> implements Observer<T> {

    //请求标识
    private String mTag;

    protected HttpObserver(String tag) {
        mTag = tag;
    }

    @Override
    public void onError(@NonNull Throwable e) {
        RxActionFunction.getInstance().remove(mTag);
        if(e instanceof ApiException) {
            onFailure((ApiException) e);
        } else {
            onFailure(new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR));
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        if(!TextUtils.isEmpty(mTag)) {
            RxActionFunction.getInstance().remove(mTag);
        }
        onSuccess(t);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if(!TextUtils.isEmpty(mTag)) {
            RxActionFunction.getInstance().add(mTag,d);
        }
        onStart();
    }

    @Override
    public void onComplete() {}

    /**
     * 业务方只需重写以下三个方法
     */
    protected void onStart(){}
    protected abstract void onFailure(ApiException e);
    protected abstract void onSuccess(T t);
}
