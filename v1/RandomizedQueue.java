import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int count = 0;

    private Item[] itemArr;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // create an array of Object (length = 1) and casting
        itemArr = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (count == itemArr.length) {
            resize(2 * itemArr.length);
        }

        itemArr[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(count);
        Item itemToRemote = itemArr[idx];
        itemArr[idx] = itemArr[--count];
        itemArr[count] = null;
        if (count == itemArr.length / 4.0) {
            resize(itemArr.length / 2);
        }
        return itemToRemote;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int idx = StdRandom.uniform(count);
        return itemArr[idx];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        if (count >= 0) System.arraycopy(itemArr, 0, copy, 0, count);
        itemArr = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int numOfItemsLeft;
        Item[] arr;

        // construct an empty randomized queue
        public ListIterator() {
            arr = (Item[]) new Object[count];
            System.arraycopy(itemArr, 0, arr, 0, count);

            StdRandom.shuffle(arr);
            numOfItemsLeft = count;
        }

        public boolean hasNext() {
            return numOfItemsLeft != 0;
        }

        public void remove() {
            /* not supported */
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (numOfItemsLeft == 0) {
                throw new NoSuchElementException();
            }
            int idx = --numOfItemsLeft;
            return arr[idx];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();


        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        Iterator<Integer> iterator1 = queue.iterator();
//        Iterator<Integer> iterator2 = queue.iterator();
        StdOut.println("[debug] ");
        while (iterator1.hasNext()) {
            StdOut.print("q: ");
            int q = iterator1.next();
            StdOut.println(q);
            StdOut.print("q2: ");
//            StdOut.println(q2);
        }


    }

}
