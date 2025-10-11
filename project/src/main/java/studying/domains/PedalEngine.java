package studying.domains;

import lombok.ToString;
import studying.enums.ProductionType;
import studying.interfaces.Engine;

import static studying.enums.ProductionType.CATAMARAN;

/**
 * Pedal engine implementation.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@ToString
public class PedalEngine implements Engine {

    private final int size;

    public PedalEngine(int size) {
        this.size = size;
    }

    /**
     * A method that checks whether an engine is compatible for a customer.
     *
     * @param customer customer
     * @return is an engine compatible for a customer
     */
    @Override
    public boolean isCompatible(Customer customer, ProductionType type) {
        return switch(type) {
            case CAR ->  customer.getArmStrength() > 5;
            case CATAMARAN -> customer.getArmStrength() > 2;
            case null, default -> throw new RuntimeException("This type of production doesn't exist");
        };
    }
}
