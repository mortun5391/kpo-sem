package studying.interfaces;

import studying.domains.Car;

public interface ICarFactory<TParams> {

    Car createCar(TParams tParams, int VIN);
}
