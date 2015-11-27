package com.ad.block.osaadblock.utils;

import android.content.Context;
import android.net.TrafficStats;

public class AdFlowUtils {
    private static final String flowstring = "flowstring";

    public static long getCurrentTotalRxFlow(){
        return TrafficStats.getTotalRxBytes()/1000;
    }


    public static long getHisTotalRxFlow(Context context){
        return RecordFileUtils.getInstance(context.getApplicationContext()).getLongData(flowstring,-1);
    }

    public static void updateHisTotalRxFlow(Context context,long t){
        RecordFileUtils.getInstance(context.getApplicationContext()).setLongData(flowstring,t);
    }











}