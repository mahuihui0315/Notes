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