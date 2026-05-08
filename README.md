java23种设计模式


## 行为型模式

TODO



------------------------------------------------------------------------------------------------------------------------


### 策略模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/strategy`

#### 概要

通过促销策略和线程池拒绝策略演示策略模式的实际应用。包含Demo（不同促销策略：A、B、C）和框架源码分析（ThreadPoolExecutor 的 RejectedExecutionHandler 拒绝策略）。

#### 核心思想

策略模式：该模式定义了一系列算法，并将每个算法封装起来，使它们可以相互替换，且算法的变化不会影响使用算法的客户。策略模式属于对象行为模式，它通过对算法进行封装，把使用算法的责任和算法的实现分割开来，并委派给不同的对象对这些算法进行管理。

核心要素：
- **抽象策略（Strategy）类**：这是一个抽象角色，通常由一个接口或抽象类实现。此角色给出所有的具体策略类所需的接口
- **具体策略（ConcreteStrategy）类**：实现了抽象策略定义的接口，提供具体的算法实现或行为
- **环境（Context）类**：持有一个策略类的引用，最终给客户端调用

#### 本案例讲解

**Demo（促销策略）**：

通过不同促销策略展示策略模式的基本用法：

1. **抽象策略（Strategy）**：定义 show 方法
2. **具体策略（StrategyA、StrategyB、StrategyC）**：实现不同的促销算法
3. **环境类（SalesMan）**：持有策略引用，调用策略方法
4. **策略切换**：通过 setStrategy 方法动态切换策略

**框架源码（线程池拒绝策略）**：

通过 ThreadPoolExecutor 拒绝策略展示策略模式在框架中的应用：

1. **RejectedExecutionHandler**：抽象策略接口，定义 rejectedExecution 方法
2. **AbortPolicy**：具体策略，抛出 RejectedExecutionException 异常
3. **CallerRunsPolicy**：具体策略，由调用线程执行任务
4. **DiscardPolicy**：具体策略，直接丢弃任务
5. **DiscardOldestPolicy**：具体策略，丢弃队列最老任务，然后重新提交

#### 使用的框架/源码

- **Java ThreadPoolExecutor**：线程池，使用策略模式处理任务拒绝
- **RejectedExecutionHandler**：拒绝策略接口，定义统一拒绝行为
- **AbortPolicy**：中止策略，抛出异常拒绝任务
- **CallerRunsPolicy**：调用者运行策略，由调用线程执行任务
- **DiscardPolicy**：丢弃策略，静默丢弃任务
- **DiscardOldestPolicy**：丢弃最老策略，丢弃队列头部任务

#### 策略模式体现

| 策略模式角色 | Demo | ThreadPoolExecutor 应用 |
|------------|---------|------------------------|
| Strategy | Strategy | RejectedExecutionHandler |
| ConcreteStrategy | StrategyA、StrategyB、StrategyC | AbortPolicy、CallerRunsPolicy 等 |
| Context | SalesMan | ThreadPoolExecutor |
| 策略方法 | show() | rejectedExecution() |
| 策略切换 | setStrategy() | 构造函数传入 |

#### 运行效果

**Demo**：
```
展示策略A
==============
展示策略C
```

**ThreadPoolExecutor 拒绝策略**：
```
执行任务: 0
任务被拒绝: 执行任务: 2
执行任务: 1
```

#### 学习收获

1. **策略模式的核心价值**：定义一系列算法，封装起来，使它们可以相互替换，避免大量 if-else
2. **ThreadPoolExecutor 的应用场景**：通过 RejectedExecutionHandler 实现不同的任务拒绝策略，满足不同业务需求
3. **策略切换**：运行时动态切换策略，灵活应对不同场景
4. **开闭原则**：新增策略只需新增具体策略类，无需修改原有代码
5. **实际应用场景**：支付方式选择、排序算法选择、压缩算法选择、路由策略


------------------------------------------------------------------------------------------------------------------------


### 命令模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/command`

#### 概要

通过餐厅点餐和 MyBatis Mapper 方法调用演示命令模式的实际应用。包含Demo（服务员传递订单给厨师）和框架源码分析（MyBatis 将 Mapper 方法调用封装为 MappedStatement 命令）。

#### 核心思想

命令模式：把"请求"封装成对象，从而让你可以参数化、排队、记录、撤销请求，使发出请求的责任和执行请求的责任分割开。

