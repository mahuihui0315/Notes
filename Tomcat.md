# Tomcat简单使用
## 简介
Tomcat 服务器是一个免费的开放源代码的Web 应用服务器，属于轻量级应用服务器，
在中小型系统和并发访问用户不是很多的场合下被普遍使用，是开发和调试JSP
程序的首选
## 发布项目

### 直接拷贝项目到文件夹
#### 方式一：
1. 将项目放入文件夹webapps/roots下
2. 重新运行Tomcat
3. 访问文件   
   3.1 浏览器地址栏输入：localhost：8080/文件名（内部访问）   
   3.2 浏览器地址栏输入：http://IP Address:8080/文件路径（外部访问）   
#### 方式二：   
1. 在webapps下新建文件夹，放入项目
2. 重新运行Tomcat
3. 访问文件   
   3.1 浏览器地址栏输入：localhost：8080/文件名（内部访问）   
   3.2 浏览器地址栏输入：http://IP Address:8080/文件路径（外部访问）
		
### 创建虚拟路径1
1. 打开conf文件夹下的server文件
2. 在host标签下新增标签`<Context docBase="项目路径" path="虚拟路径"></Context>`   
   例如:   
      文件地址：D:\xml\demo.xml   
	  项目路径：D:\xml   
	  虚拟路径：/a（必须以/开头）   
	  访问方式：http://IP Address:8080/虚拟路径/文件名（外部访问）
3. 访问文件   
   3.1 浏览器地址栏输入：localhost：8080/虚拟路径/文件名（内部访问）   
   3.2 浏览器地址栏输入：http://IP Address:8080/虚拟路径/文件名（外部访问）
		
### 创建虚拟路径2
1. 在文件夹conf/catalina/localhost下新建xml文件，例如config.xml
2. 文件内写入配置信息
```
<?version="1.0" encoding="utf-8"?>
<Context docBase="项目路径"></Context>
```
3. 访问文件:浏览器地址栏输入：http://IP Address:8080/config/文件名（外部访问）
## IDEA配置Tomcat
1. 添加系统变量   
   + 变量名：CATALINA_BASE   
   + 变量值：Tomcat安装目录   
   + 变量名：CATALINA_HOME   
   + 变量值：Tomcat安装目录
2. 修改ClassPath和Path   
   + ClassPath添加：%CATALINA_HOME%\lib\servlet-api.jar;   
   + Path添加：%CATALINA_HOME%\bin;%CATALINA_HOME%\lib
3. IDEA配置：百度/Google