package com.xkh.hzp.xkh;

import android.app.Application;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.umeng.socialize.PlatformConfig;
import com.xkh.hzp.xkh.module.CloudPushModule;
import com.xkh.hzp.xkh.module.ModulesManager;
import com.xkh.hzp.xkh.module.TutuModule;
import com.xkh.hzp.xkh.module.UmengModule;

import xkh.hzp.xkh.com.base.Global;

/**
 * @packageName xkh.hzp.xkh.com
 * @FileName App
 * @Author tangyang
 * @DATE 2018/4/27
 **/
public class App extends Application {


    {
        PlatformConfig.setWeixin("wx0977b69d0ab9fbed", "8f7ce94b39dd82fed7644497fd761e3a");
        PlatformConfig.setQQZone("101419520", "f9d32332cba45a256a78d271eb243cd8");
        PlatformConfig.setSinaWeibo("3815760382", "1191851cfadecafcfd709635eae10a0d", "http://api.xkhstar.cn");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Global.init(this);

        initCloud();
        ModulesManager.getIns().configModule(new UmengModule(), new TutuModule());
        ModulesManager.getIns().initModules();
    }

    private void initCloud() {

        PushServiceFactory.init(this);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(this, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("xkh", "init cloudchannel success");

            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d("xkh", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ModulesManager.getIns().destroyAll();
    }
}
