package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.BottomSheetDialogAdapter;
import com.xkh.hzp.xkh.adapter.CommentExpandAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.DialogItemBean;
import com.xkh.hzp.xkh.entity.result.CommentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.CheckLoginManager;
import com.xkh.hzp.xkh.utils.UserDataManager;
import com.xkh.hzp.xkh.view.SExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
import xkh.hzp.xkh.com.base.view.ActionSheetDialog;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName SeeMoreCommentActivity
 * @Author tangyang
 * @DATE 2018/6/7
 **/
public class SeeMoreCommentActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SExpandableListView.LoadingListener {

    private SwipeRefreshLayout moreCommentRefreshLayout;
    private NestedScrollView moreCommentNestedScrollView;
    private SExpandableListView moreCommentListView;
    private CommentExpandAdapter commentExpandAdapter;
    private int pageNum = 1, pageSize = 10;
    private BottomSheetDialog bottomSheetDialog;


    public static void lanuchActivity(Activity activity, String dynamicId) {
        Intent intent = new Intent(activity, SeeMoreCommentActivity.class);
        intent.putExtra("dynamicId", dynamicId);
        activity.startActivity(intent);
    }

    private String getDynamicId() {
        if (getIntent() != null) {
            return getIntent().getStringExtra("dynamicId");
        }
        return "";
    }

