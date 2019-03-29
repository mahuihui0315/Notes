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
> $ cd    

上一级目录
> $ cd ..

## make directory

> $ mkdir 文件夹名   
$ mkdir -p /文件夹名/文件夹名

## remove directory and file

> $ rmdir 文件夹名（必须未空）   
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
寻找指定指定前缀的文件
> find 路径 -name "filename*"

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
> chmod u-rwx   
chmod g-rwx   
chmod 777 文件/文件夹：添加 所有权限    
## man/help

查询命令帮助文档
> $ man 外部命令   
$ help 内部项目

## Shared folder
进入VMware的共享文件夹
> $ cd /mnt/hgfs/

## JDK Configuration
配置jdk
> 下载linux版本jdk并解压到需要的文件夹   
执行命令，打开.bashrc  
$ sudo gedit /.bashrc   
添加以下内容到最后面   
> ```
> # The PATH of jdk1.8.0_201   
> export JAVA_HOME=jdk的解压路径   
> export JRE_HOME=${JAVA_HOME}/jre   
> export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib   
> export PATH=${JAVA_HOME}/bin:$PATH
> ```   
> 退出并保存   
执行命令，使配置生效生效   
$ source /.bashrc   
> 查看是否配置成功   
$ java -version

## MySQL Installation

卸载旧版本MySQL	
+ 执行卸载语句
> $ sudo apt autoremove --purge mysql-server   
$ sudo apt remove mysql-common

+ 清除数据
> $ dpkg -l |grep ^rc|awk '{print $2}' |sudo xargs dpkg -P 

安装新版MySQL
+ 在MySQL官网下载最新版deb文件
> $ sudo dpkg -i mysql-apt-config_xxx.deb   
+ 更新软件库
> $ sudo apt update
+ 安装MySQL服务器
> $ sudo apt install mysql-server

重设默认密码
+ 寻找debian.cnf文件
> $ sudo vim /etc/mysql/debian.cnf   
+ 记录默认用户名及密码，并登陆mysql，执行以下语句
> show databases;   
use mysql;   
update user set authentication_string=PASSWORD("自定义密码") where user='root';   
update user set plugin="mysql_native_password";   
flush privileges;   
quit;   
+ 重启MySQL
> $ /etc/init.d/mysql restart
+ 使用修改后的root用户名及密码登陆

## Tomcat Installation

安装Tomcat
+ Tomcat官网下载需要的版本，并解压
> $ sudo tar -zxvf apache-tomcat-xxx.tar.gz
+ 进入安装目录下的bin目录下执行以下语句
> $ sudo vim startup.sh   
> 若拒绝进入，则修改文件夹权限   
>> $ sudo chmod 755 -R apache-tomcat-xxx
+ 打开文件之后在最后一行之前添加以下内容
> ```
> #set java environment
> export JAVA_HOME=xxx
> export JRE_HOME=${JAVA_HOME}/jre
> export CLASSPATH=.:%{JAVA_HOME}/lib:%{JRE_HOME}/lib
> export PATH=${JAVA_HOME}/bin:$PATH
> 
> #tomcat
> export TOMCAT_HOME=xxx/apache-tomcat-xxx
> ```
+ 保存并退出
+ 执行语句开启Tomcat(bin)
> sudo ./startup

+ 在浏览器访问localhost：8080验证是否成功

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