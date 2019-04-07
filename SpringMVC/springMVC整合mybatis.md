# SpringMVC整合MyBatis
基本配置与Spring整合MyBatis一致，毕竟都是Spring系列框架
## 配置文件
### applicationContext.xml
+ 数据库配置
```
<!-- 引入数据库配置文件 -->
<context:property-placeholder location="WEB-INF/jdbc.properties"/>
<!-- 通过文件数据配置数据库 -->
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="${jdbc.driver}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
    <property name="maxActive" value="10"/>
</bean>
```
+ SqlSessionFactory配置
```
<!-- SqlSessionFactory配置 -->
<bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactory">
    <property name="dataSource" ref="dataSource"/>
    <property name="configLocation" value="WEB-INF/SqlMapConfig.xml"/>
    <property name="typeAliasesPackage" value="com.springMVC.demo1.mapper"/>
</bean>
```
+ 接口动态代理配置
```
<!-- 动态代理：包扫描 -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.springMVC.demo1.mapper"/>
</bean>
```
### SqlMapConfig.xml
+ 因为数据库及SqlSessionFactory都有spring代理，因此无序再次配置，只需要一个空文件就行

### jdbc.properties
+ ...   

### log4j.properties
+ ...

## pojo
+ Item：与数据库中的item表相对应
```
public class Item {
    private int id;
    private String name;
    private Date createTime;
    private double price;
    private String detail;
    ...
}
```
## mapper
item dao的接口动态代理
+ ItemMapper.java
```
public interface ItemMapper {
    List<Item> getItemList();
    Item getItemById(Integer id);
    void updateItem(Item item);
    ...
}
```
+ ItemMapper.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.springMVC.demo1.mapper.ItemMapper">
    ...
</mapper>
```
## service
service层，实现了一些item相关业务
+ ItemService
```
public interface ItemService {
    List<Item> getItemList();
    Item getItemById(Integer id);
    void updateItem(Item item);
    ...
}
```
+ ItemServiceImpl：@Service，service层注解
```
@Service
public class ItemServiceImpl implements ItemService {
    ...
}
```
## controller
+ ItemController
```
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("itemList")
    public ModelAndView itemList(ModelAndView modelAndView){
        List<Item> itemList=itemService.getItemList();
        modelAndView.addObject("itemList",itemList);
        return modelAndView;
    }
    ...
}
```
## jsp页面
+ ...