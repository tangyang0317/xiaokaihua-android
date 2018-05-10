package com.xkh.hzp.xkh.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.TalentPictureAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
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
        List<String> talentPictureLists = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            talentPictureLists.add("");
        }
        talentPictureAdapter.setNewData(talentPictureLists);
        talentPictureAdapter.notifyDataSetChanged();
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
