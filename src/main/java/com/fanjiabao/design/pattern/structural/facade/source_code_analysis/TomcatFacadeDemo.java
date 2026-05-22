package com.fanjiabao.design.pattern.structural.facade.source_code_analysis;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/16 17:15
 * @description: 解析 RequestFacade: 对外提供安全接口, 同时隐藏并保护底层 Request 对象
 */
public class TomcatFacadeDemo extends HttpServlet {

    /**
     * 在 Tomcat 中, 请求对象的真实实现是 Request, 但不会直接暴露给开发者, 而是通过 RequestFacade 进行包装。
     * RequestFacade 实现了 HttpServletRequest 接口, 并持有 Request 对象, 将所有方法委托给 Request 执行。
     * 这种设计的核心目的不是简化调用, 而是:
     *  隐藏底层复杂实现
     *  防止开发者操作容器内部对象
     *  保证服务器安全性和稳定性
     * 因此, RequestFacade 是外观模式在 Tomcat 中的典型应用。
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();
        // 打印真实类型
        Class<?> clazz = req.getClass();
        out.println("request实际类型: " + clazz.getName() + "<br/>");
        // 验证是否是 RequestFacade
        boolean isFacade = clazz.getName().contains("RequestFacade");
        out.println("是否是Facade对象: " + isFacade + "<br/>");

        // 反射查看内部结构
        try {
            Field field = clazz.getDeclaredField("request");
            field.setAccessible(true);
            Object innerRequest = field.get(req);
            out.println("内部真实对象: " + innerRequest.getClass().getName() + "<br/>");
        } catch (Exception e) {
            out.println("无法访问内部request<br/>");
        }

        // 方法调用(委托)
        String name = req.getParameter("name");
        out.println("参数 name: " + name + "<br/>");

        out.println("<br/>结论: 调用的是Facade, 但实际执行的是内部Request逻辑");
    }

    /**
     * ===== 响应输出 =====
     * request实际类型: org.apache.catalina.connector.RequestFacade<br/>
     * 是否是Facade对象: true<br/>
     * 内部真实对象: org.apache.catalina.connector.Request<br/>
     * 参数 name: fanjiabao<br/>
     * <br/>结论: 调用的是Facade, 但实际执行的是内部Request逻辑
     */
    public static void main(String[] args) throws LifecycleException, InterruptedException, IOException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(60000);
        // 初始化
        tomcat.getConnector();
        // 创建 Context(Web应用)
        Context context = tomcat.addContext("", null);
        // 添加 Servlet 到 Context
        Tomcat.addServlet(context, "facadeDemo", new TomcatFacadeDemo());
        // 映射路径
        context.addServletMappingDecoded("/demo", "facadeDemo");
        tomcat.start();
        System.out.println("启动成功: http://localhost:60000/demo?name=fanjiabao");

        // 发送请求
        Thread.sleep(300);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) new java.net
                .URL("http://localhost:60000/demo?name=fanjiabao")
                .openConnection();
        conn.setRequestMethod("GET");
        // 读取响应
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
        String line;
        System.out.println("===== 响应输出 =====");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
        tomcat.getServer().await();
    }

}
