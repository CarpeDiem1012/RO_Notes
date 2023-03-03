
1. ***炼丹黑话？***
   
	FCN=全卷积（类似U-Net），FPN=Feature Pyramid Network，RNN=Recurrent/Recursive，ResNet=Residual，RCNN=Region-based CNN，RPN=Region Proposal Network

2. ***Recurrent 和 Recursive 的区别？***

	循环神经网络是时间上连续衔接，类似 Markovian Process，下句话的意思由这句话决定。递归神经网络是空间信息结构上的嵌套，类似一个树状图，这段话的意思由它的词决定。

3. **_一个最简单的 Transformer 需要训练哪些参数？_**

	$W_q$, $W_k$, $W_v$**

4. **_最简单的 Transformer 是否只能适用于 n input => n output?_**
	
	是的

5. **_Query, Key, Value 的意义和作用?_**

	Q 是

6. **_如何理解 feedback 和 feedforward？只使用 fb 和使用 fb+ff 的区别在于哪里？_**

7. **_解释一下 mIoU，mAP，AUC，ROC?_**

8. **_TF / PN 分别是针对 prediction 还是 gt?_**

	这个词是 **形容词+名词** 的构成方式。两者都只关注 prediction 结果本身。true/false 是指结果正确与否；positive/negative 是指结果的正反。
    
    > 联想 => 老友记第8集，Rachel验孕棒阳性，Monika安慰说：Maybe it is a false positive.

9.  ***如何解决训练过程中样本类别分布不平衡？***
	
	- （一个假设）能否根据训练集中样本类别比例，来调整训练过程中的 loss 权重？若 alpha ：beta = 3 ：1，为了避免 classifier 变成无脑认 alpha 的傻子，我们在训练过程中计算 loss 时可以给 alpha 的 MSE 乘3倍？使得 classifier 谨慎判断 alpha？
	
	- 使用 focal loss（见 Retina-Net by Kaiming He）

10. ***anchor-based 和 two-stage 的区别？***

	这是两个层面。anchor 的意义在于同时解决“在哪里？有什么？”，把一个region proposal + classification 转换为了 classification + regression 的问题，又快又精准。stage 的意义在于要不要先做 region proposal 来缩小检测范围，需要的话 region-based = two-stage。 

11. ***anchor-free 的优点和缺点？***
	
	- 训练阶段不需要计算 anchor 的 IoU
	- 不需要调整 anchor 的超参数

12.  ***one-stage 的特点和缺点？***


1. ***DL 问题中使用哪种方法进行模型验证？***
	
	使用 Cross Validation，一般是 a training set of 80-90% with the rest in the validation set.
    > *注意：使用 untouched dataset 的方法统称为 Cross Validation，即使只随机分了一个 validation set （学名叫做 holdout）也叫做 cross validation。但这里不要用 k-fold 或者 Leave One Out，Udacity 说 DL 算法不适用于这两种方法，因为通常面对大量的数据 validation 时间成本太高了，所以牺牲 variance 换取速度。*

1. ***ML model 中的 variance bias 分别指什么？***
	
	- Variance 是 the sensitivity of model (if replacing the training set with another unseen set, how much would the error rate change?)
	- Bias 是 the quality of fitting on training set (low bias = low error on training set -> highly potential overfitting)

1. ***什么样子的 Validation Set 是理想的？检查 Validation Set 应该注意哪些方面？***
	
	- 图片不能和 Training Set 太相似，最好是来自两个不同的 Sequence。切忌采用均匀分布来采样。
	- 标签的 Class Distribution 需要和 Training Set 相近。
	- 总结：需要在 Label 层面上保持一致，确保各类 class 的比例相近，避免宏观统计上的隐变量；但需要在 Input Signal 层面上避免相似，避免 overfitting 的效果作用在 validation set 上面。
	- 举例：training set 上是白天乡村的道路，validation set 是夜晚城市的道路，但两者都包含了 80% 的 Car 类和 20% 的 Person 类。

