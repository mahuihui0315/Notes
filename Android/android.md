# Android入门教程

## 系统特性
+ 应用程序框架支持组件的重用与替换
+ Dalvik虚拟机：专门为移动设备优化
+ 开源的浏览器引擎：webkit
+ SQLite结构化的数据存储
+ 优化的图形库,多媒体支持,GSM电话技术,蓝牙等
+ 采用软件叠层方式构建

## 平台架构
+ application：一般说的应用层的开发就是在这个层次上进行的，当然包括了系统内置的一组应用程序，使用的是Java语言
+ application framework：无论系统内置或者我们自己编写的App，都需要使用到这层，比如我们想弄来电黑名单，自动挂断电话，我们就需要用到电话管理(TelephonyManager) 通过该层我们就可以很轻松的实现挂断操作，而不需要关心底层实现
+ libraries、android runtime：Android给我们提供了一组C/C++库，为平台的不同组件所使用，比如媒体框架；而Android Runtime则由Android核心库集 + Dalvik虚拟机构成，Dalvik虚拟机是针对移动设备的虚拟机，它的特点:不需要很快的CPU计算速度和大量的内存空间
+ linux内核：涉及底层驱动，一些系统服务，比如安全性，内存管理以及进程管理等

## 开发环境
+ IDEA
+ Android Studio

## 常用术语
+ Dalvik：android特有虚拟机，非常适合用于移动平台
+ AVD：android virtual device，安卓虚拟设备，即安卓模拟器
+ ADT：android development tools，安卓开发工具
+ SDK：software development kit，软件开发工具包
+ DDMS：dalvik debug monitor service，安卓调试工具
+ ADB：android debug bridge，安卓调试桥
+ AAPT：android asset packing tool，安卓资源打包工具
+ AndroidManifest.xml：app包名 + 组件声明 + 程序兼容的最低版本 + 所需权限等程序的配置文件
