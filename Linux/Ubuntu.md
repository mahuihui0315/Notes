# Ubuntu入门教程

## Terminal

终端窗口    
> 快捷键：ctrl + alt + t

## change directory

进入指定路径文件夹
> $ cd 文件名   

进入根目录
> $ cd /   

回到home目录   
> $ cd ~   

上一级目录
> $ cd ..

## make directory

> $ mkdir 文件夹名   
$ mkdir -p /文件夹名/文件夹名

## remove directory and file

> $ rmdir 文件夹名（必须为空）   
$ rm 文件名   
$ rm -rf 文件夹名

## sudo

赋予本次操作root权限   
> 例如：   
$ cd /   
$ mkdir test  
 
会被拒绝：
> mkdir: cannot create directory ‘test’: Permission denied    

因为根目录需要更高权限才能操作，Ubuntu默认是普通用户，而sudo可以赋予用户root权限   
> $ sudo mkdir test

## copy file and directory

复制源文件夹下的文件到目标文件夹   
> $ cp 源路径/文件名 目标路径/文件名

复制文件夹到目标路径   
> $ cp -rf 源路径 目标路径

### 远程复制
+ $ scp: 即secure copy, 通过该命令可以将文件copy到远程服务器

#### 复制文件到远程服务器
基本格式: $ scp [local path] [filename] [hostname]@[ip address]:[remote path]

例: 复制一个txt文件到远程服务器
1. 执行命令: $ scp /home/administrator/news.txt root@192.xxx.x.xxx:.etc/squid
2. 弹出提示: Are you want to continue connecting(yes/no)?
3. 弹出密码输入提示
4. 输入密码,回车,文件开始传输,并显示相关信息

#### 重远程服务器复制文件
基本格式: $ scp remote@[ip address]:[remote path][filename] [local path]

例:复制一个.sh脚本文件
1. 执行命令: $ scp remote@www.abc.com:/usr/local/sin.sh /home/administrator

## move
移动文件
> $ mv 源路径 目标路径  

重命名   
> $ mv -f 路径名/旧文件名  路径名/新文件名
## vim

新建或打开文件   
> $ vim filename   

刚进入是普通模式
> 输入":"之后可以输入命令    
w保存，q退出，wq保存并退出  

插入模式
> 按i进入，可以书写内容   
esc退出插入模式   
双击"d"删除光标所在行

## shell

命令解释器，接收用户命令，调用相应程序
  
格式
> 后缀是 .sh   
文件第一行为 #!/bin/sh   

使用esch命令，编写一个shell文件
> esch ".." 打印字符串命令   
编写完成之后退出并保存   
$ sudo chmod 777 shellTest.sh   
将目标文件变为可执行文件，成功之后会变成绿色   
$ ./shellTest.sh

## find

### 查找文件

寻找指定指定前缀的文件
> find [path] -name "filename*"

寻找指定指定前缀的文件,忽略大小写
> find [path] -iname "filename*"

寻找在过去的n分钟被读取的文件
> find [path] -amin n

寻找在过去的n分钟被修改的文件
> find [path] -cmin n

查找空文件及空文件夹
> find [path] -empty

### 查找文件夹

 查找指定路径及文件名的文件夹
 > find [path] -type d -name "directoryname"
 
## grep
在文件中查找字符串
> grep -i "xxx" target_file

## sort
读取每一行的输入, 并按照指定的分隔符划分字段并排序, 有多种排序方式, 默认为字符集排序   
基本格式: sort [option] [filename]

以升序对文件内容进行排序
> sort target_file

以降序对文件进行排序
> 

## tar
文件压缩命令
> -c：压缩   
-x：解压   
-z：使用gzip   
-v：显示过程文件   
-f：使用档名

常用命令组合
+ zxvf：解压
> $ tar -zxvf 路径名/压缩包名   

+ zcvf：压缩   
> $ tar -zcvf 路经名/压缩包名 路径名/文件名 

## chmod
change mode：变更文件权限
+ 权限标识
> r：可读，值为4   
w：可写，值为2   
x：可执行，值为1   
-：无权限，值为0

+ 权限更改
> chmod abc filename   
a: User   
b: Group   
c: Other   
chmod 777 文件/文件夹：添加 所有权限    
## man/help

查询命令帮助文档
> $ man 外部命令   
$ help 内部项目

## Shared folder
进入VMware的共享文件夹
> $ cd /mnt/hgfs/

## Change sources.list
Ubuntu默认是国外源，下载速度太慢，可以更改为国内源
+ 更改之前先备份，以防更改出错或者以后需要使用
> $ sudo cp /etc/apt/sources.list /etc/apt/sources_copy.list
+ 打开sources.list,复制国内源进去
> $ sudo gedit /etc/apt/sources.list
+ 更新源
> $ sudo apt update
+ 更新软件

> $ sudo apt upgrade

## 更换主机名
1. 修改hostname文件
> $ sudo vim etc/hostname
2. 修改hosts文件
> $ sudo vim etc/hosts

## 查看系统的CPU信息
1. CPU个数
> cat /proc/cpuinfo|grep -c 'physical id'
2. 每个CPU的核数
> cat /proc/cpuinfo|grep -c 'processor'

## 查看系统负载
1. $ w
2. $ uptime
load average即表示系统负载, 后面的三个数值分别是1,5,15分钟内的系统平均负载

## 查看虚拟内存的统计信息
+ $ vmstat
   + r: running,当前任务数量
   + b: blocked,被阻塞的任务数量
   + si: swap input,从交换分区读入内存的数据量
   + so: swap output,从内存写入交换分区的数据量
   + bi: block input,从磁盘读入内存的数据量
   + bo: block output,从内存写入磁盘的数据量

## buffer和cache的区别
+ buffer: 将需要写入磁盘的数据先缓存到buffer区,在cpu不忙的时候仔写入磁盘
+ cache: 将需要用的数据提前从磁盘中存入cache,cpu需要用到的时候直接从cache中获取

## 查看系统资源占用情况
+ $ top

## 查看系统当前进程
+ $ ps: 用于查看当前进程

可以通过grep查看特定进程, 例如: ps -ef|grep java, 查看所有进程里CMD是java的进程信息
   
+ ps命令的参数
1. -e: 显示所有进程, 环境变量
2. -f: 全格式显示
3. -h: 不显示标题
4. -l: 长格式
5. -w: 宽输出
6. a: 显示终端上的所有进程, 包括其他用户进程
7. r: 显示正在运行的进程
8. u: 查看进程所有者及其他信息

+ $ top: 以CPU占用率列出进程

+ 进程状态: STAT    
D: 无法中断的休眠状态（通常 IO 的进程）   
R: 正在运行可中在队列中可过行的   
S: 处于休眠状态   
T: 停止或被追踪   
X: 死掉的进程   
Z: 僵尸进程


## 关闭进程
+ kill: 用于终止进程

每个进程都有一个唯一的PID(Process Identity), 可以通过pid或者进程名称来终止进程   
格式: kill [pid]

+ kill的命令参数
1. kill -STOP [pid]: 停止一个进程, 而不是销毁它
2. kill -CONT [pid]: 重新开始一个停止的进程
3. kill -KILL [pid]: 强制停止进程, 而且不实施清理
4. kill -9 [pid]: 强制终止进程


## 查看系统开启的端口
+ $ netstat -lnp

## 查看网络连接状态
+ $ netstat -an