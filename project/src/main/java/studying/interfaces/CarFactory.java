package studying.interfaces;

import studying.domains.Car;

/**
 * Interface for car factories.
 *
 * @param <T> engine parameters
 */
public interface CarFactory<T> {

    Car createCar(T params, int vin);
}
