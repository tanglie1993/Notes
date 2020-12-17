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

除非特殊指定，诸如标题(< h1 >等)和段落(< p >)默认情况下都是块级的盒子。
  
如果一个盒子对外显示为 inline，那么他的行为如下:

- 盒子不会产生换行。
-  width 和 height 属性将不起作用。
- 垂直方向的内边距、外边距以及边框会被应用但是不会把其他处于 inline 状态的盒子推开。
- 水平方向的内边距、外边距以及边框会被应用且会把其他处于 inline 状态的盒子推开。

用做链接的 < a > 元素、 < span >、 < em > 以及 < strong > 都是默认处于 inline 状态的。


## 2. 什么是flex布局？
### 设置在容器上的属性
#### flex-direction
属性决定主轴的方向（即项目的排列方向）。
.box {
  flex-direction: row | row-reverse | column | column-reverse;
}
#### flex-wrap
默认情况下，项目都排在一条线（又称"轴线"）上。flex-wrap属性定义，如果一条轴线排不下，如何换行。
.box {
  flex-wrap: nowrap | wrap | wrap-reverse;
}
#### flex-flow
flex-flow属性是flex-direction属性和flex-wrap属性的简写形式，默认值为row nowrap。
.box {
  flex-flow: <flex-direction> || <flex-wrap>;
}
  
#### justify-content
justify-content属性定义了项目在主轴上的对齐方式。
.box {
  justify-content: flex-start | flex-end | center | space-between | space-around;
}

#### align-items
align-items属性定义项目在交叉轴上如何对齐。
.box {
  align-items: flex-start | flex-end | center | baseline | stretch;
}

#### align-content
align-content属性定义了多根轴线的对齐方式。如果项目只有一根轴线，该属性不起作用。
.box {
  align-content: flex-start | flex-end | center | space-between | space-around | stretch;
}

### 设置在项目上的属性
#### order
order属性定义项目的排列顺序。数值越小，排列越靠前，默认为0。

#### flex-grow
lex-grow属性定义项目的放大比例，默认为0，即如果存在剩余空间，也不放大。

#### flex-shrink
flex-shrink属性定义了项目的缩小比例，默认为1，即如果空间不足，该项目将缩小。

#### flex-basis
flex-basis属性定义了在分配多余空间之前，项目占据的主轴空间（main size）。浏览器根据这个属性，计算主轴是否有多余空间。它的默认值为auto，即项目的本来大小。

#### flex
flex属性是flex-grow, flex-shrink 和 flex-basis的简写，默认值为0 1 auto。后两个属性可选。

#### align-self
align-self属性允许单个项目有与其他项目不一样的对齐方式，可覆盖align-items属性。默认值为auto，表示继承父元素的align-items属性，如果没有父元素，则等同于stretch。
.item {
  align-self: auto | flex-start | flex-end | center | baseline | stretch;
}

## 3. css有哪些常用的单位？
### 绝对单位
px: Pixel 像素
pt: Points 磅
pc: Picas 派卡
in: Inches 英寸
mm: Millimeter 毫米
cm: Centimeter 厘米
q: Quarter millimeters 1/4毫米

### 相对单位
%: 百分比
em: Element meter 根据文档字体计算尺寸
rem: Root element meter 根据根文档（ body/html ）字体计算尺寸
ex: 文档字符“x”的高度
ch: 文档数字“0”的的宽度
vh: View height 可视范围高度
vw: View width 可视范围宽度
vmin: View min 可视范围的宽度或高度中较小的那个尺寸
vmax: View max 可视范围的宽度或高度中较大的那个尺寸

### 运算
calc: 四则运算

## 4. 什么是css选择器？
### 基本选择器
#### 通用选择器（Universal selector）
选择所有元素。
语法：* ns|* *|*
例子：* 将匹配文档的所有元素。
#### 元素选择器（Type selector）
按照给定的节点名称，选择所有匹配的元素。
语法：elementname
例子：input 匹配任何 <input> 元素。
#### 类选择器（Class selector）
按照给定的 class 属性的值，选择所有匹配的元素。
语法：.classname
例子：.index 匹配任何 class 属性中含有 "index" 类的元素。
#### ID 选择器（ID selector）
按照 id 属性选择一个与之匹配的元素。需要注意的是，一个文档中，每个 ID 属性都应当是唯一的。
语法：#idname
例子：#toc 匹配 ID 为 "toc" 的元素。
#### 属性选择器（Attribute selector）
按照给定的属性，选择所有匹配的元素。
语法：[attr] [attr=value] [attr~=value] [attr|=value] [attr^=value] [attr$=value] [attr*=value]
例子：[autoplay] 选择所有具有 autoplay 属性的元素（不论这个属性的值是什么）。
#### 分组选择器（Grouping selectors）
选择器列表（Selector list）
, 是将不同的选择器组合在一起的方法，它选择所有能被列表中的任意一个选择器选中的节点。
语法：A, B
示例：div, span 会同时匹配 <span> 元素和 <div> 元素。
  
### 组合器（Combinators）
#### 后代组合器（Descendant combinator）
 （空格）组合器选择前一个元素的后代节点。
语法：A B
例子：div span 匹配所有位于任意 <div> 元素之内的 <span> 元素。
  
