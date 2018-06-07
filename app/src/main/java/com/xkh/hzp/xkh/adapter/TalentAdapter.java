package com.xkh.hzp.xkh.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.result.TalentResult;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;
import com.xkh.hzp.xkh.utils.GlideRoundTransform;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xkh.hzp.xkh.com.base.utils.DimentUtils;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName TalentAdapter
 * @Author tangyang
 * @DATE 2018/5/7
 **/
public class TalentAdapter extends BaseQuickAdapter<TalentResult, BaseViewHolder> {

    public TalentAdapter() {
        super(R.layout.view_item_talent, new ArrayList<TalentResult>());
    }

    @Override
    protected void convert(BaseViewHolder helper, TalentResult item) {
        helper.addOnClickListener(R.id.isAttentionTxt);
        helper.addOnClickListener(R.id.talentHeadImg);
        helper.addOnClickListener(R.id.talentPicImg);
        View spaceView = helper.getView(R.id.spaceView);
        ImageView talentHeadImg = helper.getView(R.id.talentHeadImg);
        TextView talentNickNameTxt = helper.getView(R.id.talentNickNameTxt);
        TextView isAttentionTxt = helper.getView(R.id.isAttentionTxt);
        ImageView talentPicImg = helper.getView(R.id.talentPicImg);
        TagFlowLayout talentLableFlowLayout = helper.getView(R.id.talentLableFlowLayout);
        int screenWidth = DimentUtils.getScreenWidth(mContext);
        ViewGroup.LayoutParams layoutParams = talentPicImg.getLayoutParams();
        layoutParams.width = screenWidth - DimentUtils.dip2px(mContext, 15 * 2);
        layoutParams.height = screenWidth - DimentUtils.dip2px(mContext, 15 * 2);
        talentPicImg.setLayoutParams(layoutParams);
        if (helper.getPosition() == 0) {
            if (getHeaderLayoutCount() > 0) {
                spaceView.setVisibility(View.VISIBLE);
            } else {
                spaceView.setVisibility(View.GONE);
            }
        }
        if ("focus".equals(item.getStatus())) {
            isAttentionTxt.setText("已关注");
        } else {
            isAttentionTxt.setText("+ 关注");
        }
        Glide.with(mContext).load(item.getImgUrl()).transform(new GlideRoundTransform(mContext, 10)).error(R.drawable.shape_place_holder).placeholder(R.drawable.shape_place_holder).into(talentPicImg);
        Glide.with(mContext).load(item.getHeadPortrait()).transform(new GlideCircleTransform(mContext)).error(R.mipmap.icon_female_selected).placeholder(R.mipmap.icon_female_selected).into(talentHeadImg);
        talentNickNameTxt.setText(item.getName());
        if (!TextUtils.isEmpty(item.getSignatureName())) {
            List<String> tags = Arrays.asList(item.getSignatureName().split(","));
            if (tags != null && tags.size() > 0) {
                talentLableFlowLayout.setAdapter(new TagAdapter<String>(tags) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_lable, null);
                        TextView textView = view.findViewById(R.id.lableTxt);
                        textView.setText(s);
                        return view;
                    }
                });
            }
        }


    }
}
