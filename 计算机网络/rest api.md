- 协议：

使用HTTPS协议

- 域名：

尽量将API部署在专用域名之下：
https://api.example.com
如果确定API很简单，不会有进一步扩展，可以考虑放在主域名下。
https://example.org/api/

- 版本：

将版本号信息放入 URL：
https://api.example.com/v1/
也可放入HTTP头信息中。

- 路径：

每一个网址代表一种资源：
https://api.example.com/v1/zoos
https://api.example.com/v1/animals
https://api.example.com/v1/employees

- 过滤信息

如果记录数量很多，服务器不可能都将它们返回给用户。API应该提供参数，过滤返回结果。

?limit=10：指定返回记录的数量。

?offset=10：指定返回记录的开始位置。

?page=2&per_page=100：指定第几页，以及每页的记录数。

?sortby=name&order=asc：指定返回结果按照哪个属性排序，以及排序顺序。

?animal_type_id=1：指定筛选条件。

