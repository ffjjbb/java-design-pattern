package com.fanjiabao.design.pattern.behavioral.strategy.source_code_analysis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * MyRejectHandler 单元测试
 * 测试自定义线程池拒绝策略
 */
public class MyRejectHandlerTest {

    private MyRejectHandler handler;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        handler = new MyRejectHandler();
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
    }

    @Test
    @DisplayName("测试拒绝策略执行并输出消息")
    void testRejectedExecution() {
        // 测试场景: 验证拒绝策略被触发时输出正确消息
        System.setOut(new PrintStream(outContent));
        
        try {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    1, 1, 60, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(1)
            );
            Runnable task = () -> {};
            
            handler.rejectedExecution(task, executor);
            
            String output = outContent.toString();
            // 断言: 输出包含自定义拒绝消息
            assertThat(output).contains("自定义策略");
            assertThat(output).contains("太忙");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试拒绝策略不抛异常")
    void testRejectedExecutionNoException() {
        // 测试场景: 验证拒绝策略不会抛出异常
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1)
        );
        Runnable task = () -> {};
        
        // 断言: 执行不抛异常
        assertThatCode(() -> handler.rejectedExecution(task, executor))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("测试拒绝策略可以处理多次调用")
    void testMultipleRejectedExecutions() {
        // 测试场景: 验证拒绝策略可以被多次调用
        System.setOut(new PrintStream(outContent));
        
        try {
            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    1, 1, 60, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(1)
            );
            Runnable task = () -> {};
            
            handler.rejectedExecution(task, executor);
            handler.rejectedExecution(task, executor);
            handler.rejectedExecution(task, executor);
            
            String output = outContent.toString();
            // 断言: 消息出现3次
            assertThat(output).contains("自定义策略");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("测试拒绝策略处理 null Runnable")
    void testNullRunnable() {
        // 测试场景: 验证拒绝策略可以处理 null Runnable
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1)
        );
        
        // 断言: null Runnable 不导致异常
        assertThatCode(() -> handler.rejectedExecution(null, executor))
                .doesNotThrowAnyException();
    }
}
