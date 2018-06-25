package com.xkh.hzp.xkh.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.BusinessCooperationActivity;
import com.xkh.hzp.xkh.activity.JoinTalentActivity;
import com.xkh.hzp.xkh.activity.LoginActivity;
import com.xkh.hzp.xkh.activity.MessageActivity;
import com.xkh.hzp.xkh.activity.SettingActivity;
import com.xkh.hzp.xkh.activity.UserInfoActvity;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.event.LoginEvent;
import com.xkh.hzp.xkh.event.LogoutEvent;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.Global;
import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
import xkh.hzp.xkh.com.base.view.ItemLayout;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class MineFragment extends BaseFragment {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginEvent loginEvent) {
        if (loginEvent != null && loginEvent.isSuccess()) {
            getUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserInfo(UpdateUserInfoEvent updateUserInfoEvent) {
        if (updateUserInfoEvent != null) {
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
        CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
            @Override
            public void isLogin(boolean isLogin) {
                if (!isLogin) {
                    SharedprefrenceHelper.getIns(getActivity()).clear();
                }
            }
        });
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
            if (TextUtils.isEmpty(userInfoResult.getPersonSignature())) {
                userIntroductionTxt.setText("");
            } else {
                userIntroductionTxt.setText("" + userInfoResult.getPersonSignature());
            }
            Glide.with(Global.app).load(userInfoResult.getHeadPortrait()).transform(new GlideCircleTransform(getActivity())).error(R.mipmap.icon_female_selected).into(userHeadImg);
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
    public void setListernner() {
        RxView.clicks(userMsgItemLayout).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
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

        RxView.clicks(joinTalentItemLayout).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
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
                    }
                });
        RxView.clicks(businessConperationItemLayout).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
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
                    }
                });
        RxView.clicks(settingItemLayout).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
                            @Override
                            public void isLogin(boolean isLogin) {
                                if (isLogin) {
                                    SettingActivity.lunchActivity(getActivity(), null, SettingActivity.class);
                                } else {
                                    LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
                                }
                            }
                        });
                    }
                });
        RxView.clicks(updateInfoItemLayout).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
                            @Override
                            public void isLogin(boolean isLogin) {
                                if (isLogin) {
                                    UserInfoActvity.lunchActivity(getActivity(), null, UserInfoActvity.class);
                                } else {
                                    LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
                                }
                            }
                        });
                    }
                });
        RxView.clicks(mineLoginTxt).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
                    }
                });

    }

}
