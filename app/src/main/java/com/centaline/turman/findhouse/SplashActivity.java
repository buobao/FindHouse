package com.centaline.turman.findhouse;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.centaline.turman.findhouse.base.BaseActivity;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.entity.SubscriberEvent;
import com.hwangjr.rxbus.thread.EventThread;

import butterknife.Bind;

/**
 * Created by diaoqf on 2016/7/8.
 */
public class SplashActivity extends BaseActivity {

    @Bind(R.id.splash_mark)
    protected TextView mTextView;
    @Bind(R.id.splash_logo)
    protected ImageView imageView;

    @Override
    protected int getLayout() {
        return R.layout.act_splash;
    }

    @Override
    protected void initView() {
//        LocationUtil.start(LocationUtil.LOC_ONCE, true);
//        String locationMsg = LocationUtil.getResultMessage();
//        if (locationMsg == null) {
//            Snackbar.make(mParentView, "hhhh", Snackbar.LENGTH_SHORT).show();
//        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                RxBus.get().post(AppContents.LANGUAGE_MESSAGE);
                Intent intent = new Intent(SplashActivity.this, TalkActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe(thread = EventThread.IMMEDIATE,tags = {@Tag(AppContents.LANGUAGE_MESSAGE)})
    public void doMessge(String msg) {
        Snackbar.make(mParentView,msg,Snackbar.LENGTH_SHORT).show();
    }
}





















