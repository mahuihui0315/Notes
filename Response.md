# Response

## 简介
+ 服务器响应客户端的request返回的数据

## 响应数据中有中文乱码

### 字节流输出
```
//getOutputStream默认使用UTF-8编码
response.getOutputStream().writer(" ".getBytes());
//获取getBytes（）默认编码方式：UTF-8
String csn=Charset.defaultCharset().name();
```
### 字符流输出
```
//设置服务器返回数据的编码方式
reponse.setCharacterEncoding("UTF-8");
//设置浏览器读取数据的编码方式
response.setHeader("Content-Type","text/html;charset=UTF-8");
response.getWriter().writer("");
```
### 通用方式
`response.setContentType("text/html;charset=UTF-8");`

## 重定向
`response.sendRedirect("servler页面虚拟路径")`
+ 服务器返回重定向的页面地址
   + 返回状态码：302 Location
   + 客户端再次请求访问
   + 效率比较低
   + 可以访问其他项目资源路径
   + 后续请求无法使用上次request资源