package com.xkh.hzp.xkh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.DynamicBean;

import java.util.ArrayList;

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
        ImageView dynamicUserHeadImg = helper.getView(R.id.dynamicUserHeadImg);
        TextView dynamicUserNickNameTxt = helper.getView(R.id.dynamicUserNickNameTxt);
        TextView dynamicPublishDateTxt = helper.getView(R.id.dynamicPublishDateTxt);
        TextView dynamicContentTxt = helper.getView(R.id.dynamicContentTxt);
        LinearLayout dynamicImgContentLayout = helper.getView(R.id.dynamicImgContentLayout);
        LinearLayout sharedLayout = helper.getView(R.id.sharedLayout);
        LinearLayout componentLayout = helper.getView(R.id.componentLayout);
        LinearLayout goodLayout = helper.getView(R.id.goodLayout);
        int screenWidth = DimentUtils.getScreenWidth(mContext);
        if (dynamicImgContentLayout.getChildCount() > 0) {
            dynamicImgContentLayout.removeAllViews();
        }
        if (item.getImgList() != null && item.getImgList().size() > 0) {
            if (item.getImgList().size() == 1) {
                ImageView oneImg = new ImageView(mContext);
                int width = screenWidth - DimentUtils.dip2px(mContext, 30);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, width / 2);
                oneImg.setLayoutParams(layoutParams);
                oneImg.setScaleType(ImageView.ScaleType.FIT_XY);
                oneImg.setImageResource(R.drawable.example);
                dynamicImgContentLayout.addView(oneImg);
            } else if (item.getImgList().size() == 2) {
                ImageView twoImg1 = new ImageView(mContext);
                int widthForTwo = (screenWidth - DimentUtils.dip2px(mContext, 32)) / 2;
                ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(widthForTwo, widthForTwo);
                twoImg1.setLayoutParams(layoutParams2);
                twoImg1.setScaleType(ImageView.ScaleType.FIT_XY);
                twoImg1.setImageResource(R.drawable.example);
                dynamicImgContentLayout.addView(twoImg1);

                ImageView twoImg2 = new ImageView(mContext);
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(widthForTwo, widthForTwo);
                layoutParams3.setMargins(DimentUtils.dip2px(mContext, 2), 0, 0, 0);
                twoImg2.setLayoutParams(layoutParams3);
                twoImg2.setScaleType(ImageView.ScaleType.FIT_XY);
                twoImg2.setImageResource(R.drawable.example);
                dynamicImgContentLayout.addView(twoImg2);
            } else if (item.getImgList().size() == 3) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_dynamic_img_three, null);
                ImageView threeImg1 = view.findViewById(R.id.oneImg);
                ImageView threeImg2 = view.findViewById(R.id.twoImg);
                ImageView threeImg3 = view.findViewById(R.id.threeImg);
                int threeImg1Width = (screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 3;
                int threeImg1Height = ((screenWidth - DimentUtils.dip2px(mContext, 30)) / 5 * 2) * 2;
                RelativeLayout.LayoutParams three1LayoutParams = new RelativeLayout.LayoutParams(threeImg1Width, threeImg1Height);
                threeImg1.setLayoutParams(three1LayoutParams);
                threeImg1.setScaleType(ImageView.ScaleType.FIT_XY);
                threeImg1.setImageResource(R.drawable.example);

                RelativeLayout.LayoutParams three2LayoutParams = new RelativeLayout.LayoutParams((screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 2, (screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 2);
                three2LayoutParams.setMargins(DimentUtils.dip2px(mContext, 2), 0, 0, 0);
                three2LayoutParams.addRule(RelativeLayout.RIGHT_OF, threeImg1.getId());
                threeImg2.setLayoutParams(three2LayoutParams);
                threeImg2.setScaleType(ImageView.ScaleType.FIT_XY);
                threeImg2.setImageResource(R.drawable.example);


                RelativeLayout.LayoutParams three3LayoutParams = new RelativeLayout.LayoutParams((screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 2, (screenWidth - DimentUtils.dip2px(mContext, 32)) / 5 * 2);
                three3LayoutParams.setMargins(DimentUtils.dip2px(mContext, 2), DimentUtils.dip2px(mContext, 2), 0, 0);
                three3LayoutParams.addRule(RelativeLayout.RIGHT_OF, threeImg1.getId());
                three3LayoutParams.addRule(RelativeLayout.BELOW, threeImg2.getId());
                threeImg3.setLayoutParams(three3LayoutParams);
                threeImg3.setScaleType(ImageView.ScaleType.FIT_XY);
                threeImg3.setImageResource(R.drawable.example);
                dynamicImgContentLayout.addView(view);
            } else if (item.getImgList().size() == 4) {
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.view_item_dynamic_img_four, null);
                ImageView fourImg1 = view1.findViewById(R.id.oneImg);
                ImageView fourImg2 = view1.findViewById(R.id.twoImg);
                ImageView fourImg3 = view1.findViewById(R.id.threeImg);
                ImageView fourImg4 = view1.findViewById(R.id.fourImg);

                int width1 = screenWidth - DimentUtils.dip2px(mContext, 30);
                RelativeLayout.LayoutParams four1LayoutParams = new RelativeLayout.LayoutParams(width1, width1 / 2);
                fourImg1.setLayoutParams(four1LayoutParams);
                fourImg1.setScaleType(ImageView.ScaleType.FIT_XY);
                fourImg1.setImageResource(R.drawable.example);

                int width2 = (screenWidth - DimentUtils.dip2px(mContext, 34)) / 3;
                RelativeLayout.LayoutParams four2LayoutParams = new RelativeLayout.LayoutParams(width2, width2);
                four2LayoutParams.setMargins(0, DimentUtils.dip2px(mContext, 2), 0, 0);
                four2LayoutParams.addRule(RelativeLayout.BELOW, fourImg1.getId());
                fourImg2.setLayoutParams(four2LayoutParams);
                fourImg2.setScaleType(ImageView.ScaleType.FIT_XY);
                fourImg2.setImageResource(R.drawable.example);

                RelativeLayout.LayoutParams four3LayoutParams = new RelativeLayout.LayoutParams(width2, width2);
                four3LayoutParams.setMargins(DimentUtils.dip2px(mContext, 2), DimentUtils.dip2px(mContext, 2), 0, 0);
                four3LayoutParams.addRule(RelativeLayout.BELOW, fourImg1.getId());
                four3LayoutParams.addRule(RelativeLayout.RIGHT_OF, fourImg2.getId());
                fourImg3.setLayoutParams(four3LayoutParams);
                fourImg3.setScaleType(ImageView.ScaleType.FIT_XY);
                fourImg3.setImageResource(R.drawable.example);


                RelativeLayout.LayoutParams four4LayoutParams = new RelativeLayout.LayoutParams(width2, width2);
                four4LayoutParams.setMargins(DimentUtils.dip2px(mContext, 2), DimentUtils.dip2px(mContext, 2), 0, 0);
                four4LayoutParams.addRule(RelativeLayout.BELOW, fourImg1.getId());
                four4LayoutParams.addRule(RelativeLayout.RIGHT_OF, fourImg3.getId());
                fourImg4.setLayoutParams(four4LayoutParams);
                fourImg4.setScaleType(ImageView.ScaleType.FIT_XY);
                fourImg4.setImageResource(R.drawable.example);
                dynamicImgContentLayout.addView(view1);
            }
        }

    }
}
