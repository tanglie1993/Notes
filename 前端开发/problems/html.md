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
纯表现的元素：basefont，big，center，font, s，strike，tt，u
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

## 10. HTML页面生命周期
https://zhuanlan.zhihu.com/p/111316766
