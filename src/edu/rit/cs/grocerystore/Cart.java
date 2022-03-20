package edu.rit.cs.grocerystore;


/**
 * An object that holds a number of groceries. It is "created" in the
 * CustomerPool, enqueued in a checkout line (see TSQueue) and retrieved
 * by the Clerk. Timing functionality is inherited from TimedObject.
 *
 * @author Ryan Nowak
 */
public class Cart extends TimedObject implements Comparable< Cart > {
    private int numItems;

    /**
     * Construct the Sentinel Cart that is enqueued to indicate
     * that it is the end of the simulation.
     */
    public Cart() {
    }

    /**
     * The normal customer use to create grocery carts in the simulation.
     * @param numItems
     */
    public Cart( int numItems ) {
        this.numItems = numItems;
    }

    /**
     * @return integer of how much load is in the cart
     */
    public int getCartSize() {
        return this.numItems;
    }

    /**
     * Compares two carts, for use in priority queue
     * @param other other cart object to be compared to
     * @return negative integer if this cart's load is less than other cart's
     * load, positive if this cart's load is more, and 0 if their load is equal
     */
    @Override
    public int compareTo( Cart other ) {
        return this.numItems - other.numItems;
    }

    /**
     * @return String representing cart object
     */
    @Override
    public String toString() {
        return "Cart(" + this.numItems + ")";
    }

}
