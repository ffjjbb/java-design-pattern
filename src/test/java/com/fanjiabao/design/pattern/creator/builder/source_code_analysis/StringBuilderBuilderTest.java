package com.fanjiabao.design.pattern.creator.builder.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StringBuilder 建造者模式测试
 * 
 * 测试要点:
 * 1. StringBuilder 实现了 Builder 模式
 * 2. append 方法构建最终对象
 * 3. toString 返回最终 Product
 */
class StringBuilderBuilderTest {

    @Test
    @DisplayName("StringBuilderBuilderAnalysis 应有 main 方法")
    void stringBuilderBuilderAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = StringBuilderBuilderAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = StringBuilderBuilderAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("StringBuilderBuilderAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("StringBuilder 应有 append 方法")
    void stringBuilderShouldHaveAppendMethod() throws NoSuchMethodException {
        assertThat(StringBuilder.class.getMethod("append", String.class))
                .as("StringBuilder 应有 append(String) 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("StringBuilder.append 应支持链式调用")
    void stringBuilderAppendShouldReturnStringBuilder() {
        StringBuilder sb = new StringBuilder();
        StringBuilder result = sb.append("test");
        assertThat(result)
                .as("StringBuilder.append 应返回 StringBuilder 实例支持链式调用")
                .isSameAs(sb);
    }

    @Test
    @DisplayName("StringBuilder 应有 toString 方法返回 String")
    void stringBuilderShouldHaveToStringMethod() throws NoSuchMethodException {
        assertThat(StringBuilder.class.getMethod("toString"))
                .as("StringBuilder 应有 toString 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("StringBuilder 应有容量相关方法")
    void stringBuilderShouldHaveCapacityMethods() throws NoSuchMethodException {
        assertThat(StringBuilder.class.getMethod("capacity"))
                .as("StringBuilder 应有 capacity 方法")
                .isNotNull();
        assertThat(StringBuilder.class.getMethod("ensureCapacity", int.class))
                .as("StringBuilder 应有 ensureCapacity 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("StringBuilder 应有 length 方法")
    void stringBuilderShouldHaveLengthMethod() throws NoSuchMethodException {
        assertThat(StringBuilder.class.getMethod("length"))
                .as("StringBuilder 应有 length 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("StringBuilder 应实现 CharSequence 接口")
    void stringBuilderShouldImplementCharSequence() {
        assertThat(CharSequence.class.isAssignableFrom(StringBuilder.class))
                .as("StringBuilder 应实现 CharSequence 接口")
                .isTrue();
    }

    @Test
    @DisplayName("StringBuilder 应继承 AbstractStringBuilder")
    void stringBuilderShouldExtendAbstractStringBuilder() {
        assertThat(StringBuilder.class.getSuperclass().getSimpleName())
                .as("StringBuilder 应继承 AbstractStringBuilder")
                .isEqualTo("AbstractStringBuilder");
    }

    @Test
    @DisplayName("StringBuilder 链式调用应正常工作")
    void stringBuilderChainingShouldWork() {
        String result = new StringBuilder()
                .append("Hello")
                .append(" ")
                .append("World")
                .toString();
        assertThat(result)
                .as("链式调用应正确构建字符串")
                .isEqualTo("Hello World");
    }

    @Test
    @DisplayName("StringBuilder 默认容量应为 16")
    void stringBuilderDefaultCapacityShouldBe16() {
        StringBuilder sb = new StringBuilder();
        assertThat(sb.capacity())
                .as("StringBuilder 默认容量应为 16")
                .isEqualTo(16);
    }
}
