package com.ad.block.osaadblock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if(activeInfo != null && activeInfo.isAvailable()){
            if(activeInfo.getType() == ConnectivityManager.TYPE_WIFI
                    && activeInfo.getState() == NetworkInfo.State.CONNECTED){
            }else if (activeInfo.getType() == ConnectivityManager.TYPE_MOBILE
                    && activeInfo.getState() == NetworkInfo.State.CONNECTED){
            }
        }else{
        }
    }  //如果无网络连接activeInfo为null

}


