package com.centaline.turman.findhouse.net.service;

import com.centaline.turman.findhouse.net.NetWorkManager;

/**
 * Created by diaoqf on 2016/7/15.
 */
public class ServiceManager {
    //add service here
    private static CommonService commonService;
    private static RongCloudService rongCloudService;


    private ServiceManager(){}

    public static CommonService getCommonService(){
        if (commonService == null) {
            commonService = NetWorkManager.getCommonClient().create(CommonService.class);
        }
        return commonService;
    }

    public static RongCloudService getRongCloudService(){
        if (rongCloudService == null) {
            rongCloudService = NetWorkManager.getRongCloudClient().create(RongCloudService.class);
        }
        return rongCloudService;
    }

}
