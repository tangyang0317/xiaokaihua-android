package com.xkh.hzp.xkh.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;

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
        View telentPictureSpaceView = helper.getView(R.id.telentPictureSpaceView);
        ViewGroup.LayoutParams spaceViewLayoutParams = new ViewGroup.LayoutParams(helper.getPosition() == 0 ? DimentUtils.dip2px(mContext, 15) : DimentUtils.dip2px(mContext, 10), ViewGroup.LayoutParams.WRAP_CONTENT);
        telentPictureSpaceView.setLayoutParams(spaceViewLayoutParams);
        int width = (DimentUtils.getScreenWidth(mContext) - DimentUtils.dip2px(mContext, 35)) * 4 / 5;
        int height = (int) (width + width * 0.6);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        pictureImg.setLayoutParams(layoutParams);
    }
}
