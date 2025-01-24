<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

## Neural Uncertainty for Autonomous 3D Reconstruction with Implicit Neural Representations (23 RA-L)
- 创新点
  - 使用 nerf + active slam 来辅助生成一条 view path 是一个研究方向（新，但已经有很多人在研究了）
  - 但是本文对于这个新方向首次提出了两个 key challenges
    - 寻找一种衡量 view path cost 好坏的标准，以便于找到更好的 path
    - learn from data 来做到上述目标，而不是使用 handcrafted criteria，同时保证这种学习到的标准在多个场景下拥有可泛化性能
  - 提出了 proxy of Peak Signal0-to-Noise Ratio (PSNR) 来量化 viewpoint 的质量好坏
  - PSNR 的 proxy 用来和 implicit NN 参数做一个**共同优化**
  - 这种 view quality criterion 在文中被称为 Neural Uncertainty
- 实验
  - 对比1：和 TSDF(truncated signed distance function) 方法对比
  - 对比2：和没有 view planning 的方法做对比
  - PSNR:= diff between imgs from 重建模型 and GT模型

- 参考文献
  - 文献3：使用 RGB 来推测深度信息，然后使用 triangulated uncertainty 来估计 view cost
  - 文献4： 使用 voxels 的 information gain 来作为 view cost
  - 文献5：使用 point clouds 的 Poisson field 来量化 view cost
  - 文献6：使用 mesh holes 和 boundaries 来引导 view planning
    - 以上这些方法都是基于 current view point 来推测出 next best view，本质上属于一种贪心策略，具有一定的局部性
    - 同时，这些 view cost 的标准都是一种启发式 explicit handcrafted 设计的，本文探讨了一种基于 nn 的方法

## NeuralAR 之后新作：Effective 

## 引用了上述论文的文章：[Active Neural Mapping](https://openaccess.thecvf.com/content/ICCV2023/papers/Yan_Active_Neural_Mapping_ICCV_2023_paper.pdf)