#### 直接子代组合器（Child combinator）
> 组合器选择前一个元素的直接子代的节点。
语法：A > B
例子：ul > li 匹配直接嵌套在 <ul> 元素内的所有 <li> 元素。
  
#### 一般兄弟组合器（General sibling combinator）
~ 组合器选择兄弟元素，也就是说，后一个节点在前一个节点后面的任意位置，并且共享同一个父节点。
语法：A ~ B
例子：p ~ span 匹配同一父元素下，<p> 元素后的所有 <span> 元素。
  
#### 紧邻兄弟组合器（Adjacent sibling combinator）
+ 组合器选择相邻元素，即后一个元素紧跟在前一个之后，并且共享同一个父节点。
语法：A + B
例子：h2 + p 会匹配所有紧邻在 <h2> 元素后的 <p> 元素。
  
#### 列组合器（Column combinator）
|| 组合器选择属于某个表格行的节点。
语法： A || B
例子： col || td 会匹配所有 <col> 作用域内的 <td> 元素。
### 伪选择器（Pseudo）
  
#### 伪类
: 伪选择器支持按照未被包含在文档树中的状态信息来选择元素。
例子：a:visited 匹配所有曾被访问过的 <a> 元素。
  
#### 伪元素
:: 伪选择器用于表示无法用 HTML 语义表达的实体。
例子：p::first-line 匹配所有 <p> 元素的第一行。
  
## 5. 简述选择器优先级？
下面列表中，选择器类型的优先级是递增的：

1. 类型选择器（例如，h1）和伪元素（例如，::before）
2. 类选择器 (例如，.example)，属性选择器（例如，[type="radio"]）和伪类（例如，:hover）
3. ID 选择器（例如，#example）。
4. 给元素添加的内联样式 (例如，style="font-weight:bold") 总会覆盖外部样式表的任何样式 ，因此可看作是具有最高的优先级。

通配选择符（universal selector）（*）关系选择符（combinators）（+, >, ~, ' ', ||）和 否定伪类（negation pseudo-class）（:not()）对优先级没有影响。（但是，在 :not() 内部声明的选择器会影响优先级）。


## 6. 什么是BFC?
BFC对浮动定位（参见 float）与清除浮动（参见 clear）都很重要。浮动定位和清除浮动时只会应用于同一个BFC内的元素。浮动不会影响其它BFC中元素的布局，而清除浮动只能清除同一BFC中在它前面的元素的浮动。外边距折叠（Margin collapsing）也只会发生在属于同一BFC的块级元素之间。

下列方式会创建块格式化上下文：
- 根元素（<html>）
- 浮动元素（元素的 float 不是 none）
- 绝对定位元素（元素的 position 为 absolute 或 fixed）
- 行内块元素（元素的 display 为 inline-block）
- 表格单元格（元素的 display 为 table-cell，HTML表格单元格默认为该值）
- 表格标题（元素的 display 为 table-caption，HTML表格标题默认为该值）
- 匿名表格单元格元素（元素的 display 为 table、table-row、 table-row-group、table-header-group、table-footer-group（分别是HTML table、row、tbody、thead、tfoot 的默认属性）或 inline-table）
- overflow 计算值(Computed)不为 visible 的块元素
- display 值为 flow-root 的元素
- contain 值为 layout、content 或 paint 的元素
- 弹性元素（display 为 flex 或 inline-flex 元素的直接子元素）
- 网格元素（display 为 grid 或 inline-grid 元素的直接子元素）
- 多列容器（元素的 column-count 或 column-width 不为 auto，包括 column-count 为 1）
- column-span 为 all 的元素始终会创建一个新的BFC，即使该元素没有包裹在一个多列容器中（标准变更，Chrome bug）。
  
## 7. 什么是CSS层叠上下文？
在层叠上下文中，子元素同样也按照上面解释的规则进行层叠。 重要的是，其子级层叠上下文的 z-index 值只在父级中才有意义。子级层叠上下文被自动视为父级层叠上下文的一个独立单元。

文档中的层叠上下文由满足以下任意一个条件的元素形成：
- 文档根元素（<html>）；
- position 值为 absolute（绝对定位）或  relative（相对定位）且 z-index 值不为 auto 的元素；
- position 值为 fixed（固定定位）或 sticky（粘滞定位）的元素（沾滞定位适配所有移动设备上的浏览器，但老的桌面浏览器不支持）；
- flex (flexbox) 容器的子元素，且 z-index 值不为 auto；
- grid (grid) 容器的子元素，且 z-index 值不为 auto；
- opacity 属性值小于 1 的元素（参见 the specification for opacity）；
- mix-blend-mode 属性值不为 normal 的元素；
- 以下任意属性值不为 none 的元素：
 - transform
 - filter
 - perspective
 - clip-path
 - mask / mask-image / mask-border
- isolation 属性值为 isolate 的元素；
- -webkit-overflow-scrolling 属性值为 touch 的元素；
- will-change 值设定了任一属性而该属性在 non-initial 值时会创建层叠上下文的元素（参考这篇文章）；
- contain 属性值为 layout、paint 或包含它们其中之一的合成值（比如 contain: strict、contain: content）的元素。

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
