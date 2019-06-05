# 数据库视图

## 视图的定义
视图是一种虚拟表, 物理上不存在, 只是将数据库中的特定数据的集合

## 视图的优点
1. 安全性: 通过视图可以将指定数据呈现给用户而隐藏比较重要的数据, 同时也屏蔽了真实的表结构
2. 简化操作: 如果部分数据的查询需要比较复杂的操作, 例如使用聚合函数以及其他的一些判断条件,
就可以通过创建视图来免去这些复杂的操作, 直接访问视图即可

## 视图的缺点
1. 性能差: 访问视图本质上还是对于基本表的查询, 如果视图是由一个复杂的多表查询所定义,
则即使是一个简单的查询, 也需要花费一定的时间
2. 限制修改: 当用户视图修改视图的数据时, 数据库会将其转化为对基本表的数据的修改, 对于比较复杂的视图,
可能无法进行

## 基本使用

### 1.创建视图
`create (or replace) view view_name as select * from table_name`

上述代码创建了一个名为view_name的视图, 包含了名为table_name的表的所有数据

若查询条件不想视图被用户修改, 可以在语句最后添加 "with check option"

### 2.查询视图

`select * from view_name`

上述代码查询view_name视图下的所有数据, 操作与普通表一致

### 3.修改视图
`update view_name set column1="value1" where column2="value2"`

上述代码将条件column2="value2"下的column1的值修改为value1, 操作与普通表一致, 不过视图一般只用查询

### 4.删除视图
`drop view view_name`

删除名为view_name的视图