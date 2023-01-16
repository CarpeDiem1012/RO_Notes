<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

# 动机和目的
- 现有 DA 理论适用的条件：source 和 target 之间的 shift 相对较小
- 在实践层面上，对于比较大的 shift 时一个有效的做法是：使用 pseudolabeling 或者 conditional entropy minimization
- 然而上述做法缺乏理论指导，只是实验归纳的经验
- 本文从理论上证明了该方法在一定前提下具有有效性：
  - 在 unlabelled target 上进行 entropy minimization 可以避免模型使用 spurious features
  - 除了 entropy minimization，同样适用于 pseudolabeling 的一种情况：使用 teacher model 来 initialize student model，并且在每轮 gradient step 之后都做一次 relabel
  - 并且 domain shift 允许很大
- 但注意仅在以下大前提下成立：
  - 某些 spurious features 只 related to the label of source domain 但是 not dependent on the label of target domain
    > 注意是 label space 上的 related 和 independent，这个定义很细节
  - 假设 spurious feature 是独立于 target 高斯分布的，non-spuriou features 是相关于 source label 的 log-concave 的线性组合
  - 网络初始化时，使用的是一个 decently accurate source classifier
  - 【是这样吗？】起初允许使用 spurious features， 此时的 objective landscape 允许是一个有着多个 bad local minima 的 non-convex 函数
- 特别地，有：
  - 前人证明了：假设 $P(Y|X)$ 是呈各向同性的高斯分布的，在无穷多的 unlabelled data 帮助下，entropy minimization 会收敛到最近的 local minimum
  - 而本文证明了：仅仅需要有限个 unlabelled data 就可实现收敛
  - $H \Delta H$ 散度理论证明了：在 domain shift 比较小的时候，是可以给 classifier 的 acc 定界的
  - 而本文证明了：在满足我们前提条件的 structured domain shift 下，面对比较大 domain shift 也可以提高 acc

# 核心方法
- 有 $x_1 \in DiscrimitiveFeature$, $x_2 \in SpuriousFeatures$ 满足：
  - $x_1 \sim D_{target}(\cdot|y)$ 服从给定 label 下的任意条件分布（具有相关性）
  - $x_2 \sim N_{spurious}(\mu,\Sigma)$ 服从独立于 label 的某个 Gaussin 分布
  - $w_1$ 代表 $x_1$ 的使用权重， $w_2$ 代表 $x_2$ 的使用权重
  - Entropy Minimization $L(x)=e^{- \vert w_1 \cdot x_1 + w_2 \cdot x_2 \vert}$
    - 此优化的本质就是让绝对值里的内容趋于正负无穷
    - 由于 Projectd Gradient Descent，$w_1$ 和 $w_2$ 之间存在归一化关系
    - Avoid using spurious features $\rightarrow$ $w_1$ 增大 $w_2$ 减小 
- 直觉上，很容易可以想到 self-training 是一个放大自己 bias 的过程，让模型变得更加果断但也更加固执，有可能更好也有可能更坏。问题是：什么时候会更好？什么时候会更坏？
- 先提出了两个 self-training 行不通的 Scenarios:
  - No contribution from signal
    > $x_1=0$，没有可用的有效特征时 $w_1$ 就失去影响力了，self-training 本身是会放大 $w_2$ 的
  - Initial $L(w^S)$ is small, but self-training still increases $\Vert w2 \Vert_2$
    > $w_1 \cdot x_1 + w_2 \cdot x_2$ 很大 loss 很小，其中大部分是来源于 $w_1 \cdot x_1$，但是 $w_1 \cdot x_1$ 中分为大部分的 extremely confident predictions 和小部分的 poorly confident predictions，这样的后果是，模型不会为了迁就一小部分的 unconfidently predicted samples 去增大 $w_1$，反而决定去增大 $w_2$ 造成过拟合
- 由此论文对于 sample distribution 和 initial classifier accuracy 做出了假设要求
  -  Separation Assumption on the Data
     -  要避免这种情况：一部分 sample 的 预测非常好，但另一部分却很差
     -  需要两者实现一种一致性 continuum，即 sample distribution 不能是非常孤立的聚类模式 not supported on too many extremely isolated clusters
     -  关于这点，提出了 log-concavity 和 smoothness assumption 平滑性假设，用于稳定样本分布的一致性
  - Source classifier is decently accurate, doesn’t rely too much on spurious features
    - 基于归一化关系 $w_1^2 + w_2^2 = 1$ 规定了 $\Vert w^S_1 \Vert_2 \geq 1/2$
# 学习摘录
- Importance weighting 方法：
  - 假设条件分布 $P(Y|X)$ 在 source 和 target 上是相同的
  - 可以解决 covariate shift，即 $P(X)$ 的 marginal distribution shift
- distributionally robust optimization
- gray-level co-occurance
- Spurious features 和 non-robust features 之间存在的联系，“有用但不完全有用”，属于某种依赖于外部条件的非本质的特征（比如打着伞的一般都是人，但不下雨的时候就看不出来?)
  > Spurious features are also related to adversarial example, which can possibly be attributed to non-robust features that can predict the label but are brittle under domain shift.
- 证明了 Stochastic PseudoLabeling（每个 iteration 都 re-label + Projected GD） 和 Entropy Minimization 是等价的

# 问题
- projected gradient descend 的特点？
- 实际操作的时候 relabeling 怎么做？round 和 epoch 是两个东西？
  > In the 10-way MNIST experiment, we perform 3, 6, 30 rounds of pseudo-labeling with 100, 50, and 10 epochs of training per round, interpolating between more common versions of pseudo-labeling and entropy minimization. Figure 8 shows that entropy minimization converges to better target accuracy within the same clock-time, suggesting that practitioners may benefit from pseudo-labeling with more rounds and fewer epochs per round.


# 专题：关于各种 loss function 和 gradient descent
- Exponential Loss
  - self-loss $$L(x|w)=e^{-\left|wx\right|}$$
  - cross-loss $$L(x,y|w)=e^{-wx \cdot y}$$
- Projected Gradient Descend 
  $$w^{t+1}=\frac{w^t-\eta \frac{\partial L}{\partial w}}{\Vert w^t-\eta \frac{\partial L}{\partial w} \Vert}$$
  - 保证了 $w$ 永远都是 **单位向量** 。每一轮 gradient descent 都是当前梯度方向在权重单位圆上的投影。
  - $w_1^2 + w_2^2 = 1$