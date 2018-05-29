package com.xkh.hzp.xkh.entity.result;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName TalentResult
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class TalentResult {


    /**
     * imgUrl : string
     * nickname : string
     * signatureName : ["string"]
     * status : string
     * userId : 0
     */

    private String imgUrl;
    private String nickname;
    private String status;
    private int userId;
    private List<String> signatureName;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(List<String> signatureName) {
        this.signatureName = signatureName;
    }
}
