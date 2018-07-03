package com.xkh.hzp.xkh.entity.result;

/**
 * @packageName com.xkh.hzp.xkh.entity.result
 * @FileName LikeMessageResult
 * @Author tangyang
 * @DATE 2018/6/9
 **/
public class LikeMessageResult {


    /**
     * createTime : 1528510887000
     * likeUserId : 1006677
     * headPortrait : http://images.xkhstar.com/152842242091000000.jpg
     * likeUserName : 唐洋
     * simpleDynamicResult : {"wordDescription":"在经济消费能力之外，许多人为了过上所谓的更好的生活，贷款买苹果，买单反，买奢侈品，而年轻群体很多时候就成了目前这种\u201c刺激消费\u201d下的受害者。","annexUrl":"http://images.xkhstar.com/152843410008500000.jpg,http://images.xkhstar.com/152843410241400000.jpg,http://images.xkhstar.com/152843410209700000.jpg"}
     */

    private long createTime;
    private long likeUserId;
    private String headPortrait;
    private String likeUserName;
    private String comment;
    private SimpleDynamicResultBean simpleDynamicResult;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLikeUserId() {
        return likeUserId;
    }

    public void setLikeUserId(long likeUserId) {
        this.likeUserId = likeUserId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getLikeUserName() {
        return likeUserName;
    }

    public void setLikeUserName(String likeUserName) {
        this.likeUserName = likeUserName;
    }

    public SimpleDynamicResultBean getSimpleDynamicResult() {
        return simpleDynamicResult;
    }

    public void setSimpleDynamicResult(SimpleDynamicResultBean simpleDynamicResult) {
        this.simpleDynamicResult = simpleDynamicResult;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static class SimpleDynamicResultBean {
        /**
         * wordDescription : 在经济消费能力之外，许多人为了过上所谓的更好的生活，贷款买苹果，买单反，买奢侈品，而年轻群体很多时候就成了目前这种“刺激消费”下的受害者。
         * annexUrl : http://images.xkhstar.com/152843410008500000.jpg,http://images.xkhstar.com/152843410241400000.jpg,http://images.xkhstar.com/152843410209700000.jpg
         */

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
