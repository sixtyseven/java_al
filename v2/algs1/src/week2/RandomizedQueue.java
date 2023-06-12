import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] itemArr;
    private int count;               // number of elements on queue
    private final int MIN_SIZE = 1;

    // construct an empty randomized queue
    public RandomizedQueue() {
        itemArr = (Item[]) new Object[MIN_SIZE];
        count = 0;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = itemArr[i];
        }
        itemArr = copy;
    }

    private void swap(Item[] arr, int idx1, int idx2) {
        Item temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
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
            throw new IllegalArgumentException("Item to add can not be null");
        }
        if (count >= MIN_SIZE && count == itemArr.length) {
            resize(2 * itemArr.length);
        }

        itemArr[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException(" underflow");
        }
        if (count >= MIN_SIZE * 2 && count * 4 == itemArr.length) {
            resize(itemArr.length / 2);
        }

        int randomIdx = StdRandom.uniformInt(0, count);

        swap(itemArr, randomIdx, count - 1);

        Item item = itemArr[count - 1];

        itemArr[count - 1] = null;
        count--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException(" underflow");
        }
        int randomIdx = StdRandom.uniformInt(0, count);
        return itemArr[randomIdx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    // a linked-list iterator
    private class LinkedIterator implements Iterator<Item> {

        private Item[] copy;
        private int copyCount;

        public LinkedIterator() {
            copy = (Item[]) new Object[count];
            for (int i = 0; i < count; i++) {
                copy[i] = itemArr[i];
            }
            copyCount = count;
        }

        public boolean hasNext() {
            return copyCount != 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException(" underflow");
            }
            int randomIdx = StdRandom.uniformInt(0, copyCount);
            swap(copy, randomIdx, copyCount - 1);

            Item item = copy[copyCount - 1];
            copy[copyCount - 1] = null;
            copyCount--;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported");
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> que = new RandomizedQueue<>();
        que.enqueue("a");
        que.enqueue("b");
        que.enqueue("c");
        que.enqueue("d");
        for (String item : que) {
            StdOut.printf(item + " "); //   a b c d
        }
        StdOut.println("");
        StdOut.println("======");
        que.enqueue("e");

        StdOut.print(que.dequeue());
        StdOut.println("");
        StdOut.println("====");
        que.enqueue("f");
        for (String item : que) {
            StdOut.printf(item + " "); //   a b c d
        }

    }

}
