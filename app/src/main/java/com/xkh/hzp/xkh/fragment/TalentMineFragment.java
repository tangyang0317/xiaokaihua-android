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

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.SettingActivity;
import com.xkh.hzp.xkh.activity.UserInfoActvity;
import com.xkh.hzp.xkh.adapter.MineFragmentPagerAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.event.LogoutEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class TalentMineFragment extends BaseFragment implements View.OnClickListener {
    private ScrollableLayout talentMineScrollableLayout;
    private ImageView talentMineSettingImg, talentMineMsgImg, talentMineEditImg, mineHeadImg;
    private PagerSlidingTabStrip talentMinePagerSlidingTabStrip;
    private ViewPager talentMineViewPager;
    private RelativeLayout barLayout;
    private TextView mineNickNameTxt, mineLoginTxt, talentUserSignTxt;

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
        mineLoginTxt = contentView.findViewById(R.id.mineLoginTxt);
        mineNickNameTxt = contentView.findViewById(R.id.mineNickNameTxt);
        talentMinePagerSlidingTabStrip = contentView.findViewById(R.id.talentMinePagerSlidingTabStrip);
        talentMineViewPager = contentView.findViewById(R.id.talentMineViewPager);
        mineHeadImg = contentView.findViewById(R.id.mineHeadImg);
        talentUserSignTxt = contentView.findViewById(R.id.talentUserSignTxt);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

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
                final float tabsTranslationY;
                if (y < maxY) {
                    tabsTranslationY = .0F;
                } else {
                    tabsTranslationY = y - maxY;
                }
                talentMinePagerSlidingTabStrip.setTranslationY(tabsTranslationY);

            }
        });

        queryUserInfo();
        setUserInfoData();

    }


    /***
     *查询用户信息
     */
    private void queryUserInfo() {
        String userId = UserDataManager.getInstance().getUserId();
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("userId", userId);
        ABHttp.getIns().restfulGet(UrlConfig.queryuserInfo, hashMap, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<UserInfoResult>() {
                }.getType());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                setUserInfoData();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    final UserInfoResult userInfoResult = (UserInfoResult) extra.get("result");
                    if (userInfoResult != null) {
                        UserDataManager.getInstance().saveUserInfo(userInfoResult);
                    }
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(LogoutEvent logoutEvent) {
        if (logoutEvent.isLogoutSuccess()) {
            setUserInfoData();
        }
    }


    /***
     *
     */
    private void setUserInfoData() {
        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
        if (userInfoResult != null) {
            mineLoginTxt.setVisibility(View.GONE);
            mineHeadImg.setVisibility(View.VISIBLE);
            mineNickNameTxt.setVisibility(View.VISIBLE);
            talentUserSignTxt.setVisibility(View.VISIBLE);
            mineNickNameTxt.setText(userInfoResult.getName());
            talentUserSignTxt.setText("" + userInfoResult.getPersonSignature());
            Glide.with(getActivity()).load(userInfoResult.getHeadPortrait()).transform(new GlideCircleTransform(getActivity())).placeholder(R.mipmap.icon_female_selected).error(R.mipmap.icon_female_selected).into(mineHeadImg);
        } else {
            mineLoginTxt.setVisibility(View.VISIBLE);
            mineHeadImg.setVisibility(View.GONE);
            mineNickNameTxt.setVisibility(View.GONE);
            talentUserSignTxt.setVisibility(View.GONE);
        }
    }


    @Override
    public void setListernner() {
        talentMineSettingImg.setOnClickListener(this);
        talentMineEditImg.setOnClickListener(this);
        talentMineMsgImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == talentMineSettingImg) {
            SettingActivity.lunchActivity(getActivity(), null, SettingActivity.class);
        } else if (view == talentMineEditImg) {
            UserInfoActvity.lunchActivity(getActivity(), null, UserInfoActvity.class);
        }
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

}
