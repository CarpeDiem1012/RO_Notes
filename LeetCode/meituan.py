import sys

# for line in sys.stdin:
#     a = line.split()
#     print(int(a[0]) + int(a[1]))

n = int(input())
a = list(map(int, input().split()))
k = len(a)
sum = 0
for i in range(k):
    sum =+ a[i]
avg = sum // k
    
# min +1, max -1
# sum[i] == sum/len(a) : return
def success(a):
    for i in range(k):
        if a[i] == avg 
    a.sort()
    a[0] += 1
    a[k] -= 1