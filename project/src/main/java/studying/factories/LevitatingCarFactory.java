package studying.factories;

import org.springframework.stereotype.Component;
import studying.domains.Car;
import studying.domains.LevitatingEngine;
import studying.interfaces.CarFactory;
import studying.params.EmptyEngineParams;

/**
 * Factory for levitating engine cars.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-10-04
 */
@Component
public class LevitatingCarFactory implements CarFactory<EmptyEngineParams> {
    /**
     * Create car with levitating engine and unique number.
     *
     * @param emptyEngineParams empty param
     * @param vin unique number
     * @return created car
     */
    @Override
    public Car createCar(EmptyEngineParams emptyEngineParams, int vin) {
        var engine = new LevitatingEngine();
        return new Car(engine, vin);
    }
}
