package com.xkh.hzp.xkh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xkh.hzp.xkh.fragment.PeopleProfileFragment;
import com.xkh.hzp.xkh.fragment.TalentDynamicFragment;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName HomePageFragmentPagerAdapter
 * @Author tangyang
 * @DATE 2018/6/6
 **/
public class HomePageFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] TITLES = {"动态", "资料"};
    private String talentUserId;

    public HomePageFragmentPagerAdapter(FragmentManager fm, String talentUserId) {
        super(fm);
        this.talentUserId = talentUserId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TalentDynamicFragment.getInstance(talentUserId);
            case 1:
                return PeopleProfileFragment.getInstance(talentUserId);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
