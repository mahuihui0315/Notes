# Android UI
UI(User Interface)，android中的所有组件都是继承于View或者ViewGroup

View是绘制在屏幕上的用户能与之交互的一个对象，而ViewGroup继承于view是一个用于存放其他View（和ViewGroup）对象的布局容器

创建UI布局的方式有两种， 自己在Java里写代码或者通过XML定义布局，后者显得更加方便和容易理解，也是我们最常用的手段。另外我们一般很少直接用View和ViewGroup来写布局，更多的 时候使用它们的子类控件或容器来构建布局

## View
View是android的基本视图，所有控件和布局都是直接或者间接由View派生而来

### View类的常用属性
+ android:id：设置标号
+ android:background：背景颜色或者图片
> 例如：使用图片作为背景 android:background="@mipmap/..."
+ android:padding：设置组件的内边距（上下左右都一致）
   1. paddingLeft：单独设置左内边距
   2. paddingTop：单独设置上内边距
   3. PaddingRight：单独设置右内边距
   4. paddingBottom：单独设置下内边距

## ViewGroup
View的子类用于管理一组View，为抽象类

### 依赖类

#### ViewGroup.LayoutParams
用于控制组件的大小

常用属性：
+ android:layout_width：设置组件宽度
   1. warp_content：按照组件大小显示
   2. fill_parent：将组件横向拉伸以适应父容器
   3. match_parent：将组件横向拉伸以适应父容器
+ android:layout_height：设置组件高度
   1. warp_content：按照组件大小显示
   2. fill_parent：将组件纵向拉伸以适应父容器
   3. match_parent：将组件纵向拉伸以适应父容器
#### ViewGroup.MarginLayoutParams
用于控制组件的外边距

常用属性：
+ android:layout_marginLeft:设置左外边距
+ android:layout_marginTop:设置上外边距
+ android:layout_marginRight:设置右外边距
+ android:layout_marginBottom:设置下外边距

## UI操作
通过不同的方式操作UI

### xml方式
1. 创建xml布局文件
> 例：layout文件夹下的activity_main.xml就是一个xml格式的布局文件，文件的命名可以改变，知道符合命名规则即可

2. 在Activity中使用布局文件
> setContentView(R.layout.activity_main);

### java代码方式

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //新建帧布局对象
    FrameLayout frameLayout=new FrameLayout(this);
    //设置背景图片
    frameLayout.setBackgroundResource(R.mipmap.background);
    //引用帧布局对象
    setContentView(frameLayout);
	
    //新建文本视图对象，并设置大小颜色
    TextView textView=new TextView(this);
    textView.setText(R.string.getResource);
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
    textView.setTextColor(Color.rgb(17,85,114));
    //文本视图视图对象居中
    FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    params.gravity= Gravity.CENTER;
    textView.setLayoutParams(params);

    //为创建的文本视图绑定点击函数
    textView.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(MainActivity.this).setTitle("系统提示").setMessage("链接有风险，是否继续？")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("UI_Test2","获取链接成功，即将进入");
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("UI_Test2","链接获取取消，即将退出");
                    finish();
                }
            }).show();
        }
    });
    //将文本视图组件添加到帧布局中
    frameLayout.addView(textView);
}
```

### java代码和xml混合的方式
xml文件中定义布局样式
```
<GridLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/layout"
        android:orientation="horizontal"
        android:rowCount="3"
        android:columnCount="4"
        android:padding="16sp"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
</GridLayout>
```
java文件中引用该布局并添加view
```
private ImageView[] images=new ImageView[12];
private int[] imagePath=new int[]{
        R.mipmap.jill,R.mipmap.jill,R.mipmap.jill,R.mipmap.jill,
        R.mipmap.jill,R.mipmap.jill,R.mipmap.jill,R.mipmap.jill,
        R.mipmap.jill,R.mipmap.jill,R.mipmap.jill,R.mipmap.jill,};

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gridlayout);
    GridLayout layout=findViewById(R.id.layout);
    for (int i=0;i<imagePath.length;i++){
        images[i]=new ImageView(MainActivity.this);
        images[i].setImageResource(imagePath[i]);
        images[i].setPadding(2,2,2,2);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(116,100);
        images[i].setLayoutParams(params);
        layout.addView(images[i]);
    }
}
```

## Android Layout
android中有六大布局，分别是
+ LinearLayout：线性布局
+ RelativeLayout：相对布局
+ TableLayout：表格布局
+ FrameLayout：帧布局
+ AbsoluteLayout：绝对布局
+ GirdLayout：网格布局

## LinearLayout

