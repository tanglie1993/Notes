## 什么是用户和组，他们两者有什么关系
Linux内核（2.6.x）已经可以支持到2^32 - 1 个标识符。

- 用户分类
  - 普通用户： 1 - 65535，拥有对系统的最高管理权限
  - 系统用户： 1-499（CentOS 6） ;  1-999（CentOS 7）
  - 登录用户： 500 - 60000（CentOS 6） ； 1000 - 60000 （CentOS 7），只能对自己目录下的文件进行访问和修改，具有登录系统的权限
  
### Q: What's the difference between a normal user and a system user?

A: When you are creating an account to run a daemon, service, or other system software, rather than an account for interactive use.

Technically, it makes no difference, but in the real world it turns out there are long term benefits in keeping user and software accounts in separate parts of the numeric space.

- 用户组分类
  - 普通用户组:可以加入多个用户
  - 系统组:一般加入一些系统用户
  - 私有组(也称基本组):当创建用户时,如果没有为其指明所属组,则就为其定义一个私有的用户组,起名称与用户名同名.
注:私有组可以变成普通用户组,当把其他用户加入到该组中,则其就变成了普通组

## 什么是文件权限，如何修改文件权限？

ls

 -l中显示的内容如下：

-rwxrw-r‐-1 root root 1213 Feb 2 09:39 abc

- 10个字符确定不同用户能对文件干什么

- 第一个字符代表文件（-）、目录（d），链接（l）

- 其余字符每3个一组（rwx），读（r）、写（w）、执行（x）

- 第一组rwx：文件所有者的权限是读、写和执行

- 第二组rw-：与文件所有者同一组的用户的权限是读、写但不能执行

- 第三组r--：不与文件所有者同组的其他用户的权限是读不能写和执行

也可用数字表示为：r=4，w=2，x=1  因此rwx=4+2+1=7

- 1 表示连接的文件数

- root 表示用户

- root表示用户所在的组

- 1213 表示文件大小（字节）

- Feb 2 09:39 表示最后修改日期

- abc 表示文件名

 

改变权限的命令

chmod 改变文件或目录的权限

chmod 755 abc：赋予abc权限rwxr-xr-x

chmod u=rwx，g=rx，o=rx abc：同上u=用户权限，g=组权限，o=不同组其他用户权限

chmod u-x，g+w abc：给abc去除用户执行的权限，增加组写的权限

chmod a+r abc：给所有用户添加读的权限

## 什么是shell

Shell 是一个用 C 语言编写的程序，它是用户使用 Linux 的桥梁。Shell 既是一种命令语言，又是一种程序设计语言。

Shell 是指一种应用程序，这个应用程序提供了一个界面，用户通过这个界面访问操作系统内核的服务。

## shell下怎样查看一个环境变量

-bash-3.00# env

如果只想看指定的变量设置，如路径PATH的设置，可以用 "echo $PATH"或 “ env | grep PATH"或” env | grep -i path"来查询。前面的适合知道全名的，后面2种适合只知道部分字段或者部分关键字母（甚至不确定字符大小写）的。

## 如何查看文件内容

- cat 由第一行开始显示内容，并将所有内容输出
 
- tac 从最后一行倒序显示内容，并将所有内容输出
 
- more 根据窗口大小，一页一页的现实文件内容
 
- less 和more类似，但其优点可以往前翻页，而且进行可以搜索字符
 
- head 只显示头几行
 
- tail 只显示最后几行

## 阐述sudo，su指令

通过su可以在用户之间切换，如果超级权限用户root向普通或虚拟用户切换不需要密码，而普通用户切换到其它任何用户都需要密码验证。

由于 su 对切换到超级权限用户root后，权限的无限制性，所以su并不能担任多个管理员所管理的系统。如果用su 来切换到超级用户来管理系统，也不能明确哪些工作是由哪个管理员进行的操作。特别是对于服务器的管理有多人参与管理时，最好是针对每个管理员的技术特长和管理范围，并且有针对性的下放给权限，并且约定其使用哪些工具来完成与其相关的工作，这时我们就有必要用到 sudo。

通过sudo，我们能把某些超级权限有针对性的下放，并且不需要普通用户知道root密码，所以sudo 相对于权限无限制性的su来说，还是比较安全的，所以sudo 也能被称为受限制的su ；另外sudo 是需要授权许可的，所以也被称为授权许可的su；

sudo 执行命令的流程是当前用户切换到root（或其它指定切换到的用户），然后以root（或其它指定的切换到的用户）身份执行命令，执行完成后，直接退回到当前用户；而这些的前提是要通过sudo的配置文件/etc/sudoers来进行授权。



