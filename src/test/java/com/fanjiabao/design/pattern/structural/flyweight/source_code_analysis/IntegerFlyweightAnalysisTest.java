package com.fanjiabao.design.pattern.structural.flyweight.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * IntegerFlyweightAnalysis 单元测试
 * 测试 Integer 享元模式的缓存行为
 */
public class IntegerFlyweightAnalysisTest {

    @Test
    @DisplayName("测试 Integer.valueOf 在缓存范围内返回同一对象")
    void testIntegerCacheWithinRange() {
        // 测试场景: 验证 Integer 缓存范围内（-128 到 127）的对象复用
        Integer i1 = Integer.valueOf(127);
        Integer i2 = Integer.valueOf(127);
        
        // 断言: 相同值的 Integer 在缓存范围内应该是同一个对象
        assertThat(i1).isSameAs(i2);
        assertThat(i1).isEqualTo(127);
    }

    @Test
    @DisplayName("测试 Integer 自动装箱在缓存范围内返回同一对象")
    void testIntegerAutoBoxingWithinRange() {
        // 测试场景: 验证自动装箱在缓存范围内的行为
        Integer i1 = 100;
        Integer i2 = 100;
        
        // 断言: 自动装箱在缓存范围内应该是同一个对象
        assertThat(i1).isSameAs(i2);
    }

    @Test
    @DisplayName("测试 Integer 超出缓存范围返回不同对象")
    void testIntegerCacheOutsideRange() {
        // 测试场景: 验证超出缓存范围的 Integer 会创建新对象
        Integer i3 = 128;
        Integer i4 = 128;
        
        // 断言: 超出缓存范围的 Integer 是不同对象但值相等
        assertThat(i3).isNotSameAs(i4);
        assertThat(i3).isEqualTo(i4);
    }

    @Test
    @DisplayName("测试 Integer 负数在缓存范围内")
    void testIntegerNegativeWithinRange() {
        // 测试场景: 验证负数在缓存范围内的行为
        Integer i1 = -128;
        Integer i2 = -128;
        
        // 断言: -128 在缓存范围内，应该是同一个对象
        assertThat(i1).isSameAs(i2);
        assertThat(i1).isEqualTo(-128);
    }

    @Test
    @DisplayName("测试 Integer 负数超出缓存范围")
    void testIntegerNegativeOutsideRange() {
        // 测试场景: 验证负数超出缓存范围的行为
        Integer i1 = -129;
        Integer i2 = -129;
        
        // 断言: -129 超出缓存范围，应该是不同对象
        assertThat(i1).isNotSameAs(i2);
        assertThat(i1).isEqualTo(-129);
    }

    @Test
    @DisplayName("测试 main 方法执行成功")
    void testMainMethod() {
        // 测试场景: 验证 main 方法能正常执行并输出预期内容
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            IntegerFlyweightAnalysis.main(new String[]{});
            
            String output = outContent.toString();
            // 断言: 输出包含预期的字符串
            assertThat(output).contains("i1 和 i2对象是否是同一个对象: true");
            assertThat(output).contains("i3 和 i4 对象是否是同一个对象: false");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 cds 方法可以正常调用")
    void testCdsMethod() {
        // 测试场景: 验证 cds 方法可以被调用
        IntegerFlyweightAnalysis analysis = new IntegerFlyweightAnalysis();
        
        // 执行: 调用 cds 方法（空实现，主要验证不抛异常）
        analysis.cds();
        
        // 断言: 方法正常执行，无异常
        assertThat(analysis).isNotNull();
    }
}
