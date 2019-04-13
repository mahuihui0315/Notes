# Database Connection Pool

## 定义
+ 内存中分配一块空间，存放多个数据库链接对象，提供给java程序，用完返回链接池

## 接口规范
sun定义了一套连接池规范：interface DataSource,
但是没有返回链接的接口需要手动添加，因此无法面向接口编程
解决方法：修改close方法，改为返回对象
1. 直接修改源码
2. 继承并重写方法
3. 使用装饰者模式
4. 动态代理

## DBCP
Database Connection pool

### jar
+ commons-dbcp-1.4
+ commons-pool-1.5.6

### 使用方法
1. 手动配置
```
//1.建立数据源对象            
BasicDataSource dataSource=new BasicDataSource();
//2.配置数据源
dataSource.setDriverClassName("com.mysql.jdbc.Driver");
dataSource.setUrl("jdbc:mysql://localhost/bank");
dataSource.setUsername("username");
dataSource.setPassword("password");
```
2.使用配置文件
```
//获取配置文件
InputStream is=new FileInputStream("DBCP.properties");
Properties properties=new Properties();
properties.load(is);
//创建数据源对象
BasicDataSourceFactory dataSourceFactory=new BasicDataSourceFactory();
DataSource dataSource=dataSourceFactory.createDataSource(properties);
```

## C3P0（重点）

### jar
+ c3p0-0.9.5.2
+ mchange-commons-java-0.2.12

### 使用方法
1. 手动配置
```
//获取数据源
ComboPooledDataSource dataSource=new ComboPooledDataSource();
//手动配置连接参数
dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/bank?serverTimezone=UTC");
dataSource.setUser("username");
dataSource.setPassword("password");
//获取链接
conn=dataSource.getConnection();
```
2. 使用配置文件：配置文件名必须是c3p0-config.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<c3p0-config>
    <default-config>
        <property name="driverClass">com.mysql.cj.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/bank?serverTimezone=UTC</property>
        <property name="user">root</property>
        <property name="password">1443778731mhx</property>
        <property name="initialPoolSize">5</property>
        <property name="maxPoolSize">10</property>
        <property name="checkoutTimeout">3000</property>
    </default-config>
    <named-config name="otherc3p0"> </named-config>
</c3p0-config>
```

## DBUtil

### 作用
+ 简化CRUD的代码

### jar
+ commons-dbutils-1.4

### 使用方法
+ query   
   + account为自定义类，用于接收匿名类的输出
```
public void test(){
    ComboPooledDataSource dataSource=new ComboPooledDataSource();
    QueryRunner query=new QueryRunner(dataSource);
    try{
        Account account=query.query("select * from account where id=?", new ResultSetHandler<Account>(){
            @Override
            public Account handle(ResultSet rs) throws SQLException{
                Account account=new Account();
                while(rs.next()){
                    account.setName(rs.getString("name"));
                    account.setMoney(rs.getInt("money"));
                }
                return account;
            }
        },4);
        System.out.println(account.toString());
    }catch(Exception e){
        e.printStackTrace();
    }
}
```
+ update
```
public void test(){
    ComboPooledDataSource dataSource=new ComboPooledDataSource();
    //DBUtil只是减少CRUD代码，数据库的连接还是要由链接池提供
    QueryRunner queryRunner=new QueryRunner(dataSource);
    try {
        //执行更新语句
        //queryRunner.update("update account set money=money-100 where id=?",2);
        queryRunner.update("insert into account values(null,?,?)","Jet",10000);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```