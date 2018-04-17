package org.standardmvp.utils;

import android.util.Log;

/**
 * 日志打印工具类
 * Created by Seaky on 2017/9/22.
 */

public class LogUtils {

    private static final boolean DEBUG = true;

    public static void e(String tag , String content) {
        if(!DEBUG) {
            return;
        }
        Log.e(tag,content);
    }
}
