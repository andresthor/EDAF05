/**
 * 
 */
package spanningUSA;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of bag or multiset data structure. 
 *
 */
public class MultiSet<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of MultiSet
    private int N;               // number of elements in MultiSet

    // helper class for node representation i.e. linked list
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty MultiSet.
     */
    public MultiSet() {
        first = null;
        N = 0;
    }

    /**
     * Returns true if this MultiSet is empty.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this MultiSet.
     */
    public int size() {
        return N;
    }

    /**
     * Adds the item to this MultiSet.
     */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

	/* Returns an iterator that iterates over the items in arbitrary order
	 */
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}
	
	// an iterator, does not implement remove()
	private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}
