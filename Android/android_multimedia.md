# Android Multimedia

## Notification
当应用希望向用户通知一些消息, 而该应用又不在前台运行时, 可以使用通知来完成任务

### 基本用法
1. 创建一个NotificationManager对象, 用于管理通知
```
NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
```
2. 创建NotificationChannel对象(Android8.0及以上)用于对通知进行分类, 设置优先级, 开关等
```
NotificationChannel channel= null;
//android O,即android 8.0以上版本时需要channel来使用NotificationCompat.Builder()
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
    String channelId="channel_1";
    String channelName="Notification";
    manager.createNotificationChannel(new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_HIGH));
}
```
3. 设置点击通知时跳转的活动
```
Intent intent=new Intent(this,NotificationActivity.class);
PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
```
4. 创建Notifica对象
```
Notification notification=new NotificationCompat.Builder(this,"channel_1")
        .setContentTitle("This is content title")
        .setContentText("This is content text")
        .setWhen(System.currentTimeMillis())
        .setSmallIcon(R.mipmap.ic_launcher)
        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build();
```
5. 调用manager的notify()方法显示通知
```
manager.notify(1,notification);
```

### 进阶用法

+ setSound(): 设置通知到来的提示音
```
.setSound(Uri.fromFile(new File("...")));
```

+ setVibrate(): 设置通知到来的震动效果, 通过long[]内的时间参数(毫秒)设置, 第一个参数为静止时长,
第二个为震动时长, 以此类推. 不过使用震动需要提前获取权限
```
.setVibrate(new long[]{0,1000,1000,1000})
```
```
<uses-permission android:name"android.permission.VIBRATE"/>
```

+ setLights(): 设置通知的提示灯, 第一个参数为颜色, 第二个参数为亮起时长, 第三个参数为熄灭时长
```
.setLights(Color.BLUE,1000,1000)
```