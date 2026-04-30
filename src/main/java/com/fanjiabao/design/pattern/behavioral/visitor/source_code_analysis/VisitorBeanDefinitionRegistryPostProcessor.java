package com.fanjiabao.design.pattern.behavioral.visitor.source_code_analysis;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author: FanJiaBao
 * @description:
 */
@Component
public class VisitorBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 第一步:
     *  手动注册一个 BeanDefinition
     *  重点:
     *      这里把 ${demo.name} 和 ${demo.url}
     *      放到了 BeanDefinition 的 propertyValues 中。
     *  这样 BeanDefinitionVisitor 才能真正访问到它们。
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DataSourceConfigBean.class);

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("name", "${demo.name}");
        propertyValues.add("url", "${demo.url}");

        beanDefinition.setPropertyValues(propertyValues);
        registry.registerBeanDefinition("dataSourceConfigBean", beanDefinition);
    }

    /**
     * 第二步:
     *  BeanDefinition 注册完成后, 访问并修改 BeanDefinition
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        System.out.println("\n========== BeanDefinitionVisitor 开始访问 ==========");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("dataSourceConfigBean");
        System.out.println("访问前:");
        System.out.println(beanDefinition.getPropertyValues());

        BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(strVal -> {
            System.out.println("正在访问并解析: " + strVal);
            return environment.resolvePlaceholders(strVal);
        });

        visitor.visitBeanDefinition(beanDefinition);
        System.out.println("访问后:");
        System.out.println(beanDefinition.getPropertyValues());
    }
}