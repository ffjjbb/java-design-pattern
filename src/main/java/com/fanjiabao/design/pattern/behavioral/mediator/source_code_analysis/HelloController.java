package com.fanjiabao.design.pattern.behavioral.mediator.source_code_analysis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/24
 * @description: Controller 同事类
 * 在中介者模式中:
 *  HelloController 不是中介者。
 *  它是被 DispatcherServlet 协调的同事对象。
 * 它只关心业务逻辑:
 *  接收 name
 *  返回字符串
 * 它不关心:
 *  这个请求是怎么找到自己的
 *  方法参数是怎么绑定的
 *  返回值是怎么写回浏览器的
 *  异常是怎么被处理的
 * 这些都由 DispatcherServlet 统一协调。
 */
@RestController
public class HelloController {


    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "guest") String name) {
        return "Hello " + name + ", DispatcherServlet is Mediator.";
    }

}