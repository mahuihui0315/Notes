# Android SQLite
Android提供了三种用于数据持久化的方法,分别是
+ 文件存储
+ SharedPreferences存储
+ 数据库存储

## 文件存储

### 存储数据
通过Content类的openFileOutput()方法,可以将数据存储到指定文件中

openFileOutput()接收两个参数:
+ 文件名
+ 文件的操作模式   
   1. MODE_PRIVATE: 文件同名时覆盖数据
   2. MODE_APPEND: 文件同名时追加数据

示例代码: 将传入的字符串数据存储到data文件中
```
public void saveEdit(String inputText){
    FileOutputStream outputStream=null;
    BufferedWriter writer=null;
    try{
        outputStream=openFileOutput("data", Context.MODE_PRIVATE);
        writer=new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write(inputText);
    }catch(IOException e){
        e.printStackTrace();
    }finally{
        try{
            if (writer!=null){
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
```
### 读取数据
通过Content类的openFileInput()方法,可以从指定文件中读取数据

openFileIutput只接受一个参数: 要读取的文件的名字

示例代码: 从data文件中读取字符串数据
```
public String loadEdit(){
    FileInputStream inputStream=null;
    BufferedReader reader=null;
    StringBuilder content=new StringBuilder();
    try{
        inputStream=openFileInput("data");
        reader=new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line=reader.readLine())!=null)
            content.append(line);
    }catch(IOException e){
        e.printStackTrace();
    }finally{
        if (reader!=null){
            try {
                reader.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    return content.toString();
}
```

## SharedPreferences存储
SharedPreferences使用键值对的方式,将数据存储到xml文件中

### 存储数据
1. 获取SharedPreferences对象
2. 通过SharedPreferences对象的edit()方法获取Editor对象
3. 通过不同的put方法添加对应的数据
4. 调用apply()方法提交数据

示例代码: 
```
SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
SharedPreferences.Editor editor=preferences.edit();
editor.putBoolean("remember_password",true);
editor.putString("account",account);
editor.putString("password",password);
```

### 读取数据
1. 获取SharedPreferences对象
2. 通过不同的get方法获取对应的数据

示例代码:
```
String account=getString("account","");
String password=getString("password","");
```

## SQLite数据库存储
SQLite时android内置的轻量级关系型数据库, 运算速度很快, 占用资源少, 因此很适合移动设备使用

### 创建及升级数据库
android提供了SQLiteOpenHelper的类用于创建和升级数据库, 需要自定义类并继承该类

示例代码:
```
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK="create table book("
            + "id integer primary key autoincrement,"
            + "author text,"
            + "prices real, "
            + "pages integer,"
            + "name text )";

    public static final String CREATE_CATEGORY="create table category("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code integer)";
    private Context context;

    public MyDatabaseHelper(Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast toast=Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText("Create succeeded");
        toast.show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists book");
        db.execSQL("drop table if exists category");
        onCreate(db);
    }
}
```