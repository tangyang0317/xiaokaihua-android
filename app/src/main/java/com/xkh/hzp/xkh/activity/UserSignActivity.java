package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.event.UpdateUserInfoEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.view.EditTextWithDel;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName UserSignActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class UserSignActivity extends BaseActivity {

    private EditTextWithDel userSignEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_sign;
    }

    @Override
    protected void callbackOnClickNavigationAction(View view) {
        super.callbackOnClickNavigationAction(view);
        String trim = userSignEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            completeInfo(trim);
        } else {
            finish();
        }
    }

    @Override
    public void initView() {
        setToolbarTitleTv("美丽签名");
        userSignEdit = findViewById(R.id.activity_user_sign_name);
        if (UserDataManager.getInstance().getUserInfo() == null || TextUtils.isEmpty(UserDataManager.getInstance().getUserInfo().getPersonSignature())) {
            userSignEdit.setHint("做个有气质的小姐姐");
        } else {
            userSignEdit.setText(UserDataManager.getInstance().getUserInfo().getPersonSignature());
            userSignEdit.setSelection(UserDataManager.getInstance().getUserInfo().getPersonSignature().length());
        }
        userSignEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_NEXT:
                        String signure = v.getText().toString().trim();
                        if (signure != null && !TextUtils.isEmpty(signure)) {
                            completeInfo(signure);
                        }
                        break;
                }
                return false;
            }
        });
    }


    /***
     * 完善资料
     **/
    private void completeInfo(final String userSign) {
        final String userId = UserDataManager.getInstance().getUserId();
        HashMap param = new HashMap();
        param.put("personSignature", userSign);
        param.put("userId", userId);
        ABHttp.getIns().postJSON(UrlConfig.userInfo + "?userId=" + userId, JsonUtils.toJson(param), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                Toasty.info(UserSignActivity.this, msg).show();
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    if (result) {
                        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
                        userInfoResult.setPersonSignature(userSign);
                        UserDataManager.getInstance().saveUserInfo(userInfoResult);
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
                        hideKeyBoard();
                        UserSignActivity.this.finish();
                    }
                }
            }
        });

    }


    @Override
    public void setListenner() {

    }
}
