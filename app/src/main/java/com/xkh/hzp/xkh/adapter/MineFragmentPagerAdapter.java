package com.xkh.hzp.xkh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName MineFragmentPagerAdapter
 * @Author tangyang
 * @DATE 2018/5/5
 **/
public class MineFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    public interface Provider {
        Fragment provide();
    }

    public static class Item {

        final String name;
        final Provider provider;

        public Item(String name, Provider provider) {
            this.name = name;
            this.provider = provider;
        }
    }

    private final List<Item> mItems;

    public MineFragmentPagerAdapter(FragmentManager fm, List<Item> items) {
        super(fm);
        mItems = items;
    }

    @Override
    public Fragment getItem(int position) {
        return mItems.get(position).provider.provide();
    }

    @Override
    public int getCount() {
        return mItems != null
                ? mItems.size()
                : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItems.get(position).name;
    }


}
