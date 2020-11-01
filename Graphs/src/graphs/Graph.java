package graphs;

import java.io.IOException;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;

/**
 * A template class for a graph. The class is abstract since
 * only subclasses are meant to be constructed, namely undirected graphs and
 * directed graphs. A graph must maintain nodes and edges.
 * 
 * @author Adham Ibrahim
 * @version 9/10/2020
 */

public abstract class Graph {
	
	protected final int V;
	protected int E;
	protected LinkedList<Integer>[] adj;
	
	/**
	 * Initialize a graph with V vertices, and no edges
	 * 
	 * @param V
	 */
	public Graph(int V) {
		this.V = V;
		initializeAdj();
	}
	
	/**
	 * Initialize a graph from an instream. The constructor assumes the 
	 * input format is as follows.
	 * 
	 * V
	 * E
	 * u_1, v_1,
	 * u_2, v_2,
	 * .
	 * .
	 * .
	 * u_E, v_E
	 * 
	 * @param in
	 * @throws IOException
	 */
	public Graph(In in) throws IOException {
		V = in.readInt();
		int E = in.readInt();
		
		initializeAdj();
		
		for (int e = 0; e < E; e++) {
			int u = in.readInt();
			int v = in.readInt();
			
			addEdge(u, v);
		}
	}
	
	/**
	 * Create empty adjacency list for V verticies
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void initializeAdj() {
		adj = new LinkedList[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new LinkedList<>();
		}
	}
	
	/**
	 * Abstract method to add an edge to the adjacency list.
	 * 
	 * @param u
	 * @param v
	 */
	public abstract void addEdge(int u, int v);
	
	/**
	 * Keep the adjacency list protected, and immutable, allowing
	 * this method to get the adjacent elemnents of u instead.
	 * 
	 * @param u
	 * @return reference to adj[u]
	 */
	public Iterable<Integer> adj(int u) {
		return adj[u];
	}
	
	/**
	 * @return the number of verticies
	 */
	public int V() {
		return this.V;
	}
	
	/**
	 * @return the number of edges
	 */
	public int E() {
		return this.E;
	}
	
	/**
	 * Create a String representation of the graph, in the same format that
	 * the graph reads in from instreams in. See <code>Graph(In in)</code> for this format.
	 * 
	 * @return String representation
	 */
	public String toString() {
		String ret = "";
		
		ret += V + "\n";
		ret += E + "\n";
		
		for (int u = 0; u < V; u++) {
			for (int v : adj[u]) {
				ret += u + " " + v + "\n";
			}
		}
		
		return ret;
	}
	
}
