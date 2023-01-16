<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

# 动机和目标
- DA 或者 TL 的目标是减小 cross-domain 的 distributional shifts
- 然而 distribution 本身也有很多视角，不同视角下的 shifts 有不同的解决办法
- 用判别型模型的视角来看待 ML 问题，$X \in Input Space$ 和 $Y \in Label Space$ 两者是条件分布的。操作的对象是$P(Y|X)$。
- 用生成型模型的视角来看待，$X \in Input Space$ 和 $Y \in Label Space$ 两者是联合分布的。操作的对象是$P(X,Y)$。
- shift 也和 distribution 的建模方式有关:
  - 边缘分布 $P(X)$ 对应的是 covariate shift
  - 条件分布 $P(Y|X)$ 对应的是 conditional shift
  - 两者都都考虑的 $P(X,Y)=P(Y|X)P(X)$ 是才是 dataset shift
- 多数工作的重点在于边缘分布$P(X)$，原因是：
  - source 有X有Y，然而 target 没有label，没有 Y 就无法建模 target  domain 的 $P(Y|X)$
  - 假如想要考虑 conditional shift，那就无法解决没有 $P(Y|X)$ 的矛盾
  - 因此，解决这类问题通常都会加上一个 strong assumption
    - conditional distribution may only change under location-scale transformations on X
    - [是这样吗?] 以此，可以保留 source domain 的 $P(Y|X)$ 来作为 target 的 $P(Y|X)$
    - 实际上，这个 assumption 很难满足
- 提出的方法直接在联合分布上考虑问题
- 思路还是在中间 feature 上面，使得网络对于 source 和 target 提取的特征尽量接近，同时又保证 source 上面 supervised 效果良好

# 方法核心
- 提出的方法直接在联合分布上考虑问题，因此不受这个 assumption 的制约，具有更强的泛化能力 => 然而思路就是在深层网络直接截取 activation 来拉进分布，认为 activation 就是联合分布特性的一种体现（似乎很简单，也很经验主义，应该是基于大量实验分析的结果）
- Reproducing Kernel Hibbert Space Embedding of Distributions
- 基于 MMD 和高阶的统计学原理提出了改进版本的 JMMD 用来衡量 discrepancy 或者 distance
- Loss =
  - source 上平平无奇的 cross-entropy (source X, source y)
  - $JMMD( z(X_{source}), z(X_{target}))$，其中 $z$ 取的深层activation（例如最后的全卷积fc层）
- 本身可以直接 train，另外又引入了几个 fc 层作为 feature extractor 提出了一版和 Ganin & Lempitsky 思路相同的类 GAN 网络
  - $JMMD( z(X_{source}|\theta), z(X_{target}|\theta))$，其中 $\theta$ 是用来 maximize JMDD 的，$z$是用来 minimize JMDD的

# 学习摘录
- > Deep features in standard CNN eventually transition from general to specific along the networks. The transferability of features and classifiers decreases when the cross-domain discrepancy increases.
  - 指出了可以在深度上做文章，直觉上只需要操作靠后的层，不需要通改所有层，但在多深的地方操作又是一个问题
- 介绍了希尔伯特空间，复习了 kernel trick
  - 线性空间
  - 完备空间、柯西数列
  - 赋范空间、距离空间
  - 希尔伯特空间=完备的赋范空间
  - 怎样理解 reproducing 的性质？
  - 核函数究竟是高维正交空间基底的权重的内积，还是基底的线性组合的内积？两者是有区别的，因为 $\Phi_i \cdot \Phi_j=0$ 但 $\Phi_i \cdot \Phi_i \neq  0$
- 新的高阶统计学的概念（有待学习）
  - two-sample t-test
  - null hypothesis

# 问题
- 都是 reduce shifts across domain，Transfer Leaning 和 DA 本质上的区别在哪里？
- 什么是文中提到的 exploratory factor？
- 文章提到的前人工作中对于 disentangle exploratory factor 和 reduce cross-domain discrepency 为什么顾此失彼？
- which can only reduce, but not remove, the cross-domain discrepancy 是什么意思？能完全 remove 吗？如果不能完全 remove 那 reduce 不是逼近的最好策略吗？
- 所谓可以代表 Joint Distribution 的深层 activation 实际上是从别人论文里得到的分析结果，但不一定总是 {fc6,fc7,fc8}，而这本身也属于一种超参数。迁移到新的架构上很可能会不适用，或者需要花费大量精力去实验和调查。怎么解决这个问题？
- MMD 和 JMMD 相对于其他 distance measurement 的优点在哪里？
- 传统的 kernel trick 是用来解决低维度空间线性不可分的问题，但 kernel trick 在这里映射到高维空间的优势在于什么？选哪个 kernel？
- 作者对于最后引入 GAN 的 motivation 是这样阐述的：为了保证 Hilbert Space 中的函数簇是 rich enough 的。那么如何辨别 a class of function of Hilbert Space 是否是 rich enough 的？
 
# 拓展文献
- Theory of Kernel Embedding of Conditional Distribution (Song et al., 2009; 2010; Sriperumbudur et al., 2010)
- A basis of