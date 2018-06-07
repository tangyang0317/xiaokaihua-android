package com.xkh.hzp.xkh.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.result.SearchUserResult;
import com.xkh.hzp.xkh.utils.GlideCircleTransform;

import java.util.ArrayList;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName SearchUserAdapter
 * @Author tangyang
 * @DATE 2018/6/4
 **/
public class SearchUserAdapter extends BaseQuickAdapter<SearchUserResult, BaseViewHolder> {

    public SearchUserAdapter() {
        super(R.layout.item_search_user, new ArrayList<SearchUserResult>());
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchUserResult item) {
        ImageView searchUserImg = helper.getView(R.id.searchUserImg);
        TextView searchUserNickNameTxt = helper.getView(R.id.searchUserNickNameTxt);
        TextView searchUserPublishNoteTxt = helper.getView(R.id.searchUserPublishNoteTxt);
        Glide.with(mContext).load(item.getHeadPortrait()).transform(new GlideCircleTransform(mContext)).error(R.mipmap.icon_female_selected).placeholder(R.mipmap.icon_female_selected).into(searchUserImg);
        searchUserNickNameTxt.setText(item.getName());
        searchUserPublishNoteTxt.setText("动态·" + item.getNumber());
    }
}
