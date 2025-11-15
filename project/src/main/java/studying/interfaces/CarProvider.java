package studying.interfaces;

import studying.domains.Car;
import studying.domains.Customer;

/**
 * Interface for car provider.
 *
 */
public interface CarProvider {

    Car takeCar(Customer customer);
}
