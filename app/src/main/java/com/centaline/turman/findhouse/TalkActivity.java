package com.centaline.turman.findhouse;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.widget.TextView;
import android.widget.Toast;

import com.centaline.turman.findhouse.base.BaseActivity;
import com.centaline.turman.findhouse.net.NetWorkManager;
import com.centaline.turman.findhouse.net.entity.RongCloudTokeResult;
import com.centaline.turman.findhouse.net.service.RongCloudService;
import com.centaline.turman.findhouse.net.service.ServiceManager;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by diaoqf on 2016/7/18.
 */
public class TalkActivity extends BaseActivity {
    @Bind(R.id.tv_test)
    protected TextView mTextView;

    @Override
    protected int getLayout() {
        return R.layout.act_talk;
    }

    @Override
    protected void initView() {

        Map<String, String> params = new HashMap<>();
        params.put("imgUrl", "http://d.lanrentuku.com/down/png/1209/duola-a-meng/duola-a-meng_28.png");
        params.put("userId","78h0970712037128");
        params.put("name","buobao");

        Observable.just(params)
                .flatMap(new Func1<Map<String, String>, Observable<RongCloudTokeResult>>() {
                    @Override
                    public Observable<RongCloudTokeResult> call(Map<String, String> stringStringMap) {
                        return ServiceManager.getRongCloudService().getToken(stringStringMap.get("userId"),stringStringMap.get("name"),stringStringMap.get("imgUrl"));
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RongCloudTokeResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(RongCloudTokeResult o) {
                        Snackbar.make(mParentView,o.token,Snackbar.LENGTH_SHORT).show();
                    }
                });

    }
}
