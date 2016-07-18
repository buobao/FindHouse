package com.centaline.turman.findhouse.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.centaline.turman.findhouse.AppApplication;
import com.hwangjr.rxbus.RxBus;

import butterknife.ButterKnife;

/**
 * Created by diaoqf on 2016/7/8.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected View mParentView;
    protected AppApplication mApplication;

    /**
     * 设置activity layout
     */
    protected abstract int getLayout();

    /**
     * 创建前处理
     */
    protected void beforeCreate(){}

    /**
     * 初始化view
     */
    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        beforeCreate();
        super.onCreate(savedInstanceState);
        mParentView = getLayoutInflater().inflate(getLayout(),null);
        setContentView(mParentView);
        mApplication = (AppApplication) getApplication();

        //butter knife bind
        ButterKnife.bind(this);

        //rxbus register
        RxBus.get().register(this);

        initView();
    }

    @Override
    protected void onDestroy() {
        //butterknife unbind
        ButterKnife.unbind(this);
        //rxbus unregister
        RxBus.get().unregister(this);
        super.onDestroy();
    }
}
