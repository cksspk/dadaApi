package com.ckss.project.dada.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Dada 配置类
 *
 */

@Data
@ConfigurationProperties(prefix = "dada.config")
@Component
public class DadaProperties {

    private String appKey;

    private String appSecret;

    private String host;

    private String sourceId;

    private String format;

    private String v;

    private String cityCode;

    private String callbackUrl;

    private String shopNo;



    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getHost() {
        return host;
    }

    public String getSourceId() {
        return sourceId;
    }
}
