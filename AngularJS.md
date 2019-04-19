# AngularJS
AngularJS诞生于2009年，由Misko Hevery 等人创建，后为Google所收购。是一款优秀的前端JS框架，已经被用于Google的多款产品当中。AngularJS有着诸多特性，最为核心的是：MVC、模块化、自动化双向数据绑定、语义化标签、依赖注入等等。

## 四个特征MVC模式

### MVC模式
Angular遵循软件工程的MVC模式,并鼓励展现，数据，和逻辑组件之间的松耦合.通过依赖注入，Angular为客户端的Web应用带来了传统服务端的服务，例如独立于视图的控制。 因此，后端减少了许多负担，产生了更轻的Web应用

+ Model:数据,其实就是angular变量($scope.XX);
+ View: 数据的呈现,Html+Directive(指令);
+ Controller:操作数据,就是function,数据的增删改查;

### 双向绑定
框架采用并扩展了传统HTML，通过双向的数据绑定来适应动态内容，双向的数据绑定允许模型和视图之间的自动同步

### 依赖注入
依赖注入(Dependency Injection)是一种设计模式, 指某个对象依赖的其他对象无需手工创建，则此对象在创建时，其依赖的对象由框架来自动创建并注入进来，模块中所有的service和provider两类对象，都可以根据形参名称实现DI.

### 模块化设计
高内聚低耦合法则	
1. 官方提供的模块：ng、ngRoute、ngAnimate
2. 用户自定义的模块：angular.module('模块名',[ ])

## 入门Demo

### 表达式和ng-app
新建html文件引入angular的js文件
```
<html>
<head>
    <title>augular入门</title>
    <script src="angular.min.js"></script>
</head>

<!-- ng-app：启动angula引擎 -->
<body ng-app>

<!-- 双大括号内的为表达式 -->
{{100+10+1}}
</body>
</html>
```
+ 表达式的写法是{{表达式 }} 表达式可以是变量或是运算式
+ ng-app 
   1. 作用是告诉子元素一下的指令是归angularJs的,angularJs会识别的
   2. 指令定义了 AngularJS 应用程序的 根元素。
   3. 指令在网页加载完毕时会自动引导（自动初始化）应用程序。

### 双向绑定
ng-model 指令用于绑定变量,这样用户在文本框输入的内容会绑定到变量上，而表达式可以实时地输出变量
```
<html>
<head>
    <title>augular入门</title> 
    <script src="angular.min.js"></script>
</head>

<body ng-app>
输入姓名：<input ng-model="name">
<br/>
{{name}}
</body>
</html>
```


### 初始化指令
我们如果希望有些变量具有初始值，可以使用ng-init指令来对变量初始化
```
  <html>
    <head>
      <title>augular入门</title>
      <script src="angular.min.js"></script>
    </head>

    <body ng-app ng-init="name='spike'">
    输入姓名：<input ng-model="name">

    {{name}}
    </body>
  </html>
```

### 控制器
ng-controller用于指定所使用的控制器。

$scope 的使用贯穿整个 AngularJS App 应用，它与数据模型相关联，同时也是表达式执行的上下文。有了$scope 就在视图和控制器之间建立了一个通道，基于作用域视图在修改数据时会立刻更新 $scope，同样的$scope发生改变时也会立刻重新渲染视图


```
  <html>
  <head>
    <title>augular入门</title>
    <script src="angular.min.js"></script>
    <script>
      var app=angular.module("myApp",[]);
      app.controller("myController",function($scope){
        $scope.add=function(){
          return parseInt($scope.x)+parseInt($scope.y);
        };
      });
    </script>
  </head>

  <body ng-app="myApp" ng-controller="myController">
  x:<input ng-model="x">
  y:<input ng-model="y">
  result:{{add()}}
  </body>
  </html>
```

### 事件指令
ng-click是最常用的单击事件指令，再点击时触发控制器的某个方法
```
  <html>
  <head>
    <title>augular入门</title>
    <script src="angular.min.js"></script>
    <script>
      var app=angular.module("myApp",[]);
      app.controller("myController",function($scope){
        $scope.add=function(){
          $scope.z=parseInt($scope.x)+parseInt($scope.y);
        };
      });
    </script>
  </head>

  <body ng-app="myApp" ng-controller="myController">
  x:<input ng-model="x">
  y:<input ng-model="y">
  <button ng-click="add()">result</button>
  {{z}}
  </body>
  </html>
```

### 循环数组
ng-repeat指令用于循环数组变量

**注：** 数组元素不可重复
```
  <html>
  <head>
    <title>augular入门</title>
    <script src="angular.min.js"></script>
    <script>
      var app=angular.module("myApp",[]);
      app.controller("myController",function($scope){
        $scope.list=[11,12,14,15,35];
      });
    </script>
  </head>

  <body ng-app="myApp" ng-controller="myController">
  <table>
    <tr ng-repeat="x in list">
      <td>{{x}}</td>
    </tr>
  </table>
  </body>
  </html>
```
### 循环对象数组

```
  <html>
  <head>
    <title>augular入门</title>
    <script src="angular.min.js"></script>
    <script>
      var app=angular.module("myApp",[]);
      app.controller("myController",function($scope){
        $scope.list= [
          {name:'张三',shuxue:100,yuwen:93},
          {name:'李四',shuxue:88,yuwen:87},
          {name:'王五',shuxue:77,yuwen:56}
        ];

      });
    </script>
  </head>

  <body ng-app="myApp" ng-controller="myController">
  <table>
    <tr>
      <td>姓名</td>
      <td>数学</td>
      <td>语文</td>
    </tr>
    <tr ng-repeat="entry in list">
      <td>{{entry.name}}</td>
      <td>{{entry.shuxue}}</td>
      <td>{{entry.yuwen}}</td>
    </tr>
  </table>
  </body>
  </html>
```
### 内置服务
我们的数据一般都是从后端获取的，使用内置服务$http来实现
```
  <html>
  <head>
    <title>入门小Demo-8  内置服务</title>
    <meta charset="utf-8" />
    <script src="angular.min.js"></script>
    <script>
      var app=angular.module('myApp',[]); //定义了一个叫myApp的模块
      //定义控制器
      app.controller('myController',function($scope,$http){
        $scope.findAll=function(){
          $http.get('data.json').success(
                  function(response){
                    $scope.list=response;
                  }
          );
        }
      });
    </script>
  </head>
  <body ng-app="myApp" ng-controller="myController" ng-init="findAll()">
  <table>
    <tr>
      <td>姓名</td>
      <td>数学</td>
      <td>语文</td>
    </tr>
    <tr ng-repeat="entity in list">
      <td>{{entity.name}}</td>
      <td>{{entity.shuxue}}</td>
      <td>{{entity.yuwen}}</td>
    </tr>
  </table>
  </body>
  </html>
```