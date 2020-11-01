package graphs;

import java.io.IOException;

import edu.princeton.cs.algs4.In;

/**
 * An undirected graph is a graph where every edge is bidirectional, i.e.
 * There is an edge from a node u to a node v if and only if there is an edge from v
 * to u. A consequence of this is that every path on the graph can be reversed, and that
 * connectivity can be checked with one search of the graph.
 * 
 * 
 * @author Adham Ibrahim
 * @version 9/10/2020
 */

public class UndirectedGraph extends Graph {
	
	public UndirectedGraph(int V) {
		super(V);
	}
	
	public UndirectedGraph(In in) throws IOException {
		super(in);
	}
	
	/**
	 * Add an edge from u to v and from v to u, making it
	 * bidirectional.
	 */

	@Override
	public void addEdge(int u, int v) {
		E++;
		
		adj[u].add(v);
		adj[v].add(u);
	}
	
}
