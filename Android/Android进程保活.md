##Android 进程优先级分类
- 前台进程
- 可见进程
- 服务进程
- 后台进程
- 空进程

优先级通过进程的adj值来反映，它是linux内核分配给每个系统进程的一个值。adj值定义在com.android.server.am.ProcessList类中。
oom_adj的值越小，进程的优先级越高，普通进程oom_adj值是大于等于0的，而系统进程oom_adj的值是小于0的。

##进程保活方案
- 开启一个像素的窗口
- 前台服务
- 添加Manifest文件属性值为android:persistent="true"
- 相互唤醒
- 监听系统广播
- 使用JobScheduler唤醒
- 双进程守护
- 在service的onstart方法里返回 START_STICKY
 - START_NOT_STICKY：表示当Service运行的进程被Android系统强制杀掉之后，不会重新创建该Service。
 - START_STICKY：表示Service运行的进程被Android系统强制杀掉之后，Android系统会将该Service依然设置为started状态，但是不再保存onStartCommand方法传入的intent对象。然后Android系统会尝试再次重新创建该Service，并执行onStartCommand回调方法，这时onStartCommand回调方法的Intent参数为null。
 - START_REDELIVER_INTENT：同上，但Intent不为null。