核心要素：
- **抽象命令类（Command）角色**：定义命令的接口，声明执行的方法
- **具体命令（ConcreteCommand）角色**：具体的命令，实现命令接口；通常会持有接收者，并调用接收者的功能来完成命令要执行的操作
- **实现者/接收者（Receiver）角色**：接收者，真正执行命令的对象。任何类都可能成为一个接收者，只要它能够实现命令要求实现的相应功能
- **调用者/请求者（Invoker）角色**：要求命令对象执行请求，通常会持有命令对象，可以持有很多的命令对象

#### 本案例讲解

**Demo（餐厅点餐）**：

通过服务员传递订单给厨师展示命令模式的基本用法：

1. **抽象命令（Command）**：定义 execute 方法
2. **具体命令（OrderCommand）**：持有订单和厨师引用，调用厨师做菜
3. **接收者（SeniorChef）**：厨师，真正执行做菜操作
4. **调用者（Waiter）**：服务员，持有多个命令，统一发起执行

**框架源码（MyBatis Mapper 方法调用）**：

通过 MyBatis Mapper 代理展示命令模式在框架中的应用：

1. **MappedStatement**：命令对象，封装 SQL + 参数映射 + 结果映射
2. **MapperProxy**：调用者，JDK 动态代理，拦截 Mapper 方法调用
3. **Executor**：接收者，真正执行 SQL 查询
4. **MapperMethod**：命令分发，根据方法类型调用 SqlSession 对应方法

#### 使用的框架/源码

- **MyBatis**：ORM 框架，将 Mapper 方法调用封装为命令
- **MappedStatement**：映射语句，封装 SQL 语句和配置信息
- **MapperProxy**：Mapper 代理，拦截方法调用
- **Executor**：执行器，真正执行 SQL
- **MapperMethod**：Mapper 方法，封装方法签名和参数转换
- **SqlSession**：SQL 会话，提供数据库操作接口

#### 命令模式体现

| 命令模式角色 | Demo | MyBatis 应用 |
|------------|---------|-------------|
| Command | Command | MappedStatement |
| ConcreteCommand | OrderCommand | MapperMethod |
| Receiver | SeniorChef | Executor |
| Invoker | Waiter | MapperProxy |
| 执行方法 | execute() | execute() |
| 客户端 | CommandPattern | main() |

#### 运行效果

**Demo**：
```
服务员: 后厨, 新订单来了
1号桌订单: 西红柿鸡蛋面 1份
1号桌订单: 小杯可乐 2杯
2号桌订单: 尖椒肉丝盖饭 1份
2号桌订单: 小杯雪碧 1杯
```

**MyBatis Mapper 调用**：
```
class jdk.proxy2.$Proxy4
user: root
```

#### 学习收获

1. **命令模式的核心价值**：将请求封装为对象，实现请求发送者和执行者的解耦
2. **MyBatis 的应用场景**：通过 MapperProxy 拦截方法调用，封装为 MappedStatement 命令，交给 Executor 执行
3. **命令队列**：可以存储多个命令，实现批量执行、延迟执行
4. **撤销重做**：结合备忘录模式，可以实现命令的撤销和恢复
5. **实际应用场景**：GUI 按钮点击、任务调度、事务管理、工作流引擎


------------------------------------------------------------------------------------------------------------------------


### 责任链模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/responsibility`

#### 概要

通过请假审批流程和 Spring AOP 拦截器链演示责任链模式的实际应用。包含Demo（组长、经理、总经理多级审批）和框架源码分析（Spring AOP MethodInterceptor 递归责任链）。

#### 核心思想

责任链模式：又名职责链模式，为了避免请求发送者与多个请求处理者耦合在一起，将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链；当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止。

核心要素：
- **抽象处理者（Handler）角色**：定义一个处理请求的接口，包含抽象处理方法和一个后继连接
- **具体处理者（ConcreteHandler）角色**：实现抽象处理者的处理方法，判断能否处理本次请求，如果可以处理请求则处理，否则将该请求转给它的后继者
- **客户类（Client）角色**：创建处理链，并向链头的具体处理者对象提交请求，它不关心处理细节和请求的传递过程

#### 本案例讲解

**Demo（请假审批流程）**：

通过组长、经理、总经理多级审批展示责任链模式的基本用法：

1. **抽象处理者（Handler）**：定义 handleLeave 抽象方法和 setNextHandler 方法
2. **具体处理者（GroupLeader、Manager、GeneralManager）**：实现审批逻辑，判断请假天数是否在权限范围内
3. **责任链构建**：groupLeader.setNextHandler(manager); manager.setNextHandler(generalManager);
4. **请求传递**：如果当前处理者无法处理，传递给下一个处理者

**框架源码（Spring AOP 拦截器链）**：

通过 Spring AOP MethodInterceptor 展示责任链模式在框架中的应用：

