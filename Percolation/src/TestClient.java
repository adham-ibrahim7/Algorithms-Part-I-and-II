public class TestClient {
	public static void main(String[] args) {
		Percolation percolation = new Percolation(10);
		
		for (int i = 1; i <= 10; i++) percolation.open(i, i);
		
		System.out.println(percolation.percolates());
	}
}