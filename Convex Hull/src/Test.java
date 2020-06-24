import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stopwatch;

public class Test {
	public static void main(String[] args) {
		StdDraw.setPenRadius(0.005);
		StdDraw.setXscale(-1, 1);
		StdDraw.setYscale(-1, 1);
		StdDraw.enableDoubleBuffering();
		
		int N = 100000;
		int step = 100;
		
		Point2D[] points = new Point2D[N];
		for (int i = 0; i < N; i++) {
			points[i] = new Point2D(StdRandom.uniform(-0.8, 0.8), StdRandom.uniform(-0.8, 0.8));
		}
		
		Stopwatch st = new Stopwatch();
		
		double[] t = new double[N / step];
		
		for (int M = 0; M < N; M += step) {
			st = new Stopwatch();
			ConvexHull.compute(Arrays.copyOf(points, M));
			t[M / step] = st.elapsedTime();
		}
		
		for (int i = 0; i < t.length; i++) {
			System.out.println(t[i]);
		}
		
		/*
		 *draw the hull
		 * 
		 *  
		for (int i = 0; i < points.length; i++) {
			Point2D point = points[i];
			StdDraw.text(point.x(), point.y(), Integer.toString(i));
		}
		
		for (int i = 0; i < hull.size(); i++) {
			hull.get(i).drawTo(hull.get((i + 1) % (hull.size())));
		}
		
		StdDraw.show();*/
	}
}