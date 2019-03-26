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

## Hibernate使用方法

### 创建实体类
+ 按照与表字段一一映射的关系新建一个实体类
```
public class Customer {
    private long cust_id;
    private String cust_name;
    private String cust_source;
    private String cust_industry;
    private String cust_level;
    private String cust_phone;
    private String cust_moblie;
    ...
}
```
### 创建映射
**创建映射配置文件**
+ 命名规则：类名.hbm.xml
+ 配置文件格式
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <!-- 建立类与表的映射关系 -->
    <class name="com.mhh.HibernateDemo.Customer" table="cst_customer">
        <!-- 建立类中属性与表中主键的映射 -->
        <id name="cust_id" column="cust_id">
            <generator class="native"></generator>
        </id>
        <!-- 建立类中属性与表中普通字段的映射 -->
        <property name="cust_name" column="cust_name"></property>
        <property name="cust_source" column=""></property>
        <property name="cust_industry" column=""></property>
        <property name="cust_level" column=""></property>
        <property name="cust_phone" column=""></property>
        <property name="cust_moblie" column=""></property>
    </class>
</hibernate-mapping>
```
**创建核心配置文件**

+ 命名规则：hibernate.cfg.xml
+ 配置文件格式
```
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="connection.url">jdbc:mysql://localhost:3306/hibernate?serverTimezone=UTC</property>
        <property name="connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="connection.username">root</property>
        <property name="connection.password">1443778731mhx</property>
        <property name="current_session_context_class">thread</property>
        <!-- 控制台打印sql执行语句 -->
        <property name="show_sql">true</property>
        <!-- 格式化打印出的sql执行语句 -->
        <property name="format_sql">true</property>
        <property name="hbm2ddl.auto">update</property>
        <!-- 设置方言 -->
        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
        <!-- 引入映射文件 -->
        <mapping resource="com/mhh/hibernate/demo/CstCustomerEntity.hbm.xml"/>
        <mapping class="com.mhh.hibernate.demo.CstCustomerEntity"/>
    </session-factory>
</hibernate-configuration>
```

### 映射的配置

**class标签**  
+ 用来建立类与表的映射关系
+ 属性：
> name:类的全路径   
table:表名（类名与表名一致可以省略）   
catalog:数据库名（可省略）   

**id标签**
+ 建立类中属性与表中主键的映射关系
+ 属性:
> name:类中的属性名   
column:表中的字段名   
length:长度（自动建表时的长度）   
type:类型（可省略）   

**property标签**
+ 建立类中的属性与表中普通字段的映射关系
+ 属性：
> name:类中的属性名   
column:表中的字段名   
length:长度（自动建表时的长度）   
type:类型（可省略）
 
### 核心的配置

**必须的配置**   
+ 链接数据库的基本参数
> 驱动类   
url路径   
用户名   
密码
   
+ 方言
   
**可选的配置**   
+ 显示sql
> hibernate.show_sql
+ 格式化sql
> hibernate.format_sql
+ 自动建表
> hibernate.hbm2ddl.auto  
>> none:不使用   
create:删除已有的表，并新建一个（测试）   
create-drop:删除已有的表，并新建一个，执行操作，再删除表（测试）   
update:有表就使用，没有就新建（会更新表的结构）   
validate:如果没有表不会创建，只能使用已有的表并校验表的结构是否与映射配置相同，不同则报错   

**映射文件的引入**
+ 引入映射文件位置
> <mapping resource="文件路径">

### 测试类
```
public void test(){
    //1.创建configuration对象，加载核心配置文件
    Configuration configuration=new Configuration().configure();
    //2.创建SessionFactory对象，类似于连接池
    SessionFactory sessionFactory=configuration.buildSessionFactory();
    //3.获取Session对象，类似于Connection
    Session session=sessionFactory.openSession();
    //4.开启事务
    Transaction transaction=session.beginTransaction();
    //5.执行数据库操作
    ...
    //6.提交事务
    transaction.commit();
    //7.释放资源
    session.close();
}
```

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
> hibernate放弃外键管理，需要手动编写程序设置
+ foreign
> 外部的，一对一关联映射情况下使用
