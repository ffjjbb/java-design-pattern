
# 创建型模式


### 单例模式

#### package

`src/main/java/com/fanjiabao/design/pattern/creator/singleton`

#### 概要

通过多种实现方式演示单例模式的实际应用。包含饿汉式（静态变量、静态代码块、枚举）和懒汉式（双重检查锁、静态内部类）五种实现方式，以及反射和序列化破坏单例的防护措施。

#### 核心思想

单例模式：指一个类只有一个实例，且该类能自行创建这个实例的一种模式。

核心要素：
- **私有构造方法**：防止外部通过 new 创建实例
- **私有静态实例变量**：保存唯一的实例
- **公共静态获取方法**：提供全局访问点

#### 本案例讲解

**饿汉式（类加载时创建实例）**：

1. **静态变量方式**：在成员位置声明静态变量并创建对象，类加载时创建
2. **静态代码块方式**：在静态代码块中创建对象实例
3. **枚举方式**：JVM 保证实例唯一，线程安全，天然防止反射和反序列化破坏

**懒汉式（首次使用时创建实例）**：

1. **双重检查锁**：使用 synchronized 和 volatile 保证线程安全
2. **静态内部类**：利用类加载机制保证线程安全，延迟加载

**单例破坏与防护**：

- **反射破坏**：通过私有构造器标识位判断防止重复创建
- **序列化破坏**：实现 readResolve 方法返回单例实例

#### 使用的框架/源码

- **Java volatile**：保证可见性和有序性，防止指令重排序
- **Java synchronized**：保证线程安全
- **Java 枚举**：天然防止反射和反序列化破坏
- **Serializable**：序列化接口，需要实现 readResolve 方法
- **ObjectInputStream**：反序列化时调用 readResolve 方法
- **Constructor.setAccessible()**：反射取消访问检查

#### 单例模式体现

| 实现方式 | 线程安全 | 延迟加载 | 防反射 | 防序列化 | 推荐度 |
|---------|---------|---------|-------|---------|-------|
| 静态变量 | ✓ | ✗ | ✗ | ✗ | 中 |
| 静态代码块 | ✓ | ✗ | ✗ | ✗ | 中 |
| 枚举 | ✓ | ✗ | ✓ | ✓ | 高 |
| 双重检查锁 | ✓ | ✓ | ✓ | ✗ | 中 |
| 静态内部类 | ✓ | ✓ | ✗ | ✓ | 高 |

#### 运行效果

**反射破坏单例**：
```
com.fanjiabao.design.pattern.creator.singleton.lazy.one.LazyPlanOne@...
com.fanjiabao.design.pattern.creator.singleton.lazy.one.LazyPlanOne@...
false
```

**序列化破坏单例**：
```
com.fanjiabao.design.pattern.creator.singleton.lazy.two.LazyPlanTwo@...
com.fanjiabao.design.pattern.creator.singleton.lazy.two.LazyPlanTwo@...
true
```

#### 学习收获

1. **单例模式的核心价值**：保证一个类只有一个实例，提供全局访问点
2. **枚举单例的优势**：JVM 保证实例唯一，线程安全，天然防止反射和反序列化破坏
3. **双重检查锁**：使用 volatile 防止指令重排序，synchronized 保证线程安全
4. **静态内部类**：利用类加载机制保证线程安全，延迟加载，无性能影响
5. **实际应用场景**：Spring Bean 单例作用域、数据库连接池、日志对象、配置对象


------------------------------------------------------------------------------------------------------------------------


### 工厂模式

#### package

`src/main/java/com/fanjiabao/design/pattern/creator/factory`

#### 概要

通过咖啡店点单和 Spring BeanFactory 演示工厂模式的实际应用。包含简单工厂、工厂方法、抽象工厂三种实现方式，以及框架源码分析（Spring BeanFactory 通过 getBean 方法创建 Bean 对象）。

#### 核心思想

工厂模式：定义一个用于创建对象的接口，让子类决定实例化哪个产品类对象。工厂方法使一个产品类的实例化延迟到其工厂的子类。

核心要素：
- **抽象工厂（Abstract Factory）**：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法来创建产品
- **具体工厂（ConcreteFactory）**：主要是实现抽象工厂中的抽象方法，完成具体产品的创建
- **抽象产品（Product）**：定义了产品的规范，描述了产品的主要特性和功能
- **具体产品（ConcreteProduct）**：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间一一对应

#### 本案例讲解

**Demo（咖啡店点单）**：

通过咖啡店点单展示工厂模式的基本用法：

1. **简单工厂**：一个工厂类根据参数创建不同产品，违背开闭原则
2. **工厂方法**：每个产品对应一个工厂类，符合开闭原则
3. **抽象工厂**：创建一组相关或相互依赖的对象，生产多个等级的产品

**框架源码（Spring BeanFactory）**：

通过 Spring BeanFactory 展示工厂模式在框架中的应用：

1. **抽象工厂（BeanFactory）**：定义 getBean 方法
2. **具体工厂（DefaultListableBeanFactory）**：实现 Bean 的创建和管理
3. **抽象产品（Object）**：Bean 对象
4. **具体产品**：用户定义的 Bean 类
5. **创建过程**：根据 BeanDefinition 实例化、依赖注入、初始化

#### 使用的框架/源码

- **Spring BeanFactory**：Bean 工厂接口，定义 getBean 方法
- **ApplicationContext**：应用上下文，继承 BeanFactory
- **DefaultListableBeanFactory**：默认 Bean 工厂实现
- **BeanDefinition**：Bean 定义，描述 Bean 的配置信息
- **createBean()**：创建 Bean 实例方法
- **getBean()**：获取 Bean 实例方法

