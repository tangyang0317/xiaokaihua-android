package com.xkh.hzp.xkh.module;

import android.app.Application;

import com.xkh.hzp.xkh.config.Config;

import org.lasque.tusdk.core.TuSdk;

import xkh.hzp.xkh.com.base.base.ModuleBase;

/**
 * @packageName xkh.hzp.xkh.com.module
 * @FileName TutuModule
 * @Author tangyang
 * @DATE 2018/4/27
 **/
public class TutuModule extends ModuleBase {

    private static final String MODULENAME = "tutuSDK";

    public TutuModule() {
        super(MODULENAME);
    }

    @Override
    public void initModule(Application application) {
        /****初始化TuSDK***/
        TuSdk.init(application, "688f44bc830433b2-07-catjr1");
        TuSdk.enableDebugLog(Config.isDebug);
    }

    @Override
    public void destoryModule() {

    }
}
