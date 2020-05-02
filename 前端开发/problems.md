# CSS
## 1. 什么是盒模型？
https://juejin.im/post/59ef72f5f265da4320026f76
## 2. 什么是flex布局？
http://www.ruanyifeng.com/blog/2015/07/flex-grammar.html
## 3. css有哪些常用的单位？
https://juejin.im/post/594589fc8d6d81cc72e1ca66
## 4. 什么是css选择器？
https://segmentfault.com/a/1190000007815822
## 5. 简述选择器优先级？
https://juejin.im/post/5be3d07be51d457d4932b043
## 6. 什么是BFC?
https://juejin.im/post/57.cee1b38e51d4556be5b39e1
## 7. 什么是CSS层叠上下文？
https://juejin.im/post/5b876f86518825431079ddd6
## 8. 常见的页面布局有哪些？
https://blog.csdn.net/VhWfR2u02Q/article/details/84076421
## 9. 什么是响应式布局？
https://juejin.im/post/5a179a1851882510b2751071
## 10. 什么是CSS预处理和后处理？
https://www.cnblogs.com/cyn941105/archive/2016/06/16/5590239.html
## 11. css3有哪些新特性？
https://www.jianshu.com/p/56b7302d7f7f
## 12. css display有哪些常用取值？
https://www.cnblogs.com/demonswang/p/7161313.html
## 13. 如何用css画三角形？
https://juejin.im/post/5ae902cb6fb9a07aa83e86ae#heading-13

# HTML
## 1. HTML5有哪些新特性？移除了哪些元素？

HTML5 现在已经不是 SGML 的子集，主要是关于图像，位置，存储，多任务等功能的增加
绘画 canvas
用于媒介回放的 video 和 audio 元素
本地离线存储 localStorage 长期存储数据，浏览器关闭后数据不丢失
sessionStorage 的数据在浏览器关闭后自动删除
语意化更好的内容元素，比如article、footer、header、nav、section
表单控件，calendar、date、time、email、url、search
新的技术webworker, websocket, Geolocation

移除的元素：
纯表现的元素：basefont，big，center，font, s，strike，tt，u`
对可用性产生负面影响的元素：frame，frameset，noframes

支持HTML5新标签：
IE8/IE7/IE6支持通过document.createElement方法产生的标签
可以利用这一特性让这些浏览器支持HTML5新标签
浏览器支持新标签后，还需要添加标签默认的样式

## 2.HTML5的离线储存怎么使用，工作原理能不能解释一下？

在用户没有与因特网连接时，可以正常访问站点或应用，在用户与因特网连接时，更新用户机器上的缓存文件
原理：HTML5的离线存储是基于一个新建的.appcache文件的缓存机制(不是存储技术)，通过这个文件上的解析清单离线存储资源，这些资源就会像cookie一样被存储了下来。之后当网络在处于离线状态下时，浏览器会通过被离线存储的数据进行页面展示

## 3.什么是HTML语义化？
https://juejin.im/post/5a9c8866f265da23741072bf

## 4.iframe有那些缺点？

iframe会阻塞主页面的Onload事件
搜索引擎的检索程序无法解读这种页面，不利于SEO
iframe和主页面共享连接池，而浏览器对相同域的连接有限制，所以会影响页面的并行加载
使用iframe之前需要考虑这两个缺点。如果需要使用iframe，最好是通过javascript动态给iframe添加src属性值，这样可以绕开以上两个问题

## 5.行内元素有哪些？块级元素有哪些？ 空(void)元素有那些？行内元素和块级元素有什么区别？

行内元素有：a b span img input select strong
块级元素有：div ul ol li dl dt dd h1 h2 h3 h4…p
空元素：<br> <hr> <img> <input> <link> <meta>
行内元素不可以设置宽高，不独占一行
块级元素可以设置宽高，独占一行

#浏览器




