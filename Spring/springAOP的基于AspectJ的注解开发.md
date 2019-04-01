# SpringAOP基于AspectJ的注解开发
## 文件配置
```
<!-- 注解开发，配置组件扫描 -->
<context:component-scan base-package="com.spring.contextdemo"/>
<!-- 没有扫描的情况下使用属性注解 -->
<context:annotation-config/>
<!-- 使用注解方式进行AOP开发 -->
<aop:aspectj-autoproxy/>
```
## Aspect类注解方式
+ @Aspect
> 将被标记的类声明为Aspect类
+ @Component(value="value")
> 将该类交予spring管理

## Advice注解方式
+ 前置
> @Before(value = "execution(* com.spring.contextdemo.SpaceShip.start(..))" )
+ 环绕
> @Around(value = "execution(* com.spring.contextdemo.SpaceShip.fly(..))")
+ 后置
> @AfterReturning(value = "execution(* com.spring.contextdemo.SpaceShip.stop(..))",returning = "result")
+ 异常
> @AfterThrowing(value = "execution(* com.spring.contextdemo.SpaceShip.warning(..))")
+ 最终
> @After(value = "execution(* com.spring.contextdemo.SpaceShip.warning(..))")

### pointcut注解
+ 定义
> @Pointcut(value = "execution(* com.spring.contextdemo.SpaceShip.start(..))")   
public void pointcut1(){}

+ 使用
> @Before("MyAspect.pointcut1()")