package com.xkh.hzp.xkh.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;
import com.xkh.hzp.xkh.R;

import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText meditText, editYzm;
    private LinearLayout phoneNumLinearLayout;
    private Button btnLogin, sendSms;
    private ImageView ivBack;
    private TextView tvToLogin;
    private TextView tvYzm;
    private int recLen = 60;
    private Timer timer;
    private TextView tvService;
    private TextView tvZhichi;
    private ImageView ivLogo;
    private TextView tvOrder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        hideToolbar();
        timer = new Timer();
        sendSms = findViewById(R.id.activity_login_getyzm);
        phoneNumLinearLayout = findViewById(R.id.phoneNumLinearLayout);
        meditText = findViewById(R.id.activity_login_edit);
        editYzm = findViewById(R.id.activity_login_yzm);
        tvYzm = findViewById(R.id.activity_login_getyzm);
        tvToLogin = findViewById(R.id.activity_login_toLogin);
        ivBack = findViewById(R.id.activity_login_backImg);
        ivLogo = findViewById(R.id.activity_login_logoImg);
        btnLogin = findViewById(R.id.activity_login_btnLogin);
        tvService = findViewById(R.id.activity_login_tv_service);
        tvZhichi = findViewById(R.id.activity_login_tv_zhichi);
        tvOrder = findViewById(R.id.tvOrder);

        tvOrder.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvToLogin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvYzm.setOnClickListener(this);
        tvService.setOnClickListener(this);
        tvZhichi.setOnClickListener(this);
        meditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    sendSms.setBackgroundResource(R.drawable.sh_btn_red_press);
                } else {
                    sendSms.setBackgroundResource(R.drawable.sl_btn_red);
                    btnLogin.setBackgroundResource(R.drawable.sl_btn_red);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        meditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    phoneNumLinearLayout.setBackgroundResource(R.drawable.shape_edit_bg);
                } else {
                    phoneNumLinearLayout.setBackgroundResource(R.drawable.shape_edit_enable_bg);
                }
            }
        });


        editYzm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    editYzm.setBackgroundResource(R.drawable.shape_edit_bg);
                } else {
                    editYzm.setBackgroundResource(R.drawable.shape_edit_enable_bg);
                }
            }
        });


        editYzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 4 && meditText.getText().length() == 11) {
                    btnLogin.setBackgroundResource(R.drawable.sh_btn_red_press);
                } else {
                    btnLogin.setBackgroundResource(R.drawable.sl_btn_red);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setListenner() {

    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    tvYzm.setText("(" + recLen + ")" + "再次发送");
                    tvYzm.setTextColor(getResources().getColor(R.color.color_666666));
                    tvYzm.setBackgroundResource(R.drawable.shape_btn_login_enable);
                    tvYzm.setEnabled(false);
                    if (recLen < 0) {
                        timer.cancel();
                        tvYzm.setText("获取验证码");
                        tvYzm.setBackgroundResource(R.drawable.shape_login_btn_normal);
                        tvYzm.setEnabled(true);
                    }
                }
            });
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_backImg:
                finish();
                break;
            case R.id.activity_login_btnLogin:
                if (!TextUtils.isEmpty(meditText.getText()) && !TextUtils.isEmpty(editYzm.getText())) {
                    //登录
                    btnLogin.setEnabled(false);
                    btnLogin.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnLogin.setEnabled(true);
                        }
                    }, 1000);
                    btnLogin();
                } else {
                    Toast.makeText(this, "请输入正确的手机号或验证码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_login_getyzm:
                if (!TextUtils.isEmpty(meditText.getText()) && meditText.length() == 11) {
                    //获取验证码
                } else if (TextUtils.isEmpty(meditText.getText()) || meditText.length() != 11) {
                    Toast.makeText(this, "请填写正确手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_login_tv_service:
                break;
            case R.id.activity_login_tv_zhichi:
                break;
            case R.id.tvOrder:
                break;
            case R.id.activity_login_toLogin:
                LoginWithPwdActivity.lunchActivity(LoginActivity.this, null, LoginWithPwdActivity.class);
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //登陆
    private void btnLogin() {
        final String account = meditText.getText().toString();
        String sms = editYzm.getText().toString();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(LoginActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(sms)) {
            Toasty.error(LoginActivity.this, "请输入验证码");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
