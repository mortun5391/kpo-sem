package studying.domains;

import lombok.ToString;
import studying.enums.ProductionType;
import studying.interfaces.Engine;

/**
 * Levitating engine implementation.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@ToString
public class LevitatingEngine implements Engine {

    /**
     * A method that checks whether an engine is compatible for a customer.
     *
     * @param customer customer
     * @return is an engine compatible for a customer
     */
    @Override
    public boolean isCompatible(Customer customer, ProductionType type) {
        return switch(type) {
            case CAR ->  customer.getIq() > 300;
            case CATAMARAN -> customer.getIq() > 200;
            case null, default -> throw new RuntimeException("This type of production doesn't exist");
        };    }
}
