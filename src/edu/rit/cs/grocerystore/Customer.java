package edu.rit.cs.grocerystore;

/**
 *In this simulation, a customer's only job is to wait for the specified
 * time, then enqueue its shopping cart in the checkout line queue. Every
 * customer has an integer ID. They are automatically numbered consecutively
 * starting at 1.
 *
 * @author Ryan Nowak
 */
public class Customer extends Thread {
    private int id;
    private double delay;
    private Cart cart;
    private TSQueue<Cart> queue;

    /**
     * Create a Customer object, storing all parameters provided and
     * assigning the next consecutive integer as this object's ID. First
     * Customer is #1.
     * @param id integer representing id of customer
     * @param delay how long to wait before enqueuing at checkout line
     * @param cart cart of groceries
     * @param queue the checkout line
     */
    public Customer (int id, double delay, Cart cart, TSQueue<Cart> queue) {
        this.id = id;
        this.delay = delay;
        this.cart = cart;
        this.queue = queue;

    }

    /**
     * This method should be executed by a thread uniquely assigned to this
     * Customer. This is what the method does.
     * 1. Sleep for the given delay time.
     * 2. Put the given Cart in the checkout queue.
     * 3. Print a message announcing the above has been done.
     *
     * The format of the message is:
     * Customer id with cart has entered the line, with N customers in front.
     */
    @Override
    public void run() {
        try {
            this.sleep( (long) delay);
        }
        catch (InterruptedException e) {}

        int customersInFront = this.queue.enqueue(this.cart) - 1;
        Utilities.println("Customer " + this.id + " with " + this.cart.toString() +
                " has entered the line, with " + customersInFront +
                " customers in front.");
    }
}
