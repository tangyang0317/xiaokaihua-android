package com.xkh.hzp.xkh.entity.result;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName ReplyMessageResult
 * @Author tangyang
 * @DATE 2018/6/27
 **/
public class ReplyMessageResult {


    /**
     * comment : string
     * commentId : 0
     * createTime : 2018-06-27T10:02:35.660Z
     * headPortrait : string
     * name : string
     * simpleDynamicResult : {"annexUrl":"string","dynamicId":0,"wordDescription":"string"}
     * userId : 0
     */

    private String comment;
    private long commentId;
    private long createTime;
    private String headPortrait;
    private String name;
    private SimpleDynamicResultBean simpleDynamicResult;
    private long userId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public SimpleDynamicResultBean getSimpleDynamicResult() {
        return simpleDynamicResult;
    }

    public void setSimpleDynamicResult(SimpleDynamicResultBean simpleDynamicResult) {
        this.simpleDynamicResult = simpleDynamicResult;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public static class SimpleDynamicResultBean {
        /**
         * annexUrl : string
         * dynamicId : 0
         * wordDescription : string
         */

        private String annexUrl;
        private long dynamicId;
        private String wordDescription;
        private String dynamicType;
        private String faceUrl;

        public String getAnnexUrl() {
            return annexUrl;
        }

        public void setAnnexUrl(String annexUrl) {
            this.annexUrl = annexUrl;
        }

        public long getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(long dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getWordDescription() {
            return wordDescription;
        }

        public void setWordDescription(String wordDescription) {
            this.wordDescription = wordDescription;
        }

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
    }
}
