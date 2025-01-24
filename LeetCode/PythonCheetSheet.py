# ----- Tuple ----- #
mytuple = (1,) # 注意单个元素时要有逗号
# 拼接、切片、重复、索引、计数
mytuple.count("value")

# ----- List ----- #
# 生成二维矩阵
array_2d = [[0 for _ in range(5)] for _ in range(3)] # 3*5 的二维矩阵
print(array_2d)

# 循环方式 Loop
# -1 有 index
nums = [[1]*10]
for element in array_2d:
    pass
# -2 无 index
for i, element in enumerate(nums):
    pass
# -3 反向循环（更快）
for element in reversed(nums):
    pass
# -4 反向循环2
for elment in nums[::-1]:
    pass
# -5 反向循环3
for element in range(len(nums)-1, -1, -1):
    pass # range(start, end, stride) 左闭右开
# 插入
nums.insert(0, 12)
# in-place 排序（默认 False 为升序）
nums.sort()
# in-place 反向排序
nums.sort(reverse=True)
# out-of-place 排序，需要返回
sorted(nums, key=lambda nums:nums.__hash__)
# 增加一个新元素
nums.append([0,1,2])
# 格式化增加
nums.extend([0,1,2])
# in-place 删除
del nums[2:4]

# ----- STACK based on List 基于列表 -----#
stack = []
stack.append("hello") # 添加到 Stack 的栈顶
lastOne = stack.pop # 默认从末尾处弹出
stack[-1] # 检查栈顶元素 -> Java 中的 peek() 方法
len(stack)

# ----- QUEUE based on List 基于列表 ----- #
queue = []
queue.append("hello") # 添加到 Queue 的末尾
queue.pop(0) # 指定从 index 0 开始弹出，实现 FIFO
queue[0] # peek() 方法检查队首元素
len(queue)

# ----- QUEUE based on collections.deque 基于双向队列 ----- #
from collections import deque
queue = deque()
queue.append("hello")
queue.popleft() # 实现 FIFO
queue[0] # peek()
len(queue)

# ----- MAP/DICT ----- #
mymap = {}
# 增加和查找
mymap["fist"] = "1" # put()
v = mymap["first"] # get(), ERROR 假如 Key 不存在
v = mymap.get("first") # None 假如 Key 不存在
v = mymap.get("first", "default_value") # 用 default_value 取代 None 假如不存在
# 检查 Key 是否存在
if "first" in mymap:
    pass
# 循环 Loop
for key in mymap:
    print(mymap[key])
# 返回全部 Key
mymap.keys()
# 返回全部 Value
mymap.values()
# 返回全部 (K,V) pair
mymap.items()
# 删除 (K,V) pair
del mymap["first"]
# 删除并返回 (K, V) pair
del_value = mymap.pop("first")
len(mymap)

# ----- MAP/DICT based on collections.defaultdict ----- #
from collections import defaultdict
mydict = defaultdict("default_value") # 假如 Key 不存在，默认返回 default_value
mydict = defaultdict(list) # 假如 Key 不存在，会默认返回指定类型的默认值，list 对应 []，str 对应 ""，set 对应 set()，int 对应 0
mydict["first"].append("hello") # 此时 mydict["first"] 默认是 []，随后有 [].append()

# ----- SET ----- #
myset = set()
myset2 = {"apple", "banana", "cherry"} # 和 MAP 的定义类似，SET 可以理解为没有对应键值的哈希表
myset.add("hello")
# 检查 element 是否存在
if "hello" in myset:
    pass
# 删除 element
myset.remove("hello") # EROOR 假如 element 不存在
# 查询交集
intersection = myset.intersection(myset2)
len(myset)

# ----- HEAP/PriorityQueue ----- #
# 注意 Python 中 HEAP 默认是 Min-heap，每次 pop 只弹出最小的元素，每次 add 也会默认维护 min-heap 的结构；同时 heap[0] 也是其内部最小的元素
# 时间复杂度：pop/add/insert -> O(log n)； heapify/buildHeap -> O(n)
import heapq
# 从 List 中构建 Heap
nums = [5, 7, 9, 1, 3]
heapq.heapify(nums)
# 增加
heapq.heappush(nums, 4)
# 删除
minElement = heapq.heappop(nums)
# 先增加后删除
minElement = heapq.heappushpop(nums, 10)

