package studying.domains;

import lombok.ToString;
import studying.domains.Customer;
import studying.interfaces.IEngine;

/**
 * Pedal engine implementation
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@ToString
public class PedalEngine implements IEngine {

    private final int size;
    public PedalEngine(int size) {
        this.size = size;
    }

    /**
     * A method that checks whether an engine is compatible for a customer
     * @param customer customer
     * @return is an engine compatible for a customer
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegStrength() > 5;
    }
}
