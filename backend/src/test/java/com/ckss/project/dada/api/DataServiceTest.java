package com.ckss.project.dada.api;


import com.ckss.common.exception.DadaErrorException;
import com.ckss.project.dada.domain.req.DadaAddOrderReq;
import com.ckss.project.dada.domain.req.DadaTip;
import com.ckss.project.dada.domain.result.*;
import com.ckss.project.dada.utils.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DataServiceTest {

    @Autowired
    DadaService dadaService;


    /**
     * 测试城市信息接口
     */
    @Test
    public void cityTest(){
        List<DadaCity> dadaCities = null;
        try {
            dadaCities = dadaService.queryCityCode();
            dadaCities.forEach(s-> System.out.println(s));
        } catch (DadaErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 1. 测试发布订单/订单重发
     */
    @Test
    public void testAddOrder(){
        DadaAddOrderReq dadaAddOrderReq = new DadaAddOrderReq();
        dadaAddOrderReq.setShopNo("11047059");  //门店编号 测试编号
        String orderId = String.valueOf(System.currentTimeMillis());
        //设置订单编号为之前的订单即可重新发布订单
//        orderId = "1592055614678";

        System.out.println("订单编号:"+orderId);
        dadaAddOrderReq.setOriginId(orderId);   //订单ID 时间戳
        dadaAddOrderReq.setCityCode("021");    //城市ID 杭州 0571  默认
        dadaAddOrderReq.setCargoPrice(BigDecimal.valueOf(111)); //订单金额
        dadaAddOrderReq.setIsPrepay(0);         //垫付金额, 否
        // 填写收货人信息
        dadaAddOrderReq.setReceiverName("测试收获人1号");
        dadaAddOrderReq.setReceiverAddress("测试地址1号");
        //建国北路 120.181278,30.264708                 原版  11.11111228623  121.587172
        dadaAddOrderReq.setReceiverLng(BigDecimal.valueOf(121.587172));
        dadaAddOrderReq.setReceiverLat(BigDecimal.valueOf(11.11111228623));
//        dadaAddOrderReq.setReceiverLat(BigDecimal.valueOf(30.264708));
        // 设置回调url, 订单状态每次变更就会往该url发送通知(参见回调接口)
        dadaAddOrderReq.setCallback("http://cksspk.free.idcfengye.com/dada/callback");
        dadaAddOrderReq.setReceiverPhone("13285891995");    //设置手机号码

        try {
            //1. 新增订单
            DadaAddOrderResult dadaAddOrderResult = dadaService.addOrder(dadaAddOrderReq);
            //2. 测试订单重发
//            DadaAddOrderResult dadaAddOrderResult = dadaService.reAddOrder(dadaAddOrderReq);
            System.out.println(dadaAddOrderResult);
        } catch (DadaErrorException e) {
//            e.printStackTrace();
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }


    //3. 订单预发布测试  使用【查询订单运费接口】获取平台订单编号
    @Test
    public void testQueryDeliverFee(){
        DadaAddOrderReq dadaAddOrderReq = new DadaAddOrderReq();
        dadaAddOrderReq.setShopNo("11047059");  //门店编号 测试编号
        String orderId = String.valueOf(System.currentTimeMillis());
        System.out.println("预发布订单编号:"+orderId);
        dadaAddOrderReq.setOriginId(orderId);   //订单ID 时间戳
        dadaAddOrderReq.setCityCode("0571");    //城市ID 杭州 0571  默认
        dadaAddOrderReq.setCargoPrice(BigDecimal.valueOf(111)); //订单金额
        dadaAddOrderReq.setIsPrepay(0);         //垫付金额, 否
        // 填写收货人信息
        dadaAddOrderReq.setReceiverName("测试收获人1号");
        dadaAddOrderReq.setReceiverAddress("测试地址1号");
        //建国北路 120.181278,30.264708                 原版  11.11111228623  121.587172
        dadaAddOrderReq.setReceiverLng(BigDecimal.valueOf(121.587173));
        dadaAddOrderReq.setReceiverLat(BigDecimal.valueOf(31.228624));
//        dadaAddOrderReq.setReceiverLat(BigDecimal.valueOf(30.264708));
        // 设置回调url, 订单状态每次变更就会往该url发送通知(参见回调接口)
        dadaAddOrderReq.setCallback("http://cksspk.free.idcfengye.com/dada/callback");
        dadaAddOrderReq.setReceiverPhone("13285891995");    //设置手机号码

        try {
            DadaQueryDeliverFeeResult resp = dadaService.queryDeliverFee(dadaAddOrderReq);
            System.out.println("订单预发布测试："+resp);
        } catch (DadaErrorException e) {
            System.out.println(JSONUtil.toJson(e.getDadaResp()));
        }
    }


    //4 测试添加小费
    @Test
    public void testAddTip(){
        try {
            DadaTip dadaTip = new DadaTip();
            dadaTip.setInfo("测试小费");
            dadaTip.setCityCode("021");
            dadaTip.setTips(100f);
            dadaTip.setOrderId("1592036363576");

            String resp = dadaService.addTip(dadaTip);
            System.out.println("测试添加小费："+resp);
            //编号1592028562824
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }


    /**
     * 6。 测试订单详情查询接口.
     */
    @Test
    public void testQuery(){
        try {
            String orderId = "1592055614678";
            DadaQueryResult accept = dadaService.query(orderId);
            System.out.println("测试接收订单返回："+accept);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }

    /**
     * 7. 测试取消订单接口.
     */
    @Test
    public void testFormalCancel(){
        try {
            String orderId = "1592050447960";
            DadaFormalCancelResult rsp = dadaService.formalCancel(orderId,"1","");
            System.out.println("测试取消订单返回："+rsp);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }

    /**
     * 8. 追加订单接口测试.
     */
    @Test
    public void testAppointExist(){
        try {
            String orderId = "1592050447960";
            String rsp = dadaService.appointExist(orderId,666);
            System.out.println("追加订单接口："+rsp);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }


    /**
     * 9. 取消追加订单接口测试.
     */
    @Test
    public void testCancelAppoint(){
        try {
            String orderId = "1592050447960";
            String rsp = dadaService.cancelAppointExist(orderId);
            System.out.println("取消追加订单接口："+rsp);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }

    /**
     * 10. 查询追加配送员.
     */
    @Test
    public void testTransporter(){
        try {
            List<DataTransporterResult> rsp = dadaService.transporter();
            System.out.println("查询追加配送员："+rsp);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }

    /**
     * 11. 商家投诉达达
     */
    @Test
    public void testComplaint(){
        try {
            String orderId = "1592050447960";
            String reasonId = "2";
            String rsp = dadaService.complaintDada(orderId, reasonId);
            System.out.println("商家投诉达达："+rsp);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }


    /**
     * 12. 妥投异常之物品返回完成
     */
    @Test
    public void testConfirmGoods(){
        try {
            String orderId = "1592050447960";
            String rsp = dadaService.confirmGoods(orderId);
            System.out.println("妥投异常之物品返回完成："+rsp);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }











    /**
     *
     * 5
     * 测试 订单回调
     * 1. 接收订单
     * (仅在测试环境供调试使用)，在测试环境中，可调用此接口接受订单，检测回调。
     * 接口仅限于测试环境调试使用，且触发回调URL成功后，接口直接返回成功；否则，重复三次触发，每次间隔3秒，最后返回成功。
     */
    @Test
    public void testAccept(){
        try {
            String orderId = "1592069871999";
            String accept = dadaService.accept(orderId);
            System.out.println("测试接收订单返回："+accept);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }
    /**
     * 测试 订单回调
     * 2. 完成取货
     * (仅在测试环境供调试使用)在测试环境中，可调用此接口接受订单，检测回调。
     * 接口仅限于测试环境调试使用，且触发回调URL成功后，接口直接返回成功；否则，重复三次触发，每次间隔3秒，最后返回成功。
     */
    @Test
    public void testFetch(){
        try {
            String orderId = "1592055614678";
            String accept = dadaService.fetch(orderId);
            System.out.println("测试完成取货返回："+accept);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }
    /**
     * 测试 订单回调
     * 3. 完成订单
     * (仅在测试环境供调试使用)，在测试环境中，可调用此接口接受订单，检测回调。
     * 接口仅限于测试环境调试使用，且触发回调URL成功后，接口直接返回成功；否则，重复三次触发，每次间隔3秒，最后返回成功。
     */
    @Test
    public void testFinish(){
        try {
            String orderId = "1592036363576";
            String accept = dadaService.finish(orderId);
            System.out.println("测试完成订单返回："+accept);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }
    /**
     * 测试 订单回调
     * 4. 取消订单
     * (仅在测试环境供调试使用)，在测试环境中，可调用此接口接受订单，检测回调。
     * 接口仅限于测试环境调试使用，且触发回调URL成功后，接口直接返回成功；否则，重复三次触发，每次间隔3秒，最后返回成功。
     */
    @Test
    public void testCancel(){
        try {
            String orderId = "1592055614678";
            String accept = dadaService.cancel(orderId);
            System.out.println("测试取消订单返回："+accept);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }
    /**
     * 测试 订单回调
     * 5. 异常妥投物品返还中
     * (仅在测试环境供调试使用)，在测试环境中，可调用此接口接受订单，检测回调。
     * 接口仅限于测试环境调试使用，且触发回调URL成功后，接口直接返回成功；否则，重复三次触发，每次间隔3秒，最后返回成功。
     */
    @Test
    public void testBack(){
        try {
            String orderId = "1592055614678";
            String accept = dadaService.back(orderId);
            System.out.println("测试异常妥投物品返还中："+accept);
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
        }
    }




    @Test
    public void testAddAfterQuery(){
        String deliveryNo = "Dada85ecc3a889ae40e2a8880c747040aa1c";
        try {
            String resp = dadaService.addAfterQuery(deliveryNo);
            System.out.println("查询运费后发单接口："+resp);
            //编号1592028562824
        } catch (DadaErrorException e) {
            log.error(JSONUtil.toJson(e.getDadaResp()));
//            System.out.println(JSONUtil.toJson(e.getDadaResp()));
        }
    }




}