package com.xkh.hzp.xkh.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import xkh.hzp.xkh.com.base.AbDevice;
import xkh.hzp.xkh.com.base.Global;

/**
 * @packageName com.xkh.hzp.xkh.view
 * @FileName Views
 * @Author tangyang
 * @DATE 2018/5/23
 **/
public class Views {

    public static void setHeightByRatio(View view, double ratio) {
        int widthPixels = AbDevice.SCREEN_WIDTH_PX;
        setHeightByRatio(view, widthPixels, ratio);
    }

    public static void setHeightByRatio(View view, int width, double ratio) {
        int widthPixels = width;
        int heightPixels = (int) (widthPixels * ratio);
        // 不能这样, 转型错误
        // ViewGroup.LayoutParams params = new LayoutParams(width, height);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            Logger.e("setViewHeightByRatio出错,如果是代码new出来的View，需要设置一个适合的LayoutParams");
            return;
        }
        if (ratio == 0) {
            Logger.e("setViewHeightByRatio出错,ratio为0");
            return;
        }

        params.width = widthPixels;
        params.height = heightPixels;
        view.setLayoutParams(params);
    }

    public static void setWidthByRatio(View view, int height, double ratio) {
        int heightPixels = height;
        int widthPixels = (int) (height * ratio);
        // 不能这样, 转型错误
        // ViewGroup.LayoutParams params = new LayoutParams(width, height);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            Logger.e("setViewHeightByRatio出错,如果是代码new出来的View，需要设置一个适合的LayoutParams");
            return;
        }
        if (ratio == 0) {
            Logger.e("setViewHeightByRatio出错,ratio为0");
            return;
        }

        params.width = widthPixels;
        params.height = heightPixels;
        view.setLayoutParams(params);
    }

    /**
     * MATCH_PARENT = -1; WRAP_CONTENT = -2
     */
    public static LinearLayout.LayoutParams genLLParamsByDP(int width, int height) {
        int pxWidth = width;
        int pxHeight = height;
        if (pxWidth > 0) {
            pxWidth = (int) dp2px((float) width);
        }
        if (pxHeight > 0) {
            pxHeight = (int) dp2px((float) height);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pxWidth, pxHeight);
        return params;
    }

    /**
     * MATCH_PARENT = -1; WRAP_CONTENT = -2
     */
    public static LinearLayout.LayoutParams genLLParamsByPX(int width, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        return params;
    }

    /**
     * MATCH_PARENT = -1; WRAP_CONTENT = -2
     */
    public static RelativeLayout.LayoutParams genRLParamsDP(int width, int height) {
        int pxWidth = width;
        int pxHeight = height;
        if (pxWidth > 0) {
            pxWidth = (int) dp2px((float) width);
        }
        if (pxHeight > 0) {
            pxHeight = (int) dp2px((float) height);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pxWidth, pxHeight);
        return params;
    }

    /**
     * MATCH_PARENT = -1; WRAP_CONTENT = -2
     */
    public static RelativeLayout.LayoutParams genRLParamsPX(int width, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        return params;
    }

    public static void addTitleBar(Activity activity) {

    }

    /**
     * 描述：dip转换为px.
     *
     * @param dipValue the dip value
     * @return px值
     */
    public static int dp2px(float dipValue) {
        DisplayMetrics mDisplayMetrics = AbDevice.getDM();
        return (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, mDisplayMetrics);
    }

    /**
     * 描述：px转换为dip.
     *
     * @param pxValue the px value
     * @return dip值
     */
    public static float px2dp(float pxValue) {
        DisplayMetrics mDisplayMetrics = AbDevice.getDM();
        return pxValue / mDisplayMetrics.density;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = Global.app.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = Global.app.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * TypedValue官方源码中的算法，任意单位转换为PX单位
     *
     * @param unit    TypedValue.COMPLEX_UNIT_DIP
     * @param value   对应单位的值
     * @param metrics 密度
     * @return px值
     */
    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * 获取尺寸像素值
     */
    public static float fromDimens(int resId) {
        float dimens = 0f;
        try {
            dimens = Global.app.getResources().getDimension(resId);
        } catch (Exception e) {
            dimens = 0f;
        }
        return dimens;
    }

    /**
     * 获取颜色值
     */
    public static int fromColors(int resId) {
        // 默认值
        int res = 0;
        try {
            res = Global.app.getResources().getColor(resId);
        } catch (Exception e) {
            res = Global.app.getResources().getColor(android.R.color.black);
        }
        return res;
    }

    public static String fromStrings(int resId) {
        // 默认值
        String res = "";
        try {
            res = Global.app.getResources().getString(resId);
        } catch (Exception e) {
            res = "";
        }
        return res;
    }

    public static Drawable fromDrawables(int resId) {
        Drawable drawable = null;
        try {
            drawable = Global.app.getResources().getDrawable(resId);
        } catch (Exception e) {
            drawable = null;
        }
        return drawable;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getTag(View view, int tag, T def) {
        T res = def;
        Object obj = view.getTag(tag);
        if (obj != null) {
            try {
                res = (T) obj;
            } catch (Exception e) {
                res = def;
            }
        }
        return res;
    }

    public static void freezeViewByTime(final View view, int mills) {
        view.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    view.setClickable(true);
                }
            }
        }, mills);
    }

    public static String getTextFromView(TextView et) {
        return et.getText().toString().trim();
    }


    public static void setListViewFullHeight(ListView lv) {
        ListAdapter la = lv.getAdapter();
        if (null == la) {
            return;
        }
        // calculate height of all items.
        int h = 0;
        final int cnt = la.getCount();
        for (int i = 0; i < cnt; i++) {
            View item = la.getView(i, null, lv);
            item.measure(0, 0);
            h += item.getMeasuredHeight();
        }
        // reset ListView height
        ViewGroup.LayoutParams lp = lv.getLayoutParams();
        lp.height = h + (lv.getDividerHeight() * (cnt - 1));
        lv.setLayoutParams(lp);
    }


    public static void setHeight(View view, int heightInPx) {
        ViewGroup.LayoutParams param = view.getLayoutParams();
        param.height = heightInPx;
        view.setLayoutParams(param);
    }

    public static void setWidthAndHeight(View view, int widthInPx, int heightInPx) {
        ViewGroup.LayoutParams param = view.getLayoutParams();
        param.height = heightInPx;
        param.width = widthInPx;
        view.setLayoutParams(param);
    }

    public static LinearLayoutManager genLinearLayoutManagerH(Context ctx) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        return manager;
    }

    public static LinearLayoutManager genLinearLayoutManagerV(Context ctx) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        return manager;
    }
}
