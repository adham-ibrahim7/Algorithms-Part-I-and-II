package sorts;

public abstract class Sorter {
	public static boolean less(Comparable a, Comparable b) {
		return (a.compareTo(b) < 0);
	}
	
	public static void exch(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}