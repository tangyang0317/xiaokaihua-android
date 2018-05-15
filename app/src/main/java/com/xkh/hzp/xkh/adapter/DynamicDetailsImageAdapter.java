package com.xkh.hzp.xkh.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;

import java.util.ArrayList;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName DynamicDetailsImageAdapter
 * @Author tangyang
 * @DATE 2018/5/12
 **/
public class DynamicDetailsImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DynamicDetailsImageAdapter() {
        super(R.layout.view_item_dynamic_details_img, new ArrayList<String>());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.dynamicDetailsImg);
        imageView.setImageResource(R.drawable.example);
    }
}
