package studying;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studying.domains.Customer;
import studying.factories.HandCarFactory;
import studying.factories.HandCatamaranFactory;
import studying.factories.LevitatingCarFactory;
import studying.factories.LevitatingCatamaranFactory;
import studying.factories.PedalCarFactory;
import studying.factories.PedalCatamaranFactory;
import studying.params.EmptyEngineParams;
import studying.params.PedalEngineParams;
import studying.services.HseCarService;
import studying.services.HseCatamaranService;
import studying.storages.CarStorage;
import studying.storages.CatamaranStorage;
import studying.storages.CustomerStorage;

/**
 * Tests for kpo application.
 */
@SpringBootTest
public class KpoApplicationTest {

    @Autowired
    private CarStorage carStorage;

    @Autowired
    private CustomerStorage customerStorage;

    @Autowired
    private CatamaranStorage catamaranStorage;

    @Autowired
    private HseCarService hseCarService;

    @Autowired
    private HseCatamaranService hseCatamaranService;

    @Autowired
    private PedalCarFactory pedalCarFactory;

    @Autowired
    private HandCarFactory handCarFactory;

    @Autowired
    private LevitatingCarFactory levitatingCarFactory;

    @Autowired
    private PedalCatamaranFactory pedalCatamaranFactory;

    @Autowired
    private HandCatamaranFactory handCatamaranFactory;

    @Autowired
    private LevitatingCatamaranFactory levitatingCatamaranFactory;


    @Test
    @DisplayName("Тест загрузки контекста")
    void contextLoads() {
        Assertions.assertNotNull(carStorage);
        Assertions.assertNotNull(catamaranStorage);
        Assertions.assertNotNull(customerStorage);
        Assertions.assertNotNull(hseCarService);
        Assertions.assertNotNull(hseCatamaranService);

        Assertions.assertNotNull(pedalCarFactory);
        Assertions.assertNotNull(handCarFactory);
        Assertions.assertNotNull(levitatingCarFactory);

        Assertions.assertNotNull(pedalCatamaranFactory);
        Assertions.assertNotNull(handCatamaranFactory);
        Assertions.assertNotNull(levitatingCatamaranFactory);
    }

    @Test
    @DisplayName("Тест HseCarService")
    void hseCarServiceTest() {
        customerStorage.addCustomer(new Customer("Gosha", 4, 6, 123));
        customerStorage.addCustomer(new Customer("Kolya", 6, 4, 234));
        customerStorage.addCustomer(new Customer("Kirill", 6, 6, 211));
        customerStorage.addCustomer(new Customer("Nikita", 4, 4, 300));
        customerStorage.addCustomer(new Customer("Ali", 4, 4, 320));


        carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));
        carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));

        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        carStorage.addCar(levitatingCarFactory, EmptyEngineParams.DEFAULT);
        carStorage.addCar(levitatingCarFactory, EmptyEngineParams.DEFAULT);

        System.out.println("== Customers before sales ==");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        hseCarService.sellCars();
        System.out.println();

        System.out.println("== Customers after sales ==");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

    }

    @Test
    @DisplayName("Тест HseCatamaranService")
    void hseCatamaranServiceTest() {
        customerStorage.addCustomer(new Customer("Gosha", 4, 6, 123));
        customerStorage.addCustomer(new Customer("Kolya", 6, 4, 234));
        customerStorage.addCustomer(new Customer("Kirill", 6, 6, 211));
        customerStorage.addCustomer(new Customer("Nikita", 4, 4, 300));
        customerStorage.addCustomer(new Customer("Ali", 4, 4, 320));


        catamaranStorage.addCatamaran(pedalCatamaranFactory, new PedalEngineParams(6));
        catamaranStorage.addCatamaran(pedalCatamaranFactory, new PedalEngineParams(6));

        catamaranStorage.addCatamaran(handCatamaranFactory, EmptyEngineParams.DEFAULT);
        catamaranStorage.addCatamaran(handCatamaranFactory, EmptyEngineParams.DEFAULT);

        catamaranStorage.addCatamaran(levitatingCatamaranFactory, EmptyEngineParams.DEFAULT);
        catamaranStorage.addCatamaran(levitatingCatamaranFactory, EmptyEngineParams.DEFAULT);


        System.out.println("== Customers before sales ==");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

        hseCatamaranService.sellCatamarans();
        System.out.println();

        System.out.println("== Customers after sales ==");
        customerStorage.getCustomers().stream().map(Customer::toString).forEach(System.out::println);

    }

}
