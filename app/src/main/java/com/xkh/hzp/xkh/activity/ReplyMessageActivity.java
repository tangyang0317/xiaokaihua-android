package com.xkh.hzp.xkh.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.ReplyMessageAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.ReplyMessageResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.EmptyView;
import xkh.hzp.xkh.com.base.view.XkhLoadMoreView;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName CommentMessageActivity
 * @Author tangyang
 * @DATE 2018/6/9
 **/
public class ReplyMessageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout talentClassSwipefreshLayout;
    private RecyclerView talentClassRecyclerView;
    private ReplyMessageAdapter commentMessageAdapter;
    private int pageNum = 1, pageSize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.activity_class_talent;
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(xkh.hzp.xkh.com.R.color.color_f3f5fa));
    }

    @Override
    public void initView() {
        setToolbarTitleTv("评论");
        talentClassSwipefreshLayout = findViewById(R.id.talentClassSwipefreshLayout);
        talentClassRecyclerView = findViewById(R.id.talentClassRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        talentClassRecyclerView.setLayoutManager(linearLayoutManager);
        talentClassSwipefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_ff5555));
        talentClassSwipefreshLayout.setOnRefreshListener(this);
        commentMessageAdapter = new ReplyMessageAdapter();
        EmptyView emptyView = new EmptyView(this);
        emptyView.setNodataTitle("暂无回复消息");
        emptyView.setOperateBtnVisiable(false);
        emptyView.setNodataImageSource(R.mipmap.note_empty);
        commentMessageAdapter.setEmptyView(emptyView);
        commentMessageAdapter.setLoadMoreView(new XkhLoadMoreView());
        commentMessageAdapter.setOnLoadMoreListener(this, talentClassRecyclerView);
        talentClassRecyclerView.setAdapter(commentMessageAdapter);
        initData(pageNum, pageSize);
    }

    /***
     * 加载数据
     * @param pageNum
     * @param pageSize
     */
    private void initData(final int pageNum, int pageSize) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", UserDataManager.getInstance().getUserId());
        params.put("pageNum", String.valueOf(pageNum));
        params.put("pageSize", String.valueOf(pageSize));

        ABHttp.getIns().get(UrlConfig.messageReply, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<ReplyMessageResult>>() {
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
                List<ReplyMessageResult> talentResults = (List<ReplyMessageResult>) extra.get("result");
                if (pageNum == 1) {
                    if (talentResults != null && talentResults.size() > 0) {
                        if (talentResults.size() < 10) {
                            commentMessageAdapter.loadMoreEnd();
                            commentMessageAdapter.setNewData(talentResults);
                        } else {
                            commentMessageAdapter.setEnableLoadMore(true);
                            commentMessageAdapter.setNewData(talentResults);
                        }
                    }
                } else {
                    if (talentResults != null && talentResults.size() > 0) {
                        commentMessageAdapter.loadMoreComplete();
                        commentMessageAdapter.addData(talentResults);
                    } else {
                        commentMessageAdapter.loadMoreComplete();
                        commentMessageAdapter.loadMoreEnd();
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
