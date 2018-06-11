package com.xkh.hzp.xkh.entity.result;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName DynamicDetailsResult
 * @Author tangyang
 * @DATE 2018/6/5
 **/
public class DynamicDetailsResult {

    /**
     * xkhTalentDynamic : {"id":27,"userId":1006652,"attributeId":43,"faceUrl":null,"dynamicType":"image","wordDescription":"比赛进行到第4节还剩3分27秒时，勇士后卫斯蒂芬-库里已经上场38分钟，26投11中三分球17投9中，得到33分7篮板8助攻。在投进9个三分球之后，库里超越雷-阿伦（8个）成为NBA总决赛历史上单场投进最多三分的球员。此外，17次三分出手也是NBA总决赛单场三分出手次数新高。第一","viewNumber":0,"likeNumber":0,"status":"normal","refuseReason":null,"level":null,"createTime":null,"updateTime":"2018-06-04 16:37:51","operatorId":1006652}
     * xkhTalentDynamicAnnexList : [{"id":42,"dynamicId":27,"annexUrl":"http://xkh-cdn.007fenqi.com/152808040865900000.jpg","createTime":null,"updateTime":"2018-06-04 10:44:40"},{"id":43,"dynamicId":27,"annexUrl":"http://xkh-cdn.007fenqi.com/152808040907300000.jpg","createTime":null,"updateTime":"2018-06-04 10:44:40"}]
     * ucUser : {"id":1006652,"name":"uuu","sex":null,"headPortrait":"http://xkh-cdn.007fenqi.com/DA7883D3-A5D4-4831-AFE2-601F4DA32FB1_file","account":null,"userType":null,"idNo":null,"phone":null,"source":null,"status":null,"createTime":null,"updateTime":null,"attr":null,"thirdId":null,"certPassed":null,"locale":null,"personSignature":null,"alias":null}
     * xkhDynamicCommentList : [{"id":7,"dynamicId":27,"userId":1006646,"likeNumber":0,"replyUserId":null,"comment":"I love you","createTime":"2018-06-04 17:01:20","updateTime":null,"operatorId":1006646,"status":"normal"}]
     */
    private XkhTalentDynamicBean xkhTalentDynamic;
    private UserSimpleResult userSimpleResult;
    private List<XkhTalentDynamicAnnexListBean> xkhTalentDynamicAnnexList;
    private int commentNumber;
    private String focusStatus;
    private String likeStatus;

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getFocusStatus() {
        return focusStatus;
    }

    public void setFocusStatus(String focusStatus) {
        this.focusStatus = focusStatus;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public XkhTalentDynamicBean getXkhTalentDynamic() {
        return xkhTalentDynamic;
    }

    public void setXkhTalentDynamic(XkhTalentDynamicBean xkhTalentDynamic) {
        this.xkhTalentDynamic = xkhTalentDynamic;
    }


    public List<XkhTalentDynamicAnnexListBean> getXkhTalentDynamicAnnexList() {
        return xkhTalentDynamicAnnexList;
    }

    public void setXkhTalentDynamicAnnexList(List<XkhTalentDynamicAnnexListBean> xkhTalentDynamicAnnexList) {
        this.xkhTalentDynamicAnnexList = xkhTalentDynamicAnnexList;
    }

    public UserSimpleResult getUserSimpleResult() {
        return userSimpleResult;
    }

    public void setUserSimpleResult(UserSimpleResult userSimpleResult) {
        this.userSimpleResult = userSimpleResult;
    }

    public static class XkhTalentDynamicBean {

        /**
         * id : 38
         * userId : 1006677
         * attributeId : 47
         * faceUrl :
         * dynamicType : image
         * wordDescription : 在经济消费能力之外，许多人为了过上所谓的更好的生活，贷款买苹果，买单反，买奢侈品，而年轻群体很多时候就成了目前这种“刺激消费”下的受害者。
         * viewNumber : 0
         * likeNumber : 0
         * status : normal
         * refuseReason :
         * level : 2
         * createTime :
         * updateTime : 1528434083000
         * operatorId : 1006677
         */

        private long id;
        private long userId;
        private int attributeId;
        private String faceUrl;
        private String dynamicType;
        private String wordDescription;
        private int viewNumber;
        private int likeNumber;
        private String status;
        private String refuseReason;
        private int level;
        private String createTime;
        private long updateTime;
        private int operatorId;


        public int getAttributeId() {
            return attributeId;
        }

        public void setAttributeId(int attributeId) {
            this.attributeId = attributeId;
        }

        public String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            this.faceUrl = faceUrl;
        }

        public String getDynamicType() {
            return dynamicType;
        }

        public void setDynamicType(String dynamicType) {
            this.dynamicType = dynamicType;
        }

        public String getWordDescription() {
            return wordDescription;
        }

        public void setWordDescription(String wordDescription) {
            this.wordDescription = wordDescription;
        }

        public int getViewNumber() {
            return viewNumber;
        }

        public void setViewNumber(int viewNumber) {
            this.viewNumber = viewNumber;
        }

        public int getLikeNumber() {
            return likeNumber;
        }

        public void setLikeNumber(int likeNumber) {
            this.likeNumber = likeNumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRefuseReason() {
            return refuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            this.refuseReason = refuseReason;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(int operatorId) {
            this.operatorId = operatorId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }

    public static class UserSimpleResult {

        private long userId;
        private int number;
        private String headPortrait;
        private String name;


        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class XkhTalentDynamicAnnexListBean {
        /**
         * id : 42
         * dynamicId : 27
         * annexUrl : http://xkh-cdn.007fenqi.com/152808040865900000.jpg
         * createTime : null
         * updateTime : 2018-06-04 10:44:40
         */

        private int id;
        private int dynamicId;
        private String annexUrl;
        private String createTime;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(int dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getAnnexUrl() {
            return annexUrl;
        }

        public void setAnnexUrl(String annexUrl) {
            this.annexUrl = annexUrl;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
