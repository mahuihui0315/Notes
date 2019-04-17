# zookeeper

## 解压文件
> tar -zxvf zookeeper-3.4.6.tar.gz

## 生成配置文件
conf/目录下有个zoo_sample.cfg，是样板配置文件，复制为zoo.cfg
### 两个比较重要的配置
+ dataDir=/home/gradven/zookeeper-3.4.6/data：数据存放位置，可根据需要修改
+ clientPort=2181：服务监听端口，可根据需要修改。

## 启动服务

进入bin目录，运行zkServer.sh
> ./zkServer.sh start

查看运行状态
> ./zkServer.sh status
关闭服务
> ./zkServer.sh stop