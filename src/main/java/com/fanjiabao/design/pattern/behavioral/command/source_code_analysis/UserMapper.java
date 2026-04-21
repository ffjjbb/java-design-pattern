package com.fanjiabao.design.pattern.behavioral.command.source_code_analysis;

import org.apache.ibatis.annotations.Param;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/21 10:01
 * @description: 描述
 */
public interface UserMapper {

    MysqlUser selectUserByPrimaryKey(@Param("host") String host, @Param("user") String user);

}
