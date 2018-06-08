package com.xkh.hzp.xkh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xkh.hzp.xkh.fragment.AttentionFragment;
import com.xkh.hzp.xkh.fragment.IndexDynamicFragment;
import com.xkh.hzp.xkh.fragment.TalentDynamicFragment;
import com.xkh.hzp.xkh.fragment.VideoFragment;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName DynamicFragmentPagerAdapter
 * @Author tangyang
 * @DATE 2018/6/6
 **/
public class DynamicFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] TITLES = {"达人动态", "视频", "关注"};

    public DynamicFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IndexDynamicFragment();
            case 1:
                return new VideoFragment();
            case 2:
                return new AttentionFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
