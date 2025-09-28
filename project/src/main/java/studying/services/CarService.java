package studying.services;

import studying.domains.Car;
import studying.domains.Customer;
import studying.interfaces.ICarFactory;
import studying.interfaces.ICarProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CarService implements ICarProvider {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;
    @Override
    public Car takeCar(Customer customer) {
        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams params) {
        var car = carFactory.createCar(params, ++carNumberCounter);
        cars.add(car);
    }

    public void printCars() {
        cars.stream().forEach(car -> System.out.println(car.toString()));
    }
}
