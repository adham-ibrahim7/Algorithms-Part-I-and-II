package solvers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class AStar<T extends Searchable<T>> implements SolvingMethod<T> {
	
	private PriorityQueue<T> openSet;
	private HashMap<T, T> cameFrom;
	private HashMap<T, Integer> g, f;
	private boolean found;
	private T current;
	private List<T> path;
	
	public int h(T n) {
		return n.heuristic();
	}
	
	public AStar(T initial) {
		//the nodes being explore currently
		openSet = new PriorityQueue<T>(new Comparator<T>() {
			public int compare(T a, T b) {
				return f.getOrDefault(a, Integer.MAX_VALUE) - f.getOrDefault(b, Integer.MAX_VALUE);
			}
		});
		openSet.add(initial);
		
		//cameFrom maps node to the node it came from
		cameFrom = new HashMap<T, T>();
		
		//g score map : g[n] = cheapest path from start to n known until now
		g = new HashMap<T, Integer>();
		g.put(initial, 0);
		
		//f score map : f[n] = g[n] + h[n], h being the heuristic
		//represents the best guess to a path from start to goal if it goes through n
		f = new HashMap<T, Integer>();
		f.put(initial, h(initial));
		
		//reached goal yet?
		found = false;
	}
	
	public void next() {
		if (openSet.size() > 0) {
			current = openSet.remove();
			
			if (current.isGoal()) {
				found = true;
				
				path = new LinkedList<T>();
				do  {
					path.add(0, current);
					current = cameFrom.get(current);
				} while (cameFrom.get(current) != null);
				
				return;
			}
			
			for (T neighbor : current.neighbors()) {
				int tentativeG = g.get(current) + current.cost(neighbor);
				
				if (tentativeG < g.get(current)) {
					cameFrom.put(neighbor, current);
					
					g.put(neighbor, tentativeG);
					f.put(neighbor, g.get(neighbor) + h(neighbor));
					
					if (!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}
			
			
		} else {
			//throw new RuntimeException("Called next when failure/success already found");
		}
	}

	public boolean found() {
		return found;
	}

	public Iterable<T> constructPath() {
		return path;
	}

	public int moves() {
		return path.size();
	}
}
