package com.xkh.hzp.xkh.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.R;

import java.util.Map;

/**
 * 阿里云消息接受者
 * Created by tangyang on 17/11/7.
 */

public class AliyunPushReceiver extends MessageReceiver {

    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);
        Logger.d(cPushMessage.getTitle() + "-----" + cPushMessage.getContent());
    }

    @Override
    protected void onNotification(Context context, String s, String s1, Map<String, String> map) {
        super.onNotification(context, s, s1, map);
        Logger.d(s + "--消息通知---" + s1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showChannelNotification(context, s, s1);
        }
    }

    /***
     * 渠道推送通知
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showChannelNotification(Context context, String title, String content) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //创建 通知通道  channelid和channelname是必须的（自己命名就好）
        NotificationChannel channel = new NotificationChannel("Other Channel",
                "校开花", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);//是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.GREEN);//小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        notificationManager.createNotificationChannel(channel);

        int notificationId = 0x1234;
        Notification.Builder builder = new Notification.Builder(context, "Other Channel");
        //设置通知显示图标、文字等
        builder.setSmallIcon(R.mipmap.logo)
                .setSubText(title)
                .setContentText(content)
                .setAutoCancel(true).build();
        notificationManager.notify(notificationId, builder.getNotification());

    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String s, String s1, String s2) {
        super.onNotificationClickedWithNoAction(context, s, s1, s2);
    }
}
