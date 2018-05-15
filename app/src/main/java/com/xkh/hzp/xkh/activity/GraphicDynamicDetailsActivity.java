package com.xkh.hzp.xkh.activity;

import android.content.Context;
import android.content.DialogInterface;
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

import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.adapter.CommentExpandAdapter;
import com.xkh.hzp.xkh.entity.CommentBean;
import com.xkh.hzp.xkh.entity.CommentDetailBean;
import com.xkh.hzp.xkh.entity.ReplyDetailBean;
import com.xkh.hzp.xkh.view.CommentExpandableListView;

import java.util.ArrayList;
import java.util.List;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.DimentUtils;
import xkh.hzp.xkh.com.base.utils.SoftKeyboardUtil;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName GraphicDynamicDetailsActivity
 * @Author tangyang
 * @DATE 2018/5/10
 */

public class GraphicDynamicDetailsActivity extends BaseActivity implements View.OnClickListener {

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_graphic_dynamic_details;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("动态详情");
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
        List<String> imgList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            imgList.add("");
        }
        for (int i = 0; i < imgList.size(); i++) {
            ImageView dynamicDetailsImg = (ImageView) LayoutInflater.from(this).inflate(R.layout.view_item_dynamic_details_img, null);
            int margin = DimentUtils.dip2px(this, 15);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(DimentUtils.getScreenWidth(this) - margin * 2, DimentUtils.dip2px(this, 200));
            layoutParams1.setMargins(margin, margin, margin, 0);
            dynamicDetailsImg.setLayoutParams(layoutParams1);
            dynamicDetailsImg.setImageResource(R.drawable.example);
            dynamicDetailsImgLayout.addView(dynamicDetailsImg);
        }
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