#### 工厂模式体现

| 工厂模式角色 | 简单工厂 | 工厂方法 | Spring BeanFactory |
|------------|---------|---------|-------------------|
| 抽象工厂 | SimpleFactory | CoffeeMethodFactory | BeanFactory |
| 具体工厂 | SimpleFactory | LatteCoffeeFactory | DefaultListableBeanFactory |
| 抽象产品 | Coffee | Coffee | Object |
| 具体产品 | LatteCoffee | LatteCoffee | HelloService |
| 创建方法 | createCoffee() | createCoffee() | getBean() |

#### 运行效果

**简单工厂**：
```
拿铁咖啡
```

**工厂方法**：
```
CoffeeName: 拿铁咖啡
```

**抽象工厂**：
```
美式咖啡
抹茶慕斯
```

**Spring BeanFactory**：
```
工厂模式
```

#### 学习收获

1. **工厂模式的核心价值**：将对象的创建和使用分离，降低耦合度
2. **Spring BeanFactory 的应用场景**：通过 getBean 方法创建和管理 Bean 对象，实现 IoC 容器
3. **简单工厂 vs 工厂方法**：简单工厂违背开闭原则，工厂方法符合开闭原则但增加复杂度
4. **抽象工厂**：创建一组相关对象，保证客户端始终使用同一产品族的对象
5. **实际应用场景**：Spring BeanFactory、MyBatis SqlSessionFactory、日志框架 LoggerFactory


------------------------------------------------------------------------------------------------------------------------


### 原型模式

#### package

`src/main/java/com/fanjiabao/design/pattern/creator/prototype`

#### 概要

通过奖状复制演示原型模式的实际应用。包含浅克隆（实现 Cloneable 接口）和深克隆（使用对象流序列化）两种方式，展示原型模式在对象复制中的应用。

#### 核心思想

原型模式：用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型对象相同的新对象。

核心要素：
- **抽象原型类**：规定了具体原型对象必须实现的 clone() 方法，在 Java 中就是 Cloneable 接口
- **具体原型类**：实现抽象原型类的 clone() 方法，它是可被复制的对象
- **访问类**：使用具体原型类中的 clone() 方法来复制新的对象

#### 本案例讲解

**Demo（奖状复制）**：

通过奖状复制展示原型模式的基本用法：

1. **抽象原型类（Cloneable）**：Java 提供的标记接口
2. **具体原型类（ShallowPrototypeCitation）**：实现 Cloneable 接口，重写 clone 方法
3. **浅克隆**：创建新对象，非基本类型属性仍指向原有对象地址
4. **深克隆**：使用对象流序列化，引用类型也会被克隆

**浅克隆 vs 深克隆**：

- **浅克隆**：Object.clone() 方法，引用类型共享内存地址
- **深克隆**：对象流序列化/反序列化，引用类型独立内存地址

#### 使用的框架/源码

- **Java Cloneable**：标记接口，表示对象可克隆
- **Object.clone()**：浅克隆方法，创建对象副本
- **ObjectOutputStream**：对象输出流，用于序列化
- **ObjectInputStream**：对象输入流，用于反序列化
- **Serializable**：序列化标记接口，深克隆需要实现

#### 原型模式体现

| 原型模式角色 | 浅克隆 | 深克隆 |
|------------|-------|-------|
| 抽象原型类 | Cloneable | Serializable |
| 具体原型类 | ShallowPrototypeCitation | DeepPrototypeCitation |
| 克隆方法 | super.clone() | 序列化/反序列化 |
| 引用类型 | 共享内存地址 | 独立内存地址 |
| 实现方式 | 实现 Cloneable | 实现 Serializable |

#### 运行效果

**浅克隆**：
```
false
true
克莱恩 同学: 今年业绩优秀, 赏！
克莱恩 同学: 今年业绩优秀, 赏！
```

**深克隆**：
```
false
false
樊一 同学: 今年业绩优秀, 赏！
樊二 同学: 今年业绩优秀, 赏！
```

#### 学习收获

1. **原型模式的核心价值**：通过复制已有对象来创建新对象，避免重新构造的复杂过程
2. **浅克隆 vs 深克隆**：浅克隆引用类型共享地址，深克隆引用类型独立地址
3. **Cloneable 接口**：Java 提供的标记接口，表示对象可克隆，需要重写 clone 方法
4. **深克隆实现**：使用对象流序列化/反序列化，需要实现 Serializable 接口
5. **实际应用场景**：对象创建成本高、需要保护性拷贝、Spring Bean 原型作用域


------------------------------------------------------------------------------------------------------------------------


### 建造者模式

#### package

`src/main/java/com/fanjiabao/design/pattern/creator/builder`

#### 概要

通过自行车组装和 StringBuilder 演示建造者模式的实际应用。包含Demo（指挥者指导建造者组装自行车）和框架源码分析（StringBuilder 通过 append 方法逐步构建 String 对象）。

#### 核心思想

建造者模式：将一个复杂对象的构建与表示分离，使得同样的构建过程可以创建不同的表示。分离了部件的构造（由 Builder 来负责）和装配（由 Director 负责），从而可以构造出复杂的对象。

核心要素：
- **抽象建造者类（Builder）**：这个接口规定要实现复杂对象的那些部分的创建，并不涉及具体的部件对象的创建
- **具体建造者类（ConcreteBuilder）**：实现 Builder 接口，完成复杂产品的各个部件的具体创建方法，在构造过程完成后，提供产品的实例
- **产品类（Product）**：要创建的复杂对象
- **指挥者类（Director）**：调用具体建造者来创建复杂对象的各个部分，在指导者中不涉及具体产品的信息，只负责保证对象各部分完整创建或按某种顺序创建

