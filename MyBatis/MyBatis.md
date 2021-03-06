# MyBatis入门

## 配置文件

### SqlMapConfig.xml
1. 约束
```
<!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
```
2.1 数据库连接配置：手动输入
```
<configuration>
    <!-- 和spring整合后 environments配置将废除 -->
    <environments default="development">
        <environment id="development">
            <!-- 使用jdbc事务管理 -->
            <transactionManager type="JDBC" />
            <!-- 数据库连接池 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver" />
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC" />
                <property name="username" value="password" />
                <property name="password" value="username" />
            </dataSource>
        </environment>
    </environments>
</configuration>
```

2.2 数据库连接配置：文件输入   

+ jdbc.properties
```
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC
jdbc.username=username
jdbc.password=password
```
+ 在核心配置文件中引入
```
<properties recource="jdbc.properties"/>
```
+ 配置数据库连接
```
<property name="driver" value="&{jdbc.driver}" />
<property name="url" value="&{jdbc.url}" />
<property name="username" value="&{jdbc.username}" />
<property name="password" value="&{jdbc.password}" />
```
3.1 加载映射文件：recource方法   

映射文件User.xml将User类与数据库中的表建立映射关系
```
<mappers>
    <mapper resource="com/mybatis/demo1/User.xml"/>
    ...
</mappers>
```
3.2 加载映射文件：package方法(推荐)
```
<mappers>
    <!-- 加载指定包下的所有与接口名相同的映射文件 -->
    <package name="com.mybatis.demo2"/>
</mappers>
```
4. 别名设置
```
<typeAliases>
    <!-- 指定包下的类都将类名定义为别名 -->
    <package name="com.mybatis.demo2"/>
</typeAliases>
```

### log4j.properties
```
# Global logging configuration
log4j.rootLogger=DEBUG, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

### User.xml
+ 约束
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
```

+ 查询语句
```
<mapper namespace="user">
    <select id="getUserById" parameterType="int" resultType="com.mybatis.demo1.User">
       select * from user where id=#{id}
    </select>
    ...
</mapper>
```
+ 添加语句
```
<insert id="insertUser" parameterType="com.mybatis.demo1.User">
    insert into user(username, gender, phone, address) values (#{username},#{gender},#{phone},#{address})
</insert>
```
+ 更新语句
```
<update id="updateUser" parameterType="com.mybatis.demo1.User">
    update user set address=#{address} where id=#{id}
</update>
```
+ 删除语句
```
<delete id="deleteUserById" parameterType="int">
    delete from user where id=#{id}
</delete>
```

## 测试类
+ init方法中完成必要对象的创建
+ test01中对User.xml文件中配置的查询方法进行测试
```
@Before
public void init() throws IOException {
    //创建SqlSessionFactoryBuilder对象
    sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
    //创建输入流引入配置文件
    stream=Resources.getResourceAsStream("SqlMapConfig.xml");
    //加载配置文件，创建SqlSessionFactory对象
    sqlSessionFactory=sqlSessionFactoryBuilder.build(stream);
    //创建SqlSession对象
    sqlSession=sqlSessionFactory.openSession();
}

@Test
public void test01(){
    User user=sqlSession.selectOne("user.getUserById",1);
    System.out.println(user.getUsername());
...
}
```
## MyBatis Dao

### 传统方式
+ 创建接口
   + ...
+ 实现接口
   + ...
### 接口动态代理
**开发规则**
1. namespace必需是接口的全路径名
2. 映射文件的id必须与接口名一致
3. 映射文件的parameterType必须与接口一致
4. 映射文件的resultType必须与接口的返回类型一致   

**创建接口**
```
public interface UserDao {
    public User getUserById(int id);
    public void insertUser(User user);
    public void updateUserById(User user,int id);
    public void deleteUserById(User user,int id);
}
```
**创建映射**
```
<mapper namespace="com.mybatis.demo2.UserDao">
    <select id="getUserById" parameterType="int" resultType="com.mybatis.demo1.User">
        select * from user where id=#{id}
    </select>
    ...
</mapper>
```
## 输入输出映射
### parameterType
+ 简单类型
   + int
   + string
   + ...
+ pojo对象
   + ... 
