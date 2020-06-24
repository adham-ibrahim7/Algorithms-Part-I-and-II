package solvers;

public class State<T extends Searchable<T>> implements Comparable<State<T>> {
	
	public T item;
	public State<T> previous;
	public int depth;
		
	public State(T item, State<T> previous, int depth) {
		this.item = item;
		this.previous = previous;
		this.depth = depth;
	}
	
	public int compareTo(State<T> that) {
		return this.depth - that.depth + this.item.compareTo(that.item);
	}
}