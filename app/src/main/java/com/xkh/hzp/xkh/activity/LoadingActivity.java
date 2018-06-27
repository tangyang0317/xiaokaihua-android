package com.xkh.hzp.xkh.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.xkh.hzp.xkh.MainActivity;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.utils.CheckLoginManager;

import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * 启动页
 * Created by tangyang on 18/4/19.
 */

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);

        CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
            @Override
            public void isLogin(boolean isLogin) {
                if (!isLogin) {
                    SharedprefrenceHelper.getIns(LoadingActivity.this).clear();
                }
            }
        });
//        registerNotificationChannel();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.lunchActivity(LoadingActivity.this, null, MainActivity.class);
                LoadingActivity.this.finish();
            }
        }, 2000);

    }

    /***
     *适配8.0以上,设置推送通道
     */
    private void registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, "", importance);
            // 配置通知渠道的属性
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

}
