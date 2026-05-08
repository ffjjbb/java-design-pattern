package com.fanjiabao.design.pattern.behavioral.interpreter.source_code_analysis;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/30 15:22
 * @description: Drools 解释器模式源码分析
 * Drools = 解释器模式 + Rete 网络执行引擎
 * <p>
 * 将一段业务规则表达式, 例如: Order(amount >= 1000, userLevel == "VIP")
 * 解析成 Drools 内部规则模型, 并在运行时结合 Fact(事实对象)进行匹配和执行。
 * <p>
 * 解释器模式流程
 *  1.规则语言:
 *      DRL(Drools Rule Language)
 *  2.表达式:
 *      amount >= 1000
 *      userLevel == "VIP"
 *  3.上下文:
 *      KieSession
 *      WorkingMemory
 *      Order(Fact 事实对象)
 *  4.解释执行:
 *      insert(order)
 *          -> 创建 FactHandle
 *          -> 注册传播动作（PropagationEntry）
 *      fireAllRules()
 *          -> flush 传播队列（PropagationList）
 *          -> ObjectTypeNode 判断对象类型
 *          -> AlphaNode 判断字段条件
 *          -> RuleTerminalNode 生成规则激活（Activation）
 *          -> 执行 then 逻辑
 * <p>
 * 核心
 *  1.Drools 不会直接执行字符串规则
 *  2.DRL 会被编译为: KiePackage -> RuleBase -> Rete 网络
 *  3.规则执行不是 "直接调用 if", 而是 Fact 在 Rete 网络中逐节点传播
 *  4.每一个节点(如 AlphaNode / BetaNode), 本质就是一个 "表达式解释器"
 *  5.insert() 只负责 "登记数据 + 注册传播动作"
 *     真正匹配发生在: fireAllRules()
 */
public class DroolsInterpreterAnalysis {


