package com.centaline.turman.findhouse;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centaline.turman.findhouse.base.BaseActivity;
import com.centaline.turman.findhouse.net.entity.bean.RcBlockUserBean;
import com.centaline.turman.findhouse.net.entity.result.RcBaseResult;
import com.centaline.turman.findhouse.net.entity.result.RcBlackUserListResult;
import com.centaline.turman.findhouse.net.entity.result.RcBlockUserListResult;
import com.centaline.turman.findhouse.net.entity.result.RcStatusResult;
import com.centaline.turman.findhouse.net.entity.result.RcTokenResult;
import com.centaline.turman.findhouse.net.service.ServiceManager;
import com.centaline.turman.findhouse.utils.SharedPreferencesUtil;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by diaoqf on 2016/7/18.
 */
public class TalkActivity extends BaseActivity {
    @Bind(R.id.parentPanel)
    protected LinearLayout layout;

    @Override
    protected int getLayout() {
        return R.layout.act_talk;
    }

    @Override
    protected void initView() {

        final Map<String, String> params = new HashMap<>();
        params.put("imgUrl", "http://d.lanrentuku.com/down/png/1209/duola-a-meng/duola-a-meng_28.png");
        params.put("userId","78h0970712037126");
        params.put("name","jack");
        //显示用户名、头像
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                return new UserInfo("78h0970712037126","jack", Uri.parse("http://d.lanrentuku.com/down/png/1209/duola-a-meng/duola-a-meng_28.png"));
            }
        },true);

        Button button1 = new Button(this);
        button1.setText("获取Token");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.just(params)
                        .flatMap(new Func1<Map<String, String>, Observable<RcTokenResult>>() {
                            @Override
                            public Observable<RcTokenResult> call(Map<String, String> stringStringMap) {
                                return ServiceManager.getRongCloudService().getToken(stringStringMap.get("userId"),stringStringMap.get("name"),stringStringMap.get("imgUrl"));
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcTokenResult>() {
                            @Override
                            public void call(RcTokenResult rcTokenResult) {
                                //保存token
                                SharedPreferencesUtil.saveString(AppContents.RONG_CLOUD_TOKEN,rcTokenResult.token);
                                Logger.i("获取到的Token:"+rcTokenResult.token);
                            }
                        });
            }
        });
        layout.addView(button1);

        Button button2 = new Button(this);
        button2.setText("修改用户信息");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.just(params)
                        .flatMap(new Func1<Map<String, String>, Observable<RcBaseResult>>() {
                            @Override
                            public Observable<RcBaseResult> call(Map<String, String> stringStringMap) {
                                return ServiceManager.getRongCloudService().updateUserInfo(stringStringMap.get("userId"),stringStringMap.get("name"),stringStringMap.get("imgUrl"));
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcBaseResult>() {
                            @Override
                            public void call(RcBaseResult rcBaseResult) {
                                Logger.i("修改用户信息结果:"+rcBaseResult.code);
                            }
                        });
            }
        });
        layout.addView(button2);

        Button button3 = new Button(this);
        button3.setText("获取用户状态");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.just(params.get("userId"))
                        .flatMap(new Func1<String, Observable<RcStatusResult>>() {
                            @Override
                            public Observable<RcStatusResult> call(String s) {
                                return ServiceManager.getRongCloudService().getUserStatus(s);
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcStatusResult>() {
                            @Override
                            public void call(RcStatusResult rcStatusResult) {
                                Logger.i("用户当前状态:"+rcStatusResult.status);
                            }
                        });
            }
        });
        layout.addView(button3);

        Button button4 = new Button(this);
        button4.setText("封禁用户");
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("userId","78h0970712037128");
                params.put("minute",5);

                Observable.just(params)
                        .flatMap(new Func1<Map<String, Object>, Observable<RcBaseResult>>() {
                            @Override
                            public Observable<RcBaseResult> call(Map<String, Object> stringObjectMap) {
                                return ServiceManager.getRongCloudService().blockUser(stringObjectMap.get("userId").toString(), (int)stringObjectMap.get("minute"));
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcBaseResult>() {
                            @Override
                            public void call(RcBaseResult rcBaseResult) {
                                Logger.i("用户禁言结果:"+rcBaseResult.code);
                            }
                        });
            }
        });
        layout.addView(button4);

        Button button5 = new Button(this);
        button5.setText("解禁用户");
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("userId","78h0970712037128");

                Observable.just(params)
                        .flatMap(new Func1<Map<String, Object>, Observable<RcBaseResult>>() {
                            @Override
                            public Observable<RcBaseResult> call(Map<String, Object> stringObjectMap) {
                                return ServiceManager.getRongCloudService().unblockUser(stringObjectMap.get("userId").toString());
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcBaseResult>() {
                            @Override
                            public void call(RcBaseResult rcBaseResult) {
                                Logger.i("用户解禁结果:"+rcBaseResult.code);
                            }
                        });
            }
        });
        layout.addView(button5);


        Button button6 = new Button(this);
        button6.setText("获取封禁用户");
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceManager.getRongCloudService().getBlockUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcBlockUserListResult>() {
                            @Override
                            public void call(RcBlockUserListResult rc) {
                                Logger.i(rc.code+"");
                                Logger.i("被封禁的用户:");
                                for(RcBlockUserBean user:rc.users) {
                                    Logger.i("ID:"+user.userId+",endTime:"+user.blockEndTime);
                                }
                            }
                        });
            }
        });
        layout.addView(button6);

        Button button7 = new Button(this);
        button7.setText("添加jack到黑名单");
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("userId","78h0970712037126");
                params.put("blackUserId","78h0970712037128");

                Observable.just(params)
                        .flatMap(new Func1<Map<String, Object>, Observable<RcBaseResult>>() {
                            @Override
                            public Observable<RcBaseResult> call(Map<String, Object> stringObjectMap) {
                                return ServiceManager.getRongCloudService().addUserToBlackList((String) stringObjectMap.get("userId"),(String) stringObjectMap.get("blackUserId"));
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcBaseResult>() {
                            @Override
                            public void call(RcBaseResult rcBaseResult) {
                                Logger.i("添加结果:"+rcBaseResult.code);
                            }
                        });
            }
        });
        layout.addView(button7);

        Button button8 = new Button(this);
        button8.setText("将jack移出黑名单");
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("userId","78h0970712037126");
                params.put("blackUserId","78h0970712037128");

                Observable.just(params)
                        .flatMap(new Func1<Map<String, Object>, Observable<RcBaseResult>>() {
                            @Override
                            public Observable<RcBaseResult> call(Map<String, Object> stringObjectMap) {
                                return ServiceManager.getRongCloudService().removeUserFromBlackList((String) stringObjectMap.get("userId"),(String) stringObjectMap.get("blackUserId"));
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcBaseResult>() {
                            @Override
                            public void call(RcBaseResult rcBaseResult) {
                                Logger.i("移除结果:"+rcBaseResult.code);
                            }
                        });
            }
        });
        layout.addView(button8);


        Button button9 = new Button(this);
        button9.setText("获取黑名单用户列表");
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("userId","78h0970712037126");

                Observable.just(params)
                        .flatMap(new Func1<Map<String, Object>, Observable<RcBlackUserListResult>>() {
                            @Override
                            public Observable<RcBlackUserListResult> call(Map<String, Object> stringObjectMap) {
                                return ServiceManager.getRongCloudService().getBlackUsers((String) stringObjectMap.get("userId"));
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RcBlackUserListResult>() {
                            @Override
                            public void call(RcBlackUserListResult rcBlackUserListResult) {
                                String ids = "";
                                for (String id:rcBlackUserListResult.users) {
                                    ids += id+"\n";
                                }
                                Logger.i("黑名单用户:\n%s",ids);
                            }
                        });
            }
        });
        layout.addView(button9);


        Button button10 = new Button(this);
        button10.setText("进入聊天");
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = SharedPreferencesUtil.getString(AppContents.RONG_CLOUD_TOKEN);

                //连接服务器
                if (token != null && !"".equals(token)) {
                    RongIM.connect(token, new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {

                        }

                        @Override
                        public void onSuccess(String userId) {
                            Logger.i("Connect success,userId:"+userId);
                        }
                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Logger.i("Connect failed,errorCode:"+errorCode);
                        }
                    });
                } else {
                    Observable.just("请先获取Token")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    Snackbar.make(mParentView,s,Snackbar.LENGTH_SHORT).show();
                                }
                            });
                }

                /**
                 * 启动单聊
                 * context - 应用上下文。
                 * targetUserId - 要与之聊天的用户 Id。
                 * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
                 */
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(TalkActivity.this, "78h0970712037128", "First Talk");
                }
            }
        });
        layout.addView(button10);
    }


}
