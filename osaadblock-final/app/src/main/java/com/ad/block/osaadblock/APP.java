package com.ad.block.osaadblock;

import android.app.Application;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initUM();
    }

    private void initUM(){
        //init umeng
        //打开调试模式（debug）后，在集成测试模式中，数据实时发送，不受发送策略控制
        MobclickAgent.setDebugMode(false);
        //禁止默认的页面统计方式
        MobclickAgent.openActivityDurationTrack(false);
        //您需要在程序的入口 Activity 中添加
        MobclickAgent.updateOnlineConfig(this);
        //如果enable为true，SDK会对日志进行加密
        AnalyticsConfig.enableEncrypt(false);

    }
}
