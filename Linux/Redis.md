# Redis

## NoSQL型数据库

非关系型数据库，因传统数据库逐渐无法应对web2.0带来的大规模，高并发和多数据类型等挑战而产生   
特点：   
1. 高并发读写   
2. 海量数据高效率存储
3. 可扩展性强

4种主要类型：   
1. 键值存储数据库
2. 列存储数据库
3. 文档型数据库
4. 图形数据库

## Redis
一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API   

**应用场景**  
 
需要频繁访问，时时处理，动态变化的场景   
> 热点数据存储   
限时业务的应用   
计数器相关   
排行榜相关   
好友列表   
等... 

## 常见问题及解决方式

**(error) MISCONF Redis is configured to save RDB snapshots, but is currently not able to persist on disk**

+ 原因：
> 强制关闭Redis快照导致不能持久化

+ 解决方法：
> 打开redis，运行以下命令   
config set stop-writes-on-bgsave-error no
---


## 数据结构
**string**  
+ 字符串 
> redis基本的数据类型   
二进制安全，可以包含数据   
一个key对应一个value，最大容量512M

**list**
+ 列表
> 简单的字符串列表，底层是链表   
一个key对应多个value   
头尾操作元素效率很高，中间效率降低
   
**hash**
+ 哈希
> 类似于java的Map<String，Object>   
仍旧是k-v模式但是v是一个键值对
   
**set**
+ 集合 
> string类型的无序集合，底层是HashTable   
一个key对应多个value
元素不重复
  
**sorted-set**
+ 有序集合
> 有序的string类型集合，成员唯一   
通过为每一个元素关联一个double类型的分数实现由小到大排列

## 基本操作
+ 选择数据库0~15
> select index

**key**
+ 创建key
> set keyname keyvalue

+ 删除key（一般通过设置过期时间完成）
> del keyname 

+ 模糊查询   
> *：任意多个字符   
？：单个字符   
[]：括号内的一个字符  
例：keys *

+ key存在判断   
> exists key keyname

+ 移动key到其他库
> move keyname dbindex

+ 设置key过期时间
> expire keyname seconds 

+ 查看key过期时间,-1：永不过期，-2：已过期
> ttl keyname 

+ 查看key类型
> type keyname

**string**

+ append：追加元素
> append keyname string

+ strlen：字符串长度
> stelen keyname

+ incr/decr：加1/减1(必须是数字)
> incr/derc keyname

+ incrby/decrby：加x/减x(必须是数字)
> incrby/dercby keyname x

+ getrange：获取指定范围的值
> getrange keyname startIndex endIndex

+ setrange：设置指定范围的值
> setrange keyname startIndex xxx

+ setex：设置过期时间，并替换value
> setex keyname seconds value

+ setnx：设置过期时间，并替换value（key不存在时生效）
> setnx keyname seconds value

+ mset/mget：multiple set
> mset key1 value1 key2 value2 ...

+ msetnx：若key不存在则生效

**list**

+ lpush：由左边写入元素，后来的元素会被写到前面
> lpush listname value1 value2 ...  

+ rpush：从右边写入元素，后来的元素会被写到后面
> rpush listname value1 value2 ...

+ lrange：从左读出元素
> lrange listname 0 -1(读出全部元素)

+ lpop：从左弹出一个元素
> lpop listname

+ rpop：从右弹出一个元素
> rpop listname

+ lindex：按照元素下表读出数据
> lindex listname index

+ llen：获取list元素个数
> llen listname

+ lrem：删除n个元素
> lrem listname n value

+ ltrim：截取指定的值，并赋值给list本身
> ltrim listname startIndex endIndex

+ rpoplpush：从源列右边弹出一个元素，加入目标列的左边
> rpoplpush sourcelist targetlist

+ lset：设置指定索引的元素的值
> lset listname index value

+ linsert：指定位置插入元素
> linsert listname before/after pivot value

**hash**
+ hest：创建hash
> hset keyname fieldname value

+ hget：获取hash中的值
> hget keyname fieldname

+ hmset/hmget：批量创建获取
> hmset keyname fieldname1 value1 fieldname2 vlaue2 ...   
hmget keyname fieldname1 fieldname2 ...

+ hgetall：获取全部值
> hgetall keyname

+ hdel：删除指定元素
> hdel keyname fieldname

+ hlen：hash中field的个数
> hlen keyname

+ hexists：判断hash的指定field是否存在
> hexists keyname fieldname

+ hkeys：获取hash中所有的field
> hkeys keyname

+ hvals：获取hash中所有的value
> hvals keyname

+ hincrby：按指定值增加
> hincrby keyname fieldname

+ hincrbyfloat：按指定浮点数增加
> hincrbyfloat keyname fieldname

+ hsetnx：不存在就增加
> hsetnx keyname fieldname

**set**

+ sadd：创建新的set
> sadd setname value1 value ...（重复的值只会存储一个）

+ smembers：获取set中的值
> smembers setname

+ scard：获取set中的元素个数
> scard setname

+ srem：删除set中的指定元素
> srem setname value

+ srandmember：随机抽取指定数量的元素
> srandmember setname nums

+ spop：随机出栈一个元素
> spop setnameconfig

+ smove：将源sey中的一个值移动到目标set
> smove source destination value

*数学集合类*
+ sdiff：差集，第一个set中存在而后面set中不存在
> sdiff setname1 setname2 setname3 ...

+ sinter：交集，同时存在在所有set中的元素
> sinter setname1 setname2 setname3 ...

+ sunion：并集，所有set中的所有元素
> sunion setname1 setname2 setname3 ...


**zset**
 
+ zadd：创建zset
> zadd keyname score fieldname

+ zrange：查询
> zadd keyname startIndex endIndex

+ zrangebyscore：按score查询
> zrangebyscore keyname min max   
zrangebyscore keyname min (max ：不包括最大值

+ zcard：查询键值对数量
> zcard keyname

+ zcount：按区间查询数量
> zcount keyname min max

+ zrank：查询下表
> zrank keyname member

+ zrevrange：逆序查询
> zrevrange keyname startIndex endIndex

## 消息订阅和发布

**subscribe**
+ 订阅一个频道，接收其他操作者发布的信息
> subscribe channelname(单个订阅)   
psubscribe channel*(多个订阅)


+ 发布一个消息xxx
> publish channelname "xxx"

## 事务

1. 所有的命令都是串行化按顺序执行   
2. 事务执行期间不会执行其他客户端的请求   
3. 一条命令执行失败，其他命令不会继续执行(redis5.0.4)

**multi**

+ 开启事务
> multi

+ 关闭事务
> exec

+ 回滚   
   + 手动执行回滚   
   + 提交时遇到错误自动执行
> discard
