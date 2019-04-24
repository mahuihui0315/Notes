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

## jQuery效果

### hide()
单击id为hide的元素隐藏p标签
```
$("#hide").click(function(){
  $("p").hide();
})
```

### show()
单击id为show的元素显示p标签
```
$("#show).click(function(){
  $("p").show();
})
```
### toggle() 
单击button元素，显示被隐藏的p元素，隐藏被显示的p元素
```
$("button").click(function(){
  $("p").toggle();
})
```

### fadeIn()
淡入被隐藏的元素
+ speed：反应速度，"slow"、"fast"或者毫秒
+ callback：fade动作完成后执行的函数（可省略）
```
$("button").click(function(){
  $("#id").fadeIn(speed,callback);
})
```

### fadeOut()
同fadeIn()

### fadeToggle()
同fadeIn()

### fadeTo()
+ ...
+ opacity：设置渐变的透明度，0~1之间
+ ...
```
$("button").click(function(){
  $(#id).fadeTo(speed,opacity,callback);
})
```

### slideDown()
在元素上创建向下滑动效果
+ speed：滑动速度，"slow"、"fast"或者毫秒
+ callback：滑动结束之后调用的函数（可省略）
```
$("#filp").click(function(){
  $("p").slideDown(speed,callback);
})
```

### slideUp()
在元素上创建向上滑动效果
```
$("#filp").click(function(){
  $("p").slideUp(speed,callback);
})
```

### animate()
用于创建自定义动画

`$(selector).animate({parameters},speed,callback)`
+ parameters：定义动画的属性
+ speed：规定动画的时长
+ callback：动画完成后调用的函数

**注：** 一般来说，html元素都有一个静态位置无法移动，如需对位置进行操作需要将position属性设置为relative、fixed或者absolute

例：该段代码将div标签移动到250px位置，并改变形状及透明度
```
<!DOCTYPE html>
<html>
<head>
<script src="/jquery/jquery-1.11.1.min.js">
</script>
<script> 
$(document).ready(function(){
  $("button").click(function(){
    $("div").animate({
      left:'250px',
      opacity:'0.5',
      height:'150px',
      width:'150px'
    });
  });
});
</script> 
</head>
 
<body>

<button>开始动画</button>
<div style="background:#98bf21;height:100px;width:100px;position:absolute;"></div>

</body>
</html>
```
## jQuery HTML
jQuery 拥有可操作 HTML 元素和属性的强大方法

### 获取内容
基本语法：
`$(selector).action()`

+ html()：设置或者返回元素的内容，包括html标记
+ text()：设置或者返回元素的文本内容
+ val()：设置或者返回表单字段的值
+ attr("attribute")：获取属性

### 设置内容
基本语法：
`$(selector).action("content")`

+ text()：设置或返回所选元素的文本内容
+ html()：设置或返回所选元素的内容（包括 HTML 标记）
+ val()：设置或返回表单字段的值

### 添加元素
基本语法：
`$(selector).action("content")`

+ append()：在被选元素的结尾插入内容
+ prepend()：在被选元素的开头插入内容
+ after()：在被选元素之后插入内容
+ before()：在被选元素之前插入内容

### 删除元素
基本语法：
`$(selector).remove()`
+ 删除被选元素及其子元素
+ 可过滤被删除元素，例如remove(".name")删除所有class为name的元素

基本语法：
`$(selector).empty()`
+ 删除被选元素的子元素

### css类
jQuery 拥有若干进行 CSS 操作的方法

+ addClass()：向元素添加class属性
```
$("button").click(function(){
  $("p").addClass("classname");
})
```
+ removeClass()：移除元素的class属性
```
$("button").click(function(){
  $("p").removeClass("classname");
})
```

## jQuery遍历
