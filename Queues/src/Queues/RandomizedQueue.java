package Queues;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int n;
	private Item[] entries;
	
    // construct an empty randomized queue
	public RandomizedQueue() {
    	n = 0;
    	entries = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return n;
    }
    
	private void resize(int capacity) {
    	assert capacity >= n;
    	
    	Item[] copy = (Item[]) new Object[capacity];
    	for (int i = 0; i < n; i++) {
    		copy[i] = entries[i];
    	}
    	entries = copy;
    }

    // add the item
    public void enqueue(Item item) {
    	if (item == null) throw new IllegalArgumentException("Cannot enqueue null element");
    	
    	if (n == entries.length) {
    		resize(n * 2);
    	}
    	entries[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
    	if (isEmpty()) throw new NoSuchElementException("Cannot sample from empty queue");
    	
    	int p = StdRandom.uniform(0, n);
    	Item item = entries[p];
    	entries[p] = entries[n - 1];
    	entries[(n--) - 1] = null;
    	return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	if (isEmpty()) throw new NoSuchElementException("Cannot sample from empty queue");
    
    	return entries[StdRandom.uniform(0, n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    	resize(n);
    	StdRandom.shuffle(entries);
    	return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
    	private int c = 0;
    	
    	public boolean hasNext() {
    		return c < n;
    	}
    	
    	public Item next() {
    		if (!hasNext()) throw new NoSuchElementException("No more elements to iterate over");
    		
    		return entries[c++];
    	}
    	
    	public void remove() {
    		throw new UnsupportedOperationException("remove not supported in randomized queue iteration");
    	}
    }

    // unit testing (required)
    public static void main(String[] args) {
    	RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
    	for (int i = 0; i < 10; i++) 
    		rq.enqueue(i);
    	System.out.println("size: " + rq.size());
    	System.out.println("sample: " + rq.sample());
    	for (int i = 0; i < 10; i++)
    		System.out.println(rq.dequeue());
    	System.out.println("empty?: " + rq.isEmpty());
    }
}