1. **MethodInterceptor**：拦截器接口，定义 invoke 方法
2. **ReflectiveMethodInvocation**：方法调用对象，维护拦截器链和当前索引
3. **递归调用**：每个拦截器调用 invocation.proceed()，触发下一个拦截器
4. **执行顺序**：前置通知按责任链正序进入，后置通知按调用栈逆序返回

#### 使用的框架/源码

- **Spring AOP**：面向切面编程，通过代理实现增强
- **MethodInterceptor**：方法拦截器接口，拦截方法调用
- **ReflectiveMethodInvocation**：反射方法调用，维护拦截器链
- **CglibAopProxy**：CGLIB 代理，创建代理对象
- **DynamicAdvisedInterceptor**：动态通知拦截器，适配各种 Advice
- **Advisor**：通知者，包含 Pointcut 和 Advice

#### 责任链模式体现

| 责任链模式角色 | Demo | Spring AOP 应用 |
|--------------|---------|----------------|
| Handler | Handler | MethodInterceptor |
| ConcreteHandler | GroupLeader、Manager | Aspect1、Aspect2 拦截器 |
| Request | LeaveRequest | MethodInvocation |
| Client | ChainOfResponsibilityPattern | 代理对象 |
| 处理方法 | handleLeave() | invoke() |
| 传递方法 | submit() | proceed() |

#### 运行效果

**Demo**：
```
组长审批: 吕阳, 天数: 2, 原因: 被知见障蒙蔽了
流程结束!
```

**Spring AOP 拦截器链**：
```
Aspect1 前置
Aspect2 前置
执行目标方法...
Aspect2 后置
Aspect1 后置
```

#### 学习收获

1. **责任链模式的核心价值**：将请求发送者和接收者解耦，请求沿着链传递直到被处理
2. **Spring AOP 的应用场景**：通过拦截器链实现前置通知、后置通知、异常通知等增强逻辑
3. **递归责任链**：不是简单循环，而是递归调用，前置正序进入，后置逆序返回
4. **灵活扩展**：可以动态增加、删除、调整处理者顺序，满足开闭原则
5. **实际应用场景**：Servlet Filter、Spring Interceptor、日志处理链、权限校验链


------------------------------------------------------------------------------------------------------------------------


### 状态模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/state`

#### 概要

通过电梯状态控制和 Tomcat 生命周期管理演示状态模式的实际应用。包含Demo（电梯开关门运行停止）和框架源码分析（Tomcat LifecycleBase 状态流转控制）。

#### 核心思想

状态模式：对有状态的对象，把复杂的"判断逻辑"提取到不同的状态对象中，允许状态对象在其内部状态发生改变时改变其行为。

核心要素：
- **环境（Context）角色**：也称为上下文，它定义了客户程序需要的接口，维护一个当前状态，并将与状态相关的操作委托给当前状态对象来处理
- **抽象状态（State）角色**：定义一个接口，用以封装环境对象中的特定状态所对应的行为
- **具体状态（ConcreteState）角色**：实现抽象状态所对应的行为

#### 本案例讲解

**Demo（电梯状态控制）**：

通过电梯的开关门、运行、停止展示状态模式的基本用法：

1. **环境角色（Lift）**：维护当前状态，提供 open、close、run、stop 方法
2. **状态常量**：OPENING_STATE、CLOSING_STATE、RUNNING_STATE、STOPPING_STATE
3. **状态判断**：通过 switch-case 判断当前状态，决定是否执行操作
4. **状态流转**：操作成功后更新状态

**框架源码（Tomcat 生命周期）**：

通过 Tomcat LifecycleBase 状态流转展示状态模式在框架中的应用：

1. **LifecycleState 枚举**：定义组件状态（NEW、INITIALIZED、STARTED、STOPPED、DESTROYED 等）
2. **LifecycleBase**：统一管理状态流转，检查状态合法性
3. **状态检查**：执行操作前检查当前状态是否允许该操作
4. **模板方法**：子类实现 initInternal、startInternal、stopInternal、destroyInternal

#### 使用的框架/源码

- **Tomcat**：Web 容器，管理组件生命周期
- **LifecycleState**：生命周期状态枚举，定义所有可能的状态
- **LifecycleBase**：生命周期基类，统一管理状态流转
- **LifecycleException**：生命周期异常，非法状态转换时抛出
- **LifecycleListener**：生命周期监听器，监听状态变化事件
- **LifecycleEvent**：生命周期事件，封装状态变化信息

#### 状态模式体现

