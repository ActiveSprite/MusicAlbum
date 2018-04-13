package com.example.meituan.musicalbum;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by guhugang on 2018/4/13.
 */

public class MusicAlbumView extends ViewGroup {
    private ImageView rotateView;
    private ImageView playView;
    private Drawable rotateIcon;
    private Drawable playIcon;
    private Drawable pauseIcon;
    private boolean isPlaying=true;
    public MusicAlbumView(Context context) {
        super(context);
    }

    public MusicAlbumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context,attrs);
        initChildView(context);
    }

    public MusicAlbumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context,attrs);
        initChildView(context);
    }

    public MusicAlbumView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttributes(context,attrs);
        initChildView(context);
    }

    private void getAttributes(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MusicAlbumView);
        rotateIcon=typedArray.getDrawable(R.styleable.MusicAlbumView_rotateViewIcon);
        playIcon=typedArray.getDrawable(R.styleable.MusicAlbumView_playIcon);
        pauseIcon=typedArray.getDrawable(R.styleable.MusicAlbumView_pauseIcon);
        typedArray.recycle();
    }
    public void initChildView(final Context context){
        int width=dp2px(35);
        int height=dp2px(35);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(width
                , height);
        rotateView=new ImageView(context);
        rotateView.setImageDrawable(rotateIcon);
        rotateView.setScaleType(ImageView.ScaleType.FIT_XY);
        rotateMusic();
        addView(rotateView,layoutParams);
        playView=new ImageView(context);
        playView.setImageDrawable(playIcon);
        playView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        playView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    playView.setImageDrawable(pauseIcon);
                    isPlaying=false;
                }
                else {
                    playView.setImageDrawable(playIcon);
                    isPlaying=true;
                }
            }
        });
        addView(playView,layoutParams);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int count = getChildCount();
        for(int i=0; i<count; i++){
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed) {
            layoutRotateView();
            layoutPlayView();
        }
    }
    private void layoutRotateView(){
        int w=rotateView.getMeasuredWidth();
        int h=rotateView.getMeasuredHeight();
        int l=getWidth()-w-dp2px(16);
        int t=dp2px(16);
        int r=l+w;
        int b=t+h;
        rotateView.layout(l,t,r,b);
    }
    private void layoutPlayView(){
        int w=playView.getMeasuredWidth();
        int h=playView.getMeasuredHeight();
        int l=dp2px(16);
        int t=getHeight()-dp2px(16)-h;
        int r=l+w;
        int b=t+h;
        playView.layout(l,t,r,b);
    }
    private int dp2px(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , value, getResources().getDisplayMetrics());
    }

    private void rotateMusic(){
//        ObjectAnimator animator =  ObjectAnimator.ofFloat(rotateView
//                , "rotation", 0F, 360f) ;
//        animator.setDuration(3000);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.start();
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        rotateView.startAnimation(animation);
    }
}
