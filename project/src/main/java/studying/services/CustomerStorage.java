package studying.services;

import studying.domains.Car;
import studying.domains.Customer;
import studying.interfaces.ICustomerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing customers
 * Provides functionality for adding customers.
 * Implements the {@link ICustomerProvider} interface for providing customers.
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
public class CustomerStorage implements ICustomerProvider {
    private final List<Customer> customers = new ArrayList<>();

    /**
     * Method to get customer`s list
     * @return a list of customers
     * @see Car#isCompatible(Customer)
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Method to add customer to the storage
     * @param customer customer to add
     * @see Car#isCompatible(Customer)
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
