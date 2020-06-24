package puzzle;

import java.util.Iterator;

import edu.princeton.cs.algs4.Stopwatch;
//import solvers.AStar;
import solvers.PriorityBFS;

/**
 * The Solver class which takes an initial board as input and determines
 * if the board is solvable, and allows for iteration over the solution if
 * there is one
 * 
 * Adham Ibrahim
 * 15/6/20
 */

public class Solver {
	private boolean solvable = true;
	private int moves;
	private Iterable<Board> solution;
	
    /*
     * If a board is not solvable, a twin board will be.
     * So two states are looped over in lockstep, and if the twin has a solution,
     * the original is impossible
     * 
     */
	public Solver(Board initial) {
    	if (initial == null) throw new IllegalArgumentException("null argument to solver");
    	
    	PriorityBFS<Board> orig = new PriorityBFS<Board>(initial);
    	PriorityBFS<Board> twin = new PriorityBFS<Board>(initial.twin());
    	
    	while (true) {
    		orig.next();
    		
    		if (orig.found()) {
    			break;
    		}
    		
    		twin.next();
    		
    		if (twin.found()) {
    			solvable = false;
    			break;
    		}
    	}
    	
    	if (solvable) {
    		solution = orig.constructPath();
    		moves = orig.moves();
    	}
    }

    // is the initial board solvable?
    public boolean isSolvable() {
    	return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	return (solvable) ? moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	if (!solvable) return null;
    	
    	return new Iterable<Board>() {
    		public Iterator<Board> iterator() {
    			return solution.iterator();
    		}
    	};
    }

    //test client creating a random board and solving it
    public static void main(String[] args) {
    	Board initial = Board.random(3);
    	
    	System.out.println(initial.toString("INITIAL"));
    	
    	Stopwatch st = new Stopwatch();
    	Solver solver = new Solver(initial);
    	System.out.println("Time taken: " + st.elapsedTime());
    	
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
        	System.out.println("Minimum number of moves = " + solver.moves() + "\n");
            
        	int i = 0;
        	for (Board board : solver.solution())
            	System.out.println(board.toString(i++));
        }
    }

}