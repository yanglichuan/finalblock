package com.ad.block.osaadblock.utils;

import android.content.Context;

public class AdSaveFlowUtils {
    private static final String dayabsaveflownum = "dayabsaveflownum";
    private static final String monthabsaveflownum = "monthabsaveflownum";


    private static final String daysaveDegree = "daysaveDegree";
    private static final String monthsaveDegree = "monthsaveDegree";


    public static float getDaysaveflowAdNum(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getFloatData(dayabsaveflownum);
    }


    public static float getMonthsaveflowAdNum(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getFloatData(monthabsaveflownum);
    }


    public static int getDaysaveDegree(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(daysaveDegree);
    }


    public static int getMonthsaveDegree(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthsaveDegree);
    }


    public static float saveAdd(Context context, float addnum){
        float num1 =  RecordFileUtils.getInstance(context.getApplicationContext()).getFloatData(dayabsaveflownum);
        num1+=addnum;
        RecordFileUtils.getInstance(context.getApplicationContext()).setFloatData(dayabsaveflownum, num1);

        float num2 =  RecordFileUtils.getInstance(context.getApplicationContext()).getFloatData(monthabsaveflownum);
        num2+=addnum;
        RecordFileUtils.getInstance(context.getApplicationContext()).setFloatData(monthabsaveflownum, num2);



        //处理degree的情况
        float t1 = RecordFileUtils.getInstance(context.getApplicationContext()).getFloatData(dayabsaveflownum);
        float t2 = RecordFileUtils.getInstance(context.getApplicationContext()).getFloatData(monthabsaveflownum);
        if(t1 <= 1.0f){
            int temp = RandomUtils.getRandom(1,4);
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(daysaveDegree, temp);
        }
        if(t2 <= 1.0f){
            int temp = RandomUtils.getRandom(1,4);
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthsaveDegree, temp);
        }
        if(t1 >= 1.0f){
            int temp = RandomUtils.getRandom(2,5);
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(daysaveDegree, temp);
        }
        if(t2 >= 1.0f){
            int temp = RandomUtils.getRandom(2,5);
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthsaveDegree, temp);
        }
        return num1;
    }


    public static void resetDaysaveflowNum(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setFloatData(dayabsaveflownum, 0.0f);

        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(daysaveDegree, 0);
    }


    public static void resetAllsaveflowNum(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setFloatData(dayabsaveflownum, 0.0f);
        RecordFileUtils.getInstance(context.getApplicationContext()).setFloatData(monthabsaveflownum, 0.0f);

        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(daysaveDegree, 0);
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthsaveDegree, 0);
    }












}