package com.ckss.project.dada.domain.result;

import com.ckss.project.dada.utils.JSONUtil;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 达达查询订单运费接口
 */
@Data
@ToString
public class DadaQueryDeliverFeeResult implements Serializable {

    /**
     * 配送距离(单位：米)
     * 必须：是
     */
    private Double distance;

    /**
     * 平台订单号
     */
    private String deliveryNo;

    /**
     * 实际运费(单位：元)，运费减去优惠券费用
     * 必须：是
     */
    private Double fee;

    /**
     * 运费(单位：元)
     * 必须：是
     */
    private Double deliverFee;

    /**
     * 优惠券费用(单位：元)
     * 必须：否
     */
    private Double couponFee;

    /**
     * 小费(单位：元)
     * 必须：否
     */
    private Double tips;

    /**
     * 保价费(单位：元)
     * 必须：否
     */
    private Double insuranceFee;


    public static DadaQueryDeliverFeeResult fromJson(String json) {
        return JSONUtil.fromJson(json, DadaQueryDeliverFeeResult.class,true);
    }
}
