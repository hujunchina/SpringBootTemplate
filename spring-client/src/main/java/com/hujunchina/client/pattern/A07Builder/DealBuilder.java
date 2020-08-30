package com.hujunchina.client.pattern.A07Builder;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/8/16 4:47 下午
 * @Version 1.0
 */
public class DealBuilder {

    private static DealBuilder dealBuilder = null;

    private static DealDO deal = null;

    private DealBuilder(){}

    public static DealBuilder getDealBuilder() {
        if (dealBuilder  == null){
            dealBuilder = new DealBuilder();
        }
        if (deal == null){
            deal = new DealDO();
        }
        return dealBuilder;
    }


    public DealDO getDeal(){
        return deal;
    }

    public DealBuilder withId(int id){
        deal.setId(id);
        return this;
    }

    public DealBuilder withSn(String sn){
        deal.setSn(sn);
        return this;
    }

    public DealBuilder withType(String type) {
        deal.setType(type);
        return this;
    }

    public DealBuilder withPlace(String place) {
        deal.setPlace(place);
        return this;
    }
}
