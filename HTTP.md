# HTTP

## 简介
+ Hyper Text Transfer Protocol
+ 针对客户端与服务器执行HTTP请求时执行的协议

## 请求数据内容
### 请求行
+ POST请求方式  
> 请求地址 HTTP/1.1(版本)
+ GET请求方式：
> 在地址栏拼接数据，有安全隐患   
> 数据大小1kb左右
+ POST请求方式：
> 以流的方式发送数据，更安全   
> 数据大小无限制
### 请求头
+ Accept：请求数据类型
+ Referer：请求的实际全部路径
+ Accept-Language：支持语言
+ User-Agent：用户代理，用来向服务器告知客户端信息（例如：手机版，PC版）
+ Content-Type：提交的数据类型
+ Accept-Encoding：支持编码方式
+ Host：主机地址
+ Content-Length：内容长度
+ Connection：keep-Alive，保持链接
+ Cache-Control：对缓存的操作
### 请求体
+ 客户端发送的真正数据

## 响应数据内容
### 响应行
+ HTTP/1.1（协议版本） 200（状态码） OK（对应状态码）
> 状态码 200：成功   
> 状态码 403：Forbidden   
> 状态码 404：Not Found   
> 状态码 500：服务器异常   
### 响应头
> Server：服务器类型   
> Content-Type：服务器返回的数据类型   
> Content-length：服务器返回的数据长度   
> Date：时间
### 响应体

## Web资源
### 静态资源
+ HTML
+ CSS
+ JS
### 动态资源
+ Servlet/JSP