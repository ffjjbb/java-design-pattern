package com.fanjiabao.design.pattern.behavioral.state.source_code_analysis;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * SimpleTomcatComponent 单元测试
 * 测试 Tomcat 生命周期状态模式实现
 */
public class SimpleTomcatComponentTest {

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
    }

    @Test
    @DisplayName("测试组件初始状态为 NEW")
    void testInitialState() {
        // 测试场景: 验证新创建的组件状态为 NEW
        SimpleTomcatComponent component = new SimpleTomcatComponent();
        
        // 断言: 初始状态应为 NEW
        assertThat(component.getState()).isEqualTo(LifecycleState.NEW);
    }

    @Test
    @DisplayName("测试 init 方法执行成功")
    void testInit() throws LifecycleException {
        // 测试场景: 验证初始化方法正确执行
        System.setOut(new PrintStream(outContent));
        
        try {
            SimpleTomcatComponent component = new SimpleTomcatComponent();
            component.init();
            
            // 断言: 状态变为 INITIALIZED
            assertThat(component.getState()).isEqualTo(LifecycleState.INITIALIZED);
            
            String output = outContent.toString();
            assertThat(output).contains("initInternal()");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试完整生命周期流程")
    void testFullLifecycle() throws LifecycleException {
        // 测试场景: 验证完整的生命周期流程
        System.setOut(new PrintStream(outContent));
        
        try {
            SimpleTomcatComponent component = new SimpleTomcatComponent();
            
            // 初始状态
            assertThat(component.getState()).isEqualTo(LifecycleState.NEW);
            
            // init
            component.init();
            assertThat(component.getState()).isEqualTo(LifecycleState.INITIALIZED);
            
            // start
            component.start();
            assertThat(component.getState()).isEqualTo(LifecycleState.STARTED);
            
            // stop
            component.stop();
            assertThat(component.getState()).isEqualTo(LifecycleState.STOPPED);
            
            // destroy
            component.destroy();
            assertThat(component.getState()).isEqualTo(LifecycleState.DESTROYED);
            
            String output = outContent.toString();
            assertThat(output).contains("initInternal()");
            assertThat(output).contains("startInternal()");
            assertThat(output).contains("stopInternal()");
            assertThat(output).contains("destroyInternal()");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 init 重复调用抛异常")
    void testInitTwiceThrowsException() throws LifecycleException {
        // 测试场景: 验证重复初始化抛出异常
        SimpleTomcatComponent component = new SimpleTomcatComponent();
        component.init();
        
        // 断言: 再次初始化应抛出异常
        assertThatThrownBy(() -> component.init())
                .isInstanceOf(LifecycleException.class);
    }

    @Test
    @DisplayName("测试 start 自动触发 init")
    void testStartAutoInit() throws LifecycleException {
        // 测试场景: 验证在 NEW 状态下 start 会自动调用 init
        System.setOut(new PrintStream(outContent));
        
        try {
            SimpleTomcatComponent component = new SimpleTomcatComponent();
            
            // 直接调用 start
            component.start();
            
            // 断言: 状态应为 STARTED（说明 init 自动执行了）
            assertThat(component.getState()).isEqualTo(LifecycleState.STARTED);
            
            String output = outContent.toString();
            assertThat(output).contains("initInternal()");
            assertThat(output).contains("startInternal()");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试 destroy 后无法 start")
    void testCannotStartAfterDestroy() throws LifecycleException {
        // 测试场景: 验证销毁后无法重新启动
        SimpleTomcatComponent component = new SimpleTomcatComponent();
        component.init();
        component.start();
        component.stop();
        component.destroy();
        
        // 断言: 销毁后启动应抛出异常
        assertThatThrownBy(() -> component.start())
                .isInstanceOf(LifecycleException.class);
    }

    @Test
    @DisplayName("测试 stop 在 NEW 状态下正常执行")
    void testStopInNewState() throws LifecycleException {
        // 测试场景: 验证在 NEW 状态下 stop 正常执行
        SimpleTomcatComponent component = new SimpleTomcatComponent();
        component.stop();
        
        // 断言: 状态应为 STOPPED
        assertThat(component.getState()).isEqualTo(LifecycleState.STOPPED);
    }
}
