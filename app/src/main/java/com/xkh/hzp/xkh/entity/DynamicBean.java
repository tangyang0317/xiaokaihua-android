package com.xkh.hzp.xkh.entity;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity
 * @FileName DynamicBean
 * @Author tangyang
 * @DATE 2018/5/8
 **/
public class DynamicBean {


    /**
     * dynamicId : 21
     * userId : 1006652
     * nickname : 李大神是校花达人
     * headPortrait : string
     * roleType : null
     * wordDescription : 第一篇动态，希望大家多多关注
     * viewNumber : 0
     * likeNumber : 0
     * annexUrl : http://xkh-cdn.007fenqi.com/152783951409600000.jpg,http://xkh-cdn.007fenqi.com/152783951364300000.jpg,http://xkh-cdn.007fenqi.com/152783951243000000.jpg,http://xkh-cdn.007fenqi.com/152783951468000000.jpg,http://xkh-cdn.007fenqi.com/152783951426200000.jpg
     * annexUrlList : ["http://xkh-cdn.007fenqi.com/152783951409600000.jpg","http://xkh-cdn.007fenqi.com/152783951364300000.jpg","http://xkh-cdn.007fenqi.com/152783951243000000.jpg","http://xkh-cdn.007fenqi.com/152783951468000000.jpg"]
     * updateTime : 1527839425000
     * status : null
     */

    private int dynamicId;
    private int userId;
    private String nickname;
    private String headPortrait;
    private String wordDescription;
    private int viewNumber;
    private int likeNumber;
    private String annexUrl;
    private long updateTime;
    private String status;
    private String dynamicType;
    private List<String> annexUrlList;

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getWordDescription() {
        return wordDescription;
    }

    public void setWordDescription(String wordDescription) {
        this.wordDescription = wordDescription;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAnnexUrlList() {
        return annexUrlList;
    }

    public void setAnnexUrlList(List<String> annexUrlList) {
        this.annexUrlList = annexUrlList;
    }

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }
}
