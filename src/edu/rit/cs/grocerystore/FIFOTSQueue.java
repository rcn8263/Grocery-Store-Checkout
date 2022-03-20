package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;

/**
 * FIFOTSQueue is a queue that is built to be thread-safe.
 * Objects that have been in the queue the longest are the first to leave.
 *
 * @author Ryan Nowak
 */
public class FIFOTSQueue< E extends TimedObject > implements TSQueue< E > {
    private LinkedList<E> contents = new LinkedList<>();

    /**
     * Initialize the underlying data structure used for the queue.
     */
    public FIFOTSQueue() {
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
     * @return element at front of queue
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
        E value = contents.pop();
        value.exitQueue();
        return value;
    }
}
