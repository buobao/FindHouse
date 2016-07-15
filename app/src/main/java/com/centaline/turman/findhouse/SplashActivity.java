package com.centaline.turman.findhouse;
import android.widget.TextView;

import com.centaline.turman.findhouse.base.BaseActivity;
import com.centaline.turman.findhouse.utils.LogUtil;

import butterknife.Bind;

/**
 * Created by diaoqf on 2016/7/8.
 */
public class SplashActivity extends BaseActivity {

    @Bind(R.id.splash_mark)
    protected TextView mTextView;

    @Override
    protected int getLayout() {
        return R.layout.act_splash;
    }

    @Override
    protected void initView() {

        LogUtil.d("Hello, %s","China");
        LogUtil.i("Hello, %s","China");
        LogUtil.json("{app:'name',result:['A','B','C']}");


//        Observable.just("Hello")
//                .compose(new Observable.Transformer<String, String>() {
//                    @Override
//                    public Observable<String> call(Observable<String> stringObservable) {
//                        return stringObservable.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
//                    }
//                }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.d("Turman",s);
//            }
//        });
    }
}





















