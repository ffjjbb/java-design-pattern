package com.fanjiabao.design.pattern.behavioral.memento.source_code_analysis;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: FanJiaBao
 * @createDate: 2026/4/29 16:43
 * @description: Hibernate / JPA 脏检查中的备忘录模式源码分析
 * 核心思想:
 *  - 查询 Entity 时, Hibernate 会保存 loadedState 原始快照
 *  - 修改托管状态 Entity 时, 只是修改 Java 内存对象
 *  - 事务提交时, Hibernate 自动 flush
 *  - flush 阶段执行脏检查: 当前状态 values vs 原始快照 loadedState
 *  - 如果发现不同, 自动生成 update SQL
 */
@Service
public class HibernateMementoService {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public HibernateMementoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 新增用户
     */
    @Transactional
    public Long createUser() {
        System.out.println("\n========== 1.新增用户 ==========");

        UserEntity user = userRepository.save(new UserEntity("fanjiabao", 24));
        System.out.println("保存后的用户: " + user + ", class: " + user.getClass());
        return user.getId();
    }

    /**
     * 查询用户并修改托管对象, 但不调用 save()
     */
    @Transactional
    public void updateUserWithoutSave(Long userId) {
        System.out.println("\n========== 2.清空持久化上下文 ==========");
        // 把当前 PersistenceContext 中待执行的 SQL 同步到数据库
        entityManager.flush();
        // 清空一级缓存，确保下面 findById 一定重新查询数据库
        entityManager.clear();

        System.out.println("\n========== 3.查询用户, Hibernate 保存 loadedState 快照 ==========");
        UserEntity dbUser = userRepository.findById(userId).orElseThrow();
        // 这里是备忘录思想出现的位置: Hibernate 内部会把查询出来的原始状态保存到 EntityEntry.loadedState
        System.out.println("查询出来的用户: " + dbUser + ", class: " + dbUser.getClass());

        System.out.println("\n========== 4.修改托管状态对象, 但不调用 save ==========");
        dbUser.setUsername("fanjiabao-update");
        dbUser.setAge(20);
        System.out.println("修改后的内存对象: " + dbUser);
        /**
         * 此时:
         *  当前状态 values:
         *      ["fanjiabao-update", 20]
         *  原始快照 loadedState:
         *      ["fanjiabao", 24]
         *  注意:
         *      这里还没有执行 update SQL。
         *      update SQL 会在事务提交 flush 时由 Hibernate 自动生成。
         */

        System.out.println("\n========== 5.方法结束, 事务提交, Hibernate 自动脏检查 ==========");
        /**
         * 方法结束后源码调用流程:
         *  updateUserWithoutSave(...) 方法执行完毕
         *      --> invocation.proceedWithInvocation(); ==> TransactionAspectSupport.class:187
         *          目标方法正常返回, 说明业务方法没有抛出异常
         *      提交事务
         *      --> this.commitTransactionAfterReturning(txInfo); ==> TransactionAspectSupport.class:275
         *          调用事务管理器提交
         *          --> txInfo.getTransactionManager().commit(txInfo.getTransactionStatus()); ==> TransactionAspectSupport.class:397
         *              进入事务提交模板方法
         *              --> AbstractPlatformTransactionManager.commit(...) ==> AbstractPlatformTransactionManager.class:352
         *                  真正提交事务
         *                  --> this.processCommit(defStatus); ==> AbstractPlatformTransactionManager.class:370
         *                      触发真正 doCommit
         *                      --> this.doCommit(status); ==> AbstractPlatformTransactionManager.class:403
         *                          JPA 事务提交
         *                          --> JpaTransactionManager.doCommit(DefaultTransactionStatus status) ==> JpaTransactionManager.class:317
         *                              获取 JPA EntityTransaction
         *                              --> EntityTransaction tx = txObject.getEntityManagerHolder().getEntityManager().getTransaction(); ==> JpaTransactionManager.class:324
         *                              提交 JPA 事务
         *                              --> tx.commit(); ==> JpaTransactionManager.class:325
         *                                  进入 Hibernate 事务提交
         *                                  --> EntityTransaction.commit() ==> EntityTransaction.java:42
         *                                      提交前触发 beforeCompletion
         *                                      --> SessionImpl.beforeTransactionCompletion() ==> SessionImpl.class:1543
         *                                          提交前自动 flush
         *                                          --> this.flushBeforeTransactionCompletion(); ==> SessionImpl.class:1545
         *                                              判断是否需要 flush
         *                                              --> if (this.mustFlushBeforeCompletion()) ==> SessionImpl.class:1642
         *                                              执行受管理的 flush
         *                                              --> this.managedFlush(); ==> SessionImpl.class:1644
         *                                                  真正 flush
         *                                                  --> this.doFlush(); ==> SessionImpl.class:368
         *  触发 FlushEvent
         *      --> SessionImpl.doFlush() ==> SessionImpl.class:368
         *          创建 FlushEvent
         *          --> FlushEvent event = new FlushEvent( this ); ==> SessionImpl.class:1401
         *          通知所有 FlushEventListener
         *          --> fastSessionServices.eventListenerGroup_FLUSH.fireEventOnEachListener( event, FlushEventListener::onFlush );  ==> SessionImpl.class:1402
         *          默认监听器:
         *              DefaultFlushEventListener
         *  进入 Hibernate Flush 监听器
         *      --> DefaultFlushEventListener.onFlush(FlushEvent event) ==> DefaultFlushEventListener.java:30
         *          获取 PersistenceContext
         *          --> PersistenceContext persistenceContext = source.getPersistenceContextInternal(); ==> DefaultFlushEventListener.java:32
         *          判断当前一级缓存中是否有实体或集合需要处理
         *          --> if (persistenceContext.getNumberOfManagedEntities() > 0 || persistenceContext.getCollectionEntriesSize() > 0) {...} ==> DefaultFlushEventListener.java:34
         *          开始 flush
         *          --> flushEverythingToExecutions(event); ==> DefaultFlushEventListener.java:40
         *  把 PersistenceContext 中的变化转换成 SQL 动作
         *      --> flushEverythingToExecutions(event); ==> DefaultFlushEventListener.java:40
         *          设置当前正在 flushing
         *          --> persistenceContext.setFlushing( true ); ==> AbstractFlushingEventListener.java:88
         *          准备实体和集合 flush
         *          --> session.getInterceptor().preFlush( persistenceContext.managedEntitiesIterator() ); ==> AbstractFlushingEventListener.java:77
         *          Flush 所有 Entity
         *          --> int entityCount = flushEntities( event, persistenceContext ); ==> AbstractFlushingEventListener.java:90
         *          Flush 所有 Collection
         *          --> int collectionCount = flushCollections( session, persistenceContext ); ==> AbstractFlushingEventListener.java:91
         *          设置 flushing 结束
         *          --> persistenceContext.setFlushing(false); ==> AbstractFlushingEventListener.java:97
         *  遍历所有托管 Entity
         *      --> int entityCount = flushEntities( event, persistenceContext ); ==> AbstractFlushingEventListener.java:90
         *          取出一级缓存中所有 EntityEntry
         *          --> final Map.Entry<Object,EntityEntry>[] entityEntries = persistenceContext.reentrantSafeEntityEntries(); ==> AbstractFlushingEventListener.java:209
         *          遍历每一个托管 Entity
         *          --> for ( Map.Entry<Object,EntityEntry> me : entityEntries ) {...} ==> AbstractFlushingEventListener.java:215
         *              取出实体对象
         *              --> Object entity = me.getKey();
         *              取出实体对应的 EntityEntry
         *              --> EntityEntry entry = me.getValue(); ==> AbstractFlushingEventListener.java:218
         *                  --> entityEvent = createOrReuseEventInstance( entityEvent, source, me.getKey(), entry ); ==> AbstractFlushingEventListener.java:222
         *                      创建 FlushEntityEvent
         *                      --> return new FlushEntityEvent( source, key, entry );
         *                  触发 FlushEntityEventListener
         *                  --> flushListeners.fireEventOnEachListener( entityEvent, FlushEntityEventListener::onFlushEntity ); ==> AbstractFlushingEventListener.java:226
         *              默认进入:
         *                  DefaultFlushEntityEventListener.onFlushEntity(...)
         *  单个 Entity 脏检查
         *      --> DefaultFlushEntityEventListener.onFlushEntity(FlushEntityEvent event) ==> DefaultFlushEntityEventListener.java:131
         *          获取当前 Entity
         *          --> final Object entity = event.getEntity();
         *          获取 EntityEntry
         *          --> final EntityEntry entry = event.getEntityEntry();
         *          获取当前 Session
         *          --> final EventSource session = event.getSession();
         *          判断当前 Entity 是否可能是脏对象
         *          --> final boolean mightBeDirty = entry.requiresDirtyCheck(entity); ==> DefaultFlushEntityEventListener.java:136
         *          获取当前 Entity 属性值
         *          --> final Object[] values = getValues(entity, entry, mightBeDirty, session);
         *          此时当前状态:
         *              values = ["fanjiabao-update", 20]
         *          把当前状态设置到事件对象中
         *          --> event.setPropertyValues(values); ==> DefaultFlushEntityEventListener.java:140
         *          判断是否需要执行 update
         *          --> if (isUpdateNecessary(event, mightBeDirty)) {...} ==> DefaultFlushEntityEventListener.java:146
         *  判断是否需要 update
         *      --> isUpdateNecessary(event, mightBeDirty) ==> DefaultFlushEntityEventListener.java:146
         *          如果可能是脏对象, 进入脏检查
         *          --> dirtyCheck(event); ==> DefaultFlushEntityEventListener.java:215
         *          脏检查完成后, 从 event 中取 dirtyProperties
         *          --> int[] dirtyProperties = getDirtyProperties( event ); ==> DefaultFlushEntityEventListener.java:467
         *          如果 dirtyProperties != null, 说明属性发生变化
         *          --> return true;
         *  获取 loadedState 并执行脏检查(备忘录模式核心)
         *      --> dirtyCheck(event); ==> DefaultFlushEntityEventListener.java:215
         *          获取当前对象状态
         *          --> final Object[] values = event.getPropertyValues(); ==> DefaultFlushEntityEventListener.java:494
         *          获取原始快照 loadedState
         *          --> final Object[] loadedState = entry.getLoadedState(); ==> DefaultFlushEntityEventListener.java:495
         *              这里就是备忘录模式最核心的位置: entry.getLoadedState(),
         *              loadedState 是查询 Entity 时保存的原始状态: loadedState = ["fanjiabao", 24]
         *              当前状态 values: values = ["fanjiabao-update", 20]
         *          获取 EntityEntry
         *          --> final EntityEntry entry = event.getEntityEntry(); ==> DefaultFlushEntityEventListener.java:496
         *          获取 EntityPersister
         *          --> final EntityPersister persister = entry.getPersister();
         *          获取实体 id
         *          --> final Object id = entry.getId();
         *          当前状态和原始快照比较
         *          --> dirtyProperties = persister.findDirty( values, loadedState, entity, session ); ==> DefaultFlushEntityEventListener.java:499
         *              进入实体持久化器
         *              --> AbstractEntityPersister.findDirty(...) ==> AbstractEntityPersister.java:3895
         *                  调用 DirtyHelper 比较属性
         *                  --> DirtyHelper.findDirty(...) ==> DirtyHelper.java:34
         *                      遍历每一个属性
         *                      --> for (int i = 0; i < span; i++) {...}
         *                      判断某个属性是否 dirty
         *                      --> isDirty(properties, currentState, previousState, includeColumns, session, i);
         *                          底层会根据 Hibernate Type 判断两个值是否相等
         *                          --> return properties[i].isDirtyCheckable() && properties[i].getType().isDirty( previousState[i], currentState[i], includeColumns[i], session);
         *                      对比 username:
         *                          previousState = "fanjiabao"
         *                          currentState  = "fanjiabao-update"
         *                          结果: dirty
         *                      对比 age:
         *                          previousState = 24
         *                          currentState  = 20
         *                          结果: dirty
         *                  返回脏属性下标
         *                  --> return dirtyProperties;
         *          设置脏属性到事件对象
         *          --> event.setDirtyProperties(dirtyProperties); ==> DefaultFlushEntityEventListener.java:476
         *          最终:
         *              dirtyProperties = [username属性下标, age属性下标]
         *  发现脏对象后封装 update 动作
         *      --> if (isUpdateNecessary(event, mightBeDirty)) {...} ==> DefaultFlushEntityEventListener.java:146
         *          进入调度更新
         *          --> substitute = scheduleUpdate( event ) || substitute;
         *              获取 EntityEntry
         *              --> final EntityEntry entry = event.getEntityEntry();
         *              获取当前 values
         *              --> final Object[] values = event.getPropertyValues();
         *              获取 dirtyProperties
         *              --> final int[] dirtyProperties = getDirtyProperties(event, intercepted);
         *              获取当前 Entity 版本信息, 当前案例没有版本字段可忽略
         *              --> getNextVersion(event); ==> DefaultFlushEntityEventListener.java:247
         *              创建 EntityUpdateAction
         *              --> session.getActionQueue().addAction( ==> DefaultFlushEntityEventListener.java:257
         *                      new EntityUpdateAction(
         * 						entry.getId(),
         * 						values,
         * 						dirtyProperties,
         * 						event.hasDirtyCollection(),
         * 						status == Status.DELETED && !entry.isModifiableEntity()
         * 								? persister.getValues( entity )
         * 								: entry.getLoadedState(),
         * 						entry.getVersion(),
         * 						nextVersion,
         * 						entity,
         * 						entry.getRowId(),
         * 						persister,
         * 						session
         * 				        )
         * 		        );
         *              注意:
         *                  这里 EntityUpdateAction 同时拿到了:
         *                      当前状态 values
         *                      脏属性 dirtyProperties
         *                      原始快照 entry.getLoadedState()
         *              加入 ActionQueue, Hibernate 不是马上执行 SQL, 而是先把 update 封装成 EntityUpdateAction, 放入 ActionQueue 中统一管理。
         *              --> session.getActionQueue().addAction(updateAction); ==> DefaultFlushEntityEventListener.java:257
         *  执行 ActionQueue 中的 SQL 动作
         *      Flush 阶段前面只是把变化转换成 Action, 真正执行 SQL 在 performExecutions 中完成。
         *      --> performExecutions(session); ==> AbstractFlushingEventListener.java:347
         *          准备执行动作
         *          --> session.getActionQueue().prepareActions();
         *          执行动作队列
         *          --> session.getActionQueue().executeActions();
         *      --> ActionQueue.executeActions() ==> ActionQueue.java:493
         *          按 Hibernate 定义的顺序执行 insert、update、delete 等动作
         *          --> for (OrderedActions action : ORDERED_OPERATIONS) {...}
         *          执行 update 队列
         *          --> executeActions(list);
         *      --> EntityUpdateAction.execute()
         *          执行实体更新动作
         *          --> persister.getUpdateCoordinator().update(...);
         *      --> UpdateCoordinatorStandard.update(...)
         *          根据 dirtyProperties 决定哪些字段需要进入 SQL
         *          当前 username 和 age 都发生变化
         *          最终执行 JDBC update
         *          --> mutationExecutor.execute(...)
         *  最终生成 SQL:
         *      update tb_user
         *      set age=?, username=?
         *      where id=?
         *  总结:
         *      1.updateUserWithoutSave 方法正常结束后, Spring 事务拦截器提交事务
         *      2.JpaTransactionManager 提交 JPA 事务
         *      3.Hibernate 在提交前自动 flush
         *      4.flush 时遍历 PersistenceContext 中所有托管 Entity
         *      5.对 UserEntity 执行 dirtyCheck
         *      6.dirtyCheck 使用 '当前状态values' 和 '原始快照loadedState' 做对比
         *      7.loadedState 就是 Hibernate 保存的 '备忘录'
         *      8.如果 values 和 loadedState 不同, 封装 EntityUpdateAction
         *      9.ActionQueue 最终执行 update SQL
         */
    }

    /**
     * 重新查询数据库, 验证最终结果
     */
    @Transactional(readOnly = true)
    public void verifyUser(Long userId) {
        System.out.println("\n========== 6.重新查询数据库,验证最终结果 ==========");
        UserEntity user = userRepository.findById(userId).orElseThrow();
        System.out.println("数据库最终数据: " + user);
    }
}
