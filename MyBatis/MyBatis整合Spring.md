# MyBatis整合Spring

## 整合思路
1. SqlSessionFactory对象应该放到spring容器中作为单例存在
2. 传统dao的开发方式中，应该从spring容器中获得sqlsession对象
3. Mapper代理形式中，应该从spring容器中直接获得mapper的代理对象
4. 数据库的连接以及数据库连接池事务管理都交给spring容器来完成

## 整合步骤

### 引入jar
+ ...

### 配置文件
**applicationContext.xml**
+ 数据库配置
```
<!-- 引入数据库配置文件 -->
<context:property-placeholder location="jdbc.properties"/>
<!-- 配置数据库连接 -->
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="${jdbc.driver}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```

+ SqlSessionFactory配置
```
<!-- 配置SqlSessionFactory -->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <!-- 连接数据库 -->
    <property name="dataSource" ref="dataSource"/>
    <!-- 获取配置文件 -->
    <property name="configLocation" value="SqlMapConfig.xml"/>
    <!-- 设置包别名扫描 -->
    <property name="typeAliasesPackage" value="com.mybatis.demo1.pojo"/>
</bean>
```

+ 接口动态代理配置
```
<!-- 动态代理：包扫描 -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.mybatis.demo1.mapper"/>
</bean>
```
**SqlMapConfig.xml**
+ 因为数据库及SqlSessionFactory都有spring代理，因此无序再次配置，只需要一个空文件就行

**jdbc.properties**
+ ...   

**log4j.properties**
+ ...


### 创建pojo
+ ...
### 创建接口及映射文件
+ ...

### 测试类
+ 方式一   

UserMapper需添加component注释，或者在appplicationContext中配置
```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void test(){
        User user=userMapper.getUserById(1);
        System.out.println(user.toString());
    }
    ...
}
```
+方式二
```
public class UserMapperTest {
    private ApplicationContext applicationContext;

    @Before
    public void init(){
        applicationContext=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    @Test
    /**
     * 按照id查询测试
     */
    public void test01(){
        UserMapper userMapper=applicationContext.getBean(UserMapper.class);
        User user=userMapper.getUserById(1);
        System.out.println(user.toString());
    }
    ...	
}
```