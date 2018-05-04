package com.xkh.hzp.xkh.tuSDK;

import android.app.Activity;

import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

import java.util.List;

/**
 * 涂图SDK调用基类
 * Created by tangyang on 17/11/22.
 */

public abstract class TuBase {

    /**
     * 组件帮助方法
     */
    public TuSdkHelperComponent componentHelper;


    public TuBase() {
    }


    /**
     * 显示范例
     */
    public abstract void showSample(Activity activity);

    /**
     * 显示范例
     */
    public abstract void showSample(Activity activity, int maxSelection);
}
