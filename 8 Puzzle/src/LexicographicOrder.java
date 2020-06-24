
import java.util.Iterator;

public class LexicographicOrder implements Iterable<int[]> {
	private int N;
	private int[] P;
	private boolean finished;
	
	public LexicographicOrder(int N) {
		this.N = N;
		this.P = new int[N];
		
		for (int i = 0; i < N; i++) {
			P[i] = i;
		}
		
		finished = false;
	}
	
	private void swap(int i, int j) {
		int temp = P[i];
		P[i] = P[j];
		P[j] = temp;
	}
	
	private void reverse(int l) {
		int size = N - l;
		for (int i = 0; i < size / 2; i++) {
			swap(l + i, N - 1 - i);
		}
	}
	
	public Iterator<int[]> iterator() {
		return new Iterator<int[]>() {
			public boolean hasNext() {
				return !finished;
			}

			public int[] next() {
				int x = -1;
				for (int i = 0; i < N - 1; i++) {
					if (P[i] < P[i+1]) {
						x = i;
					}
				}
				
				if (x == -1) {
					finished = false;
					return null;
				}
				
				int y = -1;
				for (int i = 0; i < N; i++) {
					if (P[x] < P[i]) {
						y = i;
					}
				}
				
				swap(x, y);
				
				reverse(x + 1);
				
				return P;
			}
			
		};
	}
}