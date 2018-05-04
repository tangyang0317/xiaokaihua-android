package com.xkh.hzp.xkh.fragment;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * Fragment创建工厂类
 * Created by tangyang on 18/4/14.
 */

public class FragmentFactory {

    private static final int FRAGMENT_PINGGU = 0;
    private static final int FRAGMENT_ZUJIN = 1;
    private static final int FRAGMENT_MINE = 2;

    //SparseArray:key是整形的key,根据整形的key来获取一个对象,效率比较高.
    private static SparseArray<Fragment> fragments = new SparseArray<>();

    //创建一个工厂方法,用来创建一个Fragment对象
    public static Fragment createFragment(int index) {
        //从集合中获取
        Fragment fragment = fragments.get(index);
        if (fragment == null) {
            switch (index) {
                case FRAGMENT_PINGGU:
                    fragment = new DynamicFragment();
                    break;
                case FRAGMENT_ZUJIN:
                    fragment = new TalentFragment();
                    break;
                case FRAGMENT_MINE:
                    fragment = new MineFragment();
                    break;
            }
            //存到集合中
            fragments.put(index, fragment);
        }
        return fragment;
    }
}
