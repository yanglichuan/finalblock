package com.ad.block.osaadblock.utils;

import android.content.Context;

public class AdBatteryUtils {
    private static final String dayabbatterynum = "dayabbatterynum";
    private static final String monthabbatterynum = "monthabbatterynum";


    public static int getDaybatteryAdNum(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayabbatterynum);
    }


    public static int getMonthbatteryAdNum(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthabbatterynum);
    }

    public static void setDaybatteryAdNum(Context context,int num){
         RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabbatterynum, num);
    }

    public static void setMonthbatteryAdNum(Context context,int num){
         RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthabbatterynum, num);
    }


    public static void resetDaybatteryNum(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabbatterynum, 0);
    }


    public static void resetAllbatteryNum(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabbatterynum, 0);
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthabbatterynum, 0);
    }








}