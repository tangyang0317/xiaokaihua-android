package com.xkh.hzp.xkh.entity;

/**
 * @packageName com.xkh.hzp.xkh.entity
 * @FileName QiNiuBean
 * @Author tangyang
 * @DATE 2018/5/16
 **/
public class QiNiuBean {

    public String code;
    public String status;
    private String hash;
    private String key;
    public String duration;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
