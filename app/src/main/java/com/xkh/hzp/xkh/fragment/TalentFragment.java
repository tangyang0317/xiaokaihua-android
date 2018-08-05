package com.xkh.hzp.xkh.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.LoginActivity;
import com.xkh.hzp.xkh.activity.SearchHistoryActivty;
import com.xkh.hzp.xkh.activity.TalentClassActivity;
import com.xkh.hzp.xkh.activity.TalentHomePageActivity;
import com.xkh.hzp.xkh.adapter.TalentAdapter;
import com.xkh.hzp.xkh.adapter.TalentClassAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.HotLableResult;
import com.xkh.hzp.xkh.entity.result.TalentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
import xkh.hzp.xkh.com.base.view.EmptyView;
import xkh.hzp.xkh.com.base.view.XkhLoadMoreView;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName DynamicFragment
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class TalentFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout talentSwipeRefreshLayout;
    private RecyclerView talentRecyclerView;
    private TextView searchLayout;
    private TalentAdapter talentAdapter;
    private View headView = null;
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
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.view_talent_header, null);
        RecyclerView recommendTalentRecycleView = headView.findViewById(R.id.recommendTalentRecycleView);
        LinearLayoutManager headerLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendTalentRecycleView.setLayoutManager(headerLayoutManager);
        talentClassAdapter = new TalentClassAdapter();
        recommendTalentRecycleView.setAdapter(talentClassAdapter);
        talentAdapter = new TalentAdapter();
        EmptyView emptyView = new EmptyView(getActivity());
        emptyView.setNodataImageSource(R.mipmap.note_empty);
        emptyView.setNodataTitle("暂无达人列表");
        emptyView.setOperateBtnVisiable(false);
        talentAdapter.setEmptyView(emptyView);
        talentAdapter.setOnLoadMoreListener(this, talentRecyclerView);
        talentAdapter.setLoadMoreView(new XkhLoadMoreView());
        talentRecyclerView.setAdapter(talentAdapter);
        initHeaderData();
        pageNum = 1;
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
        params.put("userId", UserDataManager.getInstance().getUserId());
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
                talentSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    List<TalentResult> talentResults = (List<TalentResult>) extra.get("result");
                    if (pageNum == 1) {
                        if (talentResults != null && talentResults.size() > 0) {
                            if (talentResults.size() < 10) {
                                talentAdapter.loadMoreEnd();
                                talentAdapter.setNewData(talentResults);
                            } else {
                                talentAdapter.setEnableLoadMore(true);
                                talentAdapter.setNewData(talentResults);
                            }
                        } else {
                            talentAdapter.loadMoreEnd();
                        }
                    } else {
                        if (talentResults != null && talentResults.size() > 0) {
                            talentAdapter.loadMoreComplete();
                            talentAdapter.addData(talentResults);
                        } else {
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
                        talentAdapter.addHeaderView(headView);
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

        talentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TalentResult talentResult = (TalentResult) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.isAttentionTxt:
                        if ("focus".equals(talentResult.getStatus())) {
                            //取消关注
                            cancleFocusTalent(talentResult);
                        } else {
                            focusTalent(talentResult);
                            //添加关注
                        }
                        break;
                    case R.id.talentPicImg:
                        TalentHomePageActivity.lanuchActivity(getActivity(), String.valueOf(talentResult.getUserId()));
                        break;
                    case R.id.talentNickNameTxt:
                        TalentHomePageActivity.lanuchActivity(getActivity(), String.valueOf(talentResult.getUserId()));
                        break;
                    case R.id.talentHeadImg:
                        TalentHomePageActivity.lanuchActivity(getActivity(), String.valueOf(talentResult.getUserId()));
                        break;
                }
            }
        });
    }

    private void updateListData(TalentResult talentResult) {
        if (talentResult != null) {
            if (talentAdapter.getData() != null && talentAdapter.getData().size() > 0) {
                for (int i = 0; i < talentAdapter.getData().size(); i++) {
                    if (talentAdapter.getData().get(i).getUserId() == talentResult.getUserId()) {
                        if ("focus".equals(talentResult.getStatus())) {
                            talentAdapter.getData().get(i).setStatus("take_off");
                        } else {
                            talentAdapter.getData().get(i).setStatus("focus");
                        }
                    }
                }
                talentAdapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * 关注
     *
     * @param talentUserId
     */
    private void focusTalent(final TalentResult talentUserId) {
        HashMap<String, String> param = new HashMap<>();
        param.put("beFocusUserId", String.valueOf(talentUserId.getUserId()));
        param.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().post(UrlConfig.foucsTalent, param, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
            }

            @Override
            public void onNotLogin() {
                super.onNotLogin();
                SharedprefrenceHelper.getIns(getActivity()).clear();
                LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    Toasty.info(getActivity(), "关注成功").show();
                    updateListData(talentUserId);
                }
            }
        });
    }

    /**
     * 取消关注
     *
     * @param talentUserId
     */
    private void cancleFocusTalent(final TalentResult talentUserId) {
        HashMap<String, String> param = new HashMap<>();
        param.put("beFocusUserId", String.valueOf(talentUserId.getUserId()));
        param.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().post(UrlConfig.cancleFoucsTalent, param, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
            }

            @Override
            public void onNotLogin() {
                super.onNotLogin();
                SharedprefrenceHelper.getIns(getActivity()).clear();
                LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    Toasty.info(getActivity(), "取消关注成功").show();
                    updateListData(talentUserId);
                }
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
            SearchHistoryActivty.openActivity(getActivity(), 1);
        }
    }
}
