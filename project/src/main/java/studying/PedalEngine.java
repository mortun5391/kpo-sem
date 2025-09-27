package studying;

import lombok.ToString;

@ToString
public class PedalEngine implements IEngine{

    @Override
    public boolean isCompatible(Customer customer) {
        return customer.getLegStrength() > 5;
    }
}
