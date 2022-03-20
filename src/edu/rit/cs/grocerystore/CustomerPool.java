package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *This class is responsible for creating Customers and starting each one
 * on a separate thread.
 *
 * @author Ryan Nowak
 */
public class CustomerPool {
    private TSQueue<Cart> checkoutLine;
    private int numCustomers;
    private int avgLoad;
    private double avgDelay;
    private Random rand;
    private List<Cart> carts = new LinkedList<>();

    /**
     * Store all the parameter values for later use.
     * Also, initializes a Random number generator.
     * @param checkoutLine queue for carts
     * @param numCustomers how many customers to create
     * @param avgLoad mean number of groceries in each cart
     * @param avgDelay mean amount of time between customer arrivals at checkout
     */
    public CustomerPool(TSQueue<Cart> checkoutLine, int numCustomers,
                        int avgLoad, double avgDelay) {
        this.checkoutLine = checkoutLine;
        this.numCustomers = numCustomers;
        this.avgLoad = avgLoad;
        this.avgDelay = avgDelay;
        this.rand = new Random();
    }

    /**
     * Create the given number of customers, start them all up on separate
     * threads, and wait for them to finish. In the process, a Cart will be
     * created for each Customer. Each cart has a random number of groceries
     * placed in it (avgLoad from constructor is the mean). Each customer is
     * told to wait a random amount of time more than the previous customer.
     * The additional time to wait is a random value based on the avgDelay
     * from the constructor. Note that the time for each customer to wait is
     * measured from roughly the start of this method. Therefore, times
     * assigned to each successive customer go steadily upwards.
     *
     * @return list of cart objects made for all of the customers.
     */
    public List<Cart> simulateCustomers() {
        List<Customer> customers = new LinkedList<>();
        double delay = 0;
        for (int i = 0; i < this.numCustomers; i++) {
            delay += Utilities.sinePDFDelay(this.rand, avgDelay);
            Cart cart = new Cart( (int)Utilities.sinePDFDelay(this.rand, avgLoad));
            this.carts.add(cart);
            customers.add(new Customer(i+1, delay,
                    cart, this.checkoutLine));
        }

        for (Customer customer: customers) {
            customer.start();
        }

        for (Customer customer: customers) {
            try {
                customer.join();
            }
            catch (InterruptedException e) {}
        }

        return this.carts;
    }
}