#### 本案例讲解

**Demo（自行车组装）**：

通过指挥者指导建造者组装自行车展示建造者模式的基本用法：

1. **抽象建造者（Builder）**：定义 buildFrame、buildSeat、createBike 方法
2. **具体建造者（MobileBuilder、OfoBuilder）**：实现不同品牌的自行车构建
3. **产品类（Bike）**：自行车，包含 frame、seat 属性
4. **指挥者（Director）**：调用建造者的方法，控制构建顺序

**框架源码（StringBuilder）**：

通过 StringBuilder 展示建造者模式在 JDK 中的应用：

1. **建造者（StringBuilder）**：提供 append 方法逐步构建字符串
2. **产品（String）**：最终的不可变字符串对象
3. **构建过程**：通过多次 append 调用逐步构建
4. **获取产品**：通过 toString 方法返回最终的 String 对象

#### 使用的框架/源码

- **Java StringBuilder**：字符串构建器，使用建造者模式
- **Java StringBuffer**：线程安全的字符串构建器
- **append()**：添加字符串内容方法
- **toString()**：构建最终 String 对象方法
- **ensureCapacityInternal()**：确保内部数组容量足够
- **System.arraycopy()**：拷贝字符到内部数组

#### 建造者模式体现

| 建造者模式角色 | Demo | StringBuilder 应用 |
|--------------|---------|-------------------|
| Builder | Builder | StringBuilder |
| ConcreteBuilder | MobileBuilder、OfoBuilder | StringBuilder |
| Product | Bike | String |
| Director | Director | 客户端代码 |
| 构建方法 | buildFrame()、buildSeat() | append() |
| 获取产品 | createBike() | toString() |

#### 运行效果

**Demo**：
```
碳纤维车架
真皮车座
```

**StringBuilder**：
```
Hello World
```

#### 学习收获

1. **建造者模式的核心价值**：将复杂对象的构建与表示分离，使得同样的构建过程可以创建不同的表示
2. **StringBuilder 的应用场景**：通过 append 方法逐步构建字符串，避免创建大量临时 String 对象
3. **分步构建**：将复杂对象的创建步骤分解在不同的方法中，使得创建过程更加清晰
4. **封装性**：客户端不必知道产品内部组成的细节，将产品本身与产品的创建过程解耦
5. **实际应用场景**：StringBuilder、StringBuffer、SQL 构建器、HTTP 请求构建器、AlertDialog.Builder



------------------------------------------------------------------------------------------------------------------------

# 结构型模式


### 代理模式

#### package

`src/main/java/com/fanjiabao/design/pattern/structural/proxy`

#### 概要

通过火车票代售点和 Dubbo RPC 调用演示代理模式的实际应用。包含Demo（静态代理、JDK 动态代理、CGLIB 动态代理）和框架源码分析（Dubbo 通过动态代理实现透明 RPC 调用）。

#### 核心思想

代理模式：由于某些原因需要给某对象提供一个代理以控制对该对象的访问。这时，访问对象不适合或者不能直接引用目标对象，代理对象作为访问对象和目标对象之间的中介。

核心要素：
- **抽象主题（Subject）类**：通过接口或抽象类声明真实主题和代理对象实现的业务方法
- **真实主题（RealSubject）类**：实现了抽象主题中的具体业务，是代理对象所代表的真实对象，是最终要引用的对象
- **代理（Proxy）类**：提供了与真实主题相同的接口，其内部含有对真实主题的引用，它可以控制、扩展或修饰真实主题的功能

#### 本案例讲解

**Demo（火车票代售点）**：

通过火车票代售点展示代理模式的基本用法：

1. **抽象主题（SellTickets）**：卖票接口，定义 sell 方法
2. **真实主题（TrainStation）**：火车站，实现 sell 方法
3. **静态代理（ProxyPoint）**：代售点，持有 TrainStation 引用
4. **JDK 动态代理（JDKProxyFactory）**：使用 Proxy.newProxyInstance 创建代理对象
5. **CGLIB 动态代理（CGLIBProxyFactory）**：使用 Enhancer 创建代理对象

**框架源码（Dubbo RPC）**：

通过 Dubbo 服务调用展示代理模式在框架中的应用：

1. **@DubboReference**：注入服务代理对象
2. **代理对象**：拦截方法调用，封装 RPC 调用
3. **InvokerInvocationHandler**：调用处理器，封装 RpcInvocation
4. **DubboInvoker**：执行远程调用，负载均衡、网络传输
5. **透明 RPC**：客户端无需感知远程调用，像调用本地方法一样调用远程服务

#### 使用的框架/源码

- **Dubbo**：RPC 框架，使用动态代理实现透明远程调用
- **JDK Proxy**：JDK 动态代理，基于接口
- **CGLIB Enhancer**：CGLIB 动态代理，基于继承
- **InvocationHandler**：调用处理器，拦截方法调用
- **MethodInterceptor**：CGLIB 方法拦截器
- **InvokerInvocationHandler**：Dubbo 调用处理器
- **RpcInvocation**：RPC 调用封装

#### 代理模式体现

| 代理模式角色 | Demo | Dubbo 应用 |
|------------|---------|-----------|
| Subject | SellTickets | GreetingService |
| RealSubject | TrainStation | GreetingServiceImpl |
| Proxy | ProxyPoint、$Proxy0 | Dubbo 代理对象 |
| 代理方法 | sell() | say() |
| 增强逻辑 | 收取服务费用 | RPC 调用、负载均衡 |

