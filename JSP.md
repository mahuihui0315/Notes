# JSP

## 简介
+ Java Server Pages，本质上是一个简化的Servlet设计
+ 它是在传统的网页HTML中插入Java程序段和JSP标记，而形成JSP文件，后缀名为(*.jsp)
+ 网页上的动态内容，例如查询数据服务，需要jsp实现动态内容

## 基本用法
+ 在JSP页面中添加servlet代码

`<% servlet代码 %>`

+ 代码可以分离，中间插入html代码
```
< ht <% Servlet %> ml >
<% ser %>< html ><% vlet %> 
```

## 3个指令

### page
`<%@ page %>`
+ 属性
   + language="编程语言/java"
   + contentType=“text/html;charset=UTF-8”
   + pageEncoding="UTF-8"jsp编码方式
   + extends指定jsp翻译成java后的父类
   + session="true/false"指定jsp页面是否可直接使用session
   + errorPage="跳转页面地址"出错之后跳转
   + isErrorPage="true"声明目标页面
### include
`<%@ include %>`
+ 属性
   + file="被包含页面路径"
### taglib
`<%@ taglib %>`
+ 属性
   + prefix="别名"
   + uri="标签库"
## 动作标签

+ 只包含指定页面的运行结果

`<jsp:include page=""></jsp:include>`
+ 指定前往的页面，等同于请求转发

`<jsp:forward page=""></jsp:forward>`
+ 跳转页面时带过去的参数
```
<jsp:forward page="">
    <jsp:param nam="" value=""/>
</jsp:forward>
```

## 内置对象
**注：** 可以直接使用无需创建
### 4个作用域
**注：** 可以存值，取值范围有限定
```
setAttribute("name","value")
getAttribute("name")
```
+ pageContext
> 作用域仅限当前页面
+ request
> 作用域仅限一次请求
+ session
> 作用域仅限一次会话
+ application
> 整个工程都可以访问
### page
+ 这个页面翻译成java类的实例对象
### response
+ HttpServletResponse
### config
+ ServletConfig
### out
+ JspWriter
+ out的输出内容会放到response缓冲区
### exception
+ 只在isErroePage="true"中存在
+ Throwable

## EL表达式
### 作用
+ 简化JSP中的java代码
### 格式
+ ${ java代码 }
### 取值
+ 传统格式

`<% page.getAttribute("name"); %>`
+ EL表达式

`${ pageScope.name }`不指定作用域会从小到大逐渐查询
+ 取数组中的值

`${ array[0] },${ array[1] }...`
+ 取列表中的值

`${ list[0] },${ list[1] }...`
+ 取Map中的值

`${ map.name }`如果name中有"."则用${ map[name] }
### 11个内置对象
#### JSP
+ pageContext
#### map类
+ 作用域
   + pageScope
   + requestScope
   + sessionScope
   + applicationScope
+ 请求头
   + header
   + headerValues
+ 参数
   + param
   + paramValues
+ 全局初始化参数
   + initParam
   + cookie
   
## JSTL
+ JSP Standard Tag Library
+ 用于简写JSP，一般与EL一起使用
### 导入JSTL包文件
1. 在WEB-INF文件加下新建lib文件夹
2. 使用<%@ taglib prefix="c" uri="" %>在文件中引入
### 常用功能
+ 存值
   + scope：值要被保存的域
```
<c:set></c:set>
<c:set name="" value="" scope=""></c:set>
```
+ if表达式：EL表达式成立执行if语句内的内容
```
<c:if test="EL表达式"></c:if>
```
+ 遍历：forEach.遍历元素并用var接收
```
<c:forEach begin="startIndex" end="endIndex" var="result" step="num"></c:forEach>
<c:forEach var="result" items="list"></c:forEach>
```

