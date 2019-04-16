# MySQL_慢查询日志
MySQL的慢查询日志是MySQL提供的一种日志记录，它用来记录在MySQL中响应时间超过阀值的语句，具体指运行时间超过long_query_time值的SQL，则会被记录到慢查询日志中。long_query_time的默认值为10，意思是运行10S以上的语句

## 慢查询日志相关参数
+ slow_query_log：是否开启慢查询日志，1表示开启，0表示关闭
+ slow-query-log-file：新版（5.6及以上版本）MySQL数据库慢查询日志存储路径。可以不设置该参数，系统则会默认给一个缺省的文件host_name-slow.log
+ long_query_time ：慢查询阈值，当查询时间多于设定的阈值时，记录日志

## 慢查询日志配置
+ 查看慢查询日志配置：mysql> show variables like '%slow_query_log%';
+ 开启/关闭慢查询日志：mysql> set global slow_query_log=1/0;
+ 修改long_query_time：mysql> set global long_query_time=10;
+ 查询有多少条慢查询记录：mysql> show global status like '%slow_queries%';

手动分析日志不但费时而且效率很低，因此MySQL提供了日志分析工具mysqldumpslow