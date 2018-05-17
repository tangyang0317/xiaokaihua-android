package com.xkh.hzp.xkh.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gyf.barlibrary.ImmersionBar;
import com.xkh.hzp.xkh.BannerBean;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.SearchHistoryActivty;
import com.xkh.hzp.xkh.adapter.MineFragmentPagerAdapter;
import com.xkh.hzp.xkh.http.RetrofitHttp;
import com.xkh.hzp.xkh.http.base.BaseEntity;
import com.xkh.hzp.xkh.http.base.BaseObserver;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnFlingOverListener;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;
import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.base.BaseLazyFragment;
import xkh.hzp.xkh.com.base.view.PagerSlidingTabStrip;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class DynamicFragment extends BaseFragment implements View.OnClickListener {

    private ScrollableLayout scrollableLayout;
    private LinearLayout searchLayout;
    private Banner sampleHeaderView;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabsLayout;

    @Override
    public void onClick(View view) {
        if (view == searchLayout) {
            SearchHistoryActivty.openActivity(getActivity(), 0);
        }
    }

    private interface CurrentFragment {
        @Nullable
        FragmentPagerFragment currentFragment();
    }


    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void initView(View contentView) {
        scrollableLayout = contentView.findViewById(R.id.scrollable_layout);
        sampleHeaderView = contentView.findViewById(R.id.headerBanner);
        viewPager = contentView.findViewById(R.id.view_pager);
        tabsLayout = contentView.findViewById(R.id.tabs);
        searchLayout = contentView.findViewById(R.id.searchLayout);
        MineFragmentPagerAdapter dynimicPagerAdapter = new MineFragmentPagerAdapter(getChildFragmentManager(), items());
        viewPager.setAdapter(dynimicPagerAdapter);
        tabsLayout.setViewPager(viewPager);
        scrollableLayout.setDraggableView(tabsLayout);
        final CurrentFragment currentFragment = new CurrentFragmentImpl(viewPager, getChildFragmentManager());
        scrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                final FragmentPagerFragment fragment = currentFragment.currentFragment();
                return fragment != null && fragment.canScrollVertically(direction);
            }
        });

        scrollableLayout.setOnFlingOverListener(new OnFlingOverListener() {
            @Override
            public void onFlingOver(int y, long duration) {
                final FragmentPagerFragment fragment = currentFragment.currentFragment();
                if (fragment != null) {
                    fragment.onFlingOver(y, duration);
                }
            }
        });

        scrollableLayout.addOnScrollChangedListener(new OnScrollChangedListener() {

            @Override
            public void onScrollChanged(int y, int oldY, int maxY) {
                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }
                tabsLayout.setTranslationY(tabsTranslationY);
            }
        });

        initData();
    }

    /***
     * 加载数据
     */
    private void initData() {

//        RetrofitHttp.getInstence().API().getBanner("index").compose(this.<BaseEntity<List<BannerBean>>>setThread()).subscribe(new BaseObserver<List<BannerBean>>() {
//            @Override
//            protected void onSuccees(BaseEntity<List<BannerBean>> t) throws Exception {
//                sampleHeaderView.setImages(t.getResult()).setIndicatorGravity(BannerConfig.RIGHT).isAutoPlay(true).setImageLoader(new GlideImageLoader()).start();
//            }
//
//            @Override
//            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//            }
//        });

    }


    @Override
    public void setListernner() {
        searchLayout.setOnClickListener(this);
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
        final List<MineFragmentPagerAdapter.Item> items = new ArrayList<>(4);
        items.add(new MineFragmentPagerAdapter.Item("动态",
                new MineFragmentPagerAdapter.Provider() {
                    @Override
                    public Fragment provide() {
                        return new TalentDynamicFragment();
                    }
                }
        ));
        items.add(new MineFragmentPagerAdapter.Item(
                "视频",
                new MineFragmentPagerAdapter.Provider() {
                    @Override
                    public Fragment provide() {
                        return new TalentDynamicFragment();
                    }
                }
        ));
        items.add(new MineFragmentPagerAdapter.Item(
                "关注",
                new MineFragmentPagerAdapter.Provider() {
                    @Override
                    public Fragment provide() {
                        return new TalentDynamicFragment();
                    }
                }
        ));

        return items;
    }


    /**
     * GlideImageLoader
     */
    public static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean bannerBean = (BannerBean) path;
            Glide.with(context).load(bannerBean.getImgUrl()).into(imageView);
        }
    }


}
