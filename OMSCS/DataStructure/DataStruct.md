- [Basis](#basis)
  - [Iterable and Iterator](#iterable-and-iterator)
    - [Iterator\<T\>](#iteratort)
    - [Iterable\<T\>](#iterablet)
  - [Comparable and Comparator](#comparable-and-comparator)
    - [Comparable\<T\>](#comparablet)
    - [Comparator\<T\>](#comparatort)
    - [Sorting](#sorting)
    - [排序算法中的稳定性](#排序算法中的稳定性)
  - [Big-O Notation](#big-o-notation)
    - [Assumptions](#assumptions)
    - [Principles](#principles)
    - [Average Time Complexity](#average-time-complexity)
- [Abstract Data Type (ADT)](#abstract-data-type-adt)
- [List ADT](#list-adt)
  - [ArrayList](#arraylist)
  - [LinkedList](#linkedlist)
    - [Singly (with head, tail, size)](#singly-with-head-tail-size)
    - [Doubly (with head, tail, size)](#doubly-with-head-tail-size)
    - [Circly (with head, size, no tail)](#circly-with-head-size-no-tail)
  - [Priority Queue ADT](#priority-queue-adt)
  - [Deque ADT](#deque-adt)
  - [Tree ADT](#tree-adt)
- [Data Structure](#data-structure)
  - [Arrays](#arrays)
    - [特性](#特性)
  - [LinkedList](#linkedlist-1)
    - [分类](#分类)
  - [Stack](#stack)
  - [Queue](#queue)
  - [Tree](#tree)
    - [特性](#特性-1)
    - [分类](#分类-1)
    - [Traversal](#traversal)
    - [BST](#bst)
    - [Heap （Binary）](#heap-binary)
  - [SkipList](#skiplist)
- [头脑风暴](#头脑风暴)

# Basis
## Iterable and Iterator
### Iterator\<T>
- 需要补足以下方法
```java
// compulsory
public abstract T next(); // 注意这里 return 的是 current.data 而不是 current.next.data！！！
public abstract boolean hasNext();
// non-compulsory
default void remove(){
    // Error Exception
}

```
- 需要 `import java.util.Iterator`
- 使用时需要结合 while loop 做遍历
```java
while(iter.hasNext()) {
    item = iter.next()
}
```
- 只依赖 data structure

### Iterable\<T>
- 内部已经集成了 Iterator
- 更直接可以直接使用 for-loop
```java
for (T element : myIterable) {
    ...
}
```
- 同时需要依赖 data structure 和 Iterator

## Comparable and Comparator
### Comparable\<T>
- 需要重载 compareTo(T template) 方法
- 是属于某个 Class 内部的 implementation，对于同类型的对象进行比较
- 单个形式参数
### Comparator\<T>
- 需要 `import Java.util.Comparator`
- 需要重载 compare(T template1, T template2) 方法
- 是独立存在的 Class 的 implementation，用于比较某类中的两个对象
- Comparable 和 Comparator 之间没有依赖关系
### Sorting
借用 Arrays.sort 可以对于以上 implementation 进行排序。使用时需要 `import java.util.Arrays`
- 对于 Comparable
  ```java
  Arrays.sort(myArray); // myArray 中的 class 是 Comparable 的 implementation
  ```
- 对于 Comparator
  ```java
  Arrays.sort(myArray, new myComparator()); // 
  ```

  ### 排序算法中的稳定性
  当多个待排序的元素拥有相同的值时，经过排序后，各元素之间的相对位置不发生改变，则认为这种排序算法是稳定的

## Big-O Notation
### Assumptions
- Primitive Operation executes in constant time（这是建立在现代计算机底层的算法优化之上，但事实上，当待处理数据很大很复杂，简单的四则运算也不再 trivial，因为底层硬件没有相应的 low-level instruction optimization，此时不能再视为 constant time）
- Counts the number of Primitive Operations
- the most accurate Worst-Case analysis (with the worst set of data, worst performance) 去评估最差情况下的 Upper Bound
### Principles
- Drop Constant Factors
- Drop Lower Order Terms
- 有
$$
O(2^n) > O(n^3) > O(n^2) > O(nlog(n)) > O(n) > O(log(n)) > O(1)
$$
- 特别地，对于 $log$ 而言，计算机中习惯以 2 为底数，但实际上由舍弃常数原则，底数并不重要，因为换底公式可以把底数转化为一个常数因子
$$
log_m(n) = \frac{log_2(n)}{log_2{m}} = C \cdot log_2(n) \rightarrow O(log_2(n)) 
$$
- 常数因子项并非不重要。之所以在定性分析时忽视掉常数因子，是因为 Big-O 是用于评估算法随着输入增加时的 scaling 能力的，而常数项因子并不会增长，换言之，Big-O 是一种 “在自己和自己做对比之中” 确立的指标，而非通过和其他算法的比较。但在实际工程当中，则并非如此，假如算法 A 进行了 $10,000,000,000n$ 次运算，而算法 B 进行了 $2n^2$ 次，则显然在 $n<5,000,000,000$ 时，算法 B 更优。因此，不要惊讶于某些研究者试图大费周折把常数因子从 2 降低到 1.5，因为有些时候那是值得的。

### Average Time Complexity
> When working with probability, you should always be asking yourself "What is the randomness being used?"
- 求一类算法在某种条件下的 Probablitic Expectation of Cost $E(Cost)$
- 只要涉及到概率问题，首先应该关注但却常常被人们忽视的问题是：这种随机性是体现在哪一个层面上的？选取不同的随机性对象会产生完全不同的模型和结果！
- 对于 Bianray Search Tree 搜索的 $O(log(n))$ 复杂度，随机性体现在于一个 sequence 的排列组合中，每一种 permutation 唯一对应一个 sequence，当我们按照 BST 的规则将其填入，就会得到一个唯一的 tree。于是平均复杂度就是 Weighing the performance of **A SINGLE OPERATION** over all possible configurations of a tree.

# Abstract Data Type (ADT)
Think of an ADT as being analogous to Java's interfaces in that they set the framework for what operations (methods) are available as well as what these operations do, but they leave the actual implementation details abstract.
# List ADT
List 是一种 ADT，规定了其必须拥有的方法包括：
- addAtIndex(int index, T data)
- removeAtIndex(int index)
- get(int index)
- isEmpty()
- clear()
- size()

## ArrayList
- 可动态扩展，但是需要通过 resize 来 reallocation
- size 是 non-null element 的数量
- capacity 是在 reallocated 之前可以容许的最大 element 数量
- contiguity of elements，之间不能有 null space
- element 必须从头开始赋值（zero-aligned）
- addToBack 的复杂度是 O(1)
- resize 的复杂度是 O(n)
- addToBack 的时均综合复杂度是 O(1)，这里使用 amortized analysis 来将极少数情况下需要 resize 的复杂度分摊到一般情况下的 O(1) 中
  - 在 Java 的 ArrayList 中，每次 resize 会扩展 capacity 到之前的一倍
  - 因此，对 capacity 为 n 的 ArrayList 进行 n 次 addToBack() 操作的时均复杂度为
    $$
    \frac{2n + 2n}{n}=4=O(1)
    $$
  - 其中，每次普通的 addToBack() 中包含两层操作：1.写入新内容 2.对 size 参数增一
## LinkedList
### Singly (with head, tail, size)
- addToFront -> O(1)
- addToBack -> O(n)
- removeFromFront -> O(1)
- removeFromBack -> O(n)
### Doubly (with head, tail, size)
- addToFront -> O(1)
- addToBack -> O(1)
- removeFromFront -> O(1)
- removeFromBack -> O(1)
### Circly (with head, size, no tail)
- addToFront -> O(1)
- addToBack -> O(1)
- removeFromFront -> O(1)
- removeFromBack -> O(n)

## Priority Queue ADT
Priority Queue 是一种 ADT，规定了其必须拥有的方法包括：

## Deque ADT
Priority Queue 是一种 ADT，特点是可以从两端对于 Stack/Queue 进行操作。规定了其必须拥有的方法包括：
- addFirst()
- addLast()
- removeFirst()
- removeLast()

## Tree ADT

![avatar](Images\Complexity_List.png)

# Data Structure
Actual concrete implementations of data handling for ADT are called Data Structures. 例如：List 是一种 ADT，但我们可以使用不同的 DataStructure 来实现，从而使其在 List 的基础职能之上拥有不同的特性

## Arrays
### 特性
- statically allocated
- contiguous blocks of memory（并非是每个cell都要连续存储数据）
- data access in O(1) with Pointer Arithmetic
- data search in O(n)

## LinkedList
### 分类
- SinglyLinkedList
- DoublyLinkedList
- CirlularLinkedList

## Stack
- Array Stack
- Linked Stack

## Queue
- Array Stack
- Linked Stack

## Tree
### 特性
- full: 每个 node 都有两个 child node
- complete: 每个 level 都被填满，上一层没有填满之前不能生长出下一层；leaf node 之间没有 gap，最后一层必须从左至右紧凑
- degenerate: 单链表
### 分类
- Binary Tree
  - 使用 recursion 来做 traversal
- Binary Search Tree（BT + 左下角永远是 small subtree，右下角永远是 large subtree）
  - 适合用来做 binary search => O(log(n))
  - 取决于树的形态，假如 BST 退化成一个 degenerate tree，就反而需要 O(n)
### Traversal
- Depth Recursive (Stack-based)
  - Preorder (CLR): 可以唯一定义一个 BST 的结构，倾向于先访问距离 root node 更近的 node，常用于树的拷贝和增长。本质上是一种 hybrid 的思路，是 depth search 中最贴近于 breadth search 的方法
  - Postorder (LRC): 可以唯一定义一个 BST 的结构，倾向于先访问距离 leaf node 更近的 node，常用于 leaf node 的删除
  - Inorder (LCR): 倾向于 left-to-right 的访问顺序，对于 BST 的访问历史可以直接 in ascending order 给出 node 的排序，但无法唯一定义 BST 的结构！
- Breadth Iterative (Queue-based)
  - Leverorder: 可以唯一定义一个 BST 的结构，使用 queue 完全实现了 preorder 的理想
- 关于树的重建
  - 对于 BST 来说，preorder/postorder/leverlorder 都可以唯一重建一颗 BST
  - 对于 plain BT 来说，inorder + preorder/postorder/leverlorder 可以重建一颗 BT
  - 对于 BST 而言，只要给定一个 sequence 则可以根据每一层选出的分水岭节点，完成重建
    <!-- - preoder 和 levelorder 的访问记录中，第一个 node 一定是 root，随后遵循“一个大，多个小”的顺序
    - postorder 的访问记录中，最后一个 node 一定是 root，随后
    - inorder 的访问记录中，只有 left-to-right 的顺序，无法看出 root 是哪一个，因此无法重建 BST
    - inorder 的实质就是 left-to-right traversal，而 BST 的特点是 left-to-right ordering，本质上两者提供的信息重合 -->
### BST
- Search/Add/Remove:
  - Best case $O(1)$
  - Average case $O(log(n))$
  - Worst case $O(n)$
- Height: $O(n)$
- Average height of BST: 本质上是在问所有构型下的 level 的平均数量，等价于问 search average case，都是 $O(log(n))$
- BST 对应了一个不严格的 Binary Search 过程，其中每一个 root 的选择都不一定是中位数。假如每一个 subroot 都是当前集合内的 median，那 BST 将会是 perfectly balanced
- Remove 时需要考虑的 edge case:
  - 0-child: 直接删除 -> return null
  - 1-child: 继承唯一的 branch -> return current.right/left
  - 2-child: 需要寻找 predecessor 或 successor 作为来接替当前的位置

- 重要的技巧：Pointer Reinforcement Pseudocode 利用多层递归简化逻辑
  ```java
  private Node recursiveRemove(Node curr, T args) {
    // something opration...
    if (data > curr.data) {
      curr.left = recursiveRemove(curr.left, args);
    }
    if (data < curr.data) {
      curr.right = recursiveRemove(curr.right, args);
    }
    // something opration...
    return curr
  }
  ```
### Heap （Binary）
- 满足两个条件：
  - 结构条件：complete binary tree
  - 顺序条件：root 总是比 child 大于等于 (maximum-heap) 或小于等于 (minimum-heap)
- 常常使用 1-indexed backing array 来存储数据，而非像 BST 一样使用 linked node
  - 因此对于 Heap 的 search 本质上可以认为对于 backingArray 的 search
- 对于 parent-node = backingArray[k]
  - left-child = backingArray[2k]
  - right-child = backingArray[2k+1]
  - last leaf-node = backingArray[size]
  - 没有意义的 node -> backingArray[0]
- add/remove 分为两步：先 add/remove to Array，再 reorder to top/bottom
  - best case $O(log(n))$，heap 由于结构上的 balance 使得 best case 和 worst case 都可以做到每层二分，保证了下限
  - average case $O(log(n))$
  - worst case for remove $O(log(n))$
  - worst case for add $O(n)$
- 因为使用了 backingArray 作为底层数据结构，因此 worst case add 是一个 O(n)，原因是伪动态的 ArrayList 需要 resize
- BuildHeap Algorithm
  - 直觉上讲，从零构建一个 n 元素的 Binary Heap 需要连续使用 n 次 O(log(n)) 的 Naive Adding，共花费 $O(nlog(n))$ 的时间
    - 然而，这种 $O(nlog(n))$ 是针对于每一次 adding 都碰巧需要 reorder untill the bottom 的 worst case scenario
    - 在真实的 uniformly randomized sequence 下，n 次 adding 实际上的 average time complexity 是线性时间 O(n)，因为 $O(nlog(n))$ 的概率太小了
    - 说明了 worst case analysis 很多时候会带来误导，过分低估了一个算法在实际工程中的有效性
  - 提出了一种可以保证 worst case linear 的算法：先一次性把 n 个元素填入 backingArray 当中，从最底层拥有 child node 的开始依次 maintain subheap，遍历直至 root
    - best case = worst case = average case = $O(n)$，同时说明这是一类 tight algorithm
    - $O(n)$ 线性时间的证明逻辑：在维护 subheap 时，底层的 node 虽然数量多，但每个 node 需要执行的 log(n) 数量少；顶层的 node 虽然每个需要的 log(n) 比较大，但 node 总数也是呈对数减少，相互取长补短。最后是一个等比乘等差数列的 sequence sum:
      $$ \Sigma_{k=1}^{log_{2}(n)}2^{k-1}\cdot (log_{2}(n)-k) = (log_{2}(n)-k+1)2^k-log_2(n)-1$$
      $$ k=log_2(n)$$
      $$ \Sigma=n-log_2(n)-1$$

    - 相比于 Naive Adding，在 Average 层面上都是线性时间，只快在了 constant factor 上。最大的好处是保证了 worst case 也是线性时间的下限
  - 工程角度上看，两种方法都可行

## SkipList
- 本质上是一种概率角度的 Binary Search，只有当每一层都恰好对半采样且 uniform layout 时才能做到真正的 $log(n)$
- Search/Add/Remove
  - Best case O(log(n))
  - Average case O(log(n))
  - Worst case O(n)

# 头脑风暴
1. In a previous lesson, we briefly discussed the union operation, described as follows: "Consider two MinHeaps of sizes m and n as our input. Output one MinHeap of combined size (m+n) containing all data from both heaps." 
   - What is the worst case time complexity of this operation?
     - $O(m+n)$
     - Thanks to BuildHeap, we can always just rebuild the heap from scratch in linear time. 
   - What is the worst case time complexity of this operation when m=O(1)?
     - $O(log(n))$
     - However, if one is much smaller than the other, it's not worth the effort to rebuild the larger one, and we can instead just add the items from the smaller one to the larger one.

2. In Scenario 1, there are only 248 addresses to consider, and they're in a nice, contiguous block. The rest of the IP address information is superfluous and thus does not need to be stored. A simple shift in the X part of the address should do nicely for a Bit Array. This is in comparison to a HashMap, which will have to deal with hashing and possible collisions. In fact, if implemented well, a HashMap could be made to act like a bit array in this context. In Scenario 2, there are likely many users, with a variety of IP Addresses. Creating an array to hold an index for every possible IP Address is intractable, so some sort of hashing scheme will be necessary.
3. 对于一个使用 closed-opening 来解决 collision 的 HashMap，假如不用 LinkedList 作为 collision 的存储结构，而改用一般的 BST （非 Heap 或 balanced），则 worst case time complexit of resizing is $O(N^2)$