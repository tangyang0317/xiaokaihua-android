package com.xkh.hzp.xkh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.DynamicBean;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.TimeUtils;
import com.xkh.hzp.xkh.view.FolderTextView;

import java.util.ArrayList;
import java.util.List;

import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName DynamicAdapter
 * @Author tangyang
 * @DATE 2018/5/8
 **/
public class DynamicAdapter extends BaseQuickAdapter<DynamicBean, BaseViewHolder> {

    public DynamicAdapter() {
        super(R.layout.view_item_dynamic, new ArrayList<DynamicBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicBean item) {
        helper.addOnClickListener(R.id.dynamicUserHeadImg);
        helper.addOnClickListener(R.id.sharedLayout);
        helper.addOnClickListener(R.id.componentLayout);
        helper.addOnClickListener(R.id.goodLayout);
        helper.addOnClickListener(R.id.expandable_text);
        helper.addOnClickListener(R.id.dynamicImgContentLayout);
        helper.addOnClickListener(R.id.dynamicUserHeadImg);
        helper.addOnClickListener(R.id.dynamicContentTxt);
        ImageView praisedImg = helper.getView(R.id.praisedImg);
        ImageView dynamicUserHeadImg = helper.getView(R.id.dynamicUserHeadImg);
        TextView dynamicUserNickNameTxt = helper.getView(R.id.dynamicUserNickNameTxt);
        TextView dynamicPublishDateTxt = helper.getView(R.id.dynamicPublishDateTxt);
        FolderTextView expandableTextView = helper.getView(R.id.dynamicContentTxt);
        View dividerView = helper.getView(R.id.dividerView);
        LinearLayout dynamicImgContentLayout = helper.getView(R.id.dynamicImgContentLayout);
        if ("image".equals(item.getDynamicType())) {
            generatorImg(dynamicImgContentLayout, item.getAnnexUrlList());
        } else if ("video".equals(item.getDynamicType())) {
            if (dynamicImgContentLayout.getChildCount() > 0) {
                dynamicImgContentLayout.removeAllViews();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_dynamic_video, null);
            ImageView videoFaceImg = view.findViewById(R.id.videoFaceImg);
            ImageView videoPlayIconImg = view.findViewById(R.id.videoPlayIconImg);
            TextView videoPlayCountTxt = view.findViewById(R.id.videoPlayCountTxt);
            TextView videoDuringTxt = view.findViewById(R.id.videoDuringTxt);
            int screenWidth = DimentUtils.getScreenWidth(mContext);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth - DimentUtils.dip2px(mContext, 30), screenWidth / 2);
            videoFaceImg.setLayoutParams(layoutParams);
            dynamicImgContentLayout.addView(view);
            videoPlayCountTxt.setText(item.getViewNumber() + "次播放");
            Glide.with(mContext).load(item.getFaceUrl()).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(videoFaceImg);
        }
        if ("normal".equals(item.getLikeStatus())) {
            praisedImg.setImageResource(R.mipmap.icon_praised);
        } else {
            praisedImg.setImageResource(R.mipmap.icon_unpraised);
        }
        Glide.with(mContext).load(item.getHeadPortrait()).transform(new GlideCircleTransform(mContext)).placeholder(R.mipmap.icon_female_selected).error(R.mipmap.icon_female_selected).into(dynamicUserHeadImg);
        dynamicUserNickNameTxt.setText(item.getName());
        expandableTextView.setText(item.getWordDescription());
        dynamicPublishDateTxt.setText(TimeUtils.getTimeFormatText(item.getCreateTime()));
    }


