package com.xkh.hzp.xkh.entity.result;

import java.io.Serializable;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName UserInfoResult
 * @Author tangyang
 * @DATE 2018/5/30
 **/
public class UserInfoResult implements Serializable {


    /**
     * id : 1006588
     * name : 校开花564g7r
     * sex : 1
     * headPortrait : http://xkh-cdn.007fenqi.com/icon_female_selected.png
     * account : 15728042034
     * userType : normal
     * idNo : null
     * phone : 15728042034
     * source : cash
     * status : normal
     * createTime : 1527673436000
     * updateTime : 1527673436000
     * attr : null
     * thirdId : 1001761117103591400
     * certPassed : null
     * locale : null
     */

    private int id;
    private String name;
    private String sex;
    private String headPortrait;
    private String account;
    private String userType;
    private Object idNo;
    private String phone;
    private String source;
    private String status;
    private long createTime;
    private long updateTime;
    private Object attr;
    private long thirdId;
    private Object certPassed;
    private Object locale;
    private String personSignature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Object getIdNo() {
        return idNo;
    }

    public void setIdNo(Object idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Object getAttr() {
        return attr;
    }

    public void setAttr(Object attr) {
        this.attr = attr;
    }

    public long getThirdId() {
        return thirdId;
    }

    public void setThirdId(long thirdId) {
        this.thirdId = thirdId;
    }

    public Object getCertPassed() {
        return certPassed;
    }

    public void setCertPassed(Object certPassed) {
        this.certPassed = certPassed;
    }

    public Object getLocale() {
        return locale;
    }

    public void setLocale(Object locale) {
        this.locale = locale;
    }

    public String getPersonSignature() {
        return personSignature;
    }

    public void setPersonSignature(String personSignature) {
        this.personSignature = personSignature;
    }
}
