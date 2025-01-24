class Solution:
    def minimumTotal(self, triangle) -> int:
        start_idx = 0
        n_layer = 1
        while (n_layer*(n_layer+1)/2 != len(triangle)):
            start_idx += n_layer
            n_layer += 1
        minSum = [0 for i in range(n_layer)]
        while (n_layer > 1):
            nextMinSum = []
            for i in range(n_layer - 1):
                left = minSum[i] + triangle[start_idx + i]
                right = minSum[i+1] + triangle[start_idx + i+1]
                nextMinSum.append(min(left, right))
            minSum = nextMinSum
            start_idx -= n_layer
            n_layer -= 1
        
        return minSum[0] + triangle[0]

if __name__=="__main__":
    s = Solution()
    s.minimumTotal([[2],[3,4],[6,5,7],[4,1,8,3]])