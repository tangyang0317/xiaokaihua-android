package com.xkh.hzp.xkh.activity;

import android.view.View;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.utils.UserDataManager;

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
    private View settingPwdBottomLine, modifyPwdBottomLine, findPwdBottomLine;

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
        settingPwdBottomLine = findViewById(R.id.settingPwdBottomLine);
        modifyPwdBottomLine = findViewById(R.id.modifyPwdBottomLine);
        findPwdBottomLine = findViewById(R.id.findPwdBottomLine);
        if (UserDataManager.getInstance().getUserInfo() != null && "talent".equals(UserDataManager.getInstance().getUserInfo().getUserType())) {
            findPwdItemLayout.setVisibility(View.GONE);
            settingPasswordItemLayout.setVisibility(View.GONE);
            settingPwdBottomLine.setVisibility(View.GONE);
            findPwdBottomLine.setVisibility(View.GONE);
        } else {
            if (UserDataManager.getInstance().hasPwd()) {
                settingPasswordItemLayout.setVisibility(View.GONE);
                settingPwdBottomLine.setVisibility(View.GONE);
            }
        }
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
            PasswordManagerActivity.this.finish();
        } else if (view == findPwdItemLayout) {
            FindPasswordActivity.lunchActivity(this, null, FindPasswordActivity.class);
            PasswordManagerActivity.this.finish();
        } else if (view == settingPasswordItemLayout) {
            SettingPasswordActivity.lanuchActivity(this, false);
            PasswordManagerActivity.this.finish();
        }
    }
}
