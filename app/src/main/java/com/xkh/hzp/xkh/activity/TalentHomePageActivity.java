package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.HomePageFragmentPagerAdapter;
import com.xkh.hzp.xkh.adapter.MineFragmentPagerAdapter;
import com.xkh.hzp.xkh.fragment.FragmentPagerFragment;
import com.xkh.hzp.xkh.fragment.PeopleProfileFragment;
import com.xkh.hzp.xkh.fragment.TalentDynamicFragment;

import java.util.ArrayList;
import java.util.List;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnFlingOverListener;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.PagerSlidingTabStrip;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName TalentHomePageActivity
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class TalentHomePageActivity extends BaseActivity {
    private TabLayout talentMinePagerSlidingTabStrip;
    private ViewPager talentMineViewPager;
    private RelativeLayout barLayout;
    private TextView mineNickNameTxt;

    public static void lanuchActivity(Activity activity, String talentUserId) {
        Intent intent = new Intent(activity, TalentHomePageActivity.class);
        intent.putExtra("talentUserId", talentUserId);
        activity.startActivity(intent);
    }

    public String getTalentUserId() {
        return getIntent().getStringExtra("talentUserId");
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.navigationBarAlpha(0).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_talent_homepage;
    }

    @Override
    protected void setBaseContainerBg() {
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }


    @Override
    public void initView() {
        hideToolbar();
        setToolbarTitleTv("达人主页");
        barLayout = findViewById(R.id.barLayout);
        mineNickNameTxt = findViewById(R.id.mineNickNameTxt);
        talentMinePagerSlidingTabStrip = findViewById(R.id.talentMinePagerSlidingTabStrip);
        talentMineViewPager = findViewById(R.id.talentMineViewPager);

        HomePageFragmentPagerAdapter homePageFragmentPagerAdapter = new HomePageFragmentPagerAdapter(getSupportFragmentManager(), getTalentUserId());
        talentMineViewPager.setAdapter(homePageFragmentPagerAdapter);
        talentMinePagerSlidingTabStrip.setupWithViewPager(talentMineViewPager);
    }

    @Override
    public void setListenner() {

    }
}
