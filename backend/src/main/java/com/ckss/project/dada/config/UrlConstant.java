package com.ckss.project.dada.config;

/**
 * 达达 API 地址
 * @author: cks
 */
public class UrlConstant {

    /**
     * 新增订单
     */
    public static String ORDER_ADD_URL = "/api/order/addOrder";

    /**
     * 重新发布订单
     */
    public static String ORDER_RE_ADD_URL = "/api/order/reAddOrder";

    /**
     * 订单预发布 a.查询订单运费接口
     */
    public static String ORDER_QUERY_DELIVE_RFEE_URL = "/api/order/queryDeliverFee";

    /**
     * 订单预发布 b.查询运费后发单接口
     */
    public static String ORDER_ADD_AFTER_QUERY_URL = "/api/order/addAfterQuery";

    /**
     * 增加小费
     */
    public static String ORDER_ADD_TIP_URL = "/api/order/addTip";

    /**
     * 订单详情查询
     */
    public static String ORDER_QUERY_URL = "/api/order/status/query";

    /**
     * 取消订单
     */
    public static String ORDER_FORMAL_CANCEL_URL = "/api/order/formalCancel";

    /**
     * 追加订单
     */
    public static String ORDER_APPOINT_EXIST_URL = "/api/order/appoint/exist";

    /**
     * 取消追加订单
     */
    public static String ORDER_CANCEL_APPOINT_URL = "/api/order/appoint/cancel";

    /**
     * 查询追加配送员
     */
    public static String LIST_TRANSPORTER_URL = "/api/order/appoint/list/transporter";

    /**
     * 商家投诉达达
     */
    public static String COMPLAINT_DADA_URL = "/api/complaint/dada";

    /**
     * 妥投异常之物品返回完成
     */
    public static String CONFIRM_GOODS_URL = "/api/order/confirm/goods";






    /**
     * 接受订单(仅在测试环境供调试使用)
     */
    public static String ORDER_ACCEPT_URL = "/api/order/accept";
    /**
     * 完成取货(仅在测试环境供调试使用)
     */
    public static String ORDER_FETCH_URL = "/api/order/fetch";
    /**
     * 完成订单(仅在测试环境供调试使用)
     */
    public static String ORDER_FINISH_URL = "/api/order/finish";
    /**
     * 取消订单(仅在测试环境供调试使用)
     */
    public static String ORDER_CANCEL_URL = "/api/order/cancel";
    /**
     * 异常妥投物品返还中(仅在测试环境供调试使用)
     */
    public static String ORDER_BACK_URL = "/api/order/delivery/abnormal/back";



    //消息通知部分
    /**
     * 消息确认
     */
    public static String MESSAGE_CONFIRM_URL = "/api/message/confirm";




//    public static String SHOP_ADD_URL = "/api/shop/add";
//
//    public static String MERCHANT_ADD_URL = "/merchantApi/merchant/add";

    public static String CITY_CODE_URL = "/api/cityCode/list";
}
