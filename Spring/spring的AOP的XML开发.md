# spring的AOP的xml开发
**简介：** Aspect Oriented Programming，面向切面编程，通过预编译方式和运行期动态
代理实现程序功能的统一维护的一种技术，是OOP的延续，Spring框架中的一个重要内容
## 为什么使用AOP
在不修改源代码的情况下增强功能
+ 权限校验
+ 日志记录
+ 性能监控
+ 事务控制
+ ...
## AOP的底层实现
### JDK动态代理
+ 接口及实现类
```
public interface User {
    public void save();
    public void update();
}
```

```
public class UserImpl implements User {
    @Override
    public void save() {
        System.out.println("存储用户");
    }

    @Override
    public void update() {
        System.out.println("更新用户");
    }
}
```
+ 动态代理类
```
public class GetProxy implements InvocationHandler {
    private User user;
    public GetProxy(User user){
        this.user=user;
    }
    public User createProxy(){
        User userProxy= (User) Proxy.newProxyInstance(user.getClass().getClassLoader(),user.getClass().getInterfaces(),this);
        return userProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("save")){
            System.out.println("检查用户信息");
            return method.invoke(user,args);
        }
        return method.invoke(user,args);
    }
}
```
+ 测试类
```
public class TestProxy {
    @Test
    /**
     * jdk动态代理测试
     */
    public void test(){
        User user=new UserImpl();
        user.save();
        user.update();

        User proxyUser=new GetProxy(user).createProxy();
        proxyUser.save();
        proxyUser.update();
    }
}
``
