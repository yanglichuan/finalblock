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
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.ad.block.osaadblock.event.BaseEvent;
import com.ad.block.osaadblock.event.BlockEvent;
import com.ad.block.osaadblock.utils.AdAccUtils;
import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.LauguageUtil;

public class SaveAccActivity extends BaseActivity {
    private View item1;
    private View item2;
    private TextView tv_title;
    private View speed_zhizhen;


    private TextView tv_num1;
    private TextView tv_num2;

    private TextView tv_desc;
    private TextView text_desc;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_savespeed;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title = (TextView) findViewById(R.id.tv_title);
        speed_zhizhen = findViewById(R.id.speed_zhizhen);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);

        tv_desc = (TextView) findViewById(R.id.tv_desc);
        text_desc = (TextView) findViewById(R.id.text_desc);

        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);

        setItem1Select();

        resetZhiZhen();
    }


    private void resetZhiZhen() {
        speed_zhizhen.setRotation(DEGREE_0);
    }

    private final int DEGREE_0 = -121;
    private final int DEGREE_100 = 121;
    private final float PER_DEGREE = (float) 242 / (float) 100;
    private int currentRotation = DEGREE_0;
    private final int AnimDurationFull = 1000;
    private int mDegree = 0;//100 jishu

    private void animToDeGree(int degree) {
        mDegree = degree;
        final int realRotation = (int) (PER_DEGREE * degree) + DEGREE_0;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(speed_zhizhen, "rotation", currentRotation, realRotation);
        objectAnimator.setDuration((long) ((Math.abs(realRotation - currentRotation) / 242.0f) * AnimDurationFull));
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setRepeatCount(0);
        objectAnimator.setStartDelay(500);
        objectAnimator.addListener(new AnimLL() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentRotation = realRotation;
            }
        });
        objectAnimator.start();
    }


    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        if (event instanceof BlockEvent) {
            if (item1.isSelected()) {
                setItem1Select();
            } else {
                setItem2Select();
            }
        }
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

                //
                int num1 = AdAccUtils.getDayaccAdNum(mContext);
                animToDeGree(num1);
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

    protected void adjustHeight(boolean bbar) {
        View view = this.findViewById(R.id.statusbar_sp);
        if (bbar) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(SaveAccActivity.this);
            view.setLayoutParams(params);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    public static void openActivity(Context context) {
        Intent it = new Intent();
        it.setClass(context, SaveAccActivity.class);
        context.startActivity(it);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item1:
                if (item2.isSelected()) {
                    setItem1Select();
                }
                break;

            case R.id.item2:
                if (item1.isSelected()) {
                    setItem2Select();
                }
                break;
        }
    }


    private void setItem1Select() {
        item1.setSelected(true);
        item2.setSelected(false);

        int num1 = AdAccUtils.getDayaccAdNum(mContext);
        int num2 = AdAccUtils.getMonthaccAdNum(mContext);

        if(LauguageUtil.isTrkin()){
            tv_num1.setText("%"+num1);
            tv_num2.setText("%"+num2);
        }else{
            tv_num1.setText(num1 + "%");
            tv_num2.setText(num2 + "%");
        }

        tv_title.setText(getString(R.string.dayacc));

        //
        int t = AdAccUtils.getDayaccAdNum(mContext);
        animToDeGree(t);

        if(LauguageUtil.isTrkin()){
            tv_desc.setText("%" + t);
        }else{
            tv_desc.setText(t + "%");
        }

        text_desc.setText(String.format(getString(R.string.daytipacc), t));
    }

    private void setItem2Select() {

        int num1 = AdAccUtils.getDayaccAdNum(mContext);
        int num2 = AdAccUtils.getMonthaccAdNum(mContext);

        if(LauguageUtil.isTrkin()){
            tv_num1.setText("%"+num1);
            tv_num2.setText("%"+num2);
        }else{
            tv_num1.setText(num1 + "%");
            tv_num2.setText(num2 + "%");
        }



        item1.setSelected(false);
        item2.setSelected(true);
        tv_title.setText(getString(R.string.monthacc));

        //
        int t = AdAccUtils.getMonthaccAdNum(mContext);
        animToDeGree(t);

        if(LauguageUtil.isTrkin()){
            tv_desc.setText("%"+t);
        }else{
            tv_desc.setText(t + "%");
        }
        text_desc.setText(String.format(getString(R.string.monthtipacc), t));
    }

    private boolean beDoingAnim = false;
    public void showAnim() {
        AnimLL listener = new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                beDoingAnim = true;
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                beDoingAnim = false;
            }
        };
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(item1, "translationX", -item1.getWidth(), 0);
        objectAnimator.addListener(listener);
        objectAnimator.setDuration(500);
        objectAnimator.setRepeatCount(0);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(item2, "translationX", item2.getWidth(), 0);
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
}
