# Hibernate

## JavaEE三层结构概述  
+ web层
> 技术：servlet，jsp...    
框架：struts2，springMVC...

+ 业务逻辑层
> 技术：JavaBean...   
框架：Spring...

+ 持久层
> 技术：JDBC   
框架：Hibernate，Mybatis...


## Hibernate简介 
Hibernate是一个开放源代码的对象关系映射框架，它对JDBC进行了非常轻
量级的对象封装，简化了DAO编码工作，性能好，可扩展型强，是一个持久层的ORM框架   
+ ORM：Object Relational Mapping
> 将java中的对象与关系型数据库中的表建立映射关系，从而可以通过操作对象来操作数据库表中的数据


### API
 
**session**

**简介：** 类之于connection，是hibernate与数据库的连接对象，非线程安全，使用时不能定义为全局变量
+ 保存方法
> Serializable save(Object obj)
+ 查询方法
> T get(Class c,Serializable id)    
立即加载：遇到get方法立即发送sql语句执行查询   
查询返回对象：真实对象   
查询一个不存在的对象：返回null   
T load(Class c,Serializable id)   
延迟加载：用到时才会发送sql语句执行查询   
查询返回对象：代理对象   
查询一个不存在的对象：返回异常

+ 修改方法
> void update(Object obj)    
先创建再修改会把没修改部分一起替换，不建议使用    
先查询再就该只会修改指定部分，建议使用

+ 删除方法
> void delete(Object obj)

**Transaction**

**简介：** hibernate中管理事务的对象

+ 方法
> commit()   
rollback()

**Query**

用于接收HQL   
+ 简单查询
```
String hql="from UserEntity where name like ?1";
Query query=session.createQuery(hql);
query.setParameter(1,"J%");
```
+ 分页查询
```
query.setFirstResult(0);
query.setMaxResults(3);
```

**Criteria**
+ 查询
```
Criteria criteria=session.createCriteria(UserEntity.class);
criteria.add(Restrictions.like("name","J%"));
```
+ 分页
``
criteria.setFirstResult(0);
criteria.setMaxResults(2);
``
## 持久化类的编写规则

**持久化：** 将内存中的对象持久化到数据库的过程   
**持久化类：** 与数据库建立映射关系的类，即java类+数据库表   

### 持久化类的编写规则
+ 对持久化类提供一个无参构造方法   
> hibernate底层需要使用反射生成实例
+ 属性需要私有，并提供get，set方法
> hibernate获取设置对象的值
+ 提供一个唯一标识与数据库主键对应
> hibernate通过持久化类的OID属性区分是否是同一个对象
+ 类中属性尽量使用包装类类型
> 基本数据类型默认值是0，会出现歧义，包装类对象默认值是null
+ 持久化类不要使用final
> hibernate的延迟加载返回的是一个代理对象，而代理对象使用了底层字节码增强
技术继承了这个类，final修饰之后无法被继承，延时加载方法会失效



## 主键生成策略

### 自然主键
**自然主键：** 主键本身就是表中的一个字段
### 代理主键
**代理主键：** 主键本身不是表中的必须的字段   
+ 实际开发中尽量使用代理主键   
   + 主键参与到业务逻辑中，后期可能需要修改源代码
   + 而程序设计应满足OCP原则：对程序扩展是open，修改源码是close
   
### hibernate主键生成策略
实际开发一般不会手动设置主键，hibernate为了减少程序编写，提供了很多主键生成策略
+ increment
> hibernate提供的自动增长机制，适用于short，int，long类型的主键，单线程程序中使用
+ identity
> 适用于short，int，long类型的主键，使用数据库底层的自动增强机制，例如MYSQL，MSSQL
+ sequence
> 适用于short，int，long类型的主键，采用序列方式，例如Oracle
+ uuid
> 适用于字符串类型主键
+ native
> 本地策略，可以在identity和sequence之间自动切换
+ assigned
> hibernate放弃外键管理，需要手动或者编写程序设置
+ foreign
> 外部的，一对一关联映射情况下使用

### 持久化类的三种状态

hibernate是持久层框架，通过持久型类完成ORM操作，为了更好的管理持久化类将其分成三种状态

**瞬时态(Transient)**   

+ 没有唯一标识OID，没有被session管理

**持久态(Persistent)**

+ 有唯一标识OID，被session管理
> 持久化类的持久态对象可以i自动更新数据库

**托管态(Dtached)**

+ 有唯一标识OID，但没有被session管理

**瞬时态对象**
+ 获取对象
> Object obj=new Object()

+ 转换：瞬时->持久
> save(Object obj),saveOrUpdate(Object obj)..

+ 转换：瞬时->托管
> obj.setId();

**持久态对象**
+ 获取
> get(),load()...
+ 转换：持久->瞬时
> delete()
+ 转换：持久->托管
> close(),clear(),evict(Object obj)

**托管态对象**
+ 获取
> Object obj=new Object()   
obj.setId()
+ 转化：托管->持久
> update(),savOrUpdate()...

### 持久态对象特性

+ 自动跟新数据库，无序调用update方法
> 基于hibernate的一级缓存实现

## hibernate的缓存
**缓存：** 一种优化方式，将数据放入内存，使用是直接从缓存中获取，不通过数据源

### hibernate的一级缓存
**简介：** session级别的缓存，生命周期与session一致，自带不可卸载

**一级缓存的特殊区域：快照区**
> 获取数据库数据的同时会对数据做一个快照，当提交时，对比缓存及快照，若数据没有
发生变化则不许提交

## hibernate的事务管理
### 事务特性

+ 原子性：事务不可分割   
+ 一致性：事务执行前后，数据完整性保持一致   
+ 隔离性：事务执行过程中，不受到其他事务的干扰   
+ 持久性：事务执行完毕后，数据持久到数据库中

### 隔离性可能引起的问题
**读问题**   
+ 脏读
> 一个事务读到另一个事务未提交的数据
+ 不可重复读
> 一个事务读到另一个事务update的数据，导致多次查询结果不一致
+ 虚读
> 一个事物读到另一个事务insert的数据，导致多次查询结果不一致

**读问题解决方式**   

事务的隔离级别   
+ read uncommitt
> 所有问题都会发生
+ read committed
> 解决脏读
+ repeatable read
> 解决脏读和不可重复读
+ serializable
> 解决所有问题

**hibernate设置隔离级别**   

核心配置文件中添加属性
> `<property name="hibernate.connection.isolation">4</property>`

**写问题**

### 项目中管理事务

+ 业务开始之前打开事务,业务执行之后提交事务；执行过程中出现异常，回滚事务
+ 在dao层操作数据库需要用到session对象，在service控制事务也是使用session对象完成，
我们要确保dao层和service层使用的使用同一个session对象
+ 在hibernate中,确保使用同一个session的问题，hibernate提供了sf.getCurrentSession()
方法，可获得与当前线程绑定的session对象
+ 调用getCurrentSession方法必须先进行配置
> `<property name="hibernate.current_session_context_class">thread</property>`   
+ 通过getCurrentSession方法获得的session对象,
当事务提交时session会自动关闭