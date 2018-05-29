package com.xkh.hzp.xkh.entity.request;

import java.io.Serializable;
import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity.request
 * @FileName PublishDynamicParam
 * @Author tangyang
 * @DATE 2018/5/28
 **/
public class PublishDynamicParam implements Serializable {

    /**
     * annexUrl : ["string"]
     * dynamicType : string
     * faceUrl : string
     * wordDescription : string
     */

    private String dynamicType;
    private String faceUrl;
    private String wordDescription;
    private List<String> annexUrl;

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getWordDescription() {
        return wordDescription;
    }

    public void setWordDescription(String wordDescription) {
        this.wordDescription = wordDescription;
    }

    public List<String> getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(List<String> annexUrl) {
        this.annexUrl = annexUrl;
    }
}
