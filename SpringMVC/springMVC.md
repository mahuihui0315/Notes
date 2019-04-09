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

## RequestMapping参数绑定

### 默认支持的参数类型
+ HttpServletRequest
+ HttpServletResponse
+ HttpSession
```
@RequestMapping("itemEdit")
public ModelAndView itemEdit(HttpServletRequest request,HttpServletResponse response,HttpSession session){
    ModelAndView mav = new ModelAndView();
    ...
}
```
### 简单参数绑定
+ @RequestParam(value="",required=true,defaultValue=)Object obj
   + value：实参名
   + required：参数是否必须
   + defaultValue：默认值
> 实参名与形参名不一致时使用   
```
@RequestMapping("itemEdit")
public ModelAndView itemEdit(@RequestParam(value="id",required=true,defaultValue=1)Integer ids){
    ModelAndView mav = new ModelAndView();
    ...
}
```

### Model/ModelMap
+ Model
```
@RequestMapping("itemEdit")
public String itemEdit(@RequestParam("id")Integer ids,Model m,ModelMap model){
    Item item = itemServices.getItemById(ids);
    model.addAttribute("item", item);
    return "itemEdit";
}
```

### pojo对象
**注：** 表单提交的属性名必须与pojo一致
```
@RequestMapping("updateItem")
public String updateItem(Item item,Model model){
    itemServices.update(item);
    model.addAttribute("item", item);
    return "itemEdit";
}
```

### 数组类型的参数绑定
+ 提交多个属性名一致的变量会自动包装成数组
```
<c:forEach items="${itemList }" var="item">
    <tr>
        <td><input type="checkbox" name="ids" value="${item.id}"></td>
        ...
    </tr>
</c:forEach>
```

### List类型的参数绑定
+ jsp修改
   + forEach新加varStatus属性，用于获取列表的index
   + name由item.xxx，改为`items[${status,index}].xxx`
   + 点击提交时，所有item会封装到items集合中
```
<c:forEach items="${itemList }" var="item" varStatus="status">
    <tr>
        <input type="hidden" name="items[${status.index}].id" value="${item.id}">
        <input type="text" name="items[${status.index}].name" value="${item.name}">
    </td>
    <td><input type="text" name="items[${status.index}].price" value="${item.price}"></td>
    ...
</c:forEach>
```
## @RequestMapping注解使用

### 映射路径数组
一个方法可以与多个url建立映射关系
```
@RequestMapping(value={"itemList","itemList2",...})

```
### url分级管理
将注解添加到类的头部，则相当于将方法映射url放在类映射url的下一级目录   
```
@RequestMapping(value="item")
public class ItemController{
    @RequestMapping(value="getItem")
    public String getItem(...){
        ...
    }
    ...
}
```
### 限定方法的请求方式
只有menthod属性中标注的请求方式才会被接收
```
@RequestMapping(value = "queryItem",method = {RequestMethod.POST,RequestMethod.GET})
```

## 全局转换器
+ 用于转换对象的不同格式
### 时间格式转换器
+ 新建DateConvert实现Convert
+ 本例为String转换为Date格式，输入参数为String和Date
```
public class DateConvert implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```
+ 核心配置文件中声明
```
<!-- 使用自定义转换器 -->
<mvc:annotation-driven conversion-service="conversionService2"/>
<!-- 定义转换器：spring无法转换时间格式，需要自定义 -->
<bean id="conversionService2" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
    <property name="converters">
        <list>
            <bean class="com.springMVC.demo1.utils.DateConvert"/>
        </list>
    </property>
</bean>
```

## SpringMVC的异常处理

### 全局异常处理器
+ 新建一个异常处理类实现HandlerExceptionResolver
```
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        //记录日志
        e.printStackTrace();
        //异常消息
        String result="系统异常";
        //异常判断，是否为自定义异常
        if (e instanceof  MyException)
            result=e.getMessage();
        //将异常信息响应给用户
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("message",result);
        modelAndView.setViewName("exception");
        return modelAndView;
    }
}
```
+ 在核心配置文件中声明
```
<!-- 配置全局异常处理器 -->
<bean class="com.springMVC.demo1.exception.ExceptionResolver"/>
```

### 自定义异常类
+ 新建类继承Exception
```
public class MyException extends Exception {
    private String message;
    public MyException(){
        super();
    }
    public MyException(String message){
        super();
        this.message=message;
    }
    get/set...
}
```

## 图片上传

### 导入jar
+ commons-fileuploa
+ commons-io

### 配置多媒体解析器
+ 核心配置文件中添加bean标签
```
<!-- 配置多媒体解析器 -->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <!-- 上传文件最大值 -->
    <property name="maxUploadSize" value="8388608"/>
</bean>
```

### 图片上传页面
+ form标签中添加属性

`enctype="multipart/form-data"`
+ input标签类型为file   

`<input type="file"  name="pictureFile"/>`

### 对应处理器
+ 获取图片并改名之后存储到指定目录下
```
@RequestMapping(value="updateItem",method = RequestMethod.POST)
public String updateItem(Model model, Item item, MultipartFile pictureFile) throws IOException {
    //获取随机序列用于图片的新名字
    String name= UUID.randomUUID().toString();
    //获取图片的旧名字，以获取图片后缀
    String oldName=pictureFile.getOriginalFilename();
    String suf=oldName.substring(oldName.lastIndexOf("."));
    //存储图片到指定路径
    File file=new File("C:\\Users\\MHH\\Pictures\\ACG\\"+name+suf);
    pictureFile.transferTo(file);
    //跟新item的图片id
    item.setPictureId(name+suf);
    itemService.updateItem(item);
    //包装进Model中
    model.addAttribute("item",item);
    model.addAttribute("msg","修改成功");
    return "itemEdit";
}
```

## 使用Json

### 引入jar
+ jackson-annotations
+ jackson-datebind
+ jackson-core
    + 该包需要使用2.7.0及以上版本

### 方法上添加注解
+ @ResponseBody
```
@RequestMapping(value = "getItemInJson")
@ResponseBody
public Item getItemInJson(){
    Item item=itemService.getItemById(1);
    return item;
}
```

## 拦截器

### 新建类实现HandlerInterceptor
实现方法：   

+ boolean preHandle：controller方法执行前被执行；登陆拦截，权限验证
   + return true：放行
   + return false：拦截
+ void postHandler：controller方法执行后，返回ModelAndView之前；设置或者清理页面公用参数
+ void afterHandler：最后被执行；处理异常，记录日志

### 核心配置文件中配置拦截器
```
<!-- 拦截器配置 -->
<mvc:interceptors>
    <mvc:interceptor>
        <!--
            path
            /**：拦截所有请求，包括二级以上目录
            /*：拦截所有请求，不包括二级以上目录
        -->
        <mvc:mapping path="/**"/>
        <!-- 不拦截的请求 -->
        <mvc:exclude-mapping path="/user/**"/>
        <!-- 拦截器路径 -->
        <bean class="com.springMVC.demo1.interceptor.MyInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```