package com.xkh.hzp.xkh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.MainActivity;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import xkh.hzp.xkh.com.base.utils.JsonUtils;
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isFirst = (boolean) SharedprefrenceHelper.getIns(LoadingActivity.this).get("first_guide", true);
                if (isFirst) {
                    Intent intent = new Intent(LoadingActivity.this, GuideActivity.class);
                    LoadingActivity.this.startActivity(intent);
                    LoadingActivity.this.finish();
                } else {
                    MainActivity.lunchActivity(LoadingActivity.this, null, MainActivity.class);
                    LoadingActivity.this.finish();
                }
            }
        }, 2000);


        List<Long> ids = new ArrayList<>();
        ids.add(1l);
        ids.add(2l);
        ids.add(3l);
        ids.add(4l);
        Logger.d(JsonUtils.toJson(ids));
    }

}
