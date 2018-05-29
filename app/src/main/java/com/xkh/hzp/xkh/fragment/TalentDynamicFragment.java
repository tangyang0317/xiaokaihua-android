package com.xkh.hzp.xkh.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.GraphicDynamicDetailsActivity;
import com.xkh.hzp.xkh.activity.VideoDynamicDetailsActivity;
import com.xkh.hzp.xkh.adapter.DynamicAdapter;
import com.xkh.hzp.xkh.entity.DynamicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName TalentDynamicFragment
 * @Author tangyang
 * @DATE 2018/5/4
 **/
public class TalentDynamicFragment extends FragmentPagerFragment {
    private RecyclerView dynamicObservableRecyclerView;
    private DynamicAdapter dynamicAdapter;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_dynamic_index;
    }


    @Override
    public void initView(View contentView) {
        dynamicObservableRecyclerView = contentView.findViewById(R.id.dynamicObservableRecyclerView);
        dynamicObservableRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dynamicObservableRecyclerView.setHasFixedSize(true);
        dynamicAdapter = new DynamicAdapter();
        dynamicObservableRecyclerView.setAdapter(dynamicAdapter);
        List<DynamicBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DynamicBean dynamicBean = new DynamicBean();
            List<String> stringList = new ArrayList<>();
            for (int k = 0; k < i + 1; k++) {
                stringList.add("");
            }
            dynamicBean.setImgList(stringList);
            list.add(dynamicBean);
        }
        dynamicAdapter.setNewData(list);
        dynamicAdapter.notifyDataSetChanged();

        initData();
    }

    private void initData() {


    }

    @Override
    public void setListernner() {

        dynamicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position % 2 == 0) {
                    VideoDynamicDetailsActivity.lunchActivity(getActivity(), null, VideoDynamicDetailsActivity.class);
                } else {
                    GraphicDynamicDetailsActivity.lunchActivity(getActivity(), null, GraphicDynamicDetailsActivity.class);
                }
            }
        });
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return dynamicObservableRecyclerView != null && dynamicObservableRecyclerView.canScrollVertically(direction);
    }

    @Override
    public void onFlingOver(int y, long duration) {
        if (dynamicObservableRecyclerView != null) {
            dynamicObservableRecyclerView.smoothScrollBy(0, y);
        }
    }


}