#### 运行效果

**Demo**：
```
代理类: class jdk.proxy2.$Proxy0
类加载器: jdk.proxy2.$Proxy0@...
------------------------------------
代理点收取一些服务费用(JDK动态代理方式)
火车站卖票
代理点上报交易明细
```

**Dubbo RPC**：
```
代理对象: class org.apache.dubbo.common.bytecode.Proxy0
定时调用结果: 它说 Hello World
```

#### 学习收获

1. **代理模式的核心价值**：控制对对象的访问，可以在不修改目标对象的情况下扩展功能
2. **Dubbo 的应用场景**：通过动态代理实现透明 RPC 调用，客户端像调用本地方法一样调用远程服务
3. **静态代理 vs 动态代理**：静态代理需要手动创建代理类，动态代理在运行时自动生成代理类
4. **JDK vs CGLIB**：JDK 动态代理基于接口，CGLIB 动态代理基于继承，可以代理没有接口的类
5. **实际应用场景**：Spring AOP、Dubbo RPC、MyBatis Mapper、日志框架、权限控制


------------------------------------------------------------------------------------------------------------------------


### 适配器模式

#### package

`src/main/java/com/fanjiabao/design/pattern/structural/adapter`

#### 概要

通过 SD 卡适配 TF 卡和 Java IO 流演示适配器模式的实际应用。包含Demo（SD 卡适配器读取 TF 卡）和框架源码分析（InputStreamReader 将字节流适配为字符流）。

#### 核心思想

适配器模式：将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。

核心要素：
- **目标（Target）接口**：当前系统业务所期待的接口，它可以是抽象类或接口
- **适配者（Adaptee）类**：它是被访问和适配的现存组件库中的组件接口
- **适配器（Adapter）类**：它是一个转换器，通过继承或实现适配者对象，把适配者接口转换成目标接口，让客户按目标接口的方法访问适配者

#### 本案例讲解

**Demo（SD 卡适配 TF 卡）**：

通过 SD 卡适配器读取 TF 卡展示适配器模式的基本用法：

1. **目标接口（SDCard）**：SD 卡接口，定义 readSD、writeSD 方法
2. **适配者（TFCard）**：TF 卡接口，定义 readTF、writeTF 方法
3. **适配器（SDObjectAdapterTF）**：实现 SDCard 接口，持有 TFCard 引用
4. **对象适配器**：通过组合关系，将 TF 卡适配为 SD 卡

**框架源码（InputStreamReader）**：

通过 InputStreamReader 展示适配器模式在 JDK 中的应用：

1. **目标接口（Reader）**：字符读取接口，定义 read 方法
2. **适配者（InputStream）**：字节读取接口，只能读取字节
3. **适配器（InputStreamReader）**：实现 Reader 接口，持有 InputStream 引用
4. **字节转字符**：通过 StreamDecoder 将字节流解码为字符流

#### 使用的框架/源码

- **Java IO**：输入输出流，使用适配器模式转换流类型
- **Reader**：字符读取流抽象类
- **InputStream**：字节读取流抽象类
- **InputStreamReader**：字节流转字符流的适配器
- **OutputStreamWriter**：字符流转字节流的适配器
- **StreamDecoder**：流解码器，将字节解码为字符
- **CharsetDecoder**：字符集解码器

#### 适配器模式体现

| 适配器模式角色 | Demo | Java IO 应用 |
|--------------|---------|-------------|
| Target | SDCard | Reader |
| Adaptee | TFCard | InputStream |
| Adapter | SDObjectAdapterTF | InputStreamReader |
| 适配方法 | readSD() → readTF() | read() → StreamDecoder.read() |
| 组合关系 | tfCard 字段 | in 字段 |

#### 运行效果

**Demo**：
```
SDCard read msg : hello word SDCard
------------
adapter read tf card 
TFCard read msg : hello word TFCard
```

**InputStreamReader**：
```
========== 直接使用 InputStream（字节流） ==========
35 61 61 ...

========== 使用 InputStreamReader（适配器） ==========
内容: # aa
```

#### 学习收获

1. **适配器模式的核心价值**：将不兼容的接口转换为兼容的接口，让原本不能一起工作的类可以一起工作
2. **Java IO 的应用场景**：通过 InputStreamReader 将字节流适配为字符流，实现接口转换
3. **对象适配器 vs 类适配器**：对象适配器使用组合关系，更灵活；类适配器使用继承关系，不够灵活
4. **接口转换**：适配器模式的核心是接口转换，而不是功能实现
5. **实际应用场景**：Java IO 流、Spring MVC HandlerAdapter、Arrays.asList()、日志框架适配


------------------------------------------------------------------------------------------------------------------------


### 装饰者模式

#### package

`src/main/java/com/fanjiabao/design/pattern/structural/decorator`

#### 概要

通过快餐配料和 Java IO 流演示装饰者模式的实际应用。包含Demo（炒饭加蛋、加培根）和框架源码分析（BufferedWriter 装饰 FileWriter，增加缓冲功能）。

#### 核心思想

装饰者模式：在不改变现有对象结构的情况下，动态地给该对象增加一些职责（即增加其额外功能）的模式。

核心要素：
- **抽象构件（Component）角色**：定义一个抽象接口以规范准备接收附加责任的对象
- **具体构件（ConcreteComponent）角色**：实现抽象构件，通过装饰角色为其添加一些职责
- **抽象装饰（Decorator）角色**：继承或实现抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能
- **具体装饰（ConcreteDecorator）角色**：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任

