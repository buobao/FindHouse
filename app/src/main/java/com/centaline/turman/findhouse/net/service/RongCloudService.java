package com.centaline.turman.findhouse.net.service;

import com.centaline.turman.findhouse.net.entity.result.RcBaseResult;
import com.centaline.turman.findhouse.net.entity.result.RcBlackUserListResult;
import com.centaline.turman.findhouse.net.entity.result.RcBlockUserListResult;
import com.centaline.turman.findhouse.net.entity.result.RcStatusResult;
import com.centaline.turman.findhouse.net.entity.result.RcTokenResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by diaoqf on 2016/7/18.
 */
public interface RongCloudService {

    /**
     * 获取 token
     * @param userId 用户Id
     * @param name 用户名
     * @param portraitUri 用户头像路径
     * @return
     */
    @FormUrlEncoded
    @POST("/user/getToken.json")
    Observable<RcTokenResult> getToken(@Field("userId") String userId, @Field("name") String name, @Field("portraitUri") String portraitUri);

    /**
     * 更新用户信息
     * @param userId
     * @param name
     * @param portraitUri
     * @return
     */
    @FormUrlEncoded
    @POST("/user/refresh.json")
    Observable<RcBaseResult> updateUserInfo(@Field("userId") String userId, @Field("name") String name, @Field("portraitUri") String portraitUri);

    /**
     * 获取用户在线状态
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/user/checkOnline.json")
    Observable<RcStatusResult> getUserStatus(@Field("userId") String userId);

    /**
     * 用户禁言
     * @param userId 用户ID
     * @param minute 封禁分钟数
     * @return
     */
    @FormUrlEncoded
    @POST("/user/block.json")
    Observable<RcBaseResult> blockUser(@Field("userId") String userId, @Field("minute") int minute);

    /**
     * 解禁用户
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("/user/unblock.json")
    Observable<RcBaseResult> unblockUser(@Field("userId") String userId);

    /**
     * 获取封禁用户列表
     * @return
     */
    @POST("/user/block/query.json")
    Observable<RcBlockUserListResult> getBlockUsers();

    /**
     * 添加用户到黑名单
     * @param userId  当前用Id
     * @param blackUserId 添加到黑名单用户Id
     * @return
     */
    @FormUrlEncoded
    @POST("/user/blacklist/add.json")
    Observable<RcBaseResult> addUserToBlackList(@Field("userId") String userId, @Field("blackUserId") String blackUserId);

    /**
     * 将用户移除黑名单
     * @param userId
     * @param blackUserId
     * @return
     */
    @FormUrlEncoded
    @POST("/user/blacklist/remove.json")
    Observable<RcBaseResult> removeUserFromBlackList(@Field("userId") String userId,@Field("blackUserId") String blackUserId);

    /**
     * 获取黑名单用户列表
     * @param userId 当前用户Id
     * @return
     */
    @FormUrlEncoded
    @POST("/user/blacklist/query.json")
    Observable<RcBlackUserListResult> getBlackUsers(@Field("userId") String userId);


    @FormUrlEncoded
    @POST("/message/private/publish.json")
    Observable<RcBaseResult> sendMessage(@Field("id") String id);

}




























