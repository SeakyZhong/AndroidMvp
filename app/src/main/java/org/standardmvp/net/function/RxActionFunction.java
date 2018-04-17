package org.standardmvp.net.function;

import android.support.v4.util.ArrayMap;

import io.reactivex.disposables.Disposable;

/**
 * RxJava订阅管理类
 * Created by Seaky on 2017/9/22.
 */

public class RxActionFunction {

    private static volatile RxActionFunction mInstance;
    //订阅集合
    private volatile ArrayMap<Object, Disposable> mMaps;

    public static RxActionFunction getInstance() {
        if (mInstance == null) {
            synchronized (RxActionFunction.class) {
                if (mInstance == null) {
                    mInstance = new RxActionFunction();
                }
            }
        }
        return mInstance;
    }

    private RxActionFunction() {
        mMaps = new ArrayMap<>();
    }

    //添加订阅
    public void add(Object tag, Disposable d) {
        mMaps.put(tag, d);
    }

    //移除订阅
    public void remove(Object tag) {
        if (!mMaps.isEmpty()) {
            mMaps.remove(tag);
        }
    }

    //解除订阅 取消请求 用于手动取消场景
    public boolean cancel(Object tag) {
        if (mMaps.isEmpty()) {
            return false;
        }
        if (mMaps.get(tag) == null) {
            return false;
        }
        if (!mMaps.get(tag).isDisposed()) {
            mMaps.get(tag).dispose();
            mMaps.remove(tag);
            return true;
        }
        return false;
    }
}