| 状态模式角色 | Demo | Tomcat 生命周期 |
|------------|---------|----------------|
| Context | Lift | LifecycleBase |
| State | ILift（状态常量） | LifecycleState 枚举 |
| ConcreteState | OPENING_STATE 等 | NEW、INITIALIZED、STARTED 等 |
| 状态判断 | switch-case | if-else 状态检查 |
| 状态流转 | setState() | setStateInternal() |

#### 运行效果

**Demo**：
```
电梯停止了...
```

**Tomcat 生命周期**：
```
初始状态: NEW

========== init() ==========
当前状态: INITIALIZED

========== start() ==========
当前状态: STARTED

========== stop() ==========
当前状态: STOPPED

========== destroy() ==========
当前状态: DESTROYED

========== destroy 后再 start() ==========
LifecycleException: 组件已销毁，无法启动
```

#### 学习收获

1. **状态模式的核心价值**：将状态判断逻辑分散到各个状态类中，避免大量的 if-else 或 switch-case
2. **Tomcat 的应用场景**：通过 LifecycleBase 统一管理组件生命周期，确保状态流转的合法性
3. **状态流转控制**：执行操作前检查当前状态，非法状态转换抛出异常
4. **模板方法组合**：状态模式 + 模板方法模式，LifecycleBase 固定骨架，子类实现具体行为
5. **实际应用场景**：订单状态流转、线程状态管理、TCP 连接状态、工作流引擎


------------------------------------------------------------------------------------------------------------------------


### 观察者模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/observer`

#### 概要

通过微信公众号订阅和 Spring 事件驱动机制演示观察者模式的实际应用。包含Demo（微信用户订阅公众号）和框架源码分析（Spring ApplicationEventPublisher 发布事件，ApplicationListener 监听事件）。

#### 核心思想

观察者模式：又被称为发布-订阅（Publish/Subscribe）模式，它定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态变化时，会通知所有的观察者对象，使他们能够自动更新自己。

核心要素：
- **抽象主题（Subject）**：把所有观察者对象保存在一个集合里，可以增加和删除观察者对象
- **具体主题（ConcreteSubject）**：在内部状态发生改变时，给所有注册过的观察者发送通知
- **抽象观察者（Observer）**：定义一个更新接口，使得在得到主题更改通知时更新自己
- **具体观察者（ConcreteObserver）**：实现抽象观察者定义的更新接口，以便在得到主题更改通知时更新自身的状态

#### 本案例讲解

**Demo（微信公众号订阅）**：

通过微信用户订阅公众号展示观察者模式的基本用法：

1. **抽象主题（Subject）**：定义 attach、detach、notify 方法
2. **具体主题（SubscriptionSubject）**：维护微信用户列表，发布消息通知所有订阅者
3. **抽象观察者（Observer）**：定义 update 方法
4. **具体观察者（WeiXinUser）**：实现 update 方法，接收公众号消息

**框架源码（Spring 事件驱动）**：

通过 Spring ApplicationEventPublisher 和 ApplicationListener 展示观察者模式在框架中的应用：

1. **ApplicationEventPublisher**：事件发布者，发布事件到事件广播器
2. **ApplicationEventMulticaster**：事件广播器，根据事件类型筛选监听器
3. **ApplicationListener**：事件监听器接口，监听特定类型的事件
4. **SimpleApplicationEventMulticaster**：默认事件广播器，支持同步/异步执行

#### 使用的框架/源码

- **Spring Framework**：IoC 容器，管理事件发布者和监听器
- **ApplicationEventPublisher**：事件发布接口，发布事件
- **ApplicationEventMulticaster**：事件广播器，广播事件给监听器
- **ApplicationListener**：事件监听器接口，处理事件
- **SimpleApplicationEventMulticaster**：默认事件广播器实现
- **ApplicationEvent**：事件基类，所有事件继承此类

#### 观察者模式体现

| 观察者模式角色 | Demo | Spring 事件驱动 |
|--------------|---------|----------------|
| Subject | Subject | ApplicationEventPublisher |
| ConcreteSubject | SubscriptionSubject | ApplicationContext |
| Observer | Observer | ApplicationListener |
| ConcreteObserver | WeiXinUser | SmsListener |
| 通知行为 | notify() | publishEvent() |
| 更新行为 | update() | onApplicationEvent() |

#### 运行效果

**Demo**：
```
石昊收到消息: AI又更新了！
樊任收到消息: AI又更新了！
WOW收到消息: AI又更新了！
```

**Spring 事件驱动**：
```
订单创建成功: ORDER_001
发送短信通知: 订单 ORDER_001 已创建
```

#### 学习收获

