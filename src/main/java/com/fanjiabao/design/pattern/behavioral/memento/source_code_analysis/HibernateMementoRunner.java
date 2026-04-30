package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 16:43
 * @description: 描述
 */
@Component
public class HibernateMementoRunner implements CommandLineRunner {

    private final HibernateMementoService mementoService;

    public HibernateMementoRunner(HibernateMementoService mementoService) {
        this.mementoService = mementoService;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n================= START =================");
        Long userId = mementoService.createUser();
        mementoService.updateUserWithoutSave(userId);
        mementoService.verifyUser(userId);
        System.out.println("================= END =================\n");
    }

}
