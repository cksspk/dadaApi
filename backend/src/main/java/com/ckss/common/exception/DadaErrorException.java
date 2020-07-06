package com.ckss.common.exception;

import com.ckss.project.dada.domain.DadaApiResponse;
import lombok.Data;

/**
 * @author cks
 */
@Data
public class DadaErrorException extends Exception{

    private DadaApiResponse dadaResp;

    public DadaErrorException(DadaApiResponse dadaResp){
        super(dadaResp.getMsg());
        this.dadaResp = dadaResp;
    }
}
