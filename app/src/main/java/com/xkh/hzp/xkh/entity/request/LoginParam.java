package com.xkh.hzp.xkh.entity.request;

import java.io.Serializable;

/**
 * 登陆参数
 *
 * @packageName com.xkh.hzp.xkh.entity.requestParam
 * @FileName LoginParam
 * @Author tangyang
 * @DATE 2018/5/24
 **/
public class LoginParam implements Serializable {

    /**
     * authCode :
     * deviceType : string
     * location : string
     * password : tangyang0317
     * phone : 15068789569
     * uniqueId : string
     */

    private String authCode;
    private String deviceType;
    private String location;
    private String password;
    private String phone;
    private String uniqueId;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
