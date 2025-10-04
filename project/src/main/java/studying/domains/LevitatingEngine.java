package studying.domains;

import lombok.ToString;
import studying.interfaces.IEngine;

/**
 * Levitating engine implementation
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@ToString
public class LevitatingEngine implements IEngine {

    /**
     * A method that checks whether an engine is compatible for a customer
     * @param customer customer
     * @return is an engine compatible for a customer
     */
    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getIq() > 300;
    }
}
