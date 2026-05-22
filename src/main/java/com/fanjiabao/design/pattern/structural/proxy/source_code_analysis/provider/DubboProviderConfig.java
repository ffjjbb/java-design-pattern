package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider;

import org.apache.dubbo.config.ProtocolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: FanJiaBao
 * @createDate: 2026/5/18 17:16
 * @description: 配置类
 */
@Configuration
public class DubboProviderConfig {

    @Bean
    public ProtocolConfig dubboProtocol() {
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);
        protocol.setSerialization("fastjson2");
        return protocol;
    }

}
