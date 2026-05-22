package com.fanjiabao.design.pattern.structural.bridge.source_code_analysis;

import java.sql.*;

/**
 * @author: FanJiaBao
 * @createDate: 2026/5/18 15:33
 * @description: JDBC 桥接模式源码分析
 * <p>
 * 桥接模式核心：
 *  - Abstraction: DriverManager / Connection
 *  - RefinedAbstraction: Statement / PreparedStatement
 *  - Implementor: Driver
 *  - ConcreteImplementor: MySQLDriver / OracleDriver
 * <p>
 * 核心思路：
 *  - DriverManager 统一接口 (Abstraction)
 *  - 各数据库驱动实现 Driver 接口 (Implementor)
 *  - Client 通过桥接调用 Driver 完成具体操作
 */
public class JdbcBridgeFineAnalysis {

    /**
     * JDBC 桥接模式源码分析
     * 桥接模式核心：
     *  - Abstraction: DriverManager
     *      提供统一接口给 Client 获取数据库连接
     *  - RefinedAbstraction: Connection / Statement / ResultSet
     *      提供数据库操作接口，不依赖具体实现
     *  - Implementor: Driver
     *      定义数据库驱动必须实现的 connect() 方法
     *  - ConcreteImplementor: MySQLDriver / OracleDriver
     *      实现 Driver 接口，完成实际数据库连接和对象创建
     *  - Client: main 方法
     *      使用统一接口操作数据库
     * <p>
     * 调用流程
     * 1.获取 JDBC 连接
     *  --> DriverManager.getConnection(url, user, password) (Abstraction)
     *      --> 遍历 registeredDrivers
     *          --> Connection con = aDriver.driver.connect(url, info); ==> DriverManager.java:681
     *              MySQLDriver.connect(...)
     *              解析 URL，创建 Socket/TCP 连接
     *              返回 ConnectionImpl (RefinedAbstraction)
     * 2.创建 Statement
     *  --> ConnectionImpl.createStatement() (RefinedAbstraction)
     *      返回 Statement 接口 (RefinedAbstraction)
     *      底层实现 StatementImpl (ConcreteImplementor)
     * 3.执行 SQL 查询
     *  --> StatementImpl.executeQuery(sql) (RefinedAbstraction)
     *      封装 PreparedStatement，发送 SQL 到数据库
     *      返回 ResultSetImpl (RefinedAbstraction)
     *      Client 不依赖具体实现
     * 4.遍历结果
     *  --> ResultSetImpl.next() (RefinedAbstraction)
     *      游标移动到下一行
     *      --> ResultSetImpl.getString("Host") / getString("User")
     *          获取列数据 (ConcreteImplementor)
     * 5.关闭资源
     *  --> ResultSetImpl.close() / StatementImpl.close() / ConnectionImpl.close()
     *      释放 Socket / 网络资源，清理内部状态
     * <p>
     * 桥接模式体现：
     *  - Client 通过 DriverManager 获取 Connection 接口
     *  - 底层驱动实现可独立变化, 客户端无需修改
     *  - Connection/Statement/ResultSet 是 RefinedAbstraction，Driver 是 Implementor
     *  - MySQLDriver/OracleDriver 是 ConcreteImplementor
     */
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mysql?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "123456"
        );
        System.out.println("连接对象类型: " + conn.getClass());

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT Host, User FROM user");

        while (rs.next()) {
            System.out.println(rs.getString("Host") + " -> " + rs.getString("User"));
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
