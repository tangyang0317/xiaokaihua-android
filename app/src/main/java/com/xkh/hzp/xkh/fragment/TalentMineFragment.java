package com.xkh.hzp.xkh.fragment;

import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.MessageActivity;
import com.xkh.hzp.xkh.activity.SettingActivity;
import com.xkh.hzp.xkh.activity.UserInfoActvity;
import com.xkh.hzp.xkh.adapter.HomePageFragmentPagerAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UnReadMsgResult;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.event.LoginEvent;
import com.xkh.hzp.xkh.event.LogoutEvent;
import com.xkh.hzp.xkh.event.RefreshDotEvent;
import com.xkh.hzp.xkh.event.UpdateUserInfoEvent;
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
import java.util.Map;

import xkh.hzp.xkh.com.base.Global;
import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
import xkh.hzp.xkh.com.base.view.PagerSlidingTabStrip;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName TalentMineFragment
 * @Author tangyang
 * @DATE 2018/5/8
 **/
public class TalentMineFragment extends BaseFragment implements View.OnClickListener {
    private ImageView talentMineSettingImg, talentMineMsgImg, talentMineEditImg, mineHeadImg, msgDotImg;
    private PagerSlidingTabStrip talentMinePagerSlidingTabStrip;
    private ViewPager talentMineViewPager;
    private RelativeLayout barLayout;
    private TextView mineNickNameTxt, mineLoginTxt, talentUserSignTxt;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_talent_mine;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(LogoutEvent logoutEvent) {
        if (logoutEvent.isLogoutSuccess()) {
            setUserInfoData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginEvent loginEvent) {
        if (loginEvent != null && loginEvent.isSuccess()) {
            queryUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserInfo(UpdateUserInfoEvent updateUserInfoEvent) {
        if (updateUserInfoEvent != null) {
            setUserInfoData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshDot(RefreshDotEvent refreshDotEvent) {
        msgDotImg.setVisibility(View.GONE);
    }


    @Override
    public void initView(View contentView) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        talentMineSettingImg = contentView.findViewById(R.id.talentMineSettingImg);
        talentMineMsgImg = contentView.findViewById(R.id.talentMineMsgImg);
        talentMineEditImg = contentView.findViewById(R.id.talentMineEditImg);
        msgDotImg = contentView.findViewById(R.id.msgDotImg);
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
        talentMinePagerSlidingTabStrip.setViewPager(talentMineViewPager);
        CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
            @Override
            public void isLogin(boolean isLogin) {
                if (!isLogin) {
                    SharedprefrenceHelper.getIns(getActivity()).clear();
                }
            }
        });
        queryUnReadMsg();
        queryUserInfo();

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
                        if (unReadMsgResult.isHaveUnreadComment() || unReadMsgResult.isHaveUnreadLike() || unReadMsgResult.isHaveUnreadPush()) {
                            msgDotImg.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }


    /***
     *查询用户信息
     */
    private void queryUserInfo() {
        String userId = UserDataManager.getInstance().getUserId();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", userId);
        ABHttp.getIns().get(UrlConfig.queryuserInfo, hashMap, new AbHttpCallback() {
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
            talentUserSignTxt.setText(TextUtils.isEmpty(userInfoResult.getPersonSignature()) ? "" : userInfoResult.getPersonSignature());
            Glide.with(Global.app).load(userInfoResult.getHeadPortrait()).transform(new GlideCircleTransform(getActivity())).error(R.mipmap.icon_female_selected).into(mineHeadImg);
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
        } else if (view == talentMineMsgImg) {
            MessageActivity.lunchActivity(getActivity(), null, MessageActivity.class);
        }
    }

}
