# JSON

## 特点
阅读性强
容量更小

## 对象转JSON
+ 导入lib
   1. commons-beanutils
   2. commons-collections
   3. commons-lang
   4. commons-logging
   5. ezmorph
   6. json-lib-2.4-jdk15
+ 创建JSON对象
```
JSONArray json=new JSONArray().fromObject(ObjectList);
JSONObject json=new JSONObject().fromObject(Object);
```
+ 转换为String类型

`String jsonString=json.toString();`