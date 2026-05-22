package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.consumer;

import com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author: FanJiaBao
 * @createDate: 2026/5/18 16:15
 * @description: 消费者
 * greetingService 是 Dubbo 动态代理对象:
 *  - 客户端调用接口, 实际由代理对象拦截
 *  - 代理封装 RPC 调用, 负载均衡, 网络传输
 *  - 服务端执行实际实现类业务逻辑
 */
@SpringBootApplication(scanBasePackages = {
        "com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.consumer",
        "com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.api"
})
@EnableScheduling
public class DubboConsumerApplication implements CommandLineRunner {

    @DubboReference(
            version = "1.0.0",
            url = "dubbo://127.0.0.1:20880",
            interfaceClass = GreetingService.class
    )
    private GreetingService greetingService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DubboConsumerApplication.class);
        app.setAdditionalProfiles("consumer");
        app.run(args);
    }


    /**
     * Dubbo 代理对象生成流程分析
     * 1.Spring 容器初始化
     *      --> new AnnotationConfigApplicationContext(DubboProxyCreationAnalysis.class)
     *          初始化 BeanFactory，注册所有 BeanDefinition
     *          --> AbstractApplicationContext.refresh()
     *              调用 prepareRefresh() 初始化容器基础设施
     *              --> obtainFreshBeanFactory() 获取 DefaultListableBeanFactory
     *              --> prepareBeanFactory(beanFactory) 注册基础能力(如 ClassLoader, EL解析器)
     * 2.扫描 @DubboReference 注解
     *      --> ReferenceAnnotationBeanPostProcessor.postProcessProperties(bean, beanName)
     *          检测到 greetingService 字段标注 @DubboReference
     *          --> 调用 ReferenceBean.getObject()
     * 3.创建代理对象
     *      方法作用:
     *          - 初始化 InvokerProxy
     *          - 根据接口生成动态代理对象（JDK Proxy 或 CGLIB）
     *      内部调用:
     *          --> Proxy.newProxyInstance(
     *                  interfaceClass.getClassLoader(),
     *                  new Class[]{GreetingService.class},
     *                  new InvokerInvocationHandler(referenceBean)
     *              )
     *      注意:
     *          - 代理对象封装 RPC 调用逻辑
     *          - 客户端调用接口时, 会进入代理对象拦截器
     * 4.注入到 Bean
     *      --> Spring 完成 bean 初始化后，将代理对象赋值给 greetingService
     *      --> 客户端获取到的 greetingService 是代理对象
     * 5. 调用流程（客户端 -> 代理 -> Provider -> 实现类）
     *      Client 调用:
     *          greetingService.say("Hello")
     *      代理调用链:
     *          --> JDK Proxy.invoke() / CGLIB Proxy.invoke()
     *          --> InvokerInvocationHandler.invoke(proxy, method, args)
     *              封装 RpcInvocation(method, args, interfaceName, version)
     *              --> DubboInvoker.invoke(RpcInvocation)
     *                  选择 Provider 实例 (负载均衡)
     *                  进行序列化 (fastjson2)
     *                  通过网络发送请求
     *          --> Provider: NettyServer.receive() -> DubboProtocol.requestHandler()
     *              --> DubboInvoker.invoke() 找到 ServiceConfig
     *                  --> GreetingServiceImpl.say("Hello") 执行业务
     *                  --> 返回结果
     *          --> Proxy 解包返回值
     *          --> Client 获取结果，打印输出
     * 6. 模式体现
     *      - Client: DubboConsumerApplication
     *      - Proxy: greetingService 动态代理对象
     *      - Invoker: InvokerInvocationHandler / DubboInvoker
     *      - Receiver: GreetingServiceImpl
     *      - 特性:
     *          - 客户端无需感知远程调用
     *          - 透明 RPC，解耦
     *          - 代理封装网络、序列化、负载均衡、容错
     * 7. 总结
     *      Dubbo 代理对象生成时机:
     *          - Spring Bean 初始化阶段
     *          - ReferenceAnnotationBeanPostProcessor 发现 @DubboReference
     *          - ReferenceBean.getObject() 创建 JDK/CGLIB Proxy
     *      代理对象用于拦截方法调用并触发远程 RPC
     */
    @Override
    public void run(String... args) {
        System.out.println("Consumer 启动完成, 定时调用服务...");
    }


    /**
     * 调用流程分析:
     * 1.Spring Scheduler 定时触发
     *      --> ThreadPoolTaskScheduler.schedule(Runnable, trigger)
     *      --> ScheduledAnnotationBeanPostProcessor.postProcessAfterInitialization(bean, beanName)
     *          注册 scheduledCall() 为定时任务 Runnable
     *      --> 每隔 5000ms 调用 ScheduledMethodRunnable.run()
     * 2.调用目标方法 scheduledCall()
     *      --> DubboConsumerApplication.scheduledCall()
     *          方法体:
     *              String result = greetingService.say("Hello World");
     *              System.out.println("定时调用结果: " + result);
     * 3.greetingService.say() 进入 Dubbo 动态代理
     *      --> JDK Proxy.invoke()
     *          方法作用: 拦截接口方法调用
     *          --> InvokerInvocationHandler.invoke(proxy, method, args)
     *              封装 RpcInvocation 对象(method, args, interfaceName, version)
     *              --> 调用 DubboInvoker.invoke(RpcInvocation)
     * 4. DubboInvoker.invoke() 处理 RPC 调用
     *      --> 选择 Provider 实例 (负载均衡)
     *      --> 请求序列化(fastjson2)
     *      --> 通过 NettyClient.send() 发送到 Provider
     * 5. Provider 端接收请求
     *      --> NettyServer.receive()
     *      --> DubboProtocol.requestHandler()
     *      --> DubboInvoker.invoke()
     *          找到对应的 ServiceConfig
     *      --> 调用目标实现类 GreetingServiceImpl.say("Hello World")
     *          执行实际业务逻辑
     *          --> System.out.println("GreetingServiceImpl.say() 执行实际业务");
     *          --> return "它说 Hello World"
     * 6. 返回结果到客户端
     *      --> 网络返回
     *      --> DubboInvoker.invoke() -> Proxy.invoke() -> scheduledCall()
     *      --> 输出 System.out.println("定时调用结果: " + result)
     */
    @Scheduled(fixedRate = 5000)
    public void scheduledCall() {
        System.out.println("代理对象: " + greetingService.getClass());
        // 代理对象如 ./GreetingServiceDubboProxy0.txt
        String result = greetingService.say("Hello World");
        System.out.println("定时调用结果: " + result);
        System.out.println("----------------------------------------");
    }

}