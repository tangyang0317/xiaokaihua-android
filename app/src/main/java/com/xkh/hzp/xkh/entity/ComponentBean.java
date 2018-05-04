package com.xkh.hzp.xkh.entity;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity
 * @FileName ComponentBean
 * @Author tangyang
 * @DATE 2018/4/28
 **/
public class ComponentBean {

    private String content;
    private List<String> replayList;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getReplayList() {
        return replayList;
    }

    public void setReplayList(List<String> replayList) {
        this.replayList = replayList;
    }
}
