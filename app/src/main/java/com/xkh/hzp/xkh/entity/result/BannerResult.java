package com.xkh.hzp.xkh.entity.result;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName BannerResult
 * @Author tangyang
 * @DATE 2018/5/29
 **/
public class BannerResult {


    /**
     * id : 10
     * imgUrl : http://images.xkhstar.com/c826af325c972f1e7641f918dc79e312.jpg
     * showPosition : index
     * jumpType : string
     * jumpTarget : string
     * isShow : true
     * createTime : null
     * modifyTime : 2018-05-29 17:10:51
     * operatorId : null
     * beginTime : null
     * endTime : null
     */

    private int id;
    private String imgUrl;
    private String showPosition;
    private String jumpType;
    private String jumpTarget;
    private boolean isShow;
    private String createTime;
    private String modifyTime;
    private String operatorId;
    private String beginTime;
    private String endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(String showPosition) {
        this.showPosition = showPosition;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpTarget() {
        return jumpTarget;
    }

    public void setJumpTarget(String jumpTarget) {
        this.jumpTarget = jumpTarget;
    }

    public boolean isIsShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
