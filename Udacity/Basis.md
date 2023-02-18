<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

## @ IoU Implementation
```python
''' Description
topleft & bottomright, origin @ topleft [矩阵式索引]
BBox_1: x1,x2,y1,y2
BBox_2: x1,x2,y1,y2
'''

# Calculate Intersection Coordinates
intersection_x1 = min(BBox_1.x1, BBox_2.x1) 
intersection_x2 = max(BBox_1.x2, BBox_2.x2) 
intersection_y1 = min(BBox_1.y1, BBox_2.y1)
intersection_y2 = max(BBox_1.y2, BBox_2.y2)

# Calculate Area
area_i = (intersection_x2 - intersection_x1) * (intersection_y2 -intersection_y1)
area_o = area_BBox1 + area_BBox2 - 2*area_i
```

## @ NMS Implementation (回想一下 Soft-NMS)
```python
''' Psudou-code:
1. sort dict{'bbox': , 'score': } by score in descending
使用 lambda function
2. 分数优先，保证分数最高的 bb 周围没有 redundant bb，违者删除
3. 循环一轮结束，保留剩下的
'''

```