    @Override
    protected void setBaseContainerBg() {
        super.setBaseContainerBg();
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_see_more_comment;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("更多评论");
        moreCommentRefreshLayout = findViewById(R.id.moreCommentRefreshLayout);
        moreCommentNestedScrollView = findViewById(R.id.moreCommentNestedScrollView);
        moreCommentListView = findViewById(R.id.moreCommentListView);
        moreCommentRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_ff5555));
        moreCommentRefreshLayout.setOnRefreshListener(this);
        commentExpandAdapter = new CommentExpandAdapter(this, new ArrayList<CommentResult>());
        moreCommentListView.setLoadingMoreEnabled(true);
        moreCommentListView.setmLoadingListener(this);
        moreCommentListView.setAdapter(commentExpandAdapter);
        moreCommentListView.setGroupIndicator(null);
        moreCommentListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                CommentResult commentResult = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
                if (String.valueOf(commentResult.getCommentResult().getUserId()).equals(UserDataManager.getInstance().getUserId())) {
                    /****回复和删除自己的评论****/
                    List<ActionSheetDialog.SheetItem> dialogItemBeans = new ArrayList<>();
                    dialogItemBeans.add(new ActionSheetDialog.SheetItem("回复:" + commentResult.getCommentResult().getName(), "REPLY", ActionSheetDialog.SheetItemColor.Blue));
                    dialogItemBeans.add(new ActionSheetDialog.SheetItem("删除", "DEL", ActionSheetDialog.SheetItemColor.Blue));
                    commentAndDeleteDialog(dialogItemBeans, true, groupPosition, 0);
                } else {
                    /****回复别人的评论****/
                    List<ActionSheetDialog.SheetItem> dialogItemBeans = new ArrayList<>();
                    dialogItemBeans.add(new ActionSheetDialog.SheetItem("回复:" + commentResult.getCommentResult().getName(), "REPLY", ActionSheetDialog.SheetItemColor.Blue));
                    commentAndDeleteDialog(dialogItemBeans, true, groupPosition, 0);

                }
                return true;
            }
        });
        moreCommentListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                CommentResult commentResult = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
                if (commentResult != null) {
                    CommentResult.ReplyResult replyResult = commentResult.getReplyResults().get(childPosition);
                    if (String.valueOf(replyResult.getUserId()).equals(UserDataManager.getInstance().getUserId())) {
                        /****回复和删除自己的回复*****/
                        List<ActionSheetDialog.SheetItem> dialogItemBeans = new ArrayList<>();
                        dialogItemBeans.add(new ActionSheetDialog.SheetItem("回复:" + replyResult.getName(), "REPLY", ActionSheetDialog.SheetItemColor.Blue));
                        dialogItemBeans.add(new ActionSheetDialog.SheetItem("删除", "DEL", ActionSheetDialog.SheetItemColor.Blue));
                        commentAndDeleteDialog(dialogItemBeans, false, groupPosition, childPosition);
                    } else {
                        /****回复别人的回复*****/
                        List<ActionSheetDialog.SheetItem> dialogItemBeans = new ArrayList<>();
                        dialogItemBeans.add(new ActionSheetDialog.SheetItem("回复:" + replyResult.getName(), "REPLY", ActionSheetDialog.SheetItemColor.Blue));
                        commentAndDeleteDialog(dialogItemBeans, false, groupPosition, childPosition);
                    }

                }
                return true;
            }
        });

        moreCommentListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
        queryDynamiComment(pageNum, pageSize);
    }

    /***
     * 评论和删除Dialog
     */
    private void commentAndDeleteDialog(final List<ActionSheetDialog.SheetItem> itemBeans, final boolean isComment, final int groupPosition, final int childPosiiton) {

        CheckLoginManager.getInstance().isLogin(new CheckLoginManager.CheckLoginCallBack() {
            @Override
            public void isLogin(boolean isLogin) {
                if (!isLogin) {
                    SharedprefrenceHelper.getIns(SeeMoreCommentActivity.this).clear();
                    LoginActivity.lunchActivity(SeeMoreCommentActivity.this, null, LoginActivity.class);
                    return;
                } else {
                    new ActionSheetDialog(SeeMoreCommentActivity.this).builder()
                            .setCancelable(true)
                            .setCanceledOnTouchOutside(true)
                            .addSheetItemsList(itemBeans)
                            .addSheetItemListenner(new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(String key) {
                                    if ("DEL".equals(key)) {
                                        if (isComment) {
                                            //删除评论
                                            CommentResult commentResult = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
                                            deleteComment(commentResult.getCommentResult().getId(), UserDataManager.getInstance().getUserId(), groupPosition);
                                        } else {
                                            //删除回复
                                            CommentResult commentResult = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
                                            CommentResult.ReplyResult replyResult = commentResult.getReplyResults().get(childPosiiton);
                                            deleteReply(replyResult.getId(), UserDataManager.getInstance().getUserId(), groupPosition, childPosiiton);
                                        }
                                    } else if ("COMMPENT".equals(key)) {
                                        //评论
                                        showCommentEditDialog();
                                    } else if ("REPLY".equals(key)) {
                                        //回复
                                        showReplayDialog(groupPosition, childPosiiton, isComment);
                                    }
                                }
                            }).show();
                }
            }
        });
    }


    /***
     * 删除评论
     * @param commentOrReplyId
     * @param userId
     */
    private void deleteComment(long commentOrReplyId, String userId, final int groupPosition) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(commentOrReplyId));
        params.put("userId", userId);
        params.put("type", "comment");
        ABHttp.getIns().post(UrlConfig.commentDelete, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    if (result) {
                        commentExpandAdapter.deleteTheCommentData(groupPosition);
                    }
                }
            }
        });
    }


    /***
     * 删除评论
     * @param commentOrReplyId
     * @param userId
     */
    private void deleteReply(long commentOrReplyId, String userId, final int groupPosition, final int childPosition) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(commentOrReplyId));
        params.put("userId", userId);
        params.put("type", "reply");
        ABHttp.getIns().post(UrlConfig.commentDelete, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Boolean.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    boolean result = (boolean) extra.get("result");
                    if (result) {
                        commentExpandAdapter.deleteTheReply(groupPosition, childPosition);
                    }
                }
            }
        });
    }


    /***
     * 查询动态评论
     */
    private void queryDynamiComment(final int pageNum, int pageSize) {
        HashMap<String, String> param = new HashMap<>();
        param.put("pageNum", String.valueOf(pageNum));
        param.put("pageSize", String.valueOf(pageSize));
        param.put("dynamicId", getDynamicId());
        param.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().get(UrlConfig.dynamicComment, param, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<List<CommentResult>>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    List<CommentResult> commentResults = (List<CommentResult>) extra.get("result");
                    if (pageNum == 1) {
                        moreCommentRefreshLayout.setRefreshing(false);
                        if (commentResults != null && commentResults.size() > 0) {
                            commentExpandAdapter.setNewData(commentResults);
                            for (int i = 0; i < commentResults.size(); i++) {
                                moreCommentListView.expandGroup(i);
                            }
                        }
                    } else {
                        moreCommentListView.loadMoreComp();
                        if (commentResults != null && commentResults.size() > 0) {
                            commentExpandAdapter.addData(commentResults);
                            for (int i = 0; i < commentResults.size(); i++) {
                                moreCommentListView.expandGroup(i);
                            }
                        } else {
                            moreCommentListView.setNoMore(true);
                        }
                    }
                }
            }
        });
    }


    /***
     * 显示回复的输入框
     */
    private void showReplayDialog(final int groupPosition, final int childPosition, final boolean isComment) {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.inputDialog);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.view_dialog_edit, null);
        final EditText commentEdit = contentView.findViewById(R.id.commentEdit);
        final TextView commentSendTxt = contentView.findViewById(R.id.commentSendTxt);
        commentEdit.setHint("请输入您的回复(最多100字)");
        bottomSheetDialog.setContentView(contentView);
        final CommentResult commentResultBean = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
        CommentResult.ReplyResult replyResult = null;
        if (commentResultBean != null && (commentResultBean.getReplyResults() != null && commentResultBean.getReplyResults().size() > 0)) {
            replyResult = commentResultBean.getReplyResults().get(childPosition);
        }
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
        final CommentResult.ReplyResult finalReplyResult = replyResult;
        RxView.clicks(commentSendTxt).throttleFirst(2, TimeUnit.SECONDS)//在一秒内只取第一次点击
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String replayContent = commentEdit.getText().toString();
                        if (TextUtils.isEmpty(replayContent)) {
                            Toasty.warning(SeeMoreCommentActivity.this, "回复不能为空").show();
                            return;
                        }
                        CommentResult.ReplyResult reply = new CommentResult.ReplyResult();
                        if (isComment) {
                            reply.setReplyContent(replayContent);
                            reply.setParentId(0);
                            reply.setReplyUserId(commentResultBean.getCommentResult().getUserId());
                            reply.setReplyUserName(commentResultBean.getCommentResult().getName());
                            reply.setName(UserDataManager.getInstance().getUserNickName());
                            reply.setUserId(Long.parseLong(UserDataManager.getInstance().getUserId()));
                        } else {
                            reply.setReplyContent(replayContent);
                            reply.setParentId(finalReplyResult.getId());
                            reply.setReplyUserName(finalReplyResult.getName());
                            reply.setReplyUserId(finalReplyResult.getUserId());
                            reply.setName(UserDataManager.getInstance().getUserNickName());
                            reply.setUserId(Long.parseLong(UserDataManager.getInstance().getUserId()));
                        }
                        bottomSheetDialog.dismiss();
                        hideKeyBoard();
                        sendReply(groupPosition, reply, commentResultBean.getCommentResult().getId());
                    }
                });

    }


    /**
     * 显示编辑输入框Dialog
     */
    private void showCommentEditDialog() {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.inputDialog);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.view_dialog_edit, null);
        final EditText commentEdit = contentView.findViewById(R.id.commentEdit);
        final TextView commentSendTxt = contentView.findViewById(R.id.commentSendTxt);
        commentEdit.setHint("请输入您的评论(最多100字)");
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
                        if (TextUtils.isEmpty(commentEdit.getText().toString())) {
                            Toasty.warning(SeeMoreCommentActivity.this, "评论不能为空").show();
                            return;
                        }
                        CommentResult commentResult = new CommentResult();
                        CommentResult.CommentResultBean commentResultBean = new CommentResult.CommentResultBean();
                        commentResultBean.setComment(commentEdit.getText().toString());
                        commentResultBean.setCreateTime(System.currentTimeMillis());
                        commentResultBean.setHeadPortrait(UserDataManager.getInstance().getUserHeadPic());
                        commentResultBean.setLikeNumber(0);
                        commentResultBean.setName(UserDataManager.getInstance().getUserNickName());
                        commentResultBean.setStatus("");
                        commentResultBean.setUserId(Long.parseLong(UserDataManager.getInstance().getUserId()));
                        commentResult.setCommentResult(commentResultBean);
                        commentResult.setReplyResults(new ArrayList<CommentResult.ReplyResult>());
                        bottomSheetDialog.dismiss();
                        hideKeyBoard();
                        sendComment(commentEdit.getText().toString(), commentResult);
                    }
                });
    }

    /****
     * 发送回复
     * @param groupPosition
     * @param replyResult
     * @param commentId
     */
    private void sendReply(final int groupPosition, final CommentResult.ReplyResult replyResult, long commentId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("commentId", String.valueOf(commentId));
        params.put("replyUserId", String.valueOf(replyResult.getReplyUserId()));
        params.put("userId", String.valueOf(replyResult.getUserId()));
        params.put("replyContent", replyResult.getReplyContent());
        params.put("parentId", String.valueOf(0));

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
                    long replyId = (long) extra.get("result");
                    replyResult.setId(replyId);
                    commentExpandAdapter.addTheReplyData(replyResult, groupPosition);
                    bottomSheetDialog.dismiss();
                    hideKeyBoard();
                }
            }
        });

    }


    /***
     *发送评论
     */
    private void sendComment(String comment, final CommentResult commentResult) {
        HashMap<String, String> params = new HashMap<>();
        params.put("comment", comment);
        params.put("dynamicId", getDynamicId());
        params.put("replyUserId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().postJSON(UrlConfig.dynamicComment + "?userId=" + UserDataManager.getInstance().getUserId(), JsonUtils.toJson(params), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Long.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    long id = (long) extra.get("result");
                    commentResult.getCommentResult().setId(id);
                    commentExpandAdapter.addTheCommentData(commentResult);
                    bottomSheetDialog.dismiss();
                    hideKeyBoard();
                }
            }
        });

    }


    @Override
    public void setListenner() {
        queryDynamiComment(pageNum, pageSize);
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        queryDynamiComment(pageNum, pageSize);
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        queryDynamiComment(pageNum, pageSize);
    }
}
