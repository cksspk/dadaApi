package com.ckss.project.dada.domain;

import lombok.Data;

/**
 *
 * 骑士取消订单
 * @author: cksspk
 * @date: 2020/6/13
 **/
@Data
public class TransporterCancel {
    private String orderId;
    private String dadaOrderId;
    private String cancelReason;
}
