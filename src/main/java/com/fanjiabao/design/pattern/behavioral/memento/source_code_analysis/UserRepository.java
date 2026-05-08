package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 16:33
 * @description: Repository
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}