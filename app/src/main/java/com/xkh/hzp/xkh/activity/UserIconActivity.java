package com.xkh.hzp.xkh.activity;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.tuSDK.RichEditComponentSample;
import com.xkh.hzp.xkh.tuSDK.TuMutipleHandle;

import java.io.File;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName UserIconActivity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class UserIconActivity extends BaseActivity {

    private ImageView userIcon;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_icon;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("修改头像");
        setRightImage(R.mipmap.btn_overflow_userinfo);
        userIcon = findViewById(R.id.activity_user_icon);
    }

    @Override
    protected void callbackOnclickRightMenu(View view) {
        super.callbackOnclickRightMenu(view);
        RichEditComponentSample richEditComponentSample = new RichEditComponentSample();
        richEditComponentSample.setTuMutipleHandle(new TuMutipleHandle() {
            @Override
            public void onMultipleTuSuccess(List<String> images) {
                if (images != null && images.size() > 0) {
                    Glide.with(UserIconActivity.this).load(new File(images.get(0))).placeholder(R.drawable.tangle_icon).error(R.drawable.tangle_icon).into(userIcon);
                }
            }

            @Override
            public void onFail(String msg) {
                System.out.println("");
            }
        });
        richEditComponentSample.showSample(UserIconActivity.this, 1);
    }

    @Override
    public void setListenner() {

    }
}