#### 本案例讲解

**Demo（快餐配料）**：

通过炒饭加蛋、加培根展示装饰者模式的基本用法：

1. **抽象构件（FastFood）**：快餐抽象类，定义 cost 方法
2. **具体构件（FriedRice、FriedNoodles）**：炒饭、炒面，实现 cost 方法
3. **抽象装饰（Garnish）**：配料抽象类，持有 FastFood 引用
4. **具体装饰（Egg、Bacon）**：鸡蛋、培根，动态添加配料和价格

**框架源码（Java IO 流）**：

通过 BufferedWriter 装饰 FileWriter 展示装饰者模式在 JDK 中的应用：

1. **抽象构件（Writer）**：字符输出流抽象类
2. **具体构件（FileWriter）**：文件字符输出流，直接写入文件
3. **抽象装饰（BufferedWriter）**：缓冲字符输出流，持有 Writer 引用
4. **具体装饰（BufferedWriter）**：增加缓冲区，提高写入性能

#### 使用的框架/源码

- **Java IO**：输入输出流，大量使用装饰者模式
- **Writer**：字符输出流抽象类
- **FileWriter**：文件字符输出流
- **BufferedWriter**：缓冲字符输出流，增加缓冲功能
- **InputStream**：字节输入流抽象类
- **BufferedInputStream**：缓冲字节输入流
- **DataInputStream**：数据输入流，增加读取基本类型功能

#### 装饰者模式体现

| 装饰者模式角色 | Demo | Java IO 应用 |
|--------------|-------|-------------|
| Component | FastFood | Writer |
| ConcreteComponent | FriedRice、FriedNoodles | FileWriter |
| Decorator | Garnish | BufferedWriter |
| ConcreteDecorator | Egg、Bacon | BufferedWriter |
| 组合关系 | fastFood 字段 | out 字段 |

#### 运行效果

**Demo**：
```
炒饭  10.0元
炒饭,加一个鸡蛋  12.0元
炒饭,加一个鸡蛋,加一个鸡蛋  14.0元
炒饭,加一个鸡蛋,加一个鸡蛋,加一片培根  17.0元
```

**Java IO**：
```
文件内容: hello Buffered
```

#### 学习收获

1. **装饰者模式的核心价值**：动态地给对象添加职责，比继承更灵活，遵循开闭原则
2. **Java IO 的应用场景**：通过装饰者模式组合各种流，实现缓冲、数据转换等功能
3. **动态扩展**：可以在运行时动态添加或撤销功能，而不需要创建大量子类
4. **组合优于继承**：使用组合关系代替继承，避免类爆炸，提高扩展性
5. **实际应用场景**：Java IO 流、Servlet Filter、Spring BeanWrapper、日志框架包装器


------------------------------------------------------------------------------------------------------------------------


### 桥接模式

#### package

`src/main/java/com/fanjiabao/design/pattern/structural/bridge`

#### 概要

通过视频播放器和 JDBC 演示桥接模式的实际应用。包含 Demo（不同操作系统播放不同格式视频）和框架源码分析（JDBC DriverManager 统一接口，各数据库驱动实现 Driver 接口）。

#### 核心思想

桥接模式：将抽象部分与实现部分分离，使用组合关系代替继承关系，解耦两个变化维度，降低了抽象和实现这两个可变维度的耦合度，使它们可以独立变化。从而避免类爆炸问题，适用于存在多个维度变化的系统设计。

核心要素：
- **抽象化（Abstraction）角色**：定义抽象类，并包含一个对实现化对象的引用
- **扩展抽象化（RefinedAbstraction）角色**：是抽象化角色的子类，实现父类中的业务方法，并通过组合关系调用实现化角色中的业务方法
- **实现化（Implementor）角色**：定义实现化角色的接口，供扩展抽象化角色调用
- **具体实现化（ConcreteImplementor）角色**：给出实现化角色接口的具体实现

#### 本案例讲解

**Demo（视频播放器）**：

通过不同操作系统播放不同格式视频展示桥接模式的基本用法：

1. **抽象化角色（OperatingSystem）**：操作系统抽象类，持有 VideoFile 引用
2. **扩展抽象化角色（Windows、Mac）**：具体操作系统，调用 videoFile.decode()
3. **实现化角色（VideoFile）**：视频文件接口，定义 decode 方法
4. **具体实现化角色（AviFile、RmvbFile）**：不同格式视频文件实现

**框架源码（JDBC）**：

通过 JDBC DriverManager 展示桥接模式在框架中的应用：

1. **抽象化角色（DriverManager）**：提供统一接口获取数据库连接
2. **扩展抽象化角色（Connection、Statement、ResultSet）**：数据库操作接口
3. **实现化角色（Driver）**：数据库驱动接口，定义 connect 方法
4. **具体实现化角色（MySQLDriver、OracleDriver）**：各数据库驱动实现

#### 使用的框架/源码

- **JDBC**：Java 数据库连接，使用桥接模式解耦 API 和驱动
- **DriverManager**：驱动管理器，提供统一接口
- **Driver**：驱动接口，各数据库厂商实现
- **Connection**：连接接口，不依赖具体实现
- **Statement**：语句接口，执行 SQL
- **ResultSet**：结果集接口，遍历查询结果

#### 桥接模式体现

