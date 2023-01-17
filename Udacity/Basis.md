<link rel="stylesheet" type="text/css" href="auto-number-title.css" />

## @ IoU Implementation
```python
# topleft & bottomright, origin @ topleft [矩阵式索引]
# BBox_1: x1,x2,y1,y2
# BBox_2: x1,x2,y1,y2

# Calculate Intersection Coordinates
intersection_x1 = min(BBox_1.x1, BBox_2.x1) 
intersection_x2 = max(BBox_1.x2, BBox_2.x2) 
intersection_y1 = min(BBox_1.y1, BBox_2.y1)
intersection_y2 = max(BBox_1.y2, BBox_2.y2)

# Calculate Area
area_i = (intersection_x2 - intersection_x1) * (intersection_y2 -intersection_y1)
area_o = area_BBox1 + area_BBox2 - 2*area_i
```

