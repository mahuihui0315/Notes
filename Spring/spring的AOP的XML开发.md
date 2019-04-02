# spring的AOP的xml开发
**AOP简介：** Aspect Oriented Programming，面向切面编程，通过预编译方式和运行期动态
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
```

### Cglib动态代理

## spring的基于AspectJ的AOP开发

### 相关术语
+ Joinpoint：连接点
> 所有可以被拦截，被增强的点，都是连接点
+ Pointcut：切入点
> 实际被拦截，增强的点
+ Advice：通知
> 对切入点的增强方法被称为通知或增强
+ Introduction：引介
> 类层面的增强
+ Target：目标
> 被增强的对象
+ Weaving：织入
> 将通知（advice）应用到目标（target）的过程
+ Aspect：切面
> 多个通知和切入点的组合

### spring整合junit测试
```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JunitTest {
    @Resource(name="car")
    private Car car;

    @Test
    public void test(){
        car.start();
        car.stop();
    }
}
```
### 添加必须jar
+ aopalliance-1.0.jar
+ aspectjrt-1.8.10.jar
+ aspectjweaver-1.8.10.jar

### 添加约束
> `xmlns:aop="http://www.springframework.org/schema/aop"`

> ```
> http://www.springframework.org/schema/aop
> http://www.springframework.org/schema/aop/spring-aop.xsd
> ```

### 配置格式
```
<!-- 将target交给spring管理 -->
<bean id="car" class="com.spring.aop.AspectJ_demo.CarImpl"/>
<!-- 将aspect交给spring管理 -->
<bean id="myAspect" class="com.spring.aop.AspectJ_demo.MyAspect"/>
<!-- 配置weaving -->
<aop:config>
    <!-- 配置pointcut -->
    <aop:pointcut id="pointcut1" expression="execution(* com.spring.aop.AspectJ_demo.CarImpl.start(..))"/>
    <!-- 配置advice -->
    <aop:aspect ref="myAspect">
        <aop:before method="checkCar" pointcut-ref="pointcut1"/>
        <!--<aop:after method="fixCar" pointcut-ref="car"/>-->
    </aop:aspect>
</aop:config>
```
### advice类型
+ 前置：在目标方法执行之前进行操作
> 获得切入点信息   
`<aop:before method="checkCar" pointcut-ref="pointcut1"/>`
+ 后置：在目标方法执行之后进行操作
> 获得方法的返回值  
`<aop:after method="fixCar" pointcut-ref="pointcut2"/>` 

+ 环绕：在目标方法执行之前和之后操作
> 性能监控   
`<aop:around method="checkRoad" pointcut-ref="pointcut3"/>`
+ 异常抛出：在程序出现异常的时候进行操作
+ 最终：无论程序是否有异常总会执行
+ 引介：

### pointcut语法格式
+ 基于execution的函数
+ 访问修饰符 方法返回值 包名.类名.方法名(参数)
   + public void com.spring.method(..)
   + (public可省略)*(*代表任意)  com.spring.method(..)
