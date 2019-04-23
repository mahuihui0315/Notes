# Android UI
UI(User Interface)，android中的所有组件都是继承于View或者ViewGroup

View是绘制在屏幕上的用户能与之交互的一个对象，而ViewGroup继承于view是一个用于存放其他View（和ViewGroup）对象的布局容器

创建UI布局的方式有两种， 自己在Java里写代码或者通过XML定义布局，后者显得更加方便和容易理解，也是我们最常用的手段。另外我们一般很少直接用View和ViewGroup来写布局，更多的 时候使用它们的子类控件或容器来构建布局

## Android Layout
android中有六大布局，分别是
+ LinearLayout：线性布局
+ RelativeLayout：相对布局
+ TableLayout：表格布局
+ FrameLayout：帧布局
+ AbsoluteLayout：绝对布局
+ GirdLayout：网格布局

## LinearLayout

