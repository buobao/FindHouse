package com.centaline.turman.findhouse;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.centaline.turman.findhouse.base.BaseActivity;
import com.centaline.turman.findhouse.widgets.loopviewpager.LayoutAdapter;
import com.centaline.turman.findhouse.widgets.loopviewpager.LoopRecyclerViewPager;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;


/**
 * Created by diaoqf on 2016/7/20.
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.top_image)
    protected ImageView top_image;
    @Bind(R.id.viewpager)
    protected LoopRecyclerViewPager viewpager;

    private  Bitmap bitmap;

    @Override
    protected int getLayout() {
        return R.layout.act_main;
    }

    @Override
    protected void initView() {
        try {
            InputStream open = getResources().getAssets().open("ow1.jpg");
            bitmap = BitmapFactory.decodeStream(open);
            top_image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        viewpager.setTriggerOffset(0.15f);
        viewpager.setFlingFactor(0.25f);
        viewpager.setLayoutManager(layout);
        viewpager.setAdapter(new LayoutAdapter(this, viewpager));
        viewpager.setHasFixedSize(true);
        viewpager.setLongClickable(true);

        viewpager.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int childCount = viewpager.getChildCount();
                int width = viewpager.getChildAt(0).getWidth();
                int padding = (viewpager.getWidth() - width) / 2;

                for (int j = 0; j < childCount; j++) {
                    View v = recyclerView.getChildAt(j);
                    //往左 从 padding 到 -(v.getWidth()-padding) 的过程中，由大到小
                    float rate = 0;
                    if (v.getLeft() <= padding) {
                        if (v.getLeft() >= padding - v.getWidth()) {
                            rate = (padding - v.getLeft()) * 1f / v.getWidth();
                        } else {
                            rate = 1;
                        }
                        v.setScaleY(1 - rate * 0.1f);
                    } else {
                        //往右 从 padding 到 recyclerView.getWidth()-padding 的过程中，由大到小
                        if (v.getLeft() <= recyclerView.getWidth() - padding) {
                            rate = (recyclerView.getWidth() - padding - v.getLeft()) * 1f / v.getWidth();
                        }
                        v.setScaleY(0.9f + rate * 0.1f);
                    }
                }
            }
        });

        viewpager.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (viewpager.getChildCount() < 3) {
                    if (viewpager.getChildAt(1) != null) {
                        View v1 = viewpager.getChildAt(1);
                        v1.setScaleY(0.9f);
                    }
                } else {
                    if (viewpager.getChildAt(0) != null) {
                        View v0 = viewpager.getChildAt(0);
                        v0.setScaleY(0.9f);
                    }
                    if (viewpager.getChildAt(2) != null) {
                        View v2 = viewpager.getChildAt(2);
                        v2.setScaleY(0.9f);
                    }
                }

            }
        });
    }
}


























