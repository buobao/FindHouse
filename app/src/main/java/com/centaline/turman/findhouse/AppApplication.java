package com.centaline.turman.findhouse;

import com.centaline.turman.findhouse.base.BaseApplication;
import com.centaline.turman.findhouse.utils.LogUtil;

/**
 * Created by diaoqf on 2016/7/13.
 */
public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initUtils();
    }

    private void initUtils() {
        //日志初始化
        LogUtil.init();
    }
}
