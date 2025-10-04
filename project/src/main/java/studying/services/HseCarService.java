package studying.services;

import java.util.List;
import org.springframework.stereotype.Component;
import studying.domains.Customer;
import studying.interfaces.CarProvider;
import studying.interfaces.CustomerProvider;


/**
 * Service for managing cars and customers.
 * Provides functionality for selling cars.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@Component
public class HseCarService {
    private final CarProvider carProvider;
    private final CustomerProvider customerProvider;

    public HseCarService(CarProvider carProvider, CustomerProvider customerProvider) {
        this.carProvider = carProvider;
        this.customerProvider = customerProvider;
    }

    /**
     * Sells a car to all customers who don't have a car.
     *
     */
    public void sellCars() {
        List<Customer> customers = customerProvider.getCustomers();
        customers.stream()
                .filter(customer -> customer.getCar() == null)
                .forEach(customer -> customer.setCar(carProvider.takeCar(customer)));
    }
}
