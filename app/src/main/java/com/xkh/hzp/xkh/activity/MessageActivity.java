package com.xkh.hzp.xkh.activity;

import android.view.View;

import com.xkh.hzp.xkh.R;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.view.ItemLayout;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName MessageActivity
 * @Author tangyang
 * @DATE 2018/6/9
 **/
public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private ItemLayout commentMsgItemLayout, likeMsgItemLayout, noticeMsgItemLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        setToolbarTitleTv("消息");
        commentMsgItemLayout = findViewById(R.id.commentMsgItemLayout);
        likeMsgItemLayout = findViewById(R.id.likeMsgItemLayout);
        noticeMsgItemLayout = findViewById(R.id.noticeMsgItemLayout);
    }

    @Override
    public void setListenner() {
        commentMsgItemLayout.setOnClickListener(this);
        likeMsgItemLayout.setOnClickListener(this);
        noticeMsgItemLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == commentMsgItemLayout) {
            CommentMessageActivity.lunchActivity(MessageActivity.this, null, CommentMessageActivity.class);
        } else if (view == likeMsgItemLayout) {
            LikeMessageActivity.lunchActivity(MessageActivity.this, null, LikeMessageActivity.class);
        }
    }
}
