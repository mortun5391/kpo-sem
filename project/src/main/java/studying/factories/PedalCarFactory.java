package studying.factories;

import studying.domains.Car;
import studying.domains.PedalEngine;
import studying.interfaces.ICarFactory;
import studying.params.PedalEngineParams;

/**
 * Factory for pedal engine cars
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
public class PedalCarFactory implements ICarFactory<PedalEngineParams> {

    /**
     * Create car with pedal engine and unique number
     * @param pedalEngineParams pedal engine params
     * @param VIN unique number
     * @return created car
     */
    @Override
    public Car createCar(PedalEngineParams pedalEngineParams, int VIN) {
        var engine = new PedalEngine(pedalEngineParams.pedalSize());
        return new Car(engine, VIN);
    }
}
