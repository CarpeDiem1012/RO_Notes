<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

- [Makefile 基础](#makefile-基础)
  - [Makefile 和 CMake](#makefile-和-cmake)
- [CUDA 基础](#cuda-基础)
  - [概念](#概念)
    - [Kernel, Grid and Block](#kernel-grid-and-block)
    - [Traversal over Grid and Block](#traversal-over-grid-and-block)
    - [cuda\_X\_Synchronize() 函数](#cuda_x_synchronize-函数)
    - [Device Info](#device-info)
    - [Nsight system and Nsight compute](#nsight-system-and-nsight-compute)
  - [规则](#规则)
  - [语法](#语法)
    - [相比于 cpp 的特别之处](#相比于-cpp-的特别之处)
    - [大致框架](#大致框架)
    - [Erorr Handler](#erorr-handler)
- [思考问题：](#思考问题)

# Makefile 基础
## Makefile 和 CMake
> [!TIP]
> Makefile 语法继承自 GNU, 参考 [GNU 官方 makefile 指导](https://www.gnu.org/software/make/manual/html_node/)
- wildcard 语法：寻找所有符合命名要求的文件
- patsubst 语法：更改 path 的名称
- `:` 前的都是 targets, `:` 后跟的是依赖项 prerequisite
- files 作为 targets 是主动 updated 的
- `.PHONY` 作为 targets 只有强制 `make <target>` 才会执行 (除非作为第一条 rule)
- 第一条 rule，作为 make 命令默认执行的 target
- `$@` 指代 targets 变量, `$<` 指代 prerequisite 中的第一个变量

# CUDA 基础
## 概念
### Kernel, Grid and Block
- Functions in CUDA programming are often regarded as "Kernels".
- Generally, for each Kernel to be launched, one "Grid" is allocated.
- A Grid is tensor-esque conceptual structure which is not physically existent and consists of many "Blocks", with a dimension from 1D to 3D.
  - $numBlock = gridDim.z \times gridDim.y \times gridDim.x$
  - blcokIdx 经常搭配 gridDim 来联合索引

- Likewise, a block consists of many thread, with a dimension from 1D to 3D
  - $numThread = blockDim.z \times blockDim.y \times blockDim.x$
  - threadIdx 经常搭配 blockDim 来联合索引

### Traversal over Grid and Block

### cuda_X_Synchronize() 函数
- `X := Device`
   - 用于实现 host 和 device 的同步(即 cpu 和 gpu)
   - kernel 函数的执行默认是 asynchronous 的，所以当出现譬如 data dependency 的时候，需要手动调用同步函数来强制性地让 kernel 函数的执行结果结束之后 host 再来执行下一步
   - 本质上可以理解为，host(CPU) 在停机等待 device(GPU) 中的结果计算完毕，随后再读取执行代码段中的下一条指令
   - 给出一个例子：

      ```cpp
      kernel1<<<X,Y>>>(...); // kernel start execution, CPU continues to next statement
      kernel2<<<X,Y>>>(...); // kernel is placed in queue and will start after kernel1 finishes, CPU continues to next statement
      cudaMemcpy(...); // CPU blocks until memory is copied, memory copy starts only after kernel2 finishes
      ```

- `X := Stream`

- `X := Device`

### Device Info
1. 使用 cudaGetDeviceProperties(&prop, index) 来得到 GPU 的硬件参数
2. 许多程序参数和硬件参数相关，例如 `--cuda-gpu-arch`, max block num, max threads per block, max block dimension, max grid dimension, shared memory per block/SM, warp size
    > [!IMPORTANT]
    > 在进行性能优化时，内存大小 和内存带宽 memory bandwidth 是非常重要的考量因素，结合 Roofline Model，我们需要确认需要隐藏 memory 的数据传输所造成的 overhead，对应需要多少计算量 Arithmetic Intensity 和 计算效率 Attainable FLOP/s

### Nsight system and Nsight compute
- 使用 profiling tools 来进行性能分析（会 CUDA 编程的人很多，但这两个软件的熟练度是高手和菜鸟之间的差别之一）

## 规则
- 在 cuda core 中，经常把乘法和加法合并为同一个操作，叫做 FMA (Fused Multiplication and Addition)，使得原本需要两个 clock cycle 的操作可以在 cuda core 中只需要一个  clock cycle 就可以完成
- CUDA 中有一个规定，一个 block 中可以分配的 thread 的数量最大是 1024，否则会显示配置错误
  > 即 $blockDim_0 \times blockDim_1 \times blockDim_2 = numThread \le 2^{10}$
  - 是否和 warp scheduler 的 thread num 固定为 32 有关系（1024=32*32）
- 浮点数运算时CPU和GPU之间的计算结果是有误差的
  - 一般来说误差保持在1.0E-4之内是可以接受的

## 语法
### 相比于 cpp 的特别之处
1. 后缀名称 .cu
2. `<<< ? >>>` 符号(无法在 `.cpp` 文件中调用，g++ 编译器无法识别，只有 nvcc 编译器可以识别)
3. `__global__` 声明函数或变量类型


### 大致框架
1. 传入参数
2. `cudaMalloc()` 分配在 device 中的内存空间
3. `cudaMemcpy()` 把数据从 host 中拷贝至 device 中
4. 进行计算，得到结果
5. `cudaMemcpy()` 把数据从 deivce 中拷贝回 host 中
6. `cudaFree()` 释放内存（注意 c++ 没有 garbage collection）
   - free() 的顺序应该和 malloc() 顺序相反

### Erorr Handler
1. 习惯把 cuda 的 error handler 定义成宏，避免在执行的时候发生调用 error handler 而引起的 overhead
1. 养成良好的编程习惯：在 .hpp 中多使用宏定义对 cudaError 进行包装，增加 Error 的可读性，方便排查错误的来源
   1. `__FILE__` 是编译器内部定义的一个宏：当前文件的文件名
   2. `__LINE__` 是编译器内部定义的一个宏：当前文件的行
1. 通常情况下, 只需要定义两种 error handler
   1. 调用的是 cuda runtime api 函数，会返回一个 cudaError -> 直接传参
   2. 在 kernel 结束后进行检查, 没有作为 rValue 的 cudaError -> 使用 cudaGetLastError() 或者 cudaPeekLastError()
      1. Get() 会将最后的 error 重置为 success -> 
      2. Peek() 不会重置 -> 一旦出现 non-drecoverable error 有可能会继续传播并覆盖后续所有的 error, 影响到后续的 diagnosis
1. error 的分类
   1. synchronous / asynchronous -> error 处理的时刻不一定是 error 产生的时刻
   2. recoverable / non-recoverable -> 先前发生的 error 是否会影响到后续的 error handling



# 思考问题：
- 为什么改变 blcokDim 会极大地影响处理速度，明明 thread 数量都是一样的？
- `__shared__`
- `extern`
- `<<<_,>>>`
- shared 类型分配内存
- 为什么只 free `_gClock`，不用 free std:: 空间下的 `_cClock`
- 静态和动态的区别
- 不同 GPU 的架构
- FP32 是什么，单精度/双精度，浮点数
- grid  block thread 是否对应具体的结构
- kernel 编程的逻辑是默认以 thread 为对象，那么对应 shared memory
- bank conf 的出现导致即便使用 shared memory 也会比 general 情况更慢
- 分成多个 stream, 核函数进行的过程中来做 memCpy, 进而隐藏 latency
- GELU 是什么算子
- stream 本质上属于一个 queue
- multi-streaming 的优化可以分为两种情况：
  - 当 kernel 对于计算资源没有充分利用的时候，即所需的计算量很少，即便铺满也无法调用全部的 threads（极少情况）：加速比非常大，因为可以通过并行启动 kernel 的不同阶段, 通过 overlapping 来隐藏掉 kernel 执行过程中的 stall time
  - 当 kernel 对计算资源已经充分利用的时候（在大多数实际应用中，通过设计合理的参数可以实现）：加速比是有限的（可能只有 1.2x ），此时 kernel 的每一个阶段都用尽了资源，无法再提前并行启动第下一个阶段，但是可以在 kernel 中隐藏掉 MemCpy 的时间
  - 为什么 dim3 dimBlock(16,16,1) 效果是最好的？ (8,8,1) 的占用率不高，4个 block 才能调用一个 SM 可以理解，但是 (32,32,1) 为什么不好？
- 一套 Nvidia 官方推荐的课程 https://www.olcf.ornl.gov/cuda-training-series/ 
  - unit 3 讲述了一部分 block scheduler 的原理
  - 一个论坛中的解释 https://forums.developer.nvidia.com/t/question-about-threads-per-block-and-warps-per-sm/77491/12#:~:text=Oct%2006%20%2722-,This,may%20be%20of%20interest%20for%20background.,-Before%20a%20kernel