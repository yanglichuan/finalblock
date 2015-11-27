package com.ad.block.osaadblock.utils;

import android.content.Context;

public class AdAccUtils {
    private static final String dayabaccnum = "dayabaccnum";
    private static final String monthabaccnum = "monthabaccnum";


    public static int getDayaccAdNum(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayabaccnum);
    }


    public static int getMonthaccAdNum(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthabaccnum);
    }

    public static void setDayaccAdNum(Context context,int num){
         RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabaccnum, num);
    }


    public static void setMonthaccAdNum(Context context,int num){
         RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthabaccnum, num);
    }


    public static void resetDayaccNum(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabaccnum, 0);
    }


    public static void resetAllaccNum(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabaccnum, 0);
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthabaccnum, 0);
    }








}