package com.xkh.hzp.xkh.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.CommentExpandAdapter;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.CommentBean;
import com.xkh.hzp.xkh.entity.CommentDetailBean;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.entity.ReplyDetailBean;
import com.xkh.hzp.xkh.entity.result.DynamicDetailsResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.view.CommentExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName GraphicDynamicDetailsActivity
 * @Author tangyang
 * @DATE 2018/5/10
 */

public class GraphicDynamicDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView userHeadImg;
    private TextView userNickNameTxt, userIsAttentionTxt, dynamicContentTxt;
    private LinearLayout contentEditLayout, vgBottomInfo;
    private RelativeLayout detailsShareLayout, detailsCommentLayout, detailsPraiseLayout;
    private EditText etComment;
    private TextView btnSend, dynamicDateTxt;
    private LinearLayout dynamicDetailsImgLayout;
    private NestedScrollView graphicDetailsNestedScrollView;
    private CommentExpandableListView commentExpandableListView;
    private CommentExpandAdapter commentExpandAdapter;
    private List<CommentDetailBean> commentsList;
    private CommentBean commentBean;
    private BottomSheetDialog bottomSheetDialog;

    public static void lanuchActivity(Activity activity, String dynamicId) {
        Intent intent = new Intent(activity, GraphicDynamicDetailsActivity.class);
        intent.putExtra("dynamicId", dynamicId);
        activity.startActivity(intent);
    }

    private String getDynamicId() {
        return getIntent().getStringExtra("dynamicId");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_graphic_dynamic_details;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("动态详情");
        userNickNameTxt = findViewById(R.id.userNickNameTxt);
        userIsAttentionTxt = findViewById(R.id.userIsAttentionTxt);
        dynamicContentTxt = findViewById(R.id.dynamicContentTxt);
        userHeadImg = findViewById(R.id.userHeadImg);
        graphicDetailsNestedScrollView = findViewById(R.id.graphicDetailsNestedScrollView);
        contentEditLayout = findViewById(R.id.contentEditLayout);
        vgBottomInfo = findViewById(R.id.vgBottomInfo);
        detailsShareLayout = findViewById(R.id.detailsShareLayout);
        detailsCommentLayout = findViewById(R.id.detailsCommentLayout);
        detailsPraiseLayout = findViewById(R.id.detailsPraiseLayout);
        etComment = findViewById(R.id.etComment);
        btnSend = findViewById(R.id.btnSend);
        commentExpandableListView = findViewById(R.id.commentExpandableListView);
        dynamicDetailsImgLayout = findViewById(R.id.dynamicDetailsImgLayout);
        commentsList = generateTestData();
        commentExpandAdapter = new CommentExpandAdapter(this, commentsList);
        commentExpandableListView.setAdapter(commentExpandAdapter);
        commentExpandableListView.setGroupIndicator(null);
        //默认展开所有回复
        for (int i = 0; i < commentsList.size(); i++) {
            commentExpandableListView.expandGroup(i);
        }
        commentExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
//                showReplyDialog(groupPosition);
                return true;
            }
        });

        commentExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                return false;
            }
        });

        commentExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        queryDynamicDetails();
    }

    /**
     * 根据动态Id查询动态详情
     */
    private void queryDynamicDetails() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("dynamicId", getDynamicId());
        ABHttp.getIns().restfulGet(UrlConfig.queryDynamicById, params, new AbHttpCallback() {
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
        if (dynamicBean.getUcUser() != null) {
            Glide.with(this).load(dynamicBean.getUcUser().getHeadPortrait()).transform(new GlideCircleTransform(this)).placeholder(R.mipmap.icon_female_selected).placeholder(R.mipmap.icon_female_selected).into(userHeadImg);
            userNickNameTxt.setText(dynamicBean.getUcUser().getName());
        }

        if (dynamicBean.getXkhTalentDynamic() != null) {
            dynamicContentTxt.setText(dynamicBean.getXkhTalentDynamic().getWordDescription());
//            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//            Date date = new Date(dynamicBean.getXkhTalentDynamic().getUpdateTime());
//            dynamicDateTxt.setText(sdf.format(date));
        }

        if (dynamicBean.getXkhTalentDynamicAnnexList() != null && dynamicBean.getXkhTalentDynamicAnnexList().size() > 0) {
            for (int i = 0; i < dynamicBean.getXkhTalentDynamicAnnexList().size(); i++) {
                ImageView dynamicDetailsImg = (ImageView) LayoutInflater.from(this).inflate(R.layout.view_item_dynamic_details_img, null);
                int margin = DimentUtils.dip2px(this, 15);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(DimentUtils.getScreenWidth(this) - margin * 2, DimentUtils.dip2px(this, 200));
                layoutParams1.setMargins(margin, margin, margin, 0);
                dynamicDetailsImg.setLayoutParams(layoutParams1);
                Glide.with(this).load(dynamicBean.getXkhTalentDynamicAnnexList().get(i).getAnnexUrl()).transform(new GlideCircleTransform(this)).placeholder(R.mipmap.icon_female_selected).placeholder(R.mipmap.icon_female_selected).into(dynamicDetailsImg);
                dynamicDetailsImgLayout.addView(dynamicDetailsImg);
            }
        }
    }


    /**
     * 显示编辑输入框Dialog
     */
    private void showEditDialog() {
        bottomSheetDialog = new BottomSheetDialog(this, R.style.inputDialog);
        final View contentView = LayoutInflater.from(this).inflate(R.layout.view_dialog_edit, null);
        final EditText commentEdit = contentView.findViewById(R.id.commentEdit);
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
                InputMethodManager inputMgr = (InputMethodManager) GraphicDynamicDetailsActivity.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
            }
        });

    }


    private List<CommentDetailBean> generateTestData() {
        List<CommentDetailBean> commentDetailBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CommentDetailBean commentDetailBean = new CommentDetailBean("爱吃糖的羊", "这条笔记不错" + i, "一分钟前");
            List<ReplyDetailBean> replyDetailBeanList = new ArrayList<ReplyDetailBean>();
            for (int j = 0; j < 5; j++) {
                ReplyDetailBean replyDetailBean = new ReplyDetailBean("勒布朗", "下场比赛一定要赢");
                replyDetailBeanList.add(replyDetailBean);
            }
            commentDetailBean.setReplyList(replyDetailBeanList);
            commentDetailBeanList.add(commentDetailBean);
        }
        return commentDetailBeanList;
    }

    @Override
    public void setListenner() {
        detailsCommentLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == detailsCommentLayout) {
            showEditDialog();

        }
    }
}
