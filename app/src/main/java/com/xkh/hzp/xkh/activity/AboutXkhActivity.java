package com.xkh.hzp.xkh.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.Config;

import xkh.hzp.xkh.com.base.AbDevice;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName AboutXkhActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class AboutXkhActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout relaGuifan;
    private RelativeLayout relaYinsi;
    private RelativeLayout relaServe;
    private TextView versionCodeTxt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_xkh;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("关于校花");
        relaServe = findViewById(R.id.activity_about_rl_serve);
        relaYinsi = findViewById(R.id.activity_about_rl_yinsi);
        relaGuifan = findViewById(R.id.activity_about_rl_guifan);
        versionCodeTxt = findViewById(R.id.versionCodeTxt);
        versionCodeTxt.setText("版本号" + AbDevice.appVersionName);
    }

    @Override
    public void setListenner() {
        relaServe.setOnClickListener(this);
        relaYinsi.setOnClickListener(this);
        relaGuifan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_about_rl_serve:
                WebActivity.launchWebActivity(this, Config.yonghuxieyi, "服务条款", "last");
                break;
            case R.id.activity_about_rl_yinsi:
                WebActivity.launchWebActivity(this, Config.yinsizhengce, "隐私政策", "last");
                break;
            case R.id.activity_about_rl_guifan:
                WebActivity.launchWebActivity(this, Config.shequguifan, "社区规范", "last");
                break;
        }
    }
}
