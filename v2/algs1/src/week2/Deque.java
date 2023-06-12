import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Deque.Node<Item> first;    // beginning of queue
    private Deque.Node<Item> last;     // end of queue
    private int n;               // number of elements on queue

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Deque.Node<Item> previous;
        private Deque.Node<Item> next;
    }


    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add can not be null");
        }
        Deque.Node<Item> oldFirst = first;
        first = new Deque.Node<Item>();
        first.item = item;
        first.previous = null;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.previous = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to add can not be null");
        }
        Deque.Node<Item> oldLast = last;
        last = new Deque.Node<Item>();
        last.item = item;
        last.previous = oldLast;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Deque.Node<Item> oldFirst = first;
        Item item = oldFirst.item;
        first = oldFirst.next;
        oldFirst.next = null;

        n--;
        if (isEmpty()) {
            last = null;   // to avoid loitering
        } else {
            first.previous = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Deque.Node<Item> oldLast = last;
        Item item = oldLast.item;
        last = oldLast.previous;
        oldLast.previous = null;

        n--;
        if (isEmpty()) {
            first = null;   // to avoid loitering
        } else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Deque.LinkedIterator(first);
    }

    // a linked-list iterator
    private class LinkedIterator implements Iterator<Item> {
        private Deque.Node<Item> current;

        public LinkedIterator(Deque.Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException(" underflow");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deq = new Deque<>();
        deq.addFirst("b");
        deq.addFirst("a");
        deq.addLast("c");
        deq.addLast("d");
        deq.addLast("e");
        deq.addLast("f");
        for (String d : deq) {
            StdOut.println(d); //  a b c d e f
        }
        StdOut.println("====");
        StdOut.println(deq.removeLast());  // f
        StdOut.println("====");
        deq.removeFirst();
        for (String d : deq) {
            StdOut.printf(d + " "); //   b c d e
        }
        StdOut.println("");
        StdOut.println("====");
        deq.removeFirst();
        deq.removeLast();
        for (String d : deq) {
            StdOut.println(d); //    c d
        }
        StdOut.println("====");
        deq.addFirst("a");
        deq.removeLast();
        for (String d : deq) {
            StdOut.println(d); //   a c
        }


    }

}
