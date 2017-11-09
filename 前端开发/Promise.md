# Promise
## Promise的含义
Promise对象代表一个异步操作，有三种状态：pending（进行中）、fulfilled（已成功）和rejected（已失败）。Promise对象的状态改变，只有两种可能：从pending变为fulfilled和从pending变为rejected。

## 基本用法
创建实例：
```
const promise = new Promise(function(resolve, reject) {
  // ... some code

  if (/* 异步操作成功 */){
    resolve(value);
  } else {
    reject(error);
  }
});
```
调用：
```
promise.then(function(value) {
  // success
}, function(error) {
  // failure
});
```
Promise 新建后就会立即执行。
```
let promise = new Promise(function(resolve, reject) {
  console.log('Promise');
  resolve();
});

输出：Promise
```
异步加载图片的例子：
```
function loadImageAsync(url) {
  return new Promise(function(resolve, reject) {
    const image = new Image();

    image.onload = function() {
      resolve(image);
    };

    image.onerror = function() {
      reject(new Error('Could not load image at ' + url));
    };

    image.src = url;
  });
}
```
## Promise.prototype.then() 
then方法返回的是一个新的Promise实例（注意，不是原来那个Promise实例）。因此可以采用链式写法，即then方法后面再调用另一个then方法。
```
getJSON("/post/1.json").then(function(post) {
  return getJSON(post.commentURL);
}).then(function funcA(comments) {
  console.log("resolved: ", comments);
}, function funcB(err){
  console.log("rejected: ", err);
});
```
如果采用箭头函数，上面的代码可以写得更简洁。
```
getJSON("/post/1.json").then(
  post => getJSON(post.commentURL)
).then(
  comments => console.log("resolved: ", comments),
  err => console.log("rejected: ", err)
);
```
## Promise.prototype.catch() 
Promise.prototype.catch方法是.then(null, rejection)的别名，用于指定发生错误时的回调函数。
```
getJSON('/posts.json').then(function(posts) {
  // ...
}).catch(function(error) {
  // 处理 getJSON 和 前一个回调函数运行时发生的错误
  console.log('发生错误！', error);
});
```
如果Promise状态已经变成resolved，再抛出错误是无效的。

Promise 对象的错误具有“冒泡”性质，会一直向后传递，直到被捕获为止。也就是说，错误总是会被下一个catch语句捕获。

跟传统的try/catch代码块不同的是，如果没有使用catch方法指定错误处理的回调函数，Promise 对象抛出的错误不会传递到外层代码，即不会有任何反应。
一般总是建议，Promise 对象后面要跟catch方法，这样可以处理 Promise 内部发生的错误。
catch方法返回的还是一个 Promise 对象，因此后面还可以接着调用then方法。

## Promise.all()
```
const p = Promise.all([p1, p2, p3]);
```
只有p1、p2、p3的状态都变成fulfilled，p的状态才会变成fulfilled，此时p1、p2、p3的返回值组成一个数组，传递给p的回调函数。
只要p1、p2、p3之中有一个被rejected，p的状态就变成rejected，此时第一个被reject的实例的返回值，会传递给p的回调函数。

## Promise.race()
```
const p = Promise.race([p1, p2, p3]);
```
上面代码中，只要p1、p2、p3之中有一个实例率先改变状态，p的状态就跟着改变。那个率先改变的 Promise 实例的返回值，就传递给p的回调函数。

## Promise.resolve()
有时需要将现有对象转为Promise对象，Promise.resolve方法就起到这个作用。
1. 参数是一个Promise实例
如果参数是Promise实例，那么Promise.resolve将不做任何修改、原封不动地返回这个实例。
2. 参数是一个thenable对象
thenable对象指的是具有then方法的对象。Promise.resolve方法会将这个对象转为Promise对象，然后就立即执行thenable对象的then方法。
3. 参数不是具有then方法的对象，或根本就不是对象
如果参数是一个原始值，或者是一个不具有then方法的对象，则Promise.resolve方法返回一个新的Promise对象，状态为resolved。
4. 不带有任何参数
Promise.resolve方法允许调用时不带参数，直接返回一个resolved状态的Promise对象。

## Promise.reject() 
Promise.reject(reason)方法也会返回一个新的 Promise 实例，该实例的状态为rejected。









