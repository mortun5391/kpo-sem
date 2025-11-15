package studying.factories;

import org.springframework.stereotype.Component;
import studying.domains.Car;
import studying.domains.HandEngine;
import studying.interfaces.CarFactory;
import studying.params.EmptyEngineParams;

/**
 * Factory for hand engine cars.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@Component
public class HandCarFactory implements CarFactory<EmptyEngineParams> {

    /**
     * Create car with hand engine and unique number.
     *
     * @param emptyEngineParams empty param
     * @param vin unique number
     * @return created car
     */
    @Override
    public Car createCar(EmptyEngineParams emptyEngineParams, int vin) {
        var engine = new HandEngine();
        return new Car(engine, vin);
    }
}
