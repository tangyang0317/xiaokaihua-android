package com.xkh.hzp.xkh.entity.result;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName CommentMessageResult
 * @Author tangyang
 * @DATE 2018/6/9
 **/
public class CommentMessageResult {


    /**
     * replyContent : Ty
     * replyUserName : 胡小康
     * headPortrait : http://images.xkhstar.com/22FB1AE2-37D5-448A-93ED-828B052E0B8C_file
     * replyUserId : 1006652
     * createTime : 1528445906000
     * replyId : 5
     * comment : 123456
     * userId : 1006677
     * userName : 唐洋
     * simpleDynamicResult : {"dynamicId":38,"wordDescription":"在经济消费能力之外，许多人为了过上所谓的更好的生活，贷款买苹果，买单反，买奢侈品，而年轻群体很多时候就成了目前这种\u201c刺激消费\u201d下的受害者。","annexUrl":"http://images.xkhstar.com/152843410008500000.jpg,http://images.xkhstar.com/152843410241400000.jpg,http://images.xkhstar.com/152843410209700000.jpg"}
     */
    private long commentId;
    private String replyContent;
    private String replyUserName;
    private String headPortrait;
    private long replyUserId;
    private long createTime;
    private int replyId;
    private String comment;
    private String content;
    private long userId;
    private String userName;
    private SimpleDynamicResultBean simpleDynamicResult;

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SimpleDynamicResultBean getSimpleDynamicResult() {
        return simpleDynamicResult;
    }

    public void setSimpleDynamicResult(SimpleDynamicResultBean simpleDynamicResult) {
        this.simpleDynamicResult = simpleDynamicResult;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public static class SimpleDynamicResultBean {
        /**
         * dynamicId : 38
         * wordDescription : 在经济消费能力之外，许多人为了过上所谓的更好的生活，贷款买苹果，买单反，买奢侈品，而年轻群体很多时候就成了目前这种“刺激消费”下的受害者。
         * annexUrl : http://images.xkhstar.com/152843410008500000.jpg,http://images.xkhstar.com/152843410241400000.jpg,http://images.xkhstar.com/152843410209700000.jpg
         */

        private int dynamicId;
        private String wordDescription;
        private String annexUrl;
        private String dynamicType;
        private String faceUrl;

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

        public int getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(int dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getWordDescription() {
            return wordDescription;
        }

        public void setWordDescription(String wordDescription) {
            this.wordDescription = wordDescription;
        }

        public String getAnnexUrl() {
            return annexUrl;
        }

        public void setAnnexUrl(String annexUrl) {
            this.annexUrl = annexUrl;
        }
    }
}
