package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xkh.hzp.xkh.R;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName SettingPasswordActivity
 * @Author tangyang
 * @DATE 2018/5/15
 **/
public class SettingPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button button;
    private EditText etPassword;
    private EditText etName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_pwd;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_ffffff).init();
    }

    @Override
    public void initView() {
        button = findViewById(R.id.activity_setpassword_btnLogin);
        etName = findViewById(R.id.activity_setpassword_name);
        etPassword = findViewById(R.id.activity_setpassword_password);

    }

    @Override
    public void setListenner() {
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == button) {
            String passwordStr = etName.getText().toString();
            String passwordAgainStr = etPassword.getText().toString();
            if (TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(passwordAgainStr)) {
                Toasty.error(SettingPasswordActivity.this, "请输入6-16位密码").show();
                return;
            }

            if (!passwordStr.equals(passwordAgainStr)) {
                Toasty.error(SettingPasswordActivity.this, "两次输入的密码不一致，请从新输入").show();
                return;
            }
        }
    }
}
