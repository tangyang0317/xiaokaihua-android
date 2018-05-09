package com.xkh.hzp.xkh.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;

import java.util.ArrayList;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName TalentClassAdapter
 * @Author tangyang
 * @DATE 2018/5/7
 **/
public class TalentClassAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TalentClassAdapter() {
        super(R.layout.item_talent_header, new ArrayList<String>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView talentHeadItemImg = helper.getView(R.id.talentHeadItemImg);
        TextView talentTypeTxt = helper.getView(R.id.talentTypeTxt);
    }
}
