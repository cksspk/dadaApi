package com.ckss.project.dadafront.controller;

import com.ckss.common.exception.DadaErrorException;
import com.ckss.framework.web.domain.AjaxResult;
import com.ckss.project.dada.api.DadaService;
import com.ckss.project.dada.domain.DadaCallback;
import com.ckss.project.dada.domain.DadaMessage;
import com.ckss.project.dada.domain.TransporterCancel;
import com.ckss.project.dada.utils.JSONUtil;
import com.ckss.project.dadafront.entity.DadaOrderVO;
import com.ckss.project.dadafront.service.DdOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


/**
 *
 * @author: cks
 **/

@RestController
@RequestMapping("dada/callback")
@Slf4j
@RequiredArgsConstructor
public class DadaPortalController {

    private final DadaService dadaService;

    //service 接口
    private final DdOrderService ddOrderService;


    /**
     * 每次订单状态发生变化时，会对添加订单接口中callback的URL进行回调。
     * @param dadaCallback
     * @return
     */
    @PostMapping
    public AjaxResult callback(@RequestBody DadaCallback dadaCallback){
        log.info("\n接收达达请求：[dadaCallback=[{}]",dadaCallback);
        //1. 验证数据来自达达
        if(!dadaService.checkSignature(dadaCallback.getClientId(),dadaCallback.getOrderId(),String.valueOf(dadaCallback.getUpdateTime()),dadaCallback.getSignature())){
           System.out.println("非法请求，可能属于伪造的请求！");
           throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        else{
            System.out.println("验证成功！");
        }
        //订单状态发生变化时，对订单状态进行修改
        System.out.println("修改订单");

        //更新数据组织
        DadaOrderVO dadaOrderVO = new DadaOrderVO();
        dadaOrderVO.setId(dadaCallback.getOrderId());               //订单Id
        dadaOrderVO.setClientId(dadaCallback.getClientId());        //达达运单编号
        dadaOrderVO.setStatusCode(dadaCallback.getOrderStatus());   //订单状态
        if(dadaCallback.getOrderStatus() != 100){//当订单不是创建失败，时间精确到秒，转成毫秒
            dadaCallback.setUpdateTime(dadaCallback.getUpdateTime() * 1000);
        }
        //订单更新时间        毫秒转LocalDateTime
        dadaOrderVO.setUpdateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(dadaCallback.getUpdateTime()), ZoneId.systemDefault()));
        //骑手信息
        dadaOrderVO.setDmId(dadaCallback.getDmId());
        dadaOrderVO.setDmName(dadaCallback.getDmName());
        dadaOrderVO.setDmMobile(dadaCallback.getDmMobile());
        //订单取消
        dadaOrderVO.setCancelFrom(dadaCallback.getCancelFrom());
        dadaOrderVO.setCancelReason(dadaCallback.getCancelReason());

        //更新订单
        ddOrderService.updateById(dadaOrderVO);


        Integer orderStatus = dadaCallback.getOrderStatus();
        switch (orderStatus){
            case 1:
                System.out.println("orderStatus: 待接单 ***:"+orderStatus);
                break;
            case 2:
                System.out.println("orderStatus: 待取货 ***:"+orderStatus);
                break;
            case 3:
                System.out.println("orderStatus: 配送中 ***:"+orderStatus);
                break;
            case 4:
                System.out.println("orderStatus: 已完成 ***:"+orderStatus);
                break;
            case 5:
                System.out.println("orderStatus: 已取消 ***:"+orderStatus);
                break;
            case 7:
                System.out.println("orderStatus: 订单过期 ***:"+orderStatus);
                break;
            case 8:
                System.out.println("orderStatus: 指派单 ***:"+orderStatus);
                break;
            case 9:
                System.out.println("orderStatus: 妥投异常之物品返回中 ***:"+orderStatus);
                break;
            case 10:
                System.out.println("orderStatus: 妥投异常之物品返回完成 ***:"+orderStatus);
                break;
            case 100:
                System.out.println("orderStatus: 骑士到店 ***:"+orderStatus);
                break;
            case 1000:
                System.out.println("orderStatus: 创建达达运单失败 ***:"+orderStatus);
                break;
        }

        return AjaxResult.success();
    }




    /**
     * 消息通知，达达推送消息
     * @return
     */
    @RequestMapping("msg")
    public AjaxResult msg(@RequestBody DadaMessage message){
        log.info("接收达达消息推送");
        System.out.println(message);
        AjaxResult ajax = AjaxResult.success();

        //正确的响应结果（JSON格式）
        ajax.put("status","ok");

        //1. 骑士取消订单情况
        if(message.getMessageType() == 1){
            String messageBody = message.getMessageBody();
            TransporterCancel transporter = JSONUtil.fromJson(messageBody, TransporterCancel.class);
            System.out.println("骑士取消订单："+transporter);

            String orderId = transporter.getOrderId();
            //2. 不同意退单 0:不同意，1:表示同意
            int isConfirm = 0;
            //3. 调用消息确认接口
            try {
                String s = dadaService.msgConfirm(orderId, "", isConfirm);
                System.out.println(s);
            } catch (DadaErrorException e) {
                log.error(e.getMessage());
            }
        }

        return ajax;
    }




}
