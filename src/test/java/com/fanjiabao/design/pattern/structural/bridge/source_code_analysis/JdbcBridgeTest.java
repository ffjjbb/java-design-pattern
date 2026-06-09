package com.fanjiabao.design.pattern.structural.bridge.source_code_analysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JDBC 桥接模式测试
 * 
 * 测试要点:
 * 1. DriverManager 是 Abstraction
 * 2. Driver 是 Implementor
 * 3. Connection/Statement 是 RefinedAbstraction
 */
class JdbcBridgeTest {

    @Test
    @DisplayName("JdbcBridgeFineAnalysis 应有 main 方法")
    void jdbcBridgeFineAnalysisShouldHaveMainMethod() {
        Method main = null;
        try {
            main = JdbcBridgeFineAnalysis.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            try {
                main = JdbcBridgeFineAnalysis.class.getMethod("main", String[].class);
            } catch (NoSuchMethodException ignored) {
            }
        }
        assertThat(main)
                .as("JdbcBridgeFineAnalysis 应有 main 方法")
                .isNotNull();
    }

    @Test
    @DisplayName("Driver 接口应存在")
    void driverInterfaceShouldExist() {
        assertThat(Driver.class)
                .as("Driver 接口应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("Driver 应有 connect 方法")
    void driverShouldHaveConnectMethod() {
        boolean hasConnect = false;
        for (Method method : Driver.class.getMethods()) {
            if (method.getName().equals("connect")) {
                hasConnect = true;
                break;
            }
        }
        assertThat(hasConnect)
                .as("Driver 应有 connect 方法")
                .isTrue();
    }

    @Test
    @DisplayName("DriverManager 应有 getConnection 方法")
    void driverManagerShouldHaveGetConnectionMethod() {
        boolean hasGetConnection = false;
        for (Method method : DriverManager.class.getMethods()) {
            if (method.getName().equals("getConnection")) {
                hasGetConnection = true;
                break;
            }
        }
        assertThat(hasGetConnection)
                .as("DriverManager 应有 getConnection 方法")
                .isTrue();
    }

    @Test
    @DisplayName("Connection 接口应存在")
    void connectionInterfaceShouldExist() {
        assertThat(Connection.class)
                .as("Connection 接口应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("Connection 应有 createStatement 方法")
    void connectionShouldHaveCreateStatementMethod() {
        boolean hasCreateStatement = false;
        for (Method method : Connection.class.getMethods()) {
            if (method.getName().equals("createStatement")) {
                hasCreateStatement = true;
                break;
            }
        }
        assertThat(hasCreateStatement)
                .as("Connection 应有 createStatement 方法")
                .isTrue();
    }

    @Test
    @DisplayName("Statement 接口应存在")
    void statementInterfaceShouldExist() {
        assertThat(Statement.class)
                .as("Statement 接口应存在")
                .isNotNull();
    }

    @Test
    @DisplayName("Statement 应有 executeQuery 方法")
    void statementShouldHaveExecuteQueryMethod() {
        boolean hasExecuteQuery = false;
        for (Method method : Statement.class.getMethods()) {
            if (method.getName().equals("executeQuery")) {
                hasExecuteQuery = true;
                break;
            }
        }
        assertThat(hasExecuteQuery)
                .as("Statement 应有 executeQuery 方法")
                .isTrue();
    }

    @Test
    @DisplayName("桥接模式: DriverManager 获取 Connection 应返回正确类型")
    void driverManagerShouldReturnConnectionType() {
        // 验证 getConnection 方法返回 Connection 类型
        Method getConnection = null;
        for (Method method : DriverManager.class.getMethods()) {
            if (method.getName().equals("getConnection") && method.getParameterCount() >= 1) {
                getConnection = method;
                break;
            }
        }
        assertThat(getConnection)
                .as("getConnection 方法应存在")
                .isNotNull();
        assertThat(getConnection.getReturnType())
                .as("getConnection 应返回 Connection 类型")
                .isEqualTo(Connection.class);
    }
}
