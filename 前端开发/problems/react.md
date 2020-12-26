
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
https://juejin.im/post/5dfac33fe51d455802162b75

## React 生命周期？
### 16.0前的生命周期
![lifecycle0](https://upload-images.jianshu.io/upload_images/5287253-315eac1c26082f08.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)
### 16.4+的生命周期
![lifecycle](https://upload-images.jianshu.io/upload_images/5287253-19b835e6e7802233.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

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
### Mixin
使用 ES6 class 定义的组件已经不支持 mixin 了，因为使用 mixin 的场景都可以用组合组件这种模式来做到.

#### Mixin的缺陷
- 组件与Mixin之间存在隐式依赖（Mixin经常依赖组件的特定⽅法，但在定义组件时并不知道这种依赖关系）
- 多个Mixin之间可能产⽣冲突（⽐如定义了相同的state字段）
- Mixin倾向于增加更多状态，这降低了应⽤的可预测性（The more state in your application, the harder it is to reason about it.），导致复杂度剧增
- 隐式依赖导致依赖关系不透明，维护成本和理解成本迅速攀升：
- 难以快速理解组件⾏为，需要全盘了解所有依赖Mixin的扩展⾏为，及其之间的相互影响
- 组价⾃身的⽅法和state字段不敢轻易删改，因为难以确定有没有Mixin依赖它
- Mixin也难以维护，因为Mixin逻辑最后会被打平合并到⼀起，很难搞清楚⼀个Mixin的输⼊输出

### HOC
高阶组件就是一个函数，且该函数接受一个组件作为参数，并返回一个新的组件。
HOC 在 React 的第三方库中很常见，例如 Redux 的 connect 和 Relay 的 createFragmentContainer。

#### HOC相⽐Mixin的优势
- HOC通过外层组件通过Props影响内层组件的状态，⽽不是直接改变其State不存在冲突和互相⼲扰,这就降低了 耦合度
- 不同于Mixin的打平+合并,HOC具有天然的层级结构（组件树结构），这⼜降低了复杂度
#### HOC的缺陷
- 扩展性限制: HOC⽆法从外部访问⼦组件的State因此⽆法通过shouldComponentUpdate滤掉不必要的更新,React在⽀持ES6 Class之后提供了React.PureComponent来解决这个问题
- Ref传递问题: Ref被隔断,后来的React.forwardRef来解决这个问题
- Wrapper Hell:HOC可能出现多层包裹组件的情况,多层抽象同样增加了复杂度和理解成本
- 命名冲突: 如果⾼阶组件多次嵌套,没有使⽤命名空间的话会产⽣冲突,然后覆盖⽼属性
- 不可⻅性: HOC相当于在原有组件外层再包装⼀个组件,你压根不知道外层的包装是啥,对于你是⿊盒

### Render Props
术语 “render prop” 是指一种简单的技术，用于使用一个值为函数的 prop 在 React 组件之间的代码共享。
```
class Cat extends React.Component {
  render() {
    const mouse = this.props.mouse;
    return (
      <img src="/cat.jpg" style={{ position: 'absolute', left: mouse.x, top: mouse.y }} />
    );
  }
}

class Mouse extends React.Component {
  constructor(props) {
    super(props);
    this.handleMouseMove = this.handleMouseMove.bind(this);
    this.state = { x: 0, y: 0 };
  }

  handleMouseMove(event) {
    this.setState({
      x: event.clientX,
      y: event.clientY
    });
  }

  render() {
    return (
      <div style={{ height: '100%' }} onMouseMove={this.handleMouseMove}>

        {/*
          Instead of providing a static representation of what <Mouse> renders,
          use the `render` prop to dynamically determine what to render.
        */}
        {this.props.render(this.state)}
      </div>
    );
  }
}

class MouseTracker extends React.Component {
  render() {
    return (
      <div>
        <h1>Move the mouse around!</h1>
        <Mouse render={mouse => (
          <Cat mouse={mouse} />
        )}/>
      </div>
    );
  }
}
```
#### Render Props优点
- 上述HOC的缺点Render Props都可以解决
#### Render Props缺陷:
- 使⽤繁琐: HOC使⽤只需要借助装饰器语法通常⼀⾏代码就可以进⾏复⽤,Render Props⽆法做到如此简单
- 嵌套过深: Render Props虽然摆脱了组件多层嵌套的问题,但是转化为了函数回调的嵌套

### React Hooks
Hook 是 React 16.8 的新增特性。它可以让你在不编写 class 的情况下使用 state 以及其他的 React 特性。

#### State hook
略

#### Effect hook
Effect Hook 可以让你在函数组件中执行副作用操作。

无需清除的 effect：有时候，我们只想在 React 更新 DOM 之后运行一些额外的代码。比如发送网络请求，手动变更 DOM，记录日志，这些都是常见的无需清除的操作。因为我们在执行完这些操作之后，就可以忽略他们了。

```
useEffect(() => {
    document.title = `You clicked ${count} times`;
  });
```

需要清除的 effect:有一些副作用是需要清除的。例如订阅外部数据源。

```
useEffect(() => {
    function handleStatusChange(status) {
      setIsOnline(status.isOnline);
    }
    ChatAPI.subscribeToFriendStatus(props.friend.id, handleStatusChange);
    // Specify how to clean up after this effect:
    return function cleanup() {
      ChatAPI.unsubscribeFromFriendStatus(props.friend.id, handleStatusChange);
    };
  });
  ```

#### React Hooks优点
- 简洁: React Hooks解决了HOC和Render Props的嵌套问题,更加简洁
- 解耦: React Hooks可以更⽅便地把 UI 和状态分离,做到更彻底的解耦
- 组合: Hooks 中可以引⽤另外的 Hooks形成新的Hooks,组合变化万千
- 函数友好: React Hooks为函数组件⽽⽣,从⽽解决了类组件的⼏⼤问题:
- this指向容易错误
- 分割在不同声明周期中的逻辑使得代码难以理解和维护
- 代码复⽤成本⾼（⾼阶组件容易使代码量剧增）
#### React Hooks缺陷
- 额外的学习成本（Functional Component 与Class Component之间的困惑）
- 写法上有限制（不能出现在条件、循环中），并且写法限制增加了重构成本
- 破坏了PureComponent、React.memo浅⽐较的性能优化效果（为了取最新的props和state，每次render()都要重新创建事件处函数）
- 在闭包场景可能会引⽤到旧的state、props值
- 内部实现上不直观（依赖⼀份可变的全局状态，不再那么“纯”）
- React.memo并不能完全替代shouldComponentUpdate（因为拿不到state change，只针对 props change）

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

