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
查询特定属性

+ 单个属性查询   
`List<Object> list=session.createQuery("select c.id from Employee c").list()`
+ 多个属性查询   
`List<Object[]> list=session.createQuery("select c.id,c.name from Employee c").list();`
+ 查询多个属性并封装到对象中   
`List<Employee> list=session.createQuery("select new Employee(id,name) from Employee").list();`

### 分页查询
+ query.setFirstResult(start);   
+ query.setMaxResults(end);

### 分组统计查询
+ 聚合函数的使用：count(),max(),min(),avg(),sum()
```
Query query=session.createQuery("select count(*) from Employee");
long count=(long)query.uniqueResult();
```
+ 分页查询统计
```
List<Object[]> list=session.createQuery("select name,count(*) from Employee group by name").list();
for (Object[] objects:list) {
    System.out.println(Arrays.toString(objects));
}
```

## QBC查询

### 简单查询
```
Criteria criteria=session.createCriteria(Employee.class);
List<Employee> list=criteria.list();
```

### 排序查询
+ 升序   
`List<Employee> list=criteria.addOrder(Order.asc("id")).list();`
+ 降序   
`List<Employee> list=criteria.addOrder(Order.desc("id")).list()`

### 分页查询
```
Criteria criteria=session.createCriteria(Employee.class);
criteria.setFirstResult(0);
criteria.setMaxResults(2);
List<Employee> list=criteria.list();
```

### 条件查询

**条件：** `=:eq,>:gt,>=:ge,<:lt,<=:le,<>:ne,like,in,and,or`
`List<Employee> list=criteria.add(Restrictions.eq("id",1)).list();`
### 统计查询
`long count=(long)criteria.setProjection(Projections.rowCount()).uniqueResult();`
### 离线条件查询
```
DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Employee.class);
detachedCriteria.add(Restrictions.like("name","J%"));

Criteria criteria=detachedCriteria.getExecutableCriteria(session);
List<Employee> list=criteria.list();
```
