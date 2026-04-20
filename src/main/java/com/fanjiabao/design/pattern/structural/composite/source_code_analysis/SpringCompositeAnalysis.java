package com.fanjiabao.design.pattern.structural.composite.source_code_analysis;

import com.fanjiabao.design.pattern.structural.composite.Composite;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 9:58
 * @description: Spring Framework 的父子容器: 树状结构 + 递归查找(组合模式的变种)
 */
public class SpringCompositeAnalysis {

    /**
     * 调用流程:
     *  1.new AnnotationConfigApplicationContext();
     *      --> extends GenericApplicationContext
     *          --> this.beanFactory = new DefaultListableBeanFactory();
     *  2.childContext.getBean(Composite.class);
     *      --> this.getBeanFactory().getBean(requiredType); = AbstractApplicationContext.class:649
     *          --> this.getBean(requiredType, (Object[])null); = DefaultListableBeanFactory.class:185
     *              --> this.resolveBean(ResolvableType.forRawClass(requiredType), args, false); = DefaultListableBeanFactory.class:190
     *                  // 向上递归
     *                  --> return (T)dlfb.resolveBean(requiredType, args, nonUniqueAsNull); = DefaultListableBeanFactory.class:320
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.registerBean("composite", Composite.class);
        parentContext.refresh();

        // 子容器
        AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
        // 设置父容器
        childContext.setParent(parentContext);
        childContext.refresh();

        // 从子容器获取 Bean
        Composite composite = childContext.getBean(Composite.class);
        composite.usageScenarios();
    }

}
