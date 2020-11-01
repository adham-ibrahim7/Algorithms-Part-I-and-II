package graphs;

import java.io.IOException;

import edu.princeton.cs.algs4.In;

/**
 * Directed graphs are graphs where every edge is directed from some u to some v, and there is not
 * necessarily and edge from v to u. Directed graphs are useful in some contexts, and must be treated 
 * differently, since connectivity is harder to check.
 * 
 * @author Adham Ibrahim
 * @version 9/10/2020
 */

public class DirectedGraph extends Graph {
	
	public DirectedGraph(int V) {
		super(V);
	}
	
	public DirectedGraph(In in) throws IOException {
		super(in);
	}
	
	/**
	 * Add an edge only from u to v;
	 */
	
	@Override
	public void addEdge(int u, int v) {
		E++;
		
		adj[u].add(v);
	}
	
}
