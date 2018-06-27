package com.xkh.hzp.xkh.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.activity.TalentHomePageActivity;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.CommentDetailBean;
import com.xkh.hzp.xkh.entity.ReplyDetailBean;
import com.xkh.hzp.xkh.entity.result.CommentResult;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.TimeUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Moos
 * E-mail: moosphon@gmail.com
 * Date:  18/4/20.
 * Desc: 评论与回复列表的适配器
 */

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<CommentResult> commentBeanList;
    private List<CommentResult.ReplyResult> replyBeanList;
    private Context context;
    private int pageIndex = 1;

    public CommentExpandAdapter(Context context, List<CommentResult> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    /***
     * 设置新数据
     * @param newCommentBeanList
     */
    public void setNewData(List<CommentResult> newCommentBeanList) {
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

    /***
     * 添加新数据
     * @param newCommentResult
     */
    public void addData(List<CommentResult> newCommentResult) {
        if (newCommentResult != null) {
            commentBeanList.addAll(newCommentResult);
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
        if (commentBeanList.get(i).getReplyResults() == null) {
            return 0;
        } else {
            return commentBeanList.get(i).getReplyResults().size() > 0 ? commentBeanList.get(i).getReplyResults().size() : 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getReplyResults().get(i1);
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
        Glide.with(context).load(commentBeanList.get(groupPosition).getCommentResult().getHeadPortrait())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.icon_female_selected)
                .transform(new GlideCircleTransform(context))
                .into(groupHolder.userHeadImg);
        if ("normal".equals(commentBeanList.get(groupPosition).getCommentResult().getStatus())) {
            groupHolder.praisedImg.setImageResource(R.mipmap.icon_praised);
        } else {
            groupHolder.praisedImg.setImageResource(R.mipmap.icon_unpraised);
        }
        groupHolder.itemPraisedCountTxt.setText("" + commentBeanList.get(groupPosition).getCommentResult().getLikeNumber());
        groupHolder.userNameTxt.setText(commentBeanList.get(groupPosition).getCommentResult().getName());
        groupHolder.commentTimeTxt.setText(TimeUtils.getTimeFormatText(commentBeanList.get(groupPosition).getCommentResult().getCreateTime()));
        groupHolder.commentContentTxt.setText(commentBeanList.get(groupPosition).getCommentResult().getComment());
        groupHolder.praisedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("normal".equals(commentBeanList.get(groupPosition).getCommentResult().getStatus())) {
                    //取消点赞
                    commentCancleLike(commentBeanList.get(groupPosition).getCommentResult().getId(), groupPosition, groupHolder.praisedImg, groupHolder.itemPraisedCountTxt);
                } else {
                    //点赞
                    commentLike(commentBeanList.get(groupPosition).getCommentResult().getId(), groupPosition, groupHolder.praisedImg, groupHolder.itemPraisedCountTxt);
                }
            }
        });


        groupHolder.userHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TalentHomePageActivity.lanuchActivity((Activity) context, String.valueOf(commentBeanList.get(groupPosition).getCommentResult().getUserId()));
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
        CommentResult.ReplyResult replyResult = commentBeanList.get(groupPosition).getReplyResults().get(childPosition);
        if (replyResult != null) {
            StringBuilder content = new StringBuilder();
            content.append("<font color=\"#FF5555\">" + replyResult.getName() + "回复" + replyResult.getReplyUserName() + ":" + "</font>");
            content.append("<font color=\"#949494\">" + replyResult.getReplyContent() + "</font>");
            childHolder.itemReplyContentTxt.setText(Html.fromHtml(content.toString()));
        }
        if (childPosition == getChildrenCount(groupPosition) - 1) {
            childHolder.replySpaceView.setVisibility(View.VISIBLE);
        } else {
            childHolder.replySpaceView.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder {
        private ImageView userHeadImg, praisedImg;
        private TextView userNameTxt, commentContentTxt, commentTimeTxt, itemPraisedCountTxt;

        public GroupHolder(View view) {
            userHeadImg = view.findViewById(R.id.itemCommentUserHeadImg);
            praisedImg = view.findViewById(R.id.itemCommentPraisedImg);
            userNameTxt = view.findViewById(R.id.itemCommentUserNameTxt);
            commentContentTxt = view.findViewById(R.id.itemCommentContentTxt);
            commentTimeTxt = view.findViewById(R.id.itemCommentTimeTxt);
            itemPraisedCountTxt = view.findViewById(R.id.itemPraisedCountTxt);
        }
    }

    private class ChildHolder {
        private TextView itemReplyContentTxt;
        private View replySpaceView;

        public ChildHolder(View view) {
            itemReplyContentTxt = view.findViewById(R.id.itemReplyContentTxt);
            replySpaceView = view.findViewById(R.id.replySpaceView);
        }
    }


    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     *
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(CommentResult commentDetailBean) {
        if (commentDetailBean != null) {
            commentBeanList.add(0, commentDetailBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }
    }

    /***
     * 删除评论
     * @param groupPosition
     */
    public void deleteTheCommentData(int groupPosition) {
        commentBeanList.remove(groupPosition);
        notifyDataSetChanged();
    }

    /***
     * 删除回复
     * @param groupPosition
     * @param childPosition
     */
    public void deleteTheReply(int groupPosition, int childPosition) {
        List<CommentResult.ReplyResult> replyResults = commentBeanList.get(groupPosition).getReplyResults();
        if (replyResults != null) {
            replyResults.remove(childPosition);
            for (int i = 0; i < replyResults.size(); i++) {
                if (replyResults.get(i).getParentId() != 0) {
                    replyResults.remove(i);
                }
            }
            notifyDataSetChanged();
        }
    }


    /***
     * 获取评论的内容
     * @param groupPosition
     * @return
     */
    public CommentResult.CommentResultBean getCommentData(int groupPosition) {
        return commentBeanList.get(groupPosition).getCommentResult();
    }


    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     *
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(CommentResult.ReplyResult replyDetailBean, int groupPosition) {
        if (replyDetailBean != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:" + replyDetailBean.toString());
            if (commentBeanList.get(groupPosition).getReplyResults() != null) {
                commentBeanList.get(groupPosition).getReplyResults().add(replyDetailBean);
            } else {
                List<CommentResult.ReplyResult> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentBeanList.get(groupPosition).setReplyResults(replyList);
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
    private void addReplyList(List<CommentResult.ReplyResult> replyBeanList, int groupPosition) {
        if (commentBeanList.get(groupPosition).getReplyResults() != null) {
            commentBeanList.get(groupPosition).getReplyResults().clear();
            commentBeanList.get(groupPosition).getReplyResults().addAll(replyBeanList);
        } else {
            commentBeanList.get(groupPosition).setReplyResults(replyBeanList);
        }
        notifyDataSetChanged();
    }

    /***
     * 评论点赞
     * @param commentId
     * @param groupPosition
     * @param praisedImg
     * @param itemPraisedCountTxt
     */
    public void commentLike(long commentId, final int groupPosition, final ImageView praisedImg, final TextView itemPraisedCountTxt) {
        HashMap<String, String> params = new HashMap<>();
        params.put("commentId", String.valueOf(commentId));
        params.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().post(UrlConfig.commentPraised, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Integer.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    if (success) {
                        commentBeanList.get(groupPosition).getCommentResult().setStatus("normal");
                        commentBeanList.get(groupPosition).getCommentResult().setLikeNumber(commentBeanList.get(groupPosition).getCommentResult().getLikeNumber() + 1);
                        praisedImg.setImageResource(R.mipmap.icon_praised);
                        ScaleAnimation viewShowAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        viewShowAnimation.setDuration(500);
                        praisedImg.startAnimation(viewShowAnimation);
                        itemPraisedCountTxt.setText(String.valueOf(commentBeanList.get(groupPosition).getCommentResult().getLikeNumber()));
                    }
                }
            }
        });

    }

    /***
     * 取消评论点赞
     * @param commentId
     * @param groupPosition
     * @param praisedImg
     * @param itemPraisedCountTxt
     */
    public void commentCancleLike(long commentId, final int groupPosition, final ImageView praisedImg, final TextView itemPraisedCountTxt) {
        HashMap<String, String> params = new HashMap<>();
        params.put("commentId", String.valueOf(commentId));
        params.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().post(UrlConfig.commentUnPraised, params, new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", Integer.TYPE);
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    commentBeanList.get(groupPosition).getCommentResult().setStatus(null);
                    commentBeanList.get(groupPosition).getCommentResult().setLikeNumber(commentBeanList.get(groupPosition).getCommentResult().getLikeNumber() - 1);
                    praisedImg.setImageResource(R.mipmap.icon_unpraised);
                    ScaleAnimation viewShowAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    viewShowAnimation.setDuration(500);
                    praisedImg.startAnimation(viewShowAnimation);
                    itemPraisedCountTxt.setText(String.valueOf(commentBeanList.get(groupPosition).getCommentResult().getLikeNumber()));
                }
            }
        });

    }


}
