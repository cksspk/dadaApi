package com.ckss.project.dada.api;

import com.ckss.common.exception.DadaErrorException;
import com.ckss.project.dada.domain.DadaCallback;
import com.ckss.project.dada.domain.req.DadaAddOrderReq;
import com.ckss.project.dada.domain.req.DadaTip;
import com.ckss.project.dada.domain.result.*;

import java.util.List;

/**
 * 达达 API的service.
 *
 */
public interface DadaService {

    /**
     * 获取城市信息
     * @return 城市信息列表
     * @throws DadaErrorException
     */
    List<DadaCity>  queryCityCode() throws DadaErrorException;


    /**
     * 针对所有达达API中的POST请求.
     * @param requestUrl 请求接口地址
     * @param param      求参数json值
     * @return           接口响应结果json格式 result字符串
     * @throws DadaErrorException
     */
    String post(String requestUrl, String param)throws DadaErrorException;


    /**
     * <pre>
     * 验证消息的确来自达达.
     * 详情请见: http://newopen.imdada.cn/#/development/file/order?_k=w67r9h
     * </pre>
     * @param clientId 达达运单号
     * @param orderId 订单接口中ID
     * @param updateTime 更新时间
     * @param signature 签名
     * @return
     */
    boolean checkSignature(String clientId,String orderId,String updateTime,String signature);


    //------------------达达消息接口
    /**
     * <pre>
     * 骑士取消订单.
     * 详情请见: http://newopen.imdada.cn/#/development/file/transporterCancelOrder?_k=jjm4wt
     * </pre>
     * @param orderId 商家第三方订单号
     * @param dadaOrderId 	达达订单号 非必填参数
     * @param isConfirm 	0:不同意，1:表示同意
     * @return
     */
    String msgConfirm(String orderId, String dadaOrderId, int isConfirm) throws DadaErrorException;




    //------------------达达订单业务接口
    /**
     * <pre>
     * 在达达平台发布订单
     * 详情请见: https://newopen.imdada.cn/#/development/file/add?_k=qbx8ho
     * </pre>
     * @param req 参数数据
     * @return DadaAddOrderResult
     * @throws DadaErrorException
     */
    DadaAddOrderResult addOrder(DadaAddOrderReq req) throws DadaErrorException;


    /**
     * <pre>
     * 在调用新增订单后，订单被取消、投递异常（妥投异常之物品返回完成=10）的情况下，调用此接口，可以在达达平台重新发布订单。
     * 详情请见: https://newopen.imdada.cn/#/development/file/add?_k=qbx8ho
     * </pre>
     * @param req 参数数据
     * @return DadaAddOrderResult
     * @throws DadaErrorException
     */
    DadaAddOrderResult reAddOrder(DadaAddOrderReq req) throws DadaErrorException;


    /**
     * <pre>
     * 查询订单运费接口
     * 详情请见: http://newopen.imdada.cn/#/development/file/readyAdd?_k=14j8ht
     * </pre>
     * @param req 参数数据
     * @return DadaQueryDeliverFeeResult
     * @throws DadaErrorException
     */
    DadaQueryDeliverFeeResult queryDeliverFee(DadaAddOrderReq req) throws DadaErrorException;


    /**
     * <pre>
     * 查询订单运费接口
     * 详情请见: http://newopen.imdada.cn/#/development/file/readyAdd?_k=14j8ht
     * </pre>
     * @param deliveryNo 平台订单编号
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    String addAfterQuery(String deliveryNo) throws DadaErrorException;


    /**
     * <pre>
     * 增加小费接口    TODO 添加小费失败，可能是测试接口不允许添加
     * 详情请见: http://newopen.imdada.cn/#/development/file/addTip?_k=jqrt0t
     * </pre>
     * @param dadaTip 小费
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    String addTip(DadaTip dadaTip) throws DadaErrorException;


    /**
     * <pre>
     * 订单详情查询接口
     * 详情请见: http://newopen.imdada.cn/#/development/file/statusQuery?_k=ogem1n
     * </pre>
     * @param orderId 	第三方订单编号
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    DadaQueryResult query(String orderId) throws DadaErrorException;


    /**
     * <pre>
     * 订单取消订单接口
     *
     * 在订单待接单或待取货情况下，调用此接口可取消订单。
     * 取消费用说明：接单1 分钟以内取消不扣违约金；
     * 接单后1－15分钟内取消订单，运费退回。同时扣除2元作为给配送员的违约金；
     * 配送员接单后15 分钟未到店，商户取消不扣违约金；
     * 系统取消订单说明：超过72小时未接单系统自动取消。每天凌晨2点，取消大于72小时未完成的订单。
     *
     * 详情请见: http://newopen.imdada.cn/#/development/file/formalCancel?_k=kdvflh
     * </pre>
     * @param orderId 	第三方订单编号
     * @param cancelReasonId 	取消原因ID
     * 1	没有配送员接单
     * 2	配送员没来取货
     * 3	配送员态度太差
     * 4	顾客取消订单
     * 5	订单填写错误
     * 34	配送员让我取消此单
     * 35	配送员不愿上门取货
     * 36	我不需要配送了
     * 37	配送员以各种理由表示无法完成订单
     * 1000	其他
     *
     * @param cancelReason 	取消原因(当取消原因ID为其他时，此字段必填)
     * @return 返回 扣除的违约金(单位：元)
     * @throws DadaErrorException
     */
    DadaFormalCancelResult formalCancel(String orderId, String cancelReasonId, String cancelReason) throws DadaErrorException;


