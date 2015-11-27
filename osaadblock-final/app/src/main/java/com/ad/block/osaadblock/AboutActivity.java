package com.ad.block.osaadblock;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.ad.block.osaadblock.utils.CommonUtils;

public class AboutActivity extends BaseActivity {
    private  View gocomment;
    private  View faq;

    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gocomment = findViewById(R.id.gocomment);
        gocomment.setVisibility(View.GONE);
        faq = findViewById(R.id.faq);
        faq.setVisibility(View.GONE);
        gocomment.setOnClickListener(this);
        faq.setOnClickListener(this);
        faq.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                faq.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                showAnim();
            }
        });

        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText(CommonUtils.getVersion(mContext));
    }

    @Override
    protected String getUmPageName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected boolean dealWithStatusBar() {
        return true;
    }

    protected void adjustHeight(boolean bbar){
        View view = this.findViewById(R.id.statusbar_sp);
        if(bbar){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(AboutActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    public void showAnim(){
        Animator.AnimatorListener listener1  = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                faq.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };
        Animator.AnimatorListener listener2  = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                gocomment.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(faq,
                "alpha", 0, 1);
        objectAnimator.setTarget(faq);
        objectAnimator.addListener(listener1);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setRepeatCount(0);

        ObjectAnimator objectAnimator2 = objectAnimator.clone();
        objectAnimator2.addListener(listener2);
        objectAnimator2.setTarget(gocomment);

        objectAnimator.setStartDelay(100);
        objectAnimator2.setStartDelay(200);

        objectAnimator.start();
        objectAnimator2.start();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gocomment:
                CommonUtils.jumpMarket(mContext);
                break;
            case R.id.faq:
                FaqActivity.openActivity(mContext);

                break;
        }
    }

    public static void openActivity(Context context){
        context.startActivity(new Intent(context, AboutActivity.class));
    }
}
