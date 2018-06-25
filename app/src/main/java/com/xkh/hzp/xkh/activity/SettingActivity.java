package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.Config;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.event.LogoutEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
import xkh.hzp.xkh.com.base.view.ItemLayout;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName SettingActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ItemLayout pwdManagerItemLayout, clearCacheItemLayout, aboutXkhItemLayout;
    private TextView logoutTxt;
    public static Activity instance;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("设置");
        pwdManagerItemLayout = findViewById(R.id.pwdManagerItemLayout);
        clearCacheItemLayout = findViewById(R.id.clearCacheItemLayout);
        aboutXkhItemLayout = findViewById(R.id.aboutXkhItemLayout);
        logoutTxt = findViewById(R.id.logoutTxt);
    }

    @Override
    public void setListenner() {
        pwdManagerItemLayout.setOnClickListener(this);
        clearCacheItemLayout.setOnClickListener(this);
        aboutXkhItemLayout.setOnClickListener(this);
        logoutTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == pwdManagerItemLayout) {
            PasswordManagerActivity.lunchActivity(SettingActivity.this, null, PasswordManagerActivity.class);
        } else if (view == clearCacheItemLayout) {
            Toasty.info(this, "缓存清除成功").show();
        } else if (view == aboutXkhItemLayout) {
            AboutXkhActivity.lunchActivity(this, null, AboutXkhActivity.class);
        } else if (view == logoutTxt) {
            logout();
        }
    }

    /***
     * 退出登录
     */
    private void logout() {

        ABHttp.getIns().post(UrlConfig.logout, null, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    if (result) {
                        SharedprefrenceHelper.getIns(SettingActivity.this).clear();
                        unbindAliAccount();
                        /****发送广播通知MainActivity替换"我的"页面****/
                        EventBus.getDefault().post(new LogoutEvent(true));
                        IntentUtils.sendBroadcast(SettingActivity.this, Config.LOGOUT_ACTION);
                        SettingActivity.this.finish();
                    }
                }
            }
        });

    }

    /****
     * 绑定推送账号
     */
    private void unbindAliAccount() {
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Logger.d("解绑成功");
            }

            @Override
            public void onFailed(String s, String s1) {
                Logger.d(s + "-解绑失败-" + s1);
            }
        });
    }

}
