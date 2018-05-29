package com.xkh.hzp.xkh.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.BusinessCooperationActivity;
import com.xkh.hzp.xkh.activity.JoinTalentActivity;
import com.xkh.hzp.xkh.activity.LoginActivity;
import com.xkh.hzp.xkh.activity.UserInfoActvity;

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
    private TextView userNickNameTxt, userIntroductionTxt;
    private ItemLayout userMsgItemLayout, joinTalentItemLayout, businessConperationItemLayout, updateInfoItemLayout, settingItemLayout;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View contentView) {
        userHeadImg = contentView.findViewById(R.id.userHeadImg);
        userNickNameTxt = contentView.findViewById(R.id.userNickNameTxt);
        userIntroductionTxt = contentView.findViewById(R.id.userIntroductionTxt);
        userMsgItemLayout = contentView.findViewById(R.id.userMsgItemLayout);
        joinTalentItemLayout = contentView.findViewById(R.id.joinTalentItemLayout);
        businessConperationItemLayout = contentView.findViewById(R.id.businessConperationItemLayout);
        updateInfoItemLayout = contentView.findViewById(R.id.updateInfoItemLayout);
        settingItemLayout = contentView.findViewById(R.id.settingItemLayout);

    }


    @Override
    public void setListernner() {
        userHeadImg.setOnClickListener(this);
        userMsgItemLayout.setOnClickListener(this);
        joinTalentItemLayout.setOnClickListener(this);
        businessConperationItemLayout.setOnClickListener(this);
        updateInfoItemLayout.setOnClickListener(this);
        settingItemLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == userMsgItemLayout) {

        } else if (view == joinTalentItemLayout) {
            JoinTalentActivity.lunchActivity(getActivity(), null, JoinTalentActivity.class);
        } else if (view == businessConperationItemLayout) {
            BusinessCooperationActivity.lunchActivity(getActivity(), null, BusinessCooperationActivity.class);
        } else if (view == settingItemLayout) {

        } else if (view == userHeadImg) {
            LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
        } else if (view == updateInfoItemLayout) {
            UserInfoActvity.lunchActivity(getActivity(), null, UserInfoActvity.class);
        }
    }
}
