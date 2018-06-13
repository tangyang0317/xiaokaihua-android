package com.xkh.hzp.xkh.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.utils.GlideRoundTransform;

import java.util.ArrayList;

import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName TalentPictureAdapter
 * @Author tangyang
 * @DATE 2018/5/9
 **/
public class TalentPictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TalentPictureAdapter() {
        super(R.layout.view_item_talent_picture, new ArrayList<String>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView pictureImg = helper.getView(R.id.telentPictureImg);
        LinearLayout itemContentLayout = helper.getView(R.id.itemContentLayout);
        View startTelentPictureSpaceView = helper.getView(R.id.startTelentPictureSpaceView);
        View stopTelentPictureSpaceView = helper.getView(R.id.stopTelentPictureSpaceView);
        LinearLayout.LayoutParams spaceViewLayoutParams = new LinearLayout.LayoutParams(helper.getPosition() == 0 ? DimentUtils.dip2px(mContext, 15) : DimentUtils.dip2px(mContext, 10), LinearLayout.LayoutParams.WRAP_CONTENT);
        startTelentPictureSpaceView.setLayoutParams(spaceViewLayoutParams);
        int width = (DimentUtils.getScreenWidth(mContext) - DimentUtils.dip2px(mContext, 35)) * 2 / 5;
        int height = (int) (width + width * 0.6);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        pictureImg.setLayoutParams(layoutParams);
        int layoutWidth = 0;
        if (helper.getPosition() == getData().size() - 1) {
            layoutWidth = layoutParams.width + spaceViewLayoutParams.width * 2;
            stopTelentPictureSpaceView.setVisibility(View.VISIBLE);
        } else {
            layoutWidth = layoutParams.width + spaceViewLayoutParams.width;
        }
        LinearLayout.LayoutParams itemContentLayoutParams = new LinearLayout.LayoutParams(layoutWidth, height);
        itemContentLayout.setLayoutParams(itemContentLayoutParams);
        Glide.with(mContext).load(item).transform(new GlideRoundTransform(mContext, 4)).error(R.drawable.shape_place_holder).placeholder(R.drawable.shape_place_holder).into(pictureImg);
    }
}
