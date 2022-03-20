package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;

/**
 * PriorityTSQueue is a queue that is built to be thread-safe.
 * Objects leave the queue based on a certain priority. In this case,
 * elements that compare "less" come out first.
 *
 * @author Ryan Nowak
 */
public class PriorityTSQueue< E extends TimedObject & Comparable< E > >
        implements TSQueue< E > {
    private LinkedList<E> contents = new LinkedList<>();

    /**
     * Initialize the underlying data structure used for the queue.
     */
    public PriorityTSQueue() {
    }

    /**
     * Puts the value in the queue, and calls TimedObject.enterQueue() on the value.
     * This method is synchronized because its body is a critical region.
     * @param value the value to be enqueued
     * @return size of queue
     */
    @Override
    public synchronized int enqueue( E value ) {
        contents.add(value);
        value.enterQueue();
        notifyAll();
        return contents.size();
    }

    /**
     * Removes a value from the queue and calls TimedObject.exitQueue() on the value.
     * This method is expected to block (wait) if the queue is empty, rather than
     * throwing an exception or returning a null value. This method is synchronized
     * because its body is a critical region.
     * @return smallest element from queue
     */
    @Override
    public synchronized E dequeue() {
        while (contents.isEmpty()) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        notifyAll();
        E value = contents.remove(fewest());
        value.exitQueue();
        return value;
    }

    /**
     * Compares the elements in the queue using compareTo and returns the index
     * of the element that is less than all other elements
     * @return index of element in queue
     */
    private synchronized int fewest() {
        int index = 0;
        if (contents.size() > 1) {
            for (int i = 0; i < contents.size() - 1; i++) {
                if (contents.get(i).compareTo(contents.get(index)) < 0) {
                    index = i;
                }
            }
        }
        return index;
    }
}
