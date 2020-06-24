import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int N;
	private boolean[][] grid;
	
	private WeightedQuickUnionUF uf;
	
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Cannot construct grid of size (" + n + ")");
		}
		
		N = n;
		
		grid = new boolean[N][N];
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				grid[row][col] = false;
			}
		}
		
		uf = new WeightedQuickUnionUF(N * N + 2);
	}
	
	private int index(int row, int col) {
		return row * N + col;
	}
	
	private boolean valid(int n) {
		return n >= 0 && n < N;
	}
	
	private boolean valid(int row, int col) {
		return valid(row) && valid(col);
	}
	
	public void open(int row, int col) {
		row -= 1;
		col -= 1;
		
		if (!valid(row, col)) {
			throw new IllegalArgumentException("Cannot open cell with coordinates (r: " + (row + 1) + "), (c: " + (col + 1) + ")");
		}
		
		if (isOpen(row, col)) return;
		
		grid[row][col] = true;
		
		
	}
	
	public boolean isOpen(int row, int col) {
		return false;
	}
	
	public boolean isFull(int row, int col) {
		return false;
	}
	
	public int numberOfOpenSites() {
		return 0;
	}
	
	public boolean percolates() {
		return false;
	}
	
	public static void main(String[] args) {
		
	}
}