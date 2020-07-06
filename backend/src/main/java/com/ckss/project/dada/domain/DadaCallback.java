package com.ckss.project.dada.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @className: DadaCallback
 * @description: TODO
 * @author: cksspk
 * @date: 2020/6/13
 **/
@Data
@ToString
public class DadaCallback implements Serializable {

    //	返回达达运单号，默认为空
    @JsonProperty("client_id")
    private String clientId;

    //添加订单接口中的origin_id值
    @JsonProperty("order_id")
    private String orderId;

    //订单状态(待接单＝1,待取货＝2,配送中＝3,已完成＝4,已取消＝5, 指派单=8,妥投异常之物品返回中=9, 妥投异常之物品返回完成=10, 骑士到店=100,创建达达运单失败=1000 可参考文末的状态说明）
    @JsonProperty("order_status")
    private Integer orderStatus;

    //订单取消原因,其他状态下默认值为空字符串
    @JsonProperty("cancel_reason")
    private String cancelReason;

    //订单取消原因来源(1:达达配送员取消；2:商家主动取消；3:系统或客服取消；0:默认值)
    @JsonProperty("cancel_from")
    private Integer cancelFrom;

    //更新时间，时间戳除了创建达达运单失败=100的精确毫秒，其他时间戳精确到秒
    @JsonProperty("update_time")
    private Long updateTime;

    //对client_id, order_id, update_time的值进行字符串升序排列，再连接字符串，取md5值
    @JsonProperty("signature")
    private String signature;

    //达达配送员id，接单以后会传
    @JsonProperty("dm_id")
    private Integer dmId;

    //配送员姓名，接单以后会传
    @JsonProperty("dm_name")
    private String dmName;

    //配送员手机号，接单以后会传
    @JsonProperty("dm_mobile")
    private String dmMobile;
}
