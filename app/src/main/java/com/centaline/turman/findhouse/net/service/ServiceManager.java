package com.centaline.turman.findhouse.net.service;

import com.centaline.turman.findhouse.net.NetWorkManager;

/**
 * Created by diaoqf on 2016/7/15.
 */
public class ServiceManager {
    //add service here
    private static CommonService commonService;


    private ServiceManager(){}

    public static CommonService getCommonService(){
        if (commonService == null) {
            NetWorkManager.getClient().create(CommonService.class);
        }
        return commonService;
    }

}
