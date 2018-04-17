package org.standardmvp.base;

import android.app.Application;

/**
 * 程序入口
 * Created by Seaky on 2017/9/26.
 */

public class BaseApplication extends Application {

    private volatile static BaseApplication mInstance;

    public static BaseApplication getInstance() {
        if(null == mInstance) {
            synchronized (BaseApplication.class) {
                mInstance = new BaseApplication();
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
