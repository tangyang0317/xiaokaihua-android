package com.xkh.hzp.xkh.activity;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName LoginWithPwdActivity
 * @Author tangyang
 * @DATE 2018/5/15
 **/
public class LoginWithPwdActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvFotget;
    private TextView btnToLogin;
    private RelativeLayout passwordLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_with_pwd;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void initView() {
        hideToolbar();
        ivBack = findViewById(R.id.activity_login_backImg);
        etPassword = findViewById(R.id.activity_register_password);
        etUsername = findViewById(R.id.activity_register_name);
        btnLogin = findViewById(R.id.activity_login_btnLogin);
        tvFotget = findViewById(R.id.activity_register_forgetPassword);
        btnToLogin = findViewById(R.id.activity_login_toLogin);
        passwordLayout = findViewById(R.id.ll_passwd);
        TextView tvOrder = findViewById(R.id.tvOrder);
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean b) {
                if (b) {
                    etUsername.setBackgroundResource(R.drawable.shape_edit_bg);
                } else {
                    etUsername.setBackgroundResource(R.drawable.shape_edit_enable_bg);
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    passwordLayout.setBackgroundResource(R.drawable.shape_edit_bg);
                } else {
                    passwordLayout.setBackgroundResource(R.drawable.shape_edit_enable_bg);
                }
            }
        });


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etUsername.length() > 0 && etPassword.length() >= 6) {
                    btnLogin.setBackgroundResource(R.drawable.sh_btn_red_press);
                } else {
                    btnLogin.setBackgroundResource(R.drawable.sl_btn_red);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etUsername.length() > 0 && etPassword.length() >= 6) {
                    btnLogin.setBackgroundResource(R.drawable.sh_btn_red_press);
                } else {
                    btnLogin.setBackgroundResource(R.drawable.sl_btn_red);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tvOrder.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvFotget.setOnClickListener(this);
        btnToLogin.setOnClickListener(this);
        findViewById(R.id.activity_login_tv_service).setOnClickListener(this);
        findViewById(R.id.activity_login_tv_zhichi).setOnClickListener(this);

    }

    @Override
    public void setListenner() {

    }

    @Override
    public void onClick(View view) {

    }
}
