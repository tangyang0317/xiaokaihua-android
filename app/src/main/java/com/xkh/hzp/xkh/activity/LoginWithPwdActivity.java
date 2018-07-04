package com.xkh.hzp.xkh.activity;

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

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.Config;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.WebUserBean;
import com.xkh.hzp.xkh.event.LoginEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.IntentUtils;
import com.xkh.hzp.xkh.utils.RegExpValidatorUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.AppManager;
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
    private TextView serviceTxt, yisizhengceTxt, tvOrder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_with_pwd;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

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
        serviceTxt = findViewById(R.id.activity_login_tv_service);
        yisizhengceTxt = findViewById(R.id.activity_login_tv_zhichi);
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
        serviceTxt.setOnClickListener(this);
        yisizhengceTxt.setOnClickListener(this);
        tvOrder.setOnClickListener(this);

        RxView.clicks(btnLogin).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        loginWithPwd();
                    }
                });

    }

    /***
     * 账号密码登录
     */
    private void loginWithPwd() {
        final String account = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Toasty.error(LoginWithPwdActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toasty.error(LoginWithPwdActivity.this, "密码不能为空").show();
            return;
        }


        if (RegExpValidatorUtils.IsChinese(password)) {
            Toasty.error(LoginWithPwdActivity.this, "密码不能是中文").show();
            return;
        }

        HashMap map = new HashMap();
        map.put("password", password);
        map.put("account", account);
        map.put("loginType", "account");
        ABHttp.getIns().postJSON(UrlConfig.login, new Gson().toJson(map), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                entity.putField("result", new TypeToken<WebUserBean>() {
                }.getType());
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public boolean onFailure(String code, String msg) {
                Toasty.info(LoginWithPwdActivity.this, msg).show();
                return true;
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean isSuccess, HashMap<String, Object> extra) {
                Toasty.info(LoginWithPwdActivity.this, msg).show();
                if (isSuccess) {
                    final WebUserBean loginInfoBean = (WebUserBean) extra.get("result");
                    if (loginInfoBean != null) {
                        UserDataManager.getInstance().putLoginUser(loginInfoBean);
                        boundAliAccount(String.valueOf(loginInfoBean.getUid()));
                        hideKeyBoard();
                        EventBus.getDefault().post(new LoginEvent(true));
                        IntentUtils.sendBroadcast(LoginWithPwdActivity.this, Config.LOGIN_ACTION);
                        LoginActivity.instances.finish();
                        LoginWithPwdActivity.this.finish();
                    }
                }
            }
        });


    }

    /****
     * 绑定推送账号
     * @param account
     */
    private void boundAliAccount(String account) {
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.bindAccount(account, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                Logger.d("推送账号绑定成功" + s);
            }

            @Override
            public void onFailed(String s, String s1) {
                Logger.d(s + "-推送账号绑定失败-" + s1);
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view == tvOrder) {
            WebActivity.launchWebActivity(this, Config.shequguifan, "社区规范", "last");
        } else if (view == serviceTxt) {
            WebActivity.launchWebActivity(this, Config.yonghuxieyi, "服务条款", "last");
        } else if (view == yisizhengceTxt) {
            WebActivity.launchWebActivity(this, Config.yinsizhengce, "隐私政策", "last");
        } else if (view == ivBack) {
            this.finish();
        } else if (view == tvFotget) {
            FindPasswordActivity.lanuchActivity(this, false);
        } else if (view == btnToLogin) {
            this.finish();
        }
    }
}
