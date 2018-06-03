package com.xkh.hzp.xkh.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.result.RecommondTalentResult;

import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName RecommondAttentionAdapter
 * @Author tangyang
 * @DATE 2018/6/2
 **/
public class RecommondAttentionAdapter extends BaseQuickAdapter<RecommondTalentResult, BaseViewHolder> {

    public RecommondAttentionAdapter() {
        super(R.layout.item_recommond_attention);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommondTalentResult item) {
        LinearLayout recommendAttentionLayout = helper.getView(R.id.recommendAttentionLayout);
        int width = (int) ((DimentUtils.getScreenWidth(mContext) - DimentUtils.dip2px(mContext, 30)) / 2.5);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = DimentUtils.dip2px(mContext, 10);
        if (helper.getPosition() == getItemCount() - 1) {
            layoutParams.rightMargin = DimentUtils.dip2px(mContext, 10);
        }
        recommendAttentionLayout.setLayoutParams(layoutParams);
        ImageView recommendUserHeadImg = helper.getView(R.id.recommendUserHeadImg);
        TextView recommendUserNickNameTxt = helper.getView(R.id.recommendUserNickNameTxt);
        TextView talentBeGoodItTxt = helper.getView(R.id.talentBeGoodItTxt);
        TextView isAttentionTxt = helper.getView(R.id.isAttentionTxt);
    }
}
