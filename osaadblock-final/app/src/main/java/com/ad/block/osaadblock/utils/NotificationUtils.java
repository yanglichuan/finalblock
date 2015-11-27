package com.ad.block.osaadblock.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.ad.block.osaadblock.MainActivity;
import com.ad.block.osaadblock.R;

import java.io.File;

/**
 * Notification首页
 */
public class NotificationUtils {
    /**
     * Notification的ID
     */
    public static final int notifyId = 100;

    /**
     * 初始化通知栏
     */
    public static NotificationCompat.Builder initNotify(Context ct) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ct);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                        //// TODO: 2015/10/27
                        //.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
// .setNumber(number)//显示数量
                .setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
//Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);
        return mBuilder;
    }

    /**
     * 显示通知栏
     */
    public static void showNotify(Context ct) {
        NotificationCompat.Builder mBuilder = initNotify(ct);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
// .setNumber(number)//显示数量
                .setTicker("测试通知来啦");//通知首次出现在通知栏，带上升动画效果的
        NotificationManager manager = (NotificationManager) ct.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifyId, mBuilder.build());
// mNotification.notify(getResources().getString(R.string.app_name), notiId, mBuilder.build());
    }

    /**
     * 显示大视图风格通知栏
     */
    public static void showBigStyleNotify(Context ct) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[5];
// Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("大视图内容:");
// Moves events into the big view
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }

        NotificationCompat.Builder mBuilder = initNotify(ct);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
// .setNumber(number)//显示数量
                .setStyle(inboxStyle)//设置风格
                .setTicker("测试通知来啦");// 通知首次出现在通知栏，带上升动画效果的


        NotificationManager manager = (NotificationManager) ct.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifyId, mBuilder.build());
        // mNotification.notify(getResources().getString(R.string.app_name),
        // notiId, mBuilder.build());
    }

    /**
     * 显示常驻通知栏
     */
    public static void showCzNotify(Context ct,String contentText) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ct);
        Intent it = new Intent();
        it.setClass(ct, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //PendingIntent pendingIntent = PendingIntent.getActivity(ct, 0, ((Activity) ct).getIntent(), 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(ct, 0,it, 0);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(ct.getString(R.string.app_name))
                .setContentTitle(ct.getString(R.string.app_name))
                .setContentText(contentText)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        //设置通知 消息 图标
        mNotification.icon = R.mipmap.ic_launcher;
        //在通知栏上点击此通知后自动清除此通知
        mNotification.flags = Notification.FLAG_ONGOING_EVENT|Notification.FLAG_NO_CLEAR;
        //FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除 FLAG_AUTO_CANCEL 点击和清理可以去调
        //设置显示通知时的默认的发声、震动、Light效果
        // mNotification.defaults = Notification.DEFAULT_VIBRATE;
        //设置发出消息的内容
        mNotification.tickerText = ct.getString(R.string.app_name);
        //设置发出通知的时间
        mNotification.when = System.currentTimeMillis();
        // mNotification.setLatestEventInfo(this, "常驻测试", "使用cancel()方法才可以把我去掉哦", null); //设置详细的信息 ,这个方法现在已经不用了

        NotificationManager manager = (NotificationManager)
                ct.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifyId, mNotification);
    }



    /**
     * 显示常驻通知栏
     */
    public static Notification creatNotify(Context ct,String contentText) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ct);
        Intent it = new Intent();
        it.setClass(ct, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //PendingIntent pendingIntent = PendingIntent.getActivity(ct, 0, ((Activity) ct).getIntent(), 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(ct, 0,it, 0);

        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(ct.getString(R.string.app_name))
                .setContentTitle(ct.getString(R.string.app_name))
                .setContentText(contentText)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        //设置通知 消息 图标
        mNotification.icon = R.mipmap.ic_launcher;
        //在通知栏上点击此通知后自动清除此通知
        mNotification.flags = Notification.FLAG_ONGOING_EVENT|Notification.FLAG_NO_CLEAR;
        //FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除 FLAG_AUTO_CANCEL 点击和清理可以去调
        //设置显示通知时的默认的发声、震动、Light效果
        // mNotification.defaults = Notification.DEFAULT_VIBRATE;
        //设置发出消息的内容
        mNotification.tickerText = ct.getString(R.string.app_name);
        //设置发出通知的时间
        mNotification.when = System.currentTimeMillis();
        // mNotification.setLatestEventInfo(this, "常驻测试", "使用cancel()方法才可以把我去掉哦", null); //设置详细的信息 ,这个方法现在已经不用了
//
//        NotificationManager manager = (NotificationManager)
//                ct.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(notifyId, mNotification);

        return mNotification;
    }




    /**
     * 显示通知栏点击跳转到指定Activity
     */
    public static void showTempNotify(Context ct,int notiId,String contentText) {
// Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
// notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ct);
        mBuilder.setAutoCancel(true)//点击后让通知将消失
                .setContentTitle(ct.getString(R.string.app_name))
                .setContentText(contentText)
                .setTicker(ct.getString(R.string.app_name));
//点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent();
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ct,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) ct.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notiId, mBuilder.build());
    }




    /**
     * 显示通知栏点击跳转到指定Activity
     */
    public static void showIntentActivityNotify(Context ct) {
// Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
// notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ct);
        mBuilder.setAutoCancel(true)//点击后让通知将消失
                .setContentTitle("")
                .setContentText("点击跳转")
                .setTicker("点我");
//点击的意图ACTION是跳转到Intent
        Intent resultIntent = new Intent();
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ct,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) ct.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifyId, mBuilder.build());
    }

    public static void cancelNotify(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notifyId);
    }


    /**
     * 显示通知栏点击打开Apk
     */
    public static void showIntentApkNotify(Context ct) {
// Notification.FLAG_ONGOING_EVENT --设置常驻 Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
// notification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ct);
        mBuilder.setAutoCancel(true)//点击后让通知将消失
                .setContentTitle("下载完成")
                .setContentText("点击安装")
                .setTicker("下载完成！");
//我们这里需要做的是打开一个安装包
        Intent apkIntent = new Intent();
        apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        apkIntent.setAction(android.content.Intent.ACTION_VIEW);
//注意：这里的这个APK是放在assets文件夹下，获取路径不能直接读取的，要通过COYP出去在读或者直接读取自己本地的PATH，这边只是做一个跳转APK，实际打不开的
        String apk_path = "file:///android_asset/cs.apk";
// Uri uri = Uri.parse(apk_path);
        Uri uri = Uri.fromFile(new File(apk_path));
        apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
// context.startActivity(intent);
        PendingIntent contextIntent = PendingIntent.getActivity(ct, 0, apkIntent, 0);
        mBuilder.setContentIntent(contextIntent);

        NotificationManager manager = (NotificationManager) ct.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifyId, mBuilder.build());
    }


}



