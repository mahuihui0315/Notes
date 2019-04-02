# Spring入门
## IOC(Inversion of Control)
**简介：** 将对象的创建交给spring

### 为什么使用ICO
+ 传统方式
> Dao dao=new Dao();   
可扩展性极差
+ 面向接口
> DAO dao=new DaoImplement();   
使用多态，增加了可扩展性，但是带来了耦合（接口和实现类联系过紧）问题
+ 工厂模式
> ```
> class BeanFactory{
>     public static Dao1 getDao1Implement(){
>         return new Dao1Implement();
>     }
>     public static Dao2 getDao2Implement(){
>         return new Dao2Implement():
>     }
> }
> ```
> 使用中间类BeanFactory建立对象，解决了类与接口的耦合，但接口与BeanFactory存在耦合
+ 而IOC则解决了这些问题

### IOC底层实现

#### 工厂+反射+配置文件
+ 配置文件   
`<bean id="Dao" class="xxx.DaoImplement"></bean>`
+ 工厂+反射
> ```
> class BeanFactory{
>     public static Object getBean(String id){
>         //解析xml，通过id获取class
>         ...
>         //
>         Class clazz=Class.forName();
>         return clazz.newInstance();
>     }
> }
> ```
## DI(Dependency Injection)
**简介：** 依赖注入，spring管理类的时候将类的依赖的属性注入（设置）进来
### 依赖
例：
```
class A{
  private String name;
  ...
  public void method(Object B){
  ...
  }
}
```
则类A依赖属性name和对象B
### 继承
is a（创建新类时，除非使用多态，否则一般不要用）

### 聚合
has a（创建新类时，更推荐使用）

## 工厂类

### BeanFactory
+ 旧版本工厂类
+ 遇到getBean时，才会生成类的实例

### ApplicationContext
+ 新版本工厂类
+ 加载配置文件时就实例化类
+ 两个实现类
   + ClassPathXmlApplicationContext
   > 加载类路径的配置文件
   + FileSystemXmlApplicationContext
   > 加载系统路径的配置文件
   
## 配置

### Bean的配置

#### bean标签
+ id
> 使用唯一约束，不能重复，不能使用特殊字符
+ name
> 非唯一约束，理论上可以重复，但实际不允许，可以有特殊字符

#### bean的生命周期
**配置方法：** bean标签内添加属性：
+ init-method="setup"
> 在对应的类中实现setup方法，该类被初始化时会被调用
+ destroy-method="destroy"
> 在对应的类中实现destroy方法，该类被销毁时会被调用

#### bean的作用范围配置（重要）
**配置方法：** bean标签内添加属性：
+ scope="singleton"
> 单例模式，默认
+ scope="prototype"
> 多例模式

## 属性注入
### 构造方法
```
<bean id="daoImpl" class="com.spring.Dao.DaoImpl" init-method="setup" destroy-method="destroy">
    <constructor-arg name="name" value="spike"/>
</bean>
```

### set方法
```
<bean id="daoImpl" class="com.spring.Dao.DaoImpl" init-method="setup" destroy-method="destroy">
    <property name="name" value="spike"/>
</bean>
```

### p名称空间
+ 引入p名称空间
> beans标签添加 xmlns:p="http://www.springframework.org/schema/p"

+ 普通属性
> bean标签添加属性 p:name="value"

+ 对象属性
> bean标签添加属性 p:name-ref="object"

### SpEL(Spring Expression Language)
+ 基本格式
> `<property nmae="" value="#{SpEL}">`
+ SpEL语法
   + `#{int}`
   + `#{float}`
   + `#{'string'}`
   + `#{object}`
   + `#{method(object)}`

## 集合类型的属性注入
bean标签内新建property标签
+ 数组集合
> ```
> <property name="arrayname">
>   <list>
>     <value>value</value>
>     <value>value</value>
>     ...
>  </list>
> </property>
> ```
+ list集合
> ```
> <property name="listname">
>   <list>
>     <value>value1</value>
>     <value>value2</value>
>     <ref>Object</ref>
>     ...
>  </list>
> </property>
> ```
+ set集合
> ```
> <property name="setname">
>   <set>
>     <value>value1</value>
>     <value>value2</value>
>     <ref>Object</ref>
>     ...
>  </set>
> </property>
> ```
+ map集合
> ```
> <property name="mapname">
>   <map>
>     <entry key="" value=""/>
>     <entry key="" value=""/>
>     ...
>  </map>
> </property>
> ```