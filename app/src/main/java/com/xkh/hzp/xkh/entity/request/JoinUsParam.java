package com.xkh.hzp.xkh.entity.request;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity.request
 * @FileName JoinUsParam
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class JoinUsParam {
    /**
     * applyImageList : ["string"]
     * high : 0
     * id : 0
     * microblogName : string
     * phone : string
     * userId : 0
     * weight : 0
     */
    private int high;
    private int id;
    private String microblogName;
    private String phone;
    private String contactName;
    private String userId;
    private int weight;
    private List<String> applyImageList;

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMicroblogName() {
        return microblogName;
    }

    public void setMicroblogName(String microblogName) {
        this.microblogName = microblogName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<String> getApplyImageList() {
        return applyImageList;
    }

    public void setApplyImageList(List<String> applyImageList) {
        this.applyImageList = applyImageList;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
