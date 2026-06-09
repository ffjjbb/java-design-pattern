package com.fanjiabao.design.pattern.structural.decorator.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.Writer;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BufferedWriter 装饰器模式测试
 * 
 * 测试要点:
 * 1. BufferedWriter 装饰 FileWriter
 * 2. 不改变原有功能，增加缓冲功能
 * 3. 继承 Writer 类
 */
class DecoratorExtensionTest {

    @Test
    @DisplayName("DecoratorExtension 应有 main 方法")
    void decoratorExtensionShouldHaveMainMethod() {
        Method main = null;
        try {
            main = DecoratorExtension.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = DecoratorExtension.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("DecoratorExtension 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("BufferedWriter 应继承 Writer")
    void bufferedWriterShouldExtendWriter() {
        assertThat(BufferedWriter.class.getSuperclass())
                .as("BufferedWriter 应继承 Writer")
                .isEqualTo(Writer.class);
    }

    @Test
    @DisplayName("BufferedWriter 应有 write 方法")
    void bufferedWriterShouldHaveWriteMethod() {
        boolean hasWrite = false;
        for (Method method : BufferedWriter.class.getMethods()) {
            if (method.getName().equals("write")) {
                hasWrite = true;
                break;
            }
        }
        assertThat(hasWrite)
                .as("BufferedWriter 应有 write 方法")
                .isTrue();
    }

    @Test
    @DisplayName("BufferedWriter 应有接受 Writer 的构造函数")
    void bufferedWriterShouldAcceptWriterInConstructor() {
        boolean hasWriterConstructor = false;
        for (var constructor : BufferedWriter.class.getConstructors()) {
            if (constructor.getParameterCount() == 1 && 
                constructor.getParameterTypes()[0].equals(Writer.class)) {
                hasWriterConstructor = true;
                break;
            }
        }
        assertThat(hasWriterConstructor)
                .as("BufferedWriter 应有接受 Writer 的构造函数")
                .isTrue();
    }

    @Test
    @DisplayName("BufferedWriter 应有 flush 方法")
    void bufferedWriterShouldHaveFlushMethod() throws NoSuchMethodException {
        assertThat(BufferedWriter.class.getMethod("flush"))
                .as("BufferedWriter 应有 flush 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("BufferedWriter 应有 close 方法")
    void bufferedWriterShouldHaveCloseMethod() {
        boolean hasClose = false;
        for (Method method : BufferedWriter.class.getMethods()) {
            if (method.getName().equals("close")) {
                hasClose = true;
                break;
            }
        }
        assertThat(hasClose)
                .as("BufferedWriter 应有 close 方法")
                .isTrue();
    }

    @Test
    @DisplayName("Writer 应是抽象类")
    void writerShouldBeAbstractClass() {
        assertThat(java.lang.reflect.Modifier.isAbstract(Writer.class.getModifiers()))
                .as("Writer 应是抽象类")
                .isTrue();
    }

    @Test
    @DisplayName("装饰器模式: BufferedWriter 应正确包装 Writer")
    void decoratorShouldWrapWriter() {
        // 验证装饰器模式实现
        try {
            StringBuilder sb = new StringBuilder();
            Writer writer = new Writer() {
                @Override
                public void write(char[] cbuf, int off, int len) {
                    sb.append(cbuf, off, len);
                }
                @Override
                public void flush() {}
                @Override
                public void close() {}
            };
            
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("Hello Decorator");
            bw.close();
            
            assertThat(sb.toString())
                    .as("BufferedWriter 应正确装饰底层 Writer")
                    .isEqualTo("Hello Decorator");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
