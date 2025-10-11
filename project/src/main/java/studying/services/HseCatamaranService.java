package studying.services;

import org.springframework.stereotype.Component;
import studying.domains.Customer;
import studying.interfaces.CarProvider;
import studying.interfaces.CatamaranFactory;
import studying.interfaces.CatamaranProvider;
import studying.interfaces.CustomerProvider;

import java.util.List;

@Component
public class HseCatamaranService {
    private final CatamaranProvider catamaranProvider;
    private final CustomerProvider customerProvider;

    public HseCatamaranService(CatamaranProvider catamaranProvider, CustomerProvider customerProvider) {
        this.catamaranProvider = catamaranProvider;
        this.customerProvider = customerProvider;
    }

    /**
     * Sells a car to all customers who don't have a car.
     *
     */
    public void sellCatamarans() {
        List<Customer> customers = customerProvider.getCustomers();
        customers.stream()
                .filter(customer -> customer.getCatamaran() == null)
                .forEach(customer -> customer.setCatamaran(catamaranProvider.takeCatamaran(customer)));
    }
}
