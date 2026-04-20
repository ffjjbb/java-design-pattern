package com.fanjiabao.design.pattern.behavioral.template_method.source_code_analysis;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 16:13
 * @description:
 * BeanPostProcessor: 在 Bean 初始化前后, 对 Bean 进行 "统一拦截和增强"
 * Spring不只靠继承扩展(模板方法), 还可以靠 "接口扩展"(更灵活)
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前: " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后: " + beanName);
        return bean;
    }
}
