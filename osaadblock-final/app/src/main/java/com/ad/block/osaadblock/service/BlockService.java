package com.ad.block.osaadblock.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ad.block.osaadblock.receiver.TickTimeReceiver;
import com.ad.block.osaadblock.utils.AdBlockUtils;
import com.ad.block.osaadblock.utils.AdFlowUtils;
import com.ad.block.osaadblock.utils.NotificationUtils;
import com.ad.block.osaadblock.utils.UMengUtil;

public class BlockService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //start the service through alarm repeatly
//        Intent intent = new Intent(getApplicationContext(), MyService.class);
//        mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        mPendingIntent = PendingIntent.getService(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//        long now = System.currentTimeMillis();
//        mAlarmManager.setInexactRepeating(AlarmManager.RTC, now, 60000, mPendingIntent)

        //上报数据
        UMengUtil.um_dayRealWorkingCount(this.getApplicationContext());


        registerTickReceiver();
        super.onCreate();
    }


    private BroadcastReceiver receiver;
    private void registerTickReceiver(){
        IntentFilter filter=new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        receiver = new TickTimeReceiver();
        try {
            registerReceiver(receiver,filter);
        }catch (Exception e){
        }
    }


    public static final int ONGOING_NOTIFICATION = 200;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int tadayAdnum =  AdBlockUtils.getDayBlockAdNum(BlockService.this);
        String tip = null;
        if(tadayAdnum > 0){
            tip= "今日拦截"+tadayAdnum+"条骚扰";
        }else{
            tip="拦截服务已经开启";
        }
        Notification notification = NotificationUtils.creatNotify(BlockService.this,tip);
        startForeground(ONGOING_NOTIFICATION,notification);


        //更新一下当前的流量信息
        //流量相关的
        long t  = AdFlowUtils.getCurrentTotalRxFlow();
        //更新流量
        AdFlowUtils.updateHisTotalRxFlow(BlockService.this,t);


        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        stopForeground(true);
        if(receiver != null){
            try {
                unregisterReceiver(receiver);
                receiver = null;
            }catch (Exception e){
            }
        }
        super.onDestroy();
    }




}


