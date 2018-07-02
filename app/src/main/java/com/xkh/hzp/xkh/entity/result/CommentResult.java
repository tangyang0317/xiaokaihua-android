package com.xkh.hzp.xkh.entity.result;

import java.util.List;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName CommentResult
 * @Author tangyang
 * @DATE 2018/6/6
 **/
public class CommentResult {


    /**
     * commentResult : {"id":10,"userId":1006652,"likeNumber":0,"comment":"","createTime":1528191862000,"name":"uuu","headPortrait":"http://xkh-cdn.007fenqi.com/1DDAD9E5-312D-46F8-98BA-CB2A99E5D499_file","status":null}
     * replyResults : []
     */

    private CommentResultBean commentResult;
    private List<ReplyResult> replyResults;

    public CommentResultBean getCommentResult() {
        return commentResult;
    }

    public void setCommentResult(CommentResultBean commentResult) {
        this.commentResult = commentResult;
    }

    public List<ReplyResult> getReplyResults() {
        return replyResults;
    }

    public void setReplyResults(List<ReplyResult> replyResults) {
        this.replyResults = replyResults;
    }


    public static class ReplyResult {
        private long id;
        private long replyUserId;
        private String replyUserName;
        private long userId;
        private String name;
        private String replyContent;
        private long parentId;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(long replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getReplyUserName() {
            return replyUserName;
        }

        public void setReplyUserName(String replyUserName) {
            this.replyUserName = replyUserName;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public long getParentId() {
            return parentId;
        }

        public void setParentId(long parentId) {
            this.parentId = parentId;
        }
    }

    public static class CommentResultBean {
        /**
         * id : 10
         * userId : 1006652
         * likeNumber : 0
         * comment :
         * createTime : 1528191862000
         * name : uuu
         * headPortrait : http://xkh-cdn.007fenqi.com/1DDAD9E5-312D-46F8-98BA-CB2A99E5D499_file
         * status : null
         */

        private long id;
        private long userId;
        private int likeNumber;
        private String comment;
        private long createTime;
        private String name;
        private String headPortrait;
        private String status;
        private String userType;

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

        public int getLikeNumber() {
            return likeNumber;
        }

        public void setLikeNumber(int likeNumber) {
            this.likeNumber = likeNumber;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}
