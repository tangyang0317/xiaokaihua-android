package com.xkh.hzp.xkh.entity;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity
 * @FileName DynamicBean
 * @Author tangyang
 * @DATE 2018/5/8
 **/
public class DynamicBean {

    private String title;
    private List<String> imgList;
    private String dynamicType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }
}
