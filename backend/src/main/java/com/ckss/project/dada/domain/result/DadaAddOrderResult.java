package com.ckss.project.dada.domain.result;

import com.ckss.project.dada.utils.JSONUtil;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 创建达达订单返回对象
 */
@Data
@ToString
public class DadaAddOrderResult  implements Serializable {


    private String orderId;
    /**
     * 配送距离(单位：米)
     * 必须：是
     */
    private Double distance;

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


    public static DadaAddOrderResult fromJson(String json) {
        return JSONUtil.fromJson(json, DadaAddOrderResult.class,true);
    }
}
