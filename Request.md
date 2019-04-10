# Request
## 简介
+ Request对象的作用是与客户端交互，收集客户端的Form、Cookies等数据
+ Request对象是从客户端向服务器发出请求，包括用户提交的信息以及客户端的一些信息。
客户端可通过HTML/JSON表单或在网页地址后面提供参数的方法提交数据，然后服务器通过request
对象的相关方法来获取这些数据
## 获取数据

### 获取请求体中的数据
1. 获取headername的枚举对象

`Enumeration<String> headerName=req.getHeadernames()`

2. 遍历headerName获取数据
```
while(headerName.hasMoreElements()){
    String name=headerName.nextElement();
    String value=req.getHeader(name);
}
```
### 获取客户端发送的数据
1. 一对一

`String value=req.getParameter("name");`

2. 一对多

`Map<String,String[]> map=req.getParameterMap();`

## 中文乱码问题
### Get方式
+ 乱码原因
   1. 客户端浏览器发送数据时在地址栏中使用默认编码方式已经对数据进行编码
   2. Tomcat收到数据后使用ISO-8859-1进行解码，若浏览器默认编码方式不是这个就会出现乱码
+ 解决方式
   1. 将客户端数据用ISO-8859-1将数据返回到字节码形式，再用UTF-8编码   
   username=new String(username.getBytes("ISO-8859-1"),"UTF-8")
   2. 直接在Tomcat中修改配置文件，以UTF-8默认编码   
   conf/server.xml/添加connector标签，URIEncoding="UTF-8"
### Post方式
+ 添加一段代码：重新设置request的编码方式  

`request.setCharacterEncoding("UTF-8")`

## 请求转发
`request.getRequestDispatcher("目的servlet虚拟路径").forward(request,response);`
+ 服务器代替客户端进行页面跳转
   + 返回状态码：200
   + 客户端只进行一次请求
   + 效率更高
   + 只能访问当前项目的资源路径
   + 可以使用上一次request对象