| 桥接模式角色 | Demo | JDBC 应用 |
|------------|---------|----------|
| Abstraction | OperatingSystem | DriverManager |
| RefinedAbstraction | Windows、Mac | Connection、Statement |
| Implementor | VideoFile | Driver |
| ConcreteImplementor | AviFile、RmvbFile | MySQLDriver、OracleDriver |
| 组合关系 | videoFile 字段 | Driver 注册 |

#### 运行效果

** Demo**：
```
Mac系统播放：战狼3.avi
------------------------
9
```

**JDBC**：
```
连接对象类型: class com.mysql.cj.jdbc.ConnectionImpl
localhost -> root
```

#### 学习收获

1. **桥接模式的核心价值**：将抽象与实现分离，使两个维度可以独立变化，避免类爆炸
2. **JDBC 的应用场景**：通过 DriverManager 统一接口，各数据库驱动独立实现，客户端无需修改代码
3. **组合优于继承**：使用组合关系代替继承关系，降低耦合度，提高扩展性
4. **两个变化维度**：操作系统和视频格式、JDBC API 和数据库驱动，都可以独立扩展
5. **实际应用场景**：JDBC、Java AWT（组件和平台实现）、日志框架（API 和实现）、消息队列（API 和中间件）


------------------------------------------------------------------------------------------------------------------------


### 外观模式

#### package

`src/main/java/com/fanjiabao/design/pattern/structural/facade`

#### 概要

通过智能家电控制和 Tomcat RequestFacade 演示外观模式的实际应用。包含 Demo（智能音箱统一控制灯、电视、空调）和框架源码分析（Tomcat RequestFacade 封装 Request 对象，提供安全接口）。

#### 核心思想

外观模式：又名门面模式，是一种通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易被访问的模式。该模式对外有一个统一接口，外部应用程序不用关心内部子系统的具体的细节，这样会大大降低应用程序的复杂度，提高了程序的可维护性。

核心要素：
- **外观（Facade）角色**：为多个子系统对外提供一个共同的接口
- **子系统（SubSystem）角色**：实现系统的部分功能，客户可以通过外观角色访问它

#### 本案例讲解

**Demo（智能家电控制）**：

通过智能音箱统一控制家电展示外观模式的基本用法：

1. **外观角色（SmartAppliancesFacade）**：智能音箱，提供 say 方法统一控制
2. **子系统角色（Light、TV、AirCondition）**：灯、电视、空调，各自实现 on/off 方法
3. **统一接口**：say("打开") 或 say("关闭")，外观角色协调所有子系统
4. **简化调用**：客户端只需调用外观角色，无需分别调用各子系统

**框架源码（Tomcat RequestFacade）**：

通过 Tomcat RequestFacade 展示外观模式在框架中的应用：

1. **RequestFacade**：外观类，实现 HttpServletRequest 接口
2. **Request**：内部真实对象，包含复杂的容器逻辑
3. **委托调用**：RequestFacade 的方法委托给内部 Request 执行
4. **安全封装**：隐藏内部 Request 对象，防止开发者直接操作容器内部对象

#### 使用的框架/源码

- **Tomcat**：Web 容器，使用外观模式封装 Request/Response
- **RequestFacade**：Request 外观类，实现 HttpServletRequest 接口
- **ResponseFacade**：Response 外观类，实现 HttpServletResponse 接口
- **Request**：内部 Request 对象，包含容器逻辑
- **HttpServletRequest**：Servlet 规范接口，定义标准方法
- **反射访问**：通过反射可以查看 RequestFacade 内部的真实 Request 对象

#### 外观模式体现

| 外观模式角色 | Demo | Tomcat 应用 |
|------------|---------|------------|
| Facade | SmartAppliancesFacade | RequestFacade |
| SubSystem | Light、TV、AirCondition | Request、Response |
| 统一接口 | say() | HttpServletRequest |
| 委托调用 | light.on()、tv.on() | request.getParameter() |
| 客户端 | Facade.main() | Servlet |

#### 运行效果

**Demo**：
```
睡觉了
关闭电灯
关闭电视
关闭空调
```

**Tomcat RequestFacade**：
```
===== 响应输出 =====
request实际类型: org.apache.catalina.connector.RequestFacade<br/>
是否是Facade对象: true<br/>
内部真实对象: org.apache.catalina.connector.Request<br/>
参数 name: fanjiabao<br/>
<br/>结论: 调用的是Facade, 但实际执行的是内部Request逻辑
```

#### 学习收获

1. **外观模式的核心价值**：为复杂子系统提供统一接口，降低客户端与子系统之间的耦合度
2. **Tomcat 的应用场景**：通过 RequestFacade 封装 Request，隐藏容器内部实现，保证安全性和稳定性
3. **迪米特法则**：外观模式是迪米特法则的典型应用，减少客户端与子系统之间的直接依赖
4. **安全封装**：不仅简化调用，更重要的是隐藏内部实现，防止误操作
5. **实际应用场景**：JDBC 接口、Logging 框架、Spring JdbcTemplate、Service 层封装 DAO


------------------------------------------------------------------------------------------------------------------------


### 组合模式

#### package

`src/main/java/com/fanjiabao/design/pattern/structural/composite`

#### 概要

通过菜单树结构和 Spring 父子容器演示组合模式的实际应用。包含Demo（系统管理菜单树：菜单管理、权限管理、角色管理）和框架源码分析（Spring ApplicationContext 父子容器递归查找 Bean）。

#### 核心思想

组合模式：又名部分整体模式，是用于把一组相似的对象当作一个单一的对象。组合模式依据树形结构来组合对象，用来表示部分以及整体层次。这种类型的设计模式属于结构型模式，它创建了对象组的树形结构。

