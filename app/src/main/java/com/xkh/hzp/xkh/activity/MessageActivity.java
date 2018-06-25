package com.xkh.hzp.xkh.activity;

import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.xkh.hzp.xkh.R;
import com.xkh.hzp.xkh.config.UrlConfig;
import com.xkh.hzp.xkh.entity.result.UnReadMsgResult;
import com.xkh.hzp.xkh.event.RefreshDotEvent;
import com.xkh.hzp.xkh.http.ABHttp;
import com.xkh.hzp.xkh.http.AbHttpCallback;
import com.xkh.hzp.xkh.http.AbHttpEntity;
import com.xkh.hzp.xkh.utils.DateUtils;
import com.xkh.hzp.xkh.utils.UserDataManager;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import xkh.hzp.xkh.com.base.base.BaseActivity;
import xkh.hzp.xkh.com.base.utils.JsonUtils;
import xkh.hzp.xkh.com.base.utils.SharedprefrenceHelper;
import xkh.hzp.xkh.com.base.view.ItemLayout;

/**
 * @packageName com.xkh.hzp.xkh.activity
 * @FileName MessageActivity
 * @Author tangyang
 * @DATE 2018/6/9
 **/
public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private ItemLayout commentMsgItemLayout, likeMsgItemLayout, noticeMsgItemLayout;
    private View likeBottomLine;

    private boolean commentMsgUnRead, likeMsgUnRead;

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
        likeBottomLine = findViewById(R.id.likeBottomLine);
        if (UserDataManager.getInstance().getUserInfo() != null && !"talent".equals(UserDataManager.getInstance().getUserInfo().getUserType())) {
            likeMsgItemLayout.setVisibility(View.GONE);
            likeBottomLine.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryUnReadMsg();
    }

    /***
     * 查询未读消息
     */
    private void queryUnReadMsg() {
        Map<String, String> param = new HashMap<>();
        param.put("lastReadTime", (String) SharedprefrenceHelper.getIns(this).get("lastReadTime", "2017-01-01 00:00:00"));
        param.put("userId", UserDataManager.getInstance().getUserId());
        ABHttp.getIns().postJSON(UrlConfig.msgUnRead, JsonUtils.toJson(param), new AbHttpCallback() {
            @Override
            public void setupEntity(AbHttpEntity entity) {
                super.setupEntity(entity);
                entity.putField("result", new TypeToken<UnReadMsgResult>() {
                }.getType());
            }

            @Override
            public void onSuccessGetObject(String code, String msg, boolean success, HashMap<String, Object> extra) {
                super.onSuccessGetObject(code, msg, success, extra);
                if (success) {
                    UnReadMsgResult unReadMsgResult = (UnReadMsgResult) extra.get("result");
                    if (unReadMsgResult != null) {
                        commentMsgUnRead = unReadMsgResult.isHaveUnreadComment();
                        likeMsgUnRead = unReadMsgResult.isHaveUnreadLike();
                        if (!unReadMsgResult.isHaveUnreadLike()) {
                            likeMsgItemLayout.setRightIcon(0);
                        }
                        if (!unReadMsgResult.isHaveUnreadComment()) {
                            commentMsgItemLayout.setRightIcon(0);
                        }
                    }
                }
            }
        });
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
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.dateFormatYMDHMS);
            SharedprefrenceHelper.getIns(this).put("lastReadTime", sdf.format(new Date()));
            commentMsgUnRead = false;
            if (!commentMsgUnRead && !likeMsgUnRead) {
                EventBus.getDefault().post(new RefreshDotEvent());
            }
            CommentMessageActivity.lunchActivity(MessageActivity.this, null, CommentMessageActivity.class);
        } else if (view == likeMsgItemLayout) {
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.dateFormatYMDHMS);
            SharedprefrenceHelper.getIns(this).put("lastReadTime", sdf.format(new Date()));
            likeMsgUnRead = false;
            if (!commentMsgUnRead && !likeMsgUnRead) {
                EventBus.getDefault().post(new RefreshDotEvent());
            }
            LikeMessageActivity.lunchActivity(MessageActivity.this, null, LikeMessageActivity.class);
        } else if (view == noticeMsgItemLayout) {
            NoticeActivity.lunchActivity(MessageActivity.this, null, NoticeActivity.class);
        }
    }
}
