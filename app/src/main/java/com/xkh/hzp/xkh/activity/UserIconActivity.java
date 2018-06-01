package com.xkh.hzp.xkh.activity;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.event.UpdateUserInfoEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.tuSDK.RichEditComponentSample;
import com.xkh.hzp.xkh.tuSDK.TuMutipleHandle;
import com.xkh.hzp.xkh.upload.OnUploadListener;
import com.xkh.hzp.xkh.upload.UploadImageManager;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.view.UILoadingView;

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
        if (UserDataManager.getInstance().getUserInfo() != null) {
            String headPic = UserDataManager.getInstance().getUserInfo().getHeadPortrait();
            Glide.with(this).load(headPic).transform(new GlideCircleTransform(this)).placeholder(R.mipmap.icon_female_selected).error(R.mipmap.icon_female_selected).into(userIcon);
        }
    }

    @Override
    protected void callbackOnclickRightMenu(View view) {
        super.callbackOnclickRightMenu(view);

    }

    @Override
    public void setListenner() {

    }
}
