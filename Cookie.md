# Cookie

## 简介
存储在客户端的一份小数据
+ 浏览记录
+ 自动登陆

## 存在意义
Http请求是无状态的，服务器并不知道访问自己的客户端是谁，为了收集用户信息和数据
而存在，当再次访问服务器时，会让服务器知道身份一定程度上提高了用户使用体验，例如
记录账号、密码无需再次输入，同时也侵犯了用户隐私，破环了服务器不得记录客户信息的原则

## 创建过程
1. 客户端第一次访问服务器，服务器生成Cookie
2. 下次客户端访问服务器会带上从服务器得到的Cookie

## 使用方法

+ 创建Cookie对象
> Cookie cookie=new Cookie("name","value");
+ 为response添加Cookie对象
> response.addCookie(cookie);
+ 获取客户端带来的Cookie
```
Cookie[] cookies=request.getCookies();
if(cookies!=null){
    for(Cookie c:cookies){
        String name=c.getName();
        String value=c.getValue();
    }
}
```
+ Cookie有效期
> 默认情况，关闭浏览器清除Cookie
+ 以秒为单位设置Cookie存在时间，-1为关闭浏览器清除
> cookie.setMaxAge(number)；
+ 更新Cookie值
> cookie.setVlaue(newValue);
+ 指定域名生成Cookie
> cookie.setDomain(" ");
+ 设置共享cookie的路径
> cookie.setPath(" ");

## Session
基于Cookie的一种会话机制，数据只存放在服务器端

### 使用Session
1. 新建servlet程序继承HttpSession
2. 获取session对象
> HttpSession session=request.getSession();
3. 获取会话ID
> String id=session.getId();


### 常用方法
+ 存值：session.setAttribute(name,value);
+ 取值：session.getAttribute(name);
+ 移除值：session.remoeAttribute(name);

### 生命周期
+ 创建
> 调用getSession()
+ 销毁
   1. 关闭服务器
   2. 会话过期，一般时间为30分钟
+ 强制结束会话
> session.invalidate();
+ 手动设置SessionId存在时间
> SessionId是通过Cookie传送，默认关闭浏览器清除Cookie同时也就清除了SessionId下次浏览器访问，服务器无法识别
```
String id=request.getSession().getId();
Cookie cookie=new Cookie("JSESSIONID",id);
cookie.setMaxAge(60*60*24*7);
response.addCookie(cookie);
```