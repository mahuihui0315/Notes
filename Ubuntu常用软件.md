# Ubuntu常用软件

## net-tools

+ 作用：
> 1. ifconfig:显示或配置网络设备，与windows系统下的ipconfig相识
+ 安装：
> ~$ sudo apt install net-tools

# openssh-server

+ 作用：
> 可以从其他主机上操作本地linux
+ 安装：
> 1. 安装openssh-server：~$ sudo apt-get install openssh-server
> 2. 查看是否成功：~$ ps -e | grep ssh
> 3. 手动开启ssh：~$ ssh start

# vim

+ 作用：
> 文本编译器

+ 安装：
> ~$ sudo apt install vim

## JDK Configuration
配置jdk
> 下载linux版本jdk并解压到需要的文件夹   
执行命令，打开.bashrc  
~$ sudo gedit ~/.bashrc   
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
~$ source ~/.bashrc   
> 查看是否配置成功   
~$ java -version

## MySQL Installation

卸载旧版本MySQL	
+ 执行卸载语句
> ~$ sudo apt autoremove --purge mysql-server   
~$ sudo apt remove mysql-common

+ 清除数据
> ~$ dpkg -l |grep ^rc|awk '{print $2}' |sudo xargs dpkg -P 

安装新版MySQL
+ 在MySQL官网下载最新版deb文件
> ~$ sudo dpkg -i mysql-apt-config_xxx.deb   
+ 更新软件库
> ~$ sudo apt update
+ 安装MySQL服务器
> ~$ sudo apt install mysql-server

重设默认密码
+ 寻找debian.cnf文件
> ~$ sudo vim /etc/mysql/debian.cnf   
+ 记录默认用户名及密码，并登陆mysql，执行以下语句
> show databases;   
use mysql;   
update user set authentication_string=PASSWORD("自定义密码") where user='root';   
update user set plugin="mysql_native_password";   
flush privileges;   
quit;   
+ 重启MySQL
> ~$ /etc/init.d/mysql restart
+ 使用修改后的root用户名及密码登陆

## Tomcat Installation

安装Tomcat
+ Tomcat官网下载需要的版本，并解压
> ~$ sudo tar -zxvf apache-tomcat-xxx.tar.gz
+ 进入安装目录下的bin目录下执行以下语句
> ~$ sudo vim startup.sh   
> 若拒绝进入，则修改文件夹权限   
>> ~$ sudo chmod 755 -R apache-tomcat-xxx
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