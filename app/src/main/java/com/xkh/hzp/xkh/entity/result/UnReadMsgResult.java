package com.xkh.hzp.xkh.entity.result;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName UnReadMsgResult
 * @Author tangyang
 * @DATE 2018/6/22
 **/
public class UnReadMsgResult {

    private boolean haveUnreadComment;
    private boolean haveUnreadLike;

    public boolean isHaveUnreadComment() {
        return haveUnreadComment;
    }

    public void setHaveUnreadComment(boolean haveUnreadComment) {
        this.haveUnreadComment = haveUnreadComment;
    }

    public boolean isHaveUnreadLike() {
        return haveUnreadLike;
    }

    public void setHaveUnreadLike(boolean haveUnreadLike) {
        this.haveUnreadLike = haveUnreadLike;
    }
}
