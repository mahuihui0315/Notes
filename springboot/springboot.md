# SpringBoot

## 简介
SpringBoot是由Pivotal团队提供的全新框架, 用于简化spring应用的搭建及开发过程, 
很容易就可创建基于spring的独立运行和产品级别的应用

### 特点
1. 建造独立的spring应用
2. 内嵌了Tomcat, Jetty, Undertow, 不需要部署war文件到服务器上
3. 提供了可选的 "starter" 依赖用于简化配置
4. 可以的情况下自动配置spring和第三方库
5. 提供了生产就绪特点, 例如指标, 健康检查和外化配置
6. 完全没有代码生成, 也不要配置xml

## 基本使用

### 使用IDEA构建项目
1. FIle->New ->Project
2. 选择Spring initializr, 单击next弹出配置界面
3. 配置先关信息之后, 单击next选择dependency(也可以之后手动添加), 再次单击next, 确定信息无误之后单击finish完成项目构建

### 项目结构
1. src/main/java: 程序开发以及主程序入口
   1. com/example/demo/Application.java: 系统生成的主程序
   2. com/example/demo/model: 实体与数据访问层
   3. com/example/demo/service: 业务代码层
   4. com/example/demo/controller: 页面访问控制层
2. src/main/resources: 配置文件位置
3. src/test/java: 测试程序

### 引入模块
1. pom.xml中添加web模块依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
pom.xml文件中会默认生成两个依赖
+ spring-boot-starter: 核心模块, 包括自动配置,日志等
+ spring-boot-test: 测试模块, 包括 JUnit、Hamcrest、Mockito

2. 编写controller
controller文件夹下新建类
```
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index(){
        return "Hello World";
    }
}
```
@RestController: 表示controller里的方法输出都为json格式

3. 运行程序

运行主程序, 在浏览器地址栏输入localhost:8080/hello,因为SpringBoot内嵌了tomcat,
因此不需要配置, 直接运行就可访问