1. **观察者模式的核心价值**：定义对象间的一对多依赖关系，当一个对象状态改变时，所有依赖它的对象都会收到通知
2. **Spring 的应用场景**：通过事件机制实现业务解耦，订单创建后发送短信通知，无需在订单服务中直接调用短信服务
3. **广播机制**：一个事件可以有多个监听器，实现广播通知
4. **松散耦合**：发布者和监听者不直接依赖，通过事件类型匹配
5. **实际应用场景**：GUI 事件处理、消息推送、日志监听、配置变更通知


------------------------------------------------------------------------------------------------------------------------


### 中介者模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/mediator`

#### 概要

通过房屋租赁中介和 Spring MVC DispatcherServlet 演示中介者模式的实际应用。包含 | Demo（房主和租户通过中介沟通）和框架源码分析（DispatcherServlet 协调 HandlerMapping、HandlerAdapter、ViewResolver 等组件）。

#### 核心思想

中介者模式：又叫调停模式，定义一个中介角色来封装一系列对象之间的交互，使原有对象之间的耦合松散，且可以独立地改变它们之间的交互。

核心要素：
- **抽象中介者（Mediator）**：提供同事对象注册与转发同事对象信息的抽象方法
- **具体中介者（ConcreteMediator）**：实现中介者接口，协调各个同事角色之间的交互关系
- **抽象同事类（Colleague）**：定义同事类的接口，保存中介者对象，提供同事对象交互的抽象方法
- **具体同事类（ConcreteColleague）**：是抽象同事类的实现者，当需要与其他同事对象交互时，由中介者对象负责后续的交互

#### 本案例讲解

**Demo（房屋租赁中介）**：

通过房主和租户通过中介沟通展示中介者模式的基本用法：

1. **抽象中介者（Mediator）**：定义 contact 方法，转发消息
2. **具体中介者（MediatorStructure）**：维护房主和租户引用，协调双方沟通
3. **抽象同事类（Person）**：保存中介者对象，提供 contact 方法
4. **具体同事类（HouseOwner、Tenant）**：通过中介者与其他同事对象交互

**框架源码（Spring MVC DispatcherServlet）**：

通过 DispatcherServlet 协调各组件展示中介者模式在框架中的应用：

1. **DispatcherServlet**：作为中介者，协调 HandlerMapping、HandlerAdapter、ViewResolver 等组件
2. **HandlerMapping**：根据请求路径找到 Controller 方法
3. **HandlerAdapter**：适配并执行 Controller 方法
4. **HandlerExecutionChain**：保存 Handler + Interceptor 链
5. **ViewResolver**：解析视图，返回 View 对象

#### 使用的框架/源码

- **Spring MVC**：Web 框架，DispatcherServlet 作为核心调度器
- **DispatcherServlet**：前端控制器，协调各组件处理请求
- **HandlerMapping**：处理器映射器，根据 URL 找到 Controller
- **HandlerAdapter**：处理器适配器，执行 Controller 方法
- **HandlerExecutionChain**：处理器执行链，包含 Handler 和 Interceptor
- **ViewResolver**：视图解析器，解析视图名称返回 View

#### 中介者模式体现

| 中介者模式角色 | Demo | Spring MVC 应用 |
|--------------|---------|----------------|
| Mediator | Mediator | DispatcherServlet |
| ConcreteMediator | MediatorStructure | DispatcherServlet 实例 |
| Colleague | Person | HandlerMapping、HandlerAdapter、ViewResolver |
| ConcreteColleague | HouseOwner、Tenant | RequestMappingHandlerMapping、RequestMappingHandlerAdapter |
| 协调行为 | contact() | doDispatch() |

#### 运行效果

**Demo**：
```
房主收到信息: 我要租房
租户收到信息: 我有, 来
```

**Spring MVC DispatcherServlet**：
```
请求: GET /hello?name=world
1. HandlerMapping 找到 HelloController#hello(String)
2. HandlerAdapter 执行 Controller 方法
3. ViewResolver 解析视图
4. 返回响应: "Hello, world"
```

#### 学习收获

1. **中介者模式的核心价值**：将多对多的复杂引用关系简化为一对一，降低系统耦合度
2. **Spring MVC 的应用场景**：DispatcherServlet 作为中介者，协调各组件处理请求，避免组件间直接依赖
3. **集中控制交互**：所有组件间的交互都通过中介者集中管理，便于维护和扩展
4. **松散耦合**：组件间不直接依赖，可以独立变化和复用
5. **实际应用场景**：MVC 框架、GUI 组件交互、聊天室系统


------------------------------------------------------------------------------------------------------------------------


### 迭代器模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/iterator`

#### 概要

