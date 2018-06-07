package com.xkh.hzp.xkh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.TalentPictureAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.TalentInfoResultBean;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
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
public class PeopleProfileFragment extends FragmentPagerFragment {

    private RecyclerView talentPictureRecycleView;
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        talentPictureRecycleView.setLayoutManager(layoutManager);
        talentPictureAdapter = new TalentPictureAdapter();
        talentPictureRecycleView.setAdapter(talentPictureAdapter);
        queryTalentInfo();
        List<String> talentPictureLists = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            talentPictureLists.add("");
        }
        talentPictureAdapter.setNewData(talentPictureLists);
        talentPictureAdapter.notifyDataSetChanged();
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
        talentHeightTxt.setText("身高：" + talentInfoResultBean.getHigh() + "cm");
        talentWidghtTxt.setText("体重：" + talentInfoResultBean.getWeight() + "kg");
        talentMeasurementstTxt.setText("三围：" + talentInfoResultBean.getMeasurements());
        talentConstellationTxt.setText("星座：" + talentInfoResultBean.getConstellation());
        talentSpecialtyTxt.setText(talentInfoResultBean.getPersonalitySignature());
    }

    @Override
    public void setListernner() {

    }

    @Override
    public boolean canScrollVertically(int direction) {
        return talentPictureRecycleView != null && talentPictureRecycleView.canScrollVertically(direction);
    }

    @Override
    public void onFlingOver(int y, long duration) {
        if (talentPictureRecycleView != null) {
            talentPictureRecycleView.smoothScrollBy(0, y);
        }
    }
}
