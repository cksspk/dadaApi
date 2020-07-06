package com.ckss.project.dada.domain.result;

import com.ckss.project.dada.utils.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: cksspk
 **/
@Data
@ToString
public class DadaQueryResult implements Serializable {


    //第三方订单编号
    private String orderId;
    //订单状态(待接单＝1,待取货＝2,配送中＝3,已完成＝4,已取消＝5, 指派单=8,妥投异常之物品返回中=9, 妥投异常之物品返回完成=10, 骑士到店=100,创建达达运单失败=1000 可参考文末的状态说明）
    private Integer statusCode;
    //订单状态
    private String statusMsg;
    //骑手姓名
    private String transporterName;
    //骑手电话
    private String transporterPhone;
    //骑手经度
    private String transporterLng;
    //骑手纬度
    private String transporterLat;
    //配送费,单位为元
    private Double deliveryFee;
    //小费,单位为元
    private Double tips;
    //优惠券费用,单位为元
    private Double couponFee;
    //保价费,单位为元
    private Double insuranceFee;
    //实际支付费用,单位为元
    private Double actualFee;
    //配送距离,单位为米
    private Double distance;

    //发单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //接单时间,若未接单,则为空
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptTime;
    //取货时间,若未取货,则为空
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date fetchTime;
    //送达时间,若未送达,则为空
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;
    //取消时间,若未取消,则为空
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cancelTime;
    //收货码
    private String orderFinishCode;
    //违约金
    private BigDecimal deductFee;


    public static DadaQueryResult fromJson(String json) {
        return JSONUtil.fromJson(json, DadaQueryResult.class,true);
    }
}
