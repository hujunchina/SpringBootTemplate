package com.hujunchina.common;

import java.io.Serializable;

/**
 * 服务端返回状态码
 * 需要 code，和 msg 两个字段
 * 需要实现序列化！
 *
 * @Author hujunchina@outlook.com
 * @Date 2020--6-16
 */
public enum ServiceResponseCode implements Serializable {

    SUCCESS(200, "处理成功"),

    FAILED(600, "处理失败"),

    SERVICE_ERROR(500, "服务端错误");

    /** 状态码*/
    private Integer code;

    /** 状态信息*/
    private String msg;

    /** 构造方法，不能是public*/
    ServiceResponseCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }

    /** 根据code 得到类对象*/
    public static ServiceResponseCode getCode(Integer code){
        for (ServiceResponseCode responseCode : ServiceResponseCode.values()) {
            if (code.equals(responseCode.getCode())) {
                return responseCode;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "状态码："+this.code+"（"+this.msg+"）";
    }
}
