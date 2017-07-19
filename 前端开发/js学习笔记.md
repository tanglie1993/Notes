```
var v1 = 1.1;
var v2 = 1.1;
console.log(v1 == v2)

输出
true
```


```
var v1 = 1.1;
var v2 = 1.2 - 0.1;
console.log(v1 == v2)

输出
false
```

```
var v1 = 1;
console.log(v1)
v1++;
console.log(v1)
v1 += "x";
console.log(v1)
v1++;
console.log(v1)
v1++;
console.log(v1)

输出
1
2
2x
NaN
NaN
```

```
var v1;
console.log(v1)
v1 = new Array()
console.log(v1)
v1 = new Object()
console.log(v1)

输出
undefined
[]
{}
```

####局部 JavaScript 变量
在 JavaScript 函数内部声明的变量（使用 var）是局部变量，所以只能在函数内部访问它。（该变量的作用域是局部的）。  
只要函数运行完毕，本地变量就会被删除。
####全局 JavaScript 变量
在函数外声明的变量是全局变量，网页上的所有脚本和函数都能访问它。

```
carname="Volvo";
```
将声明一个全局变量 carname，即使它在函数内执行。


```
var a = 3;
var b = "3";
console.log(a==b)
console.log(a===b)

输出
true
false
```

```
var a = true;
var b = "1";
console.log(a==b)
console.log(a===b)
b = "2"
console.log(a==b)
console.log(a===b)

输出
true
false
false
false
```

```
var a = {v1:1}
switch (a){
    case {v1:1}:
        console.log("1")
    case a:
        console.log("2")
}

输出
2
```

```
try {
    adddlert("Welcome guest!");
} catch(err) {
    txt="There was an error on this page.\n";
    txt+="Error description: " + err.message + "\n";
    txt+="Click OK to continue.\n";
    console.log(txt);
}

输出
There was an error on this page.
Error description: adddlert is not defined
Click OK to continue.
```