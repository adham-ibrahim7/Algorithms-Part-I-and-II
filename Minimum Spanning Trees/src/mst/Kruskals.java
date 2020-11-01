package mst;

import java.util.Collections;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Kruskals extends MST {
	
	private WeightedQuickUnionUF uf;

	private Bag<Edge> spanningTree = new Bag<>();
	
	public Kruskals(EdgeWeightedGraph G) {
		super(G);
		uf = new WeightedQuickUnionUF(G.V());
		
		TreeSet<Edge> sortedEdges = new TreeSet<>(Collections.reverseOrder());
		for (Edge edge : G.edges())
			sortedEdges.add(edge);
		
		for (Edge edge : sortedEdges) {
			int v = edge.either();
			int w = edge.other(v);
			
			if (uf.find(v) != uf.find(w)) {
				uf.union(v, w);
				spanningTree.add(edge);
			}
		}
	}

	@Override
	public Iterable<Edge> minSpanningTree() {
		return spanningTree;
	}

}
