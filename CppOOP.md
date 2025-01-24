<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

- [Background](#background)
  - [Major Version 版本大更新](#major-version-版本大更新)
  - [RunTime 和 CompileTime](#runtime-和-compiletime)
  - [计算机架构与 RAM 和 ROM](#计算机架构与-ram-和-rom)
  - [Memory 中的 Stack 和 Heap](#memory-中的-stack-和-heap)
  - [关于 ModernCPP 的特性](#关于-moderncpp-的特性)
    - [`nullptr`](#nullptr)
    - [`constexpr`](#constexpr)
    - [if() 和 switch() 分支中的语法](#if-和-switch-分支中的语法)
  - [内存管理](#内存管理)
  - [函数传参](#函数传参)
- [专题](#专题)
  - [Eigen 库](#eigen-库)
  - [Sophus 库](#sophus-库)
- [知识点](#知识点)

# Background
## Major Version 版本大更新
  - C++98 -> C++11 -> C++17
## RunTime 和 CompileTime
  - 对于解释型语言 Interpreted Language 来说
    - 没有 compile 的预处理阶段，代码直接交给一个 platform-dependent 的 interpreter 来进行解读执行（例如，python 的 Linux 版本和 Windows 版本解释器不同，但是同一段 source code 可以不加修改地在两个平台上 bug-free 运行）
    - 一个 platform-dependent 的 interpreter + 一个 platform-free 的 source code
    - CompileTime 被隐藏了，或者说和 RunTime 混合在了一起
  - 对于编译型语言 Compiled Language 来说
    - 需要一个 compile 阶段来完成预处理，此时代码交给一个 compiler 进行编译，生成只有底层机器才能识别执行的 二进制码executable
    - 但是 可执行文件.exe 是一个 highly platform-dependent 的文件，需要根据当前的平台来进行，因此 complier 也必须是 platform-dependent 的
    - Complie 和 Run 阶段以 executable 为界限被分开了，因此针对任务完成部署后实际工况下 RunTime 的时间很短，这也是 C++ 被工程界偏爱的原因（并非是没有时间成本，而是时间成本被编译阶段分摊了）
  - .i文件 .o文件 .ss文件
## 计算机架构与 RAM 和 ROM
  >（参考[Geeks](https://www.geeksforgeeks.org/random-access-memory-ram-and-read-only-memory-rom/)）
  - 冯诺依曼架构（Von Neurmann）和哈佛架构（Harvard），前者的程序段(Program)和数据段(Data)不加区分；后者的程序段和数据段在架构上独立，各自拥有数据和地址总线
  - RAM 和 ROM 是硬件相关的底层，与计算机使用哪种理论架构没有直接联系
  - RAM 一般用于通用计算机的运行内存，其核心特性就是 volatile，需要接通电源来保持信息，每次开机都会重置：
    - 慢速低功耗（基于 Capacitor）的 DRAM
    - 快速大功耗（基于 D触发器）的 SRAM
    - NVRAM (non-volatile) 是电脑中的磁盘，用于在电脑断电状态下储存信息
  - ROM 一般用于通用计算机的 BIOS 启动程序或者出厂后不需要修改的嵌入式设备，其核心特性是 non-volatile，在断电状态下依然可以永久保存数据：
    - PROM (Programmable)
    - EPROM (Erasable)
    - EEPROM (Electrically Erasable) -> Flash Memory (目前主流的 BIOS/UEFI ROM设备，也用于U盘和SD卡中)
## Memory 中的 Stack 和 Heap
  >（参考$^1$ [UIUC CS225](https://courses.engr.illinois.edu/cs225/fa2022/resources/stack-heap/); 参考$^2$ [StackOverflow](https://stackoverflow.com/questions/37749269/is-the-stack-in-a-cache); 参考$^3$[Cherno's Youtube](https://www.youtube.com/watch?v=wJ1L2nSIV1s)）
  - 计算机中的每一个程序、进程都有各自独立的 stack 和 heap；每一个线程会有自己的 stack，但是所有的线程都会共享同一个 heap
  - Stack 是内存（RAM）中连续分布的块（由于其在运行过程中会经常被读写，因此大概率会被 cache 所标记），通常在高地址处，内存占用顺序**由高到低**；容量小；在程序编译完成后，就已经在 CompileTime 确定不变了，作为程序段当中二进制码的一部分；
    - *是否说明此时出现的 error 算作 compiling error？*
  - Heap 是内存（RAM）中离散分布的可利用的块的集合，由一个链表结构来记录其地址，需要在 RunTime 执行时刻动态分配管理。链表的查询逻辑是**从低地址到高地址**进行查询，寻找**最早出现的**内存长度**大于等于**所需长度的内存块，并将多余的内存空间重新录入注册表中；当内存被 delete 释放后，注册表会被更新，连续的小内存碎片也会被合并为更大的块以防越切割越小 **（注意，delete只对注册表和内存进行管理，我们仍然需要将使用后的 heapPointer 重置为 nullptr，否则其地址依然可以被强制操作，但却不应该被使用）**
  - 在使用时，借助 stack 进行 allocation 会占用空间有限的 stack space，但对应的汇编代码很简洁，仅仅是一条 CPU 指令，因此性能和效率都很高；借助 heap 进行 allocation 时可用空间通常会很大，但对应的汇编代码很复杂，需要调用、查表、寻址、重整内存等等，因此性能和效率很差；因此对于空间很小的基本类型或类，优先使用 stack，对于空间很大的类或数组，优先使用 heap

## 关于 ModernCPP 的特性
### `nullptr`
  - 出现自cpp11，类型为 `nullptr_t`（`decltype(nullptr)` 显示 `nullptr_t`）
  - 可以 implicitly cast to 任意类型的指针，用于避免内存泄露

### `constexpr`
  - 数组的长度必须是一个常量表达式(constexpr)，不能是 变量 或 const 
    ```c++
      #define LEN 10
      char arr_1[LEN]; // valid
      int len(10);
      char arr_2[len]; // invalid
      const int len(10);
      char arr_3[len]; // invalid
      constexpr int len(10);
      char arr_4[len]; // valid
    ```
  
  - 可以作为返回类型的修饰符使用递归（cpp11 不支持局部变量/循环/分支，cpp14以上可以编译通过）
    ```c++
    constexpr int fibonacci(const int n) {
    return (n == 1 || n == 2) ? 1: fibonacci(n-1) + fibonacci(n-2);
    }
    ```
### if() 和 switch() 分支中的语法
  - 可以在 if() 中使用类似 for(int i = 0; ; ) 的方式定义分支作用域之内的临时变量
  - 好处是，避免在 if() 之外定义一个以后用不到的变量，便于维护
  - switch() 同理

## 内存管理
  - C++ 中一个常见的错误是在函数调用中返还 stack 对象的地址，C++代码示例：
    ```c++
    Cube* createCube() {
      Cube c(20);
      return &c; // return the address of a new Object created in stack
    }
    int main() {
      Cube *c = createCube(); // Object has run out of scope
      double r = c->getVolume();
      double v = c->gerSurfaceArea();
      return 0;
    }
    ```
    此时的指针`c` 指向的对象已经在 stack 中随着函数的返回而被释放了，因此后续是无效对象，指向了 stack 中一个非法的地址。以下是两种正确的操作方式：
      - （其一）在调用函数中使用 new 来进行 heap 上的内存分配，注意需要在主函数中使用 delete 来配合其释放
      - （其二）先在主函数 stack 中声明对象，并引用传参到被调用函数内，然后进行赋值或者其他操作，换言之，在函数外创建对象
  - 这里对比另一种面向对象语言 Java，Java 默认对于所有对象均使用 `new` 来创建（String 等特殊类(primitives)除外），因此可以在 helper function 中使用 `new` 创建对象并返还其指针的，并且 Java 具有 garbage collection 机制，无需在特定位置使用 `delete` 就可自动回收在函数调用中使用 `new` 新建的对象
  - **智能指针**：一种利用类的封装特性来解决 heap 和 stack 之间矛盾的典型案例。智能指针本质上就是一个类，满足：
    1. 构造函数 smartPtr(){} 中使用 `new` 方法在 heap 中分配内存
    2. 析构函数 ~smartPtr(){} 中使用 `delete` 方法在 heap 中释放内存;
    - 由于类在 stack 中被释放时会自动调用析构函数，因此可以做到表面上在作用域中使用变量声明的方法从 stack 中分配内存给智能指针（优点：**方便**），但底层却从 heap 中分配内存给真正的对象（优点：**节省有限的栈空间**）
## 函数传参
- 在函数/类的参数传递中，左值引用(\&) 和 指针传参(\*):
  - 左值引用(&)，本质上就是一个别名(alias)，在被调用函数中改变引用对象，函数外的原对象也会随之改变 
  - 指针传参(\*)，本质是对于指针变量（地址）的拷贝，在被调用函数中改变指针**自身的指向**，函数外的原对象并不会影响
    - *但改变指针所指内存的内容时，原对象会随之改变*
    - *如果想要实现和引用相同的效果，可以使用二次指针（指针的指针\*\*）或指针的引用（\*\&）*
-  左值引用(\&) 和 右值引用(\&\&)
   -  左值引用是一个长生命周期，具有可查询地址的变量
   -  右值引用是一个临时生命周期，无法索引地址的变量
   -  使用 std::move(lvalue) 可以把左值转化为右值，随后进行移动语义，实现在内存开销很大的变量之间进行资源转移（否则需要使用拷贝构造/赋值函数，生成一个额外的对象来赋值）
      -  细节一：std::move() 的源对象指针不需要再 delete 了，因为其内存并不需要释放，而是被目标对象的指针来接管了，但需要在移动构造函数中对源对象的指针置空为 nullptr，避免其后续被继续使用

# 专题
## Eigen 库
> 参考清华大佬的博客 [zxl19's blog](https://zxl19.github.io/sophus-note/)

## Sophus 库
> 参考清华大佬的博客 [zxl19's blog](https://zxl19.github.io/eigen-note/)

# 知识点
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