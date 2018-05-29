package com.xkh.hzp.xkh.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.SearchHistoryActivty;
import com.xkh.hzp.xkh.activity.TalentClassActivity;
import com.xkh.hzp.xkh.adapter.TalentAdapter;
import com.xkh.hzp.xkh.adapter.TalentClassAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.HotLableResult;
import com.xkh.hzp.xkh.entity.result.TalentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseFragment;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class TalentFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private SwipeRefreshLayout talentSwipeRefreshLayout;
    private RecyclerView talentRecyclerView;
    private LinearLayout searchLayout;
    private TalentAdapter talentAdapter;
    private TalentClassAdapter talentClassAdapter;
    private int pageNum = 1, pageSize = 10;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_talent;
    }

    @Override
    public void initView(View contentView) {
        searchLayout = contentView.findViewById(R.id.searchLayout);
        talentSwipeRefreshLayout = contentView.findViewById(R.id.talentSwipeRefreshLayout);
        talentRecyclerView = contentView.findViewById(R.id.talentRecyclerView);
        talentSwipeRefreshLayout.setOnRefreshListener(this);
        talentSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_ff5555));
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
        initHeaderData();
        initTalentData(pageNum, pageSize);
    }

    /***
     * 初始化达人数据
     * @param pageNum
     * @param pageSize
     */
    private void initTalentData(final int pageNum, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNum", String.valueOf(pageNum));
        params.put("pageSize", String.valueOf(pageSize));
        ABHttp.getIns().get(UrlConfig.queryTalentList, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<TalentResult>>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    List<TalentResult> talentResults = (List<TalentResult>) extra.get("result");
                    if (pageNum == 1) {
                        if (talentResults != null && talentResults.size() > 0) {
                            talentSwipeRefreshLayout.setRefreshing(false);
                            talentAdapter.setEnableLoadMore(true);
                            talentAdapter.setNewData(talentResults);
                            talentAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (talentResults != null && talentResults.size() > 0) {
                            talentSwipeRefreshLayout.setRefreshing(false);
                            talentAdapter.loadMoreComplete();
                            talentAdapter.addData(talentResults);
                            talentAdapter.notifyDataSetChanged();
                        } else {
                            talentSwipeRefreshLayout.setRefreshing(false);
                            talentAdapter.loadMoreComplete();
                            talentAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        });

    }


    /***
     * 初始化HeaderL类别数据
     */
    private void initHeaderData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("labelType", "hot");
        ABHttp.getIns().get(UrlConfig.queryHotLable, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<ArrayList<HotLableResult>>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    List<HotLableResult> hotLableResults = (List<HotLableResult>) extra.get("result");
                    if (hotLableResults != null && hotLableResults.size() > 0) {
                        talentClassAdapter.setNewData(hotLableResults);
                        talentClassAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }


    @Override
    public void setListernner() {
        searchLayout.setOnClickListener(this);
        talentClassAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HotLableResult hotLableResult = (HotLableResult) adapter.getItem(position);
                TalentClassActivity.lanuchActivity(getActivity(), hotLableResult.getId(), hotLableResult.getSignatureName());
            }
        });

    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        initTalentData(pageNum, pageSize);
    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        initTalentData(pageNum, pageSize);
    }

    @Override
    public void onClick(View view) {
        if (view == searchLayout) {
            SearchHistoryActivty.openActivity(getActivity(), 0);
        }
    }
}
