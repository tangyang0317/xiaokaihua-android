package com.xkh.hzp.xkh.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.GraphicDynamicDetailsActivity;
import com.xkh.hzp.xkh.activity.LoginActivity;
import com.xkh.hzp.xkh.activity.TalentHomePageActivity;
import com.xkh.hzp.xkh.activity.VideoDynamicDetailsActivity;
import com.xkh.hzp.xkh.adapter.DynamicAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.PraiseUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
import xkh.hzp.xkh.com.base.view.EmptyView;
import xkh.hzp.xkh.com.base.view.XkhLoadMoreView;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName TalentDynamicFragment
 * @Author tangyang
 * @DATE 2018/5/4
 **/
public class TalentDynamicFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView dynamicObservableRecyclerView;
    private SwipeRefreshLayout dynamicSwipeRefreshLayout;
    private DynamicAdapter dynamicAdapter;
    private int pageNum = 1, pageSize = 10;


    public static Fragment getInstance(String talentUserId) {
        TalentDynamicFragment talentDynamicFragment = new TalentDynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("talentUserId", talentUserId);
        talentDynamicFragment.setArguments(bundle);
        return talentDynamicFragment;
    }


    private String getTalentUserId() {

        if (getArguments() != null) {
            return getArguments().getString("talentUserId");
        }
        return "";
    }


    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_dynamic_index;
    }

    @Override
    public void initView(View contentView) {
        dynamicSwipeRefreshLayout = contentView.findViewById(R.id.dynamicSwipeRefreshLayout);
        dynamicObservableRecyclerView = contentView.findViewById(R.id.dynamicObservableRecyclerView);
        dynamicObservableRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dynamicAdapter = new DynamicAdapter();
        EmptyView emptyView = new EmptyView(getActivity());
        emptyView.setOperateBtnVisiable(false);
        emptyView.setNodataTitle("现在还没有动态哇");
        emptyView.setNodataImageSource(R.mipmap.note_empty);
        dynamicAdapter.setEmptyView(emptyView);
        dynamicSwipeRefreshLayout.setOnRefreshListener(this);
        dynamicSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_ff5555));
        dynamicAdapter.setLoadMoreView(new XkhLoadMoreView());
        dynamicAdapter.setOnLoadMoreListener(this, dynamicObservableRecyclerView);
        dynamicObservableRecyclerView.setAdapter(dynamicAdapter);
        pageNum = 1;
        initData(pageNum, pageSize);
    }

    /***
     * 加载动态列表数据
     * @param pageNum
     * @param pageSize
     */
    private void initData(final int pageNum, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageNum", String.valueOf(pageNum));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("searchUserId", getTalentUserId());
        params.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().get(UrlConfig.dynamicList, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<DynamicBean>>() {
                }.getType());

            }

            @Override
            public void onFinish() {
                super.onFinish();
                dynamicSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    List<DynamicBean> talentResults = (List<DynamicBean>) extra.get("result");
                    if (pageNum == 1) {
                        if (talentResults != null && talentResults.size() > 0) {
                            if (talentResults.size() < 10) {
                                dynamicAdapter.loadMoreEnd();
                                dynamicAdapter.setNewData(talentResults);
                            } else {
                                dynamicAdapter.setEnableLoadMore(true);
                                dynamicAdapter.setNewData(talentResults);
                            }
                        }
                    } else {
                        if (talentResults != null && talentResults.size() > 0) {
                            dynamicAdapter.loadMoreComplete();
                            dynamicAdapter.addData(talentResults);
                        } else {
                            dynamicAdapter.loadMoreComplete();
                            dynamicAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void setListernner() {
        dynamicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, final View view, final int position) {
                final DynamicBean dynamicBean = (DynamicBean) adapter.getItem(position);
                if (dynamicBean == null) {
                    return;
                }
                switch (view.getId()) {
                    case R.id.sharedLayout:
                        if ("image".equals(dynamicBean.getDynamicType())) {
                            GraphicDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                        } else if ("video".equals(dynamicBean.getDynamicType())) {
                            VideoDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                        }
                        break;
                    case R.id.componentLayout:
                        if ("image".equals(dynamicBean.getDynamicType())) {
                            GraphicDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                        } else if ("video".equals(dynamicBean.getDynamicType())) {
                            VideoDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                        }
                        break;
                    case R.id.goodLayout:
                        String dynamicId = String.valueOf(dynamicBean.getDynamicId());
                        String userId = UserDataManager.getInstance().getUserId();
                        if ("normal".equals(dynamicBean.getLikeStatus())) {
                            PraiseUtils.getIns().unPraise(dynamicId, userId, new PraiseUtils.UnPraisedCallback() {

                                @Override
                                public void onFail() {
                                    Toasty.error(getActivity(), "取消点赞失败").show();
                                }

                                @Override
                                public void notLogin() {
                                    SharedprefrenceHelper.getIns(getActivity()).clear();
                                    LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
                                }

                                @Override
                                public void onUnPraise() {
                                    ImageView praiseView = view.findViewById(R.id.praisedImg);
                                    praiseView.setImageResource(R.mipmap.icon_unpraised);
                                    ScaleAnimation viewShowAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                    viewShowAnimation.setDuration(500);
                                    praiseView.startAnimation(viewShowAnimation);
                                    ((DynamicBean) adapter.getItem(position)).setLikeStatus("");
                                }
                            });
                        } else {
                            PraiseUtils.getIns().praise(dynamicId, userId, new PraiseUtils.PraisedCallback() {
                                @Override
                                public void onPraise() {
                                    ImageView praiseView = view.findViewById(R.id.praisedImg);
                                    praiseView.setImageResource(R.mipmap.icon_praised);
                                    ScaleAnimation viewShowAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                    viewShowAnimation.setDuration(500);
                                    praiseView.startAnimation(viewShowAnimation);
                                    ((DynamicBean) adapter.getItem(position)).setLikeStatus("normal");
                                }

                                @Override
                                public void onFail() {
                                    Toasty.error(getActivity(), "点赞失败").show();
                                }

                                @Override
                                public void notLogin() {
                                    SharedprefrenceHelper.getIns(getActivity()).clear();
                                    LoginActivity.lunchActivity(getActivity(), null, LoginActivity.class);
                                }

                            });
                        }

                        break;
                    case R.id.dynamicContentTxt:
                        if ("image".equals(dynamicBean.getDynamicType())) {
                            GraphicDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                        } else if ("video".equals(dynamicBean.getDynamicType())) {
                            VideoDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                        }
                        break;
                    case R.id.dynamicImgContentLayout:
                        if ("image".equals(dynamicBean.getDynamicType())) {
                            GraphicDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                        } else if ("video".equals(dynamicBean.getDynamicType())) {
                            VideoDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                        }
                        break;
                }
            }
        });


        dynamicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final DynamicBean dynamicBean = (DynamicBean) adapter.getItem(position);
                if (dynamicBean == null) {
                    return;
                }
                if ("image".equals(dynamicBean.getDynamicType())) {
                    GraphicDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                } else if ("video".equals(dynamicBean.getDynamicType())) {
                    VideoDynamicDetailsActivity.lanuchActivity(getActivity(), String.valueOf(dynamicBean.getDynamicId()));
                }

            }
        });

    }


    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        initData(pageNum, pageSize);
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        initData(pageNum, pageSize);
    }
}
