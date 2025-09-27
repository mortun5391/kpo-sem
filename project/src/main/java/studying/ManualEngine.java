package studying;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ManualEngine implements IEngine{

    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getArmStrength() > 5;
    }
}
