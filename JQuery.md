# jQuery
jQuery 是一个 JavaScript 库，极大地简化了 JavaScript 编程

## 引入jQuery库
`<script type="text/javascript" src=""></script>`

## 基础语法
$(selector).action();
+ 美元符号定义 jQuery
+ 选择符（selector）“查询”和“查找” HTML 元素
+ jQuery 的 action() 执行对元素的操作

### 文档就绪函数
用于防止页面完全加载之前就运行jQuery代码
```
$(document).ready(function(){

--- jQuery functions go here ----

});
```

## 选择器
jQuery 元素选择器和属性选择器允许您通过标签名、属性名或内容对 HTML 元素进行选择，并操作相应的元素

### 元素选择器
jQuery使用css选择器来选取html元素
+ $("p")：选取 `<p>` 元素
+ $("p.classname")：选取所有 class="classname" 的 `<p>` 元素
+ $("p#id")：选取所有 id="id" 的` <p>` 元素

### 属性选择器
jQuery 使用 XPath 表达式来选择带有给定属性的元素
+ $("[href]") 选取所有带有 href 属性的元素
+ $("[href='#']") 选取所有带有 href 值等于 "#" 的元素
+ $("[href!='#']") 选取所有带有 href 值不等于 "#" 的元素
+ $("[href$='.jpg']") 选取所有 href 值以 ".jpg" 结尾的元素

### css选择器
jQuery CSS 选择器可用于改变 HTML 元素的 CSS 属性
例如：将所有p元素的背景颜色改为红色
> `$("p").css("background-color","red")`


## 事件函数
jQuery 事件处理方法是 jQuery 中的核心函数，事件处理程序指的是当 HTML 中发生某些事件时所调用的方法。术语由事件“触发”（或“激发”）经常会被使用，通常会把 jQuery 代码放到 `<head>`部分的事件处理方法中

例如：下面的代码中的click函数会在p元素被单击时触发，然后调用hide函数隐藏p元素
```
<html>
<head>
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $("button").click(function(){
    $("p").hide();
  });
});
</script>
</head>

<body>
<h2>This is a heading</h2>
<p>This is a paragraph.</p>
<p>This is another paragraph.</p>
<button>Click me</button>
</body>

</html>
```
### 常用事件函数
+ $(document).ready(function)：将函数绑定到文档的就绪事件（当文档完成加载时）
+ $(selector).click(function)：触发或将函数绑定到被选元素的点击事件
+ $(selector).dblclick(function)：触发或将函数绑定到被选元素的双击事件
+ $(selector).focus(function)：触发或将函数绑定到被选元素的获得焦点事件
+ $(selector).mouseover(function)：触发或将函数绑定到被选元素的鼠标悬停事件
+ $(selector).blur(function)：触发或将函数绑定到被选元素的失去焦点事件