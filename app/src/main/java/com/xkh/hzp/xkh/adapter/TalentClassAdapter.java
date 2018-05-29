package com.xkh.hzp.xkh.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.result.HotLableResult;

import java.util.ArrayList;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName TalentClassAdapter
 * @Author tangyang
 * @DATE 2018/5/7
 **/
public class TalentClassAdapter extends BaseQuickAdapter<HotLableResult, BaseViewHolder> {

    public TalentClassAdapter() {
        super(R.layout.item_talent_header, new ArrayList<HotLableResult>());
    }

    @Override
    protected void convert(BaseViewHolder helper, HotLableResult item) {
        ImageView talentHeadItemImg = helper.getView(R.id.talentHeadItemImg);
        TextView talentTypeTxt = helper.getView(R.id.talentTypeTxt);
        talentTypeTxt.setText(item.getSignatureName());
        Glide.with(mContext).load(item.getIconUrl()).placeholder(R.drawable.ic_launcher_round).error(R.drawable.ic_launcher_round).into(talentHeadItemImg);
    }
}
