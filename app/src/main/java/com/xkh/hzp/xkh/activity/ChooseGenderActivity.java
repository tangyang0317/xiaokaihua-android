package com.xkh.hzp.xkh.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName ChooseGenderActivity
 * @Author tangyang
 * @DATE 2018/5/16
 **/
public class ChooseGenderActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvMale, tvFemale;
    private ImageView ivFemale;
    private ImageView ivMale;
    private Button button;
    private String gender = "W";
    private String headPic = "http://xkh-cdn.007fenqi.com/icon_female_selected.png";

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_gender;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.5f).statusBarColor(R.color.color_ffffff).init();
    }

    @Override
    public void initView() {
        ivFemale = findViewById(R.id.activity_medetail_female_iv);
        ivMale = findViewById(R.id.activity_medetail_male_iv);
        tvMale = findViewById(R.id.activity_medetail_male);
        tvFemale = findViewById(R.id.activity_medetail_female);
        button = findViewById(R.id.button);
    }

    @Override
    public void setListenner() {
        ivMale.setOnClickListener(this);
        ivFemale.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ivFemale) {
            ivMale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_male));
            ivFemale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_female_selected));
            gender = "W";
            headPic = "http://xkh-cdn.007fenqi.com/icon_female_selected.png";
        } else if (view == ivMale) {
            ivMale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_male_selected));
            ivFemale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_female));
            gender = "M";
            headPic = "http://xkh-cdn.007fenqi.com/icon_male_selected.png";
        } else if (view == button) {
            completeInfo();
        }
    }

    private void completeInfo() {
    }
}
