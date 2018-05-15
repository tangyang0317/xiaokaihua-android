package com.xkh.hzp.xkh.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.CommentDetailBean;
import com.xkh.hzp.xkh.entity.ReplyDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Moos
 * E-mail: moosphon@gmail.com
 * Date:  18/4/20.
 * Desc: 评论与回复列表的适配器
 */

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<CommentDetailBean> commentBeanList;
    private List<ReplyDetailBean> replyBeanList;
    private Context context;
    private int pageIndex = 1;

    public CommentExpandAdapter(Context context, List<CommentDetailBean> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    public void setNewData(List<CommentDetailBean> newCommentBeanList) {
        if (newCommentBeanList != null) {
            if (commentBeanList != null) {
                commentBeanList.clear();
                commentBeanList.addAll(newCommentBeanList);
            } else {
                commentBeanList.addAll(newCommentBeanList);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("新数据为kong!");
        }

    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (commentBeanList.get(i).getReplyList() == null) {
            return 0;
        } else {
            return commentBeanList.get(i).getReplyList().size() > 0 ? commentBeanList.get(i).getReplyList().size() : 0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getReplyList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    boolean isLike = false;

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_item_comment_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        Glide.with(context).load(R.drawable.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(groupHolder.userHeadImg);
        groupHolder.userNameTxt.setText(commentBeanList.get(groupPosition).getNickName());
        groupHolder.commentTimeTxt.setText(commentBeanList.get(groupPosition).getCreateDate());
        groupHolder.commentContentTxt.setText(commentBeanList.get(groupPosition).getContent());
        groupHolder.praisedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLike) {
                    isLike = false;
                    groupHolder.praisedImg.setImageResource(R.mipmap.icon_unpraised);
                } else {
                    isLike = true;
                    groupHolder.praisedImg.setImageResource(R.mipmap.icon_praised);
                }
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_item_comment_reply_layout, viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        String replyUser = commentBeanList.get(groupPosition).getReplyList().get(childPosition).getNickName();
        if (!TextUtils.isEmpty(replyUser)) {
            childHolder.itemReplyUserNameTxt.setText(replyUser + ":");
        } else {
            childHolder.itemReplyUserNameTxt.setText("无名" + ":");
        }

        childHolder.itemReplyContentTxt.setText(commentBeanList.get(groupPosition).getReplyList().get(childPosition).getContent());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder {
        private ImageView userHeadImg, praisedImg;
        private TextView userNameTxt, commentContentTxt, commentTimeTxt;

        public GroupHolder(View view) {
            userHeadImg = view.findViewById(R.id.itemCommentUserHeadImg);
            praisedImg = view.findViewById(R.id.itemCommentPraisedImg);
            userNameTxt = view.findViewById(R.id.itemCommentUserNameTxt);
            commentContentTxt = view.findViewById(R.id.itemCommentContentTxt);
            commentTimeTxt = view.findViewById(R.id.itemCommentTimeTxt);
        }
    }

    private class ChildHolder {
        private TextView itemReplyUserNameTxt, itemReplyContentTxt;

        public ChildHolder(View view) {
            itemReplyUserNameTxt = view.findViewById(R.id.itemReplyUserNameTxt);
            itemReplyContentTxt = view.findViewById(R.id.itemReplyContentTxt);
        }
    }


    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     *
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(CommentDetailBean commentDetailBean) {
        if (commentDetailBean != null) {

            commentBeanList.add(commentDetailBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     *
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(ReplyDetailBean replyDetailBean, int groupPosition) {
        if (replyDetailBean != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:" + replyDetailBean.toString());
            if (commentBeanList.get(groupPosition).getReplyList() != null) {
                commentBeanList.get(groupPosition).getReplyList().add(replyDetailBean);
            } else {
                List<ReplyDetailBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentBeanList.get(groupPosition).setReplyList(replyList);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:添加和展示所有回复
     *
     * @param replyBeanList 所有回复数据
     * @param groupPosition 当前的评论
     */
    private void addReplyList(List<ReplyDetailBean> replyBeanList, int groupPosition) {
        if (commentBeanList.get(groupPosition).getReplyList() != null) {
            commentBeanList.get(groupPosition).getReplyList().clear();
            commentBeanList.get(groupPosition).getReplyList().addAll(replyBeanList);
        } else {

            commentBeanList.get(groupPosition).setReplyList(replyBeanList);
        }

        notifyDataSetChanged();
    }

}
