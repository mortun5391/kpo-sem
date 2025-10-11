package studying.interfaces;

import studying.domains.Customer;
import studying.enums.ProductionType;

/**
 * Interface for car engines.
 *
 */
public interface Engine {

    boolean isCompatible(Customer customer, ProductionType type);

}
