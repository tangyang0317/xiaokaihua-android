package com.xkh.hzp.xkh.module;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.socialize.UMShareAPI;
import com.xkh.hzp.xkh.config.Config;

import xkh.hzp.xkh.com.base.base.ModuleBase;

/**
 * @packageName xkh.hzp.xkh.com.module
 * @FileName UmengModule
 * @Author tangyang
 * @DATE 2018/4/27
 **/


public class UmengModule extends ModuleBase {
    public static final String NAME = "Umeng";

    public UmengModule() {
        super(NAME);
    }

    @Override
    public void initModule(Application application) {

        com.umeng.socialize.Config.DEBUG = Config.isDebug;
        OnlineConfigAgent.getInstance().setDebugMode(Config.isDebug);
        OnlineConfigAgent.getInstance().updateOnlineConfig(application);
        UMConfigure.init(application, null, null, UMConfigure.DEVICE_TYPE_PHONE, "9dd9f70f50f5430aed2988512c2d0393");
        UMConfigure.setLogEnabled(Config.isDebug);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setScenarioType(application, MobclickAgent.EScenarioType.E_DUM_NORMAL);
        UMShareAPI.get(application);

    }

    @Override
    public void destoryModule() {

    }
}
