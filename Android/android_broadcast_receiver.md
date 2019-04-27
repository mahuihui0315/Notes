# Broadcast Receiver
在Android中，Broadcast是一种广泛运用的在应用程序之间传输信息的机制。而BroadcastReceiver是对发送出来的 Broadcast进行过滤接受并响应的一类组件

## Broadcast分类

### Normal Broadcasts
异步执行的广播,发出之后所有的接收器几乎都会在同一时间接收到. 效率比较高,但是也无法被拦截

### Ordered Broadcasts
同步执行的广播,发出之后同一时间只会有一个接收器能够收到,只有在这个接收器执行完毕之后,广播才会继续传递到下一个接收器

## Broadcast Receiver的使用

### 动态注册
在activity代码中直接注册广播被称为动态注册,动态注册的广播都必须在执行完毕后取消注册

下面的示例代码通过动态注册接收器,接收网络变化的广播
1. 新建NetworkChangeReceiver类继承BroadcastReceiver,并重写onReceiver()
```
//动态注册广播器
class NetworkChangeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ...
    }
}
```
2. 创建IntentFilter实例,并添加action,以此来定义需要接收到广播类型,下面示例代码中对网络变化广播进行接收
```
IintentFilter intentFilter=new IntentFilter();
//接收网络变化的广播
intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
```
3. 新建NetworkChangeReceiver的实例,并调用registerReceiver()进行注册
```
//注册广播接收器
registerReceiver(networkChangeReceiver,intentFilter);
```
4. 重写onDestroy()方法
```
@Override
//动态注册的广播器必须手动取消注册
protected void onDestroy() {
    super.onDestroy();
    unregisterReceiver(networkChangeReceiver);
}
```
至此,动态注册完成,当网络发生变化时就会接收到广播,并调用onReceive()方法

5. 在onReceive()方法中对网络变化做出响应,通过getSystemService()获取ConnectivityManager类,
然后调用getActiveNetworkInfo()获取NetworkInfo实例,用于判断当前网络状态
```
public void onReceive(Context context, Intent intent) {
    ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
    if (networkInfo!=null&&networkInfo.isConnected()){
        Toast.makeText(context, "Network is connected", Toast.LENGTH_SHORT).show();
    }else{
        Toast.makeText(context, "Network is disconnected", Toast.LENGTH_SHORT).show();
    }
}
```
**注:** 若程序需要进行一些可能会涉及到用户隐私的操作,必须提前在配置文件中声明,例如本例中访问的网络状态
```
<manifest ...>
    ...
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    ...
</manifest>
```

### 静态注册
动态注册虽然可以自由注册与注销,但是在程序未启动时无法接收广播,因为是在onCreate()中注册的.

但静态注册就可以在程序还未启动时接收广播

下面的示例代码通过静态注册接收器,接收开机广播并做出响应
1. 新建BootCompleteReceiver类,继承BroadcastReceiver
```
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    //静态注册广播器
    public void onReceive(Context context, Intent intent) {
        ...
    }
}
```
2. 在AndroidManifest.xml文件中注册receiver
```
        <receiver
                android:name=".BootCompleteReceiver"
                android:enabled="true"
                android:exported="true">
            <intent-filter>
                ...
            </intent-filter>
        </receiver>
```
3. \<intent-filter>标签中添加action属性,指明接收广播的类型,android.intent.action.BOOT_COMPLETED为开机完成后的发送的广播
```
<intent-filter>
    <action android:name="android.intent.action.BOOT_COMPLETED"/>
</intent-filter>
```
4. 接收开机广播同样需要申请权限
```
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
```
至此,接收开机广播并做出响应的静态接收器注册完成

## 自定义Broadcast

### Normal Broadcasts
普通广播的发送十分简单
```
Intent intent=new Intent("com.example.broadcasttest.MY_BROADCAST");
sendBroadcast(intent);
```
再注册一个接收器,接收com.example.broadcasttest.MY_BROADCAST的广播即可
```
class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {;
        Log.d(TAG ,"receive broadcast");
        Toast.makeText(context, "Received in MyBroadcastReceiver", Toast.LENGTH_SHORT).show();
    }
}
```

**注:** android 8.0以后禁用了大部分的静态注册的隐式广播,因此静态注册的接收器将接收不到该广播

解决办法:

1. 动态注册接收器
2. 使用setComponent()指定接收器
> intent.setComponent(new ComponentName(MainActivity.this,MyBroadcastReceiver.class));

### Ordered Broadcasts
有序广播只需在普通广播基础上稍作修改即可

1. 替换发送广播的方法,第一个参数仍旧是Intent,第二个为权限相关字符串
```
sendOrderedBroadcast(intent,null);
```
2. 通过给接收器的\<intent-filter>设置android:priority属性决定接收器的优先级,数值为-1000~1000,数值越大,优先级越高
3. 截断广播: 可以被截断是有序广播的一大特点,通过在onReceive()方法中调用abortBroadcast()即可

## Local Broadcasts
前面使用的都是系统全局级别的广播,可以被本应用之外的所有应用接收,并且也可以接收任何其他应用的广播,非常容易引起安全问题,
因此android提供了本地广播机制,使得广播只在应用内部传播

### 发送本地广播
1. 获取LocalBroadcastManager实例
```
private LocalBroadcastManager localBroadcastManager;
@Override
protected void onCreate(Bundle savedInstanceState) {
    ...
    localBroadcastManager=LocalBroadcastManager.getInstance(this);
    ...
}
```
2. 获取Intent并设置action
```
Intent intent1=new Intent("LOCAL_BROADCAST");
```
3. 发送本地广播
```
localBroadcastManager.sendBroadcast(intent1);
```

### 接收本地广播
1. 新建LocalReceiver类继承BroadcastReceiver
```
class LocalReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
    }
}
```
2. 获取LocalReceiver实例,并注册
```
intentFilter=new IntentFilter();
intentFilter.addAction("LOCAL_BROADCAST");

localReceiver=new LocalReceiver();
localBroadcastManager.registerReceiver(localReceiver,intentFilter);
```
3. onDestroy()方法中注销LocalReceiver
```
localBroadcastManager.unregisterReceiver(localReceiver);
```
**注:** 本地接收器只能使用动态注册

#### 本地广播的优势
1. 广播不会离开程序,数据不容易泄露
2. 外部广播无法进入,程序更稳定
3. 效率更高