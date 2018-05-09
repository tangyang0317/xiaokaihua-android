package com.xkh.hzp.xkh.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.TalentClassActivity;
import com.xkh.hzp.xkh.adapter.TalentClassAdapter;
import com.xkh.hzp.xkh.adapter.TalentAdapter;
import com.xkh.hzp.xkh.entity.TalentBean;

import java.util.ArrayList;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseFragment;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class TalentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    protected ImmersionBar mImmersionBar;
    private SwipeRefreshLayout talentSwipeRefreshLayout;
    private RecyclerView talentRecyclerView;
    private TalentAdapter talentAdapter;
    private TalentClassAdapter talentClassAdapter;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_talent;
    }

    @Override
    public void initView(View contentView) {
        initImmersionBar();
        talentSwipeRefreshLayout = contentView.findViewById(R.id.talentSwipeRefreshLayout);
        talentRecyclerView = contentView.findViewById(R.id.talentRecyclerView);

        talentSwipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        talentRecyclerView.setLayoutManager(linearLayoutManager);

        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.view_talent_header, null);
        RecyclerView recommendTalentRecycleView = headView.findViewById(R.id.recommendTalentRecycleView);
        LinearLayoutManager headerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendTalentRecycleView.setLayoutManager(headerLayoutManager);
        talentClassAdapter = new TalentClassAdapter();
        recommendTalentRecycleView.setAdapter(talentClassAdapter);

        talentAdapter = new TalentAdapter();
        talentAdapter.addHeaderView(headView);
        talentRecyclerView.setAdapter(talentAdapter);
        initData();
    }

    /**
     *
     */
    private void initData() {
        List<TalentBean> talentBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TalentBean talentBean = new TalentBean();
            talentBeans.add(talentBean);
        }
        talentAdapter.setNewData(talentBeans);
        talentAdapter.notifyDataSetChanged();

        List<String> headerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            headerList.add("学院风" + i);
        }
        talentClassAdapter.setNewData(headerList);
        talentClassAdapter.notifyDataSetChanged();

    }

    /***
     * 初始化沉浸式
     */
    private void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
    }

    @Override
    public void setListernner() {

        talentClassAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TalentClassActivity.lunchActivity(getActivity(), null, TalentClassActivity.class);
            }
        });

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
