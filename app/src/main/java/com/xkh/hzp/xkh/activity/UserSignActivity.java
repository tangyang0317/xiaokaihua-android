package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseActivity;
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
//            loadData(trim);
        } else {
            finish();
        }
    }

    @Override
    public void initView() {
        setToolbarTitleTv("美丽签名");
        userSignEdit = findViewById(R.id.activity_user_sign_name);
        userSignEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_NEXT:
                        String signure = v.getText().toString().trim();
                        if (signure != null && !TextUtils.isEmpty(signure)) {
//                            loadData(signure);
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void setListenner() {

    }
}
