package puzzle;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import Queues.RandomizedQueue;

import solvers.*;

/**
 * The board class to encapsulate the operations necessary to manipulate 
 * and solve 8-puzzle boards.
 * 
 * Optimizations from COS226:
 *  - store tiles as 1d array, for hashing, and quicker copying
 * 
 * Adham Ibrahim
 * 15/6/2020
 */

public class Board implements Searchable<Board> {
	//size of board
	private final int N, SIZE;
	//the current configuration as a 2d array
	private int[] tiles;
	
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[] input, int N) {
    	this.N = N;
    	this.SIZE = N * N;
    	
    	if (input.length != SIZE) throw new IllegalArgumentException("Incorrectly sized board");
    	
    	//copy elements so a copy can be made by calling new Board(this.tiles)
    	tiles = Arrays.copyOf(input, SIZE);
    }
    
    public static Board random(int N) {
    	int size = N * N;
    	RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
    	for (int i = 0; i < N * N; i++) rq.enqueue(i);
    	
    	int[] tiles = new int[size];
    	for (int i = 0; i < size; i++)
    		tiles[i] = rq.dequeue();
    	
    	return new Board(tiles, N);
    }
                                           
    // string representation of this board
    public String toString(String header) {
    	final int MAXWIDTH = (N <= 3) ? 1 : (N <= 10) ? 2 : 3;
    	String str = header;
    	
    	for (int i = 0; i < SIZE; i++) {
    		if (i % N == 0) str += "\n";
    		str += " " + String.format("%" + MAXWIDTH + "d", tiles[i]);
    	}
    	
    	return str + "\n";
    }
    
    //string representations with headers (numbering the boards by order
    //in a solution for example)
    public String toString(int header) {
    	return toString(Integer.toString(header));
    }
    
    public String toString() {
    	return toString(N);
    }

    // board dimension n
    public int dimension() {
    	return N;
    }

    // number of tiles out of place
    public int hamming() {
    	int count = 0;
    	for (int i = 0; i < SIZE; i++)
    		if (tiles[i] != 0 && tiles[i] != i + 1) count++;
    	return count;
    }
    
    //x-coordinate of index in 2d (or the j coordinate)
    private int x(int i) {
    	return i % N;
    }
    
    //y-coordinate of index in 2d (or the i coordinate)
    private int y(int i) {
    	return i / N;
    }
    
    //index of x, y coordinate (or i, j coordinate)
    private int index(int x, int y) {
    	if (!valid(x, N) || !valid(y, N)) return -1;
    	return y * N + x;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int total = 0;
    	for (int i = 0; i < SIZE; i++) {
    		if (tiles[i] == 0) continue;
    		
    		total += Math.abs(x(i) - x(tiles[i] - 1)) + Math.abs(y(i) - y(tiles[i] - 1));
    	}
    	return total;
    }
    
    
    
    private boolean valid(int i, int max) {
    	return i >= 0 && i < max;
    }
    
    //return copy of board with elements swapped
    private Board swap(int from, int to) {
    	Board copy = new Board(tiles, N);
    	
    	int temp = copy.tiles[from];
    	copy.tiles[from] = copy.tiles[to];
    	copy.tiles[to] = temp;
    	
    	return copy;
    }
    
    private int getEmptyPosition() {
    	int empty = -1;
    	for (int i = 0; i < SIZE; i++) {
    		if (tiles[i] == 0) {
    			empty = i;
    			break;
    		}
    	}
    	
    	if (empty == -1) throw new RuntimeException("No empty position in board?");
    	
    	return empty;
    }
    
    //comparing two boards distance from goal
    public int compareTo(Board that) {
    	return this.heuristic() - that.heuristic();
    }
    
    //the heuristic function will be the manhattan distance
    public int heuristic() {
    	return manhattan();
    }

    // is this board the goal board?
    public boolean isGoal() {
    	//hamming check is quicker
    	return hamming() == 0;
    }
    
    //cost to reach neighbor is just one
    public int cost(Board neighbor) {
    	return 1;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
    	List<Board> neighbors = new ArrayList<Board>();
    	
    	int empty = getEmptyPosition();
    	
    	int x = x(empty), y = y(empty);
    	
    	int[] possibleNeighbors = new int[] {index(x-1,y), index(x+1,y), index(x,y-1), index(x,y+1)};
    	
    	for (int possibleNeighbor : possibleNeighbors) {
    		if (valid(possibleNeighbor, SIZE)) {
    			neighbors.add(swap(empty, possibleNeighbor));
    		}
    	}
    	
    	return new Iterable<Board>() {
    		public Iterator<Board> iterator() {
    			return neighbors.iterator();
    		}
    	};
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	int empty = getEmptyPosition();
    	
    	int first = 0;
    	if (empty == 0) first = 1;
    	
    	int second = SIZE - 1;
    	if (empty == SIZE - 1) second = SIZE - 2;
    	
    	return swap(first, second);
    }
    
    // does this board equal y?
    public boolean equals(Object o) {
    	if (this == o) return true;
    	if (!(o instanceof Board)) return false;
    	
    	Board b = (Board) o;
    	
    	if (this.dimension() != b.dimension()) return false;
    	
    	for (int i = 0; i < SIZE; i++) {
    		if (tiles[i] != b.tiles[i]) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    //hashcode based off tiles array
    public int hashCode() {
    	return Arrays.hashCode(tiles);
    }
    
    public static void main(String[] args) {
    	int[] t = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    	Board a = new Board(t, 3);
    	Board b = new Board(t, 3);
    	System.out.println(a.equals(b));
    	HashSet<Board> hs = new HashSet<Board>();
    	hs.add(a);
    	hs.add(b);
    	System.out.println(hs.size());
    }
}