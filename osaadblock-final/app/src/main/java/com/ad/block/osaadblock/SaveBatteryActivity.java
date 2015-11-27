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

import com.ad.block.osaadblock.event.BaseEvent;
import com.ad.block.osaadblock.event.BlockEvent;
import com.ad.block.osaadblock.utils.AdBatteryUtils;
import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.LauguageUtil;
import com.ad.block.osaadblock.wave.WaveView;

public class SaveBatteryActivity extends BaseActivity {


    private View item1;
    private View item2;
    private TextView tv_title;

    private TextView tv_num1;
    private TextView tv_num2;

    private TextView tv_num;
    private TextView text_desc;

    private WaveView wave_view;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_savebattery;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        tv_title = (TextView) findViewById(R.id.tv_title);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);

        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);
        tv_num = (TextView) findViewById(R.id.tv_num);
        text_desc = (TextView) findViewById(R.id.text_desc);

        wave_view = (WaveView) findViewById(R.id.wave_view);

        setItem1Select();
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

    @Override
    protected boolean dealWithStatusBar() {
        return true;
    }

    protected void adjustHeight(boolean bbar){
        View view = this.findViewById(R.id.statusbar_sp);
        if(bbar){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(SaveBatteryActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }


    private  boolean  beDoingAnim = false;
    public void showAnim(){
        AnimLL listener  = new AnimLL() {
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



    public static void openActivity(Context context){
        Intent it  = new Intent();
        it.setClass(context, SaveBatteryActivity.class);
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


    private void setItem1Select(){
        item1.setSelected(true);
        item2.setSelected(false);
        tv_title.setText(getString(R.string.daybattery));

        int num1 = AdBatteryUtils.getDaybatteryAdNum(mContext);
        int num2 = AdBatteryUtils.getMonthbatteryAdNum(mContext);

        if(LauguageUtil.isTrkin()){
            tv_num1.setText("%"+num1);
            tv_num2.setText("%"+num2);

            tv_num.setText("%"+num1);
        }else{
            tv_num1.setText(num1+"%");
            tv_num2.setText(num2+"%");

            tv_num.setText(num1+"%");
        }

        text_desc.setText(String.format(getString(R.string.daytipbattery),num1));

        wave_view.setProgress(num1);
    }

    private void setItem2Select(){
        item1.setSelected(false);
        item2.setSelected(true);
        tv_title.setText(getString(R.string.monthbattery));

        int num1 = AdBatteryUtils.getDaybatteryAdNum(mContext);
        int num2 = AdBatteryUtils.getMonthbatteryAdNum(mContext);

        if(LauguageUtil.isTrkin()){
            tv_num1.setText("%"+num1);
            tv_num2.setText("%"+num2);

            tv_num.setText("%"+num1);
        }else{
            tv_num1.setText(num1 + "%");
            tv_num2.setText(num2 + "%");

            tv_num.setText(num2+"%");
        }
        text_desc.setText(String.format(getString(R.string.monthtipbattery),num2));
        wave_view.setProgress(num2);
    }

}
