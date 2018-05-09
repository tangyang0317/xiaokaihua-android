package com.xkh.hzp.xkh.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.TalentAdapter;
import com.xkh.hzp.xkh.entity.TalentBean;

import java.util.ArrayList;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName TalentClassActivity
 * @Author tangyang
 * @DATE 2018/5/7
 **/
public class TalentClassActivity extends BaseActivity {

    private SwipeRefreshLayout talentClassSwipefreshLayout;
    private RecyclerView talentClassRecyclerView;
    private TalentAdapter talentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_class_talent;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("学院风");
        talentClassSwipefreshLayout = findViewById(R.id.talentClassSwipefreshLayout);
        talentClassRecyclerView = findViewById(R.id.talentClassRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        talentClassRecyclerView.setLayoutManager(linearLayoutManager);

        talentAdapter = new TalentAdapter();
        talentClassRecyclerView.setAdapter(talentAdapter);

        List<TalentBean> talentBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TalentBean talentBean = new TalentBean();
            talentBeans.add(talentBean);
        }
        talentAdapter.setNewData(talentBeans);
        talentAdapter.notifyDataSetChanged();

    }

    @Override
    public void setListenner() {

    }
}
