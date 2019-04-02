# Spring的事务管理
## Spring的事务管理API

### PlatformTransactionManager
+ spring用于管理事务的真正对象
   + DataSourceTransactionManager
   > 底层使用JDBC管理事务
   + HibernateTransactionManager
   > 底层使用Hibernate管理事务
   
### TransactionDefinition
+ 用于管理事务的相关信息
   + 隔离级别
   + 超时信息
   + 传播行为
   + ...
   
### TransactionStatus
+ 用于记录事务管理过程中事务的状态

## spring事务的传播行为
**简介：** 用于管理复杂业务中不同方法相互调用的问题
### Spring提供了7中事务传播行为

#### 保证多个操作在同一个事务中
假设方法B中使用了方法A
+ propagation_required
> 默认值，如果A中有事务，就使用A的事务，没有就创建一个事务，将操作包含起来
+ propagation_supports
> 如果A中有事务，就使用A的事务，没有就不使用事务
+ propagation_mandatory
> 如果A中有事务，就使用A的事务，没有就抛出异常

#### 保证多个操作不再同一个事物中
+ propagation_required_new
> 如果A中有事务，将A的事务挂起（暂停），创建新事务，只包含自身操作;
如果A中没有事务，创建一个新事务，包含自身操作
+ propagation_not_support
> 如果A中有事务，将A的事务挂起；没有就不使用事务管理
+ propagation_never
> 如果A中有事务，报异常

#### 嵌套式事物
+ PROPAGATION_NESTED	
> 如果A中有事务，按照A的事务执行，执行完成后，设置一个保存点，
执行B中的操作，如果没有异常，执行通过，如果有异常，可以选择回滚到最初始位置，
也可以回滚到保存点

## Spring的事物管理

### 搭建Spring事物管理环境

+ 创建Dao接口和其实现类
> AccountDao   
AccountDaoImpl
+ 创建Service接口和其实现类
> AccountService   
AccountServiceImpl
+ 将Service和Dao交给spring管理

### 管理方式1：编程式
+ 配置事务管理平台
```
<bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager" id="transactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>
```
+ 配置事务管理模板
```
<bean class="org.springframework.transaction.support.TransactionTemplate" name="transactionTemplate">
    <property name="transactionManager" ref="transactionManager"/>
</bean>
```
+ 业务层service注入管理模板
```
<bean id="accountService" class="com.spring.transaction.demo1.AccountServiceImpl">
    <!-- 将事务模板注入service -->
    <property name="transactionTemplate" ref="transactionTemplate"/>
</bean>
```
+ service中编写事务管理代码
```
@Override
public void transfer(String from, String to, int money) {
    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
        @Override
        protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            accountDao.out(from, money);
            accountDao.in(to, money);
        }
    });
}
```

### 管理方式2：声明式事务管理——xml实现
+ 配置事务管理平台
> ...
+ 配置事务管理模板
> ...
+ 配置advice
```
<!-- 配置事务的advice -->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        <!-- 事务管理的规则 -->
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>
```

+ 配置AOP
```
<!-- AOP配置 -->
<aop:config >
    <aop:pointcut id="pointcut1" expression="execution(* com.spring.transaction.aop_demo2.AccountServiceImpl.transfer(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut1"/>
</aop:config>
```

### 管理方式3：声明式事务管理——注解实现
+ 配置事务管理平台
> ...
+ 配置事务管理模板
> ...
+ 开启事务注解
```
<!-- 开启事务注解 -->
<tx:annotation-driven/>
```
+ service中添加注解
```
@Transactional
```

+