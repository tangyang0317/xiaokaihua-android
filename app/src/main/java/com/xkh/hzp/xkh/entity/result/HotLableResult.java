package com.xkh.hzp.xkh.entity.result;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName HotLableResult
 * @Author tangyang
 * @DATE 2018/5/28
 **/
public class HotLableResult {


    /**
     * createTime : 2018-05-28T09:57:06.176Z
     * iconUrl : string
     * id : 0
     * level : 0
     * operatorId : 0
     * relatedSignatureId : string
     * signatureName : string
     * signatureType : string
     * status : string
     * updateTime : 2018-05-28T09:57:06.176Z
     */

    private String iconUrl;
    private int id;
    private String signatureName;
    private String signatureType;
    private String status;


    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
