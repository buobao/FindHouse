package com.centaline.turman.findhouse.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.orhanobut.logger.Logger;


/**
 * Created by diaoqf on 2016/7/21.
 */
public class ScaleAnimationImageView extends ImageView {

    private float mWidth;
    private float mHeight;
    private ValueAnimator animator;

    public ScaleAnimationImageView(Context context) {
        super(context);
        init();
    }

    public ScaleAnimationImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);

        animator = ValueAnimator.ofFloat(1.0f,1.2f);
        animator.addUpdateListener(new MyScaleAnimatorListener(getImageMatrix()));
        animator.setDuration(10000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setStartDelay(500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mWidth = getWidth();
        mHeight = getHeight();
        super.onDraw(canvas);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
//        bm = Bitmap.createScaledBitmap(bm,(int)mWidth,(int)mHeight,true);
        super.setImageBitmap(bm);
    }

    private class MyScaleAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

        private Matrix mPrimaryMatrix;
        public MyScaleAnimatorListener ( Matrix matrix ) {
            mPrimaryMatrix = matrix;
        }

        @Override
        public void onAnimationUpdate ( ValueAnimator animation ) {
            float scale = ( Float ) animation.getAnimatedValue ();
            Matrix matrix = new Matrix ( mPrimaryMatrix );
            matrix.postScale ( scale, scale, mWidth / 2, mHeight / 2 );
            setImageMatrix ( matrix );
        }
    }
}