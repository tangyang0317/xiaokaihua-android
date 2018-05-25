package com.xkh.hzp.xkh.entity.request;

import java.io.Serializable;

/**
 * @packageName com.xkh.hzp.xkh.entity.requestParam
 * @FileName RegisterParam
 * @Author tangyang
 * @DATE 2018/5/24
 **/
public class RegisterParam implements Serializable {


    /**
     * authCode : string
     * password : string
     * phone : string
     * source : string
     */

    private String authCode;
    private String password;
    private String phone;
    private String source;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
