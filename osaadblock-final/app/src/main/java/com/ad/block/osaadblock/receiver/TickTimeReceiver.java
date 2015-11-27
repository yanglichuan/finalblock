package com.ad.block.osaadblock.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ad.block.osaadblock.event.BlockEvent;
import com.ad.block.osaadblock.service.BlockService;
import com.ad.block.osaadblock.utils.AdAccUtils;
import com.ad.block.osaadblock.utils.AdBatteryUtils;
import com.ad.block.osaadblock.utils.AdBlockUtils;
import com.ad.block.osaadblock.utils.AdFlowUtils;
import com.ad.block.osaadblock.utils.AdSaveFlowUtils;
import com.ad.block.osaadblock.utils.CalendarUtils;
import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.NotificationUtils;
import com.ad.block.osaadblock.utils.RandomUtils;

import de.greenrobot.event.EventBus;

public class TickTimeReceiver extends BroadcastReceiver {
    public int iTickCount =0;
    @Override
    public void onReceive(Context context, Intent intent) {
        //根据时间来做更新
        int daytime = AdBlockUtils.getTimeDay(context);
        int monthTime = AdBlockUtils.getTimeMonth(context);
        if(daytime == -1){
            AdBlockUtils.setTimeDay(context, CalendarUtils.getDayNum());
            AdBlockUtils.setTimeMonth(context, CalendarUtils.getMonthNum());
            //使用天数
            AdBlockUtils.resetMonthUserDays(context);


            //清空拦截
            AdBlockUtils.resetDayBlockNum(context);
            AdBlockUtils.resetAllBlockNum(context);

            //清空加速
            AdAccUtils.resetAllaccNum(context);
            AdAccUtils.resetDayaccNum(context);

            //节省流量
            AdSaveFlowUtils.resetAllsaveflowNum(context);
            AdSaveFlowUtils.resetDaysaveflowNum(context);

            //电量节省
            AdBatteryUtils.resetAllbatteryNum(context);
            AdBatteryUtils.resetDaybatteryNum(context);
        }else{
            if(daytime != CalendarUtils.getDayNum()){
                //使用天数加一
                AdBlockUtils.addMonthUsedays(context);

                //更新时间
                AdBlockUtils.setTimeDay(context, CalendarUtils.getDayNum());

                //清空拦截
                AdBlockUtils.resetDayBlockNum(context);

                ////清空加速
                AdAccUtils.resetDayaccNum(context);

                //节省流量
                AdSaveFlowUtils.resetDaysaveflowNum(context);

                //电量节省
                AdBatteryUtils.resetDaybatteryNum(context);
            }

            if(monthTime != CalendarUtils.getMonthNum()){
                //清空天数记录
                AdBlockUtils.resetMonthUserDays(context);

                //更新时间
                AdBlockUtils.setTimeMonth(context, CalendarUtils.getMonthNum());

                //清空拦截
                AdBlockUtils.resetDayBlockNum(context);
                AdBlockUtils.resetAllBlockNum(context);

                ////清空加速
                AdAccUtils.resetAllaccNum(context);
                AdAccUtils.resetDayaccNum(context);

                //节省流量
                AdSaveFlowUtils.resetAllsaveflowNum(context);
                AdSaveFlowUtils.resetDaysaveflowNum(context);

                //电量节省
                AdBatteryUtils.resetAllbatteryNum(context);
                AdBatteryUtils.resetDaybatteryNum(context);
            }
        }

        //获得今天的拦截数目
       int tadayAdnum =  AdBlockUtils.getDayBlockAdNum(context);
        if(tadayAdnum > 0){
            Notification notification = NotificationUtils.creatNotify(context, "今日拦截"+tadayAdnum+"条骚扰");
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(manager != null){
                manager.notify(BlockService.ONGOING_NOTIFICATION, notification);
            }
        }else{
            if(!CommonUtils.isServiceRunning(context, BlockService.class.getCanonicalName())){
                //
                Notification notification = NotificationUtils.creatNotify(context, "拦截服务已开启");
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if(manager != null){
                    manager.notify(BlockService.ONGOING_NOTIFICATION, notification);
                }
            }
        }

        //添加一条拦截
        iTickCount ++;
        if(iTickCount == Integer.MAX_VALUE){
            iTickCount = 0;
        }
        if(iTickCount%5 == 0){
            //如果网络是开启的
            if(CommonUtils.isNetworkActived(context)){
                //流量相关的
                long t  = AdFlowUtils.getCurrentTotalRxFlow();
                long his = AdFlowUtils.getHisTotalRxFlow(context);
                //更新流量
                AdFlowUtils.updateHisTotalRxFlow(context,t);

                //根据流量对拦截数目做处理
                int num = AdBlockUtils.getDayBlockAdNum(context);
                if(his == -1){
                    //对拦截进行处理
                    num = AdBlockUtils.blockOneAd(context,1);

                    //对加速进行处理
                    AdAccUtils.setDayaccAdNum(context,2);
                    AdAccUtils.setMonthaccAdNum(context,2);

                    //对电池电量
                    AdBatteryUtils.setDaybatteryAdNum(context,3);
                    AdBatteryUtils.setMonthbatteryAdNum(context, 3);

                    //对节省流量进行处理
                    AdSaveFlowUtils.saveAdd(context,0.002f);

                }else{

                    int dayUses = AdBlockUtils.getMonthUsedays(context);
                    //对加速进行处理
                    AdAccUtils.setDayaccAdNum(context, RandomUtils.getRandom(2, 5));
                    AdAccUtils.setMonthaccAdNum(context, RandomUtils.getRandom(dayUses*3/2,dayUses*3));
                    //对电池电量
                    AdBatteryUtils.setDaybatteryAdNum(context, RandomUtils.getRandom(3, 5));
                    AdBatteryUtils.setMonthbatteryAdNum(context, RandomUtils.getRandom(dayUses * 3 / 2, dayUses * 3));



                    if(Math.abs(t - his)>10*1000){
                        //对拦截进行处理
                        num = AdBlockUtils.blockOneAd(context,8);

                        //对节省流量进行处理
                        AdSaveFlowUtils.saveAdd(context, 0.1f);
                    }else if(Math.abs(t - his)>5*1000){
                        //对拦截进行处理
                        num = AdBlockUtils.blockOneAd(context,7);

                        //对节省流量进行处理
                        AdSaveFlowUtils.saveAdd(context, 0.05f);
                    }else if(Math.abs(t - his)>3*1000){
                        //对拦截进行处理
                        num = AdBlockUtils.blockOneAd(context,6);

                        //对节省流量进行处理
                        AdSaveFlowUtils.saveAdd(context, 0.03f);
                    }else if(Math.abs(t - his)>2*1000){
                        //对拦截进行处理
                        num = AdBlockUtils.blockOneAd(context,5);

                        //对节省流量进行处理
                        AdSaveFlowUtils.saveAdd(context, 0.02f);
                    }else if(Math.abs(t - his)>1*1000){
                        //对拦截进行处理
                        num = AdBlockUtils.blockOneAd(context,4);

                        //对节省流量进行处理
                        AdSaveFlowUtils.saveAdd(context, 0.01f);
                    }else if(Math.abs(t - his)>500){
                        //对拦截进行处理
                        num = AdBlockUtils.blockOneAd(context,3);

                        //对节省流量进行处理
                        AdSaveFlowUtils.saveAdd(context, 0.005f);
                    }else if(Math.abs(t - his)>300){
                        //对拦截进行处理
                        num = AdBlockUtils.blockOneAd(context,2);

                        //对节省流量进行处理
                        AdSaveFlowUtils.saveAdd(context, 0.003f);
                    }else if(Math.abs(t - his)>100){
                        //对拦截进行处理
                        num = AdBlockUtils.blockOneAd(context,1);

                        //对节省流量进行处理
                        AdSaveFlowUtils.saveAdd(context, 0.002f);
                    }
                }
                Notification notification = NotificationUtils.creatNotify(context, "今日拦截" + num + "条骚扰");
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if(manager != null){
                    manager.notify(BlockService.ONGOING_NOTIFICATION, notification);
                }
            }
        }
        //通知更新ui
        //通知各个activity
        EventBus.getDefault().post(new BlockEvent());


//        if(!CommonUtils.isServiceRunning(context, BlockService.class.getCanonicalName())){
//            //服务没有运行 开启服务
//           //context.startService(new Intent(context, BlockService.class));
//        }else{
//            //服务为开启状态 更新拦截的信息
//
//        }
    }  //如果无网络连接activeInfo为null

}


