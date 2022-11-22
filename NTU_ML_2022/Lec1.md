# 机器学习的本质


寻找一个function


# 机器学习的分类


- regression（预测pm2.5）
- classification（AlphaGo）
- Structural Learning == create something with structure (image, document)


# How to find the function


1. Function with unknown parameters (weights, bias)
   $$
   y = b + wx
   $$


2. Define loss from training data == Measure how good a set of value is
   $$
   L(b,w)
   $$


3. Error Surface


4. Optimaztion
   $$
   w^*, b^* = arg \min_{w,b} L
   $$


   - Gradient Descent
   - Does local minima truly cause the problem? No! 是一个假问题


# Linear Model is too Simple


- Model Bias（把复杂模型用简单模型来表示，这个过程本身会产生的Bias）


- Solution


  1. All Piecewise Linear Curves


  2. Beyond Piecewise Linear?


     - Continuous Nonlinear Functions == Approximated by Enough Piecewise Linear Curves
  
  3.  Hard sigmoid (Piecewise Linear) ==> Sigmoid (Smooth)
     $$
     y_i = c \frac{1}{1 + e^{b + wx}} \\
     y = b + \sum_i^n{c \frac{1}{1 + e^{b + wx}}}
     $$
     
  4. Sigmoid 中不同参数的影响
  
     - $w$ 影响斜率
  
     - $b$ 沿着x轴平移
     - $c$ 拉伸y坐标
  
  5. Better than Sigmoid? ReLU (Rectified Linear Unit)
  
     - 本质上，ReLU可以看作是 Hard Sigmoid的子元素
     - Why？为什么线性的、不平滑可导的 ReLU，要比非线性的、平滑可导的 Sigmoid 要好呢？


# Adaptive Learning Rate


- Adagrad (RMS)
  $$
  lr_{i} = \frac{lr}{\sigma_i} \\
  \sigma_i = RMS(grad)_{0:i} = \sqrt{\sum_{ii=0}^i grad_{ii}}
  $$
  几何角度上，RMS 可以看作在不断扩展的高维空间中，平均到每一条边长上的正交空间的对角线距离


- Momentum: weighted sum of the previous gradients


  不使用当前的 gradient，而是使用过去时间中的 grad 的加权来代表（注意这里的求和是**有方向**的向量，因此不会和 RMS 的结果抵消）


- RMSProp (RMS + alpha)


- Adam == RMSProp + Momentum


- Learning Rate Scheduling


  - Learning Rate Decay (with time)
  
  - Warm Up（应用于 Res-Net 和 Transformer） ==> RAdam
  
    一开始使用比较小的learning rate，对于error surface当前点的 grad 情况做一些小成本的探索，然后再加到最大的 learning rare，再慢慢减小
  
  ![image-20220302122450959](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20220302122450959.png)