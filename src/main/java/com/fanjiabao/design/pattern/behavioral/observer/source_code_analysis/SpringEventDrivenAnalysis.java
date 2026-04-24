package com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24 12:03
 * @description: 启动类(Spring 事件驱动观察者模式源码分析)
 * Spring 事件机制 = 观察者模式 + Spring 容器管理 + 事件类型匹配 + 事件广播器
 */
@ComponentScan("com.fanjiabao.design.pattern.behavioral.observer.source_code_analysis")
public class SpringEventDrivenAnalysis {

    /**
     * 调用流程:
     *  启动 Spring 容器, 注册监听器
     *      --> new AnnotationConfigApplicationContext(SpringEventDrivenAnalysis.class);
     *          创建 AnnotatedBeanDefinitionReader(注解读取), ClassPathBeanDefinitionScanner(类扫描)
     *          --> this();
     *          把 SpringEventDrivenAnalysis 注册成 BeanDefinition
     *          --> register(componentClasses);
     *          启动的核心方法
     *          --> refresh();
     *              设置类加载器、表达式解析器、类型转换器、注册一些特殊依赖对象
     *              --> prepareBeanFactory(beanFactory);
     *                  如果某个 Bean 需要注入 ApplicationEventPublisher, 直接把当前 Spring 容器 this 注入进去
     *                  --> beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
     *              创建事件广播器, 初始化 ApplicationEventMulticaster(把事件广播给监听器)
     *              --> initApplicationEventMulticaster();
     *                  --> beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
     *                      如果你自己定义了 applicationEventMulticaster 事件广播器(自行配置异步线程池, 错误处理), Spring 就用你自定义的
     *                      --> this.applicationEventMulticaster = beanFactory.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
     *                  如果你没定义, Spring 默认创建 SimpleApplicationEventMulticaster。
     *                  --> this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
     *              注册监听器, 找到所有 ApplicationListener 类型的 Bean, 然后把这些监听器注册到事件广播器中
     *              --> registerListeners();
     *                  找到我的 SmsListener, 把 beanName 注册到事件广播器中
     *                  --> String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
     *                      这里注册的是 beanName, 不是直接创建监听器对象, 后续真正发布事件时, Spring 可以通过 beanName 懒加载或获取监听器
     *                      --> getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
     *  业务发布事件
     *      --> orderService.createOrder();
     *          发布事件
     *          --> publisher.publishEvent(new OrderCreatedEvent(this, "ORDER_001"));
     *              实际调用
     *              --> AbstractApplicationContext.publishEvent(ApplicationEvent event)
     *                  把事件交给 ApplicationEventMulticaster, 由它负责查找匹配的监听器并调用监听器方法
     *                  --> this.applicationEventMulticaster.multicastEvent(applicationEvent, eventType);
     *                      实际是上面创建的 SimpleApplicationEventMulticaster
     *                      --> SimpleApplicationEventMulticaster.multicastEvent(event, eventType)
     *                          这段是观察者模式的核心, 根据事件类型筛选监听器
     *                          --> ApplicationListener<?> listener : getApplicationListeners(event, type)
     *                          如果配置了异步 executor, 就异步执行, 没有就同步执行
     *                          --> executor != null && listener.supportsAsyncExecution()
     *                              --> executor.execute(() -> invokeListener(listener, event));
     *                              --> invokeListener(listener, event);
     *                                  --> doInvokeListener(listener, event);
     *                                      SmsListener 实现了 ApplicationListener 接口
     *                                      --> ApplicationListener listener.onApplicationEvent(event)
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SpringEventDrivenAnalysis.class);
        OrderService orderService = context.getBean(OrderService.class);
        orderService.createOrder();
    }

}
