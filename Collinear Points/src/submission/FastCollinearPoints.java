import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private ArrayList<LineSegment> segments; //arraylist to add to when segments are found
	
	/**
	 * The faster method sorts points by slope with an origin point, and checks if three points match, making
	 * the four (including the origin) collinear
	 * 
	 * @param points the array of points to compute on
	 */
	public FastCollinearPoints(Point[] points) {
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
		
		segments = new ArrayList<LineSegment>();
		
		Point[] copyPoints = Arrays.copyOf(points, N);
		
		for (int i = 0; i < N; i++) {
			Point origin = points[i];
			
			Arrays.sort(copyPoints);
			Arrays.sort(copyPoints, origin.slopeOrder());
			
			int count = 0;
			Point start = null;
			for (int j = 1; j < N-1; j++) {
				if (copyPoints[j].slopeTo(origin) == copyPoints[j+1].slopeTo(origin)) {
					count++;
					if (count == 1) {
						start = copyPoints[j];
						count++;
					} else if (count >= 3 && j == N - 2) {
						if (start.compareTo(origin) > 0) {
							segments.add(new LineSegment(origin, copyPoints[j + 1]));
						}
					}
				} else {
                    if (count >= 3 && start.compareTo(origin) > 0) {
                        segments.add(new LineSegment(origin, copyPoints[j]));
                    }
                    count = 0;
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
		return segments.toArray(new LineSegment[numberOfSegments()]);
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
	    
	    //StdOut.println("Beginning search");

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	    
	    //StdOut.println("Completed search");
	}
}