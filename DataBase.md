# DataBase

## SQL的分类
SQL：Structured Query Language

### DDL:数据库定义语言
+ 数据库/表结构：create，drop，alert

### DML:数据库操作语言
+ 操作表数据：insert，update，delete

### DCL：数据库控制语言
+ 设置用户访问权限

### DQL:数据库查询语言
+ select，from，where

## 数据库操作

+ 创建数据库
> create database 数据库的名字;
+ 删除数据库
> drop database 数据库的名字;
+ 修改数据库
> alter database character set 字符集;
+ 查询数据库
> show databases；查看所有数据库;
+ 查看数据库的创建
> show create database 数据库的名字;
+ 查看当前正在使用的数据库
> select database;
+ 选中数据库
> use 数据库的名字;

## 表结构操作
### 创建表
```
create table 表名（
列名 列的类型（长度） 约束，
列名2 列的类型（长度） 约束
）；
```
#### 列的约束
+ 主键约束：primary key
+ 唯一约束：unique
+ 非空约束：not null
+ 自增约束：auto_increment

### 删除表
+ drop table 表名

### 修改表
+ 添加列
> alter table 表名 add 列名 列的类型 列的约束;
+ 修改列
> alter table 表名 modify 列名 列的类型 列的约束;
+ 修改列名
> alter table 表名 change 旧列名 新列名 列的类型 列的约束;
+ 删除列
> alter table 表名 drop 列名;
+ 修改表的字符集
> alter table 表名 character set 字符集;
+ 修改表名
> rename table 旧表名 to 新表名;

### 查看表
+ 查看当前数据库中所有的表名
> show tables；
+ 查看表的定义结构/创建语句
> show create table 表名;
+ 查看表的结构
> desc 表名;

## CRUD操作

### 插入操作
+ 全列值插入
> insert into 表名（列名1，列名2，列名3...）  values（值1，值2，值3...）
+ 全列值插入简写
> insert into 表名 values（值1，值2，值3...）
+ 部分列值插入(不能简写)
> insert into 表名（列名1，列名2） values（值1，值2）    
+ 批量插入
> insert into 表名 values（值1，值2，值3...）,（值1，值2，值3...）,（值1，值2，值3...）...

### 删除操作
+ 条件删除
> delete from 表名 where 条件
+ 全部删除
> delete from 表名
+ 删除表并重建
> truncate

### 修改操作
+ update 表名 set 列名1=值1，列名2=值2，... where 条件

### 查找操作
+ 条件查询
> select distinct（过滤重复数据，可省略） 列名1，列名2，列名3，... from 表名 where 条件
+ 多表查询
> select 别名.列名1，别名.列名2，... from 表名 as 别名（as可以省略）
+ 运算查询：（对运算结果进行查询，并以单独一列进行显示）   

> `select *，列名*1.5 as 别名 from 表名`

+ 关系运算符
> `>,>=,<,<=,=,!=`
+ 模糊查询
> “_”一个字符；“%”多个字符，`select * from 表名 where 列名 like ‘%字符%’`
+ 范围查询
> `select * from 表名 where 列名 in （值1，值2，...）`
#### 排序查询
+ 升序查询：asc（ascend）
> `select * from 表名 order by 列名 asc（默认排序，可不写）`
+ 降序查询：desc（descend）
> `select * from 表名 order by 列名 desc`
+ 查询并排序
> `select * from 表名 where 列名 like ‘%字符%’ order by 列名 asc/desc`
+ 聚合函数(不能用在where之后)
> sun（），avg（），count（），max（），min（）
> select sum（列名） from 表名
+ 分组查询：having可以接聚合函数
> `select 列名，count（*） from 表名 group by 列名 having 条件`
+ 编写顺序
> S..F..W..G..H..O，select..from..where..group by ..having..order by
+ 执行顺序
> from..where..group by..having..select..order by

## 多表操作
### 添加外键约束
> alter table 表名1 add foreign key（列名1） references 表名2（列名2）
### 建表原则
+ 一对多
> 在多的一方添加一个外键指向一的主键
+ 多对多
> 新建一个中间表，至少包含两个外键指向原来的两张表
+ 一对一
> 将两张表的主键建立起链接
### 内链接查询
+ 隐式内链接：先查询再做条件过滤
> `select * from 表名1 别名（可省略），表名2 别名2 where 别名1.列名1=别名2.列名2`
+ 显示内链接：带着条件查询
> `select * from 表名1别名1 inner join 表名2 别名2 on 别名1.列名1=别名2.列名2`
### 外连接查询
+ 左外连接：将左表中的数据查询出来，右表没有对应的就用null标识
> `select * from 表名1 别名2 left outer join 表名2 别名2 on 别名1.列名1=别名2.列名2`
+ 右外链接：将右表中的数据查询出来，如果左边没有对应的就用null标识
> `select * from 表名1 别名2 right outer join 表名2 别名2 on 别名1.列名1=别名2.列名2`