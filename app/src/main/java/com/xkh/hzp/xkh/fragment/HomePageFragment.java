package com.xkh.hzp.xkh.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.HomePageFragmentPagerAdapter;

import xkh.hzp.xkh.com.base.base.BaseFragment;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName HomePageFragment
 * @Author tangyang
 * @DATE 2018/6/7
 **/
public class HomePageFragment extends BaseFragment {

    private TabLayout homePageTabLayout;
    private ViewPager talentMineViewPager;
    private RelativeLayout barLayout;
    private TextView mineNickNameTxt;

    public static Fragment getInstance(String talentId) {
        HomePageFragment homePageFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("talentId", talentId);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }

    private String getTalentUserId() {
        return getArguments().getString("talentId");
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_talent_homepage;
    }

    @Override
    public void initView(View contentView) {
        barLayout = contentView.findViewById(R.id.barLayout);
        mineNickNameTxt = contentView.findViewById(R.id.mineNickNameTxt);
        homePageTabLayout = contentView.findViewById(R.id.homePageTabLayout);
        talentMineViewPager = contentView.findViewById(R.id.talentMineViewPager);
        HomePageFragmentPagerAdapter homePageFragmentPagerAdapter = new HomePageFragmentPagerAdapter(getChildFragmentManager(), getTalentUserId());
        talentMineViewPager.setAdapter(homePageFragmentPagerAdapter);
        homePageTabLayout.setupWithViewPager(talentMineViewPager);
    }

    @Override
    public void setListernner() {

    }
}
