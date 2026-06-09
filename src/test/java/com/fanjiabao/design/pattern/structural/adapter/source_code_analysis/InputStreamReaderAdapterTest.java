package com.fanjiabao.design.pattern.structural.adapter.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * InputStreamReader 适配器模式测试
 * 
 * 测试要点:
 * 1. InputStreamReader 将 InputStream 适配为 Reader
 * 2. 使用对象适配器模式（组合）
 * 3. StreamDecoder 完成字节到字符的转换
 */
class InputStreamReaderAdapterTest {

    @Test
    @DisplayName("InputStreamReaderAnalysis 应有 main 方法")
    void inputStreamReaderAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = InputStreamReaderAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = InputStreamReaderAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("InputStreamReaderAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("InputStreamReader 应继承 Reader")
    void inputStreamReaderShouldExtendReader() {
        assertThat(InputStreamReader.class.getSuperclass())
                .as("InputStreamReader 应继承 Reader")
                .isEqualTo(Reader.class);
    }

    @Test
    @DisplayName("InputStreamReader 应实现 Closeable 接口")
    void inputStreamReaderShouldImplementCloseable() {
        assertThat(java.io.Closeable.class.isAssignableFrom(InputStreamReader.class))
                .as("InputStreamReader 应实现 Closeable 接口")
                .isTrue();
    }

    @Test
    @DisplayName("InputStreamReader 应有 read 方法")
    void inputStreamReaderShouldHaveReadMethod() {
        boolean hasRead = false;
        for (Method method : InputStreamReader.class.getMethods()) {
            if (method.getName().equals("read")) {
                hasRead = true;
                break;
            }
        }
        assertThat(hasRead)
                .as("InputStreamReader 应有 read 方法")
                .isTrue();
    }

    @Test
    @DisplayName("InputStreamReader 应接受 InputStream 和字符集参数")
    void inputStreamReaderShouldAcceptInputStreamAndCharset() throws NoSuchMethodException {
        assertThat(InputStreamReader.class.getConstructor(InputStream.class, String.class))
                .as("InputStreamReader 应有 (InputStream, String) 构造函数")
                .isNotNull();
    }

    @Test
    @DisplayName("Reader 应定义 read 方法")
    void readerShouldDefineReadMethod() {
        boolean hasReadMethod = false;
        for (Method method : Reader.class.getDeclaredMethods()) {
            if (method.getName().equals("read")) {
                hasReadMethod = true;
                break;
            }
        }
        assertThat(hasReadMethod)
                .as("Reader 应定义 read 方法")
                .isTrue();
    }

    @Test
    @DisplayName("适配器模式: InputStreamReader 将字节流适配为字符流")
    void adapterShouldConvertByteStreamToCharStream() {
        // 验证 InputStreamReader 可以将 InputStream 转换为 Reader
        try {
            String testContent = "Hello Adapter";
            InputStream inputStream = new java.io.ByteArrayInputStream(testContent.getBytes());
            InputStreamReader reader = new InputStreamReader(inputStream);
            
            char[] buffer = new char[100];
            int length = reader.read(buffer);
            reader.close();
            
            assertThat(new String(buffer, 0, length))
                    .as("InputStreamReader 应正确转换字节流为字符流")
                    .isEqualTo(testContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
