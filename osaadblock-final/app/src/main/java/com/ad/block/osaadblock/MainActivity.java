package com.ad.block.osaadblock;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.ad.block.osaadblock.event.BaseEvent;
import com.ad.block.osaadblock.event.BlockEvent;
import com.ad.block.osaadblock.service.BlockService;
import com.ad.block.osaadblock.utils.AdBlockUtils;
import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.TranficUtil;
import com.ad.block.osaadblock.utils.UMengUtil;
import com.andexert.library.RippleView;

public class MainActivity extends BaseActivity implements RippleView.OnRippleCompleteListener {
    private View setting;

    private RippleView viewItem1_ft;
    private RippleView viewItem2_ft;
    private RippleView viewItem3_ft;
    private RippleView viewItem4_ft;

    private View anim_wholeview;
    private View top_color;
    private View ratateLine;
    private View opened_bg;

    private TextView tip_top;
    private TextView tip_bottom;

    private TextView scan_;

    private final int STATE_CLOSED = 1;
    private final int STATE_OPENING = 2;
    private final int STATE_OPENED = 3;
    private final int STATE_CLOSEING = 4;

    //初始化状态值
    private int iBlockState = STATE_CLOSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //上报数据
        UMengUtil.um_dayStartCount(mContext.getApplicationContext());

        scan_ = (TextView) findViewById(R.id.scan_);
        tip_top = (TextView) findViewById(R.id.tip_top);
        tip_bottom = (TextView) findViewById(R.id.tip_bottom);
        tip_bottom.setVisibility(View.GONE);
        tip_top.setText(getString(R.string.open));

        iBlockState = STATE_CLOSED;
        ratateLine = findViewById(R.id.ratateLine);
        top_color = findViewById(R.id.top_color);
        opened_bg = findViewById(R.id.opened_bg);
        opened_bg.setVisibility(View.GONE);

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(this);

        anim_wholeview = findViewById(R.id.anim_view);
        anim_wholeview.setOnClickListener(this);

        viewItem1_ft = (RippleView) findViewById(R.id.item1_ft);
        viewItem2_ft = (RippleView) findViewById(R.id.item2_ft);
        viewItem3_ft = (RippleView) findViewById(R.id.item3_ft);
        viewItem4_ft = (RippleView) findViewById(R.id.item4_ft);
        viewItem1_ft.setOnClickListener(this);
        viewItem2_ft.setOnClickListener(this);
        viewItem3_ft.setOnClickListener(this);
        viewItem4_ft.setOnClickListener(this);

