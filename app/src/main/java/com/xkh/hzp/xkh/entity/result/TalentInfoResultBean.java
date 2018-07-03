package com.xkh.hzp.xkh.entity.result;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName TalentInfoResultBean
 * @Author tangyang
 * @DATE 2018/5/25
 **/
public class TalentInfoResultBean {

    /**
     * attributeId : 0
     * constellation : string
     * high : 0
     * imgUrl : string
     * measurements : string
     * nickname : string
     * personalitySignature : string
     * signatureName : string
     * status : string
     * style : string
     * userId : 0
     * weight : 0
     */

    private int attributeId;
    private String constellation;
    private String high;
    private List<String> imgUrl;
    private String measurements;
    private String nickname;
    private String personalitySignature;
    private List<String> signatureName;
    private String status;
    private String style;
    private String personIntro;
    private int userId;
    private String weight;

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPersonalitySignature() {
        return personalitySignature;
    }

    public void setPersonalitySignature(String personalitySignature) {
        this.personalitySignature = personalitySignature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<String> getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(List<String> signatureName) {
        this.signatureName = signatureName;
    }

    public String getPersonIntro() {
        return personIntro;
    }

    public void setPersonIntro(String personIntro) {
        this.personIntro = personIntro;
    }
}
