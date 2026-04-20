package com.fanjiabao.design.pattern.behavioral.template_method.source_code_analysis;

import com.fanjiabao.design.pattern.behavioral.template_method.TemplateMethod;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 15:55
 * @description: AbstractApplicationContext 模板方法解析
 */
public class AbstractApplicationContextTemplateAnalysis {

    /**
     * refresh: 从0到1构建Spring容器的完整生命周期
     * 调用流程:
     * -->  new MyApplicationContext(AppConfig.class);
     *  --> refresh(); ==> MyApplicationContext.java:16
     *      1.准备上下文
     *      --> this.prepareRefresh(); ==> AbstractApplicationContext.class:312
     *      2.获取BeanFactory
     *      --> ConfigurableListableBeanFactory beanFactory ==> this.obtainFreshBeanFactory(); = AbstractApplicationContext.class:313
     *      3.准备BeanFactory(设置类加载器, 添加后处理器等)
     *      --> this.prepareBeanFactory(beanFactory); ==> AbstractApplicationContext.class:314
     *      4.模板方法扩展点。
     *          在 Bean 实例化之前
     *          BeanDefinition 已经加载, 允许对子类修改 BeanDefinition(元数据层)
     *      --> this.postProcessBeanFactory(beanFactory); ==> AbstractApplicationContext.class:317
     *      5.执行BeanFactory后处理器
     *      --> this.invokeBeanFactoryPostProcessors(beanFactory); ==> AbstractApplicationContext.class:319
     *      6.注册Bean后处理器(AOP关键)
     *      --> this.registerBeanPostProcessors(beanFactory); ==> AbstractApplicationContext.class:320
     *      7.初始化国际化
     *      --> this.initMessageSource(); ==> AbstractApplicationContext.class:322
     *      8.初始化事件广播器
     *      --> this.initApplicationEventMulticaster(); ==> AbstractApplicationContext.class:323
     *      9.模板方法扩展点
     *          在 Bean 初始化之前
     *          用于容器级组件初始化(如 Web 组件)
     *      --> this.onRefresh(); ==> AbstractApplicationContext.class:324
     *      10.注册监听器
     *      --> this.registerListeners(); ==> AbstractApplicationContext.class:325
     *      11.初始化所有非懒加载单例Bean
     *      --> this.finishBeanFactoryInitialization(beanFactory); ==> AbstractApplicationContext.class:326
     *      12.发布刷新完成事件
     *      --> this.finishRefresh(); ==> AbstractApplicationContext.class:327
     *      --> this.destroyBeans(); ==> AbstractApplicationContext.class:333
     *      --> this.cancelRefresh(var12); ==> AbstractApplicationContext.class:334
     */
    public static void main(String[] args) {
        MyApplicationContext context = new MyApplicationContext(AppConfig.class);
        TemplateMethod templateMethod = context.getBean(TemplateMethod.class);
        templateMethod.usageScenarios();
    }

}
