package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.BottomSheetDialogAdapter;
import com.xkh.hzp.xkh.adapter.CommentExpandAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.DialogItemBean;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.entity.result.CommentResult;
import com.xkh.hzp.xkh.entity.result.DynamicDetailsResult;
import com.xkh.hzp.xkh.entity.result.TalentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.PraiseUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;
import com.xkh.hzp.xkh.view.CommentExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.DimentUtils;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName GraphicDynamicDetailsActivity
 * @Author tangyang
 * @DATE 2018/5/10
 */

public class GraphicDynamicDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView userHeadImg, commentMineImg, detailsPraiseImg;
    private TextView userNickNameTxt, commentTxt, userIsAttentionTxt, dynamicContentTxt, dynamicDateTxt;
    private LinearLayout contentEditLayout, vgBottomInfo;
    private RelativeLayout detailsShareLayout, detailsCommentLayout, detailsPraiseLayout;
    private TextView detailsCommentTxt, detailsPraiseTxt;
    private TextView seeMoreCommentTxt;
    private View footerView;
    private LinearLayout dynamicDetailsImgLayout;
    private NestedScrollView graphicDetailsNestedScrollView;
    private CommentExpandableListView commentExpandableListView;
    private CommentExpandAdapter commentExpandAdapter;
    private BottomSheetDialog bottomSheetDialog;
    /***点赞关注状态***/
    private String likeStatus, foucsStatus;
    /***点赞和评论数量***/
    private int commentCount, likeCount;
    private long userId, dynamicId;
    private UMWeb umWeb;
    private ShareBoardlistener shareBoardlistener;


    public static void lanuchActivity(Activity activity, String dynamicId) {
        Intent intent = new Intent(activity, GraphicDynamicDetailsActivity.class);
        intent.putExtra("dynamicId", dynamicId);
        activity.startActivity(intent);
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

    private String getDynamicId() {
        return getIntent().getStringExtra("dynamicId");
    }

    @Override
    protected void setBaseContainerBg() {
        baseContainerLayout.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_graphic_dynamic_details;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("动态详情");
        hideToolbarBottomLine();
        setTitleNavigationIcon(R.drawable.icon_back);
        setToolBarTitleTextColor(getResources().getColor(R.color.color_ffffff));
        commentTxt = findViewById(R.id.commentTxt);
        commentMineImg = findViewById(R.id.commentMineImg);
        userNickNameTxt = findViewById(R.id.userNickNameTxt);
        userIsAttentionTxt = findViewById(R.id.userIsAttentionTxt);
        dynamicContentTxt = findViewById(R.id.dynamicContentTxt);
        userHeadImg = findViewById(R.id.userHeadImg);
        dynamicDateTxt = findViewById(R.id.dynamicDateTxt);
        detailsCommentTxt = findViewById(R.id.detailsCommentTxt);
        detailsPraiseTxt = findViewById(R.id.detailsPraiseTxt);
        graphicDetailsNestedScrollView = findViewById(R.id.graphicDetailsNestedScrollView);
        vgBottomInfo = findViewById(R.id.vgBottomInfo);
        detailsShareLayout = findViewById(R.id.detailsShareLayout);
        detailsCommentLayout = findViewById(R.id.detailsCommentLayout);
        detailsPraiseLayout = findViewById(R.id.detailsPraiseLayout);
        detailsPraiseImg = findViewById(R.id.detailsPraiseImg);
        commentExpandableListView = findViewById(R.id.commentExpandableListView);
        dynamicDetailsImgLayout = findViewById(R.id.dynamicDetailsImgLayout);
        footerView = LayoutInflater.from(this).inflate(R.layout.view_see_more_comment, null);
        seeMoreCommentTxt = footerView.findViewById(R.id.seeMoreCommentTxt);
        commentExpandableListView.addFooterView(footerView);
        commentExpandAdapter = new CommentExpandAdapter(this, new ArrayList<CommentResult>());
        commentExpandableListView.setAdapter(commentExpandAdapter);
        commentExpandableListView.setGroupIndicator(null);
        //默认展开所有回复

        commentExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                CommentResult commentResult = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
                if (String.valueOf(commentResult.getCommentResult().getUserId()).equals(UserDataManager.getInstance().getUserId())) {
                    /****回复和删除自己的评论****/
                    List<DialogItemBean> dialogItemBeans = new ArrayList<>();
                    dialogItemBeans.add(new DialogItemBean("回复", "REPLY"));
                    dialogItemBeans.add(new DialogItemBean("删除", "DEL"));
                    dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
                    commentAndDeleteDialog(dialogItemBeans, true, groupPosition, 0);
                } else {
                    /****回复别人的评论****/
                    List<DialogItemBean> dialogItemBeans = new ArrayList<>();
                    dialogItemBeans.add(new DialogItemBean("回复" + commentResult.getCommentResult().getName(), "REPLY"));
                    dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
                    commentAndDeleteDialog(dialogItemBeans, true, groupPosition, 0);
                }
                return true;
            }
        });

        commentExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                CommentResult commentResult = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
                if (commentResult != null) {
                    CommentResult.ReplyResult replyResult = commentResult.getReplyResults().get(childPosition);
                    if (String.valueOf(replyResult.getUserId()).equals(UserDataManager.getInstance().getUserId())) {
                        /****回复和删除自己的回复*****/
                        List<DialogItemBean> dialogItemBeans = new ArrayList<>();
                        dialogItemBeans.add(new DialogItemBean("回复", "REPLY"));
                        dialogItemBeans.add(new DialogItemBean("删除", "DEL"));
                        dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
                        commentAndDeleteDialog(dialogItemBeans, false, groupPosition, childPosition);
                    } else {
                        /****回复别人的回复*****/
                        List<DialogItemBean> dialogItemBeans = new ArrayList<>();
                        dialogItemBeans.add(new DialogItemBean("回复" + replyResult.getName(), "REPLY"));
                        dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
                        commentAndDeleteDialog(dialogItemBeans, false, groupPosition, childPosition);
                    }

                }
                return true;
            }
        });

        commentExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        queryDynamicDetails();
        queryDynamiComment(1, 10);
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
     * 评论和删除Dialog
     */
    private void commentAndDeleteDialog(List<DialogItemBean> itemBeans, final boolean isComment, final int groupPosition, final int childPosiiton) {
        final BottomSheetDialog dialog = new BottomSheetDialog(GraphicDynamicDetailsActivity.this);
        View dialogView = LayoutInflater.from(GraphicDynamicDetailsActivity.this).inflate(R.layout.dialog_comment_reply, null);
        RecyclerView listView = dialogView.findViewById(R.id.listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GraphicDynamicDetailsActivity.this);
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
                        dialog.dismiss();
                        deleteComment(commentResult.getCommentResult().getId(), UserDataManager.getInstance().getUserId(), groupPosition);
                    } else {
                        //删除回复
                        CommentResult commentResult = (CommentResult) commentExpandAdapter.getGroup(groupPosition);
                        CommentResult.ReplyResult replyResult = commentResult.getReplyResults().get(childPosiiton);
                        dialog.dismiss();
                        deleteReply(replyResult.getId(), UserDataManager.getInstance().getUserId(), groupPosition, childPosiiton);
                    }
                } else if (dialogItemBean.getOperate().equals("COMMPENT")) {
                    //评论
                    dialog.dismiss();
                    showCommentEditDialog();
                } else if (dialogItemBean.getOperate().equals("REPLY")) {
                    //回复
                    dialog.dismiss();
                    showReplayDialog(groupPosition, childPosiiton, isComment);
                } else if (dialogItemBean.getOperate().equals("CANCLE")) {
                    //取消
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    /***
     * 查询动态评论
     */
    private void queryDynamiComment(int pageNum, int pageSize) {
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
                    if (commentResults == null || commentResults.size() < 9) {
                        commentExpandableListView.removeFooterView(footerView);
                    }
                    if (commentResults != null && commentResults.size() > 0) {
                        commentExpandAdapter.setNewData(commentResults);
                        for (int i = 0; i < commentResults.size(); i++) {
                            commentExpandableListView.expandGroup(i);
                        }
                    }
                }
            }
        });
    }

    /**
     * 根据动态Id查询动态详情
     */
    private void queryDynamicDetails() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().get(UrlConfig.queryDynamicById + "/" + getDynamicId(), params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<DynamicDetailsResult>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    DynamicDetailsResult dynamicBean = (DynamicDetailsResult) extra.get("result");
                    if (dynamicBean != null) {
                        setUIData(dynamicBean);
                    }
                }
            }
        });
    }


    /****
     * 绑定UI数据
     * @param dynamicBean
     */
    private void setUIData(DynamicDetailsResult dynamicBean) {
        likeCount = dynamicBean.getXkhTalentDynamic().getLikeNumber();
        likeStatus = dynamicBean.getLikeStatus();
        commentCount = dynamicBean.getCommentNumber();
        foucsStatus = dynamicBean.getFocusStatus();
        userId = dynamicBean.getUserSimpleResult().getUserId();
        dynamicId = dynamicBean.getXkhTalentDynamic().getId();


        if (dynamicBean.getUserSimpleResult() != null) {
            Glide.with(this).load(dynamicBean.getUserSimpleResult().getHeadPortrait()).transform(new GlideCircleTransform(this)).placeholder(R.mipmap.icon_female_selected).placeholder(R.mipmap.icon_female_selected).into(userHeadImg);
            userNickNameTxt.setText(dynamicBean.getUserSimpleResult().getName());
        }
        detailsCommentTxt.setText(String.valueOf(commentCount));
        if ("normal".equals(likeStatus)) {
            //已点赞
            detailsPraiseImg.setImageResource(R.mipmap.icon_praised);
        } else {
            //未点赞
            detailsPraiseImg.setImageResource(R.mipmap.icon_unpraised);
        }
        if ("focus".equals(foucsStatus)) {
            userIsAttentionTxt.setText("已关注");
        } else {
            userIsAttentionTxt.setText("+ 关注");
        }
        detailsPraiseTxt.setText(String.valueOf(likeCount));
        if (dynamicBean.getXkhTalentDynamic() != null) {
            dynamicContentTxt.setText(dynamicBean.getXkhTalentDynamic().getWordDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            dynamicDateTxt.setText(sdf.format(dynamicBean.getXkhTalentDynamic().getUpdateTime()));
        }

        if (dynamicBean.getXkhTalentDynamicAnnexList() != null && dynamicBean.getXkhTalentDynamicAnnexList().size() > 0) {
            for (int i = 0; i < dynamicBean.getXkhTalentDynamicAnnexList().size(); i++) {
                ImageView dynamicDetailsImg = (ImageView) LayoutInflater.from(this).inflate(R.layout.view_item_dynamic_details_img, null);
                int margin = DimentUtils.dip2px(this, 15);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(DimentUtils.getScreenWidth(this) - margin * 2, DimentUtils.dip2px(this, 200));
                layoutParams1.setMargins(margin, margin, margin, 0);
                dynamicDetailsImg.setLayoutParams(layoutParams1);
                dynamicDetailsImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(dynamicBean.getXkhTalentDynamicAnnexList().get(i).getAnnexUrl()).placeholder(R.drawable.shape_place_holder).placeholder(R.drawable.shape_place_holder).into(dynamicDetailsImg);
                dynamicDetailsImgLayout.addView(dynamicDetailsImg);
            }
            initShare("http://wwww.baidu.com", dynamicBean.getXkhTalentDynamic().getWordDescription(), dynamicBean.getXkhTalentDynamicAnnexList().get(0).getAnnexUrl());
        }
    }

    /***
     * 显示回复的输入框
     */
    private void showReplayDialog(final int groupPosition, final int childPosition, final boolean isComment) {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.inputDialog);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.view_dialog_edit, null);
        final EditText commentEdit = contentView.findViewById(R.id.commentEdit);
        final TextView commentSendTxt = contentView.findViewById(R.id.commentSendTxt);
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
        commentSendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replayContent = commentEdit.getText().toString();
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
                commentResultBean.setStatus("");
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
                    commentCount++;
                    detailsCommentTxt.setText(String.valueOf(commentCount));
                    commentExpandAdapter.addTheCommentData(commentResult);
                    bottomSheetDialog.dismiss();
                    hideKeyBoard();
                }
            }
        });

    }

    /**
     * 初始化分享
     */
    private void initShare(String sharedUrl, String shareContent, String shareThumbImg) {
        //友盟分享内容
        umWeb = new UMWeb(sharedUrl);
        umWeb.setTitle(shareContent);
        umWeb.setThumb(new UMImage(GraphicDynamicDetailsActivity.this, shareThumbImg));
        umWeb.setDescription(shareContent);
        shareBoardlistener = new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                if (share_media != null) {
                    new ShareAction(GraphicDynamicDetailsActivity.this)
                            .setPlatform(share_media)
                            .setCallback(shareListener)
                            .withMedia(umWeb)
                            .share();
                }
            }
        };
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(GraphicDynamicDetailsActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(GraphicDynamicDetailsActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(GraphicDynamicDetailsActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };


    @Override
    public void setListenner() {
        detailsCommentLayout.setOnClickListener(this);
        commentTxt.setOnClickListener(this);
        seeMoreCommentTxt.setOnClickListener(this);
        userIsAttentionTxt.setOnClickListener(this);
        detailsPraiseLayout.setOnClickListener(this);
        detailsShareLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == detailsCommentLayout) {
            List<DialogItemBean> dialogItemBeans = new ArrayList<>();
            dialogItemBeans.add(new DialogItemBean("评论", "COMMPENT"));
            dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
            commentAndDeleteDialog(dialogItemBeans, true, 0, 0);
        } else if (view == detailsShareLayout) {
            new ShareAction(GraphicDynamicDetailsActivity.this)
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA, SHARE_MEDIA.QZONE)
                    .setShareboardclickCallback(shareBoardlistener)
                    .open();
        } else if (view == commentTxt) {
            List<DialogItemBean> dialogItemBeans = new ArrayList<>();
            dialogItemBeans.add(new DialogItemBean("评论", "COMMPENT"));
            dialogItemBeans.add(new DialogItemBean("取消", "CANCLE"));
            commentAndDeleteDialog(dialogItemBeans, true, 0, 0);
        } else if (view == seeMoreCommentTxt) {
            SeeMoreCommentActivity.lanuchActivity(GraphicDynamicDetailsActivity.this, getDynamicId());
        } else if (view == userIsAttentionTxt) {
            if ("focus".equals(foucsStatus)) {
                //取消关注
                cancleFocusTalent(userId);
            } else {
                //关注
                focusTalent(userId);
            }
        } else if (view == detailsPraiseLayout) {
            if ("normal".equals(likeStatus)) {
                //取消点赞
                PraiseUtils.getIns().unPraise(String.valueOf(dynamicId), String.valueOf(userId), new PraiseUtils.UnPraisedCallback() {

                    @Override
                    public void onFail() {
                        Toasty.error(GraphicDynamicDetailsActivity.this, "取消点赞失败").show();
                    }

                    @Override
                    public void notLogin() {
                        SharedprefrenceHelper.getIns(GraphicDynamicDetailsActivity.this).clear();
                        LoginActivity.lunchActivity(GraphicDynamicDetailsActivity.this, null, LoginActivity.class);
                    }

                    @Override
                    public void onUnPraise() {
                        detailsPraiseImg.setImageResource(R.mipmap.icon_unpraised);
                        ScaleAnimation viewShowAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        viewShowAnimation.setDuration(500);
                        detailsPraiseImg.startAnimation(viewShowAnimation);
                        likeStatus = "";
                        likeCount--;
                        detailsPraiseTxt.setText(String.valueOf(likeCount));
                    }
                });
            } else {
                //点赞
                PraiseUtils.getIns().praise(String.valueOf(dynamicId), String.valueOf(userId), new PraiseUtils.PraisedCallback() {
                    @Override
                    public void onPraise() {
                        detailsPraiseImg.setImageResource(R.mipmap.icon_praised);
                        ScaleAnimation viewShowAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        viewShowAnimation.setDuration(500);
                        detailsPraiseImg.startAnimation(viewShowAnimation);
                        likeCount++;
                        likeStatus = "normal";
                        detailsPraiseTxt.setText(String.valueOf(likeCount));
                    }

                    @Override
                    public void onFail() {
                        Toasty.error(GraphicDynamicDetailsActivity.this, "点赞失败").show();
                    }

                    @Override
                    public void notLogin() {
                        SharedprefrenceHelper.getIns(GraphicDynamicDetailsActivity.this).clear();
                        LoginActivity.lunchActivity(GraphicDynamicDetailsActivity.this, null, LoginActivity.class);
                    }

                });

            }
        }
    }

    /**
     * 关注
     *
     * @param talentUserId
     */
    private void focusTalent(long talentUserId) {
        HashMap<String, String> param = new HashMap<>();
        param.put("beFocusUserId", String.valueOf(talentUserId));
        param.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().post(UrlConfig.foucsTalent, param, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
            }

            @Override
            public void onNotLogin() {
                super.onNotLogin();
                SharedprefrenceHelper.getIns(GraphicDynamicDetailsActivity.this).clear();
                LoginActivity.lunchActivity(GraphicDynamicDetailsActivity.this, null, LoginActivity.class);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    Toasty.info(GraphicDynamicDetailsActivity.this, "关注成功").show();
                    userIsAttentionTxt.setText("已关注");
                    foucsStatus = "focus";
                }
            }
        });
    }

    /**
     * 取消关注
     *
     * @param talentUserId
     */
    private void cancleFocusTalent(long talentUserId) {
        HashMap<String, String> param = new HashMap<>();
        param.put("beFocusUserId", String.valueOf(talentUserId));
        param.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().post(UrlConfig.cancleFoucsTalent, param, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
            }

            @Override
            public void onNotLogin() {
                super.onNotLogin();
                SharedprefrenceHelper.getIns(GraphicDynamicDetailsActivity.this).clear();
                LoginActivity.lunchActivity(GraphicDynamicDetailsActivity.this, null, LoginActivity.class);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    Toasty.info(GraphicDynamicDetailsActivity.this, "取消关注成功").show();
                    userIsAttentionTxt.setText("+关注");
                    foucsStatus = "已关注";
                }
            }
        });
    }

    /***
     * umeng分享
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
