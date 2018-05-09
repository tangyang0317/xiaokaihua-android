package com.xkh.hzp.xkh.entity;

import java.io.Serializable;

/**
 * @packageName com.xkh.hzp.xkh.entity
 * @FileName TalentBean
 * @Author tangyang
 * @DATE 2018/5/7
 **/
public class TalentBean implements Serializable {

    private String id;
    private String imgUrl;
    private String headImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
