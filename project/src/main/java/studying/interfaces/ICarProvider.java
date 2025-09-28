package studying.interfaces;

import studying.domains.Car;
import studying.domains.Customer;

public interface ICarProvider {

    Car takeCar(Customer customer);
}
