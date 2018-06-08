package com.xkh.hzp.xkh.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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
import com.xkh.hzp.xkh.adapter.DynamicFragmentPagerAdapter;
import com.xkh.hzp.xkh.adapter.MineFragmentPagerAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.BannerResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;
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
public class DynamicFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout searchLayout;
    private Banner sampleHeaderView;
    private ViewPager dynamicViewPager;
    private TabLayout dynamicTabLayout;
    private SectorMenuButton bottomMenuButton;
    private List<BannerResult> bannerResultList;

    @Override
    public void onClick(View view) {
        if (view == searchLayout) {
            SearchHistoryActivty.openActivity(getActivity(), 0);
        }
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void initView(View contentView) {
        sampleHeaderView = contentView.findViewById(R.id.headerBanner);
        dynamicViewPager = contentView.findViewById(R.id.dynamicViewPager);
        dynamicTabLayout = contentView.findViewById(R.id.dynamicTabLayout);
        searchLayout = contentView.findViewById(R.id.searchLayout);
        bottomMenuButton = contentView.findViewById(R.id.bottomMenuButton);
        DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getChildFragmentManager());
        dynamicViewPager.setAdapter(dynamicFragmentPagerAdapter);
        dynamicTabLayout.setupWithViewPager(dynamicViewPager);

        if (UserDataManager.getInstance().getUserInfo() != null && "talent".equals(UserDataManager.getInstance().getUserInfo().getUserType())) {
            bottomMenuButton.setVisibility(View.VISIBLE);
            final List<ButtonData> buttonDatas = new ArrayList<>();
            int[] drawable = {R.mipmap.icon_publish_dynamic, R.mipmap.icon_vedio, R.mipmap.icon_img_text};
            for (int i = 0; i < 3; i++) {
                //最后一个参数表示padding
                ButtonData buttonData = ButtonData.buildIconButton(getActivity(), drawable[i], 0);
                buttonData.setBackgroundColorId(getActivity(), R.color.colorAccent);
                buttonDatas.add(buttonData);
            }
            bottomMenuButton.setButtonDatas(buttonDatas);
        } else {
            bottomMenuButton.setVisibility(View.GONE);
        }
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
