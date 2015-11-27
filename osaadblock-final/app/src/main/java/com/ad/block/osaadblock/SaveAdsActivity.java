package com.ad.block.osaadblock;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.ad.block.osaadblock.circleprogress.AnimationState;
import com.ad.block.osaadblock.circleprogress.AnimationStateChangedListener;
import com.ad.block.osaadblock.circleprogress.CircleProgressView;
import com.ad.block.osaadblock.circleprogress.TextMode;
import com.ad.block.osaadblock.event.BaseEvent;
import com.ad.block.osaadblock.event.BlockEvent;
import com.ad.block.osaadblock.utils.AdBlockUtils;
import com.ad.block.osaadblock.utils.CommonUtils;

public class SaveAdsActivity extends BaseActivity implements CircleProgressView.OnProgressChangedListener {
    private View item1;
    private View item2;
    private TextView tv_title;

    private TextView tv_num1;
    private TextView tv_num2;

    private TextView text_desc;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_saveads;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);
        text_desc = (TextView) findViewById(R.id.tv_desc);


        int num1 = AdBlockUtils.getDayBlockAdNum(mContext);
        int num2 = AdBlockUtils.getMonthBlockAdNum(mContext);
        tv_num1.setText(num1+"");
        tv_num2.setText(num2 + "");


        item1.setOnClickListener(this);
        item2.setOnClickListener(this);


        initProgress();
        setItem1Select();
    }


    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        if(event instanceof BlockEvent){
            if(item1.isSelected()){
                setItem1Select();
            }else{
                setItem2Select();
            }
        }
    }


    private void setItem1Select(){
        int num1 = AdBlockUtils.getDayBlockAdNum(mContext);
        int num2 = AdBlockUtils.getMonthBlockAdNum(mContext);
        tv_num1.setText(num1+"");
        tv_num2.setText(num2 + "");

        item1.setSelected(true);
        item2.setSelected(false);
        tv_title.setText(getString(R.string.dayblock));

        int dayDegree =  AdBlockUtils.getDayDegree(mContext);
        mCircleView.setValueAnimated(dayDegree);
        text_desc.setText(String.format(getString(R.string.daytip),dayDegree));
    }

    private void setItem2Select(){
        int num1 = AdBlockUtils.getDayBlockAdNum(mContext);
        int num2 = AdBlockUtils.getMonthBlockAdNum(mContext);
        tv_num1.setText(num1+"");
        tv_num2.setText(num2 + "");

        item1.setSelected(false);
        item2.setSelected(true);
        tv_title.setText(getString(R.string.monthblock));


        int monthDegree =  AdBlockUtils.getMonthDegree(mContext);
        mCircleView.setValueAnimated(monthDegree);
        text_desc.setText(String.format(getString(R.string.monthtip),monthDegree));
    }



    private CircleProgressView mCircleView;
    private void initProgress(){
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleView.setOnProgressChangedListener(this);
        //value setting
        mCircleView.setMaxValue(100);
        mCircleView.setValue(0);
        //mCircleView.setValueAnimated(degree);
        //show unit
        mCircleView.setUnit("%");
        mCircleView.setShowUnit(true);
        //text sizes
//        mCircleView.setTextSize(30); // text size set, auto text size off
//        mCircleView.setUnitSize(20); // if i set the text size i also have to set the unit size
//        mCircleView.setAutoTextSize(true); // enable auto text size, previous values are overwritten
        //if you want the calculated text sizes to be bigger/smaller you can do so via
       // mCircleView.setUnitScale(0.9f);
       // mCircleView.setTextScale(0.9f);
//        //custom typeface
//        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ANDROID_ROBOT.ttf");
//        mCircleView.setTextTypeface(font);
//        mCircleView.setUnitTextTypeface(font);
        //color
        //you can use a
//        mCircleView.setBarColor(
//                getResources().getColor(android.R.color.white),
//                getResources().getColor(android.R.color.white));
        mCircleView.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value

        //spinning
//        mCircleView.spin(); // stagradientrt spinning
//        mCircleView.stopSpinning(); // stops spinning. Spinner gets shorter until it disappears.
//        mCircleView.setValueAnimated(24); // stops spinning. Spinner spins until on top. Then fills to set value.
//        mCircleView.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
        //animation callbacks

        //this example shows how to show a loading text if it is in spinning mode, and the current percent value otherwise.
        mCircleView.setAnimationStateChangedListener(
                new AnimationStateChangedListener() {
                    @Override
                    public void onAnimationStateChanged(AnimationState _animationState) {
                        switch (_animationState) {
                            case IDLE:
                            case ANIMATING:
                            case START_ANIMATING_AFTER_SPINNING:
                                mCircleView.setTextMode(TextMode.PERCENT); // show percent if not spinning
                                mCircleView.setShowUnit(true);
                                break;
                            case SPINNING:
                                mCircleView.setTextMode(TextMode.TEXT); // show text while spinning
                                mCircleView.setShowUnit(false);
                            case END_SPINNING:
                                break;
                            case END_SPINNING_START_ANIMATING:
                                break;

                        }
                    }
                }
        );
    }



    @Override
    protected void onResume() {
        super.onResume();
        item1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                item1.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                showAnim();
            }
        });
    }
    @Override
    protected String getUmPageName() {
        return this.getClass().getSimpleName();
    }


    @Override
    protected boolean dealWithStatusBar() {
        return true;
    }

    protected void adjustHeight(boolean bRealDeal){
        View view = this.findViewById(R.id.statusbar_sp);
        if(bRealDeal){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(SaveAdsActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }


    public static void openActivity(Context context){
        Intent it  = new Intent();
        it.setClass(context, SaveAdsActivity.class);
        context.startActivity(it);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item1:
                if(item2.isSelected()){
                    setItem1Select();
                }
                break;

            case R.id.item2:
                if(item1.isSelected()){
                   setItem2Select();
                }
                break;
        }
    }


    private  boolean  beDoingAnim = false;
    public void showAnim(){
        Animator.AnimatorListener listener  = new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                beDoingAnim  = true;
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                beDoingAnim = false;
            }
        };
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(item1,"translationX", -item1.getWidth(),0);
        objectAnimator.addListener(listener);
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(0);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(item2,"translationX", item2.getWidth(),0);
        objectAnimator2.addListener(listener);
        objectAnimator2.setDuration(500);
        objectAnimator2.setRepeatCount(0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(objectAnimator, objectAnimator2);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.setStartDelay(200);
        set.start();
    }

    @Override
    public void onProgressChanged(float value) {

    }
}

