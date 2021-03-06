import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int N, T;
	private double[] simulations;
	private final double mean, stddev;
	private final double confidLo, confidHi;
	
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {    	
    	N = n;
    	T = trials;
    	
    	if (N <= 0 || T <= 0) {
    		throw new IllegalArgumentException("Illegal experiment: N: " + N + " T: " + T);
    	}
    	
    	simulations = new double[T];
    	for (int i = 0; i < T; i++) {
    		simulations[i] = simulate();
    	}
    	
    	mean = StdStats.mean(simulations);
    	stddev = StdStats.stddev(simulations);
    	double k = (1.96 * stddev) / Math.sqrt(T);
    	confidLo = mean - k;
    	confidHi = mean + k;
    }
    
    //return the double value for one simulation
    private double simulate() {
    	Percolation pc = new Percolation(N);
    	
    	while (!pc.percolates()) {
    		int row = StdRandom.uniform(1, N + 1);
    		int col = StdRandom.uniform(1, N + 1);
    		
    		pc.open(row, col);
    	}
    	
    	return pc.numberOfOpenSites() / (double) (N * N);
    }

    // sample mean of percolation threshold
    public double mean() {
    	return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return confidLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return confidHi;
    }

   // test client (see below)
   public static void main(String[] args) {
	   int N = 1000;//Integer.parseInt(args[0]);
	   int T = 100;//Integer.parseInt(args[1]);
	   
	   PercolationStats stats = new PercolationStats(N, T);
	   System.out.println("mean                    = " + stats.mean());
       System.out.println("stddev                  = " + stats.stddev());
       System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
   }
}