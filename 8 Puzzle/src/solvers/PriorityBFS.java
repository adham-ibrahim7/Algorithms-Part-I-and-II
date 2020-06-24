package solvers;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityBFS<T extends Searchable<T>> implements SolvingMethod<T> {
	
	private State<T> current;
	private PriorityQueue<State<T>> pq;
	private HashSet<T> visited;
	private boolean found;
	
	public PriorityBFS(T initial) {
		current = new State<T>(initial, null, 0);
		visited = new HashSet<T>();
		visited.add(initial);
		pq = new PriorityQueue<State<T>>();
		found = false;
	}
	
	public boolean found() {
		return found;
	}
	
	public void next() {
		if (current.item.isGoal()) {
			found = true;
			return;
		}
		
		for (T neighbor : current.item.neighbors()) {
			if (!visited.contains(neighbor)) {
				pq.add(new State<T>(neighbor, current, current.depth + 1));
				visited.add(neighbor);
			}
		}

		current = pq.remove();
	}
	
	public Iterable<T> constructPath() {
		List<T> solution = new LinkedList<T>();
		State<T> t = current;
		
    	while (t != null) {
    		solution.add(0, t.item);
    		t = t.previous;
    	}
    	
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return solution.iterator();
			}
		};
	}
	
	public int moves() {
		return current.depth;
	}
}
