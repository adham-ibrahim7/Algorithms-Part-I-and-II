import puzzle.Board;
import puzzle.Solver;

/*
 * The aim of this class is to run many random 8 puzzles,
 * to determine:
 *  - The probability a random puzzle is solvable
 *  - The average number of moves required to solve a solvable puzzle
 */

public class PuzzleTesting {

	public static void main(String[] args) {
		int count = 0;
		int moves = 0;
		int N = 3;
		
		LexicographicOrder lex = new LexicographicOrder(N * N);
		int total = 0;
		for (int[] P : lex) {
			Solver s = new Solver(new Board(P, N));
			if (s.isSolvable()) {
				count++;
				moves += s.moves();
			}
			total++;
			if (total > 300000) break;
		}
		
		System.out.println((double) count / total);
		System.out.println((double) moves / count);
	}

}