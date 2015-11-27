package com.ad.block.osaadblock.utils;

import java.util.Calendar;

public class CalendarUtils {

    public static int getMonthNum(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }


    public static int getDayNum(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }



}