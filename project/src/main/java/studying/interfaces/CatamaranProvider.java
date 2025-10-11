package studying.interfaces;

import studying.domains.Catamaran;
import studying.domains.Customer;

public interface CatamaranProvider {
    Catamaran takeCatamaran(Customer customer);
}
