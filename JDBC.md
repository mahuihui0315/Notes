# JDBC基本使用

## 基本使用步骤
1. 注册驱动
   + 会注册两次：DriverManager.registerDriver(new com.mysql.jdbc.Driver());
   + 注册一次效率更高：Class.forName(new com.mysql.jdbc.Driver());
2. 建立链接
   + Connection conn=DriverManager.getConncetion("jdbc:mysql://localhost/数据库","username","password")
3. 创建SQL
   + String sql="select * from 表名";
4. 建立PreparedStatement
   + PreparedStatement ps=conn.preparedStatement(sql);
   + ps.setInt(1,id);
5. 执行查询获取结果   
   + ResultSet rs=ps.executeQuery();
5. 遍历结果
```
while(rs.next()){
    int id=rs.getInt("id");
    String name=rs.getInt("name");
    int age=rs.getInt("age");
    System.out.println(id+name+age);
}
```
6. 释放资源
```
if(rs!=null){
    try{
        rs.close();
    }catch(SQLException e){
        e.printStackTrace();
    }finally{
        rs=null;
    }
}
```
## CRUD操作

+ Query
```
public void query(int id)
    ...
    String sql="select * from 表名 where id=?";
    PreparedStatement ps=conn.prepareStatement(sql);
    ps.setInt(1,id);
    ResultSet rs=ps.executeQuery();
    ...
}
```
+ Insert：基本操作同上，只需更改sql语句，并将executeQuery（）换成executeUpdate（）
+ Update：基本操作同上，只需更改sql语句，并将executeQuery（）换成executeUpdate（）
+ Delete：基本操作同上，只需更改sql语句，并将executeQuery（）换成executeUpdate（）