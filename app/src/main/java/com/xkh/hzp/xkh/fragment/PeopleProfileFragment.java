package com.xkh.hzp.xkh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.BusinessCooperationActivity;
import com.xkh.hzp.xkh.activity.GraphicDynamicDetailsActivity;
import com.xkh.hzp.xkh.activity.LoginActivity;
import com.xkh.hzp.xkh.adapter.TalentPictureAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.TalentInfoResultBean;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.CheckLoginManager;
import com.xkh.hzp.xkh.utils.UserDataManager;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseFragment;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName PeopleProfileFragment
 * @Author tangyang
 * @DATE 2018/5/9
 **/
public class PeopleProfileFragment extends BaseFragment {

    private RecyclerView talentPictureRecycleView;
    private Button linkTalentBtn;
    private TextView talentHeightTxt, talentWidghtTxt, talentMeasurementstTxt, talentConstellationTxt;
    private TextView talentSpecialtyTxt;
    private TagFlowLayout talentLableTagFlowLayout;
    private TalentPictureAdapter talentPictureAdapter;


    public static Fragment getInstance(String talentUserId) {
        PeopleProfileFragment peopleProfileFragment = new PeopleProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("talentUserId", talentUserId);
        peopleProfileFragment.setArguments(bundle);
        return peopleProfileFragment;
    }

    private String getTalentUserId() {
        if (getArguments() != null) {
            return getArguments().getString("talentUserId");
        }
        return "";
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_people_profile;
    }

    @Override
    public void initView(View contentView) {
        talentPictureRecycleView = contentView.findViewById(R.id.talentPictureRecycleView);
        talentHeightTxt = contentView.findViewById(R.id.talentHeightTxt);
        talentWidghtTxt = contentView.findViewById(R.id.talentWidghtTxt);
        talentMeasurementstTxt = contentView.findViewById(R.id.talentMeasurementstTxt);
        talentConstellationTxt = contentView.findViewById(R.id.talentConstellationTxt);
        talentSpecialtyTxt = contentView.findViewById(R.id.talentSpecialtyTxt);
        talentLableTagFlowLayout = contentView.findViewById(R.id.talentLableTagFlowLayout);
        linkTalentBtn = contentView.findViewById(R.id.linkTalentBtn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        talentPictureRecycleView.setLayoutManager(layoutManager);
        talentPictureAdapter = new TalentPictureAdapter();
        talentPictureRecycleView.setAdapter(talentPictureAdapter);
        if ("normal".equals(UserDataManager.getInstance().getUserInfo().getUserType())) {
            linkTalentBtn.setVisibility(View.VISIBLE);
        } else {
            linkTalentBtn.setVisibility(View.GONE);
        }
        queryTalentInfo();
    }

    /**
     * 查询达人信息
     */
    private void queryTalentInfo() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("userId", getTalentUserId());
        ABHttp.getIns().restfulGet(UrlConfig.queryTalentInfo, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<TalentInfoResultBean>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    TalentInfoResultBean talentInfoResultBean = (TalentInfoResultBean) extra.get("result");
                    if (talentInfoResultBean != null) {
                        setTalentInfoUI(talentInfoResultBean);
                    }
                }
            }
        });

    }

    /***
     * 设置UI数据
     * @param talentInfoResultBean
     */
    private void setTalentInfoUI(TalentInfoResultBean talentInfoResultBean) {
        talentHeightTxt.setText(talentInfoResultBean.getHigh() == 0 ? "身高:" : "身高:" + talentInfoResultBean.getHigh() + "cm");

        talentWidghtTxt.setText(talentInfoResultBean.getWeight() == 0 ? "体重:" : "体重:" + talentInfoResultBean.getWeight() + "kg");
        talentMeasurementstTxt.setText(TextUtils.isEmpty(talentInfoResultBean.getMeasurements()) ? "三围:" : "三围:" + talentInfoResultBean.getMeasurements());
        talentConstellationTxt.setText(TextUtils.isEmpty(talentInfoResultBean.getConstellation()) ? "星座:" : "星座：" + talentInfoResultBean.getConstellation());
        if (TextUtils.isEmpty(talentInfoResultBean.getPersonalitySignature())) {
            talentSpecialtyTxt.setVisibility(View.GONE);
        } else {
            talentSpecialtyTxt.setVisibility(View.VISIBLE);
            talentSpecialtyTxt.setText(talentInfoResultBean.getPersonalitySignature());
        }
        if (!TextUtils.isEmpty(talentInfoResultBean.getImgUrl())) {
            List<String> imgList = Arrays.asList(talentInfoResultBean.getImgUrl().split(","));
            if (imgList != null && imgList.size() > 0) {
                talentPictureAdapter.setNewData(imgList);
                talentPictureAdapter.notifyDataSetChanged();
            }
        }

        if (!TextUtils.isEmpty(talentInfoResultBean.getStyle())) {
            List<String> styleList = Arrays.asList(talentInfoResultBean.getStyle().split(","));
            if (styleList != null && styleList.size() > 0) {
                talentLableTagFlowLayout.setAdapter(new TagAdapter<String>(styleList) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_item_lable, null);
                        TextView textView = view.findViewById(R.id.lableTxt);
                        textView.setText(s);
                        return view;
                    }
                });
            }
        }
    }

    @Override
    public void setListernner() {
        linkTalentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }
}
