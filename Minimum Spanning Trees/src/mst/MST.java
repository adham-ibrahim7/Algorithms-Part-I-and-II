package mst;

import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Edge;

/**
 * Generic abstract MST Solver
 * 
 * <p>
 * The MST (minimum spanning tree) of a graph <em>G</em> is defined as any subgraph
 * of <em>G</em> that is both a tree, and has minimum total spanning weight, i.e.,
 * the sum of the weights of all of the edges is minimum. A graph may contain multiple
 * minimum spanning trees, in which case it is unimportant to pick any specific one.
 * 
 * @author Adham Ibrahim
 * @version 9/25/2020
 */

public abstract class MST {
	
	//a deep copy of the graph G, on which we compute
	protected final EdgeWeightedGraph G;
	
	public MST(EdgeWeightedGraph G) {
		this.G = new EdgeWeightedGraph(G);
	}
	
	/**
	 * Return the edges in the minimum spanning tree, in no particular order.
	 * @return the edges in the minimum spanning tree
	 */
	public abstract Iterable<Edge> minSpanningTree();

	public double minSpanningWeight() {
		double totalSpanningWeight = 0.0;
		
		for (Edge e : minSpanningTree()) {
			totalSpanningWeight += e.weight();
		}
		
		return totalSpanningWeight;
	}
	
}