通过学生列表遍历和 MyBatis ResultSet 遍历演示迭代器模式的实际应用。包含Demo（学生聚合遍历）和框架源码分析（MyBatis 如何遍历 JDBC ResultSet）。

#### 核心思想

迭代器模式：提供一个对象来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。

核心要素：
- **抽象聚合（Aggregate）**：定义存储、添加、删除聚合元素以及创建迭代器对象的接口
- **具体聚合（ConcreteAggregate）**：实现抽象聚合类，返回一个具体迭代器的实例
- **抽象迭代器（Iterator）**：定义访问和遍历聚合元素的接口，通常包含 hasNext()、next() 等方法
- **具体迭代器（ConcreteIterator）**：实现抽象迭代器接口中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置

#### 本案例讲解

**Demo（学生列表遍历）**：

通过学生聚合对象和迭代器展示迭代器模式的基本用法：

1. **抽象聚合（StudentAggregate）**：定义添加、删除学生和获取迭代器的方法
2. **具体聚合（StudentAggregateImpl）**：维护学生列表，返回具体迭代器实例
3. **抽象迭代器（StudentIterator）**：定义 hasNext() 和 next() 方法
4. **具体迭代器（StudentIteratorImpl）**：维护当前位置，实现遍历逻辑

**框架源码（MyBatis ResultSet 遍历）**：

通过 MyBatis 处理 JDBC ResultSet 展示迭代器模式在框架中的应用：

1. **JDBC ResultSet**：作为具体迭代器，提供 next() 方法移动游标
2. **DefaultResultSetHandler**：作为客户端，使用 resultSet.next() 遍历结果集
3. **ResultSetWrapper**：包装 ResultSet，保存列名、类型等信息
4. **结果转换**：每一行 ResultSet 转换为 Java 对象，加入 List

#### 使用的框架/源码

- **MyBatis**：ORM 框架，处理数据库查询结果
- **JDBC ResultSet**：数据库结果集，提供游标遍历能力
- **DefaultResultSetHandler**：MyBatis 结果集处理器
- **ResultSetWrapper**：ResultSet 包装类，保存元数据
- **PreparedStatementHandler**：SQL 执行处理器
- **TypeHandler**：类型转换器，处理 JDBC 类型到 Java 类型的映射

#### 迭代器模式体现

| 迭代器模式角色 | Demo | MyBatis 应用 |
|--------------|---------|-------------|
| Aggregate | StudentAggregate | 数据库查询结果集 |
| ConcreteAggregate | StudentAggregateImpl | JDBC ResultSet |
| Iterator | StudentIterator | Iterator 思想 |
| ConcreteIterator | StudentIteratorImpl | JDBC ResultSet（next 方法） |
| hasNext() | iterator.hasNext() | resultSet.next() |
| next() | iterator.next() | resultSet 当前行数据 |
| Client | IteratorPattern.main() | DefaultResultSetHandler |

#### 运行效果

**Demo**：
```
Student{name='吕阳', number='001'}
Student{name='刘长', number='002'}
Student{name='克莱恩', number='003'}
Student{name='荒天帝', number='004'}
```

**MyBatis ResultSet 遍历**：
```
MysqlUser{id=1, name='user1', age=20}
MysqlUser{id=2, name='user2', age=21}
MysqlUser{id=3, name='user3', age=22}
...
```

#### 学习收获

1. **迭代器模式的核心价值**：将聚合对象的内部结构与遍历行为解耦，客户端无需了解内部实现
2. **MyBatis 的应用场景**：通过 JDBC ResultSet 的 next() 方法遍历数据库结果，转换为 Java 对象
3. **统一遍历接口**：不同的聚合结构可以提供相同的遍历接口，简化客户端代码
4. **扩展性优势**：新增遍历方式只需新增迭代器，无需修改聚合类
5. **实际应用场景**：Java Collections Framework、数据库结果集遍历、文件系统目录遍历


------------------------------------------------------------------------------------------------------------------------


### 访问者模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/visitor`

#### 概要

通过 Spring BeanDefinitionVisitor 演示访问者模式在框架中的实际应用。BeanDefinition 保存 Bean 定义元数据，BeanDefinitionVisitor 遍历访问这些元数据，StringValueResolver 负责对字符串值进行解析和替换，实现数据结构与访问行为的解耦。

#### 核心思想

访问者模式：表示一个作用于某对象结构中的各元素的操作。它使你可以在不改变各元素的类的前提下定义作用于这些元素的新操作。

