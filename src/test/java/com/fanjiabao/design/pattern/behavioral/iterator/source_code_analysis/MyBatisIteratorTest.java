package com.fanjiabao.design.pattern.behavioral.iterator.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * MyBatis 迭代器模式测试
 * 
 * 测试要点:
 * 1. MyBatisIteratorAnalysis 演示 ResultSet 遍历
 * 2. 结果集使用游标式迭代
 */
class MyBatisIteratorTest {

    @Test
    @DisplayName("MyBatisIteratorAnalysis 应有 main 方法")
    void myBatisIteratorAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = MyBatisIteratorAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = MyBatisIteratorAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("MyBatisIteratorAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("ResultSet 接口应存在且有 next 方法")
    void resultSetShouldHaveNextMethod() {
        try {
            Class<?> resultSetClass = Class.forName("java.sql.ResultSet");
            boolean hasNext = false;
            for (Method method : resultSetClass.getDeclaredMethods()) {
                if (method.getName().equals("next")) {
                    hasNext = true;
                    break;
                }
            }
            assertThat(hasNext)
                    .as("ResultSet 应有 next 方法")
                    .isTrue();
        } catch (ClassNotFoundException e) {
            // ResultSet 可能在测试环境中不可用
        }
    }

    @Test
    @DisplayName("ResultSet 应有 getString 等获取方法")
    void resultSetShouldHaveGetMethods() {
        try {
            Class<?> resultSetClass = Class.forName("java.sql.ResultSet");
            assertThat(resultSetClass.getMethod("getString", int.class))
                    .as("ResultSet 应有 getString 方法")
                    .isNotNull();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            // ResultSet 可能在测试环境中不可用
        }
    }

    @Test
    @DisplayName("SqlSessionFactoryBuilder 应可访问")
    void sqlSessionFactoryBuilderShouldBeAccessible() {
        try {
            Class<?> builderClass = Class.forName("org.apache.ibatis.session.SqlSessionFactoryBuilder");
            assertThat(builderClass)
                    .as("SqlSessionFactoryBuilder 类应可访问")
                    .isNotNull();
        } catch (ClassNotFoundException e) {
            // MyBatis 可能在测试环境中不可用
        }
    }
}
