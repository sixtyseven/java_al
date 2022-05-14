import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int count = 0;

    private Item[] itemArr;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // casting
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
            throw new IllegalArgumentException();
        }
        int idx = StdRandom.uniform(count);
        Item itemToRemote = itemArr[idx];
        itemArr[idx] = itemArr[count - 1];
        itemArr[count--] = null;
        if (count == itemArr.length / 4) {
            resize(itemArr.length / 2);
        }
        return itemToRemote;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
        int idx = StdRandom.uniform(count);
        return itemArr[idx];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = itemArr[i];
        }
        itemArr = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int numOfItemsLeft = count;

        // construct an empty randomized queue
        public ListIterator() {
            // casting
            StdRandom.shuffle(itemArr);
        }

        public boolean hasNext() {
            return numOfItemsLeft != 0;
        }

        public void remove() {
            /* not supported */
            throw new UnsupportedOperationException();
        }

        public Item next() {
            return itemArr[--numOfItemsLeft];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
