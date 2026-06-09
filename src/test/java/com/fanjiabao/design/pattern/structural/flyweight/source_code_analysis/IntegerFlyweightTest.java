package com.fanjiabao.design.pattern.structural.flyweight.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integer 享元模式测试
 * 
 * 测试要点:
 * 1. Integer 使用 IntegerCache 缓存 [-128, 127]
 * 2. 相同值的 Integer 返回同一对象
 * 3. 超出范围的 Integer 会创建新对象
 */
class IntegerFlyweightTest {

    @Test
    @DisplayName("IntegerFlyweightAnalysis 应有 main 方法")
    void integerFlyweightAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = IntegerFlyweightAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = IntegerFlyweightAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("IntegerFlyweightAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("Integer.valueOf(127) 应返回缓存对象")
    void integerValueOf127ShouldReturnCachedObject() {
        Integer i1 = Integer.valueOf(127);
        Integer i2 = Integer.valueOf(127);
        assertThat(i1)
                .as("Integer.valueOf(127) 应返回缓存对象")
                .isSameAs(i2);
    }

    @Test
    @DisplayName("自动装箱 127 应返回缓存对象")
    void autoBoxing127ShouldReturnCachedObject() {
        Integer i1 = 127;
        Integer i2 = 127;
        assertThat(i1)
                .as("自动装箱 127 应返回缓存对象")
                .isSameAs(i2);
    }

    @Test
    @DisplayName("Integer.valueOf(128) 应返回新对象")
    void integerValueOf128ShouldReturnNewObject() {
        Integer i1 = Integer.valueOf(128);
        Integer i2 = Integer.valueOf(128);
        assertThat(i1)
                .as("Integer.valueOf(128) 应返回新对象")
                .isNotSameAs(i2);
    }

    @Test
    @DisplayName("自动装箱 128 应返回新对象")
    void autoBoxing128ShouldReturnNewObject() {
        Integer i1 = 128;
        Integer i2 = 128;
        assertThat(i1)
                .as("自动装箱 128 应返回新对象")
                .isNotSameAs(i2);
    }

    @Test
    @DisplayName("Integer 负数缓存范围应正确")
    void integerNegativeCacheRangeShouldBeCorrect() {
        Integer i1 = Integer.valueOf(-128);
        Integer i2 = Integer.valueOf(-128);
        assertThat(i1)
                .as("Integer.valueOf(-128) 应返回缓存对象")
                .isSameAs(i2);
    }

    @Test
    @DisplayName("Integer 边界值 -129 应返回新对象")
    void integerMinus129ShouldReturnNewObject() {
        Integer i1 = Integer.valueOf(-129);
        Integer i2 = Integer.valueOf(-129);
        assertThat(i1)
                .as("Integer.valueOf(-129) 应返回新对象")
                .isNotSameAs(i2);
    }

    @Test
    @DisplayName("Integer 边界值 128 应返回新对象")
    void integer128ShouldReturnNewObject() {
        Integer i1 = Integer.valueOf(128);
        Integer i2 = Integer.valueOf(128);
        assertThat(i1)
                .as("Integer.valueOf(128) 应返回新对象")
                .isNotSameAs(i2);
    }

    @Test
    @DisplayName("IntegerCache 类应存在")
    void integerCacheClassShouldExist() {
        boolean hasIntegerCache = false;
        for (var clazz : Integer.class.getDeclaredClasses()) {
            if (clazz.getSimpleName().equals("IntegerCache")) {
                hasIntegerCache = true;
                break;
            }
        }
        assertThat(hasIntegerCache)
                .as("IntegerCache 内部类应存在")
                .isTrue();
    }

    @Test
    @DisplayName("IntegerCache 应有 cache 数组")
    void integerCacheShouldHaveCacheArray() {
        boolean hasCache = false;
        for (var field : Integer.class.getDeclaredClasses()) {
            if (field.getSimpleName().equals("IntegerCache")) {
                for (var f : field.getDeclaredFields()) {
                    if (f.getName().equals("cache") && f.getType().isArray()) {
                        hasCache = true;
                        break;
                    }
                }
            }
        }
        assertThat(hasCache)
                .as("IntegerCache 应有 cache 数组")
                .isTrue();
    }
}