        if(CommonUtils.isServiceRunning(mContext,BlockService.class.getCanonicalName())){
            noAnimOpen();
        }else{
            //不做任何事情
        }
    }
    @Override
    protected String getUmPageName() {
        return this.getClass().getSimpleName();
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean dealWithStatusBar() {
        return true;
    }

    protected void adjustHeight(boolean bbar){
        View view = this.findViewById(R.id.statusbar_sp);
        if(bbar){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(MainActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }


    public static void openActivity(Context context){
        Intent it  = new Intent();
        it.setClass(context, MainActivity.class);
        context.startActivity(it);
    }


    private void setTip(){
        int num2 = AdBlockUtils.getMonthBlockAdNum(mContext);
        if(String.valueOf(num2).length() < 8){
            tip_top.setText(getString(R.string.blockAD));
            tip_bottom.setVisibility(View.VISIBLE);
            tip_bottom.setText("00000000".substring(String.valueOf(num2).length())+num2);
        }else{
            tip_top.setText(getString(R.string.blockAD));
            tip_bottom.setVisibility(View.VISIBLE);
            tip_bottom.setText(""+num2);
        }
    }

    private void setStateOpened(){
        //tip.setText(getString(R.string.close));
        //显示本月拦截的数目
        setTip();
        iBlockState = STATE_OPENED;
    }

    private void setStateClosed(){
        tip_top.setText(getString(R.string.open));
        tip_bottom.setVisibility(View.GONE);
        tip_bottom.setText("");
        iBlockState = STATE_CLOSED;
    }

    private void setStateOpening(){
        tip_top.setText(getString(R.string.opening));
        tip_bottom.setVisibility(View.GONE);
        tip_bottom.setText("");
        iBlockState = STATE_OPENING;
    }

    private void setStateCloseing(){
        tip_top.setText(getString(R.string.closeing));
        tip_bottom.setVisibility(View.GONE);
        tip_bottom.setText("");
        iBlockState = STATE_CLOSEING;
    }

    @Deprecated
    private void noAnimClose(){
        setStateClosed();
        //拦截服务已经关闭
        stopService(new Intent(mContext, BlockService.class));
        opened_bg.setVisibility(View.GONE);
        ratateLine.setVisibility(View.VISIBLE);
        top_color.setBackgroundColor(getResources().getColor(R.color.background_from));
    }

    private void noAnimOpen(){
        setStateOpened();
        startService(new Intent(mContext, BlockService.class));
        opened_bg.setVisibility(View.VISIBLE);
        ratateLine.setVisibility(View.GONE);
        top_color.setBackgroundColor(getResources().getColor(R.color.background_to));
    }

    private void animClose(){
        animFadeScaleIn(opened_bg, true, new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                opened_bg.setVisibility(View.VISIBLE);
                setStateCloseing();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animRotate(ratateLine, true);
                animBackGroundColor(top_color, true, new AnimLL() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setStateClosed();

                        //拦截服务已经关闭
                        stopService(new Intent(mContext, BlockService.class));
                    }
                });
            }
        });
        animFadeOut(ratateLine,true,new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                ratateLine.setVisibility(View.VISIBLE);
            }
        });
    }


    private void animOpen(){

        //添加统计
        UMengUtil.um_startBtClick(this.getApplicationContext());

        //遍历应用信息
        TranficUtil.getAppTrafficList((Activity) mContext,scan_);

        animRotate(ratateLine, false);
        animBackGroundColor(top_color, false,new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                setStateOpening();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animFadeScaleIn(opened_bg, false, new AnimLL() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        opened_bg.setVisibility(View.VISIBLE);
                        setStateOpened();

                        startService(new Intent(mContext,BlockService.class));
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
                animFadeOut(ratateLine,false,new AnimLL() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        ratateLine.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ratateLine.setVisibility(View.GONE);
                    }
                });
            }
        });
    }


    /**
     * zong'dong'h
     * @param view
     * @param bReverse
     */
    private void animRotate(View view, boolean bReverse){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"rotation",0,180);
        objectAnimator.setDuration(800);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        if(bReverse){
            objectAnimator.reverse();
        }else{
            objectAnimator.start();
        }
    }


    private void animBackGroundColor(View view,boolean bReverse ,Animator.AnimatorListener listener){
        //
        ObjectAnimator bg = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.opening_color);
        bg.setEvaluator(new ArgbEvaluator());
        bg.setTarget(view);
        bg.addListener(listener);
        if(bReverse){
            bg.reverse();
        }else{
            bg.start();
        }


    }

    private void animFadeScaleIn(final View view,boolean bReverse, Animator.AnimatorListener listener){
        ObjectAnimator objectAnimator = null;
        ObjectAnimator objectAnimator2 = null;
        ObjectAnimator objectAnimator3 = null;
        if(bReverse){
            objectAnimator = ObjectAnimator.ofFloat(view,"alpha",1,0);
            objectAnimator2 = ObjectAnimator.ofFloat(view,"scaleX",1,0);
            objectAnimator3 = ObjectAnimator.ofFloat(view,"scaleY",1,0);
        }else{
            objectAnimator = ObjectAnimator.ofFloat(view,"alpha",0,1);
            objectAnimator2 = ObjectAnimator.ofFloat(view,"scaleX",0,1);
            objectAnimator3 = ObjectAnimator.ofFloat(view,"scaleY",0,1);
        }
        objectAnimator.setDuration(400);
        objectAnimator.setRepeatCount(0);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator2.setDuration(400);
        objectAnimator2.setRepeatCount(0);
        objectAnimator2.setInterpolator(new DecelerateInterpolator());
        objectAnimator3.setDuration(400);
        objectAnimator3.setRepeatCount(0);
        objectAnimator3.setInterpolator(new DecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.addListener(listener);
        set.playTogether(objectAnimator,objectAnimator2,objectAnimator3);
        set.start();
    }

    private void animFadeOut(final View view,boolean bReverse, Animator.AnimatorListener listener){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"alpha",1,0);
        objectAnimator.setDuration(400);
        objectAnimator.setRepeatCount(0);
        objectAnimator.addListener(listener);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        if(bReverse){
            objectAnimator.reverse();
        }else{
            objectAnimator.start();
        }
    }



    private Handler mHandle = new Handler();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anim_view:
                if(iBlockState == STATE_OPENED){
                    animClose();
                }
                if(iBlockState == STATE_CLOSED){
                    animOpen();
                }
                break;

            case R.id.setting:
                mHandle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SettingActivity.openActivity(mContext);
                    }
                }, 500);
                break;
            case R.id.item1_ft:
                //添加统计
                UMengUtil.um_SaveAds(this.getApplicationContext());

                mHandle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveAdsActivity.openActivity(mContext);
                    }
                }, 500);
                break;
            case R.id.item2_ft:
                //添加统计
                UMengUtil.um_SaveAcc(this.getApplicationContext());


                mHandle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveAccActivity.openActivity(mContext);
                    }
                }, 500);

                break;
            case R.id.item3_ft:
                //添加统计
                UMengUtil.um_SaveFlow(this.getApplicationContext());

                mHandle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveFlowActivity.openActivity(mContext);
                    }
                }, 500);

                break;
            case R.id.item4_ft:
                //添加统计
                UMengUtil.um_SaveBattery(this.getApplicationContext());

                mHandle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SaveBatteryActivity.openActivity(mContext);
                    }
                }, 500);
                break;
        }
    }

    @Override
    public void onComplete(RippleView rippleView) {
        switch (rippleView.getId()){
            case R.id.item1_ft:
                SaveAdsActivity.openActivity(mContext);
                break;
            case R.id.item2_ft:
                SaveAccActivity.openActivity(mContext);
                break;
            case R.id.item3_ft:
                SaveFlowActivity.openActivity(mContext);
                break;
            case R.id.item4_ft:
                SaveBatteryActivity.openActivity(mContext);
                break;
        }
    }


    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        if(event instanceof BlockEvent){
            //
            setTip();
        }


    }
}
