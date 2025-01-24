import sys

class Solution:
    def __init__(self) -> None:
        pass
    def jump(self, nums) -> int:
        max_i = 0
        last_max_i = 0
        print("jump")
        # 类似 Bellman-Ford 算法，以步数 k 为基准进行迭代拓展
        # 在保证有解的情况下，k=n 是最坏情况，因此函数一定可以在 n 次迭代之内 return
        for k_step in range(len(nums)):
            print("k_step = {}".format(k_step))
            # 检查是否满足条件
            # [WARNING] 这里一定要放在状态更新之前
            if (max_i >= len(nums)-1):
                print(k_step)
            
            # 状态更新 k -> k+1
            print("i = [{}, {}]".format(last_max_i, max_i), end=", ")
            max_i_backup = max_i
            for i in range(last_max_i, max_i+1):
                # 第 k 步时，当前可以达到的格子是 i /in [0, max_i]
                # 第 k+1 步时可以达到的最远格子 max_i 取决于
                # 第 k 步时所有格子可以达到的最远格子之中的最大值
                if (i + nums[i] > max_i):
                    max_i = i + nums[i]
                    print("max_i = {}".format(max_i))
            last_max_i = max_i_backup+1

if __name__ == "__main__":
    solution = Solution()
    args = sys.argv[1:]
    solution.jump([int(x) for x in args])