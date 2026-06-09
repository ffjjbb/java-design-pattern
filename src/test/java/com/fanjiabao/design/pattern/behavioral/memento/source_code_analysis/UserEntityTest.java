package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserEntity 单元测试
 * 测试用户实体的属性和行为
 */
public class UserEntityTest {

    @Test
    @DisplayName("测试创建 UserEntity 使用默认构造函数")
    void testCreateWithDefaultConstructor() {
        // 测试场景: 使用默认构造函数创建实体
        UserEntity user = new UserEntity();
        
        // 断言: 属性应为 null
        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getAge()).isNull();
    }

    @Test
    @DisplayName("测试创建 UserEntity 使用参数构造函数")
    void testCreateWithParameterizedConstructor() {
        // 测试场景: 使用参数构造函数创建实体
        String username = "testUser";
        Integer age = 25;
        
        UserEntity user = new UserEntity(username, age);
        
        // 断言: 属性应正确设置
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getAge()).isEqualTo(age);
        assertThat(user.getId()).isNull();
    }

    @Test
    @DisplayName("测试 setUsername 方法并输出日志")
    void testSetUsername() {
        // 测试场景: 验证设置 username 时输出修改日志
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            UserEntity user = new UserEntity("oldUser", 20);
            user.setUsername("newUser");
            
            String output = outContent.toString();
            // 断言: 输出包含修改日志
            assertThat(output).contains("修改实体 username");
            assertThat(output).contains("oldUser");
            assertThat(output).contains("newUser");
            assertThat(user.getUsername()).isEqualTo("newUser");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 setAge 方法并输出日志")
    void testSetAge() {
        // 测试场景: 验证设置 age 时输出修改日志
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            UserEntity user = new UserEntity("testUser", 20);
            user.setAge(30);
            
            String output = outContent.toString();
            // 断言: 输出包含修改日志
            assertThat(output).contains("修改实体 age");
            assertThat(output).contains("20");
            assertThat(output).contains("30");
            assertThat(user.getAge()).isEqualTo(30);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 toString 方法")
    void testToString() {
        // 测试场景: 验证 toString 方法输出正确格式
        UserEntity user = new UserEntity("testUser", 25);
        
        String result = user.toString();
        
        // 断言: toString 应包含所有属性
        assertThat(result).contains("UserEntity");
        assertThat(result).contains("testUser");
        assertThat(result).contains("25");
    }

    @Test
    @DisplayName("测试多次修改属性")
    void testMultipleModifications() {
        // 测试场景: 验证多次修改属性
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outContent));
            UserEntity user = new UserEntity("user1", 20);
            
            user.setUsername("user2");
            user.setAge(25);
            user.setUsername("user3");
            user.setAge(30);
            
            // 断言: 最终值正确
            assertThat(user.getUsername()).isEqualTo("user3");
            assertThat(user.getAge()).isEqualTo(30);
            
            String output = outContent.toString();
            assertThat(output).contains("user1");
            assertThat(output).contains("user2");
            assertThat(output).contains("user3");
        } finally {
            System.setOut(originalOut);
        }
    }
}
