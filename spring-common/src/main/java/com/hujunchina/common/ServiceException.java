package com.hujunchina.common;

/**
 * 异常枚举类
 * 包括 key，msg
 * @Author hujunchina@outlook.com
 * @Date 2020-06-16
 * 通过定义枚举异常返回结果（来自edge-gateway-proxy）
 */
public enum ServiceException {
    ERROR("error", "some thing error")
    ;

    /** 关键字*/
    private String key;

    /** 描述信息*/
    private String msg;

    ServiceException(String key, String msg){
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }

    public ServiceException getException(String key){
        for (ServiceException exception : ServiceException.values()) {
            if (key.equals(exception.getKey())) {
                return exception;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "异常："+key+"("+msg+")";
    }
}
