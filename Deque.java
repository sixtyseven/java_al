import edu.princeton.cs.algs4.StdOut;

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

        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if (oldFirst != null) {
            oldFirst.previous = first;
        }
        if (count == 1) {
            last = first;
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Item oldFirstItem = first.item;
        --count;
        if (count == 0) {
            first = null;
            last = null;
        } else if (count == 1) {
            last.previous = null;
            first = last;
        } else {
            first = first.next;
            first.previous = null;
        }

        return oldFirstItem;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;

        if (oldLast != null) {
            oldLast.next = last;
        }

        if (count == 1) {
            first = last;
        }

        count++;
    }


    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }

        Item oldLastItem = last.item;
        --count;
        if (count == 0) {
            first = null;
            last = null;
        } else if (count == 1) {
            first.next = null;
            last = first;
        } else {
            last = last.previous;
            last.next = null;
        }

        return oldLastItem;

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
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        boolean res = deque.isEmpty();
        StdOut.println(res);
        deque.removeFirst();
        boolean res2 = deque.isEmpty();
        StdOut.println(res2);
    }

}
