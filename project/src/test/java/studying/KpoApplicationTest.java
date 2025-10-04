package studying;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.LevitatingCarFactory;
import studying.factories.PedalCarFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.CarService;
import studying.services.CustomerStorage;
import studying.services.HseCarService;

/**
 * Tests for kpo application.
 */
@SpringBootTest
public class KpoApplicationTest {

    @Autowired
    private CarService carService;

    @Autowired
    private CustomerStorage customerStorage;

    @Autowired
    private HseCarService hseCarService;

    @Autowired
    private PedalCarFactory pedalCarFactory;

    @Autowired
    private HandCarFactory handCarFactory;

    @Autowired
    private LevitatingCarFactory levitatingCarFactory;

    @Test
    @DisplayName("Тест загрузки контекста")
    void contextLoads() {
        Assertions.assertNotNull(carService);
        Assertions.assertNotNull(customerStorage);
        Assertions.assertNotNull(hseCarService);

        Assertions.assertNotNull(pedalCarFactory);
        Assertions.assertNotNull(handCarFactory);
        Assertions.assertNotNull(levitatingCarFactory);
    }

    @Test
    @DisplayName("Тест HseCarService")
    void hseCarServiceTest() {
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

        carService.addCar(pedalCarFactory, new PedalEngineParams(6));
        carService.addCar(pedalCarFactory, new PedalEngineParams(6));

        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        hseCarService.sellCars();

        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);
    }

}
