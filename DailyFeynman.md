1. 炼丹黑话？

    A：FCN=全卷积（类似U-Net），FPN=Feature Pyramid Network，RNN=Recurrent/Recursive，ResNet=Residual，RCNN=Region-based CNN，RPN=Region Proposal Network

1. Recurrent 和 Recursive 的区别？

    A：循环神经网络是时间上连续衔接，类似 Markovian Process，下句话的意思由这句话决定。递归神经网络是空间信息结构上的嵌套，类似一个树状图，这段话的意思由它的词决定。

1. _一个最简单的 Transformer 需要训练哪些参数？_

    A: $W_q$, $W_k$, $W_v$

1. _最简单的 Transformer 是否只能适用于 n input => n output?_

    A: 是的

1. _Query, Key, Value 的意义和作用?_
    
    A: Q 是

1. _如何理解 feedback 和 feedforward？只使用 fb 和使用 fb+ff 的区别在于哪里？_

1. _解释一下 mIoU，mAP，AUC，ROC?_

1. _TF / PN 分别是针对 prediction 还是 gt?_

    A: 这个词是 **形容词+名词** 的构成方式。两者都只关注 prediction 结果本身。true/false 是指结果正确与否；positive/negative 是指结果的正反。
    
    > 联想 => 老友记第8集，Rachel验孕棒阳性，Monika安慰说：Maybe it is a false positive.

1. 如何解决训练过程中样本类别分布不平衡？

    A: （一个假设）能否根据训练集中样本类别比例，来调整训练过程中的 loss 权重？若 alpha ：beta = 3 ：1，为了避免 classifier 变成无脑认 alpha 的傻子，我们在训练过程中计算 loss 时可以给 alpha 的 MSE 乘3倍？使得 classifier 谨慎判断 alpha？

    B：使用 focal loss（见 Retina-Net by Kaiming He）

1. anchor-based 和 two-stage 的区别？

    A：这是两个层面。anchor 的意义在于同时解决“在哪里？有什么？”，把一个region proposal + classification 转换为了 classification + regression 的问题，又快又精准。stage 的意义在于要不要先做 region proposal 来缩小检测范围，需要的话 region-based = two-stage。 

1. anchor-free 的优点和缺点？

    - 训练阶段不需要计算 anchor 的 IoU
    - 不需要调整 anchor 的超参数

1. one-stage 的特点和缺点？

    A：