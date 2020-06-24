import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import edu.princeton.cs.algs4.Point2D;

/*
 * Class that takes an array of points and provides a method to find and return the convex hull.
 * Convex Hull: The vertices of the convex polygon containing a set of points with minimum perimeter.
 */
public class ConvexHull {
	/*
	 * Graham scan algorithm used
	 */
	public static List<Point2D> compute(Point2D[] points) {
		if (points.length < 2) return null;
		
		//get lowermost point
		Point2D p = new Point2D(0.0, Double.MAX_VALUE);
		for (Point2D point : points) {
			if (point.y() < p.y()) p = point;
		}
				
		//p.atan2Order() gives comparator class to compare
		//by polar angle
		Arrays.sort(points, p.atan2Order());
		
		Stack<Point2D> stack = new Stack<Point2D>();
		//first two have to be in final hull
		stack.add(points[0]);
		stack.add(points[1]);
		
		for (int i = 2; i < points.length; i++) {
			Point2D top = stack.pop();
			//pop off stack if clockwise,
			while (Point2D.ccw(stack.peek(), top, points[i]) <= 0) {
				top = stack.pop();
			}
			
			//once counterclockwise, add new point to stack
			stack.add(top);
			stack.add(points[i]);
		}
		
		return stack;
	}
}