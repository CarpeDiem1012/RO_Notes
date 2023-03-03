
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
- `String` 类是 immutable 的，处于安全性考虑（小知识：JVM是使用 String 类来执行过程中的变量名的）
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
- stream 收集 IStream 输入的所有字符并转义
- tokens 默认的 delimiter 是 whitespace
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
- 函数重载
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

# Dicision and  Condition

## Relational and Logical Operator
- Java interpreter 会从左向右执行 logics，于是存在一种节约潜在的 short-circuit。当多个 logics 复合在一起时，优先把执行成本低的写在左侧，成本高的处理放在右侧（例如读取网页或调用复杂函数）：
  ```java
  simpleLogic && callMethod();
  simpleLogic || callMethod();
  ```
- Java 判断条件的 boolean 不能用 zero 和 non-zero 代替，boolean 必须是 true/false
- switch 语句中 case 后面记得写 break，否则会接着执行下一个 case

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
- 

# Terminology
- promotion
- formal parameter & actual parameter (argument)