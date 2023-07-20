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
  - [HashMap](#hashmap)
  - [AVLTree](#avltree)
  - [2-4 Tree](#2-4-tree)
  - [Iterative Sort](#iterative-sort)
  - [Divide and Conquer Sort](#divide-and-conquer-sort)
- [Pattern Matching Algorithm](#pattern-matching-algorithm)
  - [Brute-force Search](#brute-force-search)
  - [Boyer-Moore (BM) Algo](#boyer-moore-bm-algo)
    - [Scenarios](#scenarios)
  - [Kruth-Morris-Pratt (KMP) Algo](#kruth-morris-pratt-kmp-algo)
    - [Scenarios](#scenarios-1)
  - [Rabin-Karp (RK) Algo](#rabin-karp-rk-algo)
    - [Scenarios](#scenarios-2)
- [Graph Theory](#graph-theory)
  - [Graph](#graph)
  - [数据结构](#数据结构)
  - [Search](#search)
    - [Depth First Search](#depth-first-search)
    - [Breadth First Search](#breadth-first-search)
    - [Time Complexity](#time-complexity)
    - [Space Complexity](#space-complexity)
    - [Dijkstra Algo](#dijkstra-algo)
- [Minimum Spanning Tree (MST)](#minimum-spanning-tree-mst)
  - [Prim's Algo \<- cutting edge property](#prims-algo---cutting-edge-property)
  - [Kruskal's Algo \<- cycle property](#kruskals-algo---cycle-property)
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

- 重要的技巧：Pointer Reinforcement Pseudocode 利用多层递归简化逻辑，相比于 look-ahead 方法，PR 通过层层递归直接对于 base case 进行处理，不需要考虑复杂数据结构下 childNode 的复杂情况
  ```java
  private Node recursiveRemove(Node curr, T args) {
    // something for operation...
    if (data > curr.data) {
      curr.left = recursiveRemove(curr.left, args);
    }
    if (data < curr.data) {
      curr.right = recursiveRemove(curr.right, args);
    }
    // something for operation...
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
- add/remove 分为两步：先 add/remove to Array，再 reorder to top/bottom，注意不管对于 top-down 还是 bottom-up 而言，都只需要沿着交换的那一条 path 单向走到头即可，不需要一边向前，一边回头维护 subTree
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

## HashMap
- Set：只有 key 没有 value 的 unordered set
- Map：包含 (k, v) pair 的 unordered set
- Collision Solution
  - Closed-addressed (External Chaining)：
    - LinkedList
  - Open-addresed (Probing)：
    - Linear (a special-case double hasing with m=1)
    - Quaduatic
    - Double hashing
- 实际上，设计合理的 HashFunction 和 CompressionFunction 其实比绞尽脑汁想 Collision Solution 要有效得多
- 2023 年各个语言内部的 HashMap Collision Implementation
  - C++：
  - Python：
  - Java：
  

## AVLTree

## 2-4 Tree
- B-Tree
- Multiple Search Tree
- Red-Black Tree 

## Iterative Sort
- Bubble Sort
- Insertion Sort
- Selection Sort
- Cocktail Shaker Sort (Bi-directional Bubble)
- Heap Sort

## Divide and Conquer Sort
- Merge Sort
- Quick Sort
- k-th Quick Selection
- LSD Ridex Sort

# Pattern Matching Algorithm
- pattern of length m, text of length n
- 分类：
  - Single Occurance
  - All Occurance
## Brute-force Search
## Boyer-Moore (BM) Algo
- 注意小于等于 for (int idStart=0; idStart <= n-m; ) 
### Scenarios
  - Bigger alphabet：尽量少出现 repetition，可以使得 last occurrence table 里的 occurrence 不被频繁刷新，进而实现最大程度上的跳跃
## Kruth-Morris-Pratt (KMP) Algo
### Scenarios
  - Streaming information：因为永远向前，而 BM 需要在每个 window 内部从后向前遍历
  - Smaller alphabet, More repetition：越多的重复项更有可能产生 prefix-suffix pairs，使得 failure table 内部拥有更多 non-zero element，否则 FT 失去了使用价值
  - 过多的 repetition 会使得 BM degenerate to Brute-force，而没有 repetition 会使得 KMP degenerate to Brute-force
## Rabin-Karp (RK) Algo
### Scenarios
 - 相比于 BM 和 KMP 来说，RK 对于 pattern 的结构没有特别的要求，虽然可能会有 computational overhead，但是表现比较均衡，good adaptability

# Graph Theory
## Graph
- Vertex (`V`) & Edge (`E`) & Order (`||`)
- `Path` & `Trail` & `Walk`
- `Cycle` & `Circuit`
- `Directed` & `Undirected`
- `Self-loop` & `Parallel Edges`
- `Simple Graph` & `Multigraph`
- `Acyclic` & `Cyclic`
- `Disconnected` & `Weakly Connected` & `Strongly Connected`
- Tree = Acylic + Connected, using minimum edges to maintain connectedness |E|=|V|-1

## 数据结构
- Adjacent Matrix O(|V|^2) 适用于 dense gragh
- Adjacent List O(|V|) 适用于 sparse graph
- Edge List (不显式地储存 vertices 信息) O(|E|)

## Search
### Depth First Search
- non-recursive：使用了 stack 结构，对于早遍历的 vertex 的
- recursive：本质上是使用 for 循环来代替了 stack 的作用，使得 parent recursion 的状态能够保留，在成功完成 child recursion 之后再回溯到最近的分叉节点
> 注意这两种 implementation 下的 visiting order 是不一样的，前者先 visit 再 go deeper，后者先 go deeper 再 visit
### Breadth First Search
### Time Complexity
O(|V| + |E|)的理解：对于 DFS 和 BFS来说，本质上都是对于每一个 vertex 的 incident 进行 edge 搜索，逻辑不同而已。其中，对于每一个 vertex 进行的操作是

### Space Complexity
- 对于 stack 和 queue 来说，需要不断存放更新探索过程中还来不及弹出的 buffer vertex
- 对于 recursion 来说，重要的则是 recursion 的层数，因为递归在底层机制上是借用程序代码段的栈指针来进行状态记忆

### Dijkstra Algo
- 形象记忆：大水漫灌迷宫，使用等距离线来确定最优
- 核心 Assumption：贪心算法思想，到达当前 vertext 的最短距离 = min{到达某个相邻节点的最短距离 + 这段 edge 的长度 for 所有相邻节点}，保证当前最优，但无法保证全局最优，直到遍历之前
- 数据结构：
  - Visited Set: HashSet/HashMap
  - Distance Map: HashSet/HashMap
  - Priority Queue: Binary Heap
- Time Complexity
  - 简单版本：
    - 每一轮更新最短距离后，压入 PQ (binary heap) 的元素是当前 vertex 的 edge，因此整个动态过程中， PQ 大小的上确界是 O(|E|)，那么每一次 removal 就近似是一个 O(log(|E|))，而这样的 removal 总共有 O(|E|) 次
    - 因此 total time complexity = $O(|E|log(|E|))$
  - 优化版本（改用存在容纳上限的 min-priority queue）：
    - 其实本质上只需要 dequeue 所有 reachable vertices 数量的次数就可以实现，并不真的需要 O(|E|) 次，但由于 PQ 中存在的 (vertex, distance) pair 并不一定是 optimal 的（Visited Set 中存在的才是 optimal 的），导致维护一个大小为 O(|E|) 的 PQ 会出现同一个 vertex 对应多个 distance 的情况，因此简单版本中认为需要 dequeue O(|E|) 次
    - 现在有一种优化操作，使得每一次 enqueue 的时候，相比于之前的增添一个重复的 vertex，现在可以直接更新那个 vertex 的距离使其为历史最小值，完全去除了冗余，那么 PQ 就不必保持 O(|E|) 的大小了 (并且借助 HashMap 作为 backingArray，可以实现 O(1) 的 search，因此 updating 是一个 O(log n) )
    - 对于 starting vertex 来说，把 reachable vertices（即之间存在 trail）的数量记作 k，因此只要把 k 个 vertices 加入 Visited Set 就可以结束算法。那么使用一个容量上限为 k 的 min-QP 就可以实现目的。弹出/新增/刷新 min-PQ (binary heap) 都是 O(log(k))，但是弹出需要 O(k) 次，而新增/刷新需要 O(|E|) 次（因为依然要考虑目标 vertex 的每一条 edge）
    - 又有 k=O(|V|), 因此 total time complexity = $O((|V|+|E|) \cdot log|V|)$
    - 对于 connected graph，存在 |E| >= |V| - 1，因此简化为 $O(|E| \cdot log|V|)$
    - 对于使用 Adjacent matrix 的情况，是一个 $O(|V|^2)$
- Scenarios:
  - 对于存在 negative weights 的图，可以分为两类：
    - 构成了 negative cycle：沿着 cycle 的 Sum of Weight is negative
    - 不含有 negative cycle 只有 negative weights 
  - 但 Dijkstra 只适用于没有 negetive weights 的 graph，否则应该使用
    - Bellman-Ford Algo -> $O(|V|\cdot|E|)$
    - Floyd-Warshall Algo -> $O(|V|^3)$

# Minimum Spanning Tree (MST)
- 定义：这样一种 Tree，其包含原图所有的 vertices 的同时 total edge weights 是最小的
  - MST 是一个 subgraph
  - Tree 本身属于一种 acyclic connected graph
  - MST 具有两个性质：
    - cutting edge property：对于原图任意一刀 cut，都会生成一对 subgraphs，连接两者的多条 edge 之中最短的那一条一定在 MST 之中
    - cycle property：对于原图中存在的任意一条 cycle 而言，其中最长的 edge 一定不在 MST 之中
> MST 本身比较抽象，只需要记住和利用这两条性质。从原理上彻底理解比较困难，面对大多数判断题只需要找反例就好。
## Prim's Algo <- cutting edge property
  - 类似 Dijkstra，但是 en/de priority queue 的标准是 edge weight，优先小权重的
  - Time Complexity 和 Dijkstra 相同: $O((|E|+|V|)log(|V|))$
## Kruskal's Algo <- cycle property
  - 先对于所有的 edge 做一个 buildHeap 放入 MinPQ (需要 $O(|E|)$)，然后从最小的开始 dequeue，当前 edge 会形成 cycle 的话就舍弃，否则纳入 MST 中
    - 阶段1：构建 heap，即 MinPQ
    - 阶段2：依次 dequeue
    - 阶段3：cycle detection，然后加入 MST
  - Time Coplexity: $O(|E|log(|E|))$
    - 优化1：对于 disjoint sets 使用 path compression（底层逻辑使用 pointer reinforcement），保证后续每一次 findRoot 的操作都是 O(1)
    - 优化2：对于每一个 disjoint sets 不必更新所有节点的 height，转而去维护一个 rank，使得 union 操作也是一个 O(1)
      - rank 大的 set 的 root 作为 parent，rank 小的 root 作为 child
      - rank 相等时任选其一（看题目要求），同时对于父节点所在的 set 的 rank +1
    - disjoint sets 的使用，借助了一种增长极端缓慢的函数 inverse Ackermann function，使得上述的 amortized cost 降至 O(1)
    - 对于一个 presorted edge list，不需要 heap，直接用 queue 可以实现 O(|E|)

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
4. 对于 MergeSort 的最后几层 recursion 使用 InsertSort 来替代，是否是一种 theoretically and practically beneficial optimization？ YES!!! 不要用过于 high-level 的视角来分析实际问题，并且算法要和硬件特点相结合！
   > Explanation:
   > 
   > This modification is a good idea, and in fact, you will see a somewhat similar idea in an algorithm called intro sort. Intro sort will begin with quick sort (we'll see this in the next lesson), then switch to a different sorting algorithm (called heap sort) once it reaches a certain subarray size threshold, and it will switch to insertion sort once it reaches another threshold. This idea is good for several reasons.
   > 
   > **Merge sort and insertion sort are good at different input sizes.** (因为越高级的算法往往需要越复杂繁琐的判断条件，这些额外引入的常数项和低阶项会在 n 不够大的时候成为一种累赘) Merge sort is great for very large arrays due to the halving, but this halving has diminishing returns. At some point, the overhead of splitting, copying, and merging will overtake the benefits of halving the search space. This occurs precisely for small values of . On the other hand, insertion sort has relatively small constants, so when  is small, it actually outperforms merge sort. So, Bob's analysis was faulty because it was too loose. He should've done a more careful analysis **keeping track of constant factors and lower order terms.** (不要盲目崇拜 Big-O 的定性分析，在涉及到具体问题的时候，往往会有很多的约束条件，此时考虑 constant factor 和 lower order term 才是决定走向的关键)
   > 
   > On the other hand, there are other reasons that this is a good idea. For one, the last phases of the recursive calls will end with an **inplace algorithm** (InsertSort 的优点), which somewhat mitigates the large space overhead of merge sort. It also makes it **somewhat adaptive** (InsertSort 的优点) since we will no longer be splitting completely sorted subarrays. Finally, **insertion sort has the benefit of locality of reference**(考虑到计算机底层机制的一种 bonus). All array accesses and swaps are done on the same array and in adjacent locations unlike merge sort where we have multiple arrays, and copies occur between different arrays.

5. Space Complexity in terms of Branch Factors: Suppose we are running BFS on a tree with branch factor  from the root (a BST has a branch factor of ). The goal isn't to store a visit ordering of the nodes but rather to do some operation in the visit ordering as we dequeue each vertex from the queue. The tree's depth is . What is the worst case space complexity of BFS in this scenario? Keep in mind that since we're in a tree, we do not need to store visiting information.
   > $O(b^d)$

   What is the space complexity of recursive DFS when in the same scenario as above?
   > $O(d)$

6. 