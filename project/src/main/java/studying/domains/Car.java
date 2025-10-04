package studying.domains;

import lombok.Getter;
import lombok.ToString;
import studying.interfaces.Engine;

/**
 * It is a car with an engine and a unique number.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@ToString
public class Car {

    private Engine engine;

    @Getter
    private int vin;

    public Car(Engine engine, int vin) {
        this.engine = engine;
        this.vin = vin;
    }

    /**
     * A method that checks whether a car is compatible for a customer.
     *
     * @param customer customer
     * @return is a car compatible for a customer
     */
    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer);
    }
}
