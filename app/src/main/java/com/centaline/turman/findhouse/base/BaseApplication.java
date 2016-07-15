package com.centaline.turman.findhouse.base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.centaline.turman.findhouse.utils.LocationUtil;
import com.centaline.turman.findhouse.utils.LogUtil;
import com.centaline.turman.findhouse.utils.SharedPreferencesUtil;

/**
 * Created by diaoqf on 2016/7/8.
 */
public class BaseApplication extends Application {
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
        SDKInitializer.initialize(this);
        //初始化定位
        LocationUtil.init(this);
    }
}
