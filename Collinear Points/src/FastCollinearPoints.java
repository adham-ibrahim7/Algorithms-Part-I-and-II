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
		//checking for null or repeated points
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
		
		//copy of points, so we can access the original order, which will never change
		//since we sort the copyPoints, we need to still access the original order
		Point[] copyPoints = Arrays.copyOf(points, N);
		
		for (int i = 0; i < N; i++) {
			Point origin = points[i];
			
			//sorting by natural order first ensures that after the slope order sort,
			//points with the same slope will be in natural order amongst themselves
			Arrays.sort(copyPoints);
			Arrays.sort(copyPoints, origin.slopeOrder());
			
			int count = 0;
			//marker to show where the first point in the subarray is
			//to avoid overcounting, only add the segment when the start point
			//is "greater" than the origin, ensuring that the same line, or any
			//subset of the line wont be counted
			Point start = null;
			for (int j = 1; j < N-1; j++) {
				if (copyPoints[j].slopeTo(origin) == copyPoints[j+1].slopeTo(origin)) {
					count++;
					if (count == 1) {
						//mark the start on the first comparison
						start = copyPoints[j];
						count++;
					} else if (count >= 3 && j == N - 2) {
						//this check is necessary, because if the last few points are the same,
						//without checking this, we would miss that line
						if (start.compareTo(origin) > 0) {
							segments.add(new LineSegment(origin, copyPoints[j + 1]));
						}
					}
				} else {
					//once the subarray ends, add a new segment from the origin to the end
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
	    
	    StdOut.println("Beginning search");
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    StdOut.println("Completed search");
	    /*
	    // print and draw the line segments
	    
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	    */
	    
	}
}