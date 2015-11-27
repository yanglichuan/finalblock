package com.ad.block.osaadblock.utils;

import android.content.Context;

public class AdBlockUtils {
    private static final String dayabblocknum = "dayabblocknum";
    private static final String monthabblocknum = "monthabblocknum";

    //今日拦截的比例
    private static final String dayabblockdegree = "dayabblockdegree";
    private static final String monthadblockdegree = "monthadblockdegree";

    private static final String dayString = "daystring";
    private static final String monthString = "monthstring";


    //一月使用天数****************************************
    private static final String monthUserdays = "monthUserdays";
    public static int getMonthUsedays(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthUserdays,1);
    }
    public static int addMonthUsedays(Context context){
        int xx = getMonthUsedays(context);
        xx++;
        if(xx > 28){
            xx = 28;
        }
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthUserdays,xx);
        return xx;
    }
    public static void resetMonthUserDays(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthUserdays,1);
    }
    //一月使用天数****************************************





    public static int getDayDegree(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayabblockdegree);
    }

    public static int getMonthDegree(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthadblockdegree);
    }



    public static int getTimeDay(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayString,-1);
    }

    public static int getTimeMonth(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthString,-1);
    }

    public static void setTimeDay(Context context,int time){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayString,time);
    }

    public static void setTimeMonth(Context context,int time){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthString,time);
    }







    /**
     * 拦截随机的广告个数
     * @param context
     * @return
     */
    public static int blockRandomAd(Context context){
        int num1 =  RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayabblocknum);
        int randomI =  Math.abs(RandomUtils.getRandom(6));
        num1 +=randomI;
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblocknum, num1);

        int num2 =  RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthabblocknum);
        num2+=randomI;
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthabblocknum,num2);



        //处理degree的情况
        int t1 = RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayabblocknum);
        int t2 = RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthabblocknum);
        if(t1 == 0){
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblockdegree,0);
        }
        if(t2 == 0){
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthadblockdegree,0);
        }
        if(t1 > 0){
            int t = 75+ (RandomUtils.getRandom(4)-2);
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblockdegree,t);
        }
        if(t2 > 0){
            int t = 75+ (RandomUtils.getRandom(10)-5);
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthadblockdegree,t);
        }
        return num1;
    }

    /**
     * 拦截一个广告
     * @param context
     * @return
     */
    public static int blockOneAd(Context context,int addnum){
        int num1 =  RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayabblocknum);
        num1+=addnum;
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblocknum,num1);


        int num2 =  RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthabblocknum);
        num2+=addnum;
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthabblocknum,num2);



        //处理degree的情况
        int t1 = RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayabblocknum);
        int t2 = RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthabblocknum);
        if(t1 == 0){
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblockdegree,0);
        }
        if(t2 == 0){
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthadblockdegree,0);
        }
        if(t1 > 0){
            int t = 75+ (RandomUtils.getRandom(4)-2);
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblockdegree,t);
        }
        if(t2 > 0){
            int t = 65+ (RandomUtils.getRandom(20)-10);
            RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthadblockdegree,t);
        }

        return num1;
    }


    /**
     * 获得今日拦截的广告个数
     * @param context
     * @return
     */
    public static int getDayBlockAdNum(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(dayabblocknum);
    }


    /**
     * 获得本月拦截的广告个数
     * @param context
     * @return
     */
    public static int getMonthBlockAdNum(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getIntData(monthabblocknum);
    }






    /**
     * 把今天的拦截数目给归零了
     * @param context
     */
    public static void resetDayBlockNum(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblocknum, 0);

        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblockdegree, 0);
    }


    /**
     * 新的一月 都重新归零
     * @param context
     */
    public static void resetAllBlockNum(Context context){
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblocknum, 0);
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthabblocknum, 0);

        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(dayabblockdegree, 0);
        RecordFileUtils.getInstance(context.getApplicationContext()).setIntData(monthadblockdegree, 0);
    }
}