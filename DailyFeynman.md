
1. 炼丹黑话？
   
   FCN=全卷积（类似U-Net），FPN=Feature Pyramid Network，RNN=Recurrent/Recursive，ResNet=Residual，RCNN=Region-based CNN，RPN=Region Proposal Network

2. Recurrent 和 Recursive 的区别？

    循环神经网络是时间上连续衔接，类似 Markovian Process，下句话的意思由这句话决定。递归神经网络是空间信息结构上的嵌套，类似一个树状图，这段话的意思由它的词决定。

3. _一个最简单的 Transformer 需要训练哪些参数？_

    $W_q$, $W_k$, $W_v$

4. _最简单的 Transformer 是否只能适用于 n input => n output?_

    是的

5. _Query, Key, Value 的意义和作用?_
    
    Q 是

6. _如何理解 feedback 和 feedforward？只使用 fb 和使用 fb+ff 的区别在于哪里？_

7. _解释一下 mIoU，mAP，AUC，ROC?_

8. _TF / PN 分别是针对 prediction 还是 gt?_

    这个词是 **形容词+名词** 的构成方式。两者都只关注 prediction 结果本身。true/false 是指结果正确与否；positive/negative 是指结果的正反。
    
    > 联想 => 老友记第8集，Rachel验孕棒阳性，Monika安慰说：Maybe it is a false positive.

9. 如何解决训练过程中样本类别分布不平衡？

- （一个假设）能否根据训练集中样本类别比例，来调整训练过程中的 loss 权重？若 alpha ：beta = 3 ：1，为了避免 classifier 变成无脑认 alpha 的傻子，我们在训练过程中计算 loss 时可以给 alpha 的 MSE 乘3倍？使得 classifier 谨慎判断 alpha？

- 使用 focal loss（见 Retina-Net by Kaiming He）

10. anchor-based 和 two-stage 的区别？

    这是两个层面。anchor 的意义在于同时解决“在哪里？有什么？”，把一个region proposal + classification 转换为了 classification + regression 的问题，又快又精准。stage 的意义在于要不要先做 region proposal 来缩小检测范围，需要的话 region-based = two-stage。 

11. anchor-free 的优点和缺点？

- 训练阶段不需要计算 anchor 的 IoU
- 不需要调整 anchor 的超参数

12.  one-stage 的特点和缺点？


13. DL 问题中使用哪种方法进行模型验证？
    
    使用 Cross Validation，一般是 a training set of 80-90% with the rest in the validation set.
    
    > *注意：使用 untouched dataset 的方法统称为 Cross Validation，即使只随机分了一个 validation set （学名叫做 holdout）也叫做 cross validation。但这里不要用 k-fold 或者 Leave One Out，Udacity 说 DL 算法不适用于这两种方法，因为通常面对大量的数据 validation 时间成本太高了，所以牺牲 variance 换取速度。*

14. ML model 中的 variance bias 分别指什么？
   
   - Variance 是 the sensitivity of model (if replacing the training set with another unseen set, how much would the error rate change?)
   - Bias 是 the quality of fitting on training set (low bias = low error on training set -> highly potential overfitting)

15. 什么样子的 Validation Set 是理想的？检查 Validation Set 应该注意哪些方面？
    - 图片不能和 Training Set 太相似，最好是来自两个不同的 Sequence。切忌采用均匀分布来采样。
    - 标签的 Class Distribution 需要和 Training Set 相近。
    - 总结：需要在 Label 层面上保持一致，确保各类 class 的比例相近，避免宏观统计上的隐变量；但需要在 Input Signal 层面上避免相似，避免 overfitting 的效果作用在 validation set 上面。
    - 举例：training set 上是白天乡村的道路，validation set 是夜晚城市的道路，但两者都包含了 80% 的 Car 类和 20% 的 Person 类。