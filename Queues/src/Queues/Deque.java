package Queues;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node<Item> front, back;
	private int size;
	
    // construct an empty deque
    public Deque() {
    	front = back = null;
    	size = 0;
    }
    
    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
        
        private Node(Item _item) {
        	item = _item;
        	next = null;
        	prev = null;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
    	return front == null;
    }

    // return the number of items on the deque
    public int size() {
    	return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if (item == null) throw new IllegalArgumentException("Cannot add null element to deque");
    	
    	size++;
    	Node<Item> oldFront = front;
    	Node<Item> newFront = new Node<Item>(item);
    	newFront.next = front;
    	front = newFront;
    	if (back == null) back = oldFront;
    	if (front.next != null) front.next.prev = front;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if (item == null) throw new IllegalArgumentException("Cannot add null element to deque");
    	
    	if (back == null) {
    		if (front == null) {
    			addFirst(item);
    		} else {
    			size++;
    			back = new Node<Item>(item);
    			front.next = back;
    			back.prev = front;
    		}
    		return;
    	}
    	
    	size++;
    	Node<Item> oldBack = back;
    	Node<Item> newBack = new Node<Item>(item);
    	back.next = newBack;
		back = newBack;
    	back.prev = oldBack;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if (isEmpty()) throw new java.util.NoSuchElementException("Cannot remove from empty deque");
    	
    	Item oldFrontItem = front.item;
    	
    	size--;
    	if (size == 0) {
    		front = null;
    		back = null;
    		return oldFrontItem;
    	}
    	
    	front = front.next;
    	//if (front != null && front.next == back) back = null;
    	front.prev = null;
    	return oldFrontItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if (isEmpty()) throw new java.util.NoSuchElementException("Cannot remove from empty deque");
    	
    	if (size == 1) {
    		return removeFirst();
    	}
    	
    	size--;
    	Item oldBackItem = back.item;
        back = (back.prev == front) ? null : back.prev;
        if (back != null) {
        	back.next = null;
        } else {
        	front.next = null;
        }
    	return oldBackItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
    	return new DequeIterator(front);
    }
    
    private class DequeIterator implements Iterator<Item> {
    	private Node<Item> first;
    	public DequeIterator(Node<Item> _first) {
    		first = _first;
    	}
    	
    	public boolean hasNext() {
    		return first != null;
    	}
 
    	public Item next() {
    		if (!hasNext()) throw new java.util.NoSuchElementException("No more elements to get");
    		
    		Item oldFirst = first.item;
    		first = first.next;
    		return oldFirst;
    	}
    	
    	public void remove() {
    		throw new UnsupportedOperationException("remove operation unsupported");
    	}
    }

    // unit testing (required)
    public static void main(String[] args) {
    	Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(3);
        dq.addLast(4);
        dq.addFirst(2);
        dq.addLast(5);
        dq.addFirst(1);
        dq.addLast(6);
        dq.removeFirst();
        dq.removeLast();
        dq.removeLast();
        dq.removeFirst();
        
        for (int s : dq) {
            System.out.print(s + " ");
        }
        
        System.out.println("\nsize: " + dq.size());
        System.out.println("isEmpty: " + dq.isEmpty());
    }
}