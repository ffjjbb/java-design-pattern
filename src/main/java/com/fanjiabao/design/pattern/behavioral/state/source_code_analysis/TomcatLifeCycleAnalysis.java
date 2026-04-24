package com.fanjiabao.design.pattern.behavioral.state.source_code_analysis;

import org.apache.catalina.LifecycleException;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/23 16:16
 * @description: Tomcat 生命周期中的状态模式:
 *  1.Tomcat 使用 LifecycleState 枚举定义组件状态
 *  2.使用 LifecycleBase 统一管理状态流转
 *  3.子类只实现具体阶段行为(init/start/stop/destroy)
 *  4.组件当前状态不同, 能执行的动作不同
 *  5.非法状态切换会抛 LifecycleException
 * <p>
 * Tomcat 生命周期是用 "状态" 控制 "行为", 正是状态模式的核心思想。同时, LifecycleBase 又固定了生命周期执行骨架,
 * 子类通过实现模板方法参与各阶段逻辑, 所以这里也是状态模式 + 模板方法模式的组合使用。
 */
public class TomcatLifeCycleAnalysis {

    /**
     * 1.执行初始化:
     *  --> component.init();
     *      判断当前状态是否必须是 NEW, 如果不是 NEW, 说明生命周期顺序错误, 直接抛异常
     *      --> !this.state.equals(LifecycleState.NEW) ==> LifecycleBase.java:121
     *          --> throw new LifecycleException(msg); ==> LifecycleBase.java:409
     *      将状态从 NEW -> INITIALIZING
     *      --> setStateInternal(LifecycleState.INITIALIZING, null, false); ==> LifecycleBase.java:126
     *      调用子类模板方法
     *      --> initInternal(); ==> LifecycleBase.java:127
     *      将状态推进为 INITIALIZED
     *      --> setStateInternal(LifecycleState.INITIALIZED, null, false); ==> LifecycleBase.java:128
     * 2.执行启动:
     *  -->component.start();
     *      判断当前状态
     *      --> state.equals(LifecycleState.NEW) ==> LifecycleBase.java:160
     *          如果是 NEW, 会先自动调用 init() ==> LifecycleBase.java:161
     *          --> init();
     *      --> state.equals(LifecycleState.FAILED)
     *          如果是 FAILED, 先尝试 stop()
     *          --> stop();
     *      --> !state.equals(LifecycleState.INITIALIZED) && !state.equals(LifecycleState.STOPPED)
     *          如果已经是 STARTING_PREP / STARTING / STARTED, 直接忽略或记录日志
     *          --> invalidTransition(BEFORE_START_EVENT);
     *              --> throw new LifecycleException(msg); ==> LifecycleBase.java:409
     *      将状态推进到启动准备阶段
     *      --> setStateInternal(LifecycleState.STARTING_PREP, null, false); ==> LifecycleBase.java:170
     *      调用子类模板方法
     *      --> startInternal(); ==> LifecycleBase.java:171
     *          在子类中通常要执行, 表示子类已经真正开始启动
     *          --> setState(LifecycleState.STARTING); ==> SimpleTomcatComponent.java:62
     *      父类检查当前状态是否为 STARTING, 如果不是, 说明子类没有遵守生命周期协议, 则抛异常
     *      --> !state.equals(LifecycleState.STARTING) ==> LifecycleBase.java:176
     *          --> invalidTransition(AFTER_START_EVENT); ==> LifecycleBase.java:179
     *              --> throw new LifecycleException(msg);
     *      最终推进到 STARTED
     *      --> setStateInternal(LifecycleState.STARTED, null, false);
     * 3.执行停止:
     *  --> component.stop();
     *      判断当前状态是否允许 stop
     *      --> LifecycleState.STOPPING_PREP.equals(state) || LifecycleState.STOPPING.equals(state) || LifecycleState.STOPPED.equals(state) ==> LifecycleBase.java:210
     *      如果是新建状态, 将状态修改为停止, 然后退出
     *      --> state.equals(LifecycleState.NEW)
     *          --> state = LifecycleState.STOPPED;
     *          --> return;
     *      状态推进为停止准备
     *      --> setStateInternal(LifecycleState.STOPPING_PREP, null, false); ==> LifecycleBase.java:239
     *      调用子类模板方法
     *      --> stopInternal();
     *          子类内部
     *          --> setState(LifecycleState.STOPPING); ==> SimpleTomcatComponent.java:81
     *      父类检查 stopInternal() 后状态是否合法
     *      --> !state.equals(LifecycleState.STOPPING) && !state.equals(LifecycleState.FAILED)
     *      最终推进到 STOPPED
     *      --> setStateInternal(LifecycleState.STOPPED, null, false);
     * 4.执行销毁:
     *  --> component.destroy();
     *      判断当前状态是否允许 destroy, 如果组件还在运行, 一般要先 stop
     *      --> LifecycleState.FAILED.equals(state)
     *      --> stop();
     *      推进到销毁中
     *      --> setStateInternal(LifecycleState.DESTROYING, null, false);
     *      调用子类模板方法
     *      --> destroyInternal(); ==> LifecycleBase.java:306
     *      最终变为 DESTROYED
     *      --> setStateInternal(LifecycleState.DESTROYED, null, false);
     */
    public static void main(String[] args) throws LifecycleException {
        SimpleTomcatComponent component = new SimpleTomcatComponent();
        System.out.println("初始状态: " + component.getState());

        System.out.println("\n========== init() ==========");
        component.init();
        System.out.println("当前状态: " + component.getState());

        System.out.println("\n========== start() ==========");
        component.start();
        System.out.println("当前状态: " + component.getState());

        System.out.println("\n========== stop() ==========");
        component.stop();
        System.out.println("当前状态: " + component.getState());

        System.out.println("\n========== destroy() ==========");
        component.destroy();
        System.out.println("当前状态: " + component.getState());

        // 演示非法状态流转
        System.out.println("\n========== destroy 后再 start() ==========");
        component.start();
    }

}
