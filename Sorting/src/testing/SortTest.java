package testing;
import java.io.File;
import java.io.FileNotFoundException;
//import java.util.Arrays;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdOut;

import sorts.*;

public class SortTest {
	public static double test(int N) throws FileNotFoundException {
		Double[] nums = new Double[N];
		
		Scanner sc = new Scanner(new File("./assets/test" + N + ".txt"));
		for (int i = 0; i < N; i++) {
			nums[i] = sc.nextDouble();
		}
		
		Stopwatch st = new Stopwatch();
		Selection.sort(nums);
		return st.elapsedTime();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		for (int N = 10; N <= 1000000; N *= 10) {
			StdOut.println(test(N));
		}
	}
}