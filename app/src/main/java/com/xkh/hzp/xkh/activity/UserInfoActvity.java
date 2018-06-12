package com.xkh.hzp.xkh.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UserInfoResult;
import com.xkh.hzp.xkh.event.UpdateUserInfoEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.tuSDK.RichEditComponentSample;
import com.xkh.hzp.xkh.tuSDK.TuMutipleHandle;
import com.xkh.hzp.xkh.upload.OnUploadListener;
import com.xkh.hzp.xkh.upload.UploadImageManager;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.view.UILoadingView;

/**
 * 修改用户信息
 *
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName UserInfoActvity
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class UserInfoActvity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout userIconLayout, userIdlayout, userNickName, userGenderLayout, userSignLayout;
    private TextView userSignTxt, userIdTxt, userNickNameTxt, userGenderTxt;
    private ImageView userIconImg;
    private UILoadingView uiLoadingView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    public void initView() {
        setToolbarTitleTv("用户信息");
        userIconLayout = findViewById(R.id.activity_user_info_icon_rl);
        userIdlayout = findViewById(R.id.activity_user_info_id_rl);
        userNickName = findViewById(R.id.activity_user_info_nick_rl);
        userGenderLayout = findViewById(R.id.activity_user_info_sex_rl);
        userSignLayout = findViewById(R.id.activity_user_info_sign_rl);
        userSignTxt = findViewById(R.id.activity_user_info_sign_content);
        userGenderTxt = findViewById(R.id.activity_user_info_sex_content);
        userIdTxt = findViewById(R.id.activity_user_info_id_content);
        userNickNameTxt = findViewById(R.id.activity_user_info_nick_content);
        userIconImg = findViewById(R.id.activity_user_info_icon_img);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setUserInfoData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userInfoUpdate(UpdateUserInfoEvent updateUserInfoEvent) {
        setUserInfoData();
    }


    /***
     * 设置用户数据
     */
    private void setUserInfoData() {
        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
        if (userInfoResult != null) {
            userNickNameTxt.setText(userInfoResult.getName());
            userSignTxt.setText(userInfoResult.getPersonSignature());
            userIdTxt.setText(String.valueOf(userInfoResult.getId()));
            userGenderTxt.setText("0".equals(userInfoResult.getSex()) ? "校花" : "校草");
            Glide.with(UserInfoActvity.this).load(userInfoResult.getHeadPortrait()).transform(new GlideCircleTransform(this)).placeholder(R.mipmap.icon_female_selected).error(R.mipmap.icon_female_selected).into(userIconImg);
        }
    }


    @Override
    public void setListenner() {
        userIconLayout.setOnClickListener(this);
        userNickName.setOnClickListener(this);
        userGenderLayout.setOnClickListener(this);
        userSignLayout.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /***
     * 选择图片
     */
    private void choosePic() {
        uiLoadingView = new UILoadingView(this, false, "正在上传图片");
        RichEditComponentSample richEditComponentSample = new RichEditComponentSample();
        richEditComponentSample.setTuMutipleHandle(new TuMutipleHandle() {
            @Override
            public void onMultipleTuSuccess(List<String> images) {
                if (images != null && images.size() > 0) {
                    uiLoadingView.show();
                    doUpload(images);
                    Glide.with(UserInfoActvity.this).load(new File(images.get(0))).transform(new GlideCircleTransform(UserInfoActvity.this)).placeholder(R.drawable.tangle_icon).error(R.drawable.tangle_icon).into(userIconImg);
                }
            }

            @Override
            public void onFail(String msg) {
                Toasty.info(UserInfoActvity.this, msg).show();
            }
        });
        richEditComponentSample.showSample(UserInfoActvity.this, 1);
    }


    /***
     * 上传图片
     * @param
     */
    private void doUpload(List<String> imageList) {
        UploadImageManager.getInstances().doUpload(this, imageList, new OnUploadListener() {
            @Override
            public void onAllSuccess(List<HashMap<String, Object>> allImages) {
                String filePath = (String) allImages.get(0).get("url");
                completeInfo(filePath);
            }

            @Override
            public void onAllFailed(String message) {
                Toasty.info(UserInfoActvity.this, message).show();
            }

            @Override
            public void onThreadFinish(int position) {

            }
        });
    }


    /***
     * 完善资料
     **/
    private void completeInfo(final String filePath) {
        HashMap param = new HashMap();
        final String userId = UserDataManager.getInstance().getUserId();
        param.put("headPortrait", filePath);
        param.put("userId", userId);
        ABHttp.getIns().postJSON(UrlConfig.userInfo + "?userId=" + userId, JsonUtils.toJson(param), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                uiLoadingView.dismiss();
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                Toasty.info(UserInfoActvity.this, msg).show();
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    if (result) {
                        UserInfoResult userInfoResult = UserDataManager.getInstance().getUserInfo();
                        userInfoResult.setHeadPortrait(filePath);
                        UserDataManager.getInstance().saveUserInfo(userInfoResult);
                    }
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        if (view == userIconLayout) {
            choosePic();
        } else if (view == userNickName) {
            UserNickNameActivity.lunchActivity(UserInfoActvity.this, null, UserNickNameActivity.class);
        } else if (view == userGenderLayout) {
            UpdateGenderActivity.lunchActivity(UserInfoActvity.this, null, UpdateGenderActivity.class);
        } else if (view == userSignLayout) {
            UserSignActivity.lunchActivity(UserInfoActvity.this, null, UserSignActivity.class);
        }
    }
}
