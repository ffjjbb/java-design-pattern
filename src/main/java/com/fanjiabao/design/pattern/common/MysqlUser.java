package com.fanjiabao.design.pattern.common;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/21 10:02
 * @description: MySQL 系统库 mysql.user 表对应的实体类
 */
@Data
public class MysqlUser {

    /**
     * 主机（允许登录的IP/主机名）
     */
    private String host;

    /**
     * 用户名
     */
    private String user;

    /**
     * 查询权限 Y/N
     */
    private String selectPriv;

    /**
     * 插入权限
     */
    private String insertPriv;

    /**
     * 更新权限
     */
    private String updatePriv;

    /**
     * 删除权限
     */
    private String deletePriv;

    /**
     * 创建库/表权限
     */
    private String createPriv;

    /**
     * 删除库/表权限
     */
    private String dropPriv;

    /**
     * 重载权限（FLUSH 操作）
     */
    private String reloadPriv;

    /**
     * 关闭 MySQL 权限
     */
    private String shutdownPriv;

    /**
     * 查看进程权限
     */
    private String processPriv;

    /**
     * 文件操作权限
     */
    private String filePriv;

    /**
     * 授权权限
     */
    private String grantPriv;

    /**
     * 引用权限
     */
    private String referencesPriv;

    /**
     * 索引权限
     */
    private String indexPriv;

    /**
     * 修改表结构权限
     */
    private String alterPriv;

    /**
     * 显示数据库权限
     */
    private String showDbPriv;

    /**
     * 超级权限
     */
    private String superPriv;

    /**
     * 创建临时表权限
     */
    private String createTmpTablePriv;

    /**
     * 锁表权限
     */
    private String lockTablesPriv;

    /**
     * 执行存储过程权限
     */
    private String executePriv;

    /**
     * 复制从库权限
     */
    private String replSlavePriv;

    /**
     * 复制客户端权限
     */
    private String replClientPriv;

    /**
     * 创建视图权限
     */
    private String createViewPriv;

    /**
     * 查看视图权限
     */
    private String showViewPriv;

    /**
     * 创建存储过程权限
     */
    private String createRoutinePriv;

    /**
     * 修改存储过程权限
     */
    private String alterRoutinePriv;

    /**
     * 创建用户权限
     */
    private String createUserPriv;

    /**
     * 事件调度权限
     */
    private String eventPriv;

    /**
     * 触发器权限
     */
    private String triggerPriv;

    /**
     * 创建表空间权限
     */
    private String createTablespacePriv;

    /**
     * SSL 类型
     */
    private String sslType;

    /**
     * SSL 密码
     */
    private byte[] sslCipher;

    /**
     * X509 签发者
     */
    private byte[] x509Issuer;

    /**
     * X509 主题
     */
    private byte[] x509Subject;

    /**
     * 每小时最大查询数
     */
    private Integer maxQuestions;

    /**
     * 每小时最大更新数
     */
    private Integer maxUpdates;

    /**
     * 每小时最大连接数
     */
    private Integer maxConnections;

    /**
     * 最大用户连接数
     */
    private Integer maxUserConnections;

    /**
     * 认证插件
     */
    private String plugin;

    /**
     * 认证字符串（密码加密串）
     */
    private String authenticationString;

    /**
     * 密码是否已过期
     */
    private String passwordExpired;

    /**
     * 密码最后修改时间
     */
    private Timestamp passwordLastChanged;

    /**
     * 密码有效期（天数）
     */
    private Integer passwordLifetime;

    /**
     * 账户是否锁定
     */
    private String accountLocked;

    /**
     * 创建角色权限
     */
    private String createRolePriv;

    /**
     * 删除角色权限
     */
    private String dropRolePriv;

    /**
     * 密码重用历史次数
     */
    private Integer passwordReuseHistory;

    /**
     * 密码重用时间
     */
    private Integer passwordReuseTime;

    /**
     * 密码是否需要输入当前密码
     */
    private String passwordRequireCurrent;

    /**
     * 用户扩展属性（JSON）
     */
    private String userAttributes;
}
