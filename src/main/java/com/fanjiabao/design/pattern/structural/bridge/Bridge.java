package com.fanjiabao.design.pattern.structural.bridge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/16 9:54
 * @description: 结构型模式.桥接模式:
 * 桥接模式将抽象部分与实现部分分离, 使用组合关系代替继承关系, 解耦两个变化维度, 降低了抽象和实现这两个可变维度的耦合度, 使它们可以独立变化。
 * 从而避免类爆炸问题, 适用于存在多个维度变化的系统设计。
 */
public class Bridge {

    /**
     * 桥接(Bridge)模式包含以下主要角色:
     * 抽象化(Abstraction)角色: 定义抽象类, 并包含一个对实现化对象的引用。
     * 扩展抽象化(Refined  Abstraction)角色: 是抽象化角色的子类, 实现父类中的业务方法, 并通过组合关系调用实现化角色中的业务方法。
     * 实现化(Implementor)角色: 定义实现化角色的接口, 供扩展抽象化角色调用。
     * 具体实现化(Concrete Implementor)角色: 给出实现化角色接口的具体实现。
     * <p>
     * 优:
     * 桥接模式提高了系统的可扩充性, 在两个变化维度中任意扩展一个维度, 都不需要修改原有系统。实现细节对客户透明。
     * 如: 如果现在还有一种视频文件类型wmv, 我们只需要再定义一个类实现 VideoFile 接口即可, 其他类不需要发生变化。
     */
    public static void main(String[] args) {
        OperatingSystem system = new Mac(new AviFile());
        system.play("战狼3");
        System.out.println("------------------------");
        usageScenarios();
    }

    /**
     * 使用场景:
     * - 当一个类存在两个独立变化的维度, 且这两个维度都需要进行扩展时。
     * - 当一个系统不希望使用继承或因为多层次继承导致系统类的个数急剧增加时。
     * - 当一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性时。
     * 避免在两个层次之间建立静态的继承联系，通过桥接模式可以使它们在抽象层建立一个关联关系。
     * <p>
     * JDBC 通过定义统一的接口(如Connection,Driver), 并由各数据库厂商提供具体实现, 实现了抽象与实现的分离。
     * DriverManager 作为桥梁, 将 JDBC API 与具体数据库解耦, 这正是桥接模式的典型应用。
     */
    public static void usageScenarios() {
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 获取连接(桥接开始)
            String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
            Connection conn = DriverManager.getConnection(url, "root", "123456");

            // 创建 Statement
            Statement stmt = conn.createStatement();

            // 执行 SQL
            ResultSet rs = stmt.executeQuery("select 9 as num");

            // 处理结果
            while (rs.next()) {
                System.out.println(rs.getInt("num"));
            }

            // 关闭资源
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
