package com.imooc.sell.VO;

import java.io.Serializable;

/**
 * 返回给前端的结果
 */
public class ResultVO<T> implements Serializable{

    private static final long serialVersionUID = -1420171765608846193L;
    /** 错误码.*/
    private String code;

    /** 错误信息.*/
    private String msg;

    /** 具体内容.*/
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
