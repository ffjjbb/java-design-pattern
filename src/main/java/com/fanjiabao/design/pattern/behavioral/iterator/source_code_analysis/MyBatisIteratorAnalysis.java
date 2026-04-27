package com.fanjiabao.design.pattern.behavioral.iterator.source_code_analysis;

import com.fanjiabao.design.pattern.common.MysqlUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/27 15:20
 * @description: MyBatis 中的迭代器模式(ResultSet 遍历)源码分析
 * MyBatis ResultSet 遍历 = JDBC ResultSet 游标 + MyBatis 对象映射 + 结果收集
 * 核心思想:
 *  MyBatis 的 ResultSet 遍历不是直接使用 java.util.Iterator, 而是使用 JDBC ResultSet 这种 "游标式迭代器",
 *  它通过 resultSet.next() 一行一行移动游标, 再通过 TypeHandler 从当前行取值, 最后通过反射/MetaObject 给 JavaBean 属性赋值,
 *  并把每一行对象收集到 List 中
 * 本质:
 *      ResultSet 本身就是一种 "游标式迭代器"
 * 迭代器模式体现:
 *      resultSet.next() 负责判断是否还有下一行, 并移动游标
 *      resultSet.getXxx(...) 负责获取当前行的字段值
 *      MyBatis 负责把当前行字段值封装成 Java 对象
 */
public class MyBatisIteratorAnalysis {


