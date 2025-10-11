package studying.domains;

import lombok.Getter;
import studying.enums.ProductionType;
import studying.interfaces.Engine;

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

