package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.TalentAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.TalentBean;
import com.xkh.hzp.xkh.entity.result.TalentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.EmptyView;
import xkh.hzp.xkh.com.base.view.XkhLoadMoreView;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName TalentClassActivity
 * @Author tangyang
 * @DATE 2018/5/7
 **/
public class TalentClassActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout talentClassSwipefreshLayout;
    private RecyclerView talentClassRecyclerView;
    private TalentAdapter talentAdapter;
    private int pageNum = 1, pageSize = 10;

    public static void lanuchActivity(Activity activity, int hotLableId, String hotLableName) {
        Intent intent = new Intent(activity, TalentClassActivity.class);
        intent.putExtra("hotLableId", hotLableId);
        intent.putExtra("hotLableName", hotLableName);
        activity.startActivity(intent);
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_fc4d5e), 0);
    }

    @Override
    protected void setToolbarBgColor() {
        super.setToolbarBgColor();
        baseToolBar.setBackground(getResources().getDrawable(R.drawable.shape_bar_red_bg));
    }

    private int getHotLableId() {
        return getIntent().getIntExtra("hotLableId", 0);
    }


    private String getHotLableName() {
        return getIntent().getStringExtra("hotLableName");
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_class_talent;
    }

    @Override
    public void initView() {
        setToolbarTitleTv(getHotLableName());
        hideToolbarBottomLine();
        setTitleNavigationIcon(R.drawable.icon_back);
        setToolBarTitleTextColor(getResources().getColor(R.color.color_ffffff));
        talentClassSwipefreshLayout = findViewById(R.id.talentClassSwipefreshLayout);
        talentClassRecyclerView = findViewById(R.id.talentClassRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        talentClassRecyclerView.setLayoutManager(linearLayoutManager);
        talentClassSwipefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_ff5555));
        talentClassSwipefreshLayout.setOnRefreshListener(this);
        talentAdapter = new TalentAdapter();
        EmptyView emptyView = new EmptyView(this);
        emptyView.setNodataImageSource(R.mipmap.note_empty);
        emptyView.setNodataTitle("暂无达人");
        emptyView.setOperateBtnVisiable(false);
        talentAdapter.setEmptyView(emptyView);
        talentAdapter.setLoadMoreView(new XkhLoadMoreView());
        talentAdapter.setOnLoadMoreListener(this, talentClassRecyclerView);
        talentClassRecyclerView.setAdapter(talentAdapter);
        initData(pageNum, pageSize);
    }

    private void initData(final int pageNum, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNum", String.valueOf(pageNum));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("signatureId", String.valueOf(getHotLableId()));
        ABHttp.getIns().get(UrlConfig.queryTalentList, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<TalentResult>>() {
                }.getType());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                talentClassSwipefreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    List<TalentResult> talentResults = (List<TalentResult>) extra.get("result");
                    if (pageNum == 1) {
                        if (talentResults != null && talentResults.size() > 0) {
                            talentAdapter.setEnableLoadMore(true);
                            talentAdapter.setNewData(talentResults);
                            talentAdapter.notifyDataSetChanged();
                        } else {
                            talentAdapter.loadMoreEnd();
                        }
                    } else {
                        if (talentResults != null && talentResults.size() > 0) {
                            talentAdapter.loadMoreComplete();
                            talentAdapter.addData(talentResults);
                            talentAdapter.notifyDataSetChanged();
                        } else {
                            talentAdapter.loadMoreComplete();
                            talentAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void setListenner() {

    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        initData(pageNum, pageSize);
    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        initData(pageNum, pageSize);
    }
}
