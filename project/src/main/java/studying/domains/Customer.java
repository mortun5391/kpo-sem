package studying.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import studying.domains.Car;

/**
 * It is a customer with a name, arm strength and leg strength.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@ToString
public class Customer {
    @Getter
    private String name;

    @Getter
    @Setter
    private int armStrength;

    @Getter
    @Setter
    private int legStrength;

    @Getter
    @Setter
    private int iq;

    @Getter
    @Setter
    private Car car;

    public Customer(String name, int armStrength, int legStrength, int iq) {
        this.name = name;
        this.armStrength = armStrength;
        this.legStrength = legStrength;
        this.iq = iq;
    }
}
