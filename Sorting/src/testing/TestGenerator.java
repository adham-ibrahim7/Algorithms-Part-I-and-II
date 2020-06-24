package testing;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TestGenerator {
	public static void create(int N) throws FileNotFoundException {
		System.setOut(new PrintStream("test" + N + ".txt"));
		for (int i = 0; i < N; i++) {
			System.out.println(Math.random());
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		create(1000000);
	}
}