核心要素：
- **访问者（Visitor）**：为对象结构中的每个具体元素声明一个访问操作
- **具体访问者（ConcreteVisitor）**：实现 Visitor 声明的访问操作
- **元素（Element）**：定义一个 accept 方法，接受访问者访问
- **具体元素（ConcreteElement）**：实现 accept 方法，调用访问者的 visit 方法
- **对象结构（ObjectStructure）**：能枚举它的元素，提供一个高层接口让访问者访问其元素

#### 本案例讲解

通过 Spring BeanDefinitionVisitor 的完整执行流程展示访问者模式在框架中的应用：

1. **BeanDefinition 注册**：手动注册包含占位符 `${demo.name}` 和 `${demo.url}` 的 BeanDefinition
2. **访问者遍历**：BeanDefinitionVisitor 遍历 BeanDefinition 的所有属性值
3. **字符串解析**：StringValueResolver 解析占位符，替换为真实值
4. **Bean 创建**：使用替换后的属性值创建 Bean 实例

#### 使用的框架/源码

- **Spring Framework**：IoC 容器，管理 Bean 生命周期
- **BeanDefinition**：保存 Bean 定义元数据（beanClass、scope、propertyValues 等）
- **BeanDefinitionVisitor**：遍历访问 BeanDefinition 的元数据
- **StringValueResolver**：字符串值解析策略接口
- **Environment**：环境抽象，提供属性解析功能
- **BeanDefinitionRegistryPostProcessor**：BeanDefinition 注册后置处理器

#### 访问者模式体现

| 访问者模式角色 | Spring 对应实现 |
|--------------|----------------|
| Visitor | BeanDefinitionVisitor（访问者接口） |
| ConcreteVisitor | BeanDefinitionVisitor 实例（具体访问者） |
| Element | BeanDefinition（被访问元素） |
| ConcreteElement | GenericBeanDefinition（具体元素） |
| ObjectStructure | BeanDefinition 内部的元数据结构 |
| 访问行为 | visitBeanDefinition、visitPropertyValues、resolveValue |
| 处理策略 | StringValueResolver（字符串解析策略） |

#### 运行效果

```
========== BeanDefinitionVisitor 开始访问 ==========
访问前:
MutablePropertyValues: {name=${demo.name}, url=${demo.url}}
正在访问并解析: ${demo.name}
正在访问并解析: ${demo.url}
访问后:
MutablePropertyValues: {name=fanjiabao, url=jdbc:mysql://localhost:3306/test}

执行 setName: fanjiabao
执行 setUrl: jdbc:mysql://localhost:3306/test

========== Bean 创建完成 ==========
DataSourceConfigBean{name='fanjiabao', url='jdbc:mysql://localhost:3306/test'}
```

#### 学习收获

1. **访问者模式的核心价值**：将数据结构与作用于结构上的操作解耦，使得操作集合可以相对自由地演化
2. **Spring 的应用场景**：BeanDefinitionVisitor 在不修改 BeanDefinition 的情况下，实现了属性值的遍历和替换
3. **策略模式的组合**：StringValueResolver 作为策略接口，使得字符串解析逻辑可替换
4. **扩展性优势**：新增访问操作只需新增 Visitor，无需修改 Element 类
5. **实际应用场景**：编译器的语法树遍历、文档结构遍历、数据库查询计划优化


------------------------------------------------------------------------------------------------------------------------


### 备忘录模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/memento`

#### 概要

通过游戏角色状态备份和 Hibernate 脏检查机制演示备忘录模式的实际应用。包含白箱备忘录、黑箱备忘录两种实现方式，以及 Hibernate/JPA 中 EntityEntry.loadedState 的源码分析。

#### 核心思想

在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便以后当需要时能将该对象恢复到原先保存的状态。

核心要素：
- **发起人（Originator）**：创建备忘录，记录当前内部状态，并可使用备忘录恢复状态
- **备忘录（Memento）**：负责存储发起人对象的内部状态
- **管理者（Caretaker）**：负责保存备忘录，但不能对备忘录内容进行操作或检查

#### 本案例讲解

1. **白箱备忘录**：备忘录类完全公开，发起人和管理者都可以访问备忘录的内部状态
2. **黑箱备忘录**：备忘录类作为发起人的内部类，对外只暴露接口，管理者无法访问备忘录的内部状态
3. **Hibernate 脏检查**：查询 Entity 时保存 loadedState 快照，提交事务时对比当前状态和快照，判断是否需要执行 update

#### 使用的框架/源码

- **Spring Boot**：快速启动应用，管理 Bean 生命周期
- **Spring Data JPA**：简化数据库操作，提供 Repository 接口
- **Hibernate**：ORM 框架，实现脏检查机制
- **EntityEntry.loadedState**：Hibernate 保存的实体状态快照
- **DirtyHelper.findDirty()**：属性差异比较工具
- **ActionQueue**：统一管理 insert、update、delete 等动作

