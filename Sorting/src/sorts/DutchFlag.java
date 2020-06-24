package sorts;
public class DutchFlag extends Sorter{
	/*
	 * Will sort an array containing only 0's 1's and 2's
	 * 
	 * Let 0 represent red, 1 white, and 2 blue (hence "dutch flag")
	 * 
	 * Requirements:
	 * only call exch() up to N times, only call color up to N times (color() is just an access to the array)
	 * constant extra space
	 */
	
	public static int color(int[] a, int i) {return a[i];}
	
	public static void swap(int[] a, int i, int j) {int temp = a[i]; a[i] = a[j]; a[j] = temp;}
	
	public static void sort(int[] a) {
		int x = 0, y = a.length - 1;
		int i = x, j = y;
		while (i <= j) {
			int c1 = color(a, i), c2 = color(a, j);
			
			if (c1 == 0) swap(a, x++, i++);
			
			if (c2 == 0) swap(a, x++, j--);
			
			if (c1 == 2) swap(a, y--, i++);
			
			if (c2 == 2) swap(a, y--, j--);
			
			if (c1 == 1 && c2 == 1) {i++; j--;}
		}
	}
	
	public static void main(String[] args) {
		int[] nums = new int[] {0, 1, 0, 1, 2, 2, 1, 2, 0, 1, 2, 0, 2, 0, 0, 2};
		sort(nums);
		for (int n: nums) 
			System.out.println(n);
	}
}