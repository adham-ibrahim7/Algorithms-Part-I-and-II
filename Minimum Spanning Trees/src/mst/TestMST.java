package mst;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class TestMST {
	public static void main(String[] args) {
		final int V = 300;
		final double MAXDIST = 0.1;
		
		StdDraw.enableDoubleBuffering();
		StdDraw.setPenRadius(0.010);
		
		double[] locationX = new double[V];
		double[] locationY = new double[V];
		
		for (int i = 0; i < V; i++) {
			locationX[i] = StdRandom.uniform();
			locationY[i] = StdRandom.uniform();
		}

		EdgeWeightedGraph G = new EdgeWeightedGraph(V);
		
		for (int i = 0; i < V; i++) {
			for (int j = i+1; j < V; j++) {
				double dx = locationX[i] - locationX[j];
				double dy = locationY[i] - locationY[j];
				double dist = Math.sqrt(dx*dx + dy*dy);
				
				if (dist <= MAXDIST)
					G.addEdge(new Edge(i, j, dist));
			}
		
			StdDraw.point(locationX[i], locationY[i]);
		}
		
		StdDraw.show();
		
		KruskalMST mst = new KruskalMST(G);
		
		StdDraw.disableDoubleBuffering();
		StdDraw.setPenRadius(0.005);
		
		for (Edge e : mst.edges()) {
			int v = e.either();
			int w = e.other(v);
			
			StdDraw.line(locationX[v], locationY[v], locationX[w], locationY[w]);
			StdDraw.pause((int) (e.weight() * 1000));
		}
	}
}