#### 备忘录模式体现

| 备忘录模式角色 | 游戏角色示例 | Hibernate 脏检查 |
|--------------|-------------|-----------------|
| Originator | GameRole（游戏角色） | UserEntity（实体对象） |
| Memento | RoleStateMemento（角色状态） | EntityEntry.loadedState（快照） |
| Caretaker | RoleStateCaretaker（管理者） | PersistenceContext（持久化上下文） |

#### 运行效果

**白箱备忘录**：
```
---------------大战boos前-----------------
角色生命力: 100
角色攻击力: 100
角色防御力: 100
---------------大战boos后-----------------
角色生命力: 0
角色攻击力: 0
角色防御力: 0
---------------恢复之前的状态-----------------
角色生命力: 100
角色攻击力: 100
角色防御力: 100
```

**Hibernate 脏检查**：
```
查询时保存快照: loadedState = ["fanjiabao", 24]
修改后当前状态: values = ["fanjiabao-update", 20]
脏检查对比: username 和 age 都发生变化
执行 update SQL: update tb_user set age=?, username=? where id=?
```

#### 学习收获

1. **备忘录模式的两种实现**：白箱简单直接但破坏封装性，黑箱通过接口隔离保护内部状态
2. **Hibernate 脏检查原理**：查询时保存快照，提交时对比，只更新变化的字段
3. **状态快照的应用场景**：游戏存档、文档撤销/重做、数据库事务回滚、配置版本管理
4. **性能优化思路**：只保存必要状态、增量保存、按需恢复
5. **设计模式组合应用**：备忘录模式 + 命令模式（撤销/重做）、备忘录模式 + 原型模式（克隆快照）


------------------------------------------------------------------------------------------------------------------------


### 解释器模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/interpreter`

#### 概要

通过 Drools 规则引擎演示解释器模式应用。展示了规则引擎如何将业务规则表达式解析为可执行的规则模型，并通过 Rete 网络进行高效匹配。

#### 核心思想

给定一门语言，定义其文法表示，并定义一个解释器，该解释器使用该表示来解释语言中的句子。

核心要素：
- **抽象表达式（AbstractExpression）**：声明解释操作
- **终结符表达式（TerminalExpression）**：实现与文法中终结符相关联的解释操作
- **非终结符表达式（NonTerminalExpression）**：实现与文法中非终结符相关联的解释操作
- **上下文（Context）**：包含解释器之外的一些全局信息

#### 本案例讲解

通过 Drools 规则引擎的完整执行流程展示解释器模式在企业级应用中的体现：

1. **规则语言（DRL）**：Drools Rule Language 作为领域特定语言
2. **规则解析**：DRLParser 将规则文本解析为内部规则模型
3. **规则执行**：Rete 网络作为高效的模式匹配算法
4. **事实对象（Fact）**：Order 对象作为上下文数据

#### 使用的框架/源码

- **Drools**：开源规则引擎，基于 Rete 算法
- **KieSession**：规则执行会话，提供 insert/fireAllRules 等核心 API
- **Rete 网络**：高效的模式匹配网络，将规则编译为网络节点
- **Working Memory**：存储事实对象的工作内存
- **Agenda**：规则激活队列，管理满足条件的规则

#### 解释器模式体现

| 解释器模式角色 | Drools 对应实现 |
|--------------|----------------|
| AbstractExpression | Rule 接口、ReteNode 抽象节点 |
| TerminalExpression | AlphaNode（字段约束判断） |
| NonTerminalExpression | BetaNode（多对象关联判断） |
| Context | Working Memory（存储 Fact 对象） |
| Client | KieSession（调用 insert/fireAllRules） |

#### 运行效果

```
执行规则: VIP订单满1000打8折
执行规则: 订单未满1000不打折
规则触发数量: 2
order1 折扣: 0.8
order1 说明: 命中规则: VIP订单满1000打8折
order2 折扣: 1.0
order2 说明: 命中规则: 订单未满1000不打折
```

#### 学习收获

1. **解释器模式的实际应用**：规则引擎是解释器模式的复杂应用场景
2. **Rete 算法的优势**：空间换时间、增量匹配、共享节点
3. **业务与技术的解耦**：业务人员可通过 DRL 配置业务逻辑，无需修改 Java 代码
4. **性能优化思路**：编译时优化、运行时优化、内存优化
5. **设计模式组合应用**：解释器模式 + 组合模式 + 观察者模式
