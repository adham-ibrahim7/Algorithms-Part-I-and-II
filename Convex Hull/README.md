# Convex Hull
The *convex hull* of a set of points is the subset of those points that form a polygon of minimum perimeter that encloses all of the points.

An efficient method to compute the convex hull sorts the points by their angle formed with the lowermost point and the x-axis (the lowermost point must be in the convex hull), taking only linearithmic time.
