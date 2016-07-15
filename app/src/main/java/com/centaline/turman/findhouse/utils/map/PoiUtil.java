package com.centaline.turman.findhouse.utils.map;

import android.content.Context;

import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * Created by diaoqf on 2016/7/15.
 */
public class PoiUtil {

    private static PoiSearch mPoiSearch = null;

    /**
     * 初始化工具
     * @param context 上下文
     */
    public static void init(Context context){
        if (mPoiSearch == null) {
            mPoiSearch = PoiSearch.newInstance();
            mPoiSearch.destroy();
            OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
                public void onGetPoiResult(PoiResult result) {
                    //获取POI检索结果
                }

                public void onGetPoiDetailResult(PoiDetailResult result) {
                    //获取Place详情页检索结果
                }

                @Override
                public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                }
            };

            mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        }
    }

    /**
     * 是否已实例化工具
     * @return
     */
    public static boolean isInited(){
        return mPoiSearch != null;
    }
}
