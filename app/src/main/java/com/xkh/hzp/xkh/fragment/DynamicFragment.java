package com.xkh.hzp.xkh.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.PublishPictureTextActvity;
import com.xkh.hzp.xkh.activity.PublishVideoActivity;
import com.xkh.hzp.xkh.activity.SearchHistoryActivty;
import com.xkh.hzp.xkh.adapter.MineFragmentPagerAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.BannerResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnFlingOverListener;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;
import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.view.PagerSlidingTabStrip;
import xkh.hzp.xkh.com.base.view.sectorMenu.ButtonData;
import xkh.hzp.xkh.com.base.view.sectorMenu.ButtonEventListener;
import xkh.hzp.xkh.com.base.view.sectorMenu.SectorMenuButton;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class DynamicFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout dynamicSwipRefreshLayout;
    private ScrollableLayout scrollableLayout;
    private LinearLayout searchLayout;
    private Banner sampleHeaderView;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabsLayout;
    private SectorMenuButton bottomMenuButton;
    private List<BannerResult> bannerResultList;

    @Override
    public void onClick(View view) {
        if (view == searchLayout) {
            SearchHistoryActivty.openActivity(getActivity(), 0);
        }
    }

    @Override
    public void onRefresh() {

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
        dynamicSwipRefreshLayout = contentView.findViewById(R.id.dynamicSwipRefreshLayout);
        scrollableLayout = contentView.findViewById(R.id.scrollable_layout);
        sampleHeaderView = contentView.findViewById(R.id.headerBanner);
        viewPager = contentView.findViewById(R.id.view_pager);
        tabsLayout = contentView.findViewById(R.id.tabs);
        searchLayout = contentView.findViewById(R.id.searchLayout);
        bottomMenuButton = contentView.findViewById(R.id.bottomMenuButton);
        dynamicSwipRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_ff5555));
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


        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.icon_publish_dynamic, R.mipmap.icon_vedio, R.mipmap.icon_img_text};
        for (int i = 0; i < 3; i++) {
            //最后一个参数表示padding
            ButtonData buttonData = ButtonData.buildIconButton(getActivity(), drawable[i], 0);
            buttonData.setBackgroundColorId(getActivity(), R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        bottomMenuButton.setButtonDatas(buttonDatas);

        bottomMenuButton.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {

                if (index == 1) {
                    PublishVideoActivity.lunchActivity(getActivity(), null, PublishVideoActivity.class);
                } else if (index == 2) {
                    PublishPictureTextActvity.lunchActivity(getActivity(), null, PublishPictureTextActvity.class);
                }
            }

            @Override
            public void onExpand() {
            }

            @Override
            public void onCollapse() {
            }
        });


        initData();
    }

    /***
     * 加载数据
     */
    private void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("showPosition", "index");
        ABHttp.getIns().get(UrlConfig.banner, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<BannerResult>>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    List<BannerResult> bannerResults = (List<BannerResult>) extra.get("result");
                    bannerResultList = bannerResults;
                    if (bannerResults != null && bannerResults.size() > 0) {
                        sampleHeaderView.setImages(bannerResults).setIndicatorGravity(BannerConfig.RIGHT).isAutoPlay(true).setImageLoader(new GlideImageLoader()).start();
                    }
                }
            }
        });

    }


    @Override
    public void setListernner() {
        searchLayout.setOnClickListener(this);
        sampleHeaderView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (bannerResultList != null && bannerResultList.size() > 0) {
                    BannerResult bannerResult = bannerResultList.get(position);
                }
            }
        });
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
     * 加载banner的GlideImageLoader
     */
    public static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerResult bannerBean = (BannerResult) path;
            Glide.with(context).load(bannerBean.getImgUrl()).into(imageView);
        }
    }


}
