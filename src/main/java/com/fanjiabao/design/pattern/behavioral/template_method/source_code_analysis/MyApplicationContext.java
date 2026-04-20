package com.fanjiabao.design.pattern.behavioral.template_method.source_code_analysis;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 16:25
 * @description: 自定义 ApplicationContext 实现钩子方法
 */
public class MyApplicationContext extends AnnotationConfigApplicationContext {

    public MyApplicationContext(Class<?> configClass) {
        super();
        register(configClass);
        refresh();
    }

    /**
     * 执行时机:
     *  在 BeanDefinition 已加载完成之后
     *  但在 Bean 实例化之前执行
     * 作用:
     *  对 BeanDefinition(Bean定义元数据)进行修改
     *  可以动态调整 Bean 的行为, 而不是操作 Bean 实例
     * 常见用途:
     *  1.修改 BeanDefinition 属性
     *      - 是否懒加载(lazyInit)
     *      - 作用域(scope: singleton/prototype)
     *  2.动态注册 BeanDefinition
     *  3.修改 Bean 的依赖关系
     */
    @Override
        protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.print(name + ", ");
        }
        System.out.println("\npostProcessBeanFactory(): 对 BeanDefinition(Bean定义元数据)进行修改");
    }

    /**
     * 执行时机:
     *  在 BeanPostProcessor 注册完成之后,
     *  但在单例 Bean 实例化之前执行
     * 常见用途:
     *  1.启动后台线程(如定时任务)
     *  2.初始化外部资源(缓存,连接池等)
     *  3.初始化 Web 组件(Spring MVC 中非常重要)
     * 典型案例:
     *  Spring MVC 中:
     *      DispatcherServlet --> 在 onRefresh() 中初始化:
     *          - HandlerMapping
     *          - HandlerAdapter
     *          - ViewResolver
     */
    @Override
    protected void onRefresh() {
        System.out.println("onRefresh(): 容器刷新扩展逻辑, 启动线程, 加载资源等");
    }

}
