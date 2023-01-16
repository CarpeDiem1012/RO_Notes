<link rel="stylesheet" type="text/css" href="auto-number-title.css" />


# 动机和目标
## 已有方法
### indoor 场景
- indoor 的点云特点
  - 密集且密度均一
  - 距离较近
- KNN 聚类
- 多尺度融合
- 缺陷
  - 计算量大
  - not robust to varying sparsity
  - 难以泛化
    > ? 具体哪里难以泛化，近处密集的效果如何 
### outdoor 场景
- 先投影到 2D 拿到 pseudo image 再用 2D 的框架解决问题
    - Spherical -> 深度图
    - Bird-eye view + 极坐标做 partitioning
- 缺陷
  - 改变并损失了3维拓扑结构和几何关系
### Voxel + U-Net
- U-Net 的问题在于？

## 需求现状
- outdoor 车用级别点云特点：
  - 密度随着距离改变
  - 远处稀疏近处密集 (关键)

## 三种表征点云的方法
- Spherical Proj -> Range Image (深度图)
- Bird-eye view Proj -> Bird-view Image （丧失高度）
- Voxel Partition

# 核心方法
- 提出方法
  - 表征优化 => 延续 Voxel + U-Net 的思路
    - Cylinder Partition
    - Cylinder Conv
  - 架构优化
    - Asymmetric Residue Block -> 匹配方形的物体
  - 搜索优化
    - Dimension-decompostion based context modeling -> progressively 提炼高阶的语境信息  
- 结果： KITTI -> mIoU + 6%

# 学习摘录

# 拓展文献
