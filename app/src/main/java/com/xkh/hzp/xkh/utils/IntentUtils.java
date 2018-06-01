package com.xkh.hzp.xkh.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @packageName com.xkh.hzp.xkh.utils
 * @FileName IntentUtils
 * @Author tangyang
 * @DATE 2018/6/1
 **/
public class IntentUtils {

    /**
     * 发送指定的Action广播
     *
     * @param context
     * @param strAction
     */
    public static void sendBroadcast(Context context, String strAction) {
        Intent intent = new Intent();
        intent.setAction(strAction);
        context.sendBroadcast(intent);
    }

    /**
     * 发送指定的Action广播，并写单bundel参数
     *
     * @param context
     * @param strAction
     * @param bundle
     */
    public static void sendBroadcast(Context context, String strAction, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(strAction);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param context
     * @param gotoClass
     */
    public static void startActivity(Context context, Class<?> gotoClass) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        context.startActivity(intent);
    }


    /**
     * 跳转到指定的页面，并传递bundle参数
     *
     * @param context
     * @param gotoClass
     * @param bundle
     */
    public static void startActivity(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 跳转到指定的Activity,并结束当前的Activity
     *
     * @param context
     * @param gotoClass
     */
    public static void startActivityAndFinish(Context context, Class<?> gotoClass) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        context.startActivity(intent);
        ((Activity) context).finish();
    }


    /**
     * 跳转到指定的页面，并传递bundle参数，并结束当前的Activity
     *
     * @param context
     * @param gotoClass
     * @param bundle
     */
    public static void startActivityAndFinish(Context context, Class<?> gotoClass, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).finish();
    }


    /**
     * 打开新的Activity，启动模式为SingleTop，并且结束当前Activity
     *
     * @param context
     * @param gotoClass
     */
    public static void startActivityToTopAndFinish(Context context, Class<?> gotoClass) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    /**
     * 启动带返回码的Activity
     *
     * @param context
     * @param gotoClass
     * @param requestCode
     */
    public static void startActivityForResult(Context context, Class<?> gotoClass, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }


    /**
     * 启动带返回码和返回参数的Activity，并且结束当前Activity
     *
     * @param context
     * @param gotoClass
     * @param requestCode
     * @param bundle
     */
    public static void startActivityForResult(Context context, Class<?> gotoClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, gotoClass);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }
}
