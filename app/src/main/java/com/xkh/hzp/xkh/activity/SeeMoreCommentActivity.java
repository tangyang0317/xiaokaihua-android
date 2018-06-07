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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.BottomSheetDialogAdapter;
import com.xkh.hzp.xkh.adapter.CommentExpandAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.DialogItemBean;
import com.xkh.hzp.xkh.entity.result.CommentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.UserDataManager;
import com.xkh.hzp.xkh.view.SExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;

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
        //默认展开所有回复
        moreCommentListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                CommentResult commentResult = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
                if (String.valueOf(commentResult.getCommentResult().getUserId()).equals(UserDataManager.getInstance().getUserId())) {
                    List<DialogItemBean> dialogItemBeans = new ArrayList<>();
                    dialogItemBeans.add(new DialogItemBean("回复", "REPLY"));
                    dialogItemBeans.add(new DialogItemBean("删除", "DEL"));
                    dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
                    commentAndDeleteDialog(dialogItemBeans, true, groupPosition, 0);
                } else {
                    List<DialogItemBean> dialogItemBeans = new ArrayList<>();
                    dialogItemBeans.add(new DialogItemBean("回复", "REPLY"));
                    dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
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
                        List<DialogItemBean> dialogItemBeans = new ArrayList<>();
                        dialogItemBeans.add(new DialogItemBean("回复" + replyResult.getName(), "REPLY"));
                        dialogItemBeans.add(new DialogItemBean("删除", "DEL"));
                        dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
                        commentAndDeleteDialog(dialogItemBeans, false, groupPosition, childPosition);
                    } else {
                        List<DialogItemBean> dialogItemBeans = new ArrayList<>();
                        dialogItemBeans.add(new DialogItemBean("回复" + replyResult.getName(), "REPLY"));
                        dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
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
    private void commentAndDeleteDialog(List<DialogItemBean> itemBeans, final boolean isComment, final int groupPosition, final int childPosiiton) {
        final BottomSheetDialog dialog = new BottomSheetDialog(SeeMoreCommentActivity.this);
        View dialogView = LayoutInflater.from(SeeMoreCommentActivity.this).inflate(R.layout.dialog_comment_reply, null);
        RecyclerView listView = dialogView.findViewById(R.id.listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SeeMoreCommentActivity.this);
        listView.setLayoutManager(linearLayoutManager);
        BottomSheetDialogAdapter bottomSheetDialogAdapter = new BottomSheetDialogAdapter();
        bottomSheetDialogAdapter.setNewData(itemBeans);
        listView.setAdapter(bottomSheetDialogAdapter);
        dialog.setContentView(dialogView);
        bottomSheetDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DialogItemBean dialogItemBean = (DialogItemBean) adapter.getItem(position);
                if (dialogItemBean.getOperate().equals("DEL")) {
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
                } else if (dialogItemBean.getOperate().equals("COMMPENT")) {
                    //评论
                    dialog.dismiss();
                    showCommentEditDialog();
                } else if (dialogItemBean.getOperate().equals("REPLY")) {
                    //回复
                    dialog.dismiss();
                    showReplayDialog(groupPosition);
                } else if (dialogItemBean.getOperate().equals("CANCLE")) {
                    //取消
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
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
    private void showReplayDialog(final int groupPosition) {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.inputDialog);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.view_dialog_edit, null);
        final EditText commentEdit = contentView.findViewById(R.id.commentEdit);
        final TextView commentSendTxt = contentView.findViewById(R.id.commentSendTxt);
        bottomSheetDialog.setContentView(contentView);
        final CommentResult.CommentResultBean commentResultBean = commentExpandAdapter.getCommentData(groupPosition);

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

        commentSendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replayContent = commentEdit.getText().toString();
                CommentResult.ReplyResult replyResult = new CommentResult.ReplyResult();
                replyResult.setReplyContent(replayContent);
                replyResult.setReplyUserId(commentResultBean.getUserId());
                replyResult.setReplyUserName(commentResultBean.getName());
                replyResult.setName(UserDataManager.getInstance().getUserNickName());
                replyResult.setUserId(Long.parseLong(UserDataManager.getInstance().getUserId()));
                sendReply(groupPosition, replyResult, commentResultBean.getId());
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

        commentSendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentResult commentResult = new CommentResult();
                CommentResult.CommentResultBean commentResultBean = new CommentResult.CommentResultBean();
                commentResultBean.setComment(commentEdit.getText().toString());
                commentResultBean.setCreateTime(System.currentTimeMillis());
                commentResultBean.setHeadPortrait(UserDataManager.getInstance().getUserHeadPic());
                commentResultBean.setLikeNumber(0);
                commentResultBean.setName(UserDataManager.getInstance().getUserNickName());
                commentResultBean.setStatus("normal");
                commentResultBean.setUserId(Long.parseLong(UserDataManager.getInstance().getUserId()));
                commentResult.setCommentResult(commentResultBean);
                commentResult.setReplyResults(new ArrayList<CommentResult.ReplyResult>());
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
