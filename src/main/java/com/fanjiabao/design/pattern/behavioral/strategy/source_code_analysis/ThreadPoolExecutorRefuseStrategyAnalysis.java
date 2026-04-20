package com.fanjiabao.design.pattern.behavioral.strategy.source_code_analysis;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 17:29
 * @description:
 * 线程池在任务拒绝机制中使用了策略模式。
 * RejectedExecutionHandler 定义统一接口, 不同实现类(如 AbortPolicy,CallerRunsPolicy 等)代表不同拒绝策略。
 * 当线程池和队列都满时, ThreadPoolExecutor 会调用具体策略处理任务, 从而实现行为的动态切换。
 */
public class ThreadPoolExecutorRefuseStrategyAnalysis {

    /**
     * 调用流程:
     *  1.new ThreadPoolExecutor(...)
     *      指定策略类
     *      --> this.handler = new MyRejectHandler(); ==> ThreadPoolExecutor.java:1311
     *  2.executor.execute(...)
     *  --> reject(command); ==> ThreadPoolExecutor.java:1365
     *      --> handler.rejectedExecution(command, this); ==> ThreadPoolExecutor.java:833
     *          执行具体的拒绝策略方法
     *          --> MyRejectHandler#rejectedExecution(java.lang.Runnable, java.util.concurrent.ThreadPoolExecutor)
     */
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                1,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                new MyRejectHandler() // 拒绝策略
        );

        for (int i = 0; i < 3; i++) {
            int index = i;
            executor.execute(() -> {
                System.out.println("执行任务: " + index);
                try { Thread.sleep(2000); } catch (Exception e) {}
            });
        }
        executor.shutdown();
    }

}
