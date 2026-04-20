package com.fanjiabao.design.pattern.behavioral.strategy.source_code_analysis;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/20 17:58
 * @description: 自定义拒绝策略
 */
class MyRejectHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("自定义策略: 太忙, 你想怎么办我送你去");
    }

}
