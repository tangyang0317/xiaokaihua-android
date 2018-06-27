package com.xkh.hzp.xkh.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.entity.result.CommentMessageResult;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName CommentMessageAdapter
 * @Author tangyang
 * @DATE 2018/6/9
 **/
public class CommentMessageAdapter extends BaseQuickAdapter<CommentMessageResult, BaseViewHolder> {

    public CommentMessageAdapter() {
        super(R.layout.item_message_comment, new ArrayList<CommentMessageResult>());
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentMessageResult item) {
        helper.addOnClickListener(R.id.replyTxt);
        ImageView commentUserHeadImg = helper.getView(R.id.commentUserHeadImg);
        TextView commentUserNickNameTxt = helper.getView(R.id.commentUserNickNameTxt);
        TextView commentDateTxt = helper.getView(R.id.commentDateTxt);
        TextView commentReplyContentTxt = helper.getView(R.id.commentReplyContentTxt);
        TextView mineReplyContentTxt = helper.getView(R.id.mineReplyContentTxt);
        ImageView dynamicFaceUrlImg = helper.getView(R.id.dynamicFaceUrlImg);
        TextView dynamicContentTxt = helper.getView(R.id.dynamicContentTxt);
        TextView replyTxt = helper.getView(R.id.replyTxt);
        List<String> imgUrl = Arrays.asList(item.getSimpleDynamicResult().getAnnexUrl().split(","));
        Glide.with(mContext).load(item.getHeadPortrait()).transform(new GlideCircleTransform(mContext)).error(R.mipmap.icon_female_selected).placeholder(R.mipmap.icon_female_selected).into(commentUserHeadImg);
        Glide.with(mContext).load(imgUrl.get(0)).error(R.drawable.shape_place_holder).placeholder(R.drawable.shape_place_holder).into(dynamicFaceUrlImg);
        commentUserNickNameTxt.setText(item.getReplyUserName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        commentDateTxt.setText(sdf.format(item.getCreateTime()));
        commentReplyContentTxt.setText("回复@" + item.getUserName() + ":" + item.getReplyContent());
        String mineContent = TextUtils.isEmpty(item.getComment()) ? item.getContent() : item.getComment();
        mineReplyContentTxt.setText("@" + item.getUserName() + ":" + mineContent);
        dynamicContentTxt.setText(item.getSimpleDynamicResult().getWordDescription());
    }
}
