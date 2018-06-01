package com.xkh.hzp.xkh.activity;

import android.view.View;

import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.ItemLayout;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName PasswordManagerActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class PasswordManagerActivity extends BaseActivity implements View.OnClickListener {

    private ItemLayout modifyPwdItemLayout, findPwdItemLayout, settingPasswordItemLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_password_manager;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("密码管理");
        settingPasswordItemLayout = findViewById(R.id.settingPasswordItemLayout);
        modifyPwdItemLayout = findViewById(R.id.modifyPwdItemLayout);
        findPwdItemLayout = findViewById(R.id.findPwdItemLayout);
    }

    @Override
    public void setListenner() {
        settingPasswordItemLayout.setOnClickListener(this);
        modifyPwdItemLayout.setOnClickListener(this);
        findPwdItemLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == modifyPwdItemLayout) {
            ModifyPasswordActivity.lunchActivity(this, null, ModifyPasswordActivity.class);
        } else if (view == findPwdItemLayout) {
            FindPasswordActivity.lunchActivity(this, null, FindPasswordActivity.class);
        } else if (view == settingPasswordItemLayout) {
            SettingPasswordActivity.lunchActivity(this, null, SettingPasswordActivity.class);
        }
    }
}
