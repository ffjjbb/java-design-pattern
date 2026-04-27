package com.fanjiabao.design.pattern.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/21 10:01
 * @description: 描述
 */
public interface UserMapper {

    MysqlUser selectUserByPrimaryKey(@Param("host") String host, @Param("user") String user);

    List<MysqlUser> selectList(@Param("page") String page, @Param("size") String size);

}
