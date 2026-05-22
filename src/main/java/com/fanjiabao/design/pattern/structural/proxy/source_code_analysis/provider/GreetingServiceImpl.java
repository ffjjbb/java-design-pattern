package com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.provider;

import com.fanjiabao.design.pattern.structural.proxy.source_code_analysis.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author: FanJiaBao
 * @createDate: 2026/5/18 16:13
 * @description: 服务实现
 */
@DubboService(version = "1.0.0", interfaceClass = GreetingService.class)
public class GreetingServiceImpl implements GreetingService {


    /**
     * Dubbo 服务匹配流程分析:
     * 1.服务注册阶段
     *      --> DubboProviderApplication 启动
     *          --> @EnableDubbo(scanBasePackages = "provider") 扫描 @DubboService
     *          --> ServiceBean<GreetingService> 初始化
     *          --> ServiceConfig.export()
     *              方法作用: 发布服务到本地端口(20880)
     *              --> 暴露协议: DubboProtocol.export()
     *                  -> 创建 Exporter 对象
     *                  -> NettyServer.listen(20880)
     *          --> 如果使用注册中心, 还会注册接口名 + version 到注册中心
     * 2. 客户端调用阶段
     *      --> greetingService.say("Hello")
     *          -> Proxy.invoke() / CGLIB Proxy.invoke()
     *              -> InvokerInvocationHandler.invoke(proxy, method, args)
     *                  方法作用: 拦截方法, 封装 RpcInvocation
     *                  -> DubboInvoker.invoke(RpcInvocation)
     *                      方法作用: 找到目标 Provider 实例
     *                      1) 如果配置 url="dubbo://127.0.0.1:20880"
     *                          -> 直接调用该地址对应的 NettyClient
     *                      2) 如果使用注册中心
     *                          -> 根据接口名 + version 查询可用 Provider
     *                          -> 负载均衡选择 Provider
     *                      3) 构建 RPC 请求
     *                          -> 序列化参数 (fastjson2)
     *                          -> 通过网络发送请求
     * 3. Provider 接收请求
     *      --> NettyServer.receive()
     *      --> DubboProtocol.requestHandler()
     *          -> DubboInvoker.invoke()
     *              -> Exporter 对象查找实际服务实现
     *                  -> 根据 interfaceName + version 匹配到 GreetingServiceImpl
     *              -> 调用 GreetingServiceImpl.say("Hello")
     * 4. 返回结果
     *      --> Provider 返回值序列化
     *      --> NettyClient 解包
     *      --> Proxy.invoke() 返回结果给客户端
     * 5. 完整调用链
     *      Client: DubboConsumerApplication
     *          -> greetingService.say("Hello")
     *              -> Proxy.invoke()
     *                  -> InvokerInvocationHandler.invoke()
     *                      -> DubboInvoker.invoke()
     *                          -> NettyClient.send()
     *                              -> Provider: NettyServer.receive()
     *                                  -> DubboInvoker.invoke()
     *                                      -> Exporter 查找 GreetingServiceImpl
     *                                      -> GreetingServiceImpl.say("Hello")
     *                                  <- 返回结果
     *                              <- Proxy 返回结果
     *          <- scheduledCall() 输出
     * 总结:
     *      Dubbo 通过 interfaceName + version + URL 或注册中心信息, 定位到 Provider 暴露的 Exporter,
     *      然后调用实际实现 GreetingServiceImpl。代理对象负责将方法调用映射到远程实现。
     */
    @Override
    public String say(String name) {
        System.out.println("GreetingServiceImpl.say() 执行实际业务");
        return "它说 " + name;
    }

}
