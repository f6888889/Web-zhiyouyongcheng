package com.zhiyou.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String dataPath = "/app/data/";

    public AppProperties() {
        log.info("========== AppProperties 初始化 ==========");
        log.info("dataPath 配置值: [{}]", dataPath);
        log.info("系统用户目录: [{}]", System.getProperty("user.dir"));
        log.info("系统工作目录: [{}]", System.getProperty("user.home"));
    }
}
