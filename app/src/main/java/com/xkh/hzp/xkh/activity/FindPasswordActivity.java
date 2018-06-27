package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.event.LogoutEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.CountDownUtil;
import com.xkh.hzp.xkh.utils.RegExpValidatorUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;
import com.xkh.hzp.xkh.view.Views;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.AppManager;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName FindPasswordActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class FindPasswordActivity extends BaseActivity {
    private EditText etPassword, etNewpass, etConfirmNewpass;
    private EditText etUserName;
    private TextView tvGet;
    private Button btnSign;

    public static void lanuchActivity(Activity activity, boolean isLogin) {
        Intent intent = new Intent(activity, FindPasswordActivity.class);
        intent.putExtra("isLogin", isLogin);
        activity.startActivity(intent);
    }


    private boolean isLogin() {
        return getIntent().getBooleanExtra("isLogin", false);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    public void initView() {
        setToolbarTitleTv("找回密码");
        etUserName = findViewById(R.id.activity_password_name);
        etPassword = findViewById(R.id.activity_password_password);
        tvGet = findViewById(R.id.activity_password_tvFpassword);
        btnSign = findViewById(R.id.activity_password_btnSign);
        etNewpass = findViewById(R.id.activity_setpassword_name);
        etConfirmNewpass = findViewById(R.id.activity_setpassword_password);
        if (!TextUtils.isEmpty(UserDataManager.getInstance().getLoginId())) {
            etUserName.setText(UserDataManager.getInstance().getLoginId());
            etUserName.setSelection(UserDataManager.getInstance().getLoginId().length());
            etUserName.setEnabled(false);
        }
    }

    @Override
    public void setListenner() {
        RxView.clicks(tvGet).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (TextUtils.isEmpty(etUserName.getText()) || etUserName.getText().length() != 11) {
                            Toasty.warning(FindPasswordActivity.this, "请填写11位手机号码").show();
                            return;
                        }
                        sendAuthCode(etUserName.getText().toString());
                    }
                });


        RxView.clicks(btnSign).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        resetPassword();
                    }
                });

    }


    /***
     * 发送验证码
     */
    private void sendAuthCode(String phone) {
        LinkedHashMap<String, String> param = new LinkedHashMap<>();
        param.put("type", "pwd");
        param.put("phone", phone);
        ABHttp.getIns().restfulGet(UrlConfig.getAuthCode, param, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", String.class);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                Toasty.info(FindPasswordActivity.this, msg).show();
                if (success) {
                    new CountDownUtil(tvGet)
                            .setCountDownMillis(60_000L)//倒计时60000ms
                            .setCountDownColor(R.color.color_006cff, R.color.color_666666)//不同状态字体颜色
                            .start();
                }
            }

            @Override
            public boolean onFailure(String code, String msg) {
                return super.onFailure(code, msg);
            }
        });

    }

    /***
     * 重置密码
     */
    private void resetPassword() {
        String phoneStr = etUserName.getText().toString();
        String smsNumStr = etPassword.getText().toString();
        String newPassStr = etNewpass.getText().toString();
        String newPassConfirmStr = etConfirmNewpass.getText().toString();

        if (!RegExpValidatorUtils.IsHandset(phoneStr)) {
            Toasty.warning(this, "请填写11位手机号码").show();
            return;
        }
        if (TextUtils.isEmpty(smsNumStr) || smsNumStr.length() != 4) {
            Toasty.warning(this, "请填写验证码");
            return;
        }

        if (TextUtils.isEmpty(newPassStr)) {
            Toasty.error(FindPasswordActivity.this, "新密码不能为空").show();
            return;
        }

        if (!RegExpValidatorUtils.IsPasswLength(newPassStr)) {
            Toasty.error(FindPasswordActivity.this, "新密码长度为6-16位").show();
            return;
        }

        if (RegExpValidatorUtils.IsChinese(newPassStr)) {
            Toasty.error(FindPasswordActivity.this, "新密码不能是中文").show();
            return;
        }


        if (TextUtils.isEmpty(newPassConfirmStr)) {
            Toasty.error(FindPasswordActivity.this, "确认新密码不能为空").show();
            return;
        }

        if (!RegExpValidatorUtils.IsPasswLength(newPassConfirmStr)) {
            Toasty.error(FindPasswordActivity.this, "确认新密码长度为6-16位").show();
            return;
        }

        if (RegExpValidatorUtils.IsChinese(newPassConfirmStr)) {
            Toasty.error(FindPasswordActivity.this, "确认新密码不能是中文").show();
            return;
        }

        if (!newPassStr.equals(newPassConfirmStr)) {
            Toasty.warning(this, "新密码和确认密码输入不一致");
            return;
        }

        HashMap<String, String> param = new HashMap<>();
        param.put("authCode", smsNumStr);
        param.put("passwd", newPassStr);
        param.put("phone", phoneStr);
        ABHttp.getIns().postJSON(UrlConfig.findPwd, JsonUtils.toJson(param), new AbHttpCallback() {
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
                        if (isLogin()) {
                            logout();
                        } else {
                            FindPasswordActivity.this.finish();
                        }
                    }
                }
            }
        });

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
                        SharedprefrenceHelper.getIns(FindPasswordActivity.this).clear();
                        EventBus.getDefault().post(new LogoutEvent(true));
                        SettingActivity.instance.finish();
                        FindPasswordActivity.this.finish();
                    }
                }
            }
        });

    }

}
