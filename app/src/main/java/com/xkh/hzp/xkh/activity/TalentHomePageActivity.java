package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.xkh.hzp.xkh.view.AppBarStateChangeListener;

import java.util.HashMap;
import java.util.LinkedHashMap;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.PagerSlidingTabStrip;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName TalentHomePageActivity
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class TalentHomePageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView talentHeadImg, homePageBackImg, reportUserImg;
    private View spaceView;
    private TextView talentNickNameTxt, talentSignTxt, homePageTitleTxt;
    private PagerSlidingTabStrip homePageTabLayout;
    private ViewPager talentMineViewPager;
    private AppBarLayout homePageAppBarLayout;

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
        spaceView = findViewById(R.id.spaceView);
        homePageAppBarLayout = findViewById(R.id.homePageAppBarLayout);
        homePageBackImg = findViewById(R.id.homePageBackImg);
        homePageTitleTxt = findViewById(R.id.homePageTitleTxt);
        talentHeadImg = findViewById(R.id.talentHeadImg);
        talentNickNameTxt = findViewById(R.id.talentNickNameTxt);
        talentSignTxt = findViewById(R.id.talentSignTxt);
        homePageTabLayout = findViewById(R.id.homePageTabLayout);
        talentMineViewPager = findViewById(R.id.talentMineViewPager);
        reportUserImg = findViewById(R.id.reportUserImg);
        HomePageFragmentPagerAdapter homePageFragmentPagerAdapter = new HomePageFragmentPagerAdapter(getSupportFragmentManager(), getTalentUserId());
        talentMineViewPager.setAdapter(homePageFragmentPagerAdapter);
        homePageTabLayout.setViewPager(talentMineViewPager);
        if (getTalentUserId().equals(UserDataManager.getInstance().getUserId())) {
            reportUserImg.setVisibility(View.GONE);
        }
        queryUserInfo();
    }

    /***
     * 设置用户UI数据
     * @param userInfoResult
     */
    private void setUIData(UserInfoResult userInfoResult) {
        if (userInfoResult != null) {
            talentNickNameTxt.setText(userInfoResult.getName());
            homePageTitleTxt.setText(userInfoResult.getName());
            talentSignTxt.setText("" + userInfoResult.getPersonSignature());
            Glide.with(this).load(userInfoResult.getHeadPortrait()).transform(new GlideCircleTransform(this)).error(R.mipmap.icon_female_selected).into(talentHeadImg);
        }
    }

    /***
     *查询用户信息
     */
    private void queryUserInfo() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", getTalentUserId());
        ABHttp.getIns().get(UrlConfig.queryuserInfo, hashMap, new AbHttpCallback() {
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
        homePageAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    spaceView.setVisibility(View.GONE);
                    homePageTitleTxt.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    spaceView.setVisibility(View.VISIBLE);
                    homePageTitleTxt.setVisibility(View.VISIBLE);
                } else {
                    //中间状态

                }
            }
        });

        homePageBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TalentHomePageActivity.this.finish();
            }
        });

        reportUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportActivity.lanuchActivity(TalentHomePageActivity.this, "user", 0, Long.parseLong(getTalentUserId()));
            }
        });
    }

    @Override
    public void onClick(View view) {
    }
}
