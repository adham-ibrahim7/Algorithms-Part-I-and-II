import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;


/**
 * The Kd - Tree is a data structure that generalizes on BSTs
 * This class allows for the construction of a two dimensional binary search tree
 * using the Point2D class in algs4.jar
 * 
 * @author Adham Ibrahim
 */
public class KdTree {
	/**
	 * Structure for recursive search in a tree
	 */
	private class TreeNode {
		//the point at this search node
		private Point2D point;
		//the corresponding rectangle, generated from the past nodes'
		private RectHV rect;
		//links to left and right children
		private TreeNode left, right;
		
		private TreeNode(Point2D point, RectHV rect) {
			this.point = point;
			this.rect = rect;
		}
	}
	
	//a pointer to the root of the tree
	private TreeNode root;
	//the number of elements in the set
	private int size;
	
	//construct an empty tree (no initializations necessary)
	public KdTree() {}
	
	//the tree is empty only when no nodes have been added
	public boolean isEmpty() {
		return root == null;
	}
	
	//number of points in the set
	public int size() {
		return size;
	}
	
	//add the point to the set (effectively doing nothing if the point is already in the set)
	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException("cannot insert null point");
		
		if (contains(p)) return;
		
		size++;
		
		if (root == null) {
			root = new TreeNode(p, new RectHV(0, 0, 1, 1));
			return;
		}
		
		TreeNode node = root;
		
		int depth = 0;
		
		double xmin, xmax, ymin, ymax;
		
		while (true) {
			xmin = node.rect.xmin();
			xmax = node.rect.xmax();
			ymin = node.rect.ymin();
			ymax = node.rect.ymax();
			
			if (depth % 2 == 0) {
				if (p.x() < node.point.x()) {
					if (node.left == null) {
						xmax = node.point.x();
						node.left = new TreeNode(p, new RectHV(xmin, ymin, xmax, ymax));
						return;
					}
					node = node.left;
				} else {
					if (node.right == null) {
						xmin = node.point.x();
						node.right = new TreeNode(p, new RectHV(xmin, ymin, xmax, ymax));
						return;
					}
					node = node.right;
				}
			} else {
				if (p.y() < node.point.y()) {
					if (node.left == null) {
						ymax = node.point.y();
						node.left = new TreeNode(p, new RectHV(xmin, ymin, xmax, ymax));
						return;
					}
					node = node.left;
				} else {
					if (node.right == null) {
						ymin = node.point.y();
						node.right = new TreeNode(p, new RectHV(xmin, ymin, xmax, ymax));
						return;
					}
					node = node.right;
				}
			}
			
			depth++;
		}
	}
	
	//does the set contain the point p?
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		
		if (isEmpty()) return false;
		
		TreeNode node = root;
		
		int depth = 0;
		while (true) {
			if (node.point.equals(p)) return true;
			
			double pField, nodeField;
			if (depth % 2 == 0) {
				pField = p.x();
				nodeField = node.point.x();
			} else {
				pField = p.y();
				nodeField = node.point.y();
			}
			
			if (pField < nodeField) {
				if (node.left == null) {
					return false;
				}
				node = node.left;
			} else {
				if (node.right == null) {
					return false;
				}
				node = node.right;
			}
			
			depth++;
		}
	}
	
	//recursive helper function to draw the kdtree
	private void draw(TreeNode node, int depth) {
		//base case, do nothing if null node
		if (node == null) return;
		
		//draw line
		StdDraw.setPenRadius(0.004);
		if (depth % 2 == 0) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
		}
		
		//draw point
		StdDraw.setPenRadius(0.010);
		StdDraw.setPenColor(StdDraw.BLACK);
		node.point.draw();
		
		//recursively draw children
		draw(node.left, depth + 1);
		draw(node.right, depth + 1);
	}
	
	//draws all points to standard draw
	public void draw() {
		draw(root, 0);
	}
	
	/**
	 * Takes a temporary results list and adds points to it if they are in range
	 * If the nodes' "rectangle" does not intersect the query rect, theres no reason
	 * to bother searching the nodes children
	 */
	private void searchRange(List<Point2D> results, TreeNode node, RectHV rect) {
		if (node == null || !node.rect.intersects(rect)) return;
		
		if (rect.contains(node.point)) {
			results.add(node.point);
		}
		
		searchRange(results, node.left, rect);
		searchRange(results, node.right, rect);
	}
	
	//all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		
		if (rect == null) throw new IllegalArgumentException();
		
		List<Point2D> pointsInRange = new ArrayList<Point2D>();
		
		searchRange(pointsInRange, root, rect);
		
		return pointsInRange;
	}
	
	/*
	 * Recursiver helper for nearest neighbor search
	 */
	
	//temporary field to store closest
	private Point2D closest;
	
	private void searchNearest(Point2D p, TreeNode node) {
		double bestSoFar = p.distanceSquaredTo(closest);
		
		if (node == null || bestSoFar < node.rect.distanceSquaredTo(p)) return;
		
		if (bestSoFar > p.distanceSquaredTo(node.point)) {
			closest = node.point;
		}
		
		searchNearest(p, node.left);
		searchNearest(p, node.right);
	}
	
	//a nearest neighbor in the set to point p
	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		
		closest = root.point;
		
		searchNearest(p, root);
		
		return closest;
	}
	
	//unit testing of methods (optional)
	public static void main(String[] args) {
		KdTree t = new KdTree();
		double[][] points = {{0.7, 0.2}, {0.5, 0.4}, {0.2, 0.3}, {0.4, 0.7}, {0.9, 0.6}};
		
		for (double[] point : points) {
			t.insert(new Point2D(point[0], point[1]));
		}
		
		System.out.println(t.contains(new Point2D(0.2, 0.5)));
		
		t.draw();
	}
}
