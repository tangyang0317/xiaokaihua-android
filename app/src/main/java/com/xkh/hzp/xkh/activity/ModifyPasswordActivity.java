package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.event.LogoutEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.RegExpValidatorUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName ModifyPasswordActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class ModifyPasswordActivity extends BaseActivity {

    private Button button;
    private TextView tvFor;
    private EditText name;
    private EditText pass;
    private EditText passComfirm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    public void initView() {
        setToolbarTitleTv("修改密码");
        button = findViewById(R.id.activity_reset_pass_btnLogin);
        tvFor = findViewById(R.id.activity_reset_pass_forgetPassword);
        name = findViewById(R.id.activity_reset_pass_name);
        pass = findViewById(R.id.activity_reset_pass_password);
        passComfirm = findViewById(R.id.activity_reset_pass_password_ag);
    }

    @Override
    public void setListenner() {
        RxView.clicks(button).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        updatePassword();
                    }
                });
    }


    /***
     * 修改密码
     */
    private void updatePassword() {
        String oldPass = name.getText().toString();
        String nPass = pass.getText().toString();
        String mPassComfirm = passComfirm.getText().toString();
        if (TextUtils.isEmpty(oldPass)) {
            Toasty.error(ModifyPasswordActivity.this, "旧密码不能为空").show();
            return;
        }

        if (!RegExpValidatorUtils.IsPasswLength(oldPass)) {
            Toasty.error(ModifyPasswordActivity.this, "旧密码长度为6-16位").show();
            return;
        }

        if (RegExpValidatorUtils.IsChinese(oldPass)) {
            Toasty.error(ModifyPasswordActivity.this, "旧密码不能是中文").show();
            return;
        }


        if (TextUtils.isEmpty(nPass)) {
            Toasty.error(ModifyPasswordActivity.this, "新密码不能为空").show();
            return;
        }

        if (!RegExpValidatorUtils.IsPasswLength(nPass)) {
            Toasty.error(ModifyPasswordActivity.this, "新密码长度为6-16位").show();
            return;
        }

        if (RegExpValidatorUtils.IsChinese(nPass)) {
            Toasty.error(ModifyPasswordActivity.this, "新密码不能是中文").show();
            return;
        }


        if (TextUtils.isEmpty(mPassComfirm)) {
            Toasty.error(ModifyPasswordActivity.this, "确认密码不能为空").show();
            return;
        }

        if (!RegExpValidatorUtils.IsPasswLength(mPassComfirm)) {
            Toasty.error(ModifyPasswordActivity.this, "确认密码长度为6-16位").show();
            return;
        }

        if (RegExpValidatorUtils.IsChinese(mPassComfirm)) {
            Toasty.error(ModifyPasswordActivity.this, "确认密码不能是中文").show();
            return;
        }

        if (!mPassComfirm.equals(nPass)) {
            Toast.makeText(this, "新密码和确认新密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("oldPasswd", oldPass);
        params.put("passwd", nPass);
        params.put("loginId", UserDataManager.getInstance().getLoginId());
        ABHttp.getIns().postJSON(UrlConfig.modifyPwd, JsonUtils.toJson(params), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                Toasty.info(ModifyPasswordActivity.this, msg).show();
                if (success) {
                    logout();
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
                        SharedprefrenceHelper.getIns(ModifyPasswordActivity.this).clear();
                        EventBus.getDefault().post(new LogoutEvent(true));
                        SettingActivity.instance.finish();
                        ModifyPasswordActivity.this.finish();
                    }
                }
            }
        });

    }

}
