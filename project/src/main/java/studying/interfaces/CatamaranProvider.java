package studying.interfaces;

import studying.domains.Catamaran;
import studying.domains.Customer;

/**
 * Interface for catamaran provider.
 *
 */
public interface CatamaranProvider {
    Catamaran takeCatamaran(Customer customer);
}
