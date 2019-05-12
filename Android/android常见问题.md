# Android常见问题及解决方式

## IDEA的AVD相关

### Emulator: emulator: ERROR: Unknown AVD
模拟器无法找到需要的AVD

#### 原因
每当创建新的avd时，idea默认会将其配置文件放在user/.android/avd文件夹中，AVD Manager也会去该路径寻找avd文件并启动

但是当配置系统环境变量ANDROID_SDK_HOME之后，AVD Manager则会去ANDROID_SDK_HOME/avd文件夹寻找需要的启动文件，但是该路径并不存在，因此就会报错

#### 解决方式
将user/.android/avd文件夹复制到ANDROID_SDK_HOME对应的目录下

### Emulator: emulator: ERROR: x86 emulation currently requires hardware acceleration
模拟器需要硬件加速

#### 原因
1. 该电脑未开启VT（virtual technology）
2. 未安装Intel HAXM

#### 解决方式
1. 进入BIOS开启VT
2. IDEA中进入SDK Manager，setting/Appearance&Behavior/System Setting/Android SDK安装Intel HAXM

## Layout的视图预览相关

### Failed to load AppCompat ActionBar with unknown error
layout文件夹下的xml布局文件无法进行视图预览
#### 原因
不明

#### 解决方式
在AndroidManifast文件中找到android:theme，ctrl+单击进入对应文件，在style标签的parent属性前加“Base.”
> \<style name="AppTheme" parent="**Base.** Theme.AppCompat.Light.DarkActionBar">

### This view is not constrained. It only has designtime positions, so it will jump to (0,0) at runtime unless you add the constraints 
该view没有约束，它仅仅有设计时的位置，因此会在运行时移动到（0,0）位置

#### 原因
使用ConstraintLayout直接拖拽添加view，例如一个button时，不会将设置该view的布局，因此报提示

#### 解决方法
选择对应view单击预览视图上方的Infer constraints按钮，添加constraint

## 视图调用setOnTouchListener
自定义一个view类，实例化并调用setOnTouchListener时报警告
> If a View that overrides onTouchEvent or uses an OnTouchListener does not also implement performClick and call it when clicks are detected,
the View may not handle accessibility actions properly. Logic handling the click actions should ideally be placed in
View#performClick as some accessibility services invoke performClick when a click action should occur.

### 原因
创建自定义View类时没有重写performClick方法，但是调用setOnTouchListener时需要调用performClick方法
而重写onTouch方法可能会屏蔽performClick方法，因此报出警告

### 解决方法
在自定义View类中重写performClick方法，并在发生单击事件时使用view调用该方法

## MIUI系统Toast显示应用名问题
MIUI系统下直接使用Toast时, 会在提示信息之前加上应用名
例如:
`Toast.makeText(context,"Create succeeded", Toast.LENGTH_SHORT)`

### 解决方法
将输出信息设置为null,之后调用setText()方法重新设置输出信息
示例代码:
```
Toast toast=Toast.makeText(context, null, Toast.LENGTH_SHORT);
toast.setText("Create succeeded");
toast.show();
```

## java.lang.ClassNotFoundException: Didn't find class "android.view.View$OnUnhandledKeyEventListener" on path: DexPathList

### 原因
程序使用的API高于模拟器或者手机的API版本

### 解决方法
更换符合程序API的模拟器或者手机再次运行

## Notification在Android8.0不生效问题

### 原因
Android8.0之后的NotificationCompat.Builder()需要一个额外的参数channelId

示例代码:
```
NotificationChannel channel= null;
//android O,即android 8.0以上版本时需要channel来使用NotificationCompat.Builder()
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
    String channelId="channel_1";
    String channelName="Notification";
    manager.createNotificationChannel(new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_HIGH));
}
Notification notification=new NotificationCompat.Builder(this,"channel_1")...;
```