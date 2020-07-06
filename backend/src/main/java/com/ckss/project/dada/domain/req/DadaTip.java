package com.ckss.project.dada.domain.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 *
 **/
@Data
public class DadaTip extends BaseModel implements Serializable {

    /**
     * 第三方订单编号
     */
    @JSONField(name = "order_id")
    private String orderId;

    /**
     * 小费金额(精确到小数点后一位，单位：元)
     */
    @JSONField(name = "tips")
    private float tips;

    /**
     * 订单城市区号
     */
    @JSONField(name = "city_code")
    private String cityCode;

    /**
     * 备注(字段最大长度：512)
     */
    @JSONField(name = "info")
    private String info;

}
