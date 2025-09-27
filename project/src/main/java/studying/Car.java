package studying;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Car {

    private IEngine engine;

    @Getter
    private int VIN;

    public Car(IEngine engine, int VIN) {
        this.engine = engine;
        this.VIN = VIN;
    }

    public boolean isCompatible(Customer customer) {
        return this.engine.isCompatible(customer);
    }
}