核心要素：
- **抽象根节点（Component）**：定义系统各层次对象的共有方法和属性，可以预先定义一些默认行为和属性
- **树枝节点（Composite）**：定义树枝节点的行为，存储子节点，组合树枝节点和叶子节点形成一个树形结构
- **叶子节点（Leaf）**：叶子节点对象，其下再无分支，是系统层次遍历的最小单位

#### 本案例讲解

**Demo（菜单树结构）**：

通过系统管理菜单树展示组合模式的基本用法：

1. **抽象根节点（MenuComponent）**：定义 add、remove、getChild、print 方法
2. **树枝节点（Menu）**：维护 List<MenuComponent> 存储子节点，实现递归打印
3. **叶子节点（MenuItem）**：菜单项，无子节点，直接打印
4. **树形结构**：系统管理 → 菜单管理、权限管理、角色管理

**框架源码（Spring 父子容器）**：

通过 Spring ApplicationContext 父子容器展示组合模式在框架中的应用：

1. **AbstractApplicationContext**：抽象容器，维护 parent 引用
2. **setParent()**：设置父容器，形成树形结构
3. **getBean()**：递归查找 Bean，先在当前容器查找，找不到则向父容器查找
4. **父子容器**：子容器可以访问父容器的 Bean，父容器不能访问子容器的 Bean

#### 使用的框架/源码

- **Spring Framework**：IoC 容器，使用组合模式实现父子容器
- **AbstractApplicationContext**：抽象应用上下文，维护 parent 引用
- **DefaultListableBeanFactory**：默认 Bean 工厂，实现 Bean 查找
- **setParent()**：设置父容器方法
- **getBean()**：获取 Bean 方法，递归查找
- **parent**：父容器引用，形成树形结构

#### 组合模式体现

| 组合模式角色 | Demo | Spring 应用 |
|------------|---------|------------|
| Component | MenuComponent | AbstractApplicationContext |
| Composite | Menu | AnnotationConfigApplicationContext |
| Leaf | MenuItem | 无子容器的 ApplicationContext |
| add() | add() | setParent() |
| getChild() | getChild() | getParent() |
| print() | print() | getBean() |

#### 运行效果

**Demo**：
```
系统管理
--菜单管理
----页面访问
----展开菜单
----编辑菜单
----删除菜单
----新增菜单
--权限管理
----页面访问
----提交保存
--角色管理
----页面访问
----新增角色
----修改角色
```

**Spring 父子容器**：
```
组合模式正是应树形结构而生, 所以组合模式的使用场景就是出现树形结构的地方。
```

#### 学习收获

1. **组合模式的核心价值**：将对象组合成树形结构，统一处理单个对象和组合对象
2. **Spring 的应用场景**：通过父子容器实现 Bean 的分层管理，子容器可以访问父容器的 Bean
3. **递归结构**：树枝节点递归调用子节点的操作，实现树形结构的统一处理
4. **透明性**：客户端可以一致地使用组合结构和单个对象，不必关心处理的是单个对象还是组合结构
5. **实际应用场景**：文件系统目录树、XML/HTML DOM 树、组织架构树、菜单树


------------------------------------------------------------------------------------------------------------------------


### 享元模式

#### package

`src/main/java/com/fanjiabao/design/pattern/structural/flyweight`

#### 概要

通过五子棋棋子和 Integer 缓存演示享元模式的实际应用。包含Demo（I、L、O 三种形状的方块共享）和框架源码分析（Integer.valueOf() 使用 IntegerCache 缓存 -128 到 127 的 Integer 对象）。

#### 核心思想

享元模式：运用共享技术来有效地支持大量细粒度对象的复用。它通过共享已经存在的对象来大幅度减少需要创建的对象数量，避免大量相似对象的开销，从而提高系统资源的利用率。

核心要素：
- **抽象享元角色（Flyweight）**：通常是一个接口或抽象类，在抽象享元类中声明了具体享元类公共的方法
- **具体享元（ConcreteFlyweight）角色**：它实现了抽象享元类，称为享元对象；在具体享元类中为内部状态提供了存储空间
- **非享元（UnsharableFlyweight）角色**：并不是所有的抽象享元类的子类都需要被共享
- **享元工厂（FlyweightFactory）角色**：负责创建和管理享元角色。当客户对象请求一个享元对象时，享元工厂检査系统中是否存在符合要求的享元对象

#### 本案例讲解

**Demo（五子棋棋子）**：

通过 I、L、O 三种形状的方块展示享元模式的基本用法：

1. **抽象享元（AbstractBox）**：定义 getShape 抽象方法和 display 方法
2. **具体享元（IBox、LBox、OBox）**：实现不同形状的方块
3. **享元工厂（BoxFactory）**：维护 HashMap 存储享元对象，提供 getShape 方法
4. **内部状态**：形状（I、L、O）
5. **外部状态**：颜色（灰色、红色）

**框架源码（Integer 缓存）**：

通过 Integer.valueOf() 展示享元模式在 JDK 中的应用：

1. **IntegerCache**：内部静态类，缓存 -128 到 127 的 Integer 对象
2. **valueOf()**：如果值在缓存范围内，返回缓存对象；否则创建新对象
3. **CDS（Class Data Sharing）**：JVM 类数据共享机制，跨进程共享 Integer 缓存
4. **内存优化**：避免重复创建相同值的 Integer 对象

#### 使用的框架/源码

