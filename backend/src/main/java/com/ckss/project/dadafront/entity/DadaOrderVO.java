package com.ckss.project.dadafront.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ckss.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 达达订单
 * @author cks
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OrderVO对象", description = "达达订单")
@TableName("dada_order")
public class DadaOrderVO implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "达达运单号")
    private String clientId;

    @ApiModelProperty(value = "订单价格")
    private BigDecimal odPrice;

    @ApiModelProperty(value = "收货人名字")
    private String receiverName;

    @ApiModelProperty(value = "收货地址经度")
    private String receiverAddress;


    @ApiModelProperty(value = "收货地址经度")
    private Double receiverLng;

    @ApiModelProperty(value = "收货地址纬度")
    private Double receiverLat;


    @ApiModelProperty(value = "收货人号码")
    private String receiverPhone;

    /**
     *订单状态(待接单＝1,待取货＝2,配送中＝3,已完成＝4,已取消＝5, 指派单=8,妥投异常之物品返回中=9, 妥投异常之物品返回完成=10, 骑士到店=100,创建达达运单失败=1000
     */
    @ApiModelProperty(value = "达达订单状态")
    private Integer statusCode;

    /**
     * 实际运费(单位：元)，运费减去优惠券费用
     * 必须：是
     */
    @ApiModelProperty(value = "达达订单实际运费")
    private Double fee;

    //订单回调数据
    /**
     * 达达配送员id，接单以后会传
     */
    private Integer dmId;
    /**
     * 配送员姓名，接单以后会传
     */
    private String dmName;

    /**
     * 配送员手机号，接单以后会传
     */
    private String dmMobile;

    /**
     * 订单取消原因来源(1:达达配送员取消；2:商家主动取消；3:系统或客服取消；0:默认值)
     */
    private Integer cancelFrom;

    /**
     * 订单取消原因,其他状态下默认值为空字符串
     */
    private String cancelReason;



    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

//    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    //逻辑删除字段
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Integer deleted;


}
