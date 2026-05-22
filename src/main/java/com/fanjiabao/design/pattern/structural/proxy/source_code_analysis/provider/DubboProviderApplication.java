package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: FanJiaBao
 * @createDate: 2026/5/18 16:14
 * @description: 提供者启动类
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider")
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DubboProviderApplication.class);
        app.setAdditionalProfiles("provider");
        app.run(args);
        System.out.println("Provider 启动成功");
    }

}
