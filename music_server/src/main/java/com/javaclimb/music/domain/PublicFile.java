package com.javaclimb.music.domain;

import java.io.Serializable;
import java.util.Date;

public class PublicFile implements Serializable {
    //    主键
    private Integer id;
    //    上传者id
    private Integer uploaderId;
    //文件名
    private String name;
    //    发布文件理由简介
    private String introduction;
    //    创建时间
    private Date createTime;
    //    修改时间
    private Date updateTime;
    //    文件审核状态
    private Integer status;
    //    一级审核者审核结果反馈原因
    private String firstReason;
    //    二级审核者审核结果反馈原因
    private String secondReason;
    //预计发布开始日期
    private Date beginDate;
    //预计发布结束日期
    private Date endDate;
    //预计每日开始时间
    private String beginTime;
    //预计每日结束时间
    private String endTime;
    //    文件地址
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFirstReason() {
        return firstReason;
    }

    public void setFirstReason(String firstReason) {
        this.firstReason = firstReason;
    }

    public String getSecondReason() {
        return secondReason;
    }

    public void setSecondReason(String secondReason) {
        this.secondReason = secondReason;
    }

    public String getUrl() {
        return url;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public void setUrl(String url) {
        this.url = url;
    }


}
