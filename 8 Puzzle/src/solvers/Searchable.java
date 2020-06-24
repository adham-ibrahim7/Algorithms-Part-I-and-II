package solvers;

public interface Searchable<T> extends Comparable<T> {
	
	public int cost(T other);
	
	public int heuristic();
	
	public Iterable<T> neighbors();
	
	public boolean isGoal();
	
}