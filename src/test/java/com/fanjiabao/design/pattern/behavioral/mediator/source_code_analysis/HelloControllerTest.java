package com.fanjiabao.design.pattern.behavioral.mediator.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HelloController 单元测试
 * 测试 Controller 的业务方法
 */
public class HelloControllerTest {

    private HelloController helloController;

    @BeforeEach
    void setUp() {
        helloController = new HelloController();
    }

    @Test
    @DisplayName("测试 hello 方法使用默认参数")
    void testHelloWithDefaultParam() {
        // 测试场景: 使用默认参数调用 hello 方法
        String result = helloController.hello("guest");
        
        // 断言: 返回包含默认名称的问候语
        assertThat(result).isEqualTo("Hello guest, DispatcherServlet is Mediator.");
    }

    @Test
    @DisplayName("测试 hello 方法使用自定义参数")
    void testHelloWithCustomParam() {
        // 测试场景: 使用自定义名称调用 hello 方法
        String name = "World";
        String result = helloController.hello(name);
        
        // 断言: 返回包含自定义名称的问候语
        assertThat(result).isEqualTo("Hello World, DispatcherServlet is Mediator.");
    }

    @Test
    @DisplayName("测试 hello 方法包含中介者说明")
    void testHelloContainsMediatorInfo() {
        // 测试场景: 验证返回值包含中介者说明
        String result = helloController.hello("test");
        
        // 断言: 返回值包含 DispatcherServlet 说明
        assertThat(result).contains("DispatcherServlet");
        assertThat(result).contains("Mediator");
    }

    @Test
    @DisplayName("测试 hello 方法处理空字符串")
    void testHelloWithEmptyString() {
        // 测试场景: 使用空字符串调用 hello 方法
        String result = helloController.hello("");
        
        // 断言: 返回正确格式
        assertThat(result).isEqualTo("Hello , DispatcherServlet is Mediator.");
    }

    @Test
    @DisplayName("测试 hello 方法处理特殊字符")
    void testHelloWithSpecialCharacters() {
        // 测试场景: 使用特殊字符调用 hello 方法
        String result = helloController.hello("张三");
        
        // 断言: 返回包含特殊字符的问候语
        assertThat(result).contains("张三");
    }

    @Test
    @DisplayName("测试 HelloController 实例创建")
    void testCreateInstance() {
        // 测试场景: 验证可以创建 HelloController 实例
        HelloController controller = new HelloController();
        
        // 断言: 实例不为空
        assertThat(controller).isNotNull();
    }
}
