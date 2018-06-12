package com.xkh.hzp.xkh.activity;

import android.content.Intent;
import android.os.Handler;
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

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareAPI;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.Config;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.WebUserBean;
import com.xkh.hzp.xkh.entity.result.RegisterResult;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.event.LoginEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;

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
    /****用户是否注册***/
    private boolean isRegister = false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
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
                    loginOrRegiser();
                } else {
                    Toast.makeText(this, "请输入正确的手机号或验证码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_login_getyzm:
                if (!TextUtils.isEmpty(meditText.getText()) && meditText.length() == 11) {
                    //获取验证码
                    checkRegister();
                } else if (TextUtils.isEmpty(meditText.getText()) || meditText.length() != 11) {
                    Toast.makeText(this, "请填写正确手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_login_tv_service:
                WebActivity.launchWebActivity(this, Config.yonghuxieyi, "服务条款", "last");
                break;
            case R.id.activity_login_tv_zhichi:
                WebActivity.launchWebActivity(this, Config.yinsizhengce, "隐私政策", "last");
                break;
            case R.id.tvOrder:
                WebActivity.launchWebActivity(this, Config.shequguifan, "社区规范", "last");
                break;
            case R.id.activity_login_toLogin:
                LoginWithPwdActivity.lunchActivity(LoginActivity.this, null, LoginWithPwdActivity.class);
                break;

        }
    }

    /***
     * 登陆或者注册
     */
    private void loginOrRegiser() {
        if (isRegister) {
            registerWithAuthCode();
        } else {
            loginWithAuthCode();
        }

    }

    /****
     * 验证码注册
     */
    private void registerWithAuthCode() {
        final String account = meditText.getText().toString();
        String sms = editYzm.getText().toString();
        if (TextUtils.isEmpty(account) || account.length() != 11) {
            Toasty.warning(this, "请输入11位手机号码").show();
            return;
        }

        if (TextUtils.isEmpty(sms) || sms.length() != 4) {
            Toasty.warning(this, "请输入4位数字验证码").show();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("authCode", sms);
        params.put("phone", account);
        params.put("source", "android-app");
        ABHttp.getIns().postJSON(UrlConfig.register, JsonUtils.toJson(params), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<RegisterResult>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                Toasty.info(LoginActivity.this, msg).show();
                if (success) {
                    RegisterResult registerResult = (RegisterResult) extra.get("result");
                    if (registerResult != null) {
                        WebUserBean webUserBean = new WebUserBean();
                        webUserBean.setLoginId(registerResult.getLoginId());
                        webUserBean.setUid(registerResult.getId());
                        webUserBean.setLoginType(registerResult.getLoginType());
                        UserDataManager.getInstance().putLoginUser(webUserBean);
                        boundAliAccount(account);
                        hideKeyBoard();
                        ChooseGenderActivity.lunchActivity(LoginActivity.this, null, ChooseGenderActivity.class);
                        LoginActivity.this.finish();
                    }
                }
            }
        });


    }


    /***
     * 检查用户是否注册
     */
    private void checkRegister() {
        final String account = meditText.getText().toString();
        HashMap<String, String> param = new HashMap<>();
        param.put("phone", account);
        ABHttp.getIns().postJSON(UrlConfig.registerCheck, JsonUtils.toJson(param), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    boolean register = (boolean) extra.get("result");
                    isRegister = register;
                    sendAuthCode(account, isRegister);
                } else {
                    Toasty.error(LoginActivity.this, msg).show();
                }
            }
        });


    }

    /***
     * 短信验证码登陆
     */
    private void loginWithAuthCode() {
        final String account = meditText.getText().toString();
        String sms = editYzm.getText().toString();
        if (TextUtils.isEmpty(account) || account.length() != 11) {
            Toasty.warning(this, "请输入11位手机号码").show();
            return;
        }
        if (TextUtils.isEmpty(sms) || sms.length() != 4) {
            Toasty.warning(this, "请输入4位数字验证码").show();
            return;
        }
        HashMap map = new HashMap();
        map.put("authCode", sms);
        map.put("loginType", "code");
        map.put("account", account);
        ABHttp.getIns().postJSON(UrlConfig.login, new Gson().toJson(map), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                entity.putField("result", new TypeToken<WebUserBean>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean isSuccess, HashMap<String, Object> extra) {
                Toasty.info(LoginActivity.this, msg).show();
                if (isSuccess) {
                    final WebUserBean loginInfoBean = (WebUserBean) extra.get("result");
                    if (loginInfoBean != null) {
                        boundAliAccount(account);
                        hideKeyBoard();
                        UserDataManager.getInstance().putLoginUser(loginInfoBean);
                        LoginActivity.this.finish();
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


    /***
     * 发送验证码
     */
    private void sendAuthCode(String phone, boolean isRegister) {
        timer.schedule(task, 1000, 1000);
        LinkedHashMap<String, String> param = new LinkedHashMap<>();
        param.put("type", isRegister ? "reg" : "login");
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
                if (success) {
                    editYzm.setFocusable(true);
                    editYzm.setFocusableInTouchMode(true);
                    editYzm.requestFocus();
                }
                Toasty.info(LoginActivity.this, msg).show();
            }

            @Override
            public boolean onFailure(String code, String msg) {
                return super.onFailure(code, msg);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
