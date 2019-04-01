# Spring的JDBC模板的使用
## JDBC基本使用
```
public void test01(){
    //创建连接池
    DriverManagerDataSource dataSource=new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql:///hibernate?serverTimezone=UTC");
    dataSource.setUsername("username");
    dataSource.setPassword("password");
    //创建JDBC模板
    JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
    jdbcTemplate.update("insert into mtm_role values (null ,?)","boss");
}
```

## Spring管理下的JDBC使用
### 引入配置
```
<!-- 将链接池交给spring管理 -->
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql:///hibernate?serverTimezone=UTC"/>
    <property name="username" value="username"/>
    <property name="password" value="password"/>
</bean>
```

```
<!-- 将模板将给spring管理 -->
<bean id="template" class="org.springframework.jdbc.core.JdbcTemplate">
    <property name="dataSource" ref="dataSource"/>
</bean
```
### 使用方式
```
public void test01(){
    jdbcTemplate.update("insert into mtm_role values (null,?)","HR");
}
```

### 其他开源链接池 

## 通过配置文件设置JDBC属性

### 建立配置文件
+ jdbc.properties
> jdbc.driverClassName=com.mysql.jdbc.Driver   
jdbc.url=jdbc:mysql:///hibernate?serverTimezone=UTC   
jdbc.username=username   
jdbc.password=password   

### 引入配置文件
```
<!-- 引入JDBC配置文件 -->
<context:property-placeholder location="classpath:jdbc.properties"/>
```
### 获取配置文件的数据配置JDBC
```
<!-- 将链接池交给spring管理 -->
<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="${jdbc.driverClassName}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</bean>
```

## 使用Spring的JDBC模板完成CRUD
+ 添加
```
public void insertTest(){
    jdbcTemplate.update("insert into mtm_role values (null,?)","July");
}
```
+ 删除
```
public void deleteTest(){
    jdbcTemplate.update("delete from mtm_role where id =?",6);
}
```
+ 单属性查询
```
public void queryTest(){
    String name=jdbcTemplate.queryForObject("select name from mtm_role where id=?",String.class,5);
    System.out.println(name);
}
```
+ 聚合函数
```
public void countTest(){
    Integer count=jdbcTemplate.queryForObject("select count(*) from mtm_role",Integer.class);
    System.out.println(count);
}
```

### 查询返回封装对象

#### 新建对象继承RowMapper
```
class MyRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs,int rowNum) throws SQLException {
        Role role=new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));
        return role;
    }
}
```
+ 单个对象
```
public void test01(){
   Role role= jdbcTemplate.queryForObject("select * from mtm_role where id=?",new MyRowMapping(),5);
    System.out.println(role.toString());
}
```
+ 对象集合
```
public void test02(){
    List<Role> roles= jdbcTemplate.query("select * from mtm_role",new MyRowMapping());
    for (Role role:roles) {
        System.out.println(role.toString());
    }
}
```