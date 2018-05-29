package com.xkh.hzp.xkh.entity;

/**
 * @packageName com.xkh.hzp.xkh.entity
 * @FileName AuthCodeStatusEnum
 * @Author tangyang
 * @DATE 2018/5/25
 **/
public enum AuthCodeStatusEnum {

    AUTH_CODE_REG("reg", "注册"), AUTH_CODE_LOGIN("login", "注册"), AUTH_CODE_PWD("pwd", "找回密码");

    private String type;
    private String desc;

    AuthCodeStatusEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
