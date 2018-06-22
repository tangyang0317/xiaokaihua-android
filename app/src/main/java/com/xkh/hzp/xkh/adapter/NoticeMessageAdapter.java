package com.xkh.hzp.xkh.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.entity.result.NoticeMessageResult;
import com.xkh.hzp.xkh.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @packageName com.xkh.hzp.xkh.adapter
 * @FileName NoticeMessageAdapter
 * @Author tangyang
 * @DATE 2018/6/13
 **/
public class NoticeMessageAdapter extends BaseQuickAdapter<NoticeMessageResult, BaseViewHolder> {


    public NoticeMessageAdapter() {
        super(R.layout.item_notice_message, new ArrayList<NoticeMessageResult>());
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeMessageResult item) {
        ImageView noticeImg = helper.getView(R.id.noticeImg);
        TextView noticeTitleTxt = helper.getView(R.id.noticeTitleTxt);
        TextView noticeDateTxt = helper.getView(R.id.noticeDateTxt);
        View noticeBottomLine = helper.getView(R.id.noticeBottomLine);
        noticeTitleTxt.setText(item.getPushContent());
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.dateFormatYMDHM);
        noticeDateTxt.setText(sdf.format(item.getCreateTime()));
        if (helper.getPosition() == 0) {
            noticeBottomLine.setVisibility(View.GONE);
        } else {
            noticeBottomLine.setVisibility(View.VISIBLE);
        }
    }
}
