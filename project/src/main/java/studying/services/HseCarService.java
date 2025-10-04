package studying.services;

import studying.domains.Customer;
import studying.interfaces.ICarProvider;
import studying.interfaces.ICustomerProvider;

import java.util.List;

/**
 * Service for managing cars and customers.
 * Provides functionality for selling cars.
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
public class HseCarService {
    private final ICarProvider carProvider;
    private final ICustomerProvider customerProvider;

    public HseCarService(ICarProvider carProvider, ICustomerProvider customerProvider) {
        this.carProvider = carProvider;
        this.customerProvider = customerProvider;
    }

    /**
     * Sells a car to all customers who don't have a car
     *
     */
    public void sellCars() {
        List<Customer> customers = customerProvider.getCustomers();
        customers.stream()
                .filter(customer -> customer.getCar() == null)
                .forEach(customer -> customer.setCar(carProvider.takeCar(customer)));
    }
}
