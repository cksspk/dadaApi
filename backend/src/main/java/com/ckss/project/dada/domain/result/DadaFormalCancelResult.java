package com.ckss.project.dada.domain.result;

import com.ckss.project.dada.utils.JSONUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author: cksspk
 **/

@Data
@ToString
public class DadaFormalCancelResult {
    /**
     * 扣除的违约金(单位：元)
     */
    @JsonProperty("deduct_fee")
    private Double deductFee;

    public static DadaFormalCancelResult fromJson(String json) {
        return JSONUtil.fromJson(json, DadaFormalCancelResult.class,true);
    }
}
