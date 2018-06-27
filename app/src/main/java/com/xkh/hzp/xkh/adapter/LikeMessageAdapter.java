package com.xkh.hzp.xkh.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.result.CommentMessageResult;
import com.xkh.hzp.xkh.entity.result.LikeMessageResult;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName LikeMessageAdapter
 * @Author tangyang
 * @DATE 2018/6/9
 **/
public class LikeMessageAdapter extends BaseQuickAdapter<LikeMessageResult, BaseViewHolder> {

    public LikeMessageAdapter() {
        super(R.layout.item_message_like, new ArrayList<LikeMessageResult>());
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeMessageResult item) {
        ImageView likeUserHeadImg = helper.getView(R.id.likeUserHeadImg);
        TextView likeUserNickNameTxt = helper.getView(R.id.likeUserNickNameTxt);
        TextView likeDateTxt = helper.getView(R.id.likeDateTxt);
        ImageView dynamicFaceUrlImg = helper.getView(R.id.dynamicFaceUrlImg);
        TextView dynamicContentTxt = helper.getView(R.id.dynamicContentTxt);
        if (!TextUtils.isEmpty(item.getSimpleDynamicResult().getAnnexUrl())) {
            List<String> imgUrl = Arrays.asList(item.getSimpleDynamicResult().getAnnexUrl().split(","));
            Glide.with(mContext).load(imgUrl.get(0)).error(R.drawable.shape_place_holder).placeholder(R.drawable.shape_place_holder).into(dynamicFaceUrlImg);
        }
        Glide.with(mContext).load(item.getHeadPortrait()).transform(new GlideCircleTransform(mContext)).error(R.mipmap.icon_female_selected).placeholder(R.mipmap.icon_female_selected).into(likeUserHeadImg);
        likeUserNickNameTxt.setText(item.getLikeUserName());
        dynamicContentTxt.setText(item.getSimpleDynamicResult().getWordDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        likeDateTxt.setText(sdf.format(item.getCreateTime()));

    }
}
