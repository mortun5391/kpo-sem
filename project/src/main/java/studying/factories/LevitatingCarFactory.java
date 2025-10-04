package studying.factories;

import studying.domains.Car;
import studying.domains.HandEngine;
import studying.domains.LevitatingEngine;
import studying.domains.PedalEngine;
import studying.interfaces.ICarFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;

public class LevitatingCarFactory implements ICarFactory<EmptyEngineParams> {
    /**
     * Create car with levitating engine and unique number
     * @param emptyEngineParams empty param
     * @param VIN unique number
     * @return created car
     */
    @Override
    public Car createCar(EmptyEngineParams emptyEngineParams, int VIN) {
        var engine = new LevitatingEngine();
        return new Car(engine, VIN);
    }
}
