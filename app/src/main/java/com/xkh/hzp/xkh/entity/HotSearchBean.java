package com.xkh.hzp.xkh.entity;

import java.io.Serializable;

/**
 * Created by tangyang on 17/9/26.
 */

public class HotSearchBean implements Serializable {


    /**
     * id : 37
     * modifyTime : 1506408411000
     * order : 1
     * word : 当用户手动勾选全选 Checkbox
     */

    private int id;
    private long modifyTime;
    private int order;
    private String word;
    private Integer type; //type=1 默认搜索 type=2 热门搜索
    private boolean red;//true:标红

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

}
