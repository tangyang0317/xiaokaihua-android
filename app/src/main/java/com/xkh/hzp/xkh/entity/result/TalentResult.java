package com.xkh.hzp.xkh.entity.result;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName TalentResult
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class TalentResult {


    /**
     * <p>
     * "userId": 1006622,
     * "nickname": "我是校花达人",
     * "imgUrl": "cdn.007fenqi.com/icon_female_selected.png",
     * "status": null,
     * "signatureName": "学院风,欧美风",
     * "headPortrait": "string"
     */

    private String imgUrl;
    private String nickname;
    private String status;
    private long userId;
    private String headPortrait;
    private String signatureName;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
