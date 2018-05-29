package com.xkh.hzp.xkh.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.xkh.hzp.xkh.R;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
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
//                            loadData(nickName);
                        } else {
                            Toasty.warning(UserNickNameActivity.this, "昵称不能为空").show();
                            finish();
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void callbackOnClickNavigationAction(View view) {
        super.callbackOnClickNavigationAction(view);
        String s = nickNameEdit.getText().toString().trim();
        if (!TextUtils.isEmpty(s)) {
//            loadData(s);
        } else {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void setListenner() {

    }
}
