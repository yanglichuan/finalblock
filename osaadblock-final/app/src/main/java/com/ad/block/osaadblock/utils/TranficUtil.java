package com.ad.block.osaadblock.utils;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TranficUtil {




    public static void getAppTrafficList(final Activity context, final TextView textView) {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                //获取所有的安装在手机上的应用软件的信息，并且获取这些软件里面的权限信息
                final PackageManager pm = context.getPackageManager();//获取系统应用包管理
                //获取每个包内的androidmanifest.xml信息，它的权限等等
                List<PackageInfo> pinfos =  new ArrayList<PackageInfo>();

                List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
                for (ApplicationInfo applicationInfo : packages) {
                    try {
                        PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                                PackageManager.GET_PERMISSIONS);
                        pinfos.add(packageInfo);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(pinfos != null && pinfos.size()>0){
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setVisibility(View.VISIBLE);
                        }
                    });
                }else{
                    return null;
                }
                //遍历每个应用包信息
                for (final PackageInfo info : pinfos) {
                    //请求每个程序包对应的androidManifest.xml里面的权限
                    String[] premissions = info.requestedPermissions;
                    if (premissions != null && premissions.length > 0) {
                        //找出需要网络服务的应用程序
                        for (String premission : premissions) {
                            if ("android.permission.INTERNET".equals(premission)) {
                                //获取每个应用程序在操作系统内的进程id
                                int uId = info.applicationInfo.uid;
                                //如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
                                long rx = TrafficStats.getUidRxBytes(uId);
                                //如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
                                long tx = TrafficStats.getUidTxBytes(uId);
                                if (rx < 0 || tx < 0) {
                                    continue;
                                } else {
                                    SystemClock.sleep(1);
                                    if(!TextUtils.isEmpty(info.applicationInfo.loadLabel(pm))){
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                textView.setText(info.applicationInfo.loadLabel(pm));
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.GONE);
                    }
                });
                return null;
            }
        }.execute();
    }
//    public static void getAppTrafficList(final Activity context, final TextView textView) {
//        new AsyncTask<Void,Void,Void>(){
//            @Override
//            protected Void doInBackground(Void... params) {
//                //获取所有的安装在手机上的应用软件的信息，并且获取这些软件里面的权限信息
//                final PackageManager pm = context.getPackageManager();//获取系统应用包管理
//                //获取每个包内的androidmanifest.xml信息，它的权限等等
//                List<PackageInfo> pinfos = pm.getInstalledPackages
//                        (PackageManager.GET_PERMISSIONS);
///*                List<PackageInfo> pinfos = pm.getInstalledPackages
//                        (PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);*/
//                if(pinfos != null && pinfos.size()>0){
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setVisibility(View.VISIBLE);
//                        }
//                    });
//                }else{
//                    return null;
//                }
//                //遍历每个应用包信息
//                for (final PackageInfo info : pinfos) {
//                    //请求每个程序包对应的androidManifest.xml里面的权限
//                    String[] premissions = info.requestedPermissions;
//                    if (premissions != null && premissions.length > 0) {
//                        //找出需要网络服务的应用程序
//                        for (String premission : premissions) {
//                            if ("android.permission.INTERNET".equals(premission)) {
//                                //获取每个应用程序在操作系统内的进程id
//                                int uId = info.applicationInfo.uid;
//                                //如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
//                                long rx = TrafficStats.getUidRxBytes(uId);
//                                //如果返回-1，代表不支持使用该方法，注意必须是2.2以上的
//                                long tx = TrafficStats.getUidTxBytes(uId);
//                                if (rx < 0 || tx < 0) {
//                                    continue;
//                                } else {
//                                    SystemClock.sleep(1);
//                                    if(!TextUtils.isEmpty(info.applicationInfo.loadLabel(pm))){
//                                        context.runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                textView.setText(info.applicationInfo.loadLabel(pm));
//                                            }
//                                        });
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                context.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setVisibility(View.GONE);
//                    }
//                });
//                return null;
//            }
//        }.execute();
//    }
}