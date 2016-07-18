package com.centaline.turman.findhouse.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.centaline.turman.findhouse.BuildConfig;
import com.centaline.turman.findhouse.utils.LanguageUtil;
import com.centaline.turman.findhouse.utils.map.LocationUtil;
import com.centaline.turman.findhouse.utils.LogUtil;
import com.centaline.turman.findhouse.utils.SharedPreferencesUtil;

import io.rong.imkit.RongIM;

/**
 * Created by diaoqf on 2016/7/8.
 */
public class BaseApplication extends Application {

    public String curProcessName;

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化工具类
        initUtils();
    }

    private void initUtils() {
        //初始化sp工具
        SharedPreferencesUtil.init(getApplicationContext());
        //初始化LOG
        LogUtil.init();
        //初始化百度SDK
//        SDKInitializer.initialize(this);
        //初始化定位
//        LocationUtil.init(this);
        //初始化语言
        LanguageUtil.changeAppLanguage(getResources(),LanguageUtil.CHINESE);
        //融云
        if (BuildConfig.APPLICATION_ID.equals(getCurProcessName()) || "io.rong.push".equals(getCurProcessName())) {
            RongIM.init(this);
        }
    }

    public String getCurProcessName() {
        if (curProcessName == null) {
            int pid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    curProcessName = appProcess.processName;
                    return appProcess.processName;
                }
            }
        }
        return curProcessName;
    }
}
