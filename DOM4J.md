# DOM4J基本用法

## 简介

+ dom4j是一个Java的XML API，是jdom的升级品，用来读写XML文件的，
具有性能优异、功能强大和极其易使用的特点

## 基本用法
1. 获取SAX对象
> SAXReader reader=new SAXreader();
2. 解析指定xml文件
> Document document=reader.read(new file("src"));
3. 获取根元素
> Element rootElement=document.getRootElement();
4. 获取根元素下的子元素
> List<Element> elements=rootElement.elements();
5. 获取子元素的内容
> elements.element("标签名").getText();

## XPATH
	
### 简介
+ xml路径语言，用于快速定位元素

### 基本用法
1. 导入jaen包
2. 通过Xpath语法定位元素
> A元素下的B元素=/A/B   
> 文档中的所有B元素=//B

3. List<Element> elements=(Element) rootElement.selectSingleNode("//B");

## XML约束

### DTD

**引入方式**
1. 引入网络DTD文件   
`<!DOCTYPE 根标签名 PUBLIC "//DTD名/" "DTD路径">`
2. 引入本地DTD文件   
`<!DOCTYPE 根标签名 SYSTEM "DTD路径">`
3. xml文件嵌套DTD   
```
<!DOCTYPE 根标签名[
    <!ELEMENT 标签名 （下级标签名）>
    <!ELEMENT 标签名 （#PCDATA）>
]>
```
    
**书写格式**   

`<!ELEMENT 标签名 （下级标签名）>`   

`<!ELEMENT 标签名 （#PCDATA）>`   

`<!ATTLIST 标签名 属性名 属性类型 默认值>`   
+ 属性类型
   + CDATA：字符数据
   + ID：值唯一
   + 默认值
      + #REQUIRED：必需的
      + #IMPLIED：可选的 
   + 元素个数
      + +：一个或则多个
      + *：零个或者多个
      + ？：零个或者一个
### Schema

**格式***
```
<schema xmlns="#" targetNameSpace="#" elementFormDefault="#">
    <element name="标签名">
        <complexType>
            <sequence>
                <element name="标签名">
                </element>
            </sequence>
        </complexType>
    </element>
</schema>
```
+ sequence属性   
   + maxOccurs=“#”
      + #=数字：元素出现的最大次数
      + #=unbounded：无限次数
+ 名称空间
```
<a:name></a:name>
<b:name></b:name>
```
a与b用于区分不同的schema文件