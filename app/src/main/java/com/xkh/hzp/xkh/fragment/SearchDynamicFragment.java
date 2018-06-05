package com.xkh.hzp.xkh.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.SearchDynamicAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;

import java.util.HashMap;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseFragment;
import xkh.hzp.xkh.com.base.view.EmptyView;
import xkh.hzp.xkh.com.base.view.XkhLoadMoreView;

/**
 * @packageName com.xkh.hzp.xkh.fragment
 * @FileName SearchDynamicFragment
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class SearchDynamicFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView fragmentSearchRecycleView;
    private SearchDynamicAdapter searchDynamicAdapter;
    private int pageNum = 1, pageSize = 10;

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_search_dynamic;
    }

    private String getSearchKeywords() {
        if (getArguments() != null) {
            return (String) getArguments().get("search");
        }
        return "";
    }


    @Override
    public void initView(View contentView) {
        fragmentSearchRecycleView = contentView.findViewById(R.id.fragmentSearchRecycleView);
        fragmentSearchRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchDynamicAdapter = new SearchDynamicAdapter();
        searchDynamicAdapter.setLoadMoreView(new XkhLoadMoreView());
        searchDynamicAdapter.setOnLoadMoreListener(this, fragmentSearchRecycleView);
        EmptyView emptyView = new EmptyView(getActivity());
        emptyView.setNodataTitle("没有搜索到相关动态哟!");
        emptyView.setNodataImageSource(R.mipmap.note_empty);
        emptyView.setOperateBtnVisiable(false);
        searchDynamicAdapter.setEmptyView(emptyView);
        fragmentSearchRecycleView.setAdapter(searchDynamicAdapter);
        initData(pageNum, pageSize);
    }

    /****
     * 查询搜索动态
     * @param pageNum
     * @param pageSize
     */
    private void initData(final int pageNum, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", getSearchKeywords());
        params.put("pageNum", String.valueOf(pageNum));
        params.put("pageSize", String.valueOf(pageSize));

        ABHttp.getIns().get(UrlConfig.searchDynamic, params, new AbHttpCallback() {
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
                    List<DynamicBean> dynamicBeanList = (List<DynamicBean>) extra.get("result");
                    if (pageNum == 1) {
                        if (dynamicBeanList != null && dynamicBeanList.size() > 0) {
                            if (dynamicBeanList.size() < 10) {
                                searchDynamicAdapter.loadMoreEnd();
                                searchDynamicAdapter.setNewData(dynamicBeanList);
                            } else {
                                searchDynamicAdapter.setEnableLoadMore(true);
                                searchDynamicAdapter.setNewData(dynamicBeanList);
                            }
                        }
                    } else {
                        if (dynamicBeanList != null && dynamicBeanList.size() > 0) {
                            searchDynamicAdapter.loadMoreComplete();
                            searchDynamicAdapter.addData(dynamicBeanList);
                        } else {
                            searchDynamicAdapter.loadMoreComplete();
                            searchDynamicAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        });


    }

    @Override
    public void setListernner() {

    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        initData(pageNum, pageSize);
    }
}
