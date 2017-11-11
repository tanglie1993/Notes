# Class 的继承
## 简介
Class 可以通过extends关键字实现继承。 
子类必须在constructor方法中调用super方法，否则新建实例时会报错。 
ES5 的继承，实质是先创造子类的实例对象this，然后再将父类的方法添加到this上面（Parent.apply(this)）。ES6 的继承机制完全不同，实质是先创造父类的实例对象this（所以必须先调用super方法），然后再用子类的构造函数修改this。
如果子类没有定义constructor方法，这个方法会被默认添加，代码如下。
```
class ColorPoint extends Point {
}

// 等同于
class ColorPoint extends Point {
  constructor(...args) {
    super(...args);
  }
}
```
在子类的构造函数中，只有调用super之后，才可以使用this关键字，否则会报错。
父类的静态方法，也会被子类继承。

## Object.getPrototypeOf()
Object.getPrototypeOf方法可以用来从子类上获取父类。
```
Object.getPrototypeOf(ColorPoint) === Point
// true
```
## super关键字
- super作为函数调用时，代表父类的构造函数。
- super作为对象时，在普通方法中，指向父类的原型对象；在静态方法中，指向父类。

这里需要注意，由于super指向父类的原型对象，所以定义在父类实例上的方法或属性，是无法通过super调用的。
```
class A {
  constructor() {
    this.p = 2;
  }
}

class B extends A {
  get m() {
    return super.p;
  }
}

let b = new B();
b.m // undefined
```
ES6 规定，通过super调用父类的方法时，方法内部的this指向子类。由于this指向子类，所以如果通过super对某个属性赋值，这时super就是this，赋值的属性会变成子类实例的属性。

由于对象总是继承其他对象的，所以可以在任意一个对象中，使用super关键字。

# 类的 prototype 属性和__proto__属性
Class 作为构造函数的语法糖，同时有prototype属性和__proto__属性，因此同时存在两条继承链。

- 子类的__proto__属性，表示构造函数的继承，总是指向父类。
- 子类prototype属性的__proto__属性，表示方法的继承，总是指向父类的prototype属性。

```
Object.setPrototypeOf(B.prototype, A.prototype);
// 等同于
B.prototype.__proto__ = A.prototype;

Object.setPrototypeOf(B, A);
// 等同于
B.__proto__ = A;
```
这两条继承链，可以这样理解：作为一个对象，子类（B）的原型（__proto__属性）是父类（A）；作为一个构造函数，子类（B）的原型对象（prototype属性）是父类的原型对象（prototype属性）的实例。

## extends 的继承目标
```
class A extends Object {
}

A.__proto__ === Object // true
A.prototype.__proto__ === Object.prototype // true

class A {
}

A.__proto__ === Function.prototype // true
A.prototype.__proto__ === Object.prototype // true

class A extends null {
}

A.__proto__ === Function.prototype // true
A.prototype.__proto__ === undefined // true

var p1 = new Point(2, 3);
var p2 = new ColorPoint(2, 3, 'red');

p2.__proto__ === p1.__proto__ // false
p2.__proto__.__proto__ === p1.__proto__ // true
```
## 原生构造函数的继承
ECMAScript 的原生构造函数大致有下面这些。
- Boolean()
- Number()
- String()
- Array()
- Date()
- Function()
- RegExp()
- Error()
- Object()
以前，这些原生构造函数是无法继承的，比如，不能自己定义一个Array的子类。
ES6 允许继承原生构造函数定义子类，因为 ES6 是先新建父类的实例对象this，然后再用子类的构造函数修饰this，使得父类的所有行为都可以继承。

## Mixin 模式的实现
Mixin 指的是多个对象合成一个新的对象，新对象具有各个组成成员的接口。它的最简单实现如下。
```
const a = {
  a: 'a'
};
const b = {
  b: 'b'
};
const c = {...a, ...b}; // {a: 'a', b: 'b'}
```
























