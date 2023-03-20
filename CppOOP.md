
  

- 静态数组和动态数组：

  
  

1. 静态数组要求在 compile time 就确定内存大小，并在编译得到的 Code Seg 中按要求预留一定大小的连续内存；

  
  

```c++

// 创建阶段需要给定大小

// 大小必须在source code中指定常数

int  array[10] = {1,2,3};

```

  

2. 动态数组没有必要在 compile time 就确定内存大小，而是在 run time 根据 input 来在 Heap 中的 “缝隙中” 分配一段连续的内存空间;

  
  

```c++

// 创建阶段需要给定大小

// 大小可以必须在run time再赋值给变量

cin>>n;

int* array = new  int[n];

```

  

3. 假如甚至不想在创建阶段使用变量，那么可以使用双重指针;

  
  

```c++

// 创建阶段不需要给定大小

int** dp = new  int*;

  

// 赋值阶段再确定大小

n = a+b;

*dp = new  int[n];

```

  
  

- 使用动态内存注意要做两件事情：

  
  

1. 在 new 后 delete：否则会 memory leak

2. 在 delete 后进行指针重置：否则 ptr 仍然会指向原先的地址，但这个地址此时应该不允许操作，因此存在潜在危险

  
  

```c++

int array[] = {10,20,30};

int* p = new  int[];

p = array;

cout << p;

// p = 0x7f5a4642e8b0

// 1.释放内存

delete[] p;

cout << p;

// p = 0x7f5a4642e8b0

// 此时 p 并不会随着 delete 而消失，对 p 进行操作是由潜在危险的

// 由此判断，delete 只是将内存空间 0x7f5a4642e8b0 释放，使得下次 new 可以再次利用这片区域

// 2.重置指针

p = nullptr;

cout<<p;

// p = 0x0

// 此时 p 才真正为空

// 3.对于双重指针,注意有细微差别

int** dp = new  int*;

delete[] *dp;

*dp = nullptr;

delete dp;

dp = nullptr;

```

  
  

- 参数传递的5种类型和优缺点（重点）

1. Value

2. Reference

3. Const Reference

4. Adrress

5. Const Address

  

``` c++

// 注意 const address 并不是地址不能变，而是内容不能变

// 本质上和 const reference 是同样的用法

void  function(const  int*  a)

```

  

- 为什么在创建数组的时候需要 nullptr;

  

```c++

int* p = new  int[n]{0,0,0};

delete[] p;

p = nullptr;

```

​ 但是在 class 的 destruct 中只需要delete

```c++

class  RobotLearning{

RobotLearning(){

char* task = nullptr;

}

~RobotLearning(){

delete[] task;

}

}

```

  

- 为什么有的函数要返回引用值，C++不是自带了优化器把 return直接赋值给调用函数的左值了吗？

  

```c++

RobotLearning&  operator+=(const  RobotLearning&  other){

return *this;

}

```

  

- 注意 class 在定义后 `{}`结尾处有`;`, 这一点和 int，struct 是相同的，和 function 是不同的

- private 成员和函数，在 class 类外不可以被访问，但在同一 class 的不同 object 之间的是可以相互访问的

  

```c++

RobotLearning Eve;

RobotLearning Wally;

// 不可以

cout << Eve.height;

// 可以

double  RobotLearning::add(const  RobotLearning&  other){

return (this->height + other.height);

// this 可以访问同属一类的 other 的私有成员 other.height

}

```

  

- 关于类的构造函数 constructor：

1. 可以使用 delegate 方法来进行初始化， 且不需要区分 private member 和 传入参数的名字（即使同名）

  

```c++

class  Container

{

private:

double* data;

int length;

public:

// Constructor

explicit  Container(int  length)

: data(new  double[length]),

length(length)

{

std::cout << __PRETTY_FUNCTION__ << " called" << std::endl;

}

}

```

  

2. delegate 除了初始化变量，也可以调用函数

3. 函数重载时，可以调用不同签名的同名函数

  

```c++

// Unified initialization Constructor

explicit  Container(const  std::initializer_list<double>& list)

: Container((int)list.size()) // 此处调用了同样为 constructor 的重载同名函数 Container(int length)

{

std::uninitialized_copy(list.begin(), list.end(), data);

std::cout << __PRETTY_FUNCTION__ << " called" << std::endl;

}

```

  

