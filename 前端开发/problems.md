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

## 6.xhtml和html有什么区别?
XHTML可兼容各大浏览器、手机以及PDA，并且浏览器也能快速正确地编译网页
XHTML 元素必须被正确地嵌套，闭合，区分大小写，文档必须拥有根元素

## 7. WEB标准以及W3C标准是什么?
https://www.kancloud.cn/z591102/interview/916463

## 8. HTML全局属性(global attribute)有哪些?
class:为元素设置类标识
data-*: 为元素增加自定义属性
draggable: 设置元素是否可拖拽
id: 元素id，文档内唯一
lang: 元素内容的的语言
style: 行内css样式
title: 元素相关的建议信息

## 9. Canvas和SVG有什么区别？
svg绘制出来的每一个图形的元素都是独立的DOM节点，能够方便的绑定事件或用来修改。canvas输出的是一整幅画布
svg输出的图形是矢量图形，后期可以修改参数来自由放大缩小，不会出现失真和锯齿。而canvas输出标量画布，就像一张图片一样，放大会失真或者出现锯齿

# JS
## 1. 谈谈你对原型链的理解？
https://juejin.im/post/58f94c9bb123db411953691b
https://www.cnblogs.com/yangwenbo/p/10715295.html

## 2. JS继承
https://zhuanlan.zhihu.com/p/25578222

## 3. Javascript 中的数据类型判断？
https://juejin.im/post/59c7535a6fb9a00a600f77b4

## 4. 作用域和作用域链？
https://juejin.im/post/5c8efeb1e51d45614372addd
https://juejin.im/post/5c8290455188257e5d0ec64f

## 5. 闭包
https://juejin.im/post/58832fe72f301e00697b672d

## 6. 图片懒加载、预加载
https://juejin.im/post/5b0c3b53f265da09253cbed0

## 7. this
https://juejin.im/post/596a28f6f265da6c360a2716

## 8. 函数式编程
https://juejin.im/post/5d70e25de51d453c11684cc4

## 9. 同源策略
https://juejin.im/post/58f816198d6d81005874fd97

## 10. 如何判断两个对象相等
https://juejin.im/post/598a701b6fb9a03c5b04bb14

## 11. 事件模型
https://segmentfault.com/a/1190000006934031

## 12. 事件委托（事件代理）
https://www.cnblogs.com/liugang-vip/p/5616484.html

## 13. 函数柯里化与反柯里化


# 浏览器
## 1. 介绍一下你对浏览器内核的理解？

主要分成两部分：渲染引擎(layout engineer或Rendering Engine)和JS引擎
渲染引擎：负责取得网页的内容（HTML、XML、图像等等）、整理讯息（例如加入CSS等），以及计算网页的显示方式，然后会输出至显示器或打印机。浏览器的内核的不同对于网页的语法解释会有不同，所以渲染的效果也不相同。所有网页浏览器、电子邮件客户端以及其它需要编辑、显示网络内容的应用程序都需要内核

JS引擎：解析和执行javascript来实现网页的动态效果

最开始渲染引擎和JS引擎并没有区分的很明确，后来JS引擎越来越独立，内核就倾向于只指渲染引擎

## 2.从浏览器地址栏输入url到显示页面的步骤
https://www.jianshu.com/p/9075ee83b679



