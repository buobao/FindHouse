package com.centaline.turman.findhouse;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import com.centaline.turman.findhouse.base.BaseActivity;
import com.centaline.turman.findhouse.utils.map.LocationUtil;

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
        LocationUtil.start(LocationUtil.LOC_ONCE, true);
        String locationMsg = LocationUtil.getResultMessage();
        if (locationMsg == null) {
            Snackbar.make(mParentView, "hhhh", Snackbar.LENGTH_SHORT).show();
        }
    }
}





