    private void generatorImg(LinearLayout dynamicImgContentLayout, List<String> imgList) {
        if (dynamicImgContentLayout.getChildCount() > 0) {
            dynamicImgContentLayout.removeAllViews();
        }
        int screenWidth = DimentUtils.getScreenWidth(mContext);
        if (imgList != null && imgList.size() > 0) {
            if (imgList.size() == 1) {
                ImageView oneImg = new ImageView(mContext);
                int width = screenWidth - DimentUtils.dip2px(mContext, 30);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, width / 2);
                oneImg.setLayoutParams(layoutParams);
                oneImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext).load(imgList.get(0)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(oneImg);
                dynamicImgContentLayout.addView(oneImg);
            } else if (imgList.size() == 2) {
                ImageView twoImg1 = new ImageView(mContext);
                int widthForTwo = (screenWidth - DimentUtils.dip2px(mContext, 32)) / 2;
                ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(widthForTwo, widthForTwo);
                twoImg1.setLayoutParams(layoutParams2);
                dynamicImgContentLayout.addView(twoImg1);
                twoImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext).load(imgList.get(0)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(twoImg1);
                ImageView twoImg2 = new ImageView(mContext);
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(widthForTwo, widthForTwo);
                layoutParams3.setMargins(DimentUtils.dip2px(mContext, 2), 0, 0, 0);
                twoImg2.setLayoutParams(layoutParams3);
                twoImg2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(mContext).load(imgList.get(1)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(twoImg2);
                dynamicImgContentLayout.addView(twoImg2);
            } else if (imgList.size() == 3) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_dynamic_img_three, null);
                ImageView threeImg1 = view.findViewById(R.id.oneImg);
                ImageView threeImg2 = view.findViewById(R.id.twoImg);
                ImageView threeImg3 = view.findViewById(R.id.threeImg);
                threeImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                threeImg2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                threeImg3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                int threeImg1Width = (screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 3;
                int threeImg1Height = ((screenWidth - DimentUtils.dip2px(mContext, 30)) / 5 * 2) * 2;
                RelativeLayout.LayoutParams three1LayoutParams = new RelativeLayout.LayoutParams(threeImg1Width, threeImg1Height);
                threeImg1.setLayoutParams(three1LayoutParams);

                RelativeLayout.LayoutParams three2LayoutParams = new RelativeLayout.LayoutParams((screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 2, (screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 2);
                three2LayoutParams.setMargins(DimentUtils.dip2px(mContext, 2), 0, 0, 0);
                three2LayoutParams.addRule(RelativeLayout.RIGHT_OF, threeImg1.getId());
                threeImg2.setLayoutParams(three2LayoutParams);

                RelativeLayout.LayoutParams three3LayoutParams = new RelativeLayout.LayoutParams((screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 2, (screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 2);
                three3LayoutParams.setMargins(DimentUtils.dip2px(mContext, 2), DimentUtils.dip2px(mContext, 2), 0, 0);
                three3LayoutParams.addRule(RelativeLayout.RIGHT_OF, threeImg1.getId());
                three3LayoutParams.addRule(RelativeLayout.BELOW, threeImg2.getId());
                threeImg3.setLayoutParams(three3LayoutParams);

                Glide.with(mContext).load(imgList.get(0)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(threeImg1);
                Glide.with(mContext).load(imgList.get(1)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(threeImg2);
                Glide.with(mContext).load(imgList.get(2)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(threeImg3);
                Glide.with(mContext).load(imgList.get(2)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(threeImg3);

                dynamicImgContentLayout.addView(view);
            } else if (imgList.size() == 4) {
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.view_item_dynamic_img_four, null);
                ImageView fourImg1 = view1.findViewById(R.id.oneImg);
                ImageView fourImg2 = view1.findViewById(R.id.twoImg);
                ImageView fourImg3 = view1.findViewById(R.id.threeImg);
                ImageView fourImg4 = view1.findViewById(R.id.fourImg);

                fourImg1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                fourImg2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                fourImg3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                fourImg4.setScaleType(ImageView.ScaleType.CENTER_CROP);

                int width1 = screenWidth - DimentUtils.dip2px(mContext, 30);
                RelativeLayout.LayoutParams four1LayoutParams = new RelativeLayout.LayoutParams(width1, width1 / 2);
                fourImg1.setLayoutParams(four1LayoutParams);

                int width2 = (screenWidth - DimentUtils.dip2px(mContext, 34)) / 3;
                RelativeLayout.LayoutParams four2LayoutParams = new RelativeLayout.LayoutParams(width2, width2);
                four2LayoutParams.setMargins(0, DimentUtils.dip2px(mContext, 2), 0, 0);
                four2LayoutParams.addRule(RelativeLayout.BELOW, fourImg1.getId());
                fourImg2.setLayoutParams(four2LayoutParams);

                RelativeLayout.LayoutParams four3LayoutParams = new RelativeLayout.LayoutParams(width2, width2);
                four3LayoutParams.setMargins(DimentUtils.dip2px(mContext, 2), DimentUtils.dip2px(mContext, 2), 0, 0);
                four3LayoutParams.addRule(RelativeLayout.BELOW, fourImg1.getId());
                four3LayoutParams.addRule(RelativeLayout.RIGHT_OF, fourImg2.getId());
                fourImg3.setLayoutParams(four3LayoutParams);

                RelativeLayout.LayoutParams four4LayoutParams = new RelativeLayout.LayoutParams(width2, width2);
                four4LayoutParams.setMargins(DimentUtils.dip2px(mContext, 2), DimentUtils.dip2px(mContext, 2), 0, 0);
                four4LayoutParams.addRule(RelativeLayout.BELOW, fourImg1.getId());
                four4LayoutParams.addRule(RelativeLayout.RIGHT_OF, fourImg3.getId());
                fourImg4.setLayoutParams(four4LayoutParams);
                Glide.with(mContext).load(imgList.get(0)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(fourImg1);
                Glide.with(mContext).load(imgList.get(1)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(fourImg2);
                Glide.with(mContext).load(imgList.get(2)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(fourImg3);
                Glide.with(mContext).load(imgList.get(3)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(fourImg4);
                dynamicImgContentLayout.addView(view1);
            }
        }
    }

}
