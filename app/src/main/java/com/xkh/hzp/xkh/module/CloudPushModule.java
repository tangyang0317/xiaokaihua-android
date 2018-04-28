package com.xkh.hzp.xkh.module;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import xkh.hzp.xkh.com.base.base.ModuleBase;

/**
 * @packageName xkh.hzp.xkh.com.module
 * @FileName CloudPushModule
 * @Author tangyang
 * @DATE 2018/4/27
 **/
public class CloudPushModule extends ModuleBase {

    public static final String NAME = "cloudpush";

    public CloudPushModule() {
        super(NAME);
    }

    @Override
    public void initModule(Application application) {
        PushServiceFactory.init(application);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(application, new CommonCallback() {
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
    public void destoryModule() {

    }
}
