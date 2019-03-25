# Jedis
**简介：** Jedis 是 Redis 官方首选的 Java 客户端开发包，实现了对redis各类API进行封装调用

**jar：** jedis-3.0.1.jar
## 常用API
方法|描述
:--|:--
new Jedis(host,port)|通过给定的ip和端口号创建jedis对象
set(key,value)|创建一个k-v键值对
get(key)|获取一个key对应的value
keys(key)|获取特定的key，*代表查找所有key
del(key)|删除key
exists(key)|判断是否存在key
rename(key1,key2)|重命名key1为key2
type(key)|返回key的数据类型
expire(key,timeout)|设置过期时间
hset(key,field,value)|创建一个hash对象
hget(key,field)|获取hash表key的指定域的值
hmget(key,field1,field2,...)|获取多个域的值
hgetAll(key)|获取所有域和值
hdel(key,field)|删除指定域
hkeys(key)|获取所有域
hvals(key)|获取所有值
lpush(key,value)|从左边插入value到列表key
lpop(key)|从左边弹出一个value
rpop(key)|从右边弹出一个value
llen(key)|获取key的元素个数
sadd(key,value)|添加value到集合key中
srem(key,value)|移除value从集合key中
smembers(key)|获取集合key的所有元素
sismember(key)|判断是集合key的成员
scard(key)|返回集合key的元素数量
sdiff(key1,key2)|返回差集
sinter(key1,key2)|返回交集
sunion(key1,key2)|返回并集

## JedisPool

**简介：** jedis本身的操作十分迅速，但是链接的创建相比起来就十分缓慢，耗费大量程序性能，
因此需要链接池来代替jedis创建链接。使用时只需从链接池中获取链接，操作完毕再返还   
 
**jar：** commons-pool2-2.6.1.jar
  

**jedisPool**

+ 创建jedis链接池工具类
```
public class JedisUtil {
    public JedisUtil(){};
    private static JedisPool jedisPool;
    private static int maxTotal;
    private static int maxWaitMillis;
    private static String host;
    private static int port;

    static {
        try {
            Properties properties = new Properties();
            InputStream is = new FileInputStream("src/redis.properties");
            properties.load(is);

            maxTotal = Integer.parseInt(properties.getProperty("maxTotal"));
            maxWaitMillis = Integer.parseInt(properties.getProperty("maxWaitMillis"));
            host = properties.getProperty("host");
            port = Integer.parseInt(properties.getProperty("port"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPool=new JedisPool(jedisPoolConfig,host,port);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    public static void close(Jedis jedis){
        if (jedis!=null){
            jedis.close();
        }
    }
}
```