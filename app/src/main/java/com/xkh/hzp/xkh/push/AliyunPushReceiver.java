package com.xkh.hzp.xkh.push;

import android.content.Context;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.orhanobut.logger.Logger;

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

    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String s, String s1, String s2) {
        super.onNotificationClickedWithNoAction(context, s, s1, s2);
    }
}
