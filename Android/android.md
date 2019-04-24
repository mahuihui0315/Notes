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


## 工程目录结构
+ java：放置java代码的文件夹，在这里实现业务功能
+ res：存放各种资源文件的文件夹，例如图片，字符串，动画，音频以及各种xml文件等

### res资源文件夹
除了res外还有一个用于存放资源文件的文件夹assets，不同之处是res下的文件在R.java文件中存在唯一对应id，但是assets下的资源需要使用AssetManager以二进制的形式读取

#### 图片资源
1. drawable：存放各种位图文件（.png、.jpg等），也会存放一些drawable类型的xml文件
2. mipmap-hdpi：高分辨率图片的文件夹
3. mipmap-mdpi：中分辨率图片的文件夹，一般用于兼容旧型号手机才会使用
4. mipmap-xhdpi：超高分辨率图片的文件夹
5. mipmap-xxhdpi：超超高分辨率图片的文件夹

#### 布局资源
+ layout：存放布局资源，对特定手机型号进行屏幕适配

#### 菜单资源
+ menu：在以前有物理菜单按键的时候使用见多，现在基本不用，与菜单相关的xml文件在这里存放

#### values目录
+ colors.xml：定义资源颜色
+ styles.xml：定义资源样式
+ strings.xml：定义字符串资源

### 资源的使用
java代码中
+ 文字
> txtName.setText(getResources().getText(R.string.name));

+ 图片
> imgIcon.setBackgroundDrawableResource(R.drawable.icon);

+ 颜色
> txtName.setTextColor(getResource().getColor(R.color.red));

+ 布局
> setContentView(R.layout.min);

xml文件中

`<TextView android:text="@string/hello_world" android:layout_width="wrap_content" android:layout_height="wrap_content" android:background = "@drawable/img_back"/>`

## 重要文件

### MainActivity.java
一个MainActivity类继承了AppCompatActivity类，并实现了onCreate方法，调用setContentView方法使用了activity_main布局文件
```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

### 布局文件activity_main.xml
+ xmlns...：引入约束，用于判断语法是否正确
+ android:layout_width=""：控制组件的宽度，有三个属性
   1. warp_content：按照组件大小显示
   2. fill_parent：将组件横向拉伸以适应父容器
   3. match_parent：将组件横向拉伸以适应父容器
+ android:layout_height=""：控制组件的高度，有三个属性
   1. warp_content：按照组件大小显示
   2. fill_parent：将组件纵向拉伸以适应父容器
   3. match_parent：将组件纵向拉伸以适应父容器
+ android:text=""：为所在的textview设置文本

```
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

    <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello World!"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"/>

</android.support.constraint.ConstraintLayout>
```

### 配置文件AndroidManifest.xml
+ xmlns...：引入约束，用于判断语法是否正确
+ package：定义程序所在的包
+ android:allowBackup：是否允许备份
+ android:icon：icon图片
+ android:supportRtl：是否支持从右向左布局
+ android:theme：设置主题
+ android:name：声明一个activity，包括其包和app名称
+ intent-filter：意图过滤器，其下两个标签决定了程序的入口及其显示

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.mhh.androiddemo">
    <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
    </application>
</manifest>
```

## Android程序签名打包
Android APP都需要我们用一个证书对应用进行数字签名，不然的话是无法安装到Android手机上的，平时我们调试运行时到手机上时，是AS会自动用默认的密钥和证书来进行签名；但是我们实际发布编译时，则不会自动签名，这个时候我们就需要进行手动签名了

### 签名作用
1. 应用升级：只有用同一个证书签名，系统才会允许，如果不同系统就会要求应用程序采用不同的包名，这相当于安装了新的应用
2. 应用程序模块化：android允许多个相同签名程序在一个进程中运行，并把它们当做一个应用，因此可以将应用以模块的方式开发、部署和升级
3. 代码或者数据共享：有相同签名的不同应用可以安全的共享数据和代码

## Android4大组件

### Activity

### Service

### Broadcast Receiver

### Content Provider