    /**
     * Drools 解释器模式源码调用流程(Drools 8.44.0.Final)
     * 一、创建 KieContainer(加载规则配置)
     *  获取 Drools 入口
     *      --> KieServices kieServices = KieServices.Factory.get();
     *          --> return LazyHolder.INSTANCE; ==> KieServices.java:365
     *  创建基于 classpath 的规则容器
     *      --> KieContainer kieContainer = kieServices.getKieClasspathContainer();
     *          --> return getKieClasspathContainer(null, findParentClassLoader(getClass())); ==> KieServicesImpl.java:80
     *              查找当前应用的 ClassLoader
     *              --> findParentClassLoader(getClass())
     *              进入重载方法
     *              --> getKieClasspathContainer(null, classLoader) ==> KieServicesImpl.java:91
     *                  如果 classpathKContainer 不存在，创建新的 KieContainer
     *                  --> classpathKContainer = newKieClasspathContainer(classpathKContainerId, classLoader); ==> KieServicesImpl.java:102
     *                      进入创建 classpath 容器方法
     *                      --> newKieClasspathContainer(containerId, classLoader) ==> KieServicesImpl.java:130
     *                          --> return newKieClasspathContainer(containerId, classLoader, null); ==> KieServicesImpl.java:131
     *                              创建 KieContainerImpl, 同时创建 ClasspathKieProject
     *                              --> KieContainerImpl newContainer = new KieContainerImpl(
     *                                      UUID.randomUUID().toString(),
     *                                      new ClasspathKieProject(classLoader, listener, releaseId),
     *                                      null
     *                                  ); ==> KieServicesImpl.java:137
     *                              ClasspathKieProject 作用:
     *                                  表示一个 classpath 类型的 Drools 项目
     *                                  内部维护 kmodule.xml 解析结果
     *                                  内部维护 KieBase 和 KieSession 的配置索引
     *                              KieContainerImpl 作用:
     *                                  Drools 规则容器
     *                                  后续通过 newKieSession("orderKSession") 创建规则执行会话
     *                              初始化 KieProject
     *                              --> kProject.init(); ==> KieServicesImpl.java:140
     *                                  --> ClasspathKieProject.init() ==> ClasspathKieProject.java:95
     *                                      发现 classpath 中的 KieModule
     *                                      --> discoverKieModules(); ==> ClasspathKieProject.java:97
     *                                          作用:
     *                                              发现当前 classpath 下的规则模块
     *                                              读取 META-INF/kmodule.xml 对应的 KieModule 配置
     *                                          当前项目解析结果:
     *                                              kbase:
     *                                                  name = orderKBase
     *                                                  packages = rules
     *                                              ksession:
     *                                                  name = orderKSession
     *                                      建立 KieBase / KieSession 索引关系
     *                                      --> indexParts(null, kieModules.values(), kJarFromKBaseName); ==> ClasspathKieProject.java:98
     *                                          作用:
     *                                              建立 orderKBase -> KieModule 的映射
     *                                              建立 orderKSession -> KieSessionModel 的映射
     * 二、创建 KieSession（触发 KieBase 创建 / 规则包构建）
     *  创建规则执行会话
     *      --> KieSession kieSession = kieContainer.newKieSession("orderKSession");
     *          --> return newKieSession(kSessionName, null, null); ==> KieContainerImpl.java:597
     *              根据名称获取 KieSessionModel
     *              --> KieSessionModelImpl kSessionModel = kSessionName != null ?
     *                      (KieSessionModelImpl) getKieSessionModel(kSessionName) :
     *                      (KieSessionModelImpl) findKieSessionModel(false);
     *                  作用:
     *                      从 kmodule.xml 解析结果中找到 orderKSession 配置
     *              根据 KieSessionModel 找到对应 KieBase
     *              --> KieBase kBase = getKieBaseFromKieSessionModel(kSessionModel); ==> KieContainerImpl.java:607
     *                  通过 KieSessionModel 找到所属 KieBase 名称
     *                  --> KieBase kBase = getKieBase(kSessionModel.getKieBaseModel().getName()); ==> KieContainerImpl.java:631
     *                      先从缓存获取 KieBase
     *                      --> KieBase kBase = kBases.get(kBaseName); ==> KieContainerImpl.java:443
     *                      如果缓存中没有, 则创建 KieBase
     *                      --> kBase = createKieBase(kBaseModel, kProject, buildContext, null); ==> KieContainerImpl.java:450
     *                          开始创建 KieBase
     *                          --> private KieBase createKieBase(KieBaseModelImpl kBaseModel, KieProject kieProject, BuildContext buildContext, KieBaseConfiguration conf)
     *                              根据 KieBase 名称获取对应 KieModule
     *                              --> InternalKieModule kModule = kieProject.getKieModuleForKBase(kBaseModel.getName()); ==> KieContainerImpl.java:485
     *                                  进入 ClasspathKieProject
     *                                  --> return this.kJarFromKBaseName.get(kBaseName); ==> ClasspathKieProject.java:498
     *                                  作用:
     *                                      根据 orderKBase 找到包含该规则库的 InternalKieModule
     *                              通过 KieModule 创建 KieBase
     *                              --> InternalKnowledgeBase kBase = kModule.createKieBase(kBaseModel, kieProject, buildContext, conf); ==> KieContainerImpl.java:486
     *                                  进入 AbstractKieModule
     *                                  --> createKieBase(kBaseModel, kieProject, buildContext, conf)
     *                                      构建规则包
     *                                      --> KnowledgePackagesBuildResult knowledgePackagesBuildResult = buildKnowledgePackages(kBaseModel, kieProject, buildContext);
     *                                          作用:
     *                                              构建当前 KieBase 需要的 KiePackage
     *                                              这里是 .drl 规则进入编译流程的入口
     *                                          先尝试从已有 builder 中获取规则包
     *                                          --> Collection<KiePackage> pkgs = getKnowledgePackagesForKieBase(kBaseModel.getName());
     *                                              --> KnowledgeBuilder kbuilder = kBuilders.get(kieBaseName);
     *                                              --> return kbuilder != null ? kbuilder.getKnowledgePackages() : null; ==> AbstractKieModule.java:164
     *                                              作用:
     *                                                  如果之前已经为 orderKBase 构建过 KnowledgeBuilder，
     *                                                  这里直接复用其生成的 KnowledgePackages
     *                                          如果没有缓存的规则包，则构建
     *                                          --> if (pkgs == null) { ==> AbstractKieModule.java:192
     *                                              调用 KieProject 构建规则包
     *                                              --> KnowledgeBuilder kbuilder = kieProject.buildKnowledgePackages(kBaseModel, buildContext);
     *                                                  作用:
     *                                                      根据 kmodule.xml 中 orderKBase 的配置，
     *                                                      构建该 KieBase 对应的规则资源
     *                                                  继续进入重载方法
     *                                                  --> return buildKnowledgePackages( kBaseModel, buildContext, BUILD_ALL ); ==> AbstractKieProject.java:216
     *                                                  作用:
     *                                                      创建 / 获取 KnowledgeBuilder
     *                                                      把规则资源加入 KnowledgeBuilder
     *                                                      触发规则资源编译
     *                                              如果规则编译有错误，返回失败结果
     *                                              --> if (kbuilder.hasErrors()) { ==> AbstractKieProject.java:272
     *                                                      return new KnowledgePackagesBuildResult(true, null);
     *                                                  }
     *                                              获取编译后的规则包
     *                                              --> pkgs = kbuilder.getKnowledgePackages();
     *                                          }
     *                                          返回规则包构建结果
     *                                          --> return new KnowledgePackagesBuildResult(false, pkgs);
     *                                      如果规则包构建失败，KieBase 创建失败
     *                                      --> if (knowledgePackagesBuildResult.hasErrors()) {
     *                                              return null;
     *                                          }
     *                                      获取 KiePackage 集合
     *                                      --> Collection<KiePackage> pkgs = knowledgePackagesBuildResult.getPkgs();
     *                                      检查事件处理模式
     *                                      --> checkStreamMode(kBaseModel, conf, pkgs);
     *                                          作用:
     *                                              如果规则中需要 STREAM 模式能力, 但当前 KieBase 配置为 CLOUD 模式, 则抛出异常
     *                                      获取 ClassLoader
     *                                      --> ClassLoader cl = kieProject.getClassLoader();
     *                                      如果没有传入 KieBaseConfiguration, 则创建默认配置
     *                                      --> conf = getKnowledgeBaseConfiguration(kBaseModel, cl);
     *                                      创建 Drools 内部 RuleBase
     *                                      --> InternalRuleBase kBase = RuleBaseFactory.newRuleBase(kBaseModel.getName(), conf);
     *                                          作用:
     *                                              创建真正承载规则运行结构的 RuleBase
     *                                      把规则包加入 RuleBase
     *                                      --> kBase.addPackages(pkgs);
     *                                          作用:
     *                                              将前面构建出来的 KiePackage 加入 RuleBase
     *                                              在这个过程中，规则会被装配进 Drools 的运行时结构
     *                                          解释器模式体现:
     *                                              DRL 中的条件表达式:
     *                                                  Order(amount >= 1000, userLevel == "VIP")
     *                                              已经不再是字符串, 而是被构建成规则模型和运行时匹配结构
     *                                      包装成 KieBase 返回
     *                                      --> return KnowledgeBaseFactory.newKnowledgeBase(kBase); ==> AbstractKieModule.java:222
     *                              KieBase 创建完成后更新 KieModule
     *                              --> kModule.afterKieBaseCreationUpdate(kBaseModel.getName(), kBase); ==> KieContainerImpl.java:487
     *                              如果创建失败，返回 null
     *                              --> if (kBase == null) { ==> KieContainerImpl.java:489
     *                                      return null;
     *                                  }
     *                              设置 ReleaseId
     *                              --> kBase.setResolvedReleaseId(containerReleaseId);
     *                              设置容器 ID
     *                              --> kBase.setContainerId(containerId);
     *                              绑定 KieContainer
     *                              --> kBase.setKieContainer(this);
     *                              初始化 MBeans
     *                              --> kBase.initMBeans();
     *                              返回 KieBase
     *                              --> return kBase; ==> KieContainerImpl.java:501
     *              基于 KieBase 创建真正的 KieSession
     *              --> KieSession kSession = kBase.newKieSession( conf != null ? conf : getKieSessionConfiguration( kSessionModel ), environment ); ==> KieContainerImpl.java:610
     *                  作用:
     *                      创建规则执行上下文
     *                      内部包含 WorkingMemory、Agenda、FactHandle 管理等能力
     *              注册新创建的 KieSession
     *              --> registerNewKieSession(kSessionModel, (InternalKnowledgeBase) kBase, kSession); ==> KieContainerImpl.java:611
     *              返回 KieSession
     *              --> return kSession; ==> KieContainerImpl.java:612
     * 三、插入 Fact（事实对象进入 WorkingMemory，并登记传播动作）
     *  创建订单对象
     *      --> Order order = new Order(1L, "VIP", 1200);
     *  插入事实对象
     *      --> kieSession.insert(order);
     *          进入 StatefulKnowledgeSessionImpl
     *          --> StatefulKnowledgeSessionImpl.insert(order) ==> StatefulKnowledgeSessionImpl.class:925
     *              作用:
     *                  把普通 Java 对象插入 WorkingMemory
     *                  为该对象创建 FactHandle
     *                  注意: 这里不一定马上执行 AlphaNode.assertObject
     *              进入默认 EntryPoint 执行插入
     *              --> return this.entryPointsManager.getDefaultEntryPoint()
     *                      .insert(object, dynamic, rule, terminalNode); ==> StatefulKnowledgeSessionImpl.class:959
     *                  进入 NamedEntryPoint
     *                  --> NamedEntryPoint.insert(object, dynamic, rule, terminalNode)
     *                      获取对象类型配置
     *                      --> ObjectTypeConf typeConf = getObjectTypeConfigurationRegistry()
     *                              .getOrCreateObjectTypeConf(this.entryPoint, object);
     *                          作用:
     *                              判断当前对象属于哪种类型配置
     *                              当前对象是 Order，所以后续会匹配 Order 对应的 ObjectTypeNode
     *                      创建 FactHandle
     *                      --> handle = this.createHandle(object, typeConf); ==> NamedEntryPoint.class:194
     *                          作用:
     *                              Drools 不直接用 Java 对象在规则网络里传播
     *                              而是包装成 InternalFactHandle
     *                      继续插入
     *                      --> this.insert(handle, object, rule, typeConf, propagationContext); ==> NamedEntryPoint.class:230
     *                          作用:
     *                              把 FactHandle 保存到 ObjectStore
     *                              创建传播上下文 PropagationContext
     *                              准备把这个 Fact 传播进 Rete 网络
     *                          保存 FactHandle
     *                          --> this.objectStore.addHandle(handle, object); ==> NamedEntryPoint.java:264
     *                              作用:
     *                                  WorkingMemory 记录:
     *                                      Order 对象 <-> FactHandle
     *                          当前 Fact 进入 EntryPointNode
     *                          --> this.entryPointNode.assertObject(handle, pctx, typeConf, this.reteEvaluator); ==> NamedEntryPoint.class:265
     *                              作用:
     *                                  把 "插入 Order 事实对象" 这件事交给 Rete 网络入口
     *                              注意:
     *                                  在 Drools 8 中, 这里不一定同步直接进入 AlphaNode.assertObject。
     *                                  很多情况下会创建 PropagationEntry.Insert, 放入 PropagationList, 等 fireAllRules() 时 flush。
     * 四、执行 fireAllRules（flush 传播队列，匹配规则，执行 then）
     *  触发所有规则
     *      --> int count = kieSession.fireAllRules();
     *          --> StatefulKnowledgeSessionImpl.fireAllRules() ==> StatefulKnowledgeSessionImpl.java:1062
     *              进入 Agenda 执行规则循环
     *              --> return internalFireAllRules(agendaFilter, fireLimit); ==> StatefulKnowledgeSessionImpl.java:1079
     *                  --> fireCount = this.agenda.fireAllRules( agendaFilter, fireLimit ); ==> StatefulKnowledgeSessionImpl.java:1088
     *                      --> DefaultAgenda.fireAllRules(...) ==> DefaultAgenda.java:556
     *                          进入规则执行循环
     *                          --> return fireLoop( agendaFilter, fireLimit, RestHandler.FIRE_ALL_RULES, isInternalFire ); ==> DefaultAgenda.java:571
     *                              先 flush 传播队列
     *                              --> propagationList.flush(head); ==> DefaultAgenda.java:608
     *                                  作用:
     *                                      执行 insert 阶段登记的传播动作
     *                                      这个时候 Fact 才真正沿 Rete 网络继续向下传播
     *                                  执行插入传播动作
     *                                  --> entry.execute(reteEvaluator); ==> SynchronizedPropagationList.class:83
     *                                      --> PropagationEntry.Insert.propagate(...)
     *                                          把 Fact 传播给 ObjectTypeNode
     *                                          --> ObjectTypeNode.propagateAssert(...)
     *                                              作用:
     *                                                  判断当前 Fact 是否属于规则中声明的对象类型
     *                                              当前判断:
     *                                                  Order(amount >= 1000, userLevel == "VIP")
     *                                                  先匹配对象类型 Order
     *                                              继续传播到后续 ObjectSink
     *                                              --> sink.propagateAssertObject(...)
     *                                                  常见实现:
     *                                                      CompositeObjectSinkAdapter.propagateAssertObject(...)
     *                                                  作用:
     *                                                      根据 ObjectTypeNode 后面的节点结构，
     *                                                      把 Fact 分发给 AlphaNode / BetaNode / LeftInputAdapterNode 等
     *                                                  字段约束判断
     *                                                  --> AlphaNode.assertObject(...)
     *                                                      作用:
     *                                                          执行单对象字段条件判断
     *                                                      当前可能判断:
     *                                                          amount >= 1000
     *                                                          userLevel == "VIP"
     *                                                  条件全部匹配后进入终端节点
     *                                                  --> RuleTerminalNode.assertLeftTuple(...)
     *                                                      作用:
     *                                                          表示当前 Fact 满足某条规则的 when 条件
     *                                                          创建规则激活对象
     *                                                          放入 Agenda
     *                                                      当前命中规则:
     *                                                          "VIP订单满1000打8折"
     *                              从 Agenda 中取出已激活规则
     *                              --> RuleExecutor.fire(...)
     *                                  作用:
     *                                      真正执行命中的规则
     *                                  执行 then 部分
     *                                  --> Consequence.evaluate(...)
     *                                      当前执行:
     *                                          $order.setDiscount(0.8);
     *                                          $order.setRemark("命中规则: VIP订单满1000打8折");
     * 五、释放 KieSession
     *  释放规则会话
     *      --> kieSession.dispose();
     *          进入 StatefulKnowledgeSessionImpl
     *          --> StatefulKnowledgeSessionImpl.dispose() ==> StatefulKnowledgeSessionImpl.java:460
     *          作用:
     *              释放 WorkingMemory
     *              释放 Agenda
     *              清理当前规则会话资源
     */
    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("orderKSession");

        try {
            Order order1 = new Order(1L, "VIP", 1200);
            Order order2 = new Order(2L, "NORMAL", 800);

            // 插入 Fact, 把 Order 放入 Working Memory
            kieSession.insert(order1);
            kieSession.insert(order2);

            // 执行所有满足条件的规则
            int count = kieSession.fireAllRules();

            System.out.println("规则触发数量: " + count);
            System.out.println("order1 折扣: " + order1.getDiscount());
            System.out.println("order1 说明: " + order1.getRemark());
            System.out.println("order2 折扣: " + order2.getDiscount());
            System.out.println("order2 说明: " + order2.getRemark());
        } finally {
            // 释放 KieSession, 清理 Working Memory，释放会话资源
            kieSession.dispose();
        }
    }

}
