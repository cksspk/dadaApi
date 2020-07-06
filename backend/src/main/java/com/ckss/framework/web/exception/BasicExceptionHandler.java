package com.ckss.framework.web.exception;

import com.ckss.common.exception.CustomException;
import com.ckss.common.utils.StringUtils;
import com.ckss.framework.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @className: BlException
 * @description: 全局异常处理
 * @author: cksspk
 * @date: 2020/3/24
 **/

@Slf4j
@RestControllerAdvice
public class BasicExceptionHandler {


    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e)
    {
        if (StringUtils.isNull(e.getCode()))
        {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }


    //待修改2020年3月28日
//    @ExceptionHandler(RuntimeException.class)
//    public AjaxResult handleException(RuntimeException e) {
//        log.info("BlException:"+e.getMessage());
//        return AjaxResult.error(e.getMessage());
//    }


}
