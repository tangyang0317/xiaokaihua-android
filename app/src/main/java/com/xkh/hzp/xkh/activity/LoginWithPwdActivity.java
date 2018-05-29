package com.xkh.hzp.xkh.activity;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.WebUserBean;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
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
    private TextView tvOrder;
    private RelativeLayout passwordLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_with_pwd;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_ffffff).init();
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
        tvOrder = findViewById(R.id.tvOrder);
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
    }

    @Override
    public void setListenner() {
        tvOrder.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvFotget.setOnClickListener(this);
        btnToLogin.setOnClickListener(this);
        findViewById(R.id.activity_login_tv_service).setOnClickListener(this);
        findViewById(R.id.activity_login_tv_zhichi).setOnClickListener(this);
    }

    /***
     * 账号密码登陆
     */
    private void loginWithPwd() {
        String account = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toasty.error(LoginWithPwdActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toasty.error(LoginWithPwdActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap map = new HashMap();
        map.put("authCode", null);
        map.put("deviceType", null);
        map.put("location", null);
        map.put("password", password);
        map.put("phone", account);
        map.put("uniqueId", null);

        ABHttp.getIns().postJSON(UrlConfig.login, new Gson().toJson(map), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                entity.putField("result", new TypeToken<WebUserBean>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean isSuccess, HashMap<String, Object> extra) {
                showToast(msg);
                if (ABHttp.CODE_SUCCESS.equals(code)) {
                    WebUserBean loginInfoBean = (WebUserBean) extra.get("result");
                    if (loginInfoBean != null) {
                        UserDataManager.getInstance().putLoginUser(loginInfoBean);
                        LoginWithPwdActivity.this.finish();
                    }
                }
            }
        });


    }


    @Override
    public void onClick(View view) {

        if (view == btnLogin) {
            loginWithPwd();
        }

    }
}
