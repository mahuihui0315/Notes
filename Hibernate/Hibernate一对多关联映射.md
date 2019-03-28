# Hibernate一对多关联映射
## 一对多关系
### 建表原则
+ 多的一方创建外键，指向一的一方
### 建立实体对象
+ 多的一方设置一的一方的对象
+ 一的一方设置多的一方的对象集合（需要初始化）
### 配置映射文件
+ 多的一方
   + 一般配置同单表一样
   + 新建一条many-to-one标签   
   > name:一的一方对象名   
	class:一的一方类的全路径   
	column:多的一方表的外键名   
   `<many-to-one name="" class="" column=""></meny-to-one>` 
+ 一的一方
   + 一般配置同单表一样
   + 新建一条set标签
   > name:多的一方的对象集合名   
   column:多的一方的外键名   
   class:多的一方类的全路径   
   `<set name=""> <key column=""/> <one-to-many class=""/> </set>`
   
### 一对多相关操作
#### 级联
> 操作一个对象会同时操作另一个级联的对象
#### 方向性
> 操作多的一方是否操作一的一方   
操作一的一方是否操作多的一方
#### 级联保存或更新
+ 保存一级联多
> set标签添加属性   
cascade="save-update"
+ 保存多级联一
> many-to-one标签添加属性   
cascade="save-update"
#### 级联删除
+ 删除一级联多
> 添加属性   
cascade="delete"
+ 删除多级联一（一般不用）   
cascade="delete"
### 多余SQL语句
双向维护会产生多余的SQL语句   

解决方法
+ 单向维护
+ 使一方放弃维护外键
> 一的一方放弃维护，set标签添加属性   
inverse="true"
## 多对多关系

### 建表原则
+ 创建一个中间表，至少有两个字段，分别作为外键指向两个表的主键
### 建立实体对象
+ 每个类中都设置另一个类的对象集合（初始化）
### 配置映射文件
+ 双方实体除了常规配置外，都需另加set标签
+ 被动一方需要放弃外键
```
<!--
    多对多关系映射
    name：对方的集合名
    table：多对多关系使用的中间表名
-->
<set name="employees" table="mtm_employee_role" cascade="save-update,delete" inverse="true">
    <!--
        column：当前类对应的在中间表的外键名
     -->
    <key column="role_id"/>
    <!--
        class：对方类的全路径
        column：对方类对应的在中间表的外键名
     -->
    <many-to-many class="com.mhh.hibernate.many2many.Employee" column="employee_id"/>
</set>
```
### 多对多相关操作
#### 级联保存或更新
+ 基本与一对多相同
#### 级联删除
+ 基本与一对多相同
+ 基本不使用
#### 其他操作
