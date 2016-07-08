package com.centaline.turman.findhouse.base;

import android.app.Application;
import android.content.SharedPreferences;

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
        SharedPreferencesUtil.init(getApplicationContext());
    }
}
