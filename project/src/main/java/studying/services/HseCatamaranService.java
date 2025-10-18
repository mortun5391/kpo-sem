package studying.services;

import java.util.List;
import org.springframework.stereotype.Component;
import studying.domains.Customer;
import studying.interfaces.CatamaranProvider;
import studying.interfaces.CustomerProvider;


/**
 * Service for managing catamarans and customers.
 * Provides functionality for selling catamarans.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
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
