## 1. 谈谈你对原型链的理解？
![pic](https://img2018.cnblogs.com/blog/1480829/201904/1480829-20190416101041496-1168291931.png)

__proto__和constructor属性是对象所独有的；
prototype属性是函数所独有的，因为函数也是一种对象，所以函数也拥有__proto__和constructor属性。
__proto__属性的作用就是当访问一个对象的属性时，如果该对象内部不存在这个属性，那么就会去它的__proto__属性所指向的那个对象（父对象）里找，一直找，直到__proto__属性的终点null，然后返回undefined，通过__proto__属性将对象连接起来的这条链路即我们所谓的原型链。
prototype属性的作用就是让该函数所实例化的对象们都可以找到公用的属性和方法，即f1.__proto__ === Foo.prototype。
constructor属性的含义就是指向该对象的构造函数，所有函数（此时看成对象了）最终的构造函数都指向Function。

## 2. JS继承
众所周知，在 ES 6 之前没有类的概念，所以不能像 Java 中一个 extends 关键字就搞定了继承关系，需要一些 tricks 来实现。
### 原型链继承
```
function Parent(name) { 
    this.name = name;
}
Parent.prototype.sayName = function() {
    console.log('parent name:', this.name);
}
function Child(name) {
    this.name = name;
}

Child.prototype = new Parent('father');
Child.prototype.constructor = Child;

Child.prototype.sayName = function() {
    console.log('child name:', this.name);
}

var child = new Child('son');
child.sayName(); 
```
这种方法存在两个缺点：

- 子类型无法给超类型传递参数，在面向对象的继承中，我们总希望通过 var child = new Child('son', 'father'); 让子类去调用父类的构造器来完成继承。而不是通过像这样 new Parent('father') 去调用父类。
- Child.prototype.sayName 必须写在 Child.prototype = new Parent('father'); 之后，不然就会被覆盖掉。

### 类式继承
```
function Parent(name) { 
    this.name = name;
}
Parent.prototype.sayName = function() {
    console.log('parent name:', this.name);
}
Parent.prototype.doSomthing = function() {
    console.log('parent do something!');
}
function Child(name, parentName) {
    Parent.call(this, parentName);
    this.name = name;
}

Child.prototype.sayName = function() {
    console.log('child name:', this.name);
}

var child = new Child('son');
child.sayName();      // child name: son
child.doSomthing();   // TypeError: child.doSomthing is not a function
```

### 组合式继承
```
function Parent(name) { 
    this.name = name;
}

Parent.prototype.sayName = function() {
    console.log('parent name:', this.name);
}
Parent.prototype.doSomething = function() {
    console.log('parent do something!');
}
function Child(name, parentName) {
    Parent.call(this, parentName);
    this.name = name;
}

Child.prototype = new Parent();      
Child.prototype.constructor = Child;
Child.prototype.sayName = function() {
    console.log('child name:', this.name);
}

var child = new Child('son');
child.sayName();       // child name: son
child.doSomething();   // parent do something!
```
组合式继承是 JS 最常用的继承模式，但组合继承使用过程中会被调用两次：一次是创建子类型的时候，另一次是在子类型构造函数的内部。

### 寄生组合式继承
```
function Parent(name) {
    this.name = name;
}
Parent.prototype.sayName = function() {
    console.log('parent name:', this.name);
}

function Child(name, parentName) {
    Parent.call(this, parentName);  
    this.name = name;    
}

function create(proto) {
    function F(){}
    F.prototype = proto;
    return new F();
}

Child.prototype = create(Parent.prototype);
Child.prototype.sayName = function() {
    console.log('child name:', this.name);
}
Child.prototype.constructor = Child;

var parent = new Parent('father');
parent.sayName();    // parent name: father


var child = new Child('son', 'father');
child.sayName();     // child name: son
```
寄生组合式继承的思想在于：用一个 F 空的构造函数去取代执行了 Parent 这个构造函数。

### ES6继承
```
class Parent {
    constructor(name) {
	this.name = name;
    }
    doSomething() {
	console.log('parent do something!');
    }
    sayName() {
	console.log('parent name:', this.name);
    }
}

class Child extends Parent {
    constructor(name, parentName) {
	super(parentName);
	this.name = name;
    }
    sayName() {
 	console.log('child name:', this.name);
    }
}

const child = new Child('son', 'father');
child.sayName();            // child name: son
child.doSomething();        // parent do something!

const parent = new Parent('father');
parent.sayName();           // parent name: father
```

## 3. Javascript 中的数据类型判断？
### typeof
```
var obj = {
   name: 'zhangxiang'
};

function foo() {
    console.log('this is a function');
}

var arr = [1,2,3];

console.log(typeof 1);  // number
console.log(typeof '1');  //string
console.log(typeof true);  //boolean
console.log(typeof null); //object
console.log(typeof undefined); //undefined
console.log(typeof obj); //object
console.log(typeof foo);  //function
console.log(typeof arr);   //object
```
### instanceof
instanceof 其实适合用于判断自定义的类实例对象, 而不是用来判断原生的数据类型。

```
// a.html
<script>
  var a = [1,2,3];
</script>

//main.html
<iframe src="a.html"></iframe>

<script>
  var frame = window.frame[0];
  var a = frame.a;
  console.log(a instanceof Array);  // false
  console.log(a.contructor === Array);  //false
  console.log(a instanceof frame.Array); // true
</script>
```

### prototype
```
function foo(){};

Object.prototype.toString.call(1);  '[object Number]'
Object.prototype.toString.call('1'); '[object String]'
Object.prototype.toString.call(NaN); '[object Number]'
Object.prototype.toString.call(foo);  '[object Function]'
Object.prototype.toString.call([1,2,3]); '[object Array]'
Object.prototype.toString.call(undefined); '[object Undefined]'
Object.prototype.toString.call(null); '[object Null]'
Object.prototype.toString.call(true); '[object Boolean]'
```

### isArray polyfill
isArray 是数组类型内置的数据类型判断函数, 但是会有兼容性问题, 所以模拟 underscore 中的写法如下:
```
isArray = Array.isArray || function(array){
  return Object.prototype.toString.call(array) === '[object Array]';
}
```

### isNaN polyfill
```
isNaN: function(value){
  return isNumber(value) && isNaN(value);
}
```

### 判断是否是对象
```
isObject: function(obj){
  var type = typeof obj;
  return type === 'function' || typeof === 'object' && obj !== null;
}
```

## 4. 作用域和作用域链？
https://juejin.im/post/5c8efeb1e51d45614372addd
https://juejin.im/post/5c8290455188257e5d0ec64f

## 5. 闭包
https://juejin.im/post/58832fe72f301e00697b672d

## 6. 图片懒加载、预加载
https://juejin.im/post/5b0c3b53f265da09253cbed0

## 7. this
### 词法作用域与动态作用域
通常来说，作用域一共有两种主要的工作模型：词法作用域、动态作用域。

词法作用域是大多数编程语言所采用的模式，而动态作用域仍有一些编程语言在用，例如 Bash 脚本。而 JavaScript 就是采用的词法作用域，也就是在编程阶段，作用域就已经明确下来了。

```
function foo(){
  console.log(a);   // 输出 2
}

function bar(){
  let a = 3;
  foo();
}

let a = 2;

bar()
```
因为 JavaScript 所用的是词法作用域，自然 foo() 声明的阶段，就已经确定了变量 a 的作用域了。

倘若，JavaScript 是采用的动态作用域，foo() 中打印的将是 3。

在 JavaScript 中，影响 this 指向的绑定规则有四种：

### 默认绑定
这是最直接的一种方式，就是不加任何的修饰符直接调用函数。
```
function foo() {
  console.log(this.a)   // 输出 a
}

var a = 2;  //  变量声明到全局对象中

foo();
```
使用 var 声明的变量 a，被绑定到全局对象中，如果是浏览器，则是在 window 对象。
foo() 调用时，引用了默认绑定，this 指向了全局对象。

### 隐式绑定
```
function foo() {
  console.log(this.a);
}

let obj1 = {
  a: 1,
  foo,
};

let obj2 = {
  a: 2,
  foo,
}

obj1.foo();   // 输出 1
obj2.foo();   // 输出 2
```
### 显式绑定
这种就是使用 Function.prototype 中的三个方法 call(), apply(), bind() 了。这三个函数，都可以改变函数的 this 指向到指定的对象，不同之处在于，call() 和 apply() 是立即执行函数，并且接受的参数的形式不同：

call(this, arg1, arg2, ...)
apply(this, [arg1, arg2, ...])

而 bind() 则是创建一个新的包装函数，并且返回，而不是立刻执行。

bind(this, arg1, arg2, ...)

### new 绑定
如果 new 是一个函数的话，会是这样子的：
```
function New(Constructor, ...args){
    let obj = {};   // 创建一个新对象
    Object.setPrototypeOf(obj, Constructor.prototype);  // 连接新对象与函数的原型
    return Constructor.apply(obj, args) || obj;   // 执行函数，改变 this 指向新的对象
}

function Foo(a){
    this.a = a;
}

New(Foo, 1);  // Foo { a: 1 }
```
所以，在使用 new 来调用函数时候，我们会构造一个新对象并把它绑定到函数调用中的 this 上。

### 优先级
「new 绑定」 > 「显式绑定」 > 「隐式绑定」 > 「默认绑定。」

### 所以，this 到底是什么
this 并不是在编写的时候绑定的，而是在运行时绑定的。它的上下文取决于函数调用时的各种条件。this 的绑定和函数声明的位置没有任何关系，只取决于函数的调用方式。当一个函数被调用时，会创建一个「执行上下文」，这个上下文会包含函数在哪里被调用（调用栈）、函数的调用方式、传入的参数等信息。this 就是这个记录的一个属性，会在函数执行的过程中用到。

## 8. 函数式编程
https://juejin.im/post/5d70e25de51d453c11684cc4

## 9. 同源策略
非同源请求，均为跨域。

名词解释：同源 —— 如果两个页面拥有相同的协议（protocol），端口（port）和主机（host），那么这两个页面就属于同一个源（origin）。

通常，最常用的跨域方式有以下三种：JSONP、CORS、postMessage。

### JSONP
虽然因为同源策略的影响，不能通过XMLHttpRequest请求不同域上的数据（Cross-origin reads）。但是，在页面上引入不同域上的js脚本文件却是可以的（Cross-origin embedding）。因此在js文件载入完毕之后，触发回调，可以将需要的data作为参数传入。

【JSONP的优缺点】
优点：兼容性好（兼容低版本IE）
缺点：1.JSONP只支持GET请求； 2.XMLHttpRequest相对于JSONP有着更好的错误处理机制

### CORS
CORS 是W3C 推荐的一种新的官方方案，能使服务器支持 XMLHttpRequest 的跨域请求。CORS 实现起来非常方便，只需要增加一些 HTTP 头，让服务器能声明允许的访问来源。
值得注意的是，通常使用CORS时，异步请求会被分为简单请求和非简单请求，非简单请求的区别是会先发一次预检请求。

### postMessage
window.postMessage(message,targetOrigin) 方法是html5新引进的特性，可以使用它来向其它的window对象发送消息，无论这个window对象是属于同源或不同源，目前IE8+、FireFox、Chrome、Opera等浏览器都已经支持window.postMessage方法。

## 10. 如何判断两个对象相等
```
var toString = Object.prototype.toString;

function isFunction(obj) {
    return toString.call(obj) === '[object Function]'
}

function eq(a, b, aStack, bStack) {

    // === 结果为 true 的区别出 +0 和 -0
    if (a === b) return a !== 0 || 1 / a === 1 / b;

    // typeof null 的结果为 object ，这里做判断，是为了让有 null 的情况尽早退出函数
    if (a == null || b == null) return false;

    // 判断 NaN
    if (a !== a) return b !== b;

    // 判断参数 a 类型，如果是基本类型，在这里可以直接返回 false
    var type = typeof a;
    if (type !== 'function' && type !== 'object' && typeof b != 'object') return false;

    // 更复杂的对象使用 deepEq 函数进行深度比较
    return deepEq(a, b, aStack, bStack);
};

function deepEq(a, b, aStack, bStack) {

    // a 和 b 的内部属性 [[class]] 相同时 返回 true
    var className = toString.call(a);
    if (className !== toString.call(b)) return false;

    switch (className) {
        case '[object RegExp]':
        case '[object String]':
            return '' + a === '' + b;
        case '[object Number]':
            if (+a !== +a) return +b !== +b;
            return +a === 0 ? 1 / +a === 1 / b : +a === +b;
        case '[object Date]':
        case '[object Boolean]':
            return +a === +b;
    }

    var areArrays = className === '[object Array]';
    // 不是数组
    if (!areArrays) {
        // 过滤掉两个函数的情况
        if (typeof a != 'object' || typeof b != 'object') return false;

        var aCtor = a.constructor,
            bCtor = b.constructor;
        // aCtor 和 bCtor 必须都存在并且都不是 Object 构造函数的情况下，aCtor 不等于 bCtor， 那这两个对象就真的不相等啦
        if (aCtor == bCtor && !(isFunction(aCtor) && aCtor instanceof aCtor && isFunction(bCtor) && bCtor instanceof bCtor) && ('constructor' in a && 'constructor' in b)) {
            return false;
        }
    }


    aStack = aStack || [];
    bStack = bStack || [];
    var length = aStack.length;

    // 检查是否有循环引用的部分
    while (length--) {
        if (aStack[length] === a) {
            return bStack[length] === b;
        }
    }

    aStack.push(a);
    bStack.push(b);

    // 数组判断
    if (areArrays) {

        length = a.length;
        if (length !== b.length) return false;

        while (length--) {
            if (!eq(a[length], b[length], aStack, bStack)) return false;
        }
    }
    // 对象判断
    else {

        var keys = Object.keys(a),
            key;
        length = keys.length;

        if (Object.keys(b).length !== length) return false;
        while (length--) {

            key = keys[length];
            if (!(b.hasOwnProperty(key) && eq(a[key], b[key], aStack, bStack))) return false;
        }
    }

    aStack.pop();
    bStack.pop();
    return true;

}

console.log(eq(0, 0)) // true
console.log(eq(0, -0)) // false

console.log(eq(NaN, NaN)); // true
console.log(eq(Number(NaN), Number(NaN))); // true

console.log(eq('Curly', new String('Curly'))); // true

console.log(eq([1], [1])); // true
console.log(eq({ value: 1 }, { value: 1 })); // true

var a, b;

a = { foo: { b: { foo: { c: { foo: null } } } } };
b = { foo: { b: { foo: { c: { foo: null } } } } };
a.foo.b.foo.c.foo = a;
b.foo.b.foo.c.foo = b;

console.log(eq(a, b)) // true
```

## 11. 事件模型
https://segmentfault.com/a/1190000006934031

## 12. 事件委托（事件代理）
https://www.cnblogs.com/liugang-vip/p/5616484.html

## 13. 函数柯里化与反柯里化
https://juejin.im/post/5b561426518825195f499772

## 15. 理解 async/await
async 函数是 Generator 函数的语法糖。使用 关键字 async 来表示，在函数内部使用 await 来表示异步。相较于 Generator，Async 函数的改进在于下面四点：

- 内置执行器。Generator 函数的执行必须依靠执行器，而 Aysnc 函数自带执行器，调用方式跟普通函数的调用一样
- 更好的语义。async 和 await 相较于 * 和 yield 更加语义化
- 更广的适用性。co 模块约定，yield 命令后面只能是 Thunk 函数或 Promise对象。而 async 函数的 await 命令后面则可以是 Promise 或者 原始类型的值（Number，string，boolean，但这时等同于同步操作）
- 返回值是 Promise。async 函数返回值是 Promise 对象，比 Generator 函数返回的 Iterator 对象方便，可以直接使用 then() 方法进行调用

### Async 与其他异步操作的对比
Promise 方式
```
function getUserByPromise() {
    fetchUser()
        .then((data) => {
            console.log(data);
        }, (error) => {
            console.log(error);
        })
}
getUserByPromise();
```
### Generator 方式
```
function* fetchUserByGenerator() {
    const user = yield fetchUser();
    return user;
}

const g = fetchUserByGenerator();
const result = g.next().value;
result.then((v) => {
    console.log(v);
}, (error) => {
    console.log(error);
})
```

### async 方式
```
async function getUserByAsync(){
     let user = await fetchUser();
     return user;
 }
getUserByAsync(
.then(v => console.log(v));
```

async 函数返回一个 Promise 对象。
async 函数返回的 Promise 对象，必须等到内部所有的 await 命令的 Promise 对象执行完，才会发生状态改变。
正常情况下，await 命令后面跟着的是 Promise ，如果不是的话，也会被转换成一个 立即 resolve 的 Promise。


### 立即执行函数
要成为立即执行函数，需要满足两个条件：

声明一个匿名函数
立马调用这个匿名函数
比如，下面就是一个非常典型的立即执行函数：

(function(){console.log('这是一个立即执行函数'))()

立即执行函数的作用只有一个，那就是创建独立的作用域。 让外部无法访问作用域内部的变量，从而避免变量污染。

ES6可以直接用块级作用域。


## BOM
BOM（浏览器对象模型）以 window 对象为依托，表示浏览器窗口以及页面可见区域。同时，window 对象还是 ECMAScript 中的 Global 对象，因而所有全局变量和函数都是它的属性，且所有原生的构造函数及其他函数也都存在于它的命名空间下。本章讨论了下列 BOM 的组成部分。

- 在使用框架时，每个框架都有自己的 window 对象以及所有原生构造函数及其他函数的副本。每个框架都保存在 frames 集合中，可以通过位置或通过名称来访问。
- 有一些窗口指针，可以用来引用其他框架，包括父框架。
- top 对象始终指向最外围的框架，也就是整个浏览器窗口。
- parent 对象表示包含当前框架的框架，而 self 对象则回指 window。
- 使用 location 对象可以通过编程方式来访问浏览器的导航系统。设置相应的属性，可以逐段或整体性地修改浏览器的 URL。
- 调用 replace() 方法可以导航到一个新 URL，同时该 URL 会替换浏览器历史记录中当前显示的页面。
- navigator 对象提供了与浏览器有关的信息。到底提供哪些信息，很大程度上取决于用户的浏览器；不过，也有一些公共的属性（如 userAgent）存在于所有浏览器中。

BOM中还有两个对象：screen 和 history，但它们的功能有限。screen 对象中保存着与客户端显示器有关的信息，这些信息一般只用于站点分析。history 对象为访问浏览器的历史记录开了一个小缝隙，开发人员可以据此判断历史记录的数量，也可以在历史记录中向后或向前导航到任意页面。

## EventLoop
浏览器的event loop至少包含两个队列，macrotask队列和microtask队列。
1. microtask 即微任务，是由js引擎分发的任务，总是添加到当前任务队列末尾执行。另外在处理microtask期间，如果有新添加的microtasks，也会被添加到队列的末尾并执行： Promise、MutaionObserver、process.nextTick(Node.js 环境)
2. macrotask队列 等同于我们常说的任务队列，macrotask是由宿主环境分发的异步任务，事件轮询的时候总是一个一个任务队列去查看执行的，"任务队列"是一个先进先出的数据结构，排在前面的事件，优先被主线程读取：script(整体代码)、setTimeout、setInterval、I/O、UI交互事件、setImmediate(Node.js 环境)
3. 只要有微任务我们肯定是执行微任务的，当前进行的会执行，当前执行完的如果执行完后event loop还是检测到微任务，还是执行微任务，检测出没有微任务，我们就执行宏任务队列中的任务。
经典面试题：
```
async function async1(){
    console.log('async1 start')
    await async2()
    console.log('async1 end')
}
async function async2(){
    console.log('async2')
}
console.log('script start')
setTimeout(function(){
    console.log('setTimeout') 
},0)  
async1();
new Promise(function(resolve){
    console.log('promise1')
    resolve();
}).then(function(){
    console.log('promise2')
})
console.log('script end')
/*
解题思路：
首先按照代码的执行顺序从上往下，js始终都是单线程的，先执行的肯定是同步任务，再根据进入任务队列的顺序先进先出，先微后宏。
微任务是一次性将队列中存在的微任务执行完毕，宏任务是一个一个先进先出。
Promise是一个构造函数，调用的时候会生成Promise实例。当Promise的状态改变时会调用then函数中定义的回调函数。
我们都知道这个回调函数不会立刻执行，他是一个微任务会被添加到当前任务队列中的末尾，在下一轮任务开始执行之前执行。
async/await成对出现，async标记的函数会返回一个Promise对象，可以使用then方法添加回调函数。await后面的语句会同步执行。但 await 下面的语句会被当成微任务添加到当前任务队列的末尾异步执行。
*/

/*
答案： 
node的部分版本（比如V10.10.0）中会存在: script start -> async1 start -> async2 -> promise1 -> script end -> promise2 -> async1 end -> setTimeout
<= node8版本: script start -> async1 start -> async2 -> promise1 -> script end -> async1 end -> promise2 -> setTimeout
这主要是node.js8版本与其他版本的差异，他们对await的执行方法不同（Node新版本（11之后）与浏览器执行结果相同，这是大趋势）
*/
```
