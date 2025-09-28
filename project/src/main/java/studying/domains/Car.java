package studying.domains;

import lombok.Getter;
import lombok.ToString;
import studying.interfaces.IEngine;

/**
 * It is a car with an engine and a unique number.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@ToString
public class Car {

    private IEngine engine;

    @Getter
    private int VIN;

    public Car(IEngine engine, int VIN) {
        this.engine = engine;
        this.VIN = VIN;
    }

    /**
     * A method that checks whether a car is compatible for a customer
     * @param customer customer
     * @return is a car compatible for a customer
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer);
    }
}
