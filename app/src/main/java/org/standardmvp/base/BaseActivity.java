package org.standardmvp.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Activity基类
 * Created by Seaky on 2017/9/21.
 */

public class BaseActivity extends RxAppCompatActivity {

    //生命周期监听
    private IBaseLifeCycle mListener;

    public void setOnLifeCycleListener(IBaseLifeCycle listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
    }
}
