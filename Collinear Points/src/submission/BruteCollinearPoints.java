import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> segments; //arraylist to add to when segments are found
	
	/**
	 * The brute force method checks every unordered quadruple (p, q, r, s) of points 
	 * and checks if they all lie on the same line. If they do, the endpoints of that segment
	 * are added to the final segments arraylist to be returned with the segments() method
	 * 
	 * @param points the array of points to compute on
	 */
	public BruteCollinearPoints(Point[] points) {
		//testing for null, or repeated points
		if (points == null) throw new IllegalArgumentException("Points array is null");
		
		int N = points.length;
		for (int i = 0; i < N; i++) {
			if (points[i] == null) {
				throw new IllegalArgumentException("Point in array is null");
			}
			
			for (int j = 0; j < i; j++) {
				if (points[i].compareTo(points[j]) == 0) {
					throw new IllegalArgumentException("Repeated point in input");
				}
			}
		}
		
		//sorting the points first ensures that for ordered quadruplet (i, j, k, h)
		//of points such that i < j < k < h, the endpoints of the line will be i and h,
		//meaning that no further computation is necessary to determine the maximal endpoints
		Arrays.sort(points);
		
		segments = new ArrayList<LineSegment>();
		
		//to avoid over counting, only check each quadruplet once
		//4! = 24 => does 1/24 of the looping as a full four-nested loop
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				for (int k = j+1; k < N; k++) {
					for (int h = k+1; h < N; h++) {
						Point p = points[i], q = points[j], r = points[k], s = points[h];
						double m1 = p.slopeTo(q);
						double m2 = q.slopeTo(r);
						double m3 = r.slopeTo(s);
						
						if (m1 == m2 && m2 == m3) {
							segments.add(new LineSegment(p, s));
						}
					}
				}
			}
		}
	}
	
	/**
	 * Gives the number of segments, which is just the size of the arraylist segments
	 * @return the size
	 */
	public int numberOfSegments() {
		return segments.size();
	}
	
	
	/**
	 * Converts the arraylist to an array and returns
	 * @return an array with correct size containing the segments in the order they were found
	 */
	public LineSegment[] segments() {
		LineSegment[] segmentsArray = new LineSegment[numberOfSegments()];
		segments.toArray(segmentsArray);
		return segmentsArray;
	}
	
	public static void main(String[] args) {
		// read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}