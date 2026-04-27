package com.fanjiabao.design.pattern.behavioral.command.source_code_analysis;

import com.fanjiabao.design.pattern.common.MysqlUser;
import com.fanjiabao.design.pattern.common.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/21 9:58
 * @description:
 * MyBatisExecutor 的命令模式:
 *  将一次 Mapper 方法调用封装为命令(MapperMethod), 命令的具体内容由 MappedStatement 描述(SQL + 参数映射 + 结果映射),
 *  通过 SqlSession 作为调度中心, 统一交由 Executor 执行, 最终由 StatementHandler 转换为 JDBC 调用完成 SQL 执行。
 *  该设计实现了调用者(MapperProxy)与执行者(Executor)的彻底解耦, 并为缓存, 插件机制, 事务管理等扩展提供了统一入口。
 */
public class MyBatisExecutorCommandAnalysis {

    /**
     * 调用流程:
     *  1.构建 SqlSessionFactory
     *      --> new SqlSessionFactoryBuilder().build(...)
     *          --> var5 = this.build(parser.parse()); ==> SqlSessionFactoryBuilder.class:68
     *              拆解上面一行
     *              --> parser.parse() = XMLConfigBuilder.parse() --> Configuration 初始化(MappedStatement 在这里被解析并存入 Map)
     *              --> this.build(...)
     *                  --> return new DefaultSqlSessionFactory(config); ==> SqlSessionFactoryBuilder.class:87
     *  2.获取 SqlSession
     *      --> factory.openSession() --> DefaultSqlSessionFactory.openSession() ==> DefaultSqlSessionFactory.class:31
     *          --> this.configuration.newExecutor(tx, execType); ==> DefaultSqlSessionFactory.class:74
     *              创建 Executor(SimpleExecutor)
     *              --> executor = new SimpleExecutor(this, transaction);
     *          --> var8 = new DefaultSqlSession(this.configuration, executor, autoCommit); ==> DefaultSqlSessionFactory.class:75
     *  3.获取 Mapper 代理
     *      --> session.getMapper(UserMapper.class); = DefaultSqlSession.getMapper() ==> DefaultSqlSession.class:259
     *          --> return (T)this.configuration.getMapper(type, this); ==> DefaultSqlSession.class:260
     *              --> return (T)this.mapperRegistry.getMapper(type, sqlSession); ==> Configuration.class:817
     *                  --> return (T)mapperProxyFactory.newInstance(sqlSession); ==> MapperRegistry.class:31
     *                      --> MapperProxy<T> mapperProxy = new MapperProxy(sqlSession, this.mapperInterface, this.methodCache); ==> MapperProxyFactory.class:35
     *                      --> return (T)this.newInstance(mapperProxy); ==> MapperProxyFactory.class:36
     *                          生成 MapperProxy（JDK动态代理）
     *                          --> return (T)Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface}, mapperProxy); ==> MapperProxyFactory.class:31
     *  4.调用 Mapper 方法
     *      --> mapper.selectUserByPrimaryKey("localhost", "root");
     *          调用jdk代理对象的方法
     *          --> $Proxy4.selectUserByPrimaryKey(...) ==> $Proxy4.java:19
     *              --> return (MysqlUser)this.h.invoke(this, m3, new Object[]{string, string2}); ==> $Proxy4.java:21
     *                  this.h 就是第三步生成的 mapperProxy
     *                  --> this.h.invoke(...) = MapperProxy.invoke(Object proxy, Method method, Object[] args) ==> MapperProxy.java:42
     *                      --> return this.cachedInvoker(method).invoke(proxy, method, args, this.sqlSession); ==> MapperProxy.java:50
     *                          拆解上面一行
     *                          --> this.cachedInvoker(method) = return new PlainMethodInvoker(new MapperMethod(this.mapperInterface, method, this.sqlSession.getConfiguration())); ==> MapperProxy.java:103
     *                          --> .invoke(proxy, method, args, this.sqlSession); =  PlainMethodInvoker.invoke(...) ==> MapperProxy.java:149
     *  5.构造命令并分发
     *      --> return this.mapperMethod.execute(sqlSession, args);  ==> MapperProxy.java:150
     *          根据 Mapper 方法类型调用 SqlSession 对应方法
     *          --> MapperMethod.execute(...) ==> MapperProxy.java:150
     *              --> case SELECT: if(...) ... else{...} MapperMethod.class:65
     *                  参数转换
     *                  --> Object param = this.method.convertArgsToSqlCommandParam(args); ==> MapperMethod.class:66
     *                  交给 sqlSession
     *                  --> result = sqlSession.selectOne(this.command.getName(), param); ==> MapperMethod.class:67
     *  6.SqlSession 调度命令
     *      --> DefaultSqlSession.selectOne(...) ==> DefaultSqlSession.class:53
     *          --> return this.<E>selectList(statement, parameter, rowBounds, Executor.NO_RESULT_HANDLER); ==> DefaultSqlSession.class:119
     *              获取命令对象
     *              --> MappedStatement ms = this.configuration.getMappedStatement(statement); ==> DefaultSqlSession.class:125
     *              调用 Executor（命令执行者）
     *              --> var6 = this.executor.query(ms, this.wrapCollection(parameter), rowBounds, handler); ==> DefaultSqlSession.class:127
     *  7.Executor 执行命令
     *      --> BaseExecutor.query(...) ==> BaseExecutor.class:111
     *          解析 SQL
     *          --> BoundSql boundSql = ms.getBoundSql(parameter); ==> BaseExecutor.class:112
     *          创建缓存key
     *          --> CacheKey key = this.createCacheKey(ms, parameter, rowBounds, boundSql); ==> BaseExecutor.class:113
     *              --> return this.<E>query(ms, parameter, rowBounds, resultHandler, key, boundSql); ==> BaseExecutor.class:114
     *                  查询一级缓存
     *                  --> list = resultHandler == null ? (List)this.localCache.getObject(key) : null; ==> BaseExecutor.class:129
     *                  从数据库查询
     *                  --> list = this.<E>queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql); ==> BaseExecutor.class:133
     *  8.真正执行 SQL
     *      --> list = this.<E>doQuery(ms, parameter, rowBounds, resultHandler, boundSql); ==> BaseExecutor.class:302
     *          创建 StatementHandler
     *          --> StatementHandler handler = configuration.newStatementHandler(this.wrapper, ms, parameter, rowBounds, resultHandler, boundSql); ==> SimpleExecutor.class:50
     *              获取连接
     *              --> stmt = this.prepareStatement(handler, ms.getStatementLog()); ==> SimpleExecutor.class:51
     *              执行 SQL
     *              --> var9 = handler.query(stmt, resultHandler); ==> SimpleExecutor.class:52
     *                  构造函数决定了执行者
     *                  --> this.delegate = new PreparedStatementHandler(executor, ms, parameter, rowBounds, resultHandler, boundSql); ==> RoutingStatementHandler.class:30
     *                  --> PreparedStatementHandler.query(Statement statement, ResultHandler resultHandler) ==> PreparedStatementHandler.class:43
     *                      真正执行 SQL
     *                      --> ps.execute(); ==> PreparedStatementHandler.class:45
     *                      处理结果集
     *                      -->  return this.resultSetHandler.handleResultSets(ps); ==> PreparedStatementHandler.class:46
     * <p>
     * 命令模式总结:
     *  Command(命令):
     *      MappedStatement(SQL + 参数 + 映射)
     *  Invoker(调用者):
     *      MapperProxy(JDK动态代理)
     *  Receiver(接收者):
     *      Executor(真正执行SQL)
     *  Client(客户端):
     *      main()
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // 构建 SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));

        // 获取 SqlSession(调用者)
        try (SqlSession session = factory.openSession()) {
            // 获取 Mapper代理
            UserMapper mapper = session.getMapper(UserMapper.class);
            // 生成的 mapper 代理对象为 ./$Proxy4.java, $Proxy4 中的 this.h 属性是 ./MapperProxy.java
            System.out.println(mapper.getClass());
            // 执行方法(触发命令)
            MysqlUser u = mapper.selectUserByPrimaryKey("localhost", "root");
            System.out.println(u != null ? u.getUser() : "用户不存在");
        }

        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }

}
