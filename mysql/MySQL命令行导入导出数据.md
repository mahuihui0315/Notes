# MySQL命令行导入导出数据

## 导出数据库
+ mysqldump -u 用户名 -p 数据库名 > 导出的文件名(.sql结尾)

## 导出表
+ mysqldump -u 用户名 -p 数据库名 表名> 导出的文件名(.sql结尾)

## 导入数据
+ 进入控制台
> mysql -u root -p 
+ 使用数据库
> use database
+ 使用source命令
> source D:/XXX(文件路径)