# ----- String ----- #
# 拆分
txt = "Welcome to the jungle."
x = txt.split(sep=None, maxsplit=-1) # 默认以 space tab LF换行\n CR回车\r 等 whitespace 为 delimiter
print(x) # ['Welcome', 'to', 'the', 'jungle.']
# 重复
txt10 = txt*10
# 替换
txt.replace("old", "new", -1)
# 搜索
sentence = "hello"
result = sentence.index("hel")
print(result) # 0

# ----- 构建 Graph ----- #
n = int(input())
val = list(map(int, input().strip().split()))
# 建立 Adjacent Matrix
matrix = [[] for _ in range(n)]
for _ in range(n - 1):
    i, j = list(map(int, input().strip().split()))
    matrix[i - 1].append(j - 1)
    matrix[j - 1].append(i - 1)

# ----- 数据处理 ----- #
min = float('-inf')
max = float('inf')
'a' == chr(97) # from Unicode to Character
97 == ord('a') # from Charactor to Unicode(==ASCII==UTF-8)
0.5 == 1/2 # Python3
1 == 1/2 # Python2
0.5 == 1.0/2 # Python2
-1 == -1//2 # Python2/3，先进行小数运算，然后取 math.floor() 的结果

# ----- 二分法搜索 ----- #
import bisect
nums.sort()
index = bisect.bisect(nums, 241) # 使用 O(log n) 对于有序列表进行二分搜索

# ----- Counter ----- #
from collections import Counter
cnt = Counter(mymap)
cnt.elements()


# ----- OOP 特性 ----- #
class myClass(object):
    abstractProperty_1 = None # 属于抽象层面 class 自身的变量 
    abstractProperty_2 = None # 
    def __init__(self) -> None: # self 指代的是 instantiate 之后的具体 object
        """
        但也可以在 obj 初始化的时候 overwrite; 此时对 class 和 obj 分别调用的结果不同; 在 obj 的阶段覆盖，不会影响 class 的变量
        """
        self.abstractProperty_1 = None
        self.abstractProperty_2 = None

    # ----- 封装特性 ----- #
        self.__name = None # private 变量，只有内部可以访问修改。一般需要设置 get 和 set 函数
        self.name = None # public 变量，内部外部都可以访问修改
    # 同理有 private 成员函数
    def __inner_func(self) -> None:
        pass
    # 但是这种 private 并不严格安全，理论上可以在外部使用 `obj._ClassName__privateVariable = x` 来进行操作；使用此类方法初始化的变量不是真正的 private 变量

    # ----- classmethod ----- #
    # 此类方法只能使用 class 自身的类变量，不能访问 instance 的实例变量
    @classmethod
    def myClassMethod(self) -> None:
        print(self.abstractProperty_1) # 这里的 self 传参的是 class 本身，而非 obj
    
    # ----- staticmethod ----- #
    # 此类方法隔绝了 class/obj 和其类变量与实例方法的关系
    @staticmethod
    def myStaticMethod(self) -> None:
        pass

    # ----- property ----- #
    # 此类方法必须使用[属性]的方式来被 class/obj 来调用，不能有括号
    @property
    def myPropertyFun(self) -> None:
        pass # value = obj.myPropertyFun
    @myPropertyFun.setter
    def myPropertyFun(self, args) -> None:
        pass # obj.myPropertyFun = []
    @myPropertyFun.deleter
    def myPropertyFun(self, args) -> None:
        pass # del obj.myPropertyFun 直接实时地删除该方法
    
    # ----- 动态生成类 ----- #
    myNewClass = type("myNewClass", (object, ), {"name":"scl", "age": 25})

    # ----- 在实例化 __init__ 之前插入操作（单例模式）----- #
    def __new__(cls, *args, **kwargs):
        pass
        return object.__new__(cls)
    
    # ----- 在释放内存的同时加入操作 ----- #
    def __del__(self):
        pass # 作为析构函数，调用时刻取决于对象的声明周期

    # ----- 定制 print() 和 terminal 输出 ----- #
    def __str__(self) -> str:
        return ""
    def __repr__(self) -> str:
        return ""
    def __hash__(self) -> int:
        pass
    def __eq__(self, __value: object) -> bool:
        pass
    
# ----- 多继承 ----- #
# Python 和 C++ 都是允许多继承的算法，而 Java 不是
# 这带来一些便利，但更多时候带来了冗长的规则
# 多继承的规律并不是简单的 DFS 或者 BFS，在 Python3 中叫做 C3 算法
print(myClass.mro()) # 查看某个类的多继承顺序（Method Resolution Order）

myClass.myStaticMethod()