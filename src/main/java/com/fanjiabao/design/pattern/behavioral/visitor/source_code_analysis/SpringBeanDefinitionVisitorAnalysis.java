package com.fanjiabao.design.pattern.behavioral.visitor.source_code_analysis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/28 15:59
 * @description: Spring BeanDefinitionVisitor 访问者模式解析
 * 核心:
 *  BeanDefinition 负责保存 Bean 的定义元数据,
 *  BeanDefinitionVisitor 负责遍历访问这些元数,
 *  StringValueResolver 负责对访问到的字符串值进行解析和替换。
 * 这样 Spring 不需要在 BeanDefinition 内部写死各种字符串解析逻辑, 而是通过访问
 * 者 BeanDefinitionVisitor 把 "数据结构" 和 "访问处理行为" 解耦。
 */
@SpringBootApplication(excludeName = {
        "org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration"
})
public class SpringBeanDefinitionVisitorAnalysis {


    /**
     * 调用流程:
     *  1.启动 Spring Boot 应用
     *      --> SpringApplication.run(SpringBeanDefinitionVisitorAnalysis.class, args);
     *          静态 run 方法
     *          --> return new SpringApplication(primarySources).run(args); ==> SpringApplication.java:1354
     *              --> new SpringApplication(primarySources) = this(null, primarySources); ==> SpringApplication.java:273
     *                  创建 SpringApplication 对象:
     *                      保存主启动类 SpringBeanDefinitionVisitorAnalysis.class,
     *                      推断应用类型 WebApplicationType,
     *                      加载初始化器 ApplicationContextInitializer,
     *                      加载监听器 ApplicationListener。
     *          真正执行启动逻辑
     *          --> SpringApplication.run(args);
     *  2.SpringApplication.run(args) 启动核心流程
     *      --> SpringApplication.run(String... args) ==> SpringApplication.java:1354
     *          准备 Environment, 加载 application.yml, 命令行参数, 系统环境变量等配置。
     *          --> ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments); ==> SpringApplication.java:329
     *          创建 ApplicationContext, 根据 WebApplicationType 创建真正的 Spring 容器。
     *          --> context = createApplicationContext(); ==> SpringApplication.java:331
     *              如果是普通非 Web 项目, 常见为: AnnotationConfigApplicationContext
     *              如果是 Servlet Web 项目, 常见为: AnnotationConfigServletWebServerApplicationContext
     *          准备 ApplicationContext, 把 environment 设置到 context, 应用 ApplicationContextInitializer, 加载主启动类对应的 BeanDefinition。
     *          --> prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner); ==> SpringApplication.java:333
     *              加载启动类
     *              --> load(context, sources.toArray(new Object[0])); ==> SpringApplication.java:430
     *                  把 SpringBeanDefinitionVisitorAnalysis 注册为 BeanDefinition
     *                  后续会继续解析:
     *                      @SpringBootApplication, @SpringBootConfiguration, @EnableAutoConfiguration, @ComponentScan
     *          刷新容器
     *          --> refreshContext(context); ==> SpringApplication.java:334
     *              --> refresh(context); ==> SpringApplication.java:456
     *                  --> applicationContext.refresh(); ==> SpringApplication.java:754
     *  3.refresh() 刷新 Spring 容器
     *      --> AbstractApplicationContext.refresh(); ==> AbstractApplicationContext.java:584
     *          容器准备刷新: 设置启动时间, 标记 active 状态, 初始化属性源
     *          --> prepareRefresh(); ==> AbstractApplicationContext.java:592
     *          获取 BeanFactory(DefaultListableBeanFactory), DefaultListableBeanFactory 是 Spring IoC 核心工厂, 内部保存 BeanDefinition, 管理单例池, 负责创建 Bean 和依赖注入。
     *          --> ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory(); ==> AbstractApplicationContext.java:595
     *          准备 BeanFactory, 设置 ClassLoader、EL 表达式解析器、类型转换器, 注册 ApplicationContextAware、ApplicationEventPublisher 等特殊依赖。
     *          --> prepareBeanFactory(beanFactory); ==> AbstractApplicationContext.java:598
     *          模板方法扩展点, 默认实现为空, 子类可以在 BeanFactory 准备完成后做额外扩展。
     *          --> postProcessBeanFactory(beanFactory); ==> AbstractApplicationContext.java:602
     *          执行 BeanFactoryPostProcessor, 在普通 Bean 创建之前, 允许修改 BeanDefinition, 本案例的 BeanDefinitionVisitor 就是在这里被调用的。
     *          --> invokeBeanFactoryPostProcessors(beanFactory); ==> AbstractApplicationContext.java:606
     *              委托给 PostProcessorRegistrationDelegate 执行
     *              --> PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors()); ==> AbstractApplicationContext.java:788
     *                  先执行 BeanDefinitionRegistryPostProcessor
     *                      原因:
     *                          BeanDefinitionRegistryPostProcessor 不仅能修改 BeanDefinition,
     *                          还可以继续注册新的 BeanDefinition,
     *                          所以它比普通 BeanFactoryPostProcessor 更早执行。
     *                  找出所有 BeanDefinitionRegistryPostProcessor 类型的 Bean
     *                  --> beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false); ==> AbstractApplicationContext.java:109
     *                  找到我们自定义的: VisitorBeanDefinitionRegistryPostProcessor
     *                  提前创建这个后置处理器
     *                  注意:
     *                      普通业务 Bean 此时还没有创建,
     *                      但是 BeanDefinitionRegistryPostProcessor 会被提前创建。
     *                  --> currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class)); ==> AbstractApplicationContext.java:112
     *              执行 BeanDefinitionRegistryPostProcessor 的注册方法
     *              --> invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry, beanFactory.getApplicationStartup()); ==> AbstractApplicationContext.java:118
     *                  --> postProcessor.postProcessBeanDefinitionRegistry(registry); ==> AbstractApplicationContext.java:349
     *                      实际进入我们自己的方法
     *                      --> VisitorBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry(registry); ==> VisitorBeanDefinitionRegistryPostProcessor.java:40
     *                          创建 BeanDefinition, BeanDefinition 是 Bean 的定义信息, 不是 Bean 实例。
     *                          --> GenericBeanDefinition beanDefinition = new GenericBeanDefinition(); ==> VisitorBeanDefinitionRegistryPostProcessor.java:42
     *                          设置 BeanClass, 告诉 Spring 将来创建 dataSourceConfigBean 时, 要创建 DataSourceConfigBean 类型对象。
     *                          --> beanDefinition.setBeanClass(DataSourceConfigBean.class); ==> VisitorBeanDefinitionRegistryPostProcessor.java:43
     *                          创建属性集合 MutablePropertyValues, 保存 setter 注入的属性值。
     *                          --> MutablePropertyValues propertyValues = new MutablePropertyValues(); ==> VisitorBeanDefinitionRegistryPostProcessor.java:45
     *                          添加属性值, 这里故意把占位符放进 BeanDefinition, 后面让 BeanDefinitionVisitor 访问并替换。
     *                          --> propertyValues.add("name", "${demo.name}"); ==> VisitorBeanDefinitionRegistryPostProcessor.java:46
     *                          --> propertyValues.add("url", "${demo.url}"); ==> VisitorBeanDefinitionRegistryPostProcessor.java:47
     *                          把属性集合设置到 BeanDefinition 中
     *                          --> beanDefinition.setPropertyValues(propertyValues); ==> VisitorBeanDefinitionRegistryPostProcessor.java:49
     *                          注册 BeanDefinition
     *                          --> registry.registerBeanDefinition("dataSourceConfigBean", beanDefinition); ==> VisitorBeanDefinitionRegistryPostProcessor.java:51
     *                              --> DefaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition); ==> DefaultListableBeanFactory.java:998
     *                                  保存到 beanDefinitionMap, {key   = dataSourceConfigBean, value = GenericBeanDefinition}
     *                                  --> this.beanDefinitionMap.put(beanName, beanDefinition); ==> DefaultListableBeanFactory.java:1041
     *                                  保存到 beanDefinitionNames, 记录 BeanDefinition 注册顺序
     *                                  --> this.beanDefinitionNames.add(beanName); ==> DefaultListableBeanFactory.java:1075
     *                          注意:
     *                              这里没有创建 DataSourceConfigBean 对象。
     *                              此时只是保存了 Bean 的定义信息。
     *              执行 BeanFactoryPostProcessor 阶段, VisitorBeanDefinitionRegistryPostProcessor 既是 BeanDefinitionRegistryPostProcessor, 也是 BeanFactoryPostProcessor。
     *              --> invokeBeanFactoryPostProcessors(registryProcessors, beanFactory); ==> PostProcessorRegistrationDelegate.java:153
     *                  --> postProcessor.postProcessBeanFactory(beanFactory); ==> PostProcessorRegistrationDelegate.java:363
     *                      实际进入我们自己的方法
     *                      --> VisitorBeanDefinitionRegistryPostProcessor.postProcessBeanFactory(beanFactory); ==> VisitorBeanDefinitionRegistryPostProcessor.java:59
     *                          获取刚才注册的 BeanDefinition
     *                          --> BeanDefinition beanDefinition = beanFactory.getBeanDefinition("dataSourceConfigBean"); ==> VisitorBeanDefinitionRegistryPostProcessor.java:63
     *                              --> DefaultListableBeanFactory.getBeanDefinition(beanName);
     *                          创建 BeanDefinitionVisitor 访问者, BeanDefinitionVisitor 负责遍历 BeanDefinition 内部结构, 找到其中可以解析的字符串值。
     *                          --> BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(strVal -> {
     *                                  return environment.resolvePlaceholders(strVal);
     *                              });
     *                              StringValueResolver 作用: 定义访问到字符串后如何处理。
     *                              本案例中: environment.resolvePlaceholders(strVal) = 作用: 从 application.yml 中解析占位符。
     *                          开始访问 BeanDefinition
     *                          --> visitor.visitBeanDefinition(beanDefinition); ==> VisitorBeanDefinitionRegistryPostProcessor.java:73
     *                              --> BeanDefinitionVisitor.visitBeanDefinition(BeanDefinition beanDefinition);
     *                                  访问 parentName
     *                                  --> visitParentName(beanDefinition); ==> BeanDefinitionVisitor.java:79
     *                                  访问 beanClassName
     *                                  --> visitBeanClassName(beanDefinition); ==> BeanDefinitionVisitor.java:80
     *                                  访问 scope
     *                                  --> visitScope(beanDefinition);  ==> BeanDefinitionVisitor.java:83
     *                                  访问 factoryBeanName
     *                                  --> visitFactoryBeanName(beanDefinition);  ==> BeanDefinitionVisitor.java:81
     *                                  访问 factoryMethodName
     *                                  --> visitFactoryMethodName(beanDefinition); ==> BeanDefinitionVisitor.java:82
     *                                  访问属性值, 这是本案例核心
     *                                  --> visitPropertyValues(beanDefinition.getPropertyValues()); ==> BeanDefinitionVisitor.java:85
     *                                      --> BeanDefinitionVisitor.visitPropertyValues(MutablePropertyValues pvs); ==> BeanDefinitionVisitor.java:144
     *                                      遍历每一个 PropertyValue
     *                                      --> for (PropertyValue pv : pvs.getPropertyValueList()) ==> BeanDefinitionVisitor.java:146
     *                                          解析属性值
     *                                          --> Object newVal = resolveValue(pv.getValue()); ==> BeanDefinitionVisitor.java:147
     *                                              如果 value 是 String
     *                                              --> return resolveStringValue(strValue); ==> BeanDefinitionVisitor.java:219
     *                                                  调用 StringValueResolver
     *                                                  --> String resolvedValue = this.valueResolver.resolveStringValue(strVal); ==> BeanDefinitionVisitor.java:293
     *                                                      实际执行自定义的匿名函数
     *                                                      --> strVal -> return environment.resolvePlaceholders(strVal); ==> VisitorBeanDefinitionRegistryPostProcessor.java:69
     *                                                      --> return environment.resolvePlaceholders(strVal); ==> VisitorBeanDefinitionRegistryPostProcessor.java:70
     *                                                      解析结果:
     *                                                          ${demo.name} -> fanjiabao
     *                                                          ${demo.url}  -> jdbc:mysql://localhost:3306/test
     *                                          如果解析前后不同, 写回 BeanDefinition
     *                                          --> if (!ObjectUtils.nullSafeEquals(newVal, pv.getValue())) { ==> BeanDefinitionVisitor.java:149
     *                                                  pvs.add(pv.getName(), newVal);
     *                                              }
     *                          BeanDefinitionVisitor 访问结束
     *                          此时 BeanDefinition 中已经变成:
     *                              name = fanjiabao
     *                              url  = jdbc:mysql://localhost:3306/test
     *          注册 BeanPostProcessor
     *          --> registerBeanPostProcessors(beanFactory); ==> AbstractApplicationContext.java:608
     *          初始化所有非懒加载单例， 从这里开始, Spring 才真正创建普通业务 Bean。
     *          --> finishBeanFactoryInitialization(beanFactory); ==> AbstractApplicationContext.java:624
     *              预实例化单例 Bean
     *              --> beanFactory.preInstantiateSingletons(); ==> AbstractApplicationContext.java:926
     *                  --> DefaultListableBeanFactory.preInstantiateSingletons(); ==> DefaultListableBeanFactory.java:955
     *                      找到 dataSourceConfigBean
     *                      --> getBean(beanName); = getBean("dataSourceConfigBean"); ==> DefaultListableBeanFactory.java:971
     *                          --> AbstractBeanFactory.doGetBean(...); ==> AbstractBeanFactory.java:240
     *                              --> return createBean(beanName, mbd, args); ==> AbstractBeanFactory.java:326
     *                                  --> AbstractAutowireCapableBeanFactory.createBean(...); ==> AbstractAutowireCapableBeanFactory.java:485
     *                                      --> Object beanInstance = doCreateBean(beanName, mbdToUse, args); ==> AbstractAutowireCapableBeanFactory.java:522
     *                                          创建 Bean 实例
     *                                          --> instanceWrapper = createBeanInstance(beanName, mbd, args); ==> AbstractAutowireCapableBeanFactory.java:562
     *                                          填充 Bean 属性
     *                                          --> populateBean(beanName, mbd, instanceWrapper); ==> AbstractAutowireCapableBeanFactory.java:599
     *                                              因为 BeanDefinitionVisitor 已经提前替换了属性值, 所以这里注入的是:
     *                                                  setName("fanjiabao");
     *                                                  setUrl("jdbc:mysql://localhost:3306/test");
     *                                          初始化 Bean
     *                                          --> exposedObject = initializeBean(beanName, exposedObject, mbd); ==> AbstractAutowireCapableBeanFactory.java:600
     *          完成刷新
     *          --> this.finishRefresh(); ==> AbstractApplicationContext.java:627
     *  4.执行 Runner 验证结果
     *      refreshContext(context) 执行完成后, SpringApplication 会继续调用 Runner。
     *      --> refreshContext(context); ==> SpringApplication.java:334
     *      刷新完成后执行 Runner
     *      --> callRunners(context, applicationArguments); ==> SpringApplication.java:341
     *          从 BeanFactory 中查找所有 Runner 类型的 Bean, CommandLineRunner 继承了 Runner, 所以我们通过 @Bean 注册的 CommandLineRunner 也会被这里找到。
     *          --> ConfigurableListableBeanFactory beanFactory = context.getBeanFactory(); ==> SpringApplication.java:766
     *          --> String[] beanNames = beanFactory.getBeanNamesForType(Runner.class); ==> SpringApplication.java:767
     *          根据 beanName 获取 Runner 实例, 这里会拿到我们定义的 commandLineRunner Bean
     *          --> instancesToBeanNames.put(beanFactory.getBean(beanName, Runner.class), beanName); ==> SpringApplication.java:770
     *          按 @Order / Ordered 排序, 如果容器中有多个 CommandLineRunner / ApplicationRunner, Spring Boot 会按顺序执行。
     *          --> instancesToBeanNames.keySet().stream().sorted(comparator).forEach((runner) -> callRunner(runner, args)); ==> SpringApplication.java:774
     *              逐个执行 Runner
     *              --> forEach((runner) -> callRunner(runner, args));
     *                  判断 Runner 的具体类型, 如果是 ApplicationRunner:
     *                  --> if (runner instanceof ApplicationRunner) { ==> SpringApplication.java:786
     *                          callRunner(ApplicationRunner.class, runner, (applicationRunner) -> applicationRunner.run(args))
     *                      }
     *                  如果是 CommandLineRunner
     *                  --> if (runner instanceof CommandLineRunner) {
     *                          callRunner(CommandLineRunner.class, runner,
     * 					        (commandLineRunner) -> commandLineRunner.run(args.getSourceArgs()));
     * 					    }
     *              最终执行我们定义的 lambda
     *              --> CommandLineRunner.run(args); ==> SpringBeanDefinitionVisitorAnalysis.java:31
     *                  从容器中获取 dataSourceConfigBean
     *                  --> context.getBean(DataSourceConfigBean.class); ==> SpringBeanDefinitionVisitorAnalysis.java:33
     *                  此时 dataSourceConfigBean 已经在 refresh() 的 finishBeanFactoryInitialization(beanFactory) 阶段创建完成。
     *                  并且它的 BeanDefinition 属性值已经被 BeanDefinitionVisitor 提前替换:
     *                      ${demo.name} -> fanjiabao
     *                      ${demo.url}  -> jdbc:mysql://localhost:3306/test
     *                  所以输出:
     *                      DataSourceConfigBean{
     *                          name='fanjiabao',
     *                          url='jdbc:mysql://localhost:3306/test'
     *                      }
     *  5.访问者模式总结
     *      Visitor(访问者):
     *          BeanDefinitionVisitor
     *      Element(被访问元素):
     *          BeanDefinition
     *      ConcreteElement(具体被访问对象):
     *          GenericBeanDefinition
     *      ObjectStructure(对象结构):
     *          BeanDefinition 内部维护的元数据结构:
     *              parentName
     *              beanClassName
     *              scope
     *              factoryBeanName
     *              factoryMethodName
     *              constructorArgumentValues
     *              propertyValues
     *      访问行为:
     *          visitBeanDefinition(...)
     *          visitConstructorArgumentValues(...)
     *          visitPropertyValues(...)
     *          resolveValue(...)
     *          resolveStringValue(...)
     *      具体处理策略:
     *          StringValueResolver
     *      本案例中的处理策略:
     *          environment.resolvePlaceholders(strVal)
     *      Client(客户端):
     *          main()
     *      访问者模式核心:
     *          BeanDefinitionVisitor 负责遍历 BeanDefinition,
     *          StringValueResolver 负责解析字符串。
     *          BeanDefinition 不需要自己关心字符串如何解析,
     *          DataSourceConfigBean 也不需要知道占位符如何替换。
     *      本案例最终效果:
     *          在 Bean 创建前, 把 BeanDefinition 中的:
     *              ${demo.name}
     *              ${demo.url}
     *          替换成:
     *              fanjiabao
     *              jdbc:mysql://localhost:3306/test
     *          后续 Spring 创建 Bean 时, 注入的就是替换后的真实值。
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBeanDefinitionVisitorAnalysis.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            DataSourceConfigBean dataSourceConfigBean = context.getBean(DataSourceConfigBean.class);
            System.out.println("\n========== Bean 创建完成 ==========");
            System.out.println(dataSourceConfigBean);
        };
    }

}