    /**
     * 调用流程:
     *  加载 MyBatis 配置文件
     *      从 classpath 下读取 mybatis-config.xml 配置文件
     *      --> Resources.getResourceAsStream("mybatis-config.xml");
     *  构建 SqlSessionFactory
     *      --> new SqlSessionFactoryBuilder().build(is);
     *          解析 mybatis-config.xml, environments、mappers、typeAliases、plugins 等配置, 最终构建 Configuration 对象
     *          --> XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties); ==> SqlSessionFactoryBuilder.java:79
     *          --> return build(parser.parse()); ==> SqlSessionFactoryBuilder.java:80
     *              --> return new DefaultSqlSessionFactory(config); ==> SqlSessionFactoryBuilder.java:96
     *  打开 SqlSession
     *      --> factory.openSession();
     *          --> return openSessionFromDataSource(configuration.getDefaultExecutorType(), null, false); ==> DefaultSqlSessionFactory.java:90
     *              创建 Executor
     *              --> final Executor executor = configuration.newExecutor(tx, execType); ==> DefaultSqlSessionFactory.java:97
     *              创建 DefaultSqlSession
     *              --> return new DefaultSqlSession(configuration, executor, autoCommit); ==> DefaultSqlSessionFactory.java:98
     *  执行查询
     *      --> sqlSession.selectList("com.fanjiabao.design.pattern.common.UserMapper.selectList", param);
     *          --> DefaultSqlSession.selectList(...) ==> DefaultSqlSession.java:150
     *              根据 statementId 找到 MappedStatement
     *              --> MappedStatement ms = configuration.getMappedStatement(statement); ==> DefaultSqlSession.java:152
     *              MappedStatement 中保存了 SQL、参数映射、结果映射等信息
     *              --> return executor.query(ms, wrapCollection(parameter), rowBounds, handler); ==> DefaultSqlSession.java:154
     *  Executor 执行查询
     *      --> executor.query(...)
     *          --> BaseExecutor.query(...) ==> BaseExecutor.java:133
     *              --> return query(ms, parameter, rowBounds, resultHandler, key, boundSql); ==> BaseExecutor.java:137
     *                  先处理一级缓存
     *                  --> list = resultHandler == null ? (List<E>) localCache.getObject(key) : null;
     *                  如果缓存中没有数据, 执行数据库查询
     *                  --> list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);
     *                      --> list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
     *  创建 StatementHandler 并执行 SQL
     *      --> SimpleExecutor.doQuery(...) ==> SimpleExecutor.java:57
     *          创建 StatementHandler
     *          --> StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, resultHandler, boundSql); ==> SimpleExecutor.java:62
     *          准备 JDBC Statement
     *          --> prepareStatement(handler, ms.getStatementLog()) ==> SimpleExecutor.java:64
     *          执行查询
     *          --> handler.query(stmt, resultHandler) ==> SimpleExecutor.java:65
     *  PreparedStatementHandler 执行 JDBC 查询
     *      --> PreparedStatementHandler.query(...) ==> PreparedStatementHandler.java:63
     *          真正执行 SQL
     *          --> ps.execute(); ==> PreparedStatementHandler.java:65
     *          执行完成后, SQL 结果还在 JDBC ResultSet 中, 接下来交给 ResultSetHandler 处理结果集
     *          --> return resultSetHandler.handleResultSets(ps); ==> PreparedStatementHandler.java:66
     *  处理结果集(重点: 迭代器模式核心)
     *      --> DefaultResultSetHandler.handleResultSets(Statement stmt) ==> DefaultResultSetHandler.java:189
     *          先把 JDBC ResultSet 包装成 ResultSetWrapper
     *          ResultSetWrapper 中保存 ResultSet、列名、JDBC 类型、TypeHandler 等信息
     *          --> ResultSetWrapper rsw = getFirstResultSet(stmt); ==> DefaultResultSetHandler.java:195
     *          然后处理当前 ResultSet
     *          --> handleResultSet(rsw, resultMap, multipleResults, null); ==> DefaultResultSetHandler.java:202
     *              如果是普通 selectList 查询, 最终会进入
     *              --> handleRowValues(rsw, resultMap, resultHandler, rowBounds, null); ==> DefaultResultSetHandler.java:313
     *                  根据是否存在嵌套结果映射, 选择不同处理方式, 普通 resultMap 一般进入 simpleResultMap 路线
     *                  --> handleRowValuesForSimpleResultMap(rsw, resultMap, resultHandler, rowBounds, parentMapping); ==> DefaultResultSetHandler.java:337
     *  ResultSet 一行一行遍历(最核心)
     *      --> handleRowValuesForSimpleResultMap(...) ==> DefaultResultSetHandler.java:359
     *          --> 内部核心逻辑
     *              while (shouldProcessMoreRows(resultContext, rowBounds) && !resultSet.isClosed() && resultSet.next()) { ==> DefaultResultSetHandler.java:364
     *                  ResultMap discriminatedResultMap = resolveDiscriminatedResultMap(resultSet, resultMap, null);
     *                  Object rowValue = getRowValue(rsw, discriminatedResultMap, null);
     *                  storeObject(resultHandler, resultContext, rowValue, parentMapping, resultSet);
     *              }
     *              resultSet.next() 就是迭代器模式的核心
     *                  1.判断是否还有下一行数据
     *                  2.如果有, 把游标移动到下一行
     *  当前行转换成 Java 对象
     *      --> Object rowValue = getRowValue(rsw, discriminatedResultMap, null); ==> DefaultResultSetHandler.java:366
     *          创建结果对象
     *          --> Object rowValue = createResultObject(rsw, resultMap, lazyLoader, columnPrefix); ==> DefaultResultSetHandler.java:411
     *          对普通 JavaBean 来说, 这里会通过反射创建 MysqlUser 对象, 然后给属性赋值
     *          --> foundValues = applyPropertyMappings(rsw, resultMap, metaObject, lazyLoader, columnPrefix) || foundValues; ==> DefaultResultSetHandler.java:418
     *          --> foundValues = lazyLoader.size() > 0 || foundValues; ==> DefaultResultSetHandler.java:419
     *      存储当前行对象
     *      --> storeObject(resultHandler, resultContext, rowValue, parentMapping, resultSet); ==> DefaultResultSetHandler.java:367
     *          --> callResultHandler(resultHandler, resultContext, rowValue); ==> DefaultResultSetHandler.java:376
     *              --> resultContext.nextResultObject(rowValue); ==> DefaultResultSetHandler.java:383
     *              --> ((ResultHandler<Object>) resultHandler).handleResult(resultContext); ==> DefaultResultSetHandler.java:384
     *                  --> list.add(context.getResultObject()); ==> DefaultResultHandler.java:43
     *      每一行 ResultSet --> 转换为 MysqlUser --> 加入 List
     *  返回查询结果
     *      返回 List<MysqlUser>
     *      --> DefaultSqlSession.selectList(...) ==> DefaultSqlSession.java:150
     * <p>
     *  迭代器模式角色对应:
     *      抽象迭代器:
     *          Iterator 思想
     *      具体迭代器:
     *          JDBC ResultSet
     *      hasNext + next:
     *          resultSet.next()
     *      当前元素:
     *          ResultSet 当前行数据
     *      获取当前元素数据:
     *          resultSet.getString(...)
     *          resultSet.getInt(...)
     *          resultSet.getObject(...)
     *      聚合对象:
     *          数据库查询结果集
     *      客户端:
     *          MyBatis 的 DefaultResultSetHandler
     */
    public static void main(String[] args) throws Exception {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = factory.openSession();

        Map<String, Object> param = new HashMap<>();
        param.put("page", 1);
        param.put("size", 10);
        List<MysqlUser> list = sqlSession.selectList("com.fanjiabao.design.pattern.common.UserMapper.selectList", param);

        list.forEach(System.out::println);
        sqlSession.close();
    }
}