- **Java Integer**：包装类，使用享元模式缓存常用值
- **IntegerCache**：Integer 内部缓存类，缓存 -128 到 127
- **valueOf()**：静态方法，优先返回缓存对象
- **CDS**：JVM 类数据共享机制，提高启动速度
- **HashMap**：享元工厂使用 HashMap 管理享元对象
- **单例模式**：BoxFactory 使用单例模式确保唯一工厂实例

#### 享元模式体现

| 享元模式角色 | Demo | Integer 应用 |
|------------|---------|-------------|
| Flyweight | AbstractBox | Integer |
| ConcreteFlyweight | IBox、LBox、OBox | Integer 对象 |
| FlyweightFactory | BoxFactory | IntegerCache |
| 内部状态 | 形状（I、L、O） | 整数值 |
| 外部状态 | 颜色 | 无 |
| 享元池 | HashMap | IntegerCache.cache |

#### 运行效果

**Demo**：
```
方块形状: I, 颜色: 灰色
方块形状: O, 颜色: 灰色
方块形状: O, 颜色: 红色
两次获取到的O图形对象是否是同一个对象: true
```

**Integer 缓存**：
```
i1 和 i2对象是否是同一个对象: true
i3 和 i4 对象是否是同一个对象: false
```

#### 学习收获

1. **享元模式的核心价值**：通过共享对象减少内存占用，提高系统性能
2. **Integer 的应用场景**：缓存常用整数值，避免重复创建对象，节省内存
3. **内部状态与外部状态**：内部状态可共享（形状、整数值），外部状态不可共享（颜色）
4. **CDS 优化**：JVM 启动时直接加载缓存，跨进程共享，提高启动速度
5. **实际应用场景**：String 常量池、数据库连接池、线程池、Integer/Long 缓存


------------------------------------------------------------------------------------------------------------------------

# 行为型模式


### 模板方法模式

#### package

`src/main/java/com/fanjiabao/design/pattern/behavioral/template_method`

#### 概要

通过炒菜流程和 Spring 容器初始化演示模板方法模式的实际应用。包含 Demo（炒菜步骤：倒油、热油、放菜、放调料、翻炒）和框架源码分析（AbstractApplicationContext.refresh() 容器初始化流程）。

#### 核心思想

模板方法模式：定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类可以不改变该算法结构的情况下重定义该算法的某些特定步骤。

核心要素：
- **抽象类（AbstractClass）**：负责给出一个算法的轮廓和骨架。它由一个模板方法和若干个基本方法构成
- **模板方法**：定义了算法的骨架，按某种顺序调用其包含的基本方法
- **基本方法**：是实现算法各个步骤的方法，包括抽象方法、具体方法、钩子方法
- **具体子类（ConcreteClass）**：实现抽象类中所定义的抽象方法和钩子方法，它们是一个顶级逻辑的组成步骤

#### 本案例讲解

**Demo（炒菜流程）**：

通过炒菜步骤展示模板方法模式的基本用法：

1. **抽象类（AbstractClass）**：定义 cookProcess 模板方法，固定炒菜流程
2. **具体方法**：倒油、热油、翻炒（所有菜都一样）
3. **抽象方法**：放菜、放调料（不同菜不同实现）
4. **具体子类**：ConcreteClass_BaoCai（包菜）、ConcreteClass_CaiXin（菜心）

**框架源码（Spring 容器初始化）**：

通过 AbstractApplicationContext.refresh() 展示模板方法模式在框架中的应用：

1. **模板方法**：refresh() 定义容器初始化的完整流程
2. **具体方法**：prepareRefresh、obtainFreshBeanFactory、prepareBeanFactory 等
3. **抽象方法**：postProcessBeanFactory、onRefresh（子类扩展点）
4. **钩子方法**：finishRefresh、destroyBeans（生命周期回调）

#### 使用的框架/源码

- **Spring Framework**：IoC 容器，使用模板方法模式定义初始化流程
- **AbstractApplicationContext**：抽象应用上下文，定义 refresh 模板方法
- **refresh()**：模板方法，定义容器初始化的 12 个步骤
- **postProcessBeanFactory()**：抽象方法，子类扩展 BeanDefinition
- **onRefresh()**：抽象方法，子类初始化特殊组件
- **finishRefresh()**：钩子方法，发布刷新完成事件

#### 模板方法模式体现

| 模板方法模式角色 | Demo | Spring 应用 |
|----------------|---------|------------|
| AbstractClass | AbstractClass | AbstractApplicationContext |
| TemplateMethod | cookProcess() | refresh() |
| ConcreteMethod | pourOil()、heatOil() | prepareRefresh()、prepareBeanFactory() |
| AbstractMethod | pourVegetable()、pourSauce() | postProcessBeanFactory()、onRefresh() |
| ConcreteClass | ConcreteClass_BaoCai | AnnotationConfigApplicationContext |

#### 运行效果

**Demo**：
```
倒油
热油
放包菜
放辣椒酱
炒啊炒啊炒到熟啊
```

**Spring 容器初始化**：
```
usageScenarios(): 算法的整体步骤很固定, 但其中个别部分易变时, 这时候可以使用模板方法模式, 将容易变的部分抽象出来, 供子类实现。
```

#### 学习收获

1. **模板方法模式的核心价值**：定义算法骨架，将可变部分延迟到子类实现，实现代码复用
2. **Spring 的应用场景**：通过 refresh() 模板方法定义容器初始化流程，子类可扩展特定步骤
3. **反向控制**：父类调用子类的方法，子类决定父类算法中某个步骤的具体实现
4. **开闭原则**：新增功能只需新增子类，无需修改模板方法
5. **实际应用场景**：Servlet 生命周期、JUnit 测试框架、JdbcTemplate、Hibernate 模板


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
