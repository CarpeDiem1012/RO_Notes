<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

- [并行处理与GPU架构](#并行处理与gpu架构)
  - [并行处理](#并行处理)
    - [串行（sequential processing）：指令/代码块依次执行](#串行sequential-processing指令代码块依次执行)
    - [并行（parallel processing）](#并行parallel-processing)
    - [概念解释](#概念解释)
  - [GPU 上的并行优化](#gpu-上的并行优化)
    - [术语](#术语)
    - [优化目标](#优化目标)
    - [Computer Memory Hierarchy](#computer-memory-hierarchy)
    - [针对 Throughput 的优化（减少了 Stall Time）](#针对-throughput-的优化减少了-stall-time)
    - [针对 Memory Latency 的优化（减少了 Memory Access Time）](#针对-memory-latency-的优化减少了-memory-access-time)
    - [GPU 的特点](#gpu-的特点)
- [环境搭建 \& Server 配置 \& Local 配置](#环境搭建--server-配置--local-配置)
  - [Server 配置](#server-配置)
    - [必备环境](#必备环境)
    - [提高生产力](#提高生产力)
    - [使用 Docker](#使用-docker)
  - [Local 配置](#local-配置)

# 并行处理与GPU架构
## 并行处理
> 理解并行处理，理解SIMD，编程常见处理方式

### 串行（sequential processing）：指令/代码块依次执行
- 拥有 data dependency
  - flow dependency
  - anti dependency
  - output dependency
  - control dependency
- 拥有逻辑分支 branch
  - 例如：前置条件判断
- 使用场景：
  - 赋值语句
  - 条件语句
  - 循环语句
  - I/O

### 并行（parallel processing）
> 类似 Job scheduling，例如：有4个 core，6个时钟周期不同的 statement 需要执行，最终目标是在满足串行约束的前提下，合理分配工作站，以求最小化整体的时钟周期 cycles

- 在计算机中充分利用其多核 multi-core 属性，针对多个任务或一个任务（可拆解为不同的阶段）进行程序流程优化

- 并行化（parallelization）的具体手段包括但不限于：
  - Scheduling：把没有 data depency 的代码分配到各个 core 各自执行
  - Loop Optimization：把一个大的 loop 循环分割为多个小代码，分配到各个 core 执行
  - Pipelining：在一个指令彻底执行完之前，已经得到了关键的中间结果，可以提前启动对应的 core 来下一个指令，以减少总体等待时间（类似 scheduling）

- 实际应用中大量需要并行优化的环节：
  - I/O 部分的内存读写
  - pre / post processing (服务于 DL 算法前后)
  - resize / crop / blur / bgr2rgb
  - 双线性插值：每一次插值结果都是独立的，没有 data dependency
  - 卷积层 (Conv)：每一个卷积的结果都不相互依赖
  - 全连接层 (FC)：每一个输出结点的结果都不相互依赖
  - 针对 Loop parallelization
    - reordering
      > 不同语言中，二维数组的存储方式是不同的，编程中需要针对存储的 ordering 来改写 loop optimization，从而减少 cache-miss
      > - row major: C / C++ / Obj-C / Pacal / C#
      > - col major: Fortran / OpenGL / MATLAB / R / Julia
    - vectorization
    - tiling
    - tiling in parallel

### 概念解释

- "并行" 和 "并发"
  - 并行（parallel）：物理意义上的同时执行（微观尺度）
  - 并发（concurrent）：逻辑意义上的同时执行（宏观尺度）。一个 CPU 高速交替执行两个任务，宏观尺度上可以视作两个任务同时执行，但实际上任意瞬间只能有一个任务被执行

- "进程" 和 "线程"
  - 一个程序可以有一个 process ，process 会触发其他 child process
  - 所有 process 都是独立的（isolated），不共享内存、代码、数据、文件资源
  - thread 是 process 的子集，一个 process 可以分成多个 thread 来运行
  - thread 之间是非独立的、轻量化的，同一个 process 下的所有 thread 共享内存、代码、数据、文件资源

- "多核" 和 "加速比"（Amdahl's Law）
  - 加速比的提升是非线性的，因为并不是所有任务都是解耦合的，因此增加 core 数量是有边际效应的
  - 8核的加速比也不一定优于4核：多核之间的数据传输也需要成本，有可能收益小于开销

- 如何理解 TensorRT？
  - 可以简单理解为一个 compiler 编译器
  - 输入一个 model，将并行优化后的 model 作为输出部署到硬件上，进行推理

- SIMD (Single Instruction Multiple Data)
  - 传统计算模式 (scalar Operation) 中: 执行 4 次乘法运算 = {取指令，读数据，计算，写数据} * 4次执行
  - SIMD 中: 执行 4 次乘法运算 = {批量取指令，批量数据，批量计算，批量数据} * 1次执行

- SIMT (Single Instruction Multiple Thread)
  - 类似 SIMD 普遍存在于 Nvidia 的 tensor core 设计理念之中，可以批量执行 FP16 或 FP32 的 tensor multiplication and addition

- 异构架构 (Heterogeneous Multicore Processor)
  > 现代计算系统架构中，同一总线上连接的不仅仅只有诸多相同的 CPU core（同构架构），也存在着 CPU + GPU + RTOS 等不同的设备，各自拥有独立的 Core/cache，以及共享的 L3 cache，针对同步/异步任务，需要合理分配适合的并行处理工作

  - CPU 中的并行处理（图像预处理/后处理，Multi-task 模型）
    - OpenMP
    - pthread
    - MPI
    - Halide

  - GPU 中的并行处理（CUDA编程）
  - RTOS 中的并行处理
    > RTOS 最大的区分点在于硬实时能力，通常存在一个"实时调度器"严格按照优先级来分配CPU时间，可以避免任务频繁切换导致CPU时间的浪费，且保证重要的任务优先被执行

## GPU 上的并行优化

### 术语
- latency：完成一个指令需要的时间
- memory latency：CPU/GPU 从 memory 中读取数据所需要等待的时间（CPU并行的主要优化方向）
- throughput：CPU/GPU 单位时间内可以执行的指令数目（GPU并行的主要优化方向）
- multi-threading：多线程处理

### 优化目标
- CPU：善于处理逻辑复杂的运算，但数量有限，主要目标在于频繁访问内存的过程中减少 memory latency
- GPU: 善于处理逻辑简单的运算，但数量很多，主要目标在于增加 throughput

### Computer Memory Hierarchy
> "Locality is Efficiency, Efficiency is Power, Power is Performance, Performance is King." -- Bill Dally @ Nvidia Chef Scientist
- 理想情况下，每一次 cache access 都是 cache hit；一旦发生 cache miss，CPU 无事可做的 stall time 所带来的 latency 是很影响性能的
- memory 距离处理器越近，memory 空间越小，memory 访问速度越快，memory 价格越贵

### 针对 Throughput 的优化（减少了 Stall Time）
- Pipelining

- Multi-Threading
  - 对于因为 data dependency 或者 cache miss 而处于 stall time 的 core，在其 stall time 时安排其他的任务（对应人类的 multi-tasking）
  - 使得单一的物理核在高频切换任务之间充当了多个逻辑核，在宏观尺度上看起来同时做了多个 core 的工作

- Branch Prediction（分支预测）
  - 根据 Loop 中以往的 Branch 走向，去假设下一次的走向，并且提前进行 Pipelining 计算，假如不符合的话再进行 Rollback 回退，只损失了一次 Rollback 的时间但 Pipelining 了很多次计算
  - CPU 中需要特殊的硬件来专门负责分支预测，GPU 中则没有

### 针对 Memory Latency 的优化（减少了 Memory Access Time）
- Cache Hierarchy（多级缓存）
  - 相较于非黑即白，在 CPU register 和 main memory 之间设立 $L_1$ 至 $L_{N}$ 的多级 cache 设备
      > L1 由逻辑核（所谓 CPU Core）独占：L1-instruction cache 和 L1-data cache\
        L2 由物理核独占\
        L3 由所有物理核共享
  - 通过细化粒度，在概率上减少 memory latency 的方差和期望，使得访问更快且更稳定

- Pre-Fetch（Hidden Memory Latency）
  - 如果预先知道 A 后就计算 B，可以预先把 B 所需要的指令和 A 所需要的指令一起取出

### GPU 的特点
- Multi-threading 技术
- 大量的 core ： 上千个 Thread 的数量规模
- 每一个 core 负责的运算逻辑很简单
  - CUDA core：处理 1 个 D=A*B+C 的 Matrix 计算
  - Tensor core：处理 4\*4\*4 个 D=A*B+C 的 Matrix 计算（64倍能力）
- SIMT: 
  - 将一条指令分给大量的 Thread 来处理
  - 将 32 个 Threads 来组成一个 Warp
  - 架构中有一个 warp scheduler，专门处理线程调度的
- 负责大规模计算（图像处理/深度学习），一旦 fetch 好了数据之后就会连续处理（相邻像素之间存在空间连续 + 算子作用的时间连续），因此throughput 很高，cache miss 的 latency 影响较小
- 但是！CPU 和 GPU 之间通信时所产生的 memory latency 需要重视

# 环境搭建 & Server 配置 & Local 配置
> 理解 CUDA, cuDNN, TensorRT 版本选择, Docker 的使用

## Server 配置
### 必备环境
- 先看 TensorRT
- 再看 cuda
- 再看 cuDNN
- 再看 nvidia-driver
- 再看 torch | tensorfloe

### 提高生产力
- openssh-server + ForwardX11
- zsh
- fim
- peco
- z
- miniconda
- netron
- tmux

### 使用 Docker
- 进入 Nidia NGC release note 查看可用的 docker images 中的 Ubuntu-distro/tensorRT/cuda/cuDNN 寻找对应的 image，注意需要参考本地的 driver 版本支持
- 使用 dockerfile 对 image container 进行个性化配置
  - 指定 base image，所在时区，用户名
  - task-specific 的环境和依赖
  - copy files 拷贝需要的文件进入 docker container 当中
  - 更改权限和组
  - 设置默认 `~` 目录和用户
- 使用 `chown` 避免每次使用 sudo 权限运行 docker
- 使用 build 和 run 来创建并运行 container

## Local 配置
- 配置 VS Code
  - 在每一个 proj 文件目录下创建 .vscode 用于配置 proj-specific 的编辑环境
  - 创建 compile_commands.json
    - 使用 Build EAR
  - 安装插件
  - edit config 设置 c_cpp_properties.json
  - settings 设置 settings.json (设置 language mode 文件关联)
  - tasks 设置 tasks.json (设置 make)
  - debug 设置 launch.json (设置)
- 配置 NeoVim (Optional Advanced)