package studying.domains;

import lombok.Getter;
import lombok.ToString;
import studying.enums.ProductionType;
import studying.interfaces.Engine;

/**
 * It is a catamaran with an engine and a unique number.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-10-18
 */
@ToString
public class Catamaran {
    private Engine engine;

    @Getter
    private int vin;

    public Catamaran(Engine engine, int vin) {
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
        return this.engine.isCompatible(customer, ProductionType.CATAMARAN);
    }
}

