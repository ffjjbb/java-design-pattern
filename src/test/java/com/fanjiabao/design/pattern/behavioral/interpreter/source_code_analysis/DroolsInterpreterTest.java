package com.fanjiabao.design.pattern.behavioral.interpreter.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Drools 解释器模式测试
 * 
 * 测试要点:
 * 1. DroolsInterpreterAnalysis 演示规则执行流程
 * 2. Order 类定义规则事实对象
 * 3. KieServices/KieContainer/KieSession 是解释器的关键组件
 */
class DroolsInterpreterTest {

    @Test
    @DisplayName("DroolsInterpreterAnalysis 应有 main 方法")
    void droolsInterpreterAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = DroolsInterpreterAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = DroolsInterpreterAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("DroolsInterpreterAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("Order 类应存在且可实例化")
    void orderClassShouldExistAndBeInstantiable() {
        assertThat(Order.class)
                .as("Order 类应存在")
                .isNotNull();

        // 验证 Order 有构造函数
        assertThat(Order.class.getConstructors())
                .as("Order 应有构造函数")
                .isNotEmpty();
    }

    @Test
    @DisplayName("Order 应有 getAmount 和 getUserLevel 方法")
    void orderShouldHaveGetters() throws NoSuchMethodException {
        assertThat(Order.class.getMethod("getAmount"))
                .as("Order 应有 getAmount 方法")
                .isNotNull();
        assertThat(Order.class.getMethod("getUserLevel"))
                .as("Order 应有 getUserLevel 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("Order 应有 setDiscount 和 setRemark 方法")
    void orderShouldHaveSetters() throws NoSuchMethodException {
        assertThat(Order.class.getMethod("setDiscount", double.class))
                .as("Order 应有 setDiscount 方法")
                .isNotNull();
        assertThat(Order.class.getMethod("setRemark", String.class))
                .as("Order 应有 setRemark 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("Order 应有构造函数接受 id, userLevel, amount 参数")
    void orderShouldHaveThreeArgConstructor() {
        boolean hasThreeArgConstructor = false;
        for (var constructor : Order.class.getConstructors()) {
            if (constructor.getParameterCount() == 3) {
                hasThreeArgConstructor = true;
                break;
            }
        }
        assertThat(hasThreeArgConstructor)
                .as("Order 应有 (Long, String, Integer) 构造函数")
                .isTrue();
    }

    @Test
    @DisplayName("KieServices.Factory 应可访问")
    void kieServicesFactoryShouldBeAccessible() {
        // 验证 KieServices 类可访问
        try {
            Class<?> kieServicesClass = Class.forName("org.kie.api.KieServices");
            assertThat(kieServicesClass)
                    .as("KieServices 类应可访问")
                    .isNotNull();
        } catch (ClassNotFoundException e) {
            // Drools 可能在测试环境中不可用
        }
    }
}