+ 包装pojo对象
```
public void test07(){
    QueryVo queryVo=new QueryVo();
    User user=new User();
    user.setUsername("A2");
    queryVo.setUser(user);
    User result=sqlSession.selectOne("getUserByQueryVo",queryVo);
    System.out.println(result.getUsername());
}
```
```
<select id="getUserByQueryVo" parameterType="com.mybatis.demo1.QueryVo" resultType="com.mybatis.demo1.User" >
    select * from  user where username like '%${user.username}%'
</select>
```
### resultType
+ 简单类型
   + int
   + string
   + ...
+ pojo对象
   + ...
+ pojo对象集合
   + ...
+ resultMap
```
<!-- resultMap使用 -->
<resultMap id="userMap" type="com.mybatis.demo1.User">
    <!-- id标签用于绑定主键 -->
    <id property="id" column="id"/>
    <!-- result标签用于绑定普通字段 -->
    <!-- 若类中的属性和对应字段名相同可省略 -->
    <result property="username" column="username"/>
    <result property="gender" column="gender"/>
</resultMap>
<select id="getUserResultMap" parameterType="int" resultMap="userMap">
    select * from user where id=#{id}
</select>
```

## 动态sql
### if
```
<select id="getUserByWhere" parameterType="com.mybatis.demo1.User" resultType="com.mybatis.demo1.User">
    select * from user where 1=1
    <if test="id!=null">
        and id=#{id}
    </if>
    <if test="username!=null and username!='' ">
        and username like '%${username}%'
    </if>
</select>
```

### where
```
<select id="getUserByWhere2" parameterType="com.mybatis.demo1.User" resultType="com.mybatis.demo1.User">
    select * from user
        <where >
            <if test="id!=null">
                and id=#{id}
            </if>
            <if test="username!=null and username!='' ">
                and username like '%${username}%'
            </if>
        </where>
</select>
```

### foreach
```
<select id="getUserByIds" parameterType="com.mybatis.demo1.QueryVo" resultType="com.mybatis.demo1.User">
    select * from user
    <where>
        <foreach collection="ids" open="id in(" separator="," close=")" item="id">
            #{id}
        </foreach>
    </where>
</select>
```

### sql片段
+ sql片段定义
```
<sql id="user_column">
    id,username,gender,phone,address
</sql>
```
+ sql片段使用
```
<select id="getUserByIdSql" parameterType="com.mybatis.demo1.User" resultType="com.mybatis.demo1.User">
    select
    <include refid="user_column"/>
    from  user
    <where>
        <if test="id!=null">
            id=#{id}
        </if>
    </where>
</select>
```
## 关联查询

### 一对一查询
**注：** 新建order类与前面的user类进行一对一关联  
 
**resultMap实现**
+ order类
> user_id为表order的外键指向user表的id   
user引用指向id为user_id的对象
```
public class Order {
    private Integer id;
    private Integer user_id;
    private String number;
    private Date createtime;
    private String note;
    private User user;
    get/set...	
}
```

+ 接口动态代理
```
public interface OrderMap{
    List<Order> getOrderListMap();
    ...
}
```

+ 配置文件
```
<!-- 一对一关联查询：resultMap实现 -->
<resultMap id="order_user_map" type="com.mybatis.demo3.Order">
    <!-- 主键映射 -->
    <id property="id" column="id"/>
    <!-- 普通字段映射 -->
    <result property="user_id" column="user_id"/>
    <result property="number" column="number"/>
    <result property="createtime" column="createtime"/>
    <result property="note" column="note"/>
    <!--
        配置关联对象user
        property：order类中的user对象名
        javaType：user类的路径
    -->
    <association property="user" javaType="com.mybatis.demo3.User">
        <id property="id" column="id"/>

        <result property="username" column="username"/>
        <result property="address" column="address"/>
    </association>
</resultMap>
```
```
<select id="getOrderUserMap" resultMap="order_user_map">
    SELECT
      o.`id`,
      o.`user_id`,
      o.`number`,
      o.`createtime`,
      o.`note`,
      u.username,
      u.address
    FROM
      `order` o
      LEFT JOIN `user` u
        ON u.id = o.user_id
</select>
```
+ 测试类
```
public void test03(){
    OrderMapper orderMapper=sqlSession.getMapper(OrderMapper.class);
    List<Order> list=orderMapper.getOrderUserMap();
    for (Order order:list) {
        System.out.println(order.toString());
    }
}
```