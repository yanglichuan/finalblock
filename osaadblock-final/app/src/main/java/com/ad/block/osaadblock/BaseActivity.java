package com.ad.block.osaadblock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ad.block.osaadblock.event.BaseEvent;
import com.ad.block.osaadblock.event.CloseEvent;
import com.ad.block.osaadblock.utils.CommonUtils;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected Context mContext;
    protected View goback;

    private static final int VIBRATE_DURATION = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        EventBus.getDefault().register(this);

        int layoutId = getLayoutId();
        setContentView(layoutId);


        goback = findViewById(R.id.goback);
        if(goback != null){
            goback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        boolean bStatusBar = dealWithStatusBar();
        adjustStatusBar(bStatusBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getUmPageName());
        MobclickAgent.onResume(this);
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }



    protected abstract String getUmPageName();



    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getUmPageName());
        MobclickAgent.onPause(this);
    }


    protected abstract int getLayoutId();


    protected boolean dealWithStatusBar(){
        return false;
    }

    private void adjustStatusBar(boolean bbar){
        boolean bReadDeal = false;
        if(bbar){
            bReadDeal = CommonUtils.dealStatusBar((Activity) mContext);
        }
        adjustHeight(bReadDeal);
    }

    protected void adjustHeight(boolean bRealDeal){

    }

    public static void openActivity(Context context){

    }

    private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }

    public void onEventMainThread(BaseEvent event) {
        if(event instanceof CloseEvent){
            this.finish();
        }
    }
}
