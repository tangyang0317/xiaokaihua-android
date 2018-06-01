package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.event.LogoutEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.view.Views;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName FindPasswordActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText etPassword, etNewpass, etConfirmNewpass;
    private EditText etUserName;
    private TextView tvGet;
    private Button btnSign;
    private int recLen = 60;
    private Timer timer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_pwd;
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
        btnSign.setOnClickListener(this);
        tvGet.setOnClickListener(this);
        timer = new Timer();
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    tvGet.setText("(" + recLen + ")" + "再次发送");
                    tvGet.setTextColor(Views.fromColors(R.color.color_006cff));
                    tvGet.setBackgroundResource(R.drawable.btn_circle_custom_gray);
                    if (recLen < 0) {
                        timer.cancel();
                        tvGet.setText("获取验证码");
                    }
                }
            });
        }
    };

    @Override
    public void setListenner() {

    }


    /***
     * 发送验证码
     */
    private void sendAuthCode(String phone) {
        timer.schedule(task, 1000, 1000);
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
            }

            @Override
            public boolean onFailure(String code, String msg) {
                return super.onFailure(code, msg);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_password_btnSign:
                resetPassword();
                break;
            case R.id.activity_password_tvFpassword:
                if (TextUtils.isEmpty(etUserName.getText()) || etUserName.getText().length() != 11) {
                    Toasty.warning(this, "请填写11位手机号码").show();
                    return;
                }
                sendAuthCode(etUserName.getText().toString());
                break;
        }
    }

    /***
     * 重置密码
     */
    private void resetPassword() {
        String phoneStr = etUserName.getText().toString();
        String smsNumStr = etPassword.getText().toString();
        String newPassStr = etNewpass.getText().toString();
        String newPassConfirmStr = etConfirmNewpass.getText().toString();

        if (TextUtils.isEmpty(phoneStr) || phoneStr.length() != 11) {
            Toasty.warning(this, "请填写11位手机号码").show();
            return;
        }

        if (TextUtils.isEmpty(smsNumStr) || smsNumStr.length() != 4) {
            Toasty.warning(this, "请填写验证码");
            return;
        }

        if (TextUtils.isEmpty(newPassStr)) {
            Toasty.warning(this, "请填写新密码");
            return;
        }

        if (newPassStr.length() < 6 || newPassStr.length() > 16) {
            Toasty.warning(this, "请填写6-16位新密码");
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
                        logout();
                    }
                }
            }
        });

    }


    /***
     * 退出登陆
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
                        FindPasswordActivity.this.finish();
                    }
                }
            }
        });

    }

}
