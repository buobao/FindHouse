package com.centaline.turman.findhouse.utils;


import com.centaline.turman.findhouse.BuildConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Logger debug环境输出日志
 * Created by diaoqf on 2016/7/13.
 */
public class LogUtil {

    private static boolean isInited = false;

    public static void init(){
        if (!isInited) {
            Logger.init(BuildConfig.LOG_TAG)
                    .setMethodCount(1)
                    .setLogLevel(BuildConfig.DEBUG? LogLevel.FULL : LogLevel.NONE)
                    .setMethodOffset(3);
            isInited = true;
        }
    }

    public static void d(String message, Object... args){
        if (BuildConfig.DEBUG) {
            Logger.d(message, args);
        }
    }

    public static void e(String message, Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.e(message, args);
        }
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.e(throwable, message, args);
        }
    }

    public static void i(String message, Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.i(message, args);
        }
    }

    public static void v(String message, Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.v(message, args);
        }
    }

    public static void w(String message, Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.w(message, args);
        }
    }

    public static void wtf(String message, Object... args) {
        if (BuildConfig.DEBUG) {
            Logger.wtf(message, args);
        }
    }

    public static void json(String json) {
        if (BuildConfig.DEBUG) {
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if (BuildConfig.DEBUG) {
            Logger.xml(xml);
        }
    }

}
