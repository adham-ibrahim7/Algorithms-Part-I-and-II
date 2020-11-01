package graphs;

import java.io.IOException;
import java.util.Stack;

import edu.princeton.cs.algs4.In;

public class StackDFS {
	public static void main(String[] args) throws IOException {
		Graph g = new UndirectedGraph(new In());
		
		boolean[] vis = new boolean[g.V()];
		
		for (int start = 0; start < g.V(); start++) {
			if (vis[start]) continue;
			
			Stack<Integer> s = new Stack<>();
			s.add(start);
			
			while (!s.isEmpty()) {
				int u = s.pop();
				
				if (vis[u]) continue;
				vis[u] = true;
				
				for (int v : g.adj(u)) {
					s.add(v);
				}
				
				System.out.print(u + " ");
			}
			System.out.println();
		}
	}
}
