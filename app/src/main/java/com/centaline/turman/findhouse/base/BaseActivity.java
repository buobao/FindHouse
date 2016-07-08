package com.centaline.turman.findhouse.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by diaoqf on 2016/7/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
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
        setContentView(getLayout());
        //butter knife bind
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
