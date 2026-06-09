package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HibernateMementoService 单元测试
 * 测试 Hibernate 备忘录服务
 */
public class HibernateMementoServiceTest {

    @Test
    @DisplayName("测试 HibernateMementoService 类存在")
    void testClassExists() {
        assertThat(HibernateMementoService.class)
                .as("HibernateMementoService 类应存在")
                .isNotNull();
    }
}
