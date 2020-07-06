package com.ckss.project.dadafront.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ckss.common.exception.DadaErrorException;
import com.ckss.common.utils.IdWorker;
import com.ckss.common.utils.StringUtils;
import com.ckss.framework.web.domain.AjaxResult;
import com.ckss.project.dada.api.DadaService;
import com.ckss.project.dada.config.DadaProperties;
import com.ckss.project.dada.domain.result.DadaQueryResult;
import com.ckss.project.dadafront.entity.DadaOrderVO;
import com.ckss.project.dada.domain.req.DadaAddOrderReq;
import com.ckss.project.dada.domain.result.DadaAddOrderResult;
import com.ckss.project.dadafront.service.DdOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


/**
 * @author cks
 */
@RestController
@RequestMapping("vant/dada")
@AllArgsConstructor
@Slf4j
public class DadaOrderController {

    //配置属性
    private final DadaProperties dadaProperties;


    private final DdOrderService ddOrderService;

    //API 接口
    private final DadaService dadaService;


    private final IdWorker idWorker;

//    @Autowired
//    private DadaOrderSer dadaOrderSer;

    /**
     * 新增订单
     * @param dadaOrder
     * @return
     */
    @PostMapping()
    public AjaxResult add(@RequestBody DadaOrderVO dadaOrder){
        System.out.println("dadaOrder = " + dadaOrder);

        //组织发送给达达订单的request
        DadaAddOrderReq dadaAddOrderReq = dadaOrderTransform(dadaOrder);
        try {
            //1. 发送订单给达达
            DadaAddOrderResult dadaAddOrderResult = dadaService.addOrder(dadaAddOrderReq);
            System.out.println("dadaAddOrderResult = " + dadaAddOrderResult);

            //达达订单返回数据  【实际运费】
            dadaOrder.setFee(dadaAddOrderResult.getFee());
            //初始订单状态设置为待接单
            dadaOrder.setStatusCode(1);

            //保存订单
            boolean save = ddOrderService.save(dadaOrder);
            return save ? AjaxResult.success() : AjaxResult.error();
        } catch (DadaErrorException e) {
            log.error("创建达达订单失败："+e.getDadaResp());
            return  AjaxResult.error();
        }
    }

    @GetMapping("list")
    public AjaxResult list(Page page, DadaOrderVO dadaOrder, String beginTime, String endTime){
        Page data = ddOrderService.page(page, Wrappers.lambdaQuery(dadaOrder)
                .apply(StringUtils.isNotBlank(beginTime),"date_format (create_time,'%Y-%m-%d') >= date_format('" + beginTime + "','%Y-%m-%d')")
                .apply(StringUtils.isNotBlank(endTime),"date_format (create_time,'%Y-%m-%d') <= date_format('" + endTime + "','%Y-%m-%d')")
        );
        return AjaxResult.success(data);
    }



    @DeleteMapping("{ids}")
    public AjaxResult removeById(@PathVariable String ids){
        List list = Arrays.asList(StringUtils.split(ids, ","));
        return AjaxResult.success(ddOrderService.removeByIds(list));
    }

    /**
     * 查询订单详情
     * @param orderId 根据订单id查询订单详情
     * @return
     */
    @GetMapping("detail/{orderId}")
    public AjaxResult orderDesc(@PathVariable String orderId){
        try {
            DadaQueryResult query = dadaService.query(orderId);
            return AjaxResult.success(query);
        } catch (DadaErrorException e) {
            log.error("查询达达订单详情失败",e);
            return AjaxResult.error();
        }
    }


    /**
     * 达达订单组装
     * @param dadaOrder
     * @return
     */
    private DadaAddOrderReq dadaOrderTransform(DadaOrderVO dadaOrder){
        DadaAddOrderReq dadaAddOrderReq = new DadaAddOrderReq();
        dadaAddOrderReq.setShopNo(dadaProperties.getShopNo());
        dadaAddOrderReq.setReceiverLng(BigDecimal.valueOf(dadaOrder.getReceiverLng()));
        dadaAddOrderReq.setReceiverLat(BigDecimal.valueOf(dadaOrder.getReceiverLat()));
        dadaAddOrderReq.setCargoPrice(dadaOrder.getOdPrice());
        //订单编号
        long l = idWorker.nextId();
        dadaOrder.setId(String.valueOf(l));
        dadaAddOrderReq.setOriginId(String.valueOf(l));
        dadaAddOrderReq.setCityCode(dadaProperties.getCityCode());
        dadaAddOrderReq.setIsPrepay(0);
        dadaAddOrderReq.setReceiverName(dadaOrder.getReceiverName());
        dadaAddOrderReq.setReceiverAddress(dadaOrder.getReceiverAddress());
//        dadaAddOrderReq.setCallback("http://cksspk.free.idcfengye.com/dada/callback");
        dadaAddOrderReq.setCallback(dadaProperties.getCallbackUrl());
        dadaAddOrderReq.setReceiverPhone(dadaOrder.getReceiverPhone());    //设置手机号码
        return dadaAddOrderReq;
    }
}
