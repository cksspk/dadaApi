package com.ckss.framework.config;

import com.ckss.common.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cks
 */
@Configuration
public class IdWorkerConfig {
//
//    @Bean
//    public IdWorker idWorker(IdWorkerProperties prop) {
//        return new IdWorker(prop.getWorkerId(), prop.getDataCenterId());
//    }
    @Bean
    public IdWorker idWorker() {
        //手动配置，也可通过配置文件注入
        return new IdWorker(1,1);
    }
}
