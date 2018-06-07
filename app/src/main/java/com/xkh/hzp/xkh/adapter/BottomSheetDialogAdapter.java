package com.xkh.hzp.xkh.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import android.widget.TextView;

import com.xkh.hzp.xkh.entity.DialogItemBean;

import java.util.ArrayList;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName BottomSheetDialogAdapter
 * @Author tangyang
 * @DATE 2018/6/7
 **/
public class BottomSheetDialogAdapter extends BaseQuickAdapter<DialogItemBean, BaseViewHolder> {

    public BottomSheetDialogAdapter() {
        super(android.R.layout.simple_list_item_1, new ArrayList<DialogItemBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, DialogItemBean item) {
        TextView textView = helper.getView(android.R.id.text1);
        textView.setText(item.getName());
    }
}
