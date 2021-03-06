import java.io.*;
import java.util.*;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stopwatch;

public class Testing {
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("assets/input1M.txt"));
		
		KdTree tree = new KdTree();
		
		String line = f.readLine();
		while (line != null) {
			StringTokenizer st = new StringTokenizer(line);
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			tree.insert(new Point2D(x, y));
			line = f.readLine();
		}
		
		Stopwatch stopwatch = new Stopwatch();
		
		int total = 100000;
		for (int i = 0; i < total; i++) {
			double x = Math.random(), y = Math.random();
			tree.nearest(new Point2D(x, y));
		}
		
		double t = stopwatch.elapsedTime();
		
		System.out.println(total / t);
	}
}
