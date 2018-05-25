package com.xkh.hzp.xkh;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.socialize.PlatformConfig;
import com.xkh.hzp.xkh.config.Config;
import com.xkh.hzp.xkh.dblite.DataBaseHelper;
import com.xkh.hzp.xkh.module.ModulesManager;
import com.xkh.hzp.xkh.module.TutuModule;
import com.xkh.hzp.xkh.module.UmengModule;

import org.lasque.tusdk.core.TuSdk;

import es.dmoral.toasty.Toasty;
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
        TuSdk.enableDebugLog(Config.isDebug);
        ModulesManager.getIns().configModule(new UmengModule(), new TutuModule());
        ModulesManager.getIns().initModules();

        /****初始化数据库****/
        DataBaseHelper.getHelper(this);

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .tag("xkh")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

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
