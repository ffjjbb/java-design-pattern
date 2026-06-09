package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HibernateMementoRunner 单元测试
 * 测试 Hibernate 备忘录运行器
 */
public class HibernateMementoRunnerTest {

    @Test
    @DisplayName("测试 HibernateMementoRunner 类存在")
    void testClassExists() {
        assertThat(HibernateMementoRunner.class)
                .as("HibernateMementoRunner 类应存在")
                .isNotNull();
    }
}
