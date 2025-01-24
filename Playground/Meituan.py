from math import log2
import copy

def numOfArray(array):
    if (max(array)==array[0]):
        return 0
    else:
        array_1 = copy.deepcopy(array)
        array_1[0] = array_1[0]*2
        array_2 = copy.deepcopy(array)

        return min(numOfArray(array_1), numOfArray(array_2))+1

n = int(input())
array = list(map(int, input().split()))

print(numOfArray(array))