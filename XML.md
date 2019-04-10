# XML

## 基本作用
1. 存储数据
2. 配置文件
3. 数据传输载体

## 文档声明
`<?xml version="1.0" encoding="utf-8/gbk" standalone="yes/no"?>`
+ version：解析器版本
+ encoding：编码方式
   + 本地默认编码：gbk
   + 建议写成：utf-8
   
## 标签格式
+ 基本与html相同
   + 标签可自定义
   + 注释格式：`<!---->`
+ `<![CDATA[内部元素]]>`：内部的字符会被解析器忽略

## 转义字符
+ 小于<：`&lt;`
+ 大于>：`&gt;`
+ 与&：`&amp;`
+ 省略号 . ：`&apos;`
+ 引号"：`&quot;`

## 解析方式

### DOM
读取全部文档内容，在内存中形成树状结构，并分为几类对象，并称为Node节点
+ document对象
+ element对象
+ attribute对象
+ text对象

文档过大会有内存溢出的风险，对于小文档效率更高，且可以进行增删操作

### SAX
Simple API for xml：基于事件驱动，一次读取一行，只能查询

### API
+ jaxp：sun公司，比较繁琐
+ jdom
+ dom4j：常用API

## 对象转XML
### 导入lib
+ xstream
+ xpp3
### 创建XStream对象
```
//创建XStream对象
XStream xstream=new XStream();
//设置别名（非必需）
xstream.alias("...",Object.class)
//转换对象
String xml=xstream.toXML(Object);
```