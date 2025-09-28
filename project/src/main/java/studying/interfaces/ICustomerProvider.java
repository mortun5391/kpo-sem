package studying.interfaces;

import studying.domains.Customer;

import java.util.List;


public interface ICustomerProvider {
    List<Customer> getCustomers();
}
