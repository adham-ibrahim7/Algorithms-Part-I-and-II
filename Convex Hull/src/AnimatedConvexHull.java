import java.awt.Color;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/*
 *Animates the process of computing convex hull
 */
public class AnimatedConvexHull {
	//size of points
	private final int N;
	//all points
	private Vector[] points;
	
	//lowermost point (stored for animation purposes)
	private Vector p;
	//angle to the point p (for drawing purposes)
	private Vector[] directions;
	//stack to hold temporary states of hull
	private Stack<Vector> stack;
	//the index we reached in points when computing hull
	private int index;
	//whether or not we show detailed drawing or just points
	private boolean showAll;
	//animation rate
	private int waitMillis;
	//animation colors
	private final Color DEFAULT_COLOR, STACK_COLOR;
	
	/*
	 * Initialize with points
	 * sort points by polar angle from lowermost point
	 */
	public AnimatedConvexHull(int _N) {
		//generate points
		N = _N;
		points = new Vector[N];
		for (int i = 0; i < N; i++) {
			double x = StdRandom.uniform(-0.8, 0.8);
			double y = StdRandom.uniform(-0.8, 0.8);
			points[i] = new Vector(x, y);
		}
		
		//get lowermost point
		p = new Vector(0.0, Double.MAX_VALUE);
		for (Vector point : points) {
			if (point.y < p.y) p = point;
		}
		
		//p.atan2Order() gives comparator class to compare
		//by polar angle
		Arrays.sort(points, p.angularOrder());
		
		directions = new Vector[N];
		for (int i = 1; i < N; i++) {
			directions[i] = Vector.normalize(p.displacement(points[i]));
		}
		
		//initialize stack with first two points
		stack = new Stack<Vector>();
		stack.add(points[0]);
		stack.add(points[1]);
		
		//begin at 2
		index = 2;
		
		//only show detail if less than 20
		showAll = N <= 20;
		
		//animate depending on input
		waitMillis = (showAll) ? 300 : (N <= 50) ? 150 : 50;
		
		DEFAULT_COLOR = StdDraw.BLACK;
		STACK_COLOR = StdDraw.BOOK_RED;
		
		StdDraw.setScale(-1.0, 1.0);
		StdDraw.enableDoubleBuffering();
	}
	
	//a method to wait a bit for animation
	public void pause() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(waitMillis);
	}
	
	/*
	 * Graham scan algorithm used
	 * Method will process nodes one at a time, for animation
	 */
	public void next() throws InterruptedException {
		showPoints();
		dashedLine(stack.peek(), points[index]);
		
		pause();
		
		Vector top = stack.pop();
		//pop off stack if clockwise,
		while (!Vector.ccw(stack.peek(), top, points[index])) {
			top = stack.pop();
		}
		
		showPoints();
			
		//once counterclockwise, add new point to stack
		stack.add(top);
		stack.add(points[index++]);
		
		showPoints();
		
		pause();
	}
	
	//have we finished animating
	public boolean isDone() {
		return index == N;
	}
	
	//animate the drawing process
	public void animate() throws InterruptedException {
		while (!isDone()) next();
		//connect end to start
		stack.add(p);
		showPoints();
		
		System.out.println("Finished");
	}
	
	/*
	 * Method to draw points and the current stack
	 */
	public void showPoints() {
		StdDraw.clear();
		StdDraw.setPenColor(DEFAULT_COLOR);
		
		//draw points themselves
		StdDraw.setPenRadius(0.010);
		for (int i = 0; i < N; i++) {
			points[i].draw();
		}
		
		//show stack
		StdDraw.setPenColor(STACK_COLOR);
		StdDraw.setPenRadius(0.005);
		for (int i = 1; i < stack.size(); i++) {
			stack.get(i - 1).drawTo(stack.get(i));
		}
		
		if (!showAll) {
			int completion = (int) Math.round(((double) index / N) * 100);
			StdDraw.text(0.9, 0.9, Integer.toString(completion) + "%");
			StdDraw.show();
			return;
		}
		
		//distance from point to text
		double k = 0.05;
		
		StdDraw.setPenColor(DEFAULT_COLOR);
		StdDraw.setPenRadius(0.001);
		for (int i = 1; i < N; i++) {
			//draw line from lowermost
			points[i].drawTo(p);
			
			double x = points[i].x + directions[i].x * k;
			double y = points[i].y + directions[i].y * k;
			//write number
			StdDraw.text(x, y, Integer.toString(i));
		}
		StdDraw.text(p.x, p.y - k, "0");
		
		StdDraw.show();
	}
	
	//draw a dashed line from p to q
	public void dashedLine(Vector p, Vector q) {
		StdDraw.setPenColor(STACK_COLOR);
		
		int count = 10;
		double dx = (q.x - p.x) / count;
	    double dy = (q.y - p.y) / count;
	    
		for (int i = 0; i < count; i += 2) {
			StdDraw.line(p.x + i * dx, p.y + i * dy, p.x + (i + 1) * dx, p.y + (i + 1) * dy);
		}
		
		StdDraw.show();
	}
	
	//test client
	public static void main(String[] args) throws InterruptedException {
		AnimatedConvexHull ach = new AnimatedConvexHull(50);
		ach.animate();
	}
}