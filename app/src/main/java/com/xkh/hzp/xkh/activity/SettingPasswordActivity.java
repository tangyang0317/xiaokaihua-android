package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;

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
            HashMap<String, String> param = new HashMap<>();
            param.put("password", passwordAgainStr);
            param.put("loginId", UserDataManager.getInstance().getLoginUser().getLoginId());
            param.put("loginType", "phone");
            ABHttp.getIns().post(UrlConfig.settingPwd, param, new AbHttpCallback() {
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
                            Toasty.info(SettingPasswordActivity.this, "密码设置成功").show();
                            SettingPasswordActivity.this.finish();
                            ChooseGenderActivity.lunchActivity(SettingPasswordActivity.this, null, ChooseGenderActivity.class);
                        }
                    }
                }
            });

        }
    }
}
