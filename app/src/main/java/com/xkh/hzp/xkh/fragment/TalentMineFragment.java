package com.xkh.hzp.xkh.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.MineFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnFlingOverListener;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;
import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.view.PagerSlidingTabStrip;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName TalentMineFragment
 * @Author tangyang
 * @DATE 2018/5/8
 **/
public class TalentMineFragment extends BaseFragment {

    private ScrollableLayout talentMineScrollableLayout;
    private ImageView talentMineSettingImg, talentMineMsgImg, talentMineEditImg;
    private PagerSlidingTabStrip talentMinePagerSlidingTabStrip;
    private ViewPager talentMineViewPager;
    private RelativeLayout barLayout;
    private TextView mineNickNameTxt;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_talent_mine;
    }

    @Override
    public void initView(View contentView) {
        talentMineScrollableLayout = contentView.findViewById(R.id.talentMineScrollableLayout);
        talentMineSettingImg = contentView.findViewById(R.id.talentMineSettingImg);
        talentMineMsgImg = contentView.findViewById(R.id.talentMineMsgImg);
        talentMineEditImg = contentView.findViewById(R.id.talentMineEditImg);
        barLayout = contentView.findViewById(R.id.barLayout);
        mineNickNameTxt = contentView.findViewById(R.id.mineNickNameTxt);
        talentMinePagerSlidingTabStrip = contentView.findViewById(R.id.talentMinePagerSlidingTabStrip);
        talentMineViewPager = contentView.findViewById(R.id.talentMineViewPager);

        MineFragmentPagerAdapter dynimicPagerAdapter = new MineFragmentPagerAdapter(getChildFragmentManager(), items());
        talentMineViewPager.setAdapter(dynimicPagerAdapter);
        talentMinePagerSlidingTabStrip.setViewPager(talentMineViewPager);

        talentMineScrollableLayout.setDraggableView(talentMinePagerSlidingTabStrip);
        final CurrentFragment currentFragment = new CurrentFragmentImpl(talentMineViewPager, getChildFragmentManager());
        talentMineScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                final FragmentPagerFragment fragment = currentFragment.currentFragment();
                return fragment != null && fragment.canScrollVertically(direction);
            }
        });

        talentMineScrollableLayout.setOnFlingOverListener(new OnFlingOverListener() {
            @Override
            public void onFlingOver(int y, long duration) {
                final FragmentPagerFragment fragment = currentFragment.currentFragment();
                if (fragment != null) {
                    fragment.onFlingOver(y, duration);
                }
            }
        });

        talentMineScrollableLayout.addOnScrollChangedListener(new OnScrollChangedListener() {

            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {

                Log.d("xkh", "maxY===" + maxY + "oldY=====" + oldY + "y=====" + y);
                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }
                talentMinePagerSlidingTabStrip.setTranslationY(tabsTranslationY);
//                int bannerHeight = sampleHeaderView.getHeight();
//                if (y >= bannerHeight) {
//                    searchLayout.setVisibility(View.VISIBLE);
//                    YoYo.with(Techniques.FadeInDown).playOn(searchLayout);
//                } else {
//
//                    if (y < oldY) {
//
//                    }
//                    searchLayout.setVisibility(View.GONE);
//                    YoYo.with(Techniques.FadeOutDown).playOn(searchLayout);
//                }

            }
        });

    }

    private interface CurrentFragment {
        @Nullable
        FragmentPagerFragment currentFragment();
    }


    private static class CurrentFragmentImpl implements CurrentFragment {
        private final ViewPager mViewPager;
        private final FragmentManager mFragmentManager;
        private final FragmentPagerAdapter mAdapter;

        CurrentFragmentImpl(ViewPager pager, FragmentManager manager) {
            mViewPager = pager;
            mFragmentManager = manager;
            mAdapter = (FragmentPagerAdapter) pager.getAdapter();
        }

        @Override
        @Nullable
        public FragmentPagerFragment currentFragment() {
            final FragmentPagerFragment out;
            final int position = mViewPager.getCurrentItem();
            if (position < 0
                    || position >= mAdapter.getCount()) {
                out = null;
            } else {
                final String tag = makeFragmentName(mViewPager.getId(), mAdapter.getItemId(position));
                final Fragment fragment = mFragmentManager.findFragmentByTag(tag);
                if (fragment != null) {
                    out = (FragmentPagerFragment) fragment;
                } else {
                    // fragment is still not attached
                    out = null;
                }
            }
            return out;
        }

        private static String makeFragmentName(int viewId, long id) {
            return "android:switcher:" + viewId + ":" + id;
        }
    }

    private static List<MineFragmentPagerAdapter.Item> items() {
        final List<MineFragmentPagerAdapter.Item> items = new ArrayList<>(2);
        items.add(new MineFragmentPagerAdapter.Item("动态",
                new MineFragmentPagerAdapter.Provider() {
                    @Override
                    public Fragment provide() {
                        return new TalentDynamicFragment();
                    }
                }
        ));
        items.add(new MineFragmentPagerAdapter.Item(
                "资料",
                new MineFragmentPagerAdapter.Provider() {
                    @Override
                    public Fragment provide() {
                        return new PeopleProfileFragment();
                    }
                }
        ));
        return items;
    }


    @Override
    public void setListernner() {

    }
}
