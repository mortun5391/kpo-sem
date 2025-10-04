package studying;

import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.LevitatingCarFactory;
import studying.factories.PedalCarFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.CarService;
import studying.services.CustomerStorage;
import studying.services.HseCarService;



public class Main {

    public static void main(String[] args) {

        var carService = new CarService();
        var customerStorage = new CustomerStorage();
        var hseCarService = new HseCarService(carService, customerStorage);
        var pedalCarFactory = new PedalCarFactory();
        var handCarFactory = new HandCarFactory();
        var levitatingCarFactory = new LevitatingCarFactory();

        customerStorage.addCustomer(new Customer("Gosha", 4, 6, 123));
        customerStorage.addCustomer(new Customer("Kolya", 6, 4, 234));
        customerStorage.addCustomer(new Customer("Kirill", 6, 6, 111));
        customerStorage.addCustomer(new Customer("Nikita", 4, 4, 300));
        customerStorage.addCustomer(new Customer("Ali", 4, 4, 320));




        carService.addCar(pedalCarFactory, new PedalEngineParams(6));
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        carService.addCar(levitatingCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(levitatingCarFactory, EmptyEngineParams.DEFAULT);




        System.out.println("== Customers before sales ==");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        hseCarService.sellCars();
        System.out.println();

        System.out.println("== Customers after sales ==");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

    }
}