# Hibernate查询方式
## OID查询(了解)
**简介：** 根据对象的OID进行查询
+ get(Object.class,index)
+ load(Object.class,index)
## 对象导航查询(了解)
**简介：** 根据一个已查询的对象，获取其关联的对象(对象集合)
## HQL查询
**简介：** Hibernate Query Language,一种面向对象的查询语言，语法类似SQL   
**例如：**
```
String hql="from User where name like ?1";
Query query=session.createQuery(hql);
query.setParameter(1,"J%");
```
### 简单查询
从Employee中获取所有对象的集合
```
Query query=session.createQuery("from Employee ");
List<Employee> list=query.list();
for (Employee emp:list){
    System.out.println(emp.getName());
}
```
### 排序查询
默认为升序查询
```
//降序查询
List<Employee> list = session.createQuery("from Employee order by id desc").list();
for (Employee emp : list) {
    System.out.println(emp.getId() + " " + emp.getName());
}
```
### 条件查询
#### 按位置绑定
```
Query query=session.createQuery("from Employee  where name= ?1");
query.setParameter(1,"Julia" );
```
#### 按名称绑定
```
Query query=session.createQuery("from Employee  where name=:aaa");
query.setParameter("aaa","spike");
List<Employee> list=query.list();
```

### 投影查询


## QBC查询

