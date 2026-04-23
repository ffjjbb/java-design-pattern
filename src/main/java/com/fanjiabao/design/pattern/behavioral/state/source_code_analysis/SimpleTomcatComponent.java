package com.fanjiabao.design.pattern.behavioral.state.source_code_analysis;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.util.LifecycleBase;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/23 16:21
 * @description:
 * 继承 LifecycleBase, 模拟 Tomcat 中 Connector / Context / Service 等组件的生命周期管理方式。
 * Tomcat 的设计思想:
 *  1.父类 LifecycleBase 负责统一状态流转
 *  2.子类负责具体阶段行为
 */
public class SimpleTomcatComponent extends LifecycleBase {

    /**
     * 初始化阶段:
     *  - 加载配置
     *  - 初始化线程池
     *  - 准备网络资源
     *  - 注册 JMX
     */
    @Override
    protected void initInternal() throws LifecycleException {
        System.out.println("initInternal(): 执行组件初始化逻辑...");
    }

    /**
     * 启动阶段:
     *  在 LifecycleBase 的约定中, 子类 startInternal() 里通常要显式调用: setState(LifecycleState.STARTING);
     *  父类会在 start() 执行完后检查, 子类是否正确把状态推进到了 STARTING, 如果没有, 就说明生命周期协议被破坏了。
     */
    @Override
    protected void startInternal() throws LifecycleException {
        System.out.println("startInternal(): 启动组件, 例如启动线程/打开端口/开始提供服务...");
        // 告诉父类: 当前子类已经进入启动中状态
        setState(LifecycleState.STARTING);
        System.out.println("startInternal(): 组件正在运行...");
    }

    /**
     * 停止阶段:
     *  子类通常需要把状态推进到 STOPPING
     */
    @Override
    protected void stopInternal() throws LifecycleException {
        System.out.println("stopInternal(): 停止组件, 例如释放线程/停止服务...");
        // 告诉父类: 当前子类进入停止中状态
        setState(LifecycleState.STOPPING);
        System.out.println("stopInternal(): 组件已停止运行...");
    }

    /**
     * 销毁阶段:
     *  用于彻底释放资源
     */
    @Override
    protected void destroyInternal() throws LifecycleException {
        System.out.println("destroyInternal(): 销毁组件, 释放所有底层资源...");
    }

}
