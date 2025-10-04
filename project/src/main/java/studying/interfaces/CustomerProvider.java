package studying.interfaces;

import java.util.List;
import studying.domains.Customer;


/**
 * Interface for customer providers.
 *
 */
public interface CustomerProvider {
    List<Customer> getCustomers();
}
