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
https://juejin.im/post/58f816198d6d81005874fd97

## 10. 如何判断两个对象相等
https://juejin.im/post/598a701b6fb9a03c5b04bb14

## 11. 事件模型
https://segmentfault.com/a/1190000006934031

## 12. 事件委托（事件代理）
https://www.cnblogs.com/liugang-vip/p/5616484.html

## 13. 函数柯里化与反柯里化
https://juejin.im/post/5b561426518825195f499772

## 14. call，apply，bind 三者用法和区别
https://qianlongo.github.io/2016/04/26/js%E4%B8%ADcall%E3%80%81apply%E3%80%81bind%E9%82%A3%E4%BA%9B%E4%BA%8B/#more

## 15. 理解 async/await
https://juejin.im/post/596e142d5188254b532ce2da

## 17. JavaScript中的立即执行函数
https://juejin.im/post/59fc0a8c6fb9a04500026707

## 18. JavaScript中有哪些设计模式
https://juejin.im/post/59df4f74f265da430f311909

## 19. BOM
https://juejin.im/post/583437d8128fe1006ccffde8

## 20. 服务端渲染
https://www.cnblogs.com/muzishijie/p/11198315.html

## 21. 垃圾回收机制
https://juejin.im/post/5a6b3fcaf265da3e2c385375

## 22. EventLoop
https://juejin.im/post/5d01adb2f265da1b667bd4ad
