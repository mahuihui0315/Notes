# Maven

## 常用命令
+ mvn -v：查看版本
+ mvn compile：编译
+ mvn test：测试
+ mvn package：打包
+ mvn clean：删除target中的文件
+ mvn install：安装jar到本地仓库

## ArcheType
使用archerType自动创建项目目录骨架
> mvn archetype：generate

## 修改仓库为国内镜像
+ conf/settings.xml
```
<mirrors>
  <mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>        
  </mirror>
</mirrors>
```

## 修改本地仓库默认路径
+ conf/settings.xml
```
<localRepository>D:\maven\maven_local_repository</localRepository>
```
