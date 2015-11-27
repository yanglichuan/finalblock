package com.ad.block.osaadblock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ad.block.osaadblock.Constants;
import com.ad.block.osaadblock.service.BlockService;
import com.ad.block.osaadblock.utils.CommonUtils;
import com.ad.block.osaadblock.utils.RecordFileUtils;

public class BootBroadcastReceiver extends BroadcastReceiver {
//    static final String action_boot="android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
            boolean bauto = RecordFileUtils.getInstance(
                    context.getApplicationContext()).getBooleanData(Constants.AUTOSTARTUP,false);
            if(bauto){
                if(!CommonUtils.isServiceRunning(context,BlockService.class.getCanonicalName())){
                    context.startService(new Intent(context, BlockService.class));
                }
            }else{
                //
            }
        }
}