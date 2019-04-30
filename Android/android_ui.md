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
## 常用控件

### TextView
用于在界面上显示一段文本

#### 常用属性
+  gravity: 对齐方式
   1. top
   2. bottom
   3. left
   4. right
+ setSize: 设置文本大小
+ setColor: 设置文本颜色

### Button
在界面上点加一个按键

#### 常用属性
+ textAllCaps: 设置字母的大小写
   1. true: 全部大写(默认)
   2. false: 按照文本原来的格式

### EditText
在界面上显示一个文本输入框

#### 常用属性
+ hint: 在文本框中显示提示文字
+ maxLines: 输入框的最大行数

### ImageView
在界面上显示图片

#### 常用属性
+ src: 设置显示图片的路径   

## Android Layout
android布局管理器，继承自ViewGroup，用于管理容器中的控件，并自动设配不同的手机屏幕

android中有六大布局，分别是
+ RelativeLayout：相对布局
+ LinearLayout：线性布局
+ TableLayout：表格布局
+ FrameLayout：帧布局
+ AbsoluteLayout：绝对布局（基本被弃用，由FrameLayout代替）
+ GirdLayout：网格布局

### RelativeLayout
相对布局管理器，容器内子组件的位置总是相对兄弟组件、父容器来决定的。

常用属性：
+ layout_centerInParent：定义该组件位于父容器的中间位置
+ layout_above：定义该组件位于给出ID组件的上方
+ layout_below：定义该组件位于给出ID组件的下方
+ layout_toLeftOf：定义该组件位于给出ID组件的左方
+ layout_toRightOf：定义该组件位于给出ID组件的右方
+ layout_alignTop：控制该组件与给出ID组件的上边界对齐
+ layout_alignBottom：控制该组件与给出ID组件的下边界对齐
+ layout_alignLeft：控制该组件与给出ID组件的左边界对齐
+ layout_alignRight：控制该组件与给出ID组件的右边界对齐

### LinearLayout
线性布局管理器，会将容器里的组件一个挨着一个地排列起来

常用属性：
+ orientation：控制组件排列方式
   1. horizontal：横向
   2. vertical：竖向
+ gravity：控制包含的子元素的对齐方式
+ layout_gravity：控制该元素在父容器的对齐方式

### TableLayout
表格布局管理器，继承自LinearLayout类，因此本质依旧是线性布局

通过添加TableRow标签来控制行数，一个TableRow就是一行，并在标签内添加组件来控制列数


### FrameLayout
帧布局管理器，直接继承自ViewGroup，效果是将组件一个个叠加起来

### AbsoluteLayout
...

### GridLayout
网格布局管理器，作用类似于HTML中的table标签

常用属性：
+ rowCount：设置网格的行数量
+ columnCount：设置网格的列数量
+ layout_rowSpan：设置该组件纵向跨越几行
+ layout_columnSpan：设置该组件横向跨越几行

## 自定义控件
当系统所提供的控件不满足需求的时候就可以通过扩展现有的控件来自定义新的控件

下面的例子自定义了一个标题栏控件
1. 新建title.xml布局文件,包含两个button和一个text
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="match_parent"
              android:layout_height="match_parent">

    <Button
            android:id="@+id/title_back"
            android:layout_gravity="center|top"
            android:layout_margin="5dp"
            android:text="back"
            android:textColor="#fff"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>

    <TextView
            android:id="@+id/title_text"
            android:layout_gravity="center|top"
            android:gravity="center"
            android:layout_marginTop="5dp"
            android:layout_weight="1"
            android:text="Title Text"
            android:textColor="@color/colorPrimaryDark"
            android:textSize="24sp"
            android:layout_width="0dp"
            android:layout_height="wrap_content"/>

    <Button
            android:id="@+id/title_edit"
            android:layout_gravity="center|top"
            android:layout_margin="5sp"
            android:text="edit"
            android:textColor="#fff"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>

</LinearLayout>
```
2. 新建TitleLayout类,继承LinearLayout
```
public class TitleLayout extends LinearLayout implements View.OnClickListener{

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        ...
    }
}
```
3. 在TitleLayout中为button绑定点击事件
```
public class TitleLayout extends LinearLayout implements View.OnClickListener{

    public TitleLayout(Context context, AttributeSet attrs) {
        ...
        Button titleBack=findViewById(R.id.title_back);
        Button titleEdit=findViewById(R.id.title_edit);

        titleBack.setOnClickListener(this);
        titleEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                ((Activity)getContext()).finish();
                break;
            case R.id.title_edit:
                Toast.makeText(getContext(), "You clicked edit button", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
```
4. 在activity_main.xml文件中添加自定义组件TitleLayout
```
<com.mhh.customview.TitleLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
5. 隐藏系统自带的标题栏
```
ActionBar actionBar=getSupportActionBar();
if (actionBar!=null)
    actionBar.hide();
```

## 重要控件ListView
ListView允许用户通过上下滑动的方式将数据从屏幕外滚动到屏幕内,许多场合都需要使用到该控件

