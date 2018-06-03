package com.xkh.hzp.xkh.adapter;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.utils.TimeUtils;

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
        helper.addOnClickListener(R.id.sharedLayout);
        helper.addOnClickListener(R.id.componentLayout);
        helper.addOnClickListener(R.id.goodLayout);
        ImageView dynamicUserHeadImg = helper.getView(R.id.dynamicUserHeadImg);
        TextView dynamicUserNickNameTxt = helper.getView(R.id.dynamicUserNickNameTxt);
        TextView dynamicPublishDateTxt = helper.getView(R.id.dynamicPublishDateTxt);
        TextView dynamicContentTxt = helper.getView(R.id.dynamicContentTxt);
        ImageView videoDynamicFaceurlImg = helper.getView(R.id.videoDynamicFaceurlImg);
        LinearLayout sharedLayout = helper.getView(R.id.sharedLayout);
        LinearLayout componentLayout = helper.getView(R.id.componentLayout);
        LinearLayout goodLayout = helper.getView(R.id.goodLayout);
        View dividerView = helper.getView(R.id.dividerView);
        Glide.with(mContext).load(item.getHeadPortrait()).placeholder(R.drawable.example).error(R.drawable.example).into(videoDynamicFaceurlImg);
        Glide.with(mContext).load(item.getFaceImgUrl()).placeholder(R.mipmap.icon_female_selected).error(R.mipmap.icon_female_selected).into(dynamicUserHeadImg);
        dynamicUserNickNameTxt.setText(item.getNickname());
        dynamicContentTxt.setText(item.getWordDescription());
        dynamicPublishDateTxt.setText(TimeUtils.getTimeFormatText(item.getUpdateTime()));
    }
}
