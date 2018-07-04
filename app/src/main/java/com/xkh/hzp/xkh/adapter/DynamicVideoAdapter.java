package com.xkh.hzp.xkh.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.Nums;
import com.xkh.hzp.xkh.utils.TimeUtils;
import com.xkh.hzp.xkh.view.FolderTextView;

import java.util.ArrayList;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName DynamicVideoAdapter
 * @Author tangyang
 * @DATE 2018/6/2
 **/
public class DynamicVideoAdapter extends BaseQuickAdapter<DynamicBean, BaseViewHolder> {


    public DynamicVideoAdapter() {
        super(R.layout.view_item_video_dynamic, new ArrayList<DynamicBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicBean item) {
        helper.addOnClickListener(R.id.dynamicUserHeadImg);
        helper.addOnClickListener(R.id.dynamicUserNickNameTxt);
        helper.addOnClickListener(R.id.sharedLayout);
        helper.addOnClickListener(R.id.componentLayout);
        helper.addOnClickListener(R.id.goodLayout);
        helper.addOnClickListener(R.id.dynamicContentTxt);
        helper.addOnClickListener(R.id.dynamicImgContentLayout);
        ImageView dynamicUserHeadImg = helper.getView(R.id.dynamicUserHeadImg);
        TextView dynamicUserNickNameTxt = helper.getView(R.id.dynamicUserNickNameTxt);
        TextView dynamicPublishDateTxt = helper.getView(R.id.dynamicPublishDateTxt);
        FolderTextView dynamicContentTxt = helper.getView(R.id.dynamicContentTxt);
        ImageView praisedImg = helper.getView(R.id.praisedImg);
        ImageView videoDynamicFaceurlImg = helper.getView(R.id.videoDynamicFaceurlImg);
        TextView videoPlayCountTxt = helper.getView(R.id.videoPlayCountTxt);
        TextView videoDuringTxt = helper.getView(R.id.videoDuringTxt);
        LinearLayout sharedLayout = helper.getView(R.id.sharedLayout);
        LinearLayout componentLayout = helper.getView(R.id.componentLayout);
        LinearLayout goodLayout = helper.getView(R.id.goodLayout);
        View dividerView = helper.getView(R.id.dividerView);
        videoPlayCountTxt.setText(Nums.countTranslate(item.getViewNumber()) + "次播放");
        if (!TextUtils.isEmpty(item.getTimeLength())) {
            videoDuringTxt.setVisibility(View.VISIBLE);
            videoDuringTxt.setText(item.getTimeLength());
        } else {
            videoDuringTxt.setVisibility(View.GONE);
        }
        if ("normal".equals(item.getLikeStatus())) {
            praisedImg.setImageResource(R.mipmap.icon_praised);
        } else {
            praisedImg.setImageResource(R.mipmap.icon_unpraised);
        }
        Glide.with(mContext).load(item.getFaceUrl()).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(videoDynamicFaceurlImg);
        Glide.with(mContext).load(item.getHeadPortrait()).transform(new GlideCircleTransform(mContext)).placeholder(R.mipmap.icon_female_selected).error(R.mipmap.icon_female_selected).into(dynamicUserHeadImg);
        dynamicUserNickNameTxt.setText(item.getName());
        dynamicContentTxt.setText(item.getWordDescription());
        dynamicPublishDateTxt.setText(TimeUtils.getTimeFormatText(item.getCreateTime()));
    }
}
