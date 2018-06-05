package com.xkh.hzp.xkh.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.DynamicBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName SearchDynamicAdapter
 * @Author tangyang
 * @DATE 2018/6/4
 **/
public class SearchDynamicAdapter extends BaseQuickAdapter<DynamicBean, BaseViewHolder> {

    public SearchDynamicAdapter() {
        super(R.layout.item_search_dynamic, new ArrayList<DynamicBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicBean item) {
        ImageView searchNoteUserImg = helper.getView(R.id.searchNoteUserImg);
        ImageView searchNoteVedioFlagImg = helper.getView(R.id.searchNoteVedioFlagImg);
        TextView searchNoteContentTxt = helper.getView(R.id.searchNoteContentTxt);
        View searchNoteItemLine = helper.getView(R.id.searchNoteItemLine);
        List<String> imgUrl = Arrays.asList(item.getAnnexUrl().split(","));
        if (imgUrl != null && imgUrl.size() > 0) {
            Glide.with(mContext).load(imgUrl.get(0)).placeholder(R.drawable.shape_place_holder).error(R.drawable.shape_place_holder).into(searchNoteUserImg);
        }
        if (item.getDynamicType().equals("video")) {
            searchNoteVedioFlagImg.setVisibility(View.VISIBLE);
        } else {
            searchNoteVedioFlagImg.setVisibility(View.GONE);
        }
        searchNoteContentTxt.setText(item.getWordDescription());
        if (helper.getPosition() == getItemCount() - 1) {
            searchNoteItemLine.setVisibility(View.GONE);
        } else {
            searchNoteItemLine.setVisibility(View.VISIBLE);
        }


    }
}
