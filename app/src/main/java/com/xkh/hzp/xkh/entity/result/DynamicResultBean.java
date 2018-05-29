package com.xkh.hzp.xkh.entity.result;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName DynamicResultBean
 * @Author tangyang
 * @DATE 2018/5/25
 **/
public class DynamicResultBean {


    /**
     * annexUrl : string
     * annexUrlList : ["string"]
     * dynamicId : 0
     * headPortrait : string
     * likeNumber : 0
     * nickname : string
     * status : string
     * updateTime : 2018-05-25T06:06:32.355Z
     * userId : 0
     * userType : string
     * viewNumber : 0
     * wordDescription : string
     */

    private String annexUrl;
    private int dynamicId;
    private String headPortrait;
    private int likeNumber;
    private String nickname;
    private String status;
    private String updateTime;
    private int userId;
    private String userType;
    private int viewNumber;
    private String wordDescription;
    private List<String> annexUrlList;

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public String getWordDescription() {
        return wordDescription;
    }

    public void setWordDescription(String wordDescription) {
        this.wordDescription = wordDescription;
    }

    public List<String> getAnnexUrlList() {
        return annexUrlList;
    }

    public void setAnnexUrlList(List<String> annexUrlList) {
        this.annexUrlList = annexUrlList;
    }
}
