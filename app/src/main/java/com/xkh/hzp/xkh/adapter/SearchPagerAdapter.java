package com.xkh.hzp.xkh.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xkh.hzp.xkh.fragment.SearchDynamicFragment;
import com.xkh.hzp.xkh.fragment.SearchUserFragment;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName SearchPagerAdapter
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class SearchPagerAdapter extends FragmentPagerAdapter {

    private String search;

    public void setData(String search) {
        this.search = search;
    }

    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new SearchDynamicFragment();
        } else if (position == 1) {
            fragment = new SearchUserFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("search", search);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "动态";
            case 1:
                return "用户";
        }
        return null;
    }
}