1. ***ConvNet 各层的 learnable parameters?***
	
	- **Conv**：`PreviousOutput x W x H x D + D` -> 别忘记给每一个 kernel 加上 bias 项，一共 D 个
	- **Pooling**: No parameters -> 池化层没有 bias，也没有 activation
	- **Dropout**: No parameters -> 只有一个超参数 $p_{drop}$
		- 具有阶段差异：在 training 时 acitvated，在 val 和 test 时 deactivated
		- 在 testing 时需要 scaling，因为 testing 使用了更多的 neurons，需要除以一个比例使得 inference 阶段的输出和训练时同一个数量级。但为了不在模型部署之后时浪费时间，这里反过来对 training 阶段使用 inverse dropout。
        > In practice, we use something called **inverted dropout**, where scaling is happening during training. Why? Because we want the model to be as fast as possible when deployed, so we'd rather have even this small operation happen during training instead of at inference time.
	- **BN: ** scaling parameter $\gamma$ + shifting parameter $\beta$
		- input 是一个具有 N 个 sample，M维 feature 的张量
		- BN 对于每一个 feature 去统计 Mean and Var over *N* batch samples，然后对于每一个 feature 去做 normalization
		- 最后为了避免 normalization 对于 representation 的改变，再引入模型参数 $\gamma$ 和 $\beta$ 来做 linear transformation。原文里表示：“经过正确的学习，假如必要的话，是有可能学习出来一组  gamma 和 beta 使得 BN 的结果和 input 相同（看似白忙活）。” 因此这种“先涨价再打折”的变换本质上是引入了一个组 learnable parameters 来调控某层 representation 的 mean 和 variance。
        > Note that simply normalizing each input of a layer may change what the layer can represent. For instance, normalizing the inputs of a sigmoid would constrain them to the linear regime of the nonlinearity. To address this, we make sure that the transformation inserted in the network can represent the identity transform.
	 - **LN: ** scaling parameter $\gamma$ + shifting parameter $\beta$
		- LN 不管 batch samples，而是对于每个 single sample 的全部 feature dimension 做 normalization。形象的例子：输入是一张过曝的照片，输出则是一张很暗的图。
		- BN 需要依赖较大的 batch_size 才有使用的价值，但 LN 和 bs 无关

1. ***为什么 DL 网络最初 shallow layers 的 kernal 很少，随着深度的增加 deep layers 的 kernel 数量越来越多？***

	从直觉上讲，最初的 feature extractor 识别的是比较底层的基础特征，点、线、角，而越抽象的特征其种类就越少，这就是抽象的意义所在。但随着深度增加，基于这些基础特征进行排列组合后的具象化高级特征 candidates 呈现几何式增长，因此需要更多的 kernals 来一对一地进行识别和评估。

1. ***专题：计算顺序在很多不同问题上的具体表现***

   1. **图像信息统计学**
      - （正确）mean of std/mean -> 分先计算每一张图全部像素点的 std 和 mean，再取平均
      - （错误）std/mean of mean -> 先把所有图像在像素层面上计算 std 和 mean
      - 补充：在 mean of mean 层面上两者结果是一样的，但是 mean of std 和 std of mean 是不同的

   1. **什么是 mAP**
	   1. 对于一张图，先把所有的 prediction 按照 score/confidence 排序，然后从高到低统计 precision recall（{1}, {1, 2}, {1, 2, 3}, {1, 2, 3, 4} 的顺序）来模拟 positive threshold 的移动
	   2. 依序在 PRPlot 上描画出点
	   3. 【重要】做 curve smoothing，需要以 recall 为横轴，保证 precision 是 monotonous 的
	   4. 计算 area under PR curve 得到这张图的 AP
	   5. 对于 test set 所有 sample 的 AP 取均值得到 mAP
   
   1. **Batch Norm 和 Layer Norm**
