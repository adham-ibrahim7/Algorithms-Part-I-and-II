import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int N;
	private boolean[] grid;
	
	private WeightedQuickUnionUF uf;
	private int VIRTUAL_TOP, VIRTUAL_BOTTOM;
	private int nOpenSites;
	
	//creates grid of size n * n
	public Percolation(int _N) {
		if (_N <= 0) {
			throw new IllegalArgumentException("Cannot create grid of size: " + _N);
		}
		
		N = _N;
		grid = new boolean[N * N + 2];
		uf = new WeightedQuickUnionUF(N * N + 2);
		VIRTUAL_TOP = 0;
		VIRTUAL_BOTTOM = N * N + 1;
		nOpenSites = 0;
	}
	
	//are the row and col values within the correct range?
	private boolean valid(int row, int col) {
		return row >= 1 && row <= N && col >= 1 && col <= N;
	}
	
	//converts 2d coordinate to 1d (for grid access and uf access)
	//throws illegal argument if not valid coordinates
	private int index(int row, int col) {
		if (!valid(row, col)) {
			throw new IllegalArgumentException("Illegal coordinates: (r: " + row + ", c: " + col + ")");
		}
		
		return (row - 1) * N + col;
	}
	
	// opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	if (isOpen(row, col)) {
    		return;
    	}
    	
    	nOpenSites++;
    	
    	int p = index(row, col);
    	grid[p] = true;
    	
    	int[][] coords = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}};
    	for (int[] coord: coords) {
    		int r = row + coord[0], c = col + coord[1];
    		if (valid(r, c)) {
    			int q = index(r, c);
    			if (grid[q]) {
    				uf.union(p, q);
    			}
    		}
    	}
    	
    	if (row == 1) {
    		uf.union(VIRTUAL_TOP, p);
    	} else if (row == N) {
    		uf.union(VIRTUAL_BOTTOM, p);
    	}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	return grid[index(row, col)];
    }
    
    //is the site of index p full?
    private boolean isFull(int p) {
    	return uf.find(p) == uf.find(VIRTUAL_TOP);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	return isFull(index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return nOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
    	return isFull(VIRTUAL_BOTTOM);
    }

    // test client (optional)
    public static void main(String[] args) {
    	
    }
}