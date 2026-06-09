package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserRepository 单元测试
 * 测试用户仓库接口
 */
public class UserRepositoryTest {

    @Test
    @DisplayName("测试 UserRepository 是接口")
    void testIsInterface() {
        // 测试场景: 验证 UserRepository 是接口
        boolean isInterface = UserRepository.class.isInterface();
        
        // 断言: 是接口
        assertThat(isInterface).isTrue();
    }
}
