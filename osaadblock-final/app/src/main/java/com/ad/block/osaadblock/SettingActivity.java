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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.ad.block.osaadblock.event.CloseEvent;
import com.ad.block.osaadblock.service.BlockService;
import com.ad.block.osaadblock.togglebutton.ToggleButton;
import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.RecordFileUtils;
import com.ad.block.osaadblock.utils.UMengUtil;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

public class SettingActivity extends BaseActivity {
    private View auto_setup;
    private View vision_update;
    private View about;
    private View quit;

    private ToggleButton toggleButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加统计
        UMengUtil.um_SettingSurface(this.getApplicationContext());

        auto_setup = findViewById(R.id.auto_setup);
        vision_update = findViewById(R.id.verson_update);
        about = findViewById(R.id.about);
        quit = findViewById(R.id.quit);

        toggleButton = (ToggleButton) findViewById(R.id.togglebt);
        toggleButton.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if(on){
                    RecordFileUtils.getInstance(mContext.getApplicationContext())
                            .setBooleanData(Constants.AUTOSTARTUP, true);
                }else{
                    RecordFileUtils.getInstance(mContext.getApplicationContext())
                            .setBooleanData(Constants.AUTOSTARTUP,false);
                }
            }
        });
        boolean bauto = RecordFileUtils.getInstance(
                mContext.getApplicationContext()).getBooleanData(Constants.AUTOSTARTUP,false);
        if(bauto){
            toggleButton.toggleOn();
        }else{
            toggleButton.toggleOff();
        }

        auto_setup.setOnClickListener(this);
        vision_update.setOnClickListener(this);
        about.setOnClickListener(this);
        quit.setOnClickListener(this);

        quit.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                quit.getViewTreeObserver().removeGlobalOnLayoutListener(this);

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

    protected void adjustHeight(boolean bbar){
        View view = this.findViewById(R.id.statusbar_sp);
        if(bbar){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(SettingActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    public void showAnim(){
        AnimLL listener  = new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                auto_setup.setVisibility(View.VISIBLE);
                vision_update.setVisibility(View.VISIBLE);
                about.setVisibility(View.VISIBLE);
                quit.setVisibility(View.VISIBLE);
            }
        };
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(auto_setup,
                "alpha", 0,1);
        objectAnimator.setTarget(auto_setup);
        objectAnimator.addListener(listener);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setRepeatCount(0);
        ObjectAnimator objectAnimator2 = objectAnimator.clone();
        objectAnimator2.setTarget(vision_update);

        ObjectAnimator objectAnimator3 = objectAnimator.clone();
        objectAnimator3.setTarget(about);

        ObjectAnimator objectAnimator4 = objectAnimator.clone();
        objectAnimator4.setTarget(quit);

        objectAnimator.setStartDelay(100);
        objectAnimator2.setStartDelay(100);
        objectAnimator3.setStartDelay(100);
        objectAnimator4.setStartDelay(100);

        objectAnimator.start();
        objectAnimator2.start();
        objectAnimator3.start();
        objectAnimator4.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.verson_update:
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(mContext, getString(R.string.thisisnew), Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(mContext, getString(R.string.nonet), Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(mContext,  getString(R.string.timeout), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
                UmengUpdateAgent.update(mContext);

                break;
            case R.id.quit:
                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setCustomImage(R.mipmap.ic_launcher)
                        .setTitleText(getString(R.string.quit_tip))
                        .setCancelText(getString(R.string.Cancel))
                        .setConfirmText(getString(R.string.Yes))
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                //添加统计
                                UMengUtil.um_CloseBtClick(mContext.getApplicationContext());

                                mContext.stopService(new Intent(mContext, BlockService.class));
                                //android.os.Process.killProcess(android.os.Process.myPid());
                                //System.exit(0);
                                EventBus.getDefault().post(new CloseEvent());
                            }
                        })
                        .show();
                break;
            case R.id.auto_setup:
                if(toggleButton.isToggle()){
                    toggleButton.setToggleOff(true);
                    RecordFileUtils.getInstance(mContext.getApplicationContext())
                            .setBooleanData(Constants.AUTOSTARTUP, false);
                }else{
                    toggleButton.setToggleOn(true);
                    RecordFileUtils.getInstance(mContext.getApplicationContext())
                            .setBooleanData(Constants.AUTOSTARTUP,true);
                }

                break;
            case R.id.about:
                AboutActivity.openActivity(mContext);
                break;
        }
    }

    public static void openActivity(Context context){
        context.startActivity(new Intent(context,SettingActivity.class));
    }
}
