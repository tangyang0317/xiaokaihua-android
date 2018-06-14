package com.xkh.hzp.xkh.entity;

import java.io.Serializable;

/**
 * @packageName com.xkh.hzp.xkh.entity
 * @FileName WebUserBean
 * @Author tangyang
 * @DATE 2018/5/24
 **/
public class WebUserBean implements Serializable {

    /**
     * uid : 1006512
     * loginId : 15068789569
     * loginType : phone
     * expireTime : 1527235572175
     * loginToken : Olh9tnEL102797
     * success : true
     */
    private long uid;
    private String loginId;
    private String loginType;
    private long expireTime;
    private String loginToken;
    private boolean success;
    private boolean havePassWord;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isHavePassWord() {
        return havePassWord;
    }

    public void setHavePassWord(boolean havePassWord) {
        this.havePassWord = havePassWord;
    }
}
