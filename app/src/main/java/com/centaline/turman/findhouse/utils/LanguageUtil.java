package com.centaline.turman.findhouse.utils;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import com.centaline.turman.findhouse.AppContents;

import java.util.Locale;

/**
 * Created by diaoqf on 2016/7/18.
 */
public class LanguageUtil {

    public static final String CHINESE = "zh-CN";
    public static final String ENGLISH = "en_US";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void changeAppLanguage(Resources resources, String lan) {
        Configuration config = resources.getConfiguration();
        if (lan.equals(ENGLISH)) {
            config.setLocale(Locale.ENGLISH);
        } else {
            config.setLocale(Locale.CHINESE);
        }
        resources.updateConfiguration(config,resources.getDisplayMetrics());

        //保存语言设置
        if (SharedPreferencesUtil.isInited()) {
            SharedPreferencesUtil.saveString(AppContents.LANGUAGE,lan);
        }
    }
}
