package com.ckss.project.dada.api;

import com.ckss.common.exception.DadaErrorException;
import com.ckss.project.dada.config.DadaProperties;
import com.ckss.project.dada.config.UrlConstant;
import com.ckss.project.dada.domain.DadaApiResponse;
import com.ckss.project.dada.domain.req.DadaAddOrderReq;
import com.ckss.project.dada.domain.req.DadaTip;
import com.ckss.project.dada.domain.result.*;
import com.ckss.project.dada.utils.HttpClientUtil;
import com.ckss.project.dada.utils.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 达达 API的service
 *
 * @author cks
 */
@Service
@Slf4j
public class DadaServiceImpl implements DadaService{

    @Autowired
    private DadaProperties appConfig;

    //------------------达达消息接口实现
    @Override
    public String msgConfirm(String orderId, String dadaOrderId, int isConfirm) throws DadaErrorException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("isConfirm",isConfirm);
        String messageBody = JSONUtil.toJson(map);

        HashMap<String, Object> msgMap = new HashMap<>();
        msgMap.put("messageType",1);
        msgMap.put("messageBody",messageBody);
        String param = JSONUtil.toJson(msgMap);

        String responseContent = this.post(UrlConstant.MESSAGE_CONFIRM_URL, param);
        return responseContent;
    }

    //------------------达达订单业务接口实现

    @Override
    public DadaAddOrderResult addOrder(DadaAddOrderReq req) throws DadaErrorException {
        String responseContent = this.post(UrlConstant.ORDER_ADD_URL, req.toJson());
        return DadaAddOrderResult.fromJson(responseContent);
    }

    @Override
    public DadaAddOrderResult reAddOrder(DadaAddOrderReq req) throws DadaErrorException {
        String responseContent = this.post(UrlConstant.ORDER_RE_ADD_URL, req.toJson());
        return DadaAddOrderResult.fromJson(responseContent);
    }

    @Override
    public DadaQueryDeliverFeeResult queryDeliverFee(DadaAddOrderReq req) throws DadaErrorException {
        String responseContent = this.post(UrlConstant.ORDER_QUERY_DELIVE_RFEE_URL, req.toJson());
        return DadaQueryDeliverFeeResult.fromJson(responseContent);
    }

    @Override
    public String addAfterQuery(String deliveryNo) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("deliveryNo", deliveryNo);
        String param = JSONUtil.toJson(map);
        String responseContent = this.post(UrlConstant.ORDER_ADD_AFTER_QUERY_URL, param);
        return responseContent;
    }

    @Override
    public String addTip(DadaTip dadaTip) throws DadaErrorException {
        String responseContent = this.post(UrlConstant.ORDER_ADD_TIP_URL, dadaTip.toJson());
        return responseContent;
    }

    @Override
    public DadaQueryResult query(String orderId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        String param = JSONUtil.toJson(map);
        String responseContent = this.post(UrlConstant.ORDER_QUERY_URL,param);
        return DadaQueryResult.fromJson(responseContent);
    }

    @Override
    public DadaFormalCancelResult formalCancel(String orderId, String cancelReasonId, String cancelReason) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        map.put("cancel_reason_id", cancelReasonId);
        map.put("cancel_reason", cancelReason);
        String param = JSONUtil.toJson(map);
        String responseContent = this.post(UrlConstant.ORDER_FORMAL_CANCEL_URL, param);
        return DadaFormalCancelResult.fromJson(responseContent);
    }

    @Override
    public String appointExist(String orderId, Integer transporterId) throws DadaErrorException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("order_id", orderId);
        map.put("transporter_id", transporterId);
        //配置文件中的商户sourceId Id
        map.put("shop_no", appConfig.getSourceId());
        String param = JSONUtil.toJson(map);
        String responseContent = this.post(UrlConstant.ORDER_APPOINT_EXIST_URL, param);
        return responseContent;
    }

    @Override
    public String cancelAppointExist(String orderId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        String param = JSONUtil.toJson(map);
        String responseContent = this.post(UrlConstant.ORDER_CANCEL_APPOINT_URL, param);
        return responseContent;
    }

    @Override
    public List<DataTransporterResult> transporter() throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        //配置文件中的商户编码 sourceId Id
        map.put("shop_no", appConfig.getSourceId());
        String param = JSONUtil.toJson(map);
        String responseContent = this.post(UrlConstant.LIST_TRANSPORTER_URL, param);
        return DataTransporterResult.fromJson(responseContent);
    }

    @Override
    public String complaintDada(String orderId, String reasonId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        //配置文件中的商户编码 sourceId Id
        map.put("reason_id", reasonId);
        String param = JSONUtil.toJson(map);
        String responseContent = this.post(UrlConstant.COMPLAINT_DADA_URL, param);
        return responseContent;
    }

    @Override
    public String confirmGoods(String orderId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        String param = JSONUtil.toJson(map);
        String responseContent = this.post(UrlConstant.CONFIRM_GOODS_URL, param);
        return responseContent;
    }


    @Override
    public String accept(String orderId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        String param = JSONUtil.toJson(map);
        return this.post(UrlConstant.ORDER_ACCEPT_URL, param);
    }

    @Override
    public String fetch(String orderId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        String param = JSONUtil.toJson(map);
        return this.post(UrlConstant.ORDER_FETCH_URL, param);
    }

    @Override
    public String finish(String orderId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        String param = JSONUtil.toJson(map);
        return this.post(UrlConstant.ORDER_FINISH_URL, param);
    }

    @Override
    public String cancel(String orderId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        String param = JSONUtil.toJson(map);
        return this.post(UrlConstant.ORDER_CANCEL_URL, param);
    }

    @Override
    public String back(String orderId) throws DadaErrorException {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        String param = JSONUtil.toJson(map);
        return this.post(UrlConstant.ORDER_BACK_URL, param);
    }

    //---------------订单业务




    @Override
    public List<DadaCity> queryCityCode() throws DadaErrorException{
        //请求城市信息
        String responseContent = post(UrlConstant.CITY_CODE_URL, "");
        return DadaCity.fromJson(responseContent);
    }





    /**
     * psot请求达达
     * @param requestUrl API 接口地址
     * @param param      业务参数
     * @return
     */
    public String post(String requestUrl, String param)throws DadaErrorException {
        String requestParams = this.getRequestParams(param);
        requestUrl = appConfig.getHost().concat(requestUrl);
        String resp = HttpClientUtil.postRequest(requestUrl, requestParams);
//      System.out.println("resp:"+resp);
        DadaApiResponse dadaResp = JSONUtil.fromJson(resp, DadaApiResponse.class);
        //接口返回码异常
        if(dadaResp.getCode() != 0){
            throw new DadaErrorException(dadaResp);
        }
        return resp;
    }





    @Override
    public boolean checkSignature(String clientId,String orderId,String updateTime,String signature) {
//        //第一步：将参与签名的字段的值进行升序排列
//        //第二步：将排序过后的参数，进行字符串拼接
//        //第三步：对第二步连接的字符串进行md5加密
        if(StringUtils.isBlank(signature)){
            throw new IllegalArgumentException("非法请求参数，签名为空 : " + signature);
        }
        return signature.equals(gen(clientId,orderId,updateTime));
    }

    private String gen(String... arr){
        if (StringUtils.isAnyEmpty(arr)) {
            throw new IllegalArgumentException("非法请求参数，有部分参数为空 : " + Arrays.toString(arr));
        }else{
            Arrays.sort(arr);
            String toSign = arr[0] + arr[1] + arr[2];
            return encrypt(toSign);
        }
    }

    /**
     * 组织发送参数
     * @param param
     * @return
     */
    private String getRequestParams(String param) {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("source_id", this.appConfig.getSourceId());
        requestParams.put("app_key", this.appConfig.getAppKey());
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        requestParams.put("format", this.appConfig.getFormat());
//        requestParams.put("format", AppConstant.FORMAT);
        requestParams.put("v", this.appConfig.getV());
        requestParams.put("body", param);
        requestParams.put("signature", this.getSign(requestParams));
        return JSONUtil.toJson(requestParams);
    }


    /**
     * 生成签名
     * @param requestParams
     * @return
     */
    private String getSign(Map<String, String> requestParams) {
        //请求参数键值升序排序
        Map<String, String> sortedParams = new TreeMap<String, String>(requestParams);
        Set<Map.Entry<String, String>> entrySets = sortedParams.entrySet();

        //拼参数字符串。
        StringBuilder signStr = new StringBuilder();
        for (Map.Entry<String, String> entry : entrySets) {
            signStr.append(entry.getKey()).append(entry.getValue());
        }

        //MD5签名并校验
        String toSign = this.appConfig.getAppSecret() + signStr.toString() + this.appConfig.getAppSecret();
        String sign = encrypt(toSign);
        return sign.toUpperCase();
    }

    /**
     * 加密
     * @param inbuf
     * @return
     */
    private String encrypt(String inbuf) {
        String s = null;
        char[] hexDigits = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(inbuf.getBytes("UTF-8"));
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
