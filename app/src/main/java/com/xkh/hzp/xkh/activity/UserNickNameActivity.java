package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

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
 * @FileName UserNickNameActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class UserNickNameActivity extends BaseActivity {

    private EditTextWithDel nickNameEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_nick_name;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("用户昵称");
        nickNameEdit = findViewById(R.id.activity_user_name_name);
        nickNameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_NEXT:
                        String nickName = v.getText().toString().trim();
                        if (nickName != null && !nickName.isEmpty()) {
                            completeInfo(nickName);
                        } else {
                            Toasty.warning(UserNickNameActivity.this, "昵称不能为空").show();
                            finish();
                        }
                        break;
                }
                return false;
            }
        });

        if (UserDataManager.getInstance().getUserInfo() != null) {
            nickNameEdit.setText(UserDataManager.getInstance().getUserInfo().getName());
        } else {
            nickNameEdit.setHint("取个名字");
        }

    }

    @Override
    protected void callbackOnClickNavigationAction(View view) {
        super.callbackOnClickNavigationAction(view);
        String s = nickNameEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(s)) {
            completeInfo(s);
        } else {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /***
     * 完善资料
     **/
    private void completeInfo(final String nickName) {
        final String userId = UserDataManager.getInstance().getUserId();
        HashMap param = new HashMap();
        param.put("nickname", nickName);
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
                Toasty.info(UserNickNameActivity.this, msg).show();
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    if (result) {
                        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
                        userInfoResult.setName(nickName);
                        UserDataManager.getInstance().saveUserInfo(userInfoResult);
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
                        hideKeyBoard();
                        UserNickNameActivity.this.finish();
                    }
                }
            }
        });

    }


    @Override
    public void setListenner() {

    }
}
