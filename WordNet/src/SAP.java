import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

/**
 * This class extends the functionality of the {@link edu.princeton.cs.algs4.Digraph Digraph} class, 
 * allowing for efficient computation of the <em>lowest common ancestor</em> of two nodes as well as
 * the <em>shortest ancestral path</em> (SAP) between two nodes.
 * <p>
 * The SAP class also allows for the computation of the above as applied to sets of nodes.
 * These sets are labeled red and blue, for visual purposes. The shortest ancestral path 
 * between the red and blue sets is defined to be the shortest among every pair containing a
 * node from each, and the common ancestor is now not necessarily the lowest among all pairs,
 * but rather the root of the path that yields the aforementioned SAP.
 * <p>
 * The class determines all of the above in &Theta;(<em>E</em> + <em>V</em>) runtime and space.
 * 
 * @author Adham Ibrahim
 * @version 9/19/2020
 * @see edu.princeton.cs.algs4.Digraph
 */

public class SAP {
	
	//reference to the digraph on which we compute
	private Digraph G;
	
	/**
	 * Initializes SAP with a digraph (Not necessarily a DAG)
	 * @param G
	 */
	public SAP(Digraph G) {
		if (G == null) throw new IllegalArgumentException("Cannot construct SAP from null digraph");
		this.G = new Digraph(G);
	}
	
	private Iterable<Integer> toIterable(int v) {
		List<Integer> iterable = new ArrayList<>(1);
		iterable.add(v);
		return iterable;
	}
	
	public int length(int v, int w) {
		return length(toIterable(v), toIterable(w));
	}
	
	public int ancestor(int v, int w) {
		return ancestor(toIterable(v), toIterable(w));
	}
	
	private int bestLength;
	
	public int length(Iterable<Integer> redNodes, Iterable<Integer> blueNodes) {
		ancestor(redNodes, blueNodes);
		
		if (bestLength == INF)
			return -1;
		else
			return bestLength;
	}
	
	private static final int INF = Integer.MAX_VALUE;
	
	public int ancestor(Iterable<Integer> redNodes, Iterable<Integer> blueNodes) {
		BreadthFirstDirectedPaths redBFS = new BreadthFirstDirectedPaths(G, redNodes);
		BreadthFirstDirectedPaths blueBFS = new BreadthFirstDirectedPaths(G, blueNodes);
		
		int bestAncestor = -1;
		
		bestLength = INF;
		for (int currentAncestor = 0; currentAncestor < G.V(); currentAncestor++) {
			if (redBFS.hasPathTo(currentAncestor) && blueBFS.hasPathTo(currentAncestor)) {
				int currentLength = redBFS.distTo(currentAncestor) + blueBFS.distTo(currentAncestor);
				
				if (currentLength < bestLength) {
					bestLength = currentLength;
					bestAncestor = currentAncestor;
				}
			}
		}
		
		return bestAncestor;
	}
	
	/**
	 * Performs unit testing
	 * @param args
	 */
	public static void main(String[] args) {
		Digraph G = new Digraph(new In("assets/digraph25.txt"));
		SAP sap = new SAP(G);
		
		List<Integer> red = new ArrayList<>();
		red.add(6); red.add(17); red.add(16);
		List<Integer> blue = new ArrayList<>();
		blue.add(13); blue.add(23); blue.add(24);

		System.out.println(sap.ancestor(red, blue)); //3
		System.out.println(sap.length(red, blue)); //4
	}
	
}