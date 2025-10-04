package studying.services;

import studying.domains.Car;
import studying.domains.Customer;
import studying.interfaces.ICarFactory;
import studying.interfaces.ICarProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing cars.
 * Provides functionality for adding, issuing, and displaying cars.
 * Implements the {@link ICarProvider} interface for providing cars to customers.
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
public class CarService implements ICarProvider {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    /**
     * Issues a suitable car to a customer based on their characteristics.
     * The car is selected based on compatibility with the customer and is removed
     * from the available cars list after being issued.
     *
     * @param customer the customer for whom the car is being selected
     * @return a suitable car or {@code null} if no compatible car is found
     * @see Car#isCompatible(Customer)
     */
    @Override
    public Car takeCar(Customer customer) {
        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /**
     * Adds a new car to the service using the specified car factory.
     * The car is created with the provided parameters and assigned a unique car number.
     *
     * @param <TParams> the type of parameters required by the car factory
     * @param carFactory the factory used to create the car
     * @param params the parameters required to create the car
     */
    public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams params) {
        var car = carFactory.createCar(params, ++carNumberCounter);
        cars.add(car);
    }

    /**
     * Prints all available cars in the service to the standard output.
     * Each car is printed using its {@code toString()} method.
     */
    public void printCars() {
        cars.stream().forEach(car -> System.out.println(car.toString()));
    }
}
