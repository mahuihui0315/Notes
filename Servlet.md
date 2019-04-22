# Servlet

## 简介
+ 一个java程序，用于在服务器上接收和响应客户端HTTP请求
+ Tomcat相当于Servlet的容器

## 基本使用步骤
### 新建web工程
+ ...
### 新建类
+ 继承HttpServlet类
+ 复写doGet和doPost方法
### 配置servlet
+ WEB-INF下的web.xml文件中新增
```
<servlet>
    <servlet-name>servlet名字</servlet-class>
    <servlet-class>servlet路径</servlet-class>
</servlet>
<servelt-mapping>
    <servlet-name>servlet名字</servlet-name>
    <url-pattern>/虚拟路径</url-pattern>
</servlet-mapping>
```
### 注解代替servlet配置
+ 在Servlet程序前添加@WebServlet("/虚拟路径")
### 更改访问路径上的项目名
1. Edit Configurations
2. Deployment
3. Aplication context：/项目名
### 路径匹配方式
1. 全路径匹配
> localhost:8080/项目名/路径  

2. 前端路径匹配   
> localhost:8080/项目名/*

3. 扩展名匹配
> localhost:8080/*.扩展名

## servlet生命周期
**定义：** 从创建到销毁对象一定会执行的方法
1. 创建Servlet对象
   + 创建的同时会执行init（）方法
   + 只会执行一次
2. 收到客户端访问请求
   + 执行service（）方法
   + 每次收到请求都会访问一次
3. 关闭Servlet
   + 执行destro（）方法
      1. 从服务器中移除正在运行的Servlet
      2. 正常关闭服务器，tomcat中执行shutdown.bat
	  
注：非正常关闭服务器不会执行

## 提前初始化
+ 意义
> 默认情况只有初次访问servlet才会执行初始化，如果初始化内容过多会耗时过长
+ 方法
   1. 配置web.xml时添加标签：`<load-on startup>数字</load-on-startup>`
   2. 标签中的数字决定初始化的时间，越小越早，一般为2即可
   
## ServletConfig
+ 用于配置servlet初始化数据
### 应用场景
1. 开发中需要使用别人的servlet类
2. 该servlet类中有些参数需要变动
3. 在web.xml中添加init-param来改变参数
+ 初始化数据
```
<servlet>
    <init-param>
	    <param-name>name<param-name>
	    <param-value>value<param-value>
    </init-param>
    ...
</servlet>
```
### 使用方法

1. 获取ServletConfig对象

`ServletConfig config=getServletConfig();`

2. 获取单个配置文件信息
```
String servletName=config.getServletName();
String servletParameter=config.getInitParameter();
```

3. 遍历配置文件信息
```
Enumeration<String> names=config.getInitParameterNames();
while(names.hasMoreElements()){
    String key=(String)names.nextElement();
    String value=config.getInitParameter(key);
    ...
}
```
## ServletContext
### 读取全局参数
+ 全局参数，一个web应用只有一个
+ 全局参数位置：web.xml文件中
+ 全局参数格式：
```
<context-param>
    <param-name><param-name>
    <param-value><param-value>
<context-param>
```
#### 使用方法
```
ServletContext context=getServletContext();
String 变量名=context.getInitParameter("参数");
```

### 获取web资源
#### 方式1
1. 获取对应资源的绝对路径
```
//获取context对象
ServletContext context=getServletContext();
//对应项目的绝对路径
String path=context.getRealPath("");
```
2. 通过绝对路径寻找资源
```
Properties properties=new Properties();
InputStream is=new FileInputStream(path);
properties.load(is);
properties..getProperty("name");
```
#### 方式2
```
Properties properties=new Properties();
InputStream is=new context.getResourceAsStream("相对路径")
properties.load(is);
properties..getProperty("name");
```
#### 方式3
```
Properties properties=new Properties();
//ClassLoader路径：工程下的Classes文件夹
InputStream is=this.getClass().getClassLoader().getResourceAsStream("相对路径");
properties.load(is);
```
### 创建及销毁时间
服务器启动时，为每一个web程序创建一个ServletContext对象

## 下载资源

### 直接提供超链接
```
//使用Tomcat默认servlet
<a href="文件路径">文件名</a>
```
### 手动编码下载
+ jsp
```
<a href="下载页面路径?filename=文件名">文件名</a>
```
+ servlet
```
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //1.获取需要下载的而文件名
    String fileName=req.getParameter("filename");
    //2.通过文件名获取文件路径
    String path=getServletContext().getRealPath("download/"+fileName);
    //3.对Chrome浏览器进行重新编码，防止文件保存时出现名字不显示的错误
    String browser=req.getHeader("User-Agent");
    fileName= URLEncoder.encode(fileName,"UTF-8");
    //4.设置浏览器以下载形式打开文件
    resp.setHeader("Content-Disposition","attachment;filename="+fileName);
    //5.将文件转换问字节流输出
    InputStream is=new FileInputStream(path);
    OutputStream os=resp.getOutputStream();
    int len=0;
    byte[] buffer=new byte[1024];
    while((len=is.read(buffer))!=-1){
        os.write(buffer,0,len);
    }
    is.close();
    os.close();
}
```
+ 下载文件名为中文
```
//1.将filename用tomcat默认编码方式编码，再使用浏览器默认方式解码
filename=new String(filename.getBytes("Tomcat编码"),"浏览器编码")
//2.IE和Chrome浏览器需要使用UELEncoding
filename=URLEncoder.encoder(filename,"UTF-8");
//获取浏览器类型
String browser=request.getHeader("User-Agent");
``