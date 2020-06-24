import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;

public class PointSET {
	private TreeSet<Point2D> points;
	
	//construct an empty set of points
	public PointSET() {
		points = new TreeSet<Point2D>();
	}
	
	//is the set empty?
	public boolean isEmpty() {
		return points.isEmpty();
	}
	
	//number of points in the set
	public int size() {
		return points.size();
	}
	
	//add the point to the set (effectively doing nothing if the point is already in the set)
	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException("cannot insert null point");
		points.add(p);
	}
	
	//does the set contain the point p?
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException("cannot check if null point is contained");
		return points.contains(p);
	}
	
	//draws all points to standard draw
	public void draw() {
		for (Point2D point : points) {
			point.draw();
		}
	}
	
	//all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new IllegalArgumentException("null rectangles are bad");
		List<Point2D> pointsInRange = new ArrayList<Point2D>();
		
		for (Point2D point : points) {
			if (rect.contains(point)) {
				pointsInRange.add(point);
			}
		}
		
		return pointsInRange;
	}
	
	//a nearest neighbor in the set to point p
	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException("cannot check with null point");
		Point2D nearestPoint = null;
		
		for (Point2D point : points) {
			if (nearestPoint == null || nearestPoint.distanceSquaredTo(p) > point.distanceSquaredTo(p)) {
				nearestPoint = point;
			}
		}
		
		return nearestPoint;
	}
	
	//unit testing of methods (optional)
	public static void main(String[] args) {
		PointSET ps = new PointSET();
		ps.insert(new Point2D(0.1, 0.1));
		ps.insert(new Point2D(0.9, 0.9));
		System.out.println(ps.nearest(new Point2D(0.7, 0.7)));
	}
}
