# Ubuntu常见问题及解决方法

---

## 屏幕过小

+ 解决方法：安装VMwaretools
> 1. 将安装包移动到home目录
> 2. 解压安装包：tar -zxvf 安装包名
> 3. 进入解压目录：cd 解压目录名
> 4. 安装VMwaretools：sudo ./vw（tab键自动补全文件名）
> 5. VMware界面查看->立即适应客户机

## Package * is not available, but is referred to by another package
+ 原因：ubuntu的/etc/apt/source.list中的源比较旧，需要更新
+ 解决方法：更新源
> ~$ sudo apt-get -y update

## The remote system refused the connection

+ 使用SecureCRT链接linux报错，系统拒绝链接
+ 原因：linux未安装openssh-server
+ 解决方法：安装openssh-server
> 1. 安装openssh-server：~$ sudo apt-get install openssh-server
> 2. 查看是否成功：~$ ps -e | grep ssh
> 3. 手动开启ssh：~$ ssh start

