# SpringMVC

## 基本工作流程
1. 用户发出请求到前端控制器DispatcherServlet
2. DispatcherServlet调用处理器映射器HandlerMapping
3. HandlerMapping处理器映射器找到具体的处理器，
生成处理器对象等，返回给DispatcherServlet
4. DispatcherServlet再调用处理器适配器HandlerAdapter
5. HandlerAdapter调用对应处理器Controller
6. Controller执行并返回ModelAndView
7. HandlerAdapter将ModelAndView返回给DispatcherServlet
8. DispatcherServlet调用视图解析器ViewResolver
9. ViewResolver处理ModelAndView返回view
10. DispatcherServlet根据view进行视图渲染，并返回给用户

## 文件配置

### web.xml

#### 约束
```
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
```

#### DispatcherServlet
```
<!-- 前端控制器配置 -->
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <!-- 拦截所有请求 -->
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

#### 
```
<!-- 核心配置文件 -->
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/applicationContext.xml</param-value>
</context-param>
```

####
```
<!-- 使用监听器加载Spring配置文件 -->
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

### applicationContext.xml

#### ViewResolver
一般将jsp文件放在WEB-INF/jsp文件夹中，因此前缀后缀固定，可由ViewResolver代写，
实际访问只需写出文件名
```
<!-- 视图解析器的配置 -->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/jsp/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```

#### HandlerMapping&HandlerAdapter
方式一：
```
<!-- 配置处理器映射器 -->
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/>
<!-- 配置处理器适配器 -->
<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"/>
```
方式二：
```
<!-- 配置注解驱动，代替映射器与适配置，同时支持json响应 -->
<mvc:annotation-driven/>
```

#### component-scan
```
<!-- 配置controller包扫描 -->
<context:component-scan base-package="com.springMVC.demo1.controller"/>
<!--配置service包扫描-->
<context:component-scan base-package="com.springMVC.demo1.service"/>
```
## Controller

### @Controller
+ controller类上添加注解@Controller，以此告知Spring这是一个处理器

### @RequestMapping("xxx")
+ 方法上添加注解@RequestMapping，用于将方法与请求url建立映射
+ xxx代表相对于src文件夹的相对路径   

**例：**   

此方法将查询数据库中的item表，并将结果存储到ModelAndView中返回
```
@RequestMapping("itemList")
public ModelAndView itemList(ModelAndView modelAndView){
    List<Item> itemList=itemService.getItemList();
    modelAndView.addObject("itemList",itemList);
    return modelAndView;
}
```
### 存值并页面跳转
方式一：
```
ModelAndView modelAndView=new ModelAndView();
modelAndView.addObject("name",value);
modelAndView.setViewName("xxx");
return modelAndView
```
方式二：
```
Model model=new Model();
model.addAttribute("name",value);
return "xxx";
```