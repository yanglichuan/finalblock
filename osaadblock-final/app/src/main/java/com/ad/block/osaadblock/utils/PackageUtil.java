package com.ad.block.osaadblock.utils; /**
 *
 * Copyright (c) 2014 CoderKiss
 *
 * CoderKiss[AT]gmail.com
 *
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;


public class PackageUtil {

    private static final String BOOT_START_PERMISSION = "android.permission.RECEIVE_BOOT_COMPLETED";


    public static PackageInfo getPackageInfo(Context ct, String packageName) {
        PackageInfo packageInfo = null;
        Context context = ct.getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        try {
            int flags = PackageManager.GET_ACTIVITIES | PackageManager.GET_GIDS
                    | PackageManager.GET_CONFIGURATIONS
                    | PackageManager.GET_INSTRUMENTATION
                    | PackageManager.GET_PERMISSIONS
                    | PackageManager.GET_PROVIDERS
                    | PackageManager.GET_RECEIVERS
                    | PackageManager.GET_SERVICES
                    | PackageManager.GET_SIGNATURES
                    | PackageManager.GET_UNINSTALLED_PACKAGES;
            packageInfo = packageManager.getPackageInfo(packageName, flags);
        } catch (Exception ignore) {
        }
        return packageInfo;
    }

    public static boolean isBootStart(Context ct, String packageName) {
        Context context = ct.getApplicationContext();
        PackageManager pm = context.getPackageManager();
        int flag = pm.checkPermission(BOOT_START_PERMISSION, packageName);
        return (flag == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isAutoStart(Context ct,String packageName) {
        Context context = ct.getApplicationContext();
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_BOOT_COMPLETED);
        List<ResolveInfo> resolveInfoList = pm.queryBroadcastReceivers(intent,
                PackageManager.GET_DISABLED_COMPONENTS);
        for (ResolveInfo ri : resolveInfoList) {
            String pn = ri.loadLabel(pm).toString();
            if (packageName.equals(pn)) {
                return true;
            }
        }
        return false;
    }

    public static String getPackageDir(Context ct, String packageName) {
        String applicationDir = null;
        PackageInfo pi = getPackageInfo(ct,packageName);
        if (pi != null) {
            applicationDir = pi.applicationInfo.dataDir;
        }
        return applicationDir;
    }
}