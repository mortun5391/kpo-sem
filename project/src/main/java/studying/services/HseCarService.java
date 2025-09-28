package studying.services;

import studying.domains.Customer;
import studying.interfaces.ICarFactory;
import studying.interfaces.ICarProvider;
import studying.interfaces.ICustomerProvider;

import java.util.List;

public class HseCarService {
    private final ICarProvider carProvider;
    private final ICustomerProvider customerProvider;

    public HseCarService(ICarProvider carProvider, ICustomerProvider customerProvider) {
        this.carProvider = carProvider;
        this.customerProvider = customerProvider;
    }

    public void sellCars() {
        List<Customer> customers = customerProvider.getCustomers();
        customers.stream()
                .filter(customer -> customer.getCar() == null)
                .forEach(customer -> customer.setCar(carProvider.takeCar(customer)));
    }
}
