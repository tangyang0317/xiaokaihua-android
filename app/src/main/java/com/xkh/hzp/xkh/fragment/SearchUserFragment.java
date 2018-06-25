package com.xkh.hzp.xkh.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.TalentHomePageActivity;
import com.xkh.hzp.xkh.adapter.SearchUserAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.SearchUserResult;
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
 * @FileName SearchUserFragment
 * @Author tangyang
 * @DATE 2018/5/17
 **/
public class SearchUserFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView fragmentSearchRecycleView;
    private SearchUserAdapter searchUserAdapter;
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
        searchUserAdapter = new SearchUserAdapter();
        searchUserAdapter.setLoadMoreView(new XkhLoadMoreView());
        searchUserAdapter.setOnLoadMoreListener(this, fragmentSearchRecycleView);
        EmptyView emptyView = new EmptyView(getActivity());
        emptyView.setNodataTitle("没有搜索到相关达人哟!");
        emptyView.setNodataImageSource(R.mipmap.note_empty);
        emptyView.setOperateBtnVisiable(false);
        searchUserAdapter.setEmptyView(emptyView);
        fragmentSearchRecycleView.setAdapter(searchUserAdapter);
        initData(pageNum, pageSize);
    }

    /****
     * 查询搜索动态
     * @param pageNum
     * @param pageSize
     */
    private void initData(final int pageNum, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userType", "talent");
        params.put("name", getSearchKeywords());
        params.put("limit", String.valueOf(pageSize));
        params.put("offset", String.valueOf((pageNum - 1) * pageSize));
        ABHttp.getIns().get(UrlConfig.searchUser, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<SearchUserResult>>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    List<SearchUserResult> dynamicBeanList = (List<SearchUserResult>) extra.get("result");
                    if (pageNum == 1) {
                        if (dynamicBeanList != null && dynamicBeanList.size() > 0) {
                            if (dynamicBeanList.size() < 10) {
                                searchUserAdapter.loadMoreEnd();
                                searchUserAdapter.setNewData(dynamicBeanList);
                            } else {
                                searchUserAdapter.setEnableLoadMore(true);
                                searchUserAdapter.setNewData(dynamicBeanList);
                            }
                        }
                    } else {
                        if (dynamicBeanList != null && dynamicBeanList.size() > 0) {
                            searchUserAdapter.loadMoreComplete();
                            searchUserAdapter.addData(dynamicBeanList);
                        } else {
                            searchUserAdapter.loadMoreComplete();
                            searchUserAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void setListernner() {
        searchUserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchUserResult searchUserResult = (SearchUserResult) adapter.getItem(position);
                if (searchUserResult == null)
                    return;
                TalentHomePageActivity.lanuchActivity(getActivity(), String.valueOf(searchUserResult.getUserId()));
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        initData(pageNum, pageSize);
    }
}
