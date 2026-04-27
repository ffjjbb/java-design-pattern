package com.fanjiabao.design.pattern.behavioral.mediator.source_code_analysis;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24
 * @description: Spring MVC 配置类
 * @EnableWebMvc 作用: 开启 Spring MVC 注解驱动能力, 导入 DelegatingWebMvcConfiguration
 *  最终会注册 Spring MVC 需要的核心组件:
 *      RequestMappingHandlerMapping
 *              扫描 @Controller / @RestController 中的 @RequestMapping / @GetMapping。
 *              建立 URL 到 Controller 方法的映射关系。
 *      RequestMappingHandlerAdapter
 *              负责执行 Controller 方法。
 *              处理方法参数绑定、返回值处理、消息转换器等。
 *      HandlerExceptionResolver
 *              处理 Controller 执行过程中抛出的异常。
 *  注意:
 *      DispatcherServlet 本身是中介者。
 *      @EnableWebMvc 注册的是中介者需要协调的各类组件。
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.fanjiabao.design.pattern.behavioral.mediator.source_code_analysis")
public class AppConfig {}