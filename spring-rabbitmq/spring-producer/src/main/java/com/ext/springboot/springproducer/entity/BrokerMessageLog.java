package com.ext.springboot.springproducer.entity;

import java.util.Date;

/**
 * @Author: xting
 * @CreateDate: 2019/5/29 11:29
 *
 * 消息记录实体
 */
public class BrokerMessageLog {

    /** 消息id.*/
    private String messageId;

    /** 消息体.*/
    private String message;

    /** 发送次数.*/
    private Integer tryCount;

    /** 发送状态.*/
    private String status;

    /** 超时时间.*/
    private Date nextRetry;

    /** 创建时间.*/
    private Date createTime;

    /** 更新时间.*/
    private Date updateTime;

    public BrokerMessageLog() {
    }

    public BrokerMessageLog(String messageId, String message, Integer tryCount, String status, Date nextRetry, Date createTime, Date updateTime) {
        this.messageId = messageId;
        this.message = message;
        this.tryCount = tryCount;
        this.status = status;
        this.nextRetry = nextRetry;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
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
}
