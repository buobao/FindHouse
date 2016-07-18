package com.centaline.turman.findhouse.net.service;

import com.centaline.turman.findhouse.net.entity.RongCloudTokeResult;

import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by diaoqf on 2016/7/18.
 */
public interface RongCloudService {
    @Multipart
    @POST("/user/getToken.json")
    Observable<RongCloudTokeResult> getToken(@Part("userId") String userId, @Part("name") String name, @Part("portraitUri") String portraitUri);
}
