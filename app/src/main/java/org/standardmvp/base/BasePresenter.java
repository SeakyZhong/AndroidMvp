package org.standardmvp.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Presenter基类
 * Created by Seaky on 2017/9/21.
 */

public class BasePresenter<V, T> implements IBaseLifeCycle {

    private Reference<V> mViewRef;
    private Reference<T> mActivityRef;

    public BasePresenter(V view, T activity) {
        attachView(view);
        attachActivity(activity);
        setListener(activity);
    }

    private void setListener(T activity) {
        if(null != getActivity() && activity instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setOnLifeCycleListener(this);
        }
    }

    //绑定view接口
    private void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    //绑定Activity
    private void attachActivity(T activity) {
        mActivityRef = new WeakReference<T>(activity);
    }

    //销毁view引用
    private void detachView() {
        if (isViewAttached()) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    //销毁Activity引用
    private void detachActivity() {
        if (isActivityAttached()) {
            mActivityRef.clear();
            mActivityRef = null;
        }
    }

    //获取View接口
    public V getView() {
        if (mViewRef == null) {
            return null;
        }
        return mViewRef.get();
    }

    //获取Activity
    protected T getActivity() {
        if (mActivityRef == null) {
            return null;
        }
        return mActivityRef.get();
    }

    //判断是否已绑定view
    private boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    //判断是否已绑定Activity
    private boolean isActivityAttached() {
        return mActivityRef != null && mActivityRef.get() != null;
    }

    //销毁时清除所有
    @Override
    public void onDestroy() {
        detachView();
        detachActivity();
    }
}
