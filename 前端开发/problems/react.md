https://juejin.im/post/5cd9752f6fb9a03247157b6d

## 为什么选择使用框架而不是原生?
- 组件化: 其中以 React 的组件化最为彻底,甚至可以到函数级别的原子组件,高度的组件化可以是我们的工程易于维护、易于组合拓展。
- 天然分层: JQuery 时代的代码大部分情况下是面条代码,耦合严重,现代框架不管是 MVC、MVP还是MVVM 模式都能帮助我们进行分层，代码解耦更易于读写。
- 生态: 现在主流前端框架都自带生态,不管是数据流管理架构还是 UI 库都有成熟的解决方案。
- 开发效率: 现代前端框架都默认自动更新DOM,而非我们手动操作,解放了开发者的手动DOM成本,提高开发效率,从根本上解决了UI 与状态同步问题.

## 虚拟DOM的优劣如何?
优点:
- 保证性能下限: 虚拟DOM可以经过diff找出最小差异,然后批量进行patch,这种操作虽然比不上手动优化,但是比起粗暴的DOM操作性能要好很多,因此虚拟DOM可以保证性能下限
- 无需手动操作DOM: 虚拟DOM的diff和patch都是在一次更新中自动进行的,我们无需手动操作DOM,极大提高开发效率
- 跨平台: 虚拟DOM本质上是JavaScript对象,而DOM与平台强相关,相比之下虚拟DOM可以进行更方便地跨平台操作,例如服务器渲染、移动端开发等等

缺点:
- 无法进行极致优化: 在一些性能要求极高的应用中虚拟DOM无法进行针对性的极致优化,比如VScode采用直接手动操作DOM的方式进行极端的性能优化

## 虚拟 DOM 原理?
https://github.com/Advanced-Interview-Question/front-end-interview/blob/master/docs/guide/virtualDom.md

## React 生命周期？
https://juejin.im/post/5df648836fb9a016526eba01
https://www.jianshu.com/p/514fe21b9914

## React的请求应该放在哪个生命周期中?
由于JavaScript中异步事件的性质，当您启动API调用时，浏览器会在此期间返回执行其他工作。当React渲染一个组件时，它不会等待componentWillMount它完成任何事情 - React继续前进并继续render,没有办法“暂停”渲染以等待数据到达。

而且在componentWillMount请求会有一系列潜在的问题,首先,在服务器渲染时,如果在 componentWillMount 里获取数据，fetch data会执行两次，一次在服务端一次在客户端，这造成了多余的请求,其次,在React 16进行React Fiber重写后,componentWillMount可能在一次渲染中多次调用.

目前官方推荐的异步请求是在componentDidmount中进行.

## setState到底是异步还是同步?
setState只在合成事件和钩子函数中是“异步”的，在原生事件和setTimeout 中都是同步的。

setState 的“异步”并不是说内部由异步代码实现，其实本身执行的过程和代码都是同步的，只是合成事件和钩子函数的调用顺序在更新之前，导致在合成事件和钩子函数中没法立马拿到更新后的值，形成了所谓的“异步”，当然可以通过第二个参数 setState(partialState, callback) 中的callback拿到更新后的结果。

setState 的批量更新优化也是建立在“异步”（合成事件、钩子函数）之上的，在原生事件和setTimeout 中不会批量更新，在“异步”中如果对同一个值进行多次setState，setState的批量更新策略会对其进行覆盖，取最后一次的执行，如果是同时setState多个不同的值，在更新时会对其进行合并批量更新。

## React组件通信如何实现?
- 父组件向子组件通讯: 父组件可以向子组件通过传 props 的方式，向子组件进行通讯
- 子组件向父组件通讯: props+回调的方式,父组件向子组件传递props进行通讯，此props为作用域为父组件自身的函数，子组件调用该函数，将子组件想要传递的信息，作为参数，传递到父组件的作用域中
- 兄弟组件通信: 找到这两个兄弟节点共同的父节点,结合上面两种方式由父节点转发信息进行通信
- 跨层级通信: Context设计目的是为了共享那些对于一个组件树而言是“全局”的数据，例如当前认证的用户、主题或首选语言,对于跨越多层的全局数据通过Context通信再适合不过
- 发布订阅模式: 发布者发布事件，订阅者监听事件并做出反应,我们可以通过引入event模块进行通信
- 全局状态管理工具: 借助Redux或者Mobx等全局状态管理工具进行通信,这种工具会维护一个全局状态中心Store,并根据不同的事件产生新的状态

## React如何进行组件/逻辑复用?
https://github.com/Advanced-Interview-Question/front-end-interview/blob/master/docs/guide/abstract.md

## 什么是 mixin、HOC、Hook?
https://juejin.im/post/5cad39b3f265da03502b1c0a#heading-5

## 什么是 render props?
https://zhuanlan.zhihu.com/p/31267131

## 什么是Fiber?
https://juejin.im/post/5ab7b3a2f265da2378403e57

## 什么是Time Slicing和suspense API?
https://auth0.com/blog/time-slice-suspense-react16/

## 什么是mobx?
https://juejin.im/post/5a7fd72c5188257a766324ae

## 什么是react-saga和thunk?
https://github.com/redux-saga/redux-saga
https://github.com/reduxjs/redux-thunk

## 什么是受控和非受控组件？
https://juejin.im/post/5eba071d6fb9a0432f0ffc2e

