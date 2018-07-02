package com.xkh.hzp.xkh.activity;

import android.content.DialogInterface;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.CommentMessageAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.entity.result.CommentMessageResult;
import com.xkh.hzp.xkh.entity.result.CommentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.EmptyView;
import xkh.hzp.xkh.com.base.view.XkhLoadMoreView;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName CommentMessageActivity
 * @Author tangyang
 * @DATE 2018/6/9
 **/
public class CommentMessageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout talentClassSwipefreshLayout;
    private RecyclerView talentClassRecyclerView;
    private CommentMessageAdapter commentMessageAdapter;
    private int pageNum = 1, pageSize = 10;
    private BottomSheetDialog bottomSheetDialog;

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
        setToolbarTitleTv("回复");
        talentClassSwipefreshLayout = findViewById(R.id.talentClassSwipefreshLayout);
        talentClassRecyclerView = findViewById(R.id.talentClassRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        talentClassRecyclerView.setLayoutManager(linearLayoutManager);
        talentClassSwipefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_ff5555));
        talentClassSwipefreshLayout.setOnRefreshListener(this);
        commentMessageAdapter = new CommentMessageAdapter(false);
        EmptyView emptyView = new EmptyView(this);
        emptyView.setNodataTitle("暂无用户评论");
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

        ABHttp.getIns().get(UrlConfig.messageComment, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<CommentMessageResult>>() {
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
                List<CommentMessageResult> talentResults = (List<CommentMessageResult>) extra.get("result");
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
        commentMessageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CommentMessageResult commentMessageResult = (CommentMessageResult) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.replyTxt:
                        showReplayDialog(commentMessageResult);
                        break;
                }
            }
        });
    }


    /***
     * 显示回复的输入框
     */
    private void showReplayDialog(final CommentMessageResult commentMessageResult) {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.inputDialog);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.view_dialog_edit, null);
        final EditText commentEdit = contentView.findViewById(R.id.commentEdit);
        final TextView commentSendTxt = contentView.findViewById(R.id.commentSendTxt);
        commentEdit.setHint("请输入您的回复(最多100字)");
        bottomSheetDialog.setContentView(contentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) contentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        contentView.measure(0, 0);
        behavior.setPeekHeight(contentView.getMeasuredHeight());
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                hideKeyBoard();
            }
        });
        RxView.clicks(commentSendTxt).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String replayContent = commentEdit.getText().toString();
                        if (TextUtils.isEmpty(replayContent)) {
                            Toasty.warning(CommentMessageActivity.this, "回复不能为空").show();
                            return;
                        }
                        sendReply(commentMessageResult, replayContent);
                    }
                });

    }


    /***
     * 发送回复
     * @param commentMessageResult
     * @param replyContent
     */
    private void sendReply(final CommentMessageResult commentMessageResult, String replyContent) {
        HashMap<String, String> params = new HashMap<>();
        params.put("commentId", String.valueOf(commentMessageResult.getCommentId()));
        params.put("replyUserId", String.valueOf(commentMessageResult.getReplyUserId()));
        params.put("userId", String.valueOf(commentMessageResult.getUserId()));
        params.put("replyContent", replyContent);
        params.put("parentId", String.valueOf(commentMessageResult.getReplyId()));

        ABHttp.getIns().post(UrlConfig.commentReply, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Long.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    bottomSheetDialog.dismiss();
                    hideKeyBoard();
                }
            }
        });

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
