package com.ext.springboot.springproducer.entity;

import java.io.Serializable;

/**
 * @Author: xting
 * @CreateDate: 2019/5/27 15:21
 */
public class Order implements Serializable {

    private static final long serialVersionUID = -1281798097234536285L;

    private String id;

    private String name;

    /** 标识消息的唯一id */
    private String messageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
