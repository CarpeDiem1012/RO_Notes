# Training Loss

1. 理想的情况下

   For the $D^{all}$​ and its optimal $h^{all}$​, find such optimal $h^{train}$​ that
   $$
   L(h^{train},D^{all}) - L(h^{all},D^{all}) \le \delta
   $$
   假如是 $>$ 的话，那么就是一个 bad training  dataset $D^{train}$

   - 注意，这里不需要绝对值，因为定义 $h^{all}$ 是在 $D^{all}$ 上最优的，一定是 loss 最小的
   - $L(h^{train},D^{train})$ 很可能要比 $L(h^{all},D^{all})$ 还要小

2. 如何理解Model Complexity 与过拟合？

   - 浅显直观角度上，用多项式拟合函数的过程来理解，复杂模型参数多，容易过拟合.
   - 这里给出了更深层面的解答——从概率角度，结合了模型参数和采样数量。Hoeffding's inequality：
   $$
   P(D^{train}\ is\ bad) \le \abs{\mathcal{H}}2e^{-2N\epsilon^2}
   $$
   - 欠拟合的本质，是模型参数不够多，导致了理想的 Loss ($L(h^{all},D^{all})$) 太大了，假如理想是崩坏的，就算现实和理想再接近 ($\abs{L(h^{train},D^{all}) - L(h^{all},D^{all})}<\delta$) 也充其量是崩坏的现实
   - 过拟合的本质，是因为参数太多、样本太少，想要找到足够接近真实分布的训练集的概率太小了。导致我们看上去似乎降低了足够的 loss，但实际上并不能在真实分布上有很小的 loss

# Optimization Fails

1. Seddle Point & Local Minimum 

   查看当前的 Hessian 矩阵

   找到一个负特征值对应的特征向量u，保证 uHu小于0。因此，以u的方向来更新 theta，就可以使得loss更小

2. Seddle Point ***v.s.*** Local Minimum 

   - 实际上，很难同时考虑所有的模型参数来逼近真正的 local minimum。很多时候只能在一部分参数上达到 local minimum，但在其他参数维度上不一定是最小的。

   - $minimum ratio =\frac{number\ of\ positive\ eigens}{number\ of\ all\ eigens}$

     等于 1 时，说明所有eigen value都是正值。这时，是真的local minimum。

# Small Batch v.s. Large Batch

1. GPU 存在一定“红利区”，在这个阶段，假如使用小的 batch_size 反而是浪费了计算资源。

2. 因为在 红利区之内，增大 batch_size 不会增加每个 batch 的计算时长太多，但可以极大程度减少 每一轮 epoch 的训练次数。

3. Which is Better Performed？

   - 首先，更小的batch_size通常有更好的训练结果。
     - 原因：optimization fails

   - 其次，更小的batch_size通常也有更好的 test accuracy。
     - 原因：因为小的batch在训练的时候具有随机性，一个很狭窄的峡谷，很难困住小的batch，容易受到随机性影响跳出去。因此，最终停留的位置都是平坦的大峡谷。但大的batch更稳定，同时也更容易困在狭窄的峡谷里。

4. Trade-off：用更好的训练性能（both on training and test data）来换取更快的训练速度（在GPU的红利区之内，batch越大，训练越快）



* 注意：PyTorch框架中，假如 criteria = nn.CrossEntrophyLoss()，会自动在 net = nn.Sequential() 末尾加一个 layer = nn.Softmax()，不要额外多加入一层！
