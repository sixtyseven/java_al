import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int count = 0;

    private class Node {
        Node next;
        Node previous;
        Item item;
    }


    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }


    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldFirst = first;
        oldFirst.previous = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        last = last.previous;
        last.item = item;
        last.next = null;

        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first != null) {
            Item oldfirstItem = first.item;
            first = first.next;
            first.previous = null;
            count--;
            return oldfirstItem;
        }

        throw new NoSuchElementException();
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last != null) {
            Item oldLastItem = last.item;
            last = last.previous;
            last.next = null;
            count--;
            return oldLastItem;
        }
        throw new NoSuchElementException();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            /* not supported */
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
