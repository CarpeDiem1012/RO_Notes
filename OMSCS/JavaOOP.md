- [History and Origin](#history-and-origin)
  - [Motivations:](#motivations)
  - [Features:](#features)
- [Compiler v.s. Interpreter -- Java's Hybrid Mode](#compiler-vs-interpreter----javas-hybrid-mode)
  - [Should-know](#should-know)
  - [Should-master:](#should-master)
- [Basis](#basis)
  - [Concepts](#concepts)
  - [Type Coversion](#type-coversion)
  - [Predined Classes](#predined-classes)
  - [Gargage Collection](#gargage-collection)
  - [String](#string)
  - [I/OStream](#iostream)
  - [Package](#package)
  - [Scope](#scope)
  - [Method](#method)
  - [Overwrite](#overwrite)
  - [Dicision and  Condition](#dicision-and--condition)
    - [Relational and Logical Operator](#relational-and-logical-operator)
- [OOP](#oop)
  - [Behaviors \& State](#behaviors--state)
  - [Constructor](#constructor)
  - [Static](#static)
  - [Accessor and Muatator](#accessor-and-muatator)
  - [Constructor Chain](#constructor-chain)
  - [Super](#super)
  - [Overriding](#overriding)
  - [Upcasting \& Downcasting](#upcasting--downcasting)
  - [Final](#final)
  - [Abstract](#abstract)
  - [Interface](#interface)
  - [Exception](#exception)
- [必备知识](#必备知识)
  - [Generics](#generics)
  - [Anonymous Inner Class](#anonymous-inner-class)
  - [Functional Interface](#functional-interface)
  - [Lambda Expression](#lambda-expression)
  - [Pass by Value/Reference](#pass-by-valuereference)
  - [Primitive Type \& Literal Type \& Class Type](#primitive-type--literal-type--class-type)
- [Quiz \& CodingError](#quiz--codingerror)
- [Terminology](#terminology)


# History and Origin

## Motivations:
- The existing C++ and C are efficient ***but too flexible to control***
- Increasing demand of safter computing on devices

## Features:
- Avoid much of the memory and security issues
- Automatic memory management

# Compiler v.s. Interpreter -- Java's Hybrid Mode

## Should-know
- 汇编语言生成 intermediate binary files，不同的 platform 需要不同的 compiler 根据不同的 instruction set （例如 ARM 和 x86）生成不同的 binary files，编译和执行是 offline 分开的
- 解释型语言不生成 intermediate files，不同的 platform 只需要不同的 interpreter 就能执行相同的 source code，由于不生成中间文件，一边翻译一遍执行 on the fly
- 高级语言 platform independent 但 machine code denied，二进制文件 machine code friendly 但 platform dependent
- Java 通过引入一种面向 interpreter 但更 low-level 的中间文件 Bytecode，做到了既 platform independent 又 machine code friendly，用 compiler 的方式来做着 interpreter 的工作
- Java intergrate advantages of compied languages, the execution efficiency, and that of interpreted languages, the independency from platform regardless of hardware-specific instruction set.

## Should-master:
```shell
ls
HelloWorld.java

javac HelloWorld.java # pseudo-compile
ls
HelloWorld.java HellowWorld. # 生成更接近 Bytecode

java HelloWorld # 注意不要带着 .class 扩展名
```

# Basis
## Concepts
- Syntax is the representation of all rules to follow in the programming language, e.g. identifiers, reserved words, symbols.
- Semantics is the meaning of statements.
- Errors
	- Compiler Errors: 只检查 Syntax Errors，例如 `myValue = 10/0` 是语法正确、语义失效的，但是 Compiler 不会报错，换言之，可以 javac 顺利生成 Bytecode 文件，但是 java 执行时回报错。
	- Runtime Errors：原因多样，除了上述情况还可能的原因是 out of memory、在多个资源合作时过早中断进程
	- Logical Errors

## Type Coversion
- Promotion
- Assignment
  - 能否数据类型转换不依据内存长度，而是看对应的范围，例如：
    - int 和 float 都是 32 bits 型数据，但前者表达范围比后者小
    - long 是 64 bits 而 float 是 32 bits，但前者可以转换为后者，而后者不能转换为前者
  - `double > float > long > int > short (!= char) > byte`
- Casting
  - 使用 parenthesis 来进行 explicit conversion，向 compiler 表示你已经意识到该转换，避免跳出精度缺失的 compiler error

## Predined Classes
由于 class variables (reference variables) 拥有更复杂的结构，在 java 中对其使用一种特殊的内存管理模块叫做 heap（堆）。因此，其和 primitive type variables 在声明阶段的意义是不同的：
```java
// 两者都是在内存中开辟了一个区域用于存储变量
int aNumber; // declaration 确实代表了 aNumber 就是一个真实的 int 变量
String aString; // 代表了 aString 是在 heap 中用于存放该变量的地址（引用）
```

## Gargage Collection
自动化完成了 C++ 中的 new delete pair

## String
- `String` 类是 immutable 的，处于安全性考虑（小知识：JVM是使用 String 类来记录执行过程中的变量名的）
- `String.substring()` 左闭右开，out of index 不会是 compiling error 而是 runtime error
- `String.equal(String another)` 检查 sequence of characters 是否相同；`String == String` 检查 address of object 是否相同
- 两种 instantiation 方法：
  - `String aString = "Liangchen";`
  - `String aString = new  String("Liangchen");`
  - 相同点：都完成了初始化
  - 不同点：literal assignment 方法触发了 Java 特有的 String Interning 机制。Java 会将第一次见到的 String 储存在 heap 中的 string constant pool 中，当第二次见到时，会直接使用原有 object 的共享地址创造 alias
  - 例子：
    ```java
    String aString = "Liangchen";
    String bString = "Liangchen";
    String cString = new String("Liangchen");
    aString == bString; // True
    aString == cString; // False
    ```
- 关系操作符不适用于 String

## I/OStream
- stream 收集 IStream 输入的所有字符并转义（以回车为终止）
- tokens 默认的 delimiter 是 whitespace（space \t \n \r）
- `Scanner.next*()` 函数会实时触发中断并且等待 IOStream，处理后剩余的部分会储存在 Scanner 的 buffer 当中
- `Scanner.nextInt()`，`Scanner.next()` 会识别并提取 `\n` 之前的部分，把 `\n` 留给后面的 next operator 来处理；`Scanner.nextline()` 会识别并提取 `\n`，处理过后会消除掉 stream buffer 中的 `\n`

## Package
- hierachical structure with subpackage encapsulated within
- 允许使用 asterisk wildcard
- asterisk wildcard v.s. fully-qualified names，只会在 compile time 时存在 overheaded loading time，但不会在 runtime 时有任何区别

## Scope
- 就算外部有 for while if 等关键词，对于 single statement 而言必须要一个 `{}` 才能将其划分在一个 scope 内，否则属于外部的 scope

## Method
- 关键字 pubic 表明了其 visibility，可以被当前所在 class 之外的其他 class 调用
- 关键字 static 表明了其作用域，用于描述不能？不需要被 instantiated 的、仅仅需要调用 class 本身的抽象类

## Overwrite
- 方法：同样的函数名，不同的关键字、返回类型、形式参数（数量、类型、顺序），但至少形式参数一定要有不同，例如：
  ```java
  public static boolean searchArray(String target, String[] array)

  boolean searchArray(int target, int[] array)
  ```
- 底层原理：
  - At Compile Time：Java compile 在看到可重载方法调用时，会自动寻找并配对正确的函数体，然后生成 error-free 的 bytecode
  - At Runtime：JVM 已经知道该执行哪一个重载方法了
- 例子：常用的 println() 和 String.indexOf() 就应用了重载原理
  ```java
  System.out.println("90"); //calls println(String)
  System.out.println(90); //calls println(int)
  System.out.println(90L); //calls println(long)
  System.out.println(90.0F); //calls println(float)
  System.out.println(90.0); //calls println(double)
  System.out.println('9'); //calls println(char)
  System.out.println(true); //calls println(boolean)
  ```

## Dicision and  Condition

### Relational and Logical Operator
- Java interpreter 会从左向右执行 logics，于是存在一种节约潜在的 short-circuit。当多个 logics 复合在一起时，优先把执行成本低的写在左侧，成本高的处理放在右侧（例如读取网页或调用复杂函数）：
  ```java
  simpleLogic && callMethod();
  simpleLogic || callMethod();
  ```
- Java 判断条件的 boolean 不能用 zero 和 non-zero 代替，boolean 必须是 true/false
- switch 语句中 case 后面记得写 break，否则会接着执行下一个 case

# OOP
- 三大特性：encapsulation、inheritance、polymorphism
- Encapsulation：
  - Every instance data should be private.
  - Client of class should only manipulate these variables using public methods
- Inheritance:
  - 一个 superclass 可以有多个 subclass，但一个 subclass 无法继承多个 superclass，此时只能遵循 maximum similarity 原则来选一个作为 actual parent
- Polymorphism:
  - 真实类型取决于 instantiate 阶段跟在 `new` 操作符后面的类型，而不是 declaration 阶段申明的类型
    ```java
    Canine pixy;
    pixy = new Poodle();
    // pixy 的真实类型是 Poodle，但同时具有 Canine 所具有的一切属性，因为是 superclass
    ```
- class 可以嵌套定义
- 所有 java 的类都有内建函数 obj.toString() 返回一个带有类名和哈希值的 String 类型。假如直接对 obj 使用 println() 则会默认调用 toString() 函数
- 重载 toString() 方法可以帮助我们使用 println 来自定义输出

## Behaviors & State
- helper methods 通常是 static 的
- behaviors methods 通常是 public 的，被称作对象的 interface

## Constructor
- 一旦自定义了构造函数，默认的 new 方法就不再适用了

## Static
- 静态方法只能调用静态变量，不能直接和非静态（instance）之间互动
- static 成员函数和变量可以作为 “类的特性”，内化于每一个 instance 之内，不会因为 instance 的创建而拷贝依存在新的对象之中，且不能被 instance 调用或修改
- 但是 static 函数和变量可以作为 “类的特性” 被自己和所有子类在定义方法时调用

## Accessor and Muatator
- accessor 使用 public 方法来读取类私有的成员变量
- mutator 直接对于类内部成员进行编辑操作

## Constructor Chain
- 遵循 Don‘t Repeat Yourself 原则，避免书写多个功能相似的构造函数
- 假如功能完全重合，一个更简单的构造函数可以通过调用更复杂的构造函数来实现其功能，此时使用 `this` 来指代复杂的构造函数
  ```java
  public anObj(int a, int b){
    this(int a, int b, 20);
  }
  ```
- 使用 `this` 关键字来代表当前成员变量/成员函数所属类的 instance，借此实现在方法A中直接调用方法B
- 在类的函数体中，当形式参数的名字与类成员变量名字重复时，该名称仅用于指代形式参数（成员变量被形式参数 shadowed），此时就需要借助 `this` 关键字来调用具有相同名称的类成员函数

## Super
- `super()` 在 subclass 的构造函数中只能出现在第一行
- 假如 subclass 的构造函数中没有 `super()`，则默认执行一次 superclass 的缺省构造函数。因此，假如 superclass 中重载了构造函数，需要记得在 subclass 中显示地调用 `super()`

## Overriding
- 在 subclass 中重新定义 superclass 的成员，两者必须拥有完全相同的函数签名（返回类型，参数类型和数量）
- 假如不满足上述条件，则视为对于一种**函数重载**（逻辑上可以理解为先继承父类的函数，再对其进行多功能的拓展）
- 特别注意！overriding 可以使用不同 visibility 的方式，但需要遵循一条原则：任何在 superclass 上奏效的方法一定也会在 subclass 上奏效。例如：superclass 中定义为 private 的成员，允许在 subclass 中改写为 public，但是反之是禁止的。

## Upcasting & Downcasting
```java
Person p; // 已知 Person 是 Student 的 superclass
p = new Student(); // p 的名义类型是 Person，但实际类型（引用地址指向）是 Student
p.run();

// Case 1:  Student 在继承之后覆写了 run() 方法，
// 则，执行的是 Student 类的方法，而不是 Person 类的

// Case 2 (Dynamic Binding):  Student 没有重定义 run() 方法，
// 则，执行的是 Student 在关系树上游距离其最近的拥有 run() 的类的 run()，因此不一定是 Person 类的（除非 Person 就是）

// Case 3:  Student 在继承之后额外定义了 run() 方法，
// 则无法执行，因为 p 名义上是 Person，而 Person 没有 run()，需要做 downcasting
(Student)p.run();

// Case 4:  Student 覆写了 run() 方法，
Person p = new Person(); // 已知 Person 是 Student 的 superclass
(Student)p.run(); // compile time 的 Downcasting (Student)p 是合法的
// 但 runtime 无法执行，因为 p 实际上作为 Person 类，无法被 downcasting 成 subclass
```

- Up or Down 在形式上都可以，但在 runtime 时需要满足 actual class 的 upcasting 才是真正合法的
- 换言之，downcasting 的本质是一种 declaration 和 instantiation 不一致造成的幻觉。downcasting 只是名义上的类型，实际类型只能做 upcasting。

## Final
- `final` 修饰的方法不可 overriden 或者 overwritten
- `final` 修饰的类不可 subclassed

## Abstract
- 类和方法都可以是抽象的
- 具有抽象方法的类必须也声明为抽象
- 抽象的方法必须被所有层级下的 subclass 都定义一遍（被 override 的抽象方法不会随着继承关系而延续）

## Interface
```java
public interface Imposter() {
  public int aInt; // 这是非法的！
  public static final int AINT=100; // 这是合理的！

  public abstract void freeze(Player p); // 等同于 void free(Player p); 只声明不定义
  static void print(){
    Sysytem.out.println("I am here!");
  } // 直接定义静态方法
  default void sabotage(Player p) {
    Sysytem.out.println("Ouch!");
  } // 直接在此处定义，而不是先声明，再在 implementor 中定义
}
```
- 使用起来相当于一个 abstract 类
  - interface 无法声明/定义 instance variable 对象的成员变量
  - 可以定义类的 static variable（默认 modified as `public static final`）
  - 可以声明 instance method 对象的成员函数（默认 modified as `public abstract`）
  - 可以定义 default 方法
  - 可以定义静态方法
- default 方法可以直接在 interface 中进行定义，同时不需要 recompile 使用了该 interface 的所有 implementor，只需要 compile 当前这个 interface 即可
- interface 可以嵌套，此时使用的是 `extends` 而不是 `implements` 关键词
- 同一个 class 可以 `implement` 多个 interface（然而一个 subclass 最多只能继承一个 superclass）
- 同一个 interface 可以 `extends` 多个 interface

## Exception
- An exception represents an error that ***occurs at runtime***. 
- Throwable Hierarchy 分为 Error 和 Exception 两类；其中 Error 是一类系统无法修复的错误类型，例如 VirtualMachineError 和 OutOfMemoryError；而 Exception 是系统可以借助某些手段创建 handler 的类型。
- Exception 分为 unchecked（可以顺利compile） 和 checked（explicitly throw otherwise not compiled），对于文件管理来说，一定要在相关 method 末尾加上 `throws FileNotFoundException`，否则将直接无法 compile
- 

# 必备知识
## Generics
类似 C++ 中的 Template，是一种通用型的 class（或者interface），可以通过参数化 <> 来指定其对象和功能
- Comparable\<O>
  - .compareTo(O)
- generic 可以嵌套、多重使用，例如如下 declaration 都是合法的：
  ```java
    public class AAA<X, Y>
    public class BBB<T extends Comparable<T>>
    public class CCC<T extends Comparable & List > // 可以使用 extends 来对于 T 的 superclass 做限定，注意无论是 superclass 还是 interface 都使用 extends，并且用 & 来连结
    ``` 
- List\<E>
  - .add()
  - .remove()
  - .contains()
- ArrayList\<elementType>(initialCapacity)
  - 是一种使用 Array 作为底层数据结构的 List 的 implementation，其中使用一个叫做 elementData 的 Object[] 的 Array 来进行动态管理（因为 Object 是所有 Class 的 superclass）
  - 初始化方法 
    ```java
    ArrayList<elementType> aList = new ArrayList<elementType>(initialCapacity)
    ```
  - 初始化时不指定长度的时候 initialCapaticity 默认为 10
  - 指定 <elementType> 后的成为 parameterized type，不指定则称作 raw type
  - 注意 `int` 或 `double` 的本质是 primitive type 而非 Object，但是 ArrayList 的 autoboxing 特性允许使用 `Integer` `Double` 作为 Object 来参数化 ArrayList，同时自动将每一个元素和 `int` 或 `double` 在赋值和读取的时候映射
  - 缺点：这种结构并不是动态的，我们总是要 predefine an internal array （capacity），当元素数量随着 add 终于超出预期时，就需要在重新创建一个 internal array，并对旧有的 internal array 进行一次 copy paste，消耗大量的内存和时间
- LinkedArrayList
  - 链表是一种动态的、非连续的数据结构
    - linkedArray.head 是唯一的线索
    - 空链表：head = null
    - 单元素链表：head.next = null
    - 多元素链表：head != end, end.next = null
    - 元素遍历核心：current = current.next
    ```java
    public class GenericLinkedList<E> {
    private class Node<E> { //inner class
        E data;
        Node<E> next;
        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
    private Node<E> head; //the only instance variable of the list
    public GenericLinkedList() {
        head = null; //the list starts off empty
    }
    ```
  - 缺点：对链表进行索引时，底层逻辑是在按照指针顺序遍历链表，因此需要花费 O(n) 的线性时间。对比下，对列表的索引可以直接索引，底层逻辑是在连续的内存地址上对首地址做 offset，因此只需要花费 O(1) 的常数时间。
- 直接使用 Generics Type 来生成 Array
  ```java
  T[] backingArray = new T[10];
  ```
  是错误的！
  ```java
  T[] backingArray = (T[]) new Object[10];
  ```
  需要先生成 Object 类型的 Array，再 downcasting 成为下游的 T[] Array


## Anonymous Inner Class
没有名字的 Class，直接使用 interface 的句柄来定义
- 需要在函数体 {} 之内填补全部的 abstract method
- 需要在 {} 之后加上分号 ;
## Functional Interface
只有一个 abstract method 的 interface（因此也只需要补足唯一的抽象方法，使得其功能上和函数 function 很相似）
## Lambda Expression

## Pass by Value/Reference
Java 全部采用 pass by value。下面举例分析两种情况：

  ```java
  public static void main(String[] args) {
      Container count = new Container(0);     // step 1: create new Container named count
      helper(count);                          // step 2: call the helper method on count
      System.out.println(count.getItem());    // step 4: print out the value of count
  }

  // 情况1：x 作为一个 reference variable 的 copy，本身也是个地址，但是在创造之初与 count 指向了同一处内存，函数内的操作使 x 重新指向了另一块地址
  public static void helper(Container x) {
      x = new Container(x.getItem() + 1);     // step 3: create a new object reference
  }

  // 情况2：函数内的操作没有对于 x 进行直接操作，而是对于其 refer to 的地方 ———— 那个 class Container 进行了操作，因此起到了看似 pass by reference 的效果，但实际上是一个 pass by copy value
  public static void helper(Container x) {
    x.setItem(x.getItem() + 1);             // step 3: set x's item to x's item + 1, modifying the original object
  }
  ```

## Primitive Type & Literal Type & Class Type
- "class Object 是 Java 中一切的 superclass" 这种说法是不完善的，因此 primitives 和 literals 并不是 class 也没有继承 Object
- Similarly to primitives, Strings are a very common typing, so rather than creating new objects every time a String is created, **it just creates literals/constants that are stored in a String Pool for faster access.**
- 使用 literals 方法生成的 String 储存在 StringPool 中，内容相同的 literals 会公用同一个地址，使用 `==` 操作符来检查 reference 的结果为 true，因此不存在两个内容相同的 literals，不论作为左值还是右值

# Quiz & CodingError
- 相比于 short 类型，byte 类型可以表示最小的数字 [错，这两种都是有符号型，分正负]
- 实例化 String 的方法
  ```java
  String aString;
  aString = "Hi there";

  String aString = "Hi there";

  String aString = new String("Hi there");
  ```
- 注意 quiz 中的 whitespace 陷阱
  ```java
  for (int i = 50; i > 0; i -= 10) {
   System.out.print(i);
  }

  错误：output>>50 40 30 20 10
  正确：output>>5040302010
  ```
- 注意 local variable 的 scope 严格跟着 {} 走
- 和 Python 不同，使用 `for (type entity : Array)` 时，entity 只是一个 copy，只能用来 search 不能用来 overwrite
- java 的函数是引用传值，会直接修改实际参数本体的数据。但是 return 时返回的是 copy
- 生成范围 [min, max] 内随机数 `(int)(Math.random() * ((max - min) + 1)) + min`
- 注意语言陷阱：Every Java class has a default no-parameter constructor in addition to the ones you write.
- 对于声明后未赋值的变量，只有类的成员变量才会自动赋值，直接在 main 函数中声明的变量不会自动赋值
  ```java
  // 反例
  public static void main() {
    int aInt;
    String aString;
    System.out.println(aInt); // 报错，not instantiated
    System.out.println(aString); // 报错，not instantiated
  }

  // 正例
  class Test() {
    private static int aInt;
    private static String aString;
    System.out.println(Test.aInt); // 默认为 0
    System.out.println(Test.aString); // 默认为 null
  }
  ```
- The private modifier enforces encapsulation, static does not.

- 关于排序算法：insertion ordering selection ordering binary searching

- 关于 interface Comparable<T>，假如某个 Object 使用了该模板，并且 override 了 compareTo(T O) 方法，就可以使用 Array 类的静态方法 Arrays.sort(Object[] O) 来进行（升序）排序

- An exception represents an error that occurs at runtime.
- `int sum = new Integer(3);` 是可以 compile 的

# Terminology
- promotion `(float)10.12`
- formal parameter & actual parameter (argument)
- client class