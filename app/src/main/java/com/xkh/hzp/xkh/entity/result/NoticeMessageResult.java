package com.xkh.hzp.xkh.entity.result;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName NoticeMessageResult
 * @Author tangyang
 * @DATE 2018/6/13
 **/
public class NoticeMessageResult {

    private long createTime;
    private String name;
    private String pushContent;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }
}
