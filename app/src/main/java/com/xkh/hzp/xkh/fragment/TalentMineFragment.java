package com.xkh.hzp.xkh.fragment;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.SettingActivity;
import com.xkh.hzp.xkh.activity.UserInfoActvity;
import com.xkh.hzp.xkh.adapter.HomePageFragmentPagerAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.event.LogoutEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.CheckLoginManager;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.LinkedHashMap;

import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName TalentMineFragment
 * @Author tangyang
 * @DATE 2018/5/8
 **/
public class TalentMineFragment extends BaseFragment implements View.OnClickListener {
    private ImageView talentMineSettingImg, talentMineMsgImg, talentMineEditImg, mineHeadImg;
    private TabLayout talentMinePagerSlidingTabStrip;
    private ViewPager talentMineViewPager;
    private RelativeLayout barLayout;
    private TextView mineNickNameTxt, mineLoginTxt, talentUserSignTxt;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_talent_mine;
    }

    @Override
    public void initView(View contentView) {
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
        String userId = UserDataManager.getInstance().getUserId();
        HomePageFragmentPagerAdapter homePageFragmentPagerAdapter = new HomePageFragmentPagerAdapter(getChildFragmentManager(), userId);
        talentMineViewPager.setAdapter(homePageFragmentPagerAdapter);
        talentMinePagerSlidingTabStrip.setupWithViewPager(talentMineViewPager);
        CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
            @Override
            public void isLogin(boolean isLogin) {
                if (!isLogin) {
                    SharedprefrenceHelper.getIns(getActivity()).clear();
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queryUserInfo();
                setUserInfoData();
            }
        }, 500);

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

}
