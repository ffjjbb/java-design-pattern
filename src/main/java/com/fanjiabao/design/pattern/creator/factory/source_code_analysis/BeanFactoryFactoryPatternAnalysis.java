package com.fanjiabao.design.pattern.creator.factory.source_code_analysis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: FanJiaBao
 * @createDate: 2026/5/18 18:23
 * @description: Spring BeanFactory 工厂模式分析
 *  - BeanFactory 是典型的工厂模式实现, 用于创建和管理 Bean 对象
 *  - 客户端只调用 getBean(), 无需关心 Bean 的具体实现或初始化流程
 * <p>
 * 模式角色:
 *  - Factory: BeanFactory / ApplicationContext
 *  - Product: Bean 对象（例如 HelloService）
 *  - Client: 调用 getBean() 的代码
 */
public class BeanFactoryFactoryPatternAnalysis  {

    public static void main(String[] args) {

        /**
         * 创建 Spring 容器（Factory）
         * 说明:
         *  - AnnotationConfigApplicationContext 是 ApplicationContext 实现
         *  - 内部封装了 DefaultListableBeanFactory
         *  - 负责 Bean 注册、实例化和依赖注入
         * 源码调用流程:
         *  --> AnnotationConfigApplicationContext(AppConfig.class)
         *      - prepareRefresh() 初始化容器基础设施
         *      - obtainFreshBeanFactory() 创建 DefaultListableBeanFactory
         *      - registerBeanDefinition(HelloService) 注册 BeanDefinition
         *      - refresh() 完成容器刷新、单例 Bean 初始化
         */
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        /**
         * 客户端获取 Bean（Product）
         * 说明:
         *  - 调用 getBean() 获取对象
         *  - BeanFactory 内部根据 BeanDefinition 实例化 Bean
         * 源码调用流程:
         *  --> context.getBean(HelloService.class)
         *      -> DefaultListableBeanFactory.getBean()
         *          - resolveBean() 解析依赖并创建对象
         *          - 如果单例，先从单例池获取，否则调用 createBean()
         *      -> createBean()
         *          - 实例化对象: new HelloService()
         *          - BeanPostProcessor.postProcessBeforeInitialization()
         *          - 调用 init-method 或 @PostConstruct
         *          - BeanPostProcessor.postProcessAfterInitialization()
         *  --> 返回 HelloService 对象 (Product)
         */
        HelloService helloService = context.getBean(HelloService.class);

        /**
         * 调用 Product 方法执行业务逻辑
         */
        String result = helloService.say();
        System.out.println(result);
    }

    /**
     * Bean 定义（Product）
     */
    @Configuration
    static class AppConfig {

        @Bean
        public HelloService helloService() {
            return new HelloService();
        }
    }

    /**
     * Product 类
     */
    static class HelloService {
        public String say() {
            return "工厂模式";
        }
    }

}
