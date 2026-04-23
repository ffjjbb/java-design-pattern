package com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/22 17:11
 * @description: Spring AOP 的责任链模式:
 *  1.在创建 UserService 时, 如果发现它符合 AOP 增强条件, 就返回代理对象
 *  2.调用 UserService.test() 时, 先进入代理逻辑, 再进入责任链
 * Spring AOP 中通过 MethodInterceptor 形成责任链, 每个拦截器在 invoke 方法中调用 invocation.proceed(), 从而递归触发下一个拦截器,
 * 最终执行目标方法。整个过程本质是一个 "递归责任链", 而不是简单循环。
 */
public class SpringAopChainOfResponsibilityAnalysis {

    /**
     * 调用流程:
     *  1.创建 AnnotationConfigApplicationContext 容器
     *      --> new AnnotationConfigApplicationContext(AppConfig.class)
     *          初始化容器基础设施
     *          --> this(); ==> AnnotationConfigApplicationContext.class:37
     *              初始化注解读取器, 解析 @Configuration, @Bean, @Component, @Import
     *              --> this.reader = new AnnotatedBeanDefinitionReader(this); ==> AnnotationConfigApplicationContext.class:25
     *              初始化包扫描器, 扫描 @Component,@Service,@Repository,@Controller
     *              --> this.scanner = new ClassPathBeanDefinitionScanner(this); ==> AnnotationConfigApplicationContext.class:27
     *          --> this.register(componentClasses); ==> AnnotationConfigApplicationContext.class:38
     *              将 AppConfig 注册为 BeanDefinition
     *              --> this.reader.register(componentClasses); ==> AnnotationConfigApplicationContext.class:68
     *          真正启动整个容器
     *          --> this.refresh(); ==> AnnotationConfigApplicationContext.class:39
     *  2.refresh() 刷新容器
     *      --> this.refresh(); ==> AnnotationConfigApplicationContext.class:39
     *          容器准备刷新: 设置启动时间, 标记容器状态为 active, 初始化属性源校验
     *          --> this.prepareRefresh(); ==> AbstractApplicationContext.class:311
     *          获取新的 BeanFactory(DefaultListableBeanFactory), 是 Spring IoC 的核心工厂, 保存 BeanDefinition, 创建 Bean, 管理单例池, 负责依赖注入
     *          --> ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory(); ==> AbstractApplicationContext.class:312
     *          给 BeanFactory 设置一些基础能力, 比如: ClassLoader,EL 表达式解析器,注册内置依赖,注册一些特殊可解析依赖
     *          --> this.prepareBeanFactory(beanFactory); ==> AbstractApplicationContext.class:313
     *          模板方法中的扩展点, 默认实现为空, 由子类按需扩展
     *          --> this.postProcessBeanFactory(beanFactory); ==> AbstractApplicationContext.class:316
     *          执行 BeanFactoryPostProcessor
     *          --> this.invokeBeanFactoryPostProcessors(beanFactory); ==> AbstractApplicationContext.class:318
     *              如果 AppConfig 上存在 @EnableAspectJAutoProxy
     *              --> AspectJAutoProxyRegistrar.registerBeanDefinitions(...) ==> AspectJAutoProxyRegistrar.registerBeanDefinitions(...)
     *                  注册自动代理创建器
     *                  --> AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(...) ==> AspectJAutoProxyRegistrar.registerBeanDefinitions(...)
     *                      最终注册:
     *                      --> AnnotationAwareAspectJAutoProxyCreator
     *          注册 BeanPostProcessor
     *          --> this.registerBeanPostProcessors(beanFactory); ==> AbstractApplicationContext.class:319
     *              这里会把 AnnotationAwareAspectJAutoProxyCreator 注册到容器中
     *          初始化所有非懒加载单例
     *          --> this.finishBeanFactoryInitialization(beanFactory); ==> AbstractApplicationContext.class:325
     *  3.UserService 创建并被包装为代理对象
     *      在 Bean 初始化完成后, 自动代理创建器介入
     *      --> AbstractAutoProxyCreator.postProcessAfterInitialization(bean, beanName) ==> AbstractAutoProxyCreator.java:316
     *          判断当前 Bean 是否需要增强
     *          --> return wrapIfNecessary(bean, beanName, cacheKey); ==> AbstractAutoProxyCreator.java:320
     *              若命中切面, 创建代理对象
     *              --> Object proxy = createProxy(bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean)); ==> AbstractAutoProxyCreator.java:37
     *      所以:
     *      --> context.getBean(UserService.class)
     *          获取到的不是原始 UserService, 而是 CGLIB 代理对象 ./UserService$$SpringCGLIB$$0.java
     *  4.调用代理对象方法
     *      --> userService.test();
     *          实际调用的是代理类方法
     *          --> UserService$$SpringCGLIB$$0.test()
     *              代理方法不会直接 super.test()
     *              而是先取出回调拦截器
     *              --> MethodInterceptor methodInterceptor = this.CGLIB$CALLBACK_0; ==> UserService$$SpringCGLIB$$0.test()
     *              再进入拦截逻辑
     *              --> methodInterceptor.intercept(this, CGLIB$test$0$Method, CGLIB$emptyArgs, CGLIB$test$0$Proxy); ==> UserService$$SpringCGLIB$$0.test()
     *  5.CGLIB$CALLBACK_0 对应 Spring AOP 总拦截器
     *      --> ./CglibAopProxy.DynamicAdvisedInterceptor.txt
     *          调用入口:
     *              --> DynamicAdvisedInterceptor.intercept(...) ==> CglibAopProxy.DynamicAdvisedInterceptor.txt:46
     *              获取目标对象
     *              --> TargetSource targetSource = this.advised.getTargetSource(); ==> CglibAopProxy.DynamicAdvisedInterceptor.txt:50
     *              获取当前方法对应的拦截器链
     *              --> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(...) ==> CglibAopProxy.DynamicAdvisedInterceptor.txt:59
     *              创建方法调用对象
     *              --> retVal = new CglibAopProxy.CglibMethodInvocation(...).proceed(); ==> CglibAopProxy.DynamicAdvisedInterceptor.txt:63
     *  6.责任链递归执行
     *      --> new CglibAopProxy.CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy) ==> ./CglibAopProxy.DynamicAdvisedInterceptor.txt:65
     *          生成代理类如下
     *          --> ./CglibAopProxy.CglibMethodInvocation.txt
     *              --> return super.proceed(); ==> CglibAopProxy.CglibMethodInvocation.txt:22
     *                  父类如下
     *                  --> ./ReflectiveMethodInvocation.txt
     *                      --> ReflectiveMethodInvocation.proceed() ==> ./ReflectiveMethodInvocation.txt:69
     *                          如果已经到达责任链末尾
     *                          --> return invokeJoinpoint(); ==> ReflectiveMethodInvocation.txt:72
     *                              执行目标方法
     *                              --> return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments); ==> ReflectiveMethodInvocation.txt:88
     *                          否则取出下一个拦截器
     *                          --> interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex) ==> ./ReflectiveMethodInvocation.txt:74
     *                              调用当前拦截器
     *                              --> return dm.interceptor().invoke(this); ==> ReflectiveMethodInvocation.txt:79
     *                      每个拦截器内部通常都是(如Aspect1.java:20)
     *                          --> 前置逻辑
     *                          --> invocation.proceed()
     *                          --> 后置逻辑
     *  7.最终输出顺序
     *  '''Aspect1 前置
     *    Aspect2 前置
     *    执行目标方法...
     *    Aspect2 后置
     *    Aspect1 后置'''
     *  <p>
     *  责任链不是简单循环, 而是递归调用; 所以前置通知按责任链正序进入, 后置通知按调用栈逆序返回。
     *      --> Aspect1 前置 --> Aspect2 前置 --> 执行目标方法... --> Aspect2 后置 --> Aspect1 后置
     *      Aspect1.invoke() {
     *          前置1
     *          invocation.proceed() ---> Aspect2.invoke() {
     *              前置2
     *              invocation.proceed() ---> 目标方法
     *              后置2
     *          }
     *          后置1
     *      }
     *  8.责任链模式总结
     *      Handler(处理者):
     *          MethodInterceptor
     *      ConcreteHandler(具体处理者):
     *          Around/Before/After 等 Advice 适配后的拦截器
     *      Request(请求):
     *          MethodInvocation / ReflectiveMethodInvocation
     *      Invoker(调用入口):
     *          代理对象 UserService$$SpringCGLIB$$0
     *      Receiver(最终执行者):
     *          目标对象 UserService
     *      Client(客户端):
     *          main()
     * 9.通过 Arthas 获取 UserService 代理类
     *  选择当前进程, 查找代理类
     *      --> sc -d *UserService*SpringCGLIB*
     *          输出
     *          class-info        com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis.UserService$$SpringCGLIB$$0
     *          classLoaderHash   63947c6b
     *      反编译代理类
     *      --> jad com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis.UserService$$SpringCGLIB$$0
     *  观察反编译的 ./UserService$$SpringCGLIB$$0 中属性 private MethodInterceptor CGLIB$CALLBACK_0 到底是什么
     *      --> watch com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis.UserService$$SpringCGLIB$$0 test 'target.getCallback(0).getClass().getName()' -b
     *          等待test()方法调用, 控制台输出
     *          method=com.fanjiabao.design.pattern.behavioral.responsibility.source_code_analysis.UserService$$SpringCGLIB$$0.test location=AtEnter
     *          ts=2026-04-23 11:59:55.066; [cost=0.6695ms] result=@String[org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor]
     *      查看 CGLIB$CALLBACK_0
     *      --> jad org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor
     *  观察反编译的 ./CglibAopProxy.DynamicAdvisedInterceptor 中的 new CglibAopProxy.CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
     *      --> jad -c 63947c6b org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation ==> ./CglibAopProxy.CglibMethodInvocation.txt
     *      --> jad -c 63947c6b org.springframework.aop.framework.ReflectiveMethodInvocation ==> ./ReflectiveMethodInvocation.txt
     *          最终执行的 proceed() 方法在 ./ReflectiveMethodInvocation.proceed()
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建注解容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 获取 Bean(此时已经是代理对象)
        UserService userService = context.getBean(UserService.class);
        // userService 的代理对象为 UserService$$SpringCGLIB$$0.txt, 其中的 CGLIB$CALLBACK_0 属性是 ./CglibAopProxy.DynamicAdvisedInterceptor.txt
        // new CglibAopProxy.CglibMethodInvocation 的对象为 ./CglibAopProxy.CglibMethodInvocation.txt, 它的父类是 ./ReflectiveMethodInvocation.txt
        // 最终执行的代码逻辑为 ./ReflectiveMethodInvocation.txt:69
        System.out.println(userService.getClass());

        // 执行方法(触发责任链)
        userService.test();
        while (true){
            Thread.sleep(10000);
            System.out.println("-----------------");
            userService.test();
        }
    }

}