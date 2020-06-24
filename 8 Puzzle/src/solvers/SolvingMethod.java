package solvers;

public interface SolvingMethod<T extends Searchable<T>> {
	
	public void next();
	
	public boolean found();
	
	public Iterable<T> constructPath();
	
	public int moves();
	
}