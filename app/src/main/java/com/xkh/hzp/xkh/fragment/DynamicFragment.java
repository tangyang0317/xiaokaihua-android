package com.xkh.hzp.xkh.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.GraphicDynamicDetailsActivity;
import com.xkh.hzp.xkh.activity.LoginActivity;
import com.xkh.hzp.xkh.activity.MessageActivity;
import com.xkh.hzp.xkh.activity.PublishPictureTextActvity;
import com.xkh.hzp.xkh.activity.PublishVideoActivity;
import com.xkh.hzp.xkh.activity.SearchHistoryActivty;
import com.xkh.hzp.xkh.activity.VideoDynamicDetailsActivity;
import com.xkh.hzp.xkh.activity.WebActivity;
import com.xkh.hzp.xkh.adapter.DynamicFragmentPagerAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.BannerResult;
import com.xkh.hzp.xkh.entity.result.UnReadMsgResult;
import com.xkh.hzp.xkh.event.RefreshDotEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.CheckLoginManager;
import com.xkh.hzp.xkh.utils.DateUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;
import com.xkh.hzp.xkh.view.AppBarStateChangeListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
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
    private TextView searchLayout;
    private ImageView msgImg, msgDotImg;
    private Banner sampleHeaderView;
    private ViewPager dynamicViewPager;
    private PagerSlidingTabStrip dynamicTabLayout;
    private SectorMenuButton bottomMenuButton;
    private List<BannerResult> bannerResultList;
    private AppBarLayout dynamicAppBarLayout;
    private View mFakeStatusBar;
    private Toolbar dynamicToolBar;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshDot(RefreshDotEvent refreshDotEvent) {
        msgDotImg.setVisibility(View.GONE);
    }

    @Override
    public void initView(View contentView) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mFakeStatusBar = contentView.findViewById(R.id.statusBarView);
        sampleHeaderView = contentView.findViewById(R.id.headerBanner);
        dynamicViewPager = contentView.findViewById(R.id.dynamicViewPager);
        dynamicTabLayout = contentView.findViewById(R.id.dynamicTabLayout);
        searchLayout = contentView.findViewById(R.id.searchLayout);
        msgImg = contentView.findViewById(R.id.msgImg);
        msgDotImg = contentView.findViewById(R.id.msgDotImg);
        dynamicAppBarLayout = contentView.findViewById(R.id.dynamicAppBarLayout);
        dynamicToolBar = contentView.findViewById(R.id.dynamicToolBar);
        bottomMenuButton = contentView.findViewById(R.id.bottomMenuButton);
        DynamicFragmentPagerAdapter dynamicFragmentPagerAdapter = new DynamicFragmentPagerAdapter(getChildFragmentManager());
        dynamicViewPager.setAdapter(dynamicFragmentPagerAdapter);
        dynamicTabLayout.setViewPager(dynamicViewPager);
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
        queryUnReadMsg();
        initData();
    }


    @Override
    public void onClick(View view) {
        if (view == searchLayout) {
            SearchHistoryActivty.openActivity(getActivity(), 0);
        }
    }


    /***
     * 查询未读消息
     */
    private void queryUnReadMsg() {
        Map<String, String> param = new HashMap<>();
        param.put("lastCommentTime", (String) SharedprefrenceHelper.getIns(getActivity()).get("lastCommentTime", "2017-01-01 00:00:00"));
        param.put("lastReplyTime", (String) SharedprefrenceHelper.getIns(getActivity()).get("lastReplyTime", "2017-01-01 00:00:00"));
        param.put("lastPushTime", (String) SharedprefrenceHelper.getIns(getActivity()).get("lastPushTime", "2017-01-01 00:00:00"));
        param.put("lastLikeTime", (String) SharedprefrenceHelper.getIns(getActivity()).get("lastLikeTime", "2017-01-01 00:00:00"));
        param.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().postJSON(UrlConfig.msgUnRead, JsonUtils.toJson(param), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<UnReadMsgResult>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    UnReadMsgResult unReadMsgResult = (UnReadMsgResult) extra.get("result");
                    if (unReadMsgResult != null) {
                        if (unReadMsgResult.isHaveUnreadComment() || unReadMsgResult.isHaveUnreadLike() || unReadMsgResult.isHaveUnreadPush() || unReadMsgResult.isHaveUnreadReply()) {
                            msgDotImg.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }


    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_dynamic;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /***
     * 加载数据
     */
    private void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("showPosition", "index");
        params.put("endTime", DateUtils.getStringByFormat(System.currentTimeMillis(), DateUtils.dateFormatYMDHMS));
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
                        sampleHeaderView.setVisibility(View.VISIBLE);
                        sampleHeaderView.setImages(bannerResults).setIndicatorGravity(BannerConfig.RIGHT).isAutoPlay(true).setImageLoader(new GlideImageLoader()).start();
                    }
                }
            }
        });

    }


    @Override
    public void setListernner() {
        searchLayout.setOnClickListener(this);

        dynamicAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                if (state == State.EXPANDED) {
                    //展开状态
                    Drawable mDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.shape_bar_red_bg);
                    mDrawable.setAlpha(10);
                    dynamicToolBar.setBackground(mDrawable);

                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    Drawable mDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.shape_bar_red_bg);
                    mDrawable.setAlpha(255);
                    dynamicToolBar.setBackground(mDrawable);
                } else {
                    //中间状态
                    float alpha = (float) verticalOffset / dynamicAppBarLayout.getHeight();
                    Drawable mDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.shape_bar_red_bg);
                    mDrawable.setAlpha((int) alpha);
                    dynamicToolBar.setBackground(mDrawable);

                }
            }
        });

        sampleHeaderView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (bannerResultList != null && bannerResultList.size() > 0) {
                    BannerResult bannerResult = bannerResultList.get(position);
                    if ("0".equals(bannerResult.getJumpType())) {
                        WebActivity.launchWebActivity(getActivity(), bannerResult.getJumpTarget(), "首页banner", "last");
                    } else if ("1".equals(bannerResult.getJumpType())) {
                        VideoDynamicDetailsActivity.lanuchActivity(getActivity(), bannerResult.getJumpTarget());
                    } else if ("2".equals(bannerResult.getJumpType())) {
                        GraphicDynamicDetailsActivity.lanuchActivity(getActivity(), bannerResult.getJumpTarget());
                    }
                }
            }
        });

        RxView.clicks(msgImg).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
                            @Override
                            public void isLogin(boolean isLogin) {
                                if (isLogin) {
                                    MessageActivity.lunchActivity(getActivity(), null, MessageActivity.class);
                                } else {
                                    LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
                                }
                            }
                        });
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
