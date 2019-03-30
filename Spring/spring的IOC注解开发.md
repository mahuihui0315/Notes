# Spring的IOC注解开发
## 引入配置文件
### 添加约束
```
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd">
```
### 配置组件扫描
+ base-package中填入需要使用IOC注解开发的包路径
> `<context:component-scan base-package="xxx"/>`

### 属性注入
+ 有set方法
> 在set方法添加注解@Value("value")
+ 没有set方法
> 在属性上添加注解@Value("value")

## IOC注解详解
### @Component：组件
**简介：** 修饰一个类，将其交给spring管理
#### 三个衍生注解
+ @Controller：web层
+ @Service：service层
+ @Repository：dao层

### 属性注入
#### 普通属性
+ @Value("value")
#### 对象属性
+ @Autowired：按照属性类型注入
+ @Autowired   
@Qualifier(value="")   
两个注解一起使用，强制按照名称（id）方式注入
+ @Resource   
按照名称注入

### 生命周期
+ @PostConstruct：类初始时zhixing
+ @PreDestory：applicationContext关闭时执行

### 作用范围
+ @Scope
   + singleton：单例模式（默认）
   + prototype：多例模式

## IOC的xml和注解开发比较
### xml
+ 可以适用于任何场景
+ 结构清晰，方便维护

### 注解
+ 类不是自己实现时无法使用
+ 开发方便

## IOC的xml和注解整合开发
xml管理bean，注解完成属性注入

