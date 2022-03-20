package edu.rit.cs.grocerystore;

/**
 *In the simulation, the person who checks out customers' groceries.
 * In terms of Producer/Consumer architectures, this is the consumer.
 * In terms of queuing theory, this is the service.
 *
 * @author Ryan Nowak
 */
public class Clerk extends Thread {
    private TSQueue<Cart> checkoutLine;

    /**
     * Create a clerk and connect it to its checkout line.
     * @param checkoutLine TSQueue to connect the clerk to
     */
    public Clerk(TSQueue<Cart> checkoutLine) {
        this.checkoutLine = checkoutLine;
    }

    /**
     * This method, running on a separate thread, continuously removes
     * carts from the checkout line and sleeps for a time period to simulate
     * the checkout process. When the sleep time for a cart is complete, it
     * then "tells" the cart that it has finished servicing the cart so that
     * times can be saved. See TimedObject.servicingDone(). The sleep time is
     * the number of items in the cart, multiplied by
     * Utilities.TIME_PER_CART_ITEM. The method exits when it removes the
     * special cart Utilities.NO_MORE_CARTS, which is not included in any
     * timing measurements.
     */
    @Override
    public void run() {
        Cart cart = checkoutLine.dequeue();
        while (!cart.equals(Utilities.NO_MORE_CARTS)) {
            Utilities.println("Clerk got " + cart.toString());
            try {
                this.sleep(Utilities.TIME_PER_CART_ITEM*cart.getCartSize());
            }
            catch (InterruptedException e) {}
            cart.servicingDone();
            cart = checkoutLine.dequeue();
        }
    }
}