- 在 4+2 种 de/constructor 之中：default cons, default des, copy cons, copy assign, move cons, move assign

```c++

// 注意 move constructor 一定不能嵌套 default constructor 来 new memory，要保证 soft-copy， 否则会 memory leak

RobotLearning(RobotLeaning&& other){

this->length = other.length;

this->data = other.data;

other.length = 0;

other.data = nullptr;

}

  

// 而 copy constructor 一定要嵌套 default constructor 来实现 deep-copy，否则会 delete 重复

RobotLearning(const RobotLeaning& other): RobotLearning(other.length)

{

for (auto i=; i < other.length; i++){

data[i] = other.data[i];

}

}

  

// 两者对应的 operator 同理，并且记住要先 delete 旧的 ptr

// move assignment

RobotLearning&  operator=(RobotLeaning&&  other){

delete[]  this->data;

this->data = other.data;

...

}

  

// copy assignment

RobotLearning&  operator=(const  RobotLeaning&  other){

delete[]  this->data;

this->data = new  double[other.length];

...

}

```

  

- virtual function 相关的关键词 inline:

  

1. 什么是 `inline`

  

```cpp

// 内联 inline 类似宏定义 macro, 类似shell中的source, 在函数调用时不去开辟新的 stack 而是在当前 stack 内直接将函数名称替换为函数体的内容

// 这样就避免了频繁调用函数对栈内存重复开辟所带来的消耗

```

  

2. 如何用 `inline`

  

```c++

// 1. 定义在类中的成员函数默认是内联的，如果在类定义时就在类内给出函数定义，那当然最好。如果在类中未给出成员函数定义，而又想内联该函数的话，那在类外要加上 `inline`，否则就认为不是内联的

class  A{

public:void  Foo(int x, int y) { } // 自动地成为内联函数

}

// 两个缺点:

// 1. 不规范,不易debug

// 2. 默认内联不一定是好的, 反而大多数时候是效果不好的

  

// 2. 将成员函数的定义体放在类声明之中虽然能带来书写上的方便，但不是一种良好的编程风格，上例应该改成:

// 头文件

class  A{

public:

void  Foo(int  x, int  y);

}

// 定义文件

inline  void  A::Foo(int  x, int  y){} // 实际上大多数情况不应该使用 inline

  

// 3. inline 是一种"用于实现的关键字"，而不是一种"用于声明的关键字"

// 无效

inline  void  Foo(int  x, int  y); // inline 仅与函数声明放在一起

void  Foo(int  x, int  y){}

// 有效

void  Foo(int  x, int  y);

inline  void  Foo(int  x, int  y) {} // inline 与函数定义体放在一起

```

  

3. 什么时候用/不用 `inline`:

  

```cpp

/*

@@@@ 慎用 inline @@@@

  

内联能提高函数的执行效率，为什么不把所有的函数都定义成内联函数？如果所有的函数都是内联函数，还用得着"内联"这个关键字吗？

1. 内联是以代码膨胀（复制）为代价，仅仅省去了函数调用的开销，从而提高函数的执行效率(trade-off)。

2. 如果执行函数体内代码的时间，相比于函数调用的开销较大，那么效率的收获会很少(会有提升,但意义不大)。

3. 另一方面，每一处内联函数的调用都要复制代码，将使程序的总代码量增大，消耗更多的内存空间(单纯的缺点)。

  

以下情况不宜使用内联：

1. 如果函数体内的代码比较长，使用内联将导致内存消耗代价较高。

2. 如果函数体内出现循环，那么执行函数体内代码的时间要比函数调用的开销大。

3. 类的构造函数和析构函数容易让人误解成使用内联更有效。要当心构造函数和析构函数可能会隐藏一些行为，如"偷偷地"执行了基类或成员对象的构造函数和析构函数。

4. 所以不要随便地将构造函数和析构函数的定义体放在类声明中。一个好的编译器将会根据函数的定义体，自动地取消不值得的内联。

*/

```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE2MjI4MDA4MDcsLTEwNjcyNjE4NTFdfQ
==
-->