    /**
     * <pre>
     * 追加订单接口
     * 商户调用该接口将已发布的订单追加给指定的配送员
     * 1. 追加的订单必须是该门店发出的处于待接单状态的订单
     * 2. 只能从符合条件的配送员列表选取配送员进行追加,参考查询追加配送员
     * 详情请见: http://newopen.imdada.cn/#/development/file/appointOrder?_k=17o9ob
     * </pre>
     * @param orderId 	第三方订单编号
     * @param transporterId 	追加的配送员ID
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    String appointExist(String orderId, Integer transporterId) throws DadaErrorException;


    /**
     * <pre>
     * 取消追加订单接口
     * 被取消的追加订单，状态变为待接单，其他配送员可见   TODO 测试取消追加订单失败
     * 详情请见: http://newopen.imdada.cn/#/development/file/appointOrderCancel?_k=bagkad
     * </pre>
     * @param orderId 	第三方订单编号
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    String cancelAppointExist(String orderId) throws DadaErrorException;


    /**
     * <pre>
     * 查询追加配送员
     * 商户调用该接口查询可追加订单的配送员列表
     * 详情请见: http://newopen.imdada.cn/#/development/file/listTransportersToAppoint?_k=tmmuux
     * </pre>
     * @return List<DataTransporterResult>
     * @throws DadaErrorException
     */
    List<DataTransporterResult> transporter() throws DadaErrorException;


    /**
     * <pre>
     * 商家投诉达达
     * 达达配送员接单后，商家如果对达达服务不满意，均可以使用该接口对达达进行投诉。
     * 详情请见: http://newopen.imdada.cn/#/development/file/complaintDada?_k=rwnrll
     * </pre>
     * @param orderId 	第三方订单编号
     * @param reasonId 	投诉原因ID
     * 1	达达态度恶劣
     * 2	接单后未取货
     * 3	取货速度太慢
     * 4	送货速度太慢
     * 5	货品未送达
     * 6	货品损坏
     * 7	违规收取顾客小费
     * 11	达达衣冠不整
     * 69	达达恶意取消订单
     * 71	达达提前点击取货/送达
     * 214	达达无标准保温箱
     * 251	无法联系上骑士
     * 40002	没有冰袋
     * 40004	虚假发起妥投失败
     * 50209	骑士肇事逃逸
     * 50210	骑士偷窃物品
     * 50244	骑士拒绝取货/配送
     * 50245	骑士私自取消订单
     * 50246	骑士骚扰/殴打
     *
     *
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    String complaintDada(String orderId, String reasonId) throws DadaErrorException;


    /**
     * <pre>
     * 妥投异常之物品返回完成
     * 订单妥投异常后，订单状态变为9，骑士将物品进行返还，如果商家确认收到物品后，可以使用该 接口进行确认，订单状态变成10，同时订单终结。
     * 详情请见: http://newopen.imdada.cn/#/development/file/abnormalConfirm?_k=i31dp4
     * </pre>
     * @param orderId 	第三方订单编号
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    String confirmGoods(String orderId) throws DadaErrorException;

    /**
     * <pre>
     * 接受订单(仅在测试环境供调试使用)
     * 在测试环境中，可调用此接口接受订单，检测回调。
     * 详情请见: http://newopen.imdada.cn/#/development/file/accept?_k=9dn2ha
     * </pre>
     * @param orderId 第三方订单编号
     * @return 接口响应字符串
     */
    String accept(String orderId) throws DadaErrorException;

    /**
     * <pre>
     * 完成取货(仅在测试环境供调试使用)
     * 在测试环境中，可调用此接口完成取货，检测回调。
     * 详情请见: http://newopen.imdada.cn/#/development/file/fetch?_k=34bx4q
     * </pre>
     * @param orderId 第三方订单编号
     * @return 接口响应字符串
     */
    String fetch(String orderId) throws DadaErrorException;

    /**
     * <pre>
     * 完成订单(仅在测试环境供调试使用)
     * 在测试环境中，可调用此接口完成订单，检测回调。
     * 详情请见: http://newopen.imdada.cn/#/development/file/finish?_k=364vc8
     * </pre>
     * @param orderId 第三方订单编号
     * @return 接口响应字符串
     */
    String finish(String orderId) throws DadaErrorException;

    /**
     * <pre>
     * 取消订单(仅在测试环境供调试使用)
     * 在测试环境中，可调用此接口取消订单，检测回调。
     * 详情请见: http://newopen.imdada.cn/#/development/file/cancel?_k=3jjms3
     * </pre>
     * @param orderId 第三方订单编号
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    String cancel(String orderId) throws DadaErrorException;


    /**
     * <pre>
     * 异常妥投物品返还中(仅在测试环境供调试使用)
     * 在测试环境中，可调用此接口模拟订单妥投异常物品返还中，检测回调。
     * 详情请见: http://newopen.imdada.cn/#/development/file/abnormalReturning?_k=6mzr6n
     * </pre>
     * @param orderId 第三方订单编号
     * @return 接口响应字符串
     * @throws DadaErrorException
     */
    String back(String orderId) throws DadaErrorException;


}
