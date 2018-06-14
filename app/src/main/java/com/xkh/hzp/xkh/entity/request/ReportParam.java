package com.xkh.hzp.xkh.entity.request;

/**
 * @packageName com.xkh.hzp.xkh.entity.request
 * @FileName ReportParam
 * @Author tangyang
 * @DATE 2018/6/13
 **/
public class ReportParam {


    /**
     * content : string
     * dynamicId : 0
     * reportType : string
     * title : string
     * userId : 0
     */
    private String content;
    private long dynamicId;
    private String reportType;
    private String title;
    private long userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(long dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
