package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName ModifyPasswordActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {

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
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_reset_pass_btnLogin:
                String oldPass = name.getText().toString();
                String nPass = pass.getText().toString();
                String mPassComfirm = passComfirm.getText().toString();
                if (TextUtils.isEmpty(oldPass)) {
                    Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(nPass)) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mPassComfirm)) {
                    Toast.makeText(this, "请再次确认新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mPassComfirm.equals(nPass)) {
                    Toast.makeText(this, "新密码和确认新密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                updatePassword(oldPass, nPass);

                break;
        }
    }

    /***
     * 修改密码
     * @param oldPass
     * @param nPass
     */
    private void updatePassword(String oldPass, String nPass) {

        HashMap<String, String> params = new HashMap<>();
        params.put("oldPasswd", oldPass);
        params.put("passwd", nPass);
        ABHttp.getIns().postJSON(UrlConfig.modifyPwd, JsonUtils.toJson(params), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    Toasty.info(ModifyPasswordActivity.this, "密码修改成功");
                    ModifyPasswordActivity.this.finish();
                }
            }
        });

    }
}
