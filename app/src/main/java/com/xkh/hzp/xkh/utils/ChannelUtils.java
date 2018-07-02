package com.xkh.hzp.xkh.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * @packageName com.xkh.hzp.xkh.utils
 * @FileName ChannelUtils
 * @Author tangyang
 * @DATE 2018/6/28
 **/
public class ChannelUtils {

    public static String getChannel(Context context, String key) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "";
    }

}
