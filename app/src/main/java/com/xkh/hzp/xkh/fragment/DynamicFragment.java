package com.xkh.hzp.xkh.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.view.PagerSlidingTabStrip;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class DynamicFragment extends BaseFragment {

    private PagerSlidingTabStrip dynamicPagerSlidingTabStrip;
    private ViewPager dynamicViewPager;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void initView(View contentView) {
        dynamicPagerSlidingTabStrip = contentView.findViewById(R.id.dynamicPagerSlidingTabStrip);
        dynamicViewPager = contentView.findViewById(R.id.dynamicViewPager);
        dynamicViewPager.setAdapter(new DynamicPagerAdapter(getChildFragmentManager()));
        dynamicPagerSlidingTabStrip.setViewPager(dynamicViewPager);

    }

    @Override
    public void setListernner() {

    }

    class DynamicPagerAdapter extends FragmentPagerAdapter {

        String[] title = {"图片", "视频", "其他"};

        public DynamicPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return new TalentFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
