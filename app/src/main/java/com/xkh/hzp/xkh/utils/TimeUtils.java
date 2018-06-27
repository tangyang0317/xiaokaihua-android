package com.xkh.hzp.xkh.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @packageName com.xkh.hzp.xkh.utils
 * @FileName TimeUtils
 * @Author tangyang
 * @DATE 2018/6/1
 **/
public class TimeUtils {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 30 * day;// 月
    private final static long year = 12 * month;// 年
    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(long date) {
        if (date <= 0) {
            return null;
        }
        long diff = new Date().getTime() - date;
        long r = 0;
        if (diff > year) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(date);
        }
        if (diff > day * 2) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
            return simpleDateFormat.format(date);
        }
        if (diff > day && diff < day * 2) {
            return "昨天";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }
}
