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
