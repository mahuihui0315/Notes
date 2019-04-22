# Dubbox

## 简介
Dubbox 致力于提供高性能和透明化的RPC远程服务调用方案，以及SOA服务治理方案。简单的说，dubbox就是个服务框架，只有在分布式的时候，才有dubbox这样的分布式服务框架的需求，并且本质是个远程服务调用的分布式框架。

## 基本架构
+ Provider: 暴露服务的服务提供方
+ Consumer: 调用远程服务的服务消费方
+ Registry: 服务注册与发现的注册中心
+ Monitor: 统计服务的调用次调和调用时间的监控中心
+ Container: 服务运行容器

### 调用关系
0. 服务容器负责启动，加载，运行服务提供者
1. 服务提供者在启动时，向注册中心注册自己提供的服务
2. 服务消费者在启动时，向注册中心订阅自己所需的服务
3. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推
送变更数据给消费者
4. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，
如果调用失败，再选另一台调用
5. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计
数据到监控中心

## 安装jar到本地maven仓库
因为网上资源库中不存在dubbox的最新jar，因此需要手动安装到本地
+ dubbo-2.8.4.jar

打开命令提示符，输入以下命令
> mvn install:install-file -Dfile=绝对路径\dubbo-2.8.4.jar -DgroupId=com.alibaba -DartifactId=dubbo -Dversion=2.8.4 -Dpackaging=jar

## registry：zookeeper
官方推荐使用 zookeeper 注册中心

注册中心负责服务地址的注册与查找，相当于目录服务，服务提供者和消费者只在启动时与注册中心交互，注册中心不转发请求

Zookeeper 是 Apacahe Hadoop 的子项目，是一个树型的目录服务，支持变更推送，适合作为Dubbox 服务的注册中心，工业强度较高，可用于生产环境

## dubbo-admin