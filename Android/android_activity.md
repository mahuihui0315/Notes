# Activity
Activity是Android提供的四大组件之一,是进行Android开发必不可少的组件.
Activity是一个界面的载体,所有可见的都在活动中,可以把它与html页面进行类比,
html页面由各种各样的标签组成,而Activity则可以由各种控件组成

## 基本用法

### 新建类继承AppCompatActivity

```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
```

+ onCreat():活动创建时必定会执行的方法
+ setContentView():获取布局资源

### AndroidManifest中注册activity

```
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
    <activity android:name=".MainActivity">

    </activity>
</application>
```
IDEA会在activity被创建时直接注册好,但是要启动还需要配置主活动,即程序运行时最先被启动的活动
```
<intent-filter>
	<action android:name="android.intent.action.MAIN"/>

	<category android:name="android.intent.category.LAUNCHER"/>
</intent-filter>
```

### 在activity中使用Toast
Toast是Android提供的提醒工具,用于用户发送简单消息
```
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button button=findViewById(R.id.button_01);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "You clicked button01", Toast.LENGTH_SHORT).show();
        }
    });
}
```
示例代码中获取布局文件中的一个button并注册了点击事件监听,当该button被点击时,会调用静态方法makeText生成Toast对象并发送消息

### 销毁activity
+ 返回键
+ finish()

## 使用Intent在activity之间切换
Intent是android程序中各个组件之间进行交互的一种重要方式,
不仅可以指明当前组件的执行动作,还可以在不同组件之间传递数据
### 显示Intent
创建两个activity在主活动中添加代码
```
button.setOnClickListener(new View.OnClickListener() {
	@Override
	//单击触发时跳转activity
	public void onClick(View v) {
		Intent intent=new Intent(MainActivity.this,SecondActivity.class);
		startActivity(intent);
	}
});
```
示例代码为button绑定了单击事件监听,当button被点击时触发onClick函数,通过intent对象开启SecondActivity

### 隐式Intent
隐式Intent并不直接制定跳转的activity,而是通过一系列的action和category等信息,由系统分析该指定那个activity启动

在\<activity>标签下的\<intent-filter>中指明action和category
```
<activity android:name=".SecondActivity">
    <intent-filter>
        <action android:name="com.mhh.androiddemo.ACTION_START"/>
        <category android:name="android.intent.category.DEFAULT"/>
    </intent-filter>
</activity>
```
设置intent对象的action和category
```
button.setOnClickListener(new View.OnClickListener() {
    @Override
    //单击触发时跳转activity
    public void onClick(View v) {
        Intent intent=new Intent("com.mhh.androiddemo.ACTION_START");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }
});
```
单击button触发onClick函数时,系统会根据action和category启动匹配的activity

一个intent对象只能有一个action但是可以拥有多个category,\<intent-filter>标签中也是

#### 通过隐式Intent调用其他应用
```
button.setOnClickListener(new View.OnClickListener() {
    @Override
    //单击触发时跳转activity
    public void onClick(View v) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.bilibili.com"));
        startActivity(intent);
    }
});
```
在上述代码中,若button被点击,onClick函数触发就会跳转到浏览器应用打开指定网页

这一功能需要\<intent-filter>下的\<data>标签来实现,通过data标签可以更加精确的设置activity能够相应的数据类型

data标签常用属性:
+ android:scheme: 用于指定数据的协议部分,例如http
+ android:host: 用于指定数据的主机名部分,例如示例的www.bilibili.com
+ android:post: 用于指定数据的端口号

### 向下一个activity传递数据
Intent提供了putExtra()方法用于向Intent中写入数据
```
button.setOnClickListener(new View.OnClickListener() {
    @Override
    //单击触发时跳转activity
    public void onClick(View v) {
        String data=new String("Hello SecondActivity");
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        intent.putExtra("extra_data",data);
        startActivity(intent);
    }
}
```
示例代码,button发生单击事件时会创建字符串数据data并存进intenbt中,由intent带到SecondActivity中

通过getIntent()方法获取Intent,然后通过getStringExtra()方法获取数据
```
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=getIntent();
        String data=intent.getStringExtra("extra_data");
        Toast.makeText(SecondActivity.this, data, Toast.LENGTH_SHORT).show();
        Log.d("SecondActivity",data);
    }
});
```
示例代码,button发生单击事件时,获取intent中的数据,并通知用户

## activity的生命周期

### 返回栈
android使用Task来管理activity,一个Task就是一组存放在栈中的activity的集合,这个栈被称为返回栈(Back Stack)


### 活动状态
1. 运行状态: 当一个activity处于栈顶时,就是运行状态,即用户正在交互的activity
2. 暂停状态: 当activity不在处于栈顶但是仍然可见时,就是暂停状态,例如被弹窗类activity覆盖,但是依旧可以看到弹窗之外的内容,系统一般不会轻易回收处于暂停状态的activity
3. 停止状态: 当该activity完全不可见时,即处于这种状态,虽然后台还会保留相应的状态及成员变量,但是存在被系统回收的风险
4. 销毁状态: 当一个activity从返回栈弹出后,就进去这种状态,系统会最先回收

### 生命周期
1. onCreate(): 活动被创建时调用,在该方法中完成初始化操作
2. onStart(): 活动由不可见变为可见时调用
3. onResume(): 活动进入栈顶时,即要和用户进行交互时调用
4. onPause(): 活动退出栈顶时使用,即用户要与另一个activity进行交互
5. onStop(): 活动处完全不可见时调用,即被另一个activity完全覆盖
6. onDestroy(): 活动被销毁之前调用
7. onRestart(): 活动由停止状态变为运行状态时调用

### 验证生命周期
通过在不同生命周期对应方法下添加日志记录来追踪一个activity的生命周期
```
@Override
protected void onStart(){
    super.onStart();
    Log.d(TAG,"onStart");
}

@Override
protected void onResume() {
    super.onResume();
    Log.d(TAG,"onResume");
}

...
```

## activity的启动模式
在AndroidManifest.xml中的\<activity>标签中设置android:launchMode属性可以改变启动模式
### standard
默认启动模式,在该模式下的activity,每次被访问时无论返回栈中是否存在都会创建一个新的该activity的实例,并压入返回栈

### singleTop
该模式下的activity若处于栈顶,则再次被访问时不会创建新的实例,但是不处于栈顶时被访问依旧会创建新实例

### singleTask
该模式下的activity在栈中,被访问时会直接移动到栈顶,并弹出其上所有的activity,不在栈时才会新建实例

### singleInstance
被声明为singleInstance的activity会被单独放进一个返回栈,无论被那个应用访问时会都会进入该栈,以此解决共享问题




