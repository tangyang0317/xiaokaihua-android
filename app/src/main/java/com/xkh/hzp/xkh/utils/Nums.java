package com.xkh.hzp.xkh.utils;

import android.content.Context;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;

/**
 * @packageName com.xkh.hzp.xkh.utils
 * @FileName Nums
 * @Author tangyang
 * @DATE 2018/6/28
 **/
public class Nums {

    public static <T> boolean contains(T[] array, T value) {
        for (T t : array) {
            if (t.equals(value)) {
                return true;
            }
        }
        return false;
    }


    public static double roundDouble(double num) {
        double res = 0;
        BigDecimal bd = new BigDecimal(num);
        res = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return res;
    }

    public static double formatTriple(double num) {
        double res = 0;
        BigDecimal bd = new BigDecimal(num);
        res = bd.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        return res;
    }


    public static boolean checkPositiveAndToast(Context ctx, long num, String info) {
        boolean isValid = false;
        if (num > 0) {
            isValid = true;
        } else {
            Toasty.info(ctx, info).show();
        }
        return isValid;
    }

    public static <T> boolean numEqual(Object ob1, T ob2) {
        if (ob1 == null || ob2 == null) {
            return false;
        } else if (ob1 != null) {
            return ob1.equals(ob2);
        } else if (ob2 != null) {
            return ob2.equals(ob1);
        } else {
            return false;
        }

    }

    public static <T> boolean isNullOrZero(T obj) {
        if (obj == null || obj.equals(0)) {
            return true;
        }
        return false;
    }

    /**
     * @param objValue       传入的参数
     * @param fractionDigits 保留多少位
     * @return
     */
    public static final String formatDecimal(Object objValue, int fractionDigits) {
        Double value = null;
        if (objValue instanceof String) {
            value = Double.valueOf((String) objValue);
        } else if (objValue instanceof Integer || objValue instanceof Float || objValue instanceof Double) {
            value = ((Number) objValue).doubleValue();
        } else if (objValue instanceof BigDecimal) {
            value = ((BigDecimal) objValue).doubleValue();
        } else {
            value = 0.00d;
        }
        BigDecimal doubleValue = new BigDecimal(value);
        String format = "#0.";
        for (int i = 0; i < fractionDigits; i++) {
            format += "0";
        }
        DecimalFormat df = new DecimalFormat(format);
        return df.format(doubleValue);
    }


    public static String countTranslate(int num) {
        String str;
        if (num > 10000) {
            String goodCount = new DecimalFormat("#.0").format(num / 10000.0);
            str = "" + goodCount + "万";
        } else {
            str = "" + num;
        }
        return str;
    }

}
