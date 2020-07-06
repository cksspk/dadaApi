/*
 *  Copyright (c) 2015.  meicanyun.com Corporation Limited.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Yage Company. ("Confidential Information").  You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with meicanyun.com.
 */

package com.ckss.project.dada.domain.req;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 达达创建订单对象
 */
@Data
public class DadaAddOrderReq extends BaseModel{

    /**
     * 门店编号，门店创建后可在门店列表和单页查看  必传：是
     */
    @JSONField(name = "shop_no")
    private String shopNo;

    /**
     * 第三方对接平台订单id      必传：是
     */
    @JSONField(name = "origin_id")
    private String originId;

    /**
     * 订单所在城市的code  必传：是
     */
    @JSONField(name = "city_code")
    private String cityCode;

    /**
     * 订单金额     必传：是
     */
    @JSONField(name = "cargo_price")
    private BigDecimal cargoPrice;

    /**
     * 是否需要垫付 1:是 0:否 (垫付订单金额，非运费)  必传：是
     */
    @JSONField(name = "is_prepay")
    private Integer isPrepay;

    /**
     * 收货人姓名  必传：是
     */
    @JSONField(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人地址  必传：是
     */
    @JSONField(name = "receiver_address")
    private String receiverAddress;

    /**
     * 收货人地址纬度（高德坐标系，若是其他地图经纬度需要转化成高德地图经纬度，高德地图坐标拾取器）     必传：是
     */
    @JSONField(name = "receiver_lat")
    private BigDecimal receiverLat;

    /**
     * 收货人地址经度（高德坐标系，若是其他地图经纬度需要转化成高德地图经纬度，高德地图坐标拾取器)     必传：是
     */
    @JSONField(name = "receiver_lng")
    private BigDecimal receiverLng;

    /**
     * 	回调URL    必传：是
     */
    private String	callback;

    /**
     * 	收货人手机号（手机号和座机号必填一项）    必传：否
     */
    @JSONField(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 	收货人座机号（手机号和座机号必填一项）   必传：否
     */
    private String	receiver_tel;

    /**
     * 小费（单位：元，精确小数点后一位）     必传：是
     */
    private Double tips;

    /**
     * 	订单备注   必传：否
     */
    private String info;

    /**
     * 	订单商品类型：食品小吃-1,饮料-2,鲜花-3,文印票务-8,便利店-9,水果生鲜-13,同城电商-19, 医药-20,蛋糕-21,酒品-24,小商品市场-25,服装-26,汽修零配-27,数码-28,小龙虾-29,火锅-51,其他-5
     * 	必传：否
     */
    private Integer cargo_type;

    /**
     * 	订单重量（单位：Kg）   必传：是
     */
    private Double cargo_weight;

    /**
     * 订单商品数量   必传：否
     */
    private Integer cargo_num;

    /**
     * 	发票抬头   必传：否
     */
    private String invoice_title;

    /**
     * 	订单来源标示（只支持字母，最大长度为10）   必传：否
     */
    private String origin_mark;

    /**
     * 订单来源编号，最大长度为30，该字段可以显示在骑士APP订单详情页面，示例：
     * origin_mark_no:"#京东到家#1"
     * 达达骑士APP看到的是：#京东到家#1
     *
     * 必传：否
     */
    private String origin_mark_no;

    /**
     * 是否使用保价费（0：不使用保价，1：使用保价； 同时，请确保填写了订单金额（cargo_price））
     * 商品保价费(当商品出现损坏，可获取一定金额的赔付)
     * 保费=配送物品实际价值*费率（5‰），配送物品价值及最高赔付不超过10000元， 最高保费为50元（物品价格最小单位为100元，不足100元部分按100元认定，保价费向上取整数， 如：物品声明价值为201元，保价费为300元*5‰=1.5元，取整数为2元。）
     * 若您选择不保价，若物品出现丢失或损毁，最高可获得平台30元优惠券。 （优惠券直接存入用户账户中）。
     *
     * 必传：否
     */
    private Integer is_use_insurance;

    /**
     * 收货码（0：不需要；1：需要。收货码的作用是：骑手必须输入收货码才能完成订单妥投）
     *
     * 必传：否
     */
    private Integer is_finish_code_needed;

    /**
     * 预约发单时间（预约时间unix时间戳(10位),精确到分;整分钟为间隔，并且需要至少提前5分钟预约，可以支持未来3天内的订单发预约单。）
     *
     * 必传：否
     */
    private Integer delay_publish_time;

    /**
     * 	是否选择直拿直送（0：不需要；1：需要。选择直拿直送后，同一时间骑士只能配送此订单至完成，同时，也会相应的增加配送费用）
     *
     * 必传：否
     */
    private Integer is_direct_delivery;

    /**
     * 订单商品明细
     *
     * 必传：否
     */
    private Object product_list;

    /**
     * 货架信息,该字段可在骑士APP订单备注中展示
     *
     * 必传：否
     */
    private String pick_up_pos;

}
