# SpringBoot常见问题

## pom.xml中引入配置不生效
例如: 引入了web模块的依赖, 在controller中使用@RestController注解报错

### 解决方法
右键pom.xml文件maven->reimport, 重新导入pom.xml文件的内容