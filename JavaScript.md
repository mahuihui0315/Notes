# JavaScript
JavaScript是一种轻量级的编程语言，可插入HTML页面的编程代码，可由所有的现代浏览器执行

## 基本使用方式
HTML 中的脚本必须位于 `<script>` 与 `</script>` 标签之间

脚本可被放置在 HTML 页面的 `<body>` 和 `<head>` 部分中

放置在head中，被调用时执行
```
<!DOCTYPE html>
<html>

<head>
<script>
function myFunction()
{
document.getElementById("demo").innerHTML="My First JavaScript Function";
}
</script>
</head>

<body>
<h1>My Web Page</h1>
<p id="demo">A Paragraph</p>
<button type="button" onclick="myFunction()">Try it</button>
</body>
</html>
```
放置在body，页面加载时执行
```
<!DOCTYPE html>
<html>
<body>

<h1>My Web Page</h1>

<p id="demo">A Paragraph</p>

<button type="button" onclick="myFunction()">Try it</button>

<script>
function myFunction()
{
document.getElementById("demo").innerHTML="My First JavaScript Function";
}
</script>

</body>
</html>
```
外部引入

通过`<script src=""></script>`标签引入

### 获取元素
可以通过documentget.ElementByID(id)获取指定id的元素
### 输出元素
通过document.write("")向页面中写入元素，但若在页面加载完成后使用会覆盖页面