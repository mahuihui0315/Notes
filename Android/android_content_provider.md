# Android Content Provider
内容提供器(Content Provider)主要用于在不同的应用之间实现数据共享的功能,
它提供了一整套机制,允许一个程序访问另一个程序的数据,同时保证数据的安全性.

## Android的权限机制
对于涉及到用户隐私和安全的权限,应用必须在Manifest文件中进行权限声明,而用户可以选择授予权限或者拒绝

### 权限分类
+ 普通权限: 普通权限指的是不会直接威胁到用户隐私和安全的权限,应用可以无需用户授权就使用
+ 危险权限: 该类权限必须在得到用户的授权才能使用,共有9组
   1. CALENDAR
   2. CAMERA
   3. CONTACTS
   4. LOCATION
   5. MICROPHONE
   6. PHONE
   7. SENSORS
   8. SMS
   9. STORAGE
   
## 运行时权限申请
android6.0以后危险权限使用时需要进行运行时权限申请

核心就是在运行时检测是否有所需危险权限,没有就立刻向用户申请,得到允许之后才能继续使用

下面的例子中使用了CALL_PHONE的危险权限,并在使用前申请权限
1. 编写call()方法,用于调用系统功能,拨打电话
```
public void call(){
    try{
        Intent intent=new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:xxxxxxx"));
        startActivity(intent);
    }catch(SecurityException e){
        e.printStackTrace();
    }
}
```
2. 新建button类型的view绑定点击事件,并在onClick()方法校验是否具有CALL_PHONE权限,没有就申请,有就直接调用call()
```
@Override
public void onClick(View v) {
    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!=
            PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }else{
        call();
    }
}
```
3. 重写onRequestPermissionResult()方法: requCode是对该权限就行唯一表示的请求码,需要申请的权限则放到String[]中
```
@Override
public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
    switch(requestCode){
        case 1:
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                call();
            }else{
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
            break;
        default:
            break;
    }
}
```

## 访问其他程序的数据
下面的示例代码中读取了手机联系人信息并显示出来

1. 布局文件中添加ListView组件用于显示联系人信息,并在活动中获取实例
```
<ListView
        android:id="@+id/contacts_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></ListView>
```

```
ListView contacts=findViewById(R.id.contacts_list);
//ListView的适配器
ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contactsList);
contacts.setAdapter(adapter);
```
2. 编写readContacts()方法,用于读取手机联系人
```
private void readContacts(){
    Cursor cursor=null;
    try{
        //查询联系人中的所有数据,并存放到cursor对象中
        cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);
        if (cursor!=null){
            while(cursor.moveToNext()){
                //从cursor中读取联系人姓名及电话
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactsList.add(name+"\n"+number);
            }
            //更新ListView组件
            adapter.notifyDataSetChanged();
        }
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        //释放资源
        if (cursor!=null){
            cursor.close();
        }
    }
}
```
3. 判断应用是否具有相应权限,没有就申请,有就调用readContacts()
```
if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!=
        PackageManager.PERMISSION_GRANTED){
    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
}else{
    readContacts();
}
```
4. 重写onRequestPermissionResult方法
```
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch(requestCode){
        case 1:
            if (grantResults.length!=0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                readContacts();
            }else{
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
            break;
        default:
            break;
    }
}
```
5. 在Manifest文件中声明权限申请
```
<uses-permission android:name="android.permission.READ_CONTACTS"/>
```

## 自定义内容提供器
1. 新建一个类继承ContentProvider

示例代码:
```
public class DatabaseProvider extends ContentProvider {
    public DatabaseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return -1;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return -1;
    }
}
```
2. 使用UriMatcher实现匹配内容URI的功能

UriMatcher提供了一个addUri()方法,接收三个参数: authority,path和一个自定义代码, 当调用
UriMatcher的match()方法传入一个URI时, 会返回一个匹配的自定义代码, 通过这种方式可以控制
外部应用访问的资源, 只有被提供自定义代码的资源才会被访问

示例代码:
```
private static final int BOOK_DIR=0;
private static final int BOOK_ITEM=1;
private static final int CATEGORY_DIR=2;
private static final int CATEGORY_ITEM=3;
private static final String AUTHORITY="com.mhh.sqlitedemo.provider";
private static UriMatcher uriMatcher;

static{
    uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
    uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
    uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
    uriMatcher.addURI(AUTHORITY,"category/#",CATEGORY_ITEM);
}
```
3. 以数据查询为例, 只有匹配的URI才能访问数据

示例代码:
```
@Override
public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    database=databaseHelper.getReadableDatabase();
    Cursor cursor=null;
    switch(uriMatcher.match(uri)){
        case BOOK_DIR:
            cursor=database.query("book",projection,selection,selectionArgs,null,null,sortOrder);
            break;
        case BOOK_ITEM:
            cursor=database.query("book",projection,"id=?",new String[]{uri.getPathSegments().get(1)},
                    null,null,sortOrder);
            break;
        case CATEGORY_DIR:
            cursor=database.query("category",projection,selection,selectionArgs,null,null,sortOrder);
            break;
        case CATEGORY_ITEM:
            cursor=database.query("category",projection,"id=?",new String[]{uri.getPathSegments().get(1)},
                    null,null,sortOrder);
            break;
        default:
            break;
    }
    return cursor;
}
```

