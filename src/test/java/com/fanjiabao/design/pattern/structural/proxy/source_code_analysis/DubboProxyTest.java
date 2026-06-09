package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis;

import com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.api.GreetingService;
import com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider.DubboProviderApplication;
import com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider.DubboProviderConfig;
import com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider.GreetingServiceImpl;
import com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.consumer.DubboConsumerApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Dubbo 代理模式测试
 * 
 * 测试要点:
 * 1. GreetingService 是服务接口
 * 2. GreetingServiceImpl 实现服务接口
 * 3. Dubbo 使用代理模式进行 RPC 调用
 */
class DubboProxyTest {

    @Test
    @DisplayName("GreetingService 接口应存在")
    void greetingServiceInterfaceShouldExist() {
        assertThat(GreetingService.class)
                .as("GreetingService 接口应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("GreetingService 应有 say 方法")
    void greetingServiceShouldHaveSayMethod() {
        boolean hasSay = false;
        for (Method method : GreetingService.class.getMethods()) {
            if (method.getName().equals("say")) {
                hasSay = true;
                break;
            }
        }
        assertThat(hasSay)
                .as("GreetingService 应有 say 方法")
                .isTrue();
    }

    @Test
    @DisplayName("GreetingServiceImpl 应实现 GreetingService")
    void greetingServiceImplShouldImplementGreetingService() {
        assertThat(GreetingService.class.isAssignableFrom(GreetingServiceImpl.class))
                .as("GreetingServiceImpl 应实现 GreetingService")
                .isTrue();
    }

    @Test
    @DisplayName("GreetingServiceImpl 应有 @DubboService 注解")
    void greetingServiceImplShouldHaveDubboServiceAnnotation() {
        boolean hasDubboService = false;
        for (var annotation : GreetingServiceImpl.class.getAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("DubboService")) {
                hasDubboService = true;
                break;
            }
        }
        assertThat(hasDubboService)
                .as("GreetingServiceImpl 应有 @DubboService 注解")
                .isTrue();
    }

    @Test
    @DisplayName("GreetingServiceImpl.say 方法应返回正确值")
    void greetingServiceImplSayShouldReturnCorrectValue() {
        try {
            GreetingService service = new GreetingServiceImpl();
            String result = service.say("test");
            assertThat(result)
                    .as("say 方法应返回包含输入的结果")
                    .contains("test");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("DubboProviderConfig 应存在")
    void dubboProviderConfigShouldExist() {
        assertThat(DubboProviderConfig.class)
                .as("DubboProviderConfig 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("DubboProviderApplication 应存在")
    void dubboProviderApplicationShouldExist() {
        assertThat(DubboProviderApplication.class)
                .as("DubboProviderApplication 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("DubboConsumerApplication 应存在")
    void dubboConsumerApplicationShouldExist() {
        assertThat(DubboConsumerApplication.class)
                .as("DubboConsumerApplication 类应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("DubboConsumerApplication 应有 main 方法")
    void dubboConsumerApplicationShouldHaveMainMethod() {
        Method main = null;
        try {
            main = DubboConsumerApplication.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = DubboConsumerApplication.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("DubboConsumerApplication 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("DubboProviderApplication 应有 main 方法")
    void dubboProviderApplicationShouldHaveMainMethod() {
        Method main = null;
        try {
            main = DubboProviderApplication.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = DubboProviderApplication.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("DubboProviderApplication 应有 main 方法")
                .isNotNull();
    }
}
