# Hibernate配置及使用方法

## 创建实体类
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
## 创建映射配置文件
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
**映射的配置**

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

## 创建核心配置文件
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

**核心的配置**

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

## 测试类
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