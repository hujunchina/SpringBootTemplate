package com.hujunchina.client.pattern.A07Builder;


import lombok.Data;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/16 4:15 下午
 * @Version 1.0
 */
@Data
public class DealDO {

    private int id;

    private String sn;

    private String place;

    private String latitude;

    private String longitude;

    private String date;

    private String owner;

    private String operator;

    private String type;

    private Double price;
}
