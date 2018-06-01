package com.xkh.hzp.xkh.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.BusinessCooperationActivity;
import com.xkh.hzp.xkh.activity.JoinTalentActivity;
import com.xkh.hzp.xkh.activity.LoginActivity;
import com.xkh.hzp.xkh.activity.SettingActivity;
import com.xkh.hzp.xkh.activity.UserInfoActvity;
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
import xkh.hzp.xkh.com.base.view.ItemLayout;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private ImageView userHeadImg;
    private TextView userNickNameTxt, userIntroductionTxt, mineLoginTxt;
    private ItemLayout userMsgItemLayout, joinTalentItemLayout, businessConperationItemLayout, updateInfoItemLayout, settingItemLayout;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_mine;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(LogoutEvent logoutEvent) {
        if (logoutEvent.isLogoutSuccess()) {
            setUserInfoData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(getActivity());
    }

    @Override
    public void initView(View contentView) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mineLoginTxt = contentView.findViewById(R.id.mineLoginTxt);
        userHeadImg = contentView.findViewById(R.id.userHeadImg);
        userNickNameTxt = contentView.findViewById(R.id.userNickNameTxt);
        userIntroductionTxt = contentView.findViewById(R.id.userIntroductionTxt);
        userMsgItemLayout = contentView.findViewById(R.id.userMsgItemLayout);
        joinTalentItemLayout = contentView.findViewById(R.id.joinTalentItemLayout);
        businessConperationItemLayout = contentView.findViewById(R.id.businessConperationItemLayout);
        updateInfoItemLayout = contentView.findViewById(R.id.updateInfoItemLayout);
        settingItemLayout = contentView.findViewById(R.id.settingItemLayout);
        getUserInfo();
    }


    private void setUserInfoData() {
        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
        if (userInfoResult != null) {
            mineLoginTxt.setVisibility(View.GONE);
            userHeadImg.setVisibility(View.VISIBLE);
            userNickNameTxt.setVisibility(View.VISIBLE);
            userIntroductionTxt.setVisibility(View.VISIBLE);
            userNickNameTxt.setText(userInfoResult.getName());
            userIntroductionTxt.setText("" + userInfoResult.getPersonSignature());
            Glide.with(getActivity()).load(userInfoResult.getHeadPortrait()).transform(new GlideCircleTransform(getActivity())).placeholder(R.mipmap.icon_female_selected).error(R.mipmap.icon_female_selected).into(userHeadImg);
        } else {
            mineLoginTxt.setVisibility(View.VISIBLE);
            userHeadImg.setVisibility(View.GONE);
            userNickNameTxt.setVisibility(View.GONE);
            userIntroductionTxt.setVisibility(View.GONE);
        }
    }


    /***
     *查询用户信息
     */
    private void getUserInfo() {
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
    public void setListernner() {
        userMsgItemLayout.setOnClickListener(this);
        joinTalentItemLayout.setOnClickListener(this);
        businessConperationItemLayout.setOnClickListener(this);
        updateInfoItemLayout.setOnClickListener(this);
        settingItemLayout.setOnClickListener(this);
        mineLoginTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == userMsgItemLayout) {

        } else if (view == joinTalentItemLayout) {
            CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
                @Override
                public void isLogin(boolean isLogin) {
                    if (isLogin) {
                        JoinTalentActivity.lunchActivity(getActivity(), null, JoinTalentActivity.class);
                    } else {
                        LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
                    }
                }
            });
        } else if (view == businessConperationItemLayout) {
            CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
                @Override
                public void isLogin(boolean isLogin) {
                    if (isLogin) {
                        BusinessCooperationActivity.lunchActivity(getActivity(), null, BusinessCooperationActivity.class);
                    } else {
                        LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
                    }
                }
            });
        } else if (view == settingItemLayout) {
            SettingActivity.lunchActivity(getActivity(), null, SettingActivity.class);
        } else if (view == updateInfoItemLayout) {
            UserInfoActvity.lunchActivity(getActivity(), null, UserInfoActvity.class);
        } else if (view == mineLoginTxt) {
            LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
        }
    }
}
