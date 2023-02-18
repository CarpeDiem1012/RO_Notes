# Tips

- 指向函数的函数指针没有（），直接是 *pointer = &<函数名>，或者可以省略&（因为没有括号，所以编译器自动识别这是指针而非函数的返回值）

- 一些看似不需要返回值、只负责在函数体内部实现功能的函数（例如，初始化函数 init，重要的 msg 发送、接收函数，service 开启函数）实际上需要返回一个 bool 来便于增强反馈和可调试性

- ros::init() 会初始化  ROS。第一次使用 ros::NodeHandle 创建 NodeHandle 对象的同时，会 register 一个 node，随后只会增加当前 node 下 NodeHandle 的数量。每一个使用标准 roscpp 接口来编写的 .cpp 只能运行一个 node，但是一个 node 可以拥有多个 NodeHandle 对象

- 相对名称与私有名称的区别：

  - 相对名称（xxx）的解析 = default-namespace（默认为 /，可修改为__ns:=） + 相对名称

  - 私有名称（~xxx）的解析 = 节点名称 + 相对名称

    > ？为什么要使用私有名称？一些计算图源只与本节点有关系，而不会与其他节点打交道，这些资源的命名直接放置在节点名称下会更有利于系统结构，没有必要使用相对名称，并为其专门修改 default-namespace

- 使用 roslaunch 

- 节点名称 node_name 与 计算图源名称 graph resource name 的区别

- 官方 Tutorial[http://wiki.ros.org/roscpp/Overview/Time] 建议使用 Timer 而非 Rate 来控制发布频率.

# Package

- `rospack find`

- `roscd` 
- `rosls`

# Node

- `rosnode list`

- `rosnode info <node-name>`
- `rosnode kill <>`

# Topic

- `rostopic list`
- `rostopic info <topic-name>`
- `rostopic echo <>`
- `rostopic hz <>`
- `rostopic bw <>`
- `rostopic pub -r <rate-in-hz> <tpc-name> <msg-tpye> <msg-content>`
- `rosmsg show <msg-name>`

# 参数传递

- 方法一：rosrun 命令行中修改，更灵活

  1. 修改当前节点的默认 namespace

     > 什么是 namespace：每一个计算图源（graph resource），即 node/topic/service/parameter，都拥有一个计算图源名称，由命名空间来构成。而每一个节点拥有一个默认的命名空间，作为其派生的计算图源的相对名称的基准。

  - `__ns:=default-namespace`

  2. 修改 node_name

     > 什么是 node_name：

  - `__name:=`

  3. 重映射 graph resources name

     > 注意此时传递参数遵循使用**相对名称**的原则，但底层在执行重映射的时候是把所有 names 都先resolve 成 global names，再进行 remapping

  - <previous-name>:=<new-name>

  4. 给 private 的 param 赋值（类似重映射）

     > 什么是 private 的 node：

  - <_para-name>:=<param-value> 注意只有一个下划线

- 方法二：在 .launch file 修改配置，适合批量处理

  1. 修改当前节点的默认 namespace
  
  	> 什么是 namespace：每一个计算图源（graph resource），即 node/topic/service/parameter，都拥有一个计算图源名称，由命名空间来构成。而每一个节点拥有一个默认的命名空间，作为其派生的计算图源的相对名称的基准。
  
  - `__ns:=default-namespace`
  
    

# Parameter

 > 注意区分 param server 的 parameter 和 launch file 中 的 arg

  - `rosparam list`
  - `rosparam get <param-name>`
  - `rosparam get <namespace>`
  - `rosparam set <param-name> <param-value>`
  - `rosparam set <namespace> <param-value: YAML format>`
  - parameter 只能通过 rosparam 来创建，或者在 .launch 中来调用 element <param> 间接创建
  - parameter 也可以被 node 创建 `nh.setParam("param-name", param-value)`，目前了解到 cpp 只能获取参数的值
  - parameter 一旦被创建之后存储在 parameter server 中，与 node 的存在与否无关，即便 param 是由 node 创建的，在 rosnode kill 后也依然存在