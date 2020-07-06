package com.ckss.project.dada.domain;

import lombok.Data;
import lombok.ToString;

/**
 * 达达消息通知
 * @author: cksspk
 * @date: 2020/6/13
 **/

@Data
@ToString
public class DadaMessage {

    private Integer messageType;
    private String messageBody;
    private Integer createTime;
}
