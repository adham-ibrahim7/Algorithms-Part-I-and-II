package testing;

public class Stopwatch {
	private long start;
	
	public Stopwatch() {
		reset();
	}
	
	public void reset() {
		start = System.currentTimeMillis();
	}
	
	public double elapsedTime() {
		long now = System.currentTimeMillis();
		return (now - start) / 1000.0;
	}
}
