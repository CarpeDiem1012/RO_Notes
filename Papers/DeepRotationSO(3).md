# 动机
- rotation estimation 很重要（VO, Objext Pose Estimation）
- 对于 DeepLearning 的解法来说，选择一种合适的 Rotation Representation 很重要（旋转矩阵、四元数...）
# 方法
- 提出全新的 Symmetric Matrix Representation
  - 平滑性 -> 收敛和泛化能力优秀 towards large rotation targets regression
  - 本质上隐含了一种 基于四元数的 Symmeric Bingham belief，可以更好对于 uncertainty 进行建模

# 实验方法
- 在两个模态数据上进行实验
  - 理论层面：合成点云数据集 -> predictive accuracy
  - 工程层面：实时收集的地、空机器人图像测试 -> 表现出的优秀泛化能力可以很好的应对 OOD 场景，对于 unseen environmental effect 和 corrupted image 具有非常好的抗干扰性
  - 同时，这种 robustness 并不是通过训练过程中的 likelihood loss/stochastic sampling/auxiliary classifier 学习得到的。间接证明了这种 representation 本身的优越性，是胜在了属性优秀而非技巧伶俐