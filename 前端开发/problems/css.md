# CSS
## 什么是盒模型？
可以认为每个html标签都是一个方块，然后这个方块又包着几个小方块，如同盒子一层层的包裹着，这就是所谓的盒模型。

### IE盒模型和W3C标准盒模型
1. W3C 标准盒模型：
属性width,height只包含内容content，不包含border和padding。
2. IE 盒模型：
属性width,height包含border和padding，指的是content+padding+border。

### 块级盒子（Block box） 和 内联盒子（Inline box）
一个被定义成块级的（block）盒子会表现出以下行为:

- 盒子会在内联的方向上扩展并占据父容器在该方向上的所有可用空间，在绝大数情况下意味着盒子会和父容器一样宽
- 每个盒子都会换行
- width 和 height 属性可以发挥作用
- 内边距（padding）, 外边距（margin） 和 边框（border） 会将其他元素从当前盒子周围“推开”

除非特殊指定，诸如标题(/<h1/>等)和段落(/<p/>)默认情况下都是块级的盒子。
  
如果一个盒子对外显示为 inline，那么他的行为如下:

- 盒子不会产生换行。
-  width 和 height 属性将不起作用。
- 垂直方向的内边距、外边距以及边框会被应用但是不会把其他处于 inline 状态的盒子推开。
- 水平方向的内边距、外边距以及边框会被应用且会把其他处于 inline 状态的盒子推开。

用做链接的 <a> 元素、 <span>、 <em> 以及 <strong> 都是默认处于 inline 状态的。


## 2. 什么是flex布局？
http://www.ruanyifeng.com/blog/2015/07/flex-grammar.html
## 3. css有哪些常用的单位？
https://juejin.im/post/594589fc8d6d81cc72e1ca66
## 4. 什么是css选择器？
https://segmentfault.com/a/1190000007815822
## 5. 简述选择器优先级？
https://juejin.im/post/5be3d07be51d457d4932b043
## 6. 什么是BFC?
https://juejin.im/post/5cee1b38e51d4556be5b39e1
## 7. 什么是CSS层叠上下文？
https://juejin.im/post/5b876f86518825431079ddd6
## 8. 常见的页面布局有哪些？
https://blog.csdn.net/VhWfR2u02Q/article/details/84076421
https://www.jianshu.com/p/3e5a9e620757
https://www.jianshu.com/p/81ef7e7094e8
## 9. 什么是响应式布局？
https://juejin.im/post/5a179a1851882510b2751071
## 10. 什么是CSS预处理和后处理？
https://www.zhihu.com/question/266405943
## 11. css3有哪些新特性？
https://www.jianshu.com/p/f988d438ee17
## 12. css display有哪些常用取值？
https://www.cnblogs.com/demonswang/p/7161313.html
## 13. 如何用css画三角形？
https://juejin.im/post/5ae902cb6fb9a07aa83e86ae#heading-13
