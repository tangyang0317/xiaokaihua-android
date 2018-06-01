package com.xkh.hzp.xkh.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName ChooseGenderActivity
 * @Author tangyang
 * @DATE 2018/5/16
 **/
public class UpdateGenderActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvMale, tvFemale;
    private ImageView ivFemale;
    private ImageView ivMale;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_gender;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("修改性别");
        ivFemale = findViewById(R.id.activity_medetail_female_iv);
        ivMale = findViewById(R.id.activity_medetail_male_iv);
        tvMale = findViewById(R.id.activity_medetail_male);
        tvFemale = findViewById(R.id.activity_medetail_female);
        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
        if (userInfoResult != null) {
            if ("0".equals(userInfoResult.getSex())) {
                ivMale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_male));
                ivFemale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_female_selected));
                tvFemale.setTextColor(getResources().getColor(R.color.color_ff5555));
                tvMale.setTextColor(getResources().getColor(R.color.color_666666));
            } else if ("1".equals(userInfoResult.getSex())) {
                ivMale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_male_selected));
                ivFemale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_female));
                tvMale.setTextColor(getResources().getColor(R.color.color_ff5555));
                tvFemale.setTextColor(getResources().getColor(R.color.color_666666));
            }
        }

    }


    @Override
    public void setListenner() {
        ivMale.setOnClickListener(this);
        ivFemale.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == ivFemale) {
            ivMale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_male));
            ivFemale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_female_selected));
            tvFemale.setTextColor(getResources().getColor(R.color.color_ff5555));
            tvMale.setTextColor(getResources().getColor(R.color.color_666666));
            completeInfo(0);
        } else if (view == ivMale) {
            ivMale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_male_selected));
            ivFemale.setImageDrawable(getResources().getDrawable(R.mipmap.icon_female));
            tvMale.setTextColor(getResources().getColor(R.color.color_ff5555));
            tvFemale.setTextColor(getResources().getColor(R.color.color_666666));
            completeInfo(1);
        }
    }

    /***
     * 完善资料
     **/
    private void completeInfo(final int gender) {
        String userId = UserDataManager.getInstance().getUserId();
        HashMap param = new HashMap();
        param.put("sex", String.valueOf(gender));
        param.put("userId", userId);
        ABHttp.getIns().postJSON(UrlConfig.userInfo + "?userId=" + userId, JsonUtils.toJson(param), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                Toasty.info(UpdateGenderActivity.this, msg).show();
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    if (result) {
                        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
                        userInfoResult.setSex(String.valueOf(gender));
                        UserDataManager.getInstance().saveUserInfo(userInfoResult);
                        EventBus.getDefault().post(new UpdateUserInfoEvent());
                        UpdateGenderActivity.this.finish();
                    }
                }
            }
        });

    }
}
