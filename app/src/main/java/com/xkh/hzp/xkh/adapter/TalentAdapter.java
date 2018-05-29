package com.xkh.hzp.xkh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.TalentBean;
import com.xkh.hzp.xkh.entity.result.TalentResult;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
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
        talentHeadImg.setImageResource(R.drawable.ic_launcher_round);
        talentNickNameTxt.setText("爱吃糖的羊");
        talentPicImg.setImageResource(R.drawable.example);
        List<String> tags = new ArrayList<>();
        tags.add("学院风");
        tags.add("田园风");
        tags.add("甜美风");
        tags.add("日系风");
        tags.add("欧美风");
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
