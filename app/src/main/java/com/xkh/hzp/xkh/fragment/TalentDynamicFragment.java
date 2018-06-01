package com.xkh.hzp.xkh.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.GraphicDynamicDetailsActivity;
import com.xkh.hzp.xkh.activity.VideoDynamicDetailsActivity;
import com.xkh.hzp.xkh.adapter.DynamicAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.entity.result.TalentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.http.Url;
import xkh.hzp.xkh.com.base.view.EmptyView;
import xkh.hzp.xkh.com.base.view.XkhLoadMoreView;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName TalentDynamicFragment
 * @Author tangyang
 * @DATE 2018/5/4
 **/
public class TalentDynamicFragment extends FragmentPagerFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private RecyclerView dynamicObservableRecyclerView;
    private DynamicAdapter dynamicAdapter;
    private int pageNum = 1, pageSize = 10;

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
        EmptyView emptyView = new EmptyView(getActivity());
        emptyView.setOperateBtnVisiable(false);
        emptyView.setNodataTitle("现在还没有动态哇");
        emptyView.setNodataImageSource(R.mipmap.note_empty);
        dynamicAdapter.setEmptyView(emptyView);
        dynamicAdapter.setLoadMoreView(new XkhLoadMoreView());
        dynamicAdapter.setOnLoadMoreListener(this, dynamicObservableRecyclerView);
        dynamicObservableRecyclerView.setAdapter(dynamicAdapter);
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
        params.put("dynamicSearchType", "image");
        ABHttp.getIns().get(UrlConfig.dynamicList, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<DynamicBean>>() {
                }.getType());

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
        dynamicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.dynamicUserHeadImg:
                        break;
                    case R.id.sharedLayout:
                        break;
                    case R.id.componentLayout:
                        break;
                    case R.id.dynamicContentTxt:
                        break;
                    case R.id.dynamicImgContentLayout:
                        break;
                }
            }
        });

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


    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        initData(pageNum, pageSize);
    }
}
