import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Vector implements Comparable<Vector> {
	public final double x, y;
	public final double r;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
		
		this.r = Math.sqrt(x * x + y * y);
	}
	
	public static boolean ccw(Vector a, Vector b, Vector c) {
        double area = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        return area > 0;
    }
	
	public static Vector normalize(Vector p) {
		return new Vector(p.x / p.r, p.y / p.r);
	}

	public int compareTo(Vector p) {
		if (y < p.y) return -1;
		else if (y > p.y) return 1;
		else if (x < p.x) return -1;
		else if (x > p.x) return 1;
		return 0;
	}
	
	public boolean equals(Vector p) {
		return compareTo(p) == 0;
	}
	
	public double angleTo(Vector p) {
		double dy = p.y - y;
		double dx = p.x - x;
		
		return Math.atan2(dy, dx);
	}
	
	public Comparator<Vector> angularOrder() {
		return new AngularOrder();
	}
	
	private class AngularOrder implements Comparator<Vector> {
		public int compare(Vector p, Vector q) {
			double a1 = angleTo(p);
			double a2 = angleTo(q);
			if (a1 < a2) return -1;
			if (a1 > a2) return 1;
			return 0;
		}
	}
	
	public Vector displacement(Vector p) {
		return new Vector(p.x - x, p.y - y);
	}
	
	public void draw() {
		StdDraw.point(x, y);
	}
	
	public void drawTo(Vector p) {
		StdDraw.line(x, y, p.x, p.y);
	}
}