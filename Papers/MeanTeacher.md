<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

# 动机和目的
- 灵感：同一类物体的感知变化，人类依然可以认为这是同一类东西，分类器模型应该也需要对于 similar data 给出一个 consistent prediction
- 本文不对于 label predictions 求均值，而对于 model weights 求均值
- Teacher-Student Model
  - cost of inconsistency outweighs the cost of misclassification
  - prevent from learning new information
  - suffer from confirmation bias

- $\Pi$ Model
  - 
- Temporal Ensembling
  - 对于每一个 training example 的所有 predictions，维护了一个 Exponentail Moving Average
  - 惩罚所有与这个 target 不一致的 predictions
  - 每轮 epoch 改变一次
- 本文目的： 不通过额外的训练，来生成一个更好的 teacher model

# 核心方法
- The teacher model is an average of consecutive student models
- Averaging model weights over training steps tends to produce a more accurate model than using the final weights directly.

# 学习摘录

# 问题

# 拓展文献
- Virtual Adversarial Training @ Miyato