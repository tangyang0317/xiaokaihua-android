package com.xkh.hzp.xkh.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * 修改用户信息
 *
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName UserInfoActvity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class UserInfoActvity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout userIconLayout, userIdlayout, userNickName, userGenderLayout, userSignLayout;
    private TextView userSignTxt, userIdTxt, userNickNameTxt, userGenderTxt;
    private ImageView userIconImg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        userIconLayout = findViewById(R.id.activity_user_info_icon_rl);
        userIdlayout = findViewById(R.id.activity_user_info_id_rl);
        userNickName = findViewById(R.id.activity_user_info_nick_rl);
        userGenderLayout = findViewById(R.id.activity_user_info_sex_rl);
        userSignLayout = findViewById(R.id.activity_user_info_sign_rl);
        userSignTxt = findViewById(R.id.activity_user_info_sign_content);
        userGenderTxt = findViewById(R.id.activity_user_info_sex_content);
        userIdTxt = findViewById(R.id.activity_user_info_id_content);
        userNickNameTxt = findViewById(R.id.activity_user_info_nick_content);
        userIconImg = findViewById(R.id.activity_user_info_icon_img);
    }

    @Override
    public void setListenner() {
        userIconLayout.setOnClickListener(this);
        userNickName.setOnClickListener(this);
        userGenderLayout.setOnClickListener(this);
        userSignLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == userIconLayout) {
            UserIconActivity.lunchActivity(UserInfoActvity.this, null, UserIconActivity.class);
        } else if (view == userNickName) {
            UserNickNameActivity.lunchActivity(UserInfoActvity.this, null, UserNickNameActivity.class);
        } else if (view == userGenderLayout) {
            ChooseGenderActivity.lunchActivity(UserInfoActvity.this, null, ChooseGenderActivity.class);
        } else if (view == userSignLayout) {
            UserSignActivity.lunchActivity(UserInfoActvity.this, null, UserSignActivity.class);
        }
    }
}
