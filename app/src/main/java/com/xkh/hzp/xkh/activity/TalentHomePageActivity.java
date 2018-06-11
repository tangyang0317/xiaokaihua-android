package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.HomePageFragmentPagerAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;
import java.util.LinkedHashMap;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName TalentHomePageActivity
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class TalentHomePageActivity extends BaseActivity {

    private ImageView talentHeadImg;
    private TextView talentNickNameTxt, talentSignTxt;
    private TabLayout homePageTabLayout;
    private ViewPager talentMineViewPager;
    private RelativeLayout barLayout;

    public static void lanuchActivity(Activity activity, String talentUserId) {
        Intent intent = new Intent(activity, TalentHomePageActivity.class);
        intent.putExtra("talentUserId", talentUserId);
        activity.startActivity(intent);
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    public String getTalentUserId() {
        return getIntent().getStringExtra("talentUserId");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_talent_homepage;
    }

    @Override
    public void initView() {
        hideToolbar();
        talentHeadImg = findViewById(R.id.talentHeadImg);
        talentNickNameTxt = findViewById(R.id.talentNickNameTxt);
        talentSignTxt = findViewById(R.id.talentSignTxt);
        barLayout = findViewById(R.id.barLayout);
        homePageTabLayout = findViewById(R.id.homePageTabLayout);
        talentMineViewPager = findViewById(R.id.talentMineViewPager);
        HomePageFragmentPagerAdapter homePageFragmentPagerAdapter = new HomePageFragmentPagerAdapter(getSupportFragmentManager(), getTalentUserId());
        talentMineViewPager.setAdapter(homePageFragmentPagerAdapter);
        homePageTabLayout.setupWithViewPager(talentMineViewPager);
        queryUserInfo();
    }

    /***
     * 设置用户UI数据
     * @param userInfoResult
     */
    private void setUIData(UserInfoResult userInfoResult) {
        if (userInfoResult != null) {
            talentNickNameTxt.setText(userInfoResult.getName());
            talentSignTxt.setText("" + userInfoResult.getPersonSignature());
            Glide.with(this).load(userInfoResult.getHeadPortrait()).transform(new GlideCircleTransform(this)).placeholder(R.mipmap.icon_female_selected).error(R.mipmap.icon_female_selected).into(talentHeadImg);
        }
    }

    /***
     *查询用户信息
     */
    private void queryUserInfo() {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("userId", getTalentUserId());
        ABHttp.getIns().restfulGet(UrlConfig.queryuserInfo, hashMap, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<UserInfoResult>() {
                }.getType());
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    UserInfoResult userInfoResult = (UserInfoResult) extra.get("result");
                    setUIData(userInfoResult);
                }
            }
        });
    }


    @Override
    public void setListenner() {

    }
}
