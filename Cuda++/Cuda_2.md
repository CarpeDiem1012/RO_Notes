<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

# TensorRT
## 宏观概念
- TensorRT = SDK + 面向 nvidia gpu 的 compiler，实现

- 自动优化模型
  - 寻找可以优化的地方
  - 
- 支持多框架输入生成的 ONNX
- 支持 Python/C++ 的 API
- 常见搭配 Pytorch -> ONNX -> Nvidia Device(Jetson/DRIVE/Tesla)

## Beyond TensorRT
- Qualcomm
- Hailo (以色列独角兽，有针对自己的硬件设计的 Dataflow Compiler，其中量化和调度的技巧多而精巧，可以参考 Hailo, Google TPU, MIT Eyeriss 的 paper)
- Dataflow 虽然是一个很古老的概念，但由于 GPU 中的数据很多，仍有重新发掘的潜力

##  Why TensorRT
- 硬件设计和编译技术成熟
  - Bug 较少
  - 对于 quantification 的支持比较完善
  - SDK 很充足（polygraphy, onnxsurgeon）
- 参考文档多，学习资源丰富
- 社区很大

## 缺点
- 成本高，量产开销大
- 过于大的 TOPS(Tera Operation per Second) 是否真的好?

## 工作流
- 模型转换
- 精度校准
- 算子融合
- 更改资源调度策略（gridDim, blockDim）
- 多流处理
- 内存复用
- 构造推理引擎，生成 Python/C++ 的 API

## 常见问题
### 算子的版本支持
- 不同 TensorRT 版本是否支持：
  - Pytorch: Facebook
  - ONNX: Microsoft
  - TensorRT: NVIDIA
- 解决方案：
  - 修改 Pytorch 的算子
  - 自己写插件自定义算子以及 cuda 的核函数
  - 跳过 ONNX，自己创建 parser 直接调用 TensroRT API 来逐层创建网络（例如纯 c 写的 darkNet）

### 版本间优化策略不同
- 例如： Transformer 的优化在 7 和 8 中是不一样的

### 预期 TensorRT 的优化和实际的优化结果是不一样的
- kernel auto-tuning 的权限高且黑盒子，例如：用户期望使用 tensor core，但 tensorRT 觉得反而 cuda core 的效率更高

### 对于天生并行性差的 layer 没有帮助
- 例如： mobileNet 中，有大量的 1x1 conv，再怎么优化也没有 7x7 conv 的并行效果好

## 总结
TensorRT 只是一个工具而非目的，不要过分依赖 TensorRT 以及它的结果。真正应该关注的是部署目标硬件的特性，通过技巧和实验做一些 Benchmark 和 Profiling，使用 profile tools 来分析模型的计算瓶颈在哪里，以及预期策略和世纪策略之间产生分歧的原因

# 应用场景
## 核心思想
在训练阶段和部署阶段的思路和倾向是有很大不同的
### 训练阶段
- 不必考虑算力成本
- 随意使用复杂的算法来追求更好的性能和精度
### 部署阶段
- 首先考虑算力限制
- 在可以接受的精度损失之内，尽可能简化模型

## 自动驾驶场景
- Realtime 实时性
  - 一般来说 15fps 是底线，30 fps 是底线
  - 100 km/h = 28 m/s
- Power Consumption 电力消耗
  - 100W 以内
- Long Range Accuracy 大场景下的精度

## 挑战
- 算力强，且不需要水冷的散热（汽车中硬件集成复杂）
- 多任务（检测，分割，追踪，融合定位导航）
- 大范围高精度（意味着远处的检测/分割/追踪需要用高分辨率的图像，更吃资源）
- Perception + Planning

## Jetson 系列
AGX Xavier / AGX Orin 放在汽车后备箱的位置

# 优化策略 
## 层融合 layer fusion
- 每一个 layer 作为算子单独构造为 kernel，有启动和 memory 操作的 overhead
- 把没有 dependency 的 layer 融合为一个 kernel 可以消除这部分 overhead 提高效率

### 垂直层融合： conv + batch norm + ReLU
- 三个环节的操作在数学形式上可以写作同一个式子，进而放进同一个 kernel function 之中

### 水平层融合： transformor 中对于 (k,q,v) 的生成矩阵进行融合
- 当模型中在水平方向（多个任务或分支并行时）有比较多的同类 layer，会直接进行融合
> [!TIP]
> 来自讲师的经验：很多模型有各种各样的激活函数，GELU/Swish/Mish 等，这些激活函数通常很复杂，但实际的性能提升是有限的，在遇到复杂激活函数时，可以尝试更改为 ReLU 来看看是否性能会有很大地损失

## 核自调 Kernel auto-tuning
- TensroRT 对于每一个层，会使用不同的 kernel 函数来进行性能测试（例如 32x32 / 32x64 / 64x64 / 64x128 / 128x128），并且针对不同硬件有不同的策略
> [!TIP]
> 有时由于模型架构的计算密度不够高，在 kernel auto-tuning 时 TensorRT 认为没必要选择 tensor core 而是选择 CUDA core 来使用

## 量化 Quantization
> [!IMPORTANT]
> 对于模型压缩和部署来说很重要，是当前部署过程中**最优先考虑**的策略，后面会单独讲解
- 训练时，因为需要有限考虑精度而不需要台重视速度，所以会使用 FP32 来表示权重 w 和激活值，但是位数约多，精度越高，占用内存也越大，计算越慢，实时性越差
- 但在部署的时候，我们需要想办法把 FP32 的数据尽量压缩，能够用 16bits / 8bits / 4bits 来表示，这个过程称之为量化
  - 例如，将模型权重从 FP32（单精度）变为 FP16（半精度） 
  - 实际上，在模型训练阶段也在使用量化的技巧，详见 Baidu+NVIDIA 在 ICLR2018 发表的 Mixed Precison Traning 文章使用 FP16+FP32 进行混合精度训练