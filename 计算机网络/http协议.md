## http请求

由三部分组成，分别是请求行、消息报头和请求正文。

### 请求行

包含方法、URI和协议的版本。主要方法有：

 - GET     请求获取Request-URI所标识的资源
 - POST    在Request-URI所标识的资源后附加新的数据
 - HEAD    请求获取由Request-URI所标识的资源的响应消息报头
 - PUT     请求服务器存储一个资源，并用Request-URI作为其标识
 - DELETE  请求服务器删除Request-URI所标识的资源
 - TRACE   请求服务器回送收到的请求信息，主要用于测试或诊断
 - CONNECT 保留将来使用
 - OPTIONS 请求查询服务器的性能，或者查询与资源相关的选项和需求


### 消息报头

HTTP消息报头包括普通报头、请求报头、响应报头、实体报头。

**普通报头**：

包括Cache-Control、Date、Connection等内容。

**请求报头**主要内容有：

	**Accept** 指定客户端接受哪些类型的信息。 
	eg：Accept：image/gif、Accept：text/html
	**Accept-Charset** 用于指定客户端接受的字符集。
	**Accept-Language** 用于指定客户端接受的自然语言。
	**Host**（发送请求时，该报头域是必需的） 用于指定被请求资源的Internet主机和端口号。
    eg： http://www.guet.edu.cn/index.html 
    包含Host请求报头域：Host：www.guet.edu.cn（此处使用缺省端口号80）
	**User-Agent** 表示客户端的操作系统、浏览器和其它属性。

**响应报头**主要状态有：

	**Location**
	Location响应报头域用于重定向接受者到一个新的位置。Location响应报头域常用在更换域名的时候。
	**Server**
	Server响应报头域包含了服务器用来处理请求的软件信息。与User-Agent请求报头域是相对应的。例如：
	Server：Apache-Coyote/1.1

**实体报头**

请求和响应消息都可以传送一个实体。实体报头定义了关于实体正文和请求所标识的资源的元信息。

常用的实体报头：

	Content-Encoding
	Content-Language
	Content-Length
	Content-Type
	Content-Type
	Last-Modified


- 请求正文

略。

## http响应

由三部分组成，分别是状态行、消息报头和响应正文。

### 状态行

状态行格式如下：

HTTP-Version Status-Code Reason-Phrase CRLF

其中Reason-Phrase表示状态代码的文本描述。

	- 1xx：指示信息--表示请求已接收，继续处理
	- 2xx：成功--表示请求已被成功接收、理解、接受
	- 3xx：重定向--要完成请求必须进行更进一步的操作
	- 4xx：客户端错误--请求有语法错误或请求无法实现
	- 5xx：服务器端错误--服务器未能实现合法的请求

常见状态码：

	- 200 OK
	- 400 Bad Request 
	- 401 Unauthorized 
	- 403 Forbidden
	- 404 Not Found
	- 500 Internal Server Error
	- 503 Server Unavailable

### 消息报头

如上所述。

### 响应正文

略。















