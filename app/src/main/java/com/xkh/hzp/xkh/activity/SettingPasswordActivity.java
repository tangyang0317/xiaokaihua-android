package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.WebUserBean;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.RegExpValidatorUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName SettingPasswordActivity
 * @Author tangyang
 * @DATE 2018/5/15
 **/
public class SettingPasswordActivity extends BaseActivity {
    private Button button;
    private EditText etPassword;
    private EditText etName;

    public static void lanuchActivity(Activity activity, boolean showRightBtn) {
        Intent intent = new Intent(activity, SettingPasswordActivity.class);
        intent.putExtra("showRightBtn", showRightBtn);
        activity.startActivity(intent);
    }

    private boolean isShowRightBtn() {
        return getIntent().getBooleanExtra("showRightBtn", false);
    }

    @Override
    protected void callbackOnclickRightMenu(View view) {
        super.callbackOnclickRightMenu(view);
        this.finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_pwd;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    public void initView() {
        setToolbarTitleTv("设置密码");
        button = findViewById(R.id.activity_setpassword_btnLogin);
        etName = findViewById(R.id.activity_setpassword_name);
        etPassword = findViewById(R.id.activity_setpassword_password);
        if (isShowRightBtn()) {
            setRightTitleTxt("跳过");
            setToolBarRightTextColor(getResources().getColor(R.color.color_666666));
        }
    }

    @Override
    public void setListenner() {
        RxView.clicks(button).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        setttingPwd();
                    }
                });
    }


    /***
     * 设置密码
     */
    private void setttingPwd() {
        String passwordStr = etName.getText().toString();
        String passwordAgainStr = etPassword.getText().toString();
        if (TextUtils.isEmpty(passwordStr)) {
            Toasty.error(SettingPasswordActivity.this, "密码不能为空").show();
            return;
        }

        if (!RegExpValidatorUtils.IsPasswLength(passwordStr)) {
            Toasty.error(SettingPasswordActivity.this, "密码长度为6-16位").show();
            return;
        }

        if (RegExpValidatorUtils.IsChinese(passwordStr)) {
            Toasty.error(SettingPasswordActivity.this, "密码不能是中文").show();
            return;
        }


        if (TextUtils.isEmpty(passwordAgainStr)) {
            Toasty.error(SettingPasswordActivity.this, "确认密码不能为空").show();
            return;
        }

        if (!RegExpValidatorUtils.IsPasswLength(passwordAgainStr)) {
            Toasty.error(SettingPasswordActivity.this, "确认密码长度为6-16位").show();
            return;
        }

        if (RegExpValidatorUtils.IsChinese(passwordAgainStr)) {
            Toasty.error(SettingPasswordActivity.this, "确认密码不能是中文").show();
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
                        if (UserDataManager.getInstance().getLoginUser() != null) {
                            WebUserBean webUserBean = UserDataManager.getInstance().getLoginUser();
                            webUserBean.setHavePassWord(true);
                            UserDataManager.getInstance().putLoginUser(webUserBean);
                        }
                        Toasty.info(SettingPasswordActivity.this, "密码设置成功").show();
                        SettingPasswordActivity.this.finish();
                    }
                }
            }
        });
    }
}
