package com.xkh.hzp.xkh.entity;

/**
 * @packageName com.xkh.hzp.xkh.entity
 * @FileName DialogItemBean
 * @Author tangyang
 * @DATE 2018/6/7
 **/
public class DialogItemBean {

    private String name;

    public DialogItemBean(String name, String operate) {
        this.name = name;
        this.operate = operate;
    }

    private String operate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}
