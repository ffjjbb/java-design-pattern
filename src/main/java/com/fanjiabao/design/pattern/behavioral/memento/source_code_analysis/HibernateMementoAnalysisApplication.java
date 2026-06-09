package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 16:34
 * @description: Hibernate / JPA 脏检查中的备忘录模式源码分析
 *  Hibernate 脏检查最像备忘录模式的地方不是 update SQL, 而是查询 Entity 时保存了 EntityEntry.loadedState。
 *      查询时:
 *          loadedState = ["fanjiabao", 24]
 *      修改后:
 *          values = ["fanjiabao-update", 20]
 *      提交事务时:
 *          Hibernate 通过 DefaultFlushEntityEventListener.dirtyCheck(...), 使用 values 和 loadedState 对比。
 *      如果不同:
 *          说明对象被修改过,
 *          然后封装 EntityUpdateAction,
 *          最终执行 update SQL。
 *  所以即使不调用 save(), 只要 Entity 是托管状态, Hibernate 也能通过 loadedState 这个 "备忘录", 动发现对象变化并更新数据库。
 */
@SpringBootApplication(excludeName = {
        "org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration"
})
public class HibernateMementoAnalysisApplication {


    /**
     * 调用流程:
     *  启动 Spring Boot 容器
     *      --> ConfigurableApplicationContext context = SpringApplication.run(HibernateMementoAnalysisApplication.class, args);
     *      创建 SpringApplication
     *      --> new SpringApplication(primarySources) ==> SpringApplication.java:1354
     *      执行 run 方法
     *      --> .run(args); ==> SpringApplication.java:1354
     *      创建 ApplicationContext
     *      --> context = createApplicationContext(); ==> SpringApplication.java:331
     *          当前项目引入了 spring-webmvc、tomcat 相关依赖, 所以这里创建的是 Web 环境 ApplicationContext, 一般为 AnnotationConfigServletWebServerApplicationContext
     *          --> return this.applicationContextFactory.create(this.webApplicationType); ==> SpringApplication.java:589
     *      刷新 Spring 容器
     *      --> refreshContext(context); ==> SpringApplication.java:334
     *          --> refresh(context); ==> SpringApplication.java:456
     *              --> applicationContext.refresh(); ==> SpringApplication.java:754
     *  Spring 核心容器刷新流程
     *      --> AbstractApplicationContext.refresh() ==> AbstractApplicationContext.java:584
     *      准备 BeanFactory
     *      --> prepareBeanFactory(beanFactory); ==> AbstractApplicationContext.java:598
     *      执行 BeanFactoryPostProcessor, 这里会解析 @SpringBootApplication、@ComponentScan、自动配置类等
     *      --> invokeBeanFactoryPostProcessors(beanFactory); ==> AbstractApplicationContext.java:606
     *      注册 BeanPostProcessor, 后续 AOP、事务代理、Repository 代理都依赖这些后置处理器
     *      --> registerBeanPostProcessors(beanFactory); ==> AbstractApplicationContext.java:608
     *      初始化非懒加载单例 Bean
     *      --> finishBeanFactoryInitialization(beanFactory); ==> AbstractApplicationContext.java:624
     *          --> beanFactory.preInstantiateSingletons(); ==> AbstractApplicationContext.java:962
     *          进入 DefaultListableBeanFactory
     *          --> DefaultListableBeanFactory.preInstantiateSingletons(); ==> DefaultListableBeanFactory.java:955
     *              遍历所有 BeanDefinition 名称
     *              --> List<String> beanNames = new ArrayList<>(this.beanDefinitionNames); ==> DefaultListableBeanFactory.java:962
     *              遍历 beanName
     *              --> for (String beanName : beanNames) {...} ==> DefaultListableBeanFactory.java:965
     *                  合并 BeanDefinition
     *                  --> RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName); ==> DefaultListableBeanFactory.java:966
     *                  判断是否需要提前实例化, 条件: (1.不是抽象 Bean, 2.是单例, 3.不是懒加载)
     *                  --> if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {...} ==> DefaultListableBeanFactory.java:967
     *                  普通 Bean 进入 getBean, 真正创建 Bean。 getBean("hibernateMementoRunner"), getBean("hibernateMementoService")
     *                  --> getBean(beanName); ==> DefaultListableBeanFactory.java:971
     *                      获取 Bean
     *                      --> return doGetBean(name, null, null, false); ==> AbstractBeanFactory.java:200
     *                          如果一级缓存没有, 创建 Bean
     *                          --> return createBean(beanName, mbd, args); ==> AbstractBeanFactory.java:326
     *                              真正创建 Bean 实例
     *                              --> Object beanInstance = doCreateBean(beanName, mbdToUse, args); ==> AbstractAutowireCapableBeanFactory.java:522
     *                                  实例化对象
     *                                  --> instanceWrapper = createBeanInstance(beanName, mbd, args); ==> AbstractAutowireCapableBeanFactory.java:562
     *                                  属性填充 / 依赖注入
     *                                  --> populateBean(beanName, mbd, instanceWrapper); ==> AbstractAutowireCapableBeanFactory.java:599
     *                                  初始化 Bean
     *                                  --> exposedObject = initializeBean(beanName, exposedObject, mbd); ==> AbstractAutowireCapableBeanFactory.java:600
     *                                      执行初始化前置处理
     *                                      --> wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName); ==> AbstractAutowireCapableBeanFactory.java:1778
     *                                      执行初始化方法
     *                                      --> invokeInitMethods(beanName, wrappedBean, mbd); ==> AbstractAutowireCapableBeanFactory.java:1782
     *                                      执行初始化后置处理
     *                                      --> wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName); ==> AbstractAutowireCapableBeanFactory.java:1789
     *                                          事务 AOP 代理就是在这里创建的
     *                                          --> Object current = processor.postProcessAfterInitialization(result, beanName); ==> AbstractAutowireCapableBeanFactory.java:438
     *                                              判断当前 Bean 是否需要代理
     *                                              --> return wrapIfNecessary(bean, beanName, cacheKey); ==> AbstractAutoProxyCreator.java:320
     *                                              找到当前 Bean 匹配的 Advisor, 例如 @Transactional 对应: BeanFactoryTransactionAttributeSourceAdvisor
     *                                              --> Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null); ==> AbstractAutoProxyCreator.java:368
     *                                                  如果存在匹配 Advisor, 创建代理对象
     *                                                  --> Object proxy = createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean)); ==> AbstractAutoProxyCreator.java:371
     *                                                      --> return buildProxy(beanClass, beanName, specificInterceptors, targetSource, false); ==> AbstractAutoProxyCreator.java:466
     *                                                          创建 ProxyFactory
     *                                                          --> ProxyFactory proxyFactory = new ProxyFactory(); ==> AbstractAutoProxyCreator.java:482
     *                                                          添加事务拦截器
     *                                                          --> proxyFactory.addAdvisors(advisors); ==> AbstractAutoProxyCreator.java:505
     *                                                          把目标对象放进去
     *                                                          --> proxyFactory.setTargetSource(targetSource); ==> AbstractAutoProxyCreator.java:506
     *                                                          获取代理对象
     *                                                          --> return (classOnly ? proxyFactory.getProxyClass(classLoader) : proxyFactory.getProxy(classLoader)); ==> AbstractAutoProxyCreator.java:519
     *                                                          --> proxyFactory.getProxy(classLoader)
     *                                                              --> return createAopProxy().getProxy(classLoader); ==> ProxyFactory.java:110
     *                                                                  createAopProxy() 会根据条件选择 JDK 动态代理或 CGLIB, 当前 HibernateMementoService 是普通类, 所以事务代理一般使用 CGLIB
     *                                                                  --> return buildProxy(classLoader, false); ==> CglibAopProxy.java:163
     *                                                                      创建 CGLIB Enhancer
     *                                                                      --> Enhancer enhancer = createEnhancer(); ==> CglibAopProxy.java:193
     *                                                                      设置父类
     *                                                                      --> enhancer.setSuperclass(proxySuperClass); ==> CglibAopProxy.java:201
     *                                                                      设置回调过滤器
     *                                                                      --> enhancer.setCallbackFilter(filter); ==> CglibAopProxy.java:215
     *                                                                      创建代理 Class 和代理对象
     *                                                                      --> return (classOnly ? createProxyClass(enhancer) : createProxyClassAndInstance(enhancer, callbacks)); ==> CglibAopProxy.java:221
     *                                                                          当前对象实际类型是 ObjenesisCglibAopProxy
     *                                                                          所以这里发生多态调用
     *                                                                          --> ObjenesisCglibAopProxy.createProxyClassAndInstance() ==> ObjenesisCglibAopProxy.java:61
     *                                                                              创建代理 Class
     *                                                                              --> Class<?> proxyClass = enhancer.createClass(); ==> ObjenesisCglibAopProxy.java:62
     *                                                                              使用 Objenesis 创建代理对象
     *                                                                              --> proxyInstance = objenesis.newInstance(proxyClass, enhancer.getUseCache()); ==> ObjenesisCglibAopProxy.java:67
     *                                                                              设置 callbacks
     *                                                                              --> ((Factory) proxyInstance).setCallbacks(callbacks); ==> ObjenesisCglibAopProxy.java:91
     *                                                                              返回代理对象
     *                                                                              --> return proxyInstance; ==> ObjenesisCglibAopProxy.java:92
     *                                                          最终生成:
     *                                                              HibernateMementoService$$SpringCGLIB$$0
     *  Spring Data JPA Repository 创建流程
     *      自动配置启用 JPA Repository
     *      --> JpaRepositoriesAutoConfiguration ==> JpaRepositoriesAutoConfiguration.java
     *          Spring Boot 启动时导入 Spring Data JPA 的 Repository 注册逻辑
     *          --> @Import(JpaRepositoriesImportSelector.class) ==> JpaRepositoriesAutoConfiguration.java:79
     *      导入 Repository 注册器
     *      --> JpaRepositoriesImportSelector.selectImports(...) ==> JpaRepositoriesAutoConfiguration.java:127
     *          返回真正要导入的 Repository 注册器, 最终导入 JpaRepositoriesRegistrar
     *          --> return new String[] { determineImport() };
     *      进入 Repository 注册器
     *          JpaRepositoriesRegistrar 本身主要负责声明 JPA Repository 扩展配置,
     *          它继承 RepositoryBeanDefinitionRegistrarSupport
     *      --> JpaRepositoriesRegistrar ==> JpaRepositoriesRegistrar.class:12
     *      扫描 Repository 接口并注册 BeanDefinition
     *      --> RepositoryBeanDefinitionRegistrarSupport.registerBeanDefinitions(...) ==> RepositoryBeanDefinitionRegistrarSupport.class:41
     *          创建 RepositoryConfigurationDelegate
     *          --> RepositoryConfigurationDelegate delegate = new RepositoryConfigurationDelegate(configurationSource, this.resourceLoader, this.environment); ==> RepositoryBeanDefinitionRegistrarSupport.class:49
     *          真正注册 Repository
     *          --> delegate.registerRepositoriesIn(registry, extension); ==> RepositoryBeanDefinitionRegistrarSupport.class:50
     *      --> RepositoryConfigurationDelegate.registerRepositoriesIn(...) ==> RepositoryConfigurationDelegate.class:78
     *          获取 Repository 配置信息
     *          --> Collection<RepositoryConfiguration<RepositoryConfigurationSource>> configurations = extension.getRepositoryConfigurations(this.configurationSource, this.resourceLoader, this.inMultiStoreMode);
     *          遍历每一个 Repository 接口
     *          --> for(RepositoryConfiguration<? extends RepositoryConfigurationSource> configuration : configurations) {...} ==> RepositoryConfigurationDelegate.class:100
     *          构建 Repository BeanDefinition
     *          --> BeanDefinitionBuilder definitionBuilder = builder.build(configuration);
     *          注册 BeanDefinition
     *          --> registry.registerBeanDefinition(beanName, beanDefinition); ==> RepositoryConfigurationDelegate.class:119
     *              最终效果:
     *                  beanName:
     *                      userRepository
     *                  beanClass:
     *                      JpaRepositoryFactoryBean
     *          注意:
     *              这里注册的不是 UserRepository 实现类, 因为 UserRepository 是接口, 没有实现类。
     *              Spring Data JPA 注册的是 JpaRepositoryFactoryBean, 后面由 FactoryBean 生产 UserRepository 代理对象。
     *      创建 Repository Bean
     *          这一步不是单独的 JPA 创建流程, 而是回到上面的 Spring 单例 Bean 创建流程:
     *          --> finishBeanFactoryInitialization(beanFactory); ==> AbstractApplicationContext.java:624
     *              --> beanFactory.preInstantiateSingletons(); ==> AbstractApplicationContext.java:962
     *                  --> DefaultListableBeanFactory.preInstantiateSingletons(); ==> DefaultListableBeanFactory.java:955
     *                      --> getBean("userRepository")
     *          由于 userRepository 对应的 BeanClass 是 JpaRepositoryFactoryBean, 所以这里真正创建出来的是: JpaRepositoryFactoryBean
     *          而不是:
     *              UserRepository 实现类
     *      初始化 JpaRepositoryFactoryBean
     *          JpaRepositoryFactoryBean 创建完成后, 会进入 Bean 初始化流程:
     *          初始化 Bean
     *          --> exposedObject = initializeBean(beanName, exposedObject, mbd); ==> AbstractAutowireCapableBeanFactory.java:600
     *              执行初始化方法
     *              --> invokeInitMethods(beanName, wrappedBean, mbd); ==> AbstractAutowireCapableBeanFactory.java:1782
     *              因为 JpaRepositoryFactoryBean 实现了 InitializingBean, 所以会执行 afterPropertiesSet()
     *      --> ((InitializingBean) bean).afterPropertiesSet(); ==> AbstractAutowireCapableBeanFactory.java:1833
     *          --> JpaRepositoryFactoryBean.afterPropertiesSet() ==> JpaRepositoryFactoryBean.class:73
     *              --> super.afterPropertiesSet();
     *                  创建真正负责生成 Repository 代理对象的工厂
     *                  --> this.factory = this.createRepositoryFactory(); ==> 创建 JpaRepositoryFactory
     *          准备通过 RepositoryFactorySupport 创建 Repository 代理对象
     *          --> this.repository = Lazy.of(() -> (Repository)this.factory.getRepository(this.repositoryInterface, repositoryFragmentsToUse)); ==> RepositoryFactoryBeanSupport.class:167
     *              创建 Repository 目标对象 SimpleJpaRepository
     *              --> this.factory.getRepository(this.repositoryInterface, repositoryFragmentsToUse)
     *                  获取 Repository 元信息
     *                  --> RepositoryMetadata metadata = this.getRepositoryMetadata(repositoryInterface); ==> RepositoryFactorySupport.class:158
     *                  获取 RepositoryInformation
     *                  --> RepositoryInformation information = this.getRepositoryInformation(metadata, composition);
     *                  创建目标对象
     *                  --> Object target = this.getTargetRepository(information); ==> RepositoryFactorySupport.class:180
     *      --> JpaRepositoryFactory.getTargetRepository(...) ==> JpaRepositoryFactory.class:118
     *          获取实体元信息
     *          --> JpaEntityInformation<?, Serializable> entityInformation = this.getEntityInformation(information.getDomainType()); ==> JpaRepositoryFactory.class:119
     *          通过反射创建目标 Repository
     *          --> Object repository = this.getTargetRepositoryViaReflection(information, new Object[]{entityInformation, entityManager}); ==> JpaRepositoryFactory.class:120
     *          最终创建 SimpleJpaRepository, 才是真正执行 save、findById、delete 等 CRUD 方法的目标对象
     *          --> new SimpleJpaRepository<>(entityInformation, entityManager); ==> SimpleJpaRepository.java
     *      创建 Repository 代理对象
     *      --> RepositoryFactorySupport.getRepository(...) ==> RepositoryFactorySupport.class:147
     *          创建 ProxyFactory
     *          --> ProxyFactory result = new ProxyFactory(); ==> RepositoryFactorySupport.class:186
     *          设置目标对象
     *          --> result.setTarget(target); ==> RepositoryFactorySupport.class:187
     *          设置代理接口
     *          --> result.setInterfaces(repositoryInterface, Repository.class, TransactionalProxy.class);
     *          添加 Repository 方法拦截器
     *          --> result.addAdvice(new DefaultMethodInvokingMethodInterceptor());
     *          添加默认方法拦截器
     *          --> result.addAdvice(new ImplementationMethodExecutionInterceptor(information, compositionToUse, this.methodInvocationListeners)); ==> RepositoryFactorySupport.class:1211
     *          生成代理对象
     *          --> T repository = (T)result.getProxy(this.classLoader); ==> RepositoryFactorySupport.class:212
     *          最终返回:
     *              UserRepository 的 JDK 动态代理对象
     *      返回 RepositoryFactorySupport 创建出来的 UserRepository 代理对象
     *      --> this.repository.get(); ==> RepositoryFactoryBeanSupport.class:170
     *          所以: getBean("userRepository")
     *          最终拿到的不是 JpaRepositoryFactoryBean,
     *          而是它生产出来的: UserRepository 代理对象
     *  Spring Boot 启动完成后执行 Runner
     *      --> callRunners(context, applicationArguments); ==> SpringApplication.java:341
     *      找到所有 Runner
     *      --> String[] beanNames = beanFactory.getBeanNamesForType(Runner.class);
     *      执行 CommandLineRunner
     *      --> instancesToBeanNames.keySet().stream().sorted(comparator).forEach((runner) -> callRunner(runner, args)); ==> SpringApplication.java:774
     *      进入本案例启动逻辑
     *      --> HibernateMementoRunner.run(String... args) ==> HibernateMementoRunner.java:21
     *      注意: 这里 Runner 持有的是 HibernateMementoService 代理对象, 所以下面调用 createUser、updateUserWithoutSave、verifyUser 都会经过 Spring 事务拦截器。
     *  创建用户事务
     *      --> mementoService.createUser(); ==> HibernateMementoRunner.java:24
     *      进入事务代理
     *      --> HibernateMementoService$$SpringCGLIB$$0.createUser(...)
     *      执行事务拦截器
     *      --> TransactionInterceptor.invoke(...)
     *          --> invokeWithinTransaction(...)
     *      创建事务
     *      --> createTransactionIfNecessary(...) ==> TransactionAspectSupport.class:354
     *          --> PlatformTransactionManager.getTransaction(...)
     *              --> JpaTransactionManager.doBegin(...)
     *      doBegin 的核心作用: 1.创建或获取 EntityManager, 2.开启 JPA 事务, 3.把 EntityManager 绑定到当前线程
     *      执行目标方法
     *      --> invocation.proceedWithInvocation();
     *          --> HibernateMementoService.createUser(...)
     *      保存用户
     *      --> userRepository.save(new UserEntity("fanjiabao", 24));
     *          实际先进入 UserRepository 代理对象, 再调用真正目标对象 SimpleJpaRepository
     *          --> RepositoryFactorySupport.QueryExecutorMethodInterceptor.invoke(...)
     *              --> SimpleJpaRepository.save(...)
     *          判断是否为新对象
     *          --> entityInformation.isNew(entity)
     *          新对象执行 persist
     *          --> entityManager.persist(entity); ==> SimpleJpaRepository.java
     *              --> SharedEntityManagerInvocationHandler.invoke(...)
     *                  --> SessionImpl.persist(...)
     *      Hibernate 将 UserEntity 加入持久化上下文
     *      --> PersistenceContext 管理 UserEntity
     *      createUser 方法执行完成
     *      --> commitTransactionAfterReturning(txInfo); ==> TransactionAspectSupport.java
     *          --> PlatformTransactionManager.commit(...) ==> AbstractPlatformTransactionManager.java
     *              --> JpaTransactionManager.doCommit(...) ==> JpaTransactionManager.java
     *                  --> EntityTransaction.commit(); ==> JpaTransactionManager.java
     *      最终执行 insert SQL
     *          Hibernate:
     *              insert into tb_user (age, username, id)
     *              values (?, ?, default)
     *      创建完成后数据库中数据:
     *          id       = 1
     *          username = fanjiabao
     *          age      = 24
     *  修改用户事务
     *      --> mementoService.updateUserWithoutSave(userId); ==> HibernateMementoRunner.java
     *      进入事务代理
     *      --> HibernateMementoService$$SpringCGLIB$$0.updateUserWithoutSave(...)
     *      执行事务拦截器
     *      --> TransactionInterceptor.invoke(...) ==> TransactionInterceptor.java
     *          --> invokeWithinTransaction(...) ==> TransactionAspectSupport.java
     *      开启事务
     *      --> createTransactionIfNecessary(...) ==> TransactionAspectSupport.java
     *          --> JpaTransactionManager.doBegin(...) ==> JpaTransactionManager.java
     *      执行目标方法
     *      --> invocation.proceedWithInvocation(); ==> TransactionAspectSupport.java
     *          --> HibernateMementoService.updateUserWithoutSave(...)
     *      清空持久化上下文
     *      --> entityManager.flush(); ==> SharedEntityManagerCreator.java
     *          作用:
     *              将当前 PersistenceContext 中待同步 SQL 刷到数据库。
     *              这里新事务刚开始, 一般没有待同步内容。
     *      --> entityManager.clear(); ==> SharedEntityManagerCreator.java
     *          作用:
     *              清空一级缓存, 确保下面 findById 一定重新查询数据库。
     *      查询用户
     *      --> userRepository.findById(userId); ==> HibernateMementoService.java
     *          实际先进入 UserRepository 代理对象,
     *          再调用真正目标对象 SimpleJpaRepository
     *          --> RepositoryFactorySupport.QueryExecutorMethodInterceptor.invoke(...) ==> RepositoryFactorySupport.java
     *              --> SimpleJpaRepository.findById(ID id) ==> SimpleJpaRepository.java
     *                  --> entityManager.find(domainType, id, hints); ==> SimpleJpaRepository.java
     *                      --> SharedEntityManagerInvocationHandler.invoke(...) ==> SharedEntityManagerCreator.java
     *                          --> SessionImpl.find(...) ==> SessionImpl.java
     *      Hibernate 执行 select 查询
     *          Hibernate:
     *              select
     *                  ue1_0.id,
     *                  ue1_0.age,
     *                  ue1_0.username
     *              from
     *                  tb_user ue1_0
     *              where
     *                  ue1_0.id=?
     *      ResultSet 转换成 UserEntity
     *          当前查出来的数据:
     *              username = fanjiabao
     *              age      = 24
     *          得到 Java 对象:
     *              UserEntity{id=1, username='fanjiabao', age=24}
     *  Hibernate 保存原始快照 loadedState(备忘录模式核心)
     *      查询完成后, Hibernate 不只是返回 UserEntity,
     *      还会把这个 Entity 放入一级缓存 PersistenceContext 中管理。
     *      将 Entity 加入 PersistenceContext
     *      --> StatefulPersistenceContext.addEntity(...) ==> StatefulPersistenceContext.java
     *      创建 EntityEntry
     *      --> EntityEntryFactory.createEntityEntry(...) ==> EntityEntryFactory.java
     *      EntityEntry 中保存 loadedState
     *      --> loadedState ==> EntityEntry.java
     *      loadedState 保存的是数据库查询出来那一刻的原始状态:
     *          loadedState = ["fanjiabao", 24]
     *      这里就是备忘录模式思想:
     *          UserEntity 是当前对象
     *          EntityEntry.loadedState 是旧状态快照
     *          PersistenceContext 负责保存和管理这个快照
     *      对应备忘录模式:
     *          Originator:
     *              UserEntity
     *          Memento:
     *              EntityEntry.loadedState
     *          Caretaker:
     *              PersistenceContext / EntityEntry
     *  修改托管对象, 但不调用 save
     *      --> dbUser.setUsername("fanjiabao-update"); ==> HibernateMementoService.java
     *      --> dbUser.setAge(20); ==> HibernateMementoService.java
     *      注意:
     *          这里只是修改 Java 内存对象。
     *          没有调用 userRepository.save(dbUser)。
     *          此时还没有执行 update SQL。
     *      当前内存对象状态:
     *          values = ["fanjiabao-update", 20]
     *      Hibernate 保存的原始快照:
     *          loadedState = ["fanjiabao", 24]
     *      所以现在 Hibernate 手里有两份状态:
     *          1. 当前对象状态 values
     *          2. 查询时保存的备忘录 loadedState
     *  updateUserWithoutSave 方法结束, 事务提交
     *      --> commitTransactionAfterReturning(txInfo);
     *          --> PlatformTransactionManager.commit(...)
     *              --> JpaTransactionManager.doCommit(...)
     *                  --> EntityTransaction.commit();
     *      Hibernate 在事务提交前会执行 flush
     *      --> SessionImpl.flushBeforeTransactionCompletion()
     *          --> SessionImpl.managedFlush()
     *              --> SessionImpl.doFlush()
     *      doFlush 触发 FlushEvent
     *      --> eventListenerGroups.eventListenerGroup_FLUSH.fireEventOnEachListener(...)
     *          --> DefaultFlushEventListener.onFlush(FlushEvent event)
     *  Flush 阶段遍历所有托管 Entity
     *      --> DefaultFlushEventListener.onFlush(...)
     *          判断 PersistenceContext 中是否存在需要处理的实体或集合
     *      --> flushEverythingToExecutions(event);
     *          作用:
     *              把 PersistenceContext 中的变化转换成待执行 SQL 动作。
     *      --> flushEntities(event, persistenceContext);
     *          作用:
     *              遍历 PersistenceContext 中所有托管状态 Entity。
     *      对每个 Entity 触发 FlushEntityEvent
     *      --> flushListeners.fireEventOnEachListener(entityEvent, FlushEntityEventListener::onFlushEntity);
     *      进入单个 Entity 的脏检查
     *      --> DefaultFlushEntityEventListener.onFlushEntity(FlushEntityEvent event)
     *  单个 Entity 脏检查(重点)
     *      --> DefaultFlushEntityEventListener.onFlushEntity(...)
     *      取出 Entity
     *      --> Object entity = event.getEntity();
     *      取出 EntityEntry
     *      --> EntityEntry entry = event.getEntityEntry();
     *      判断是否需要脏检查
     *      --> boolean mightBeDirty = entry.requiresDirtyCheck(entity);
     *      获取当前对象属性值
     *      --> Object[] values = getValues(entity, entry, mightBeDirty, session);
     *      此时 values 是当前对象最新状态:
     *          values = ["fanjiabao-update", 20]
     *      判断是否需要执行 update
     *      --> if (isUpdateNecessary(event, mightBeDirty)) {...}
     *      进入脏检查
     *      --> dirtyCheck(event);
     *  获取 loadedState 并比较(备忘录模式最核心)
     *      --> dirtyCheck(event);
     *      取出当前状态
     *      --> final Object[] values = event.getPropertyValues();
     *      取出 EntityEntry
     *      --> final EntityEntry entry = event.getEntityEntry();
     *      取出 Persister
     *      --> final EntityPersister persister = entry.getPersister();
     *      取出原始快照 loadedState
     *      --> final Object[] loadedState = entry.getLoadedState();
     *      这里就是备忘录模式最核心的一行:
     *          entry.getLoadedState()
     *      loadedState 是查询 Entity 时保存的原始状态:
     *          loadedState = ["fanjiabao", 24]
     *      当前状态:
     *          values = ["fanjiabao-update", 20]
     *      调用 Hibernate 的属性差异比较
     *      --> dirtyProperties = persister.findDirty(values, loadedState, entity, session);
     *          --> AbstractEntityPersister.findDirty(...)
     *              --> DirtyHelper.findDirty(...)
     *      DirtyHelper.findDirty 内部会逐个属性比较:
     *          username:
     *              当前值: fanjiabao-update
     *              快照值: fanjiabao
     *              结果: 发生变化
     *          age:
     *              当前值: 20
     *              快照值: 24
     *              结果: 发生变化
     *      最终返回 dirtyProperties:
     *          dirtyProperties = [username属性下标, age属性下标]
     *      如果 dirtyProperties 不为空,
     *      Hibernate 就认为这个 Entity 是脏对象, 需要执行 update。
     *  封装 update 动作
     *      --> scheduleUpdate(event);
     *      创建 EntityUpdateAction
     *      --> new EntityUpdateAction(...)
     *      加入 ActionQueue
     *      --> session.getActionQueue().addAction(updateAction);
     *      注意:
     *          这里还不是直接执行 SQL。
     *          Hibernate 先把 update 操作封装成 Action。
     *      ActionQueue 的作用:
     *          统一管理 insert、update、delete 等动作,
     *          后面按顺序批量执行。
     *  执行 ActionQueue 中的 SQL 动作
     *      --> performExecutions(session);
     *          --> session.getActionQueue().prepareActions();
     *          --> session.getActionQueue().executeActions();
     *      执行 EntityUpdateAction
     *      --> EntityUpdateAction.execute()
     *      执行更新协调器
     *      --> persister.getUpdateCoordinator().update(...)
     *          --> UpdateCoordinatorStandard.update(...)
     *      最终执行 update SQL:
     *          Hibernate:
     *              update
     *                  tb_user
     *              set
     *                  age=?,
     *                  username=?
     *              where
     *                  id=?
     *      到这里数据库中的数据变成:
     *          username = fanjiabao-update
     *          age      = 20
     *  验证最终结果
     *      --> mementoService.verifyUser(userId);
     *      进入事务代理
     *      --> HibernateMementoService$$SpringCGLIB$$0.verifyUser(...)
     *      开启只读事务
     *      --> TransactionInterceptor.invoke(...)
     *          --> JpaTransactionManager.doBegin(...)
     *      重新查询数据库
     *      --> userRepository.findById(userId);
     *          --> RepositoryFactorySupport.QueryExecutorMethodInterceptor.invoke(...)
     *              --> SimpleJpaRepository.findById(...)
     *                  --> entityManager.find(...)
     *                      --> SessionImpl.find(...)
     *      查询结果:
     *          UserEntity{id=1, username='fanjiabao-update', age=20}
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HibernateMementoAnalysisApplication.class, args);
        UserRepository bean = context.getBean(UserRepository.class);
        // ./jdk.proxy2.$Proxy103.txt
        System.out.println(bean.getClass());
    }

}
