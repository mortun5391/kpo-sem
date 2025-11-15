package studying;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import studying.builders.ReportBuilder;
import studying.domains.Customer;
import studying.domains.Report;
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
        assertNotNull(carStorage);
        assertNotNull(catamaranStorage);
        assertNotNull(customerStorage);
        assertNotNull(hseCarService);
        assertNotNull(hseCatamaranService);

        assertNotNull(pedalCarFactory);
        assertNotNull(handCarFactory);
        assertNotNull(levitatingCarFactory);

        assertNotNull(pedalCatamaranFactory);
        assertNotNull(handCatamaranFactory);
        assertNotNull(levitatingCatamaranFactory);
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

    @Test
    @DisplayName("Тест создания пустого отчета")
    void testEmptyReport() {
        ReportBuilder builder = new ReportBuilder();
        Report report = builder.build();

        assertNotNull(report);
        assertNotNull(report.title());
        assertNotNull(report.content());
        assertTrue(report.title().contains("Отчет за"));

        assertTrue(report.content().isEmpty());
    }

    @Test
    @DisplayName("Тест добавления операций в отчет")
    void testAddOperations() {
        ReportBuilder builder = new ReportBuilder();
        builder.addOperation("Создан новый покупатель")
                .addOperation("Продажа автомобиля")
                .addOperation("Обновление склада");

        Report report = builder.build();

        assertNotNull(report.content());
        assertTrue(report.content().contains("Операция: Создан новый покупатель"));
        assertTrue(report.content().contains("Операция: Продажа автомобиля"));
        assertTrue(report.content().contains("Операция: Обновление склада"));
    }

    @Test
    @DisplayName("Тест добавления покупателей в отчет")
    void testAddCustomers() {
        List<Customer> customers = List.of(
                new Customer("Иван", 4, 6, 100),
                new Customer("Мария", 6, 4, 200),
                new Customer("Петр", 6, 6, 150)
        );

        ReportBuilder builder = new ReportBuilder();
        builder.addCustomers(customers);

        Report report = builder.build();

        assertNotNull(report.content());
        assertTrue(report.content().contains("Покупатели:"));
        assertTrue(report.content().contains("Иван"));
        assertTrue(report.content().contains("Мария"));
        assertTrue(report.content().contains("Петр"));
    }

    @Test
    @DisplayName("Тест комбинированного отчета")
    void testCombinedReport() {
        List<Customer> customers = List.of(
                new Customer("Алексей", 4, 4, 300)
        );

        ReportBuilder builder = new ReportBuilder();
        builder.addOperation("Инициализация системы")
                .addCustomers(customers)
                .addOperation("Завершение работы");

        Report report = builder.build();

        String content = report.content();
        assertTrue(content.contains("Операция: Инициализация системы"));
        assertTrue(content.contains("Покупатели:"));
        assertTrue(content.contains("Алексей"));
        assertTrue(content.contains("Операция: Завершение работы"));
    }

    @Test
    @DisplayName("Тест структуры отчета")
    void testReportStructure() {
        ReportBuilder builder = new ReportBuilder();
        Report report = builder.build();


        String reportString = report.toString();
        assertTrue(reportString.contains("\n\n"));

        String[] parts = reportString.split("\n\n", 2);
        assertEquals(2, parts.length);
        assertTrue(parts[0].contains("Отчет за"));
        assertNotNull(parts[1]);
    }


    @Test
    @DisplayName("Тест отчета со складами в едином стиле")
    void testReportWithStorageInfo() {
        carStorage.addCar(pedalCarFactory, new PedalEngineParams(6));
        carStorage.addCar(handCarFactory, EmptyEngineParams.DEFAULT);

        catamaranStorage.addCatamaran(pedalCatamaranFactory, new PedalEngineParams(4));
        catamaranStorage.addCatamaran(levitatingCatamaranFactory, EmptyEngineParams.DEFAULT);

        ReportBuilder builder = new ReportBuilder();
        builder.addOperation("Инициализация системы")
                .addCarStorageInfo(carStorage.getCars())
                .addCatamaranStorageInfo(catamaranStorage.getCatamarans())
                .addOperation("Отчет сгенерирован");

        Report report = builder.build();

        String content = report.content();
        assertTrue(content.contains("Склад автомобилей:"));
        assertTrue(content.contains("Склад катамаранов:"));
        assertTrue(content.contains("PedalEngine"));
        assertTrue(content.contains("HandEngine"));
    }

    @Test
    @DisplayName("Тест отчета с пустыми складами")
    void testReportWithEmptyStorages() {
        carStorage.getCars().clear();
        catamaranStorage.getCatamarans().clear();

        ReportBuilder builder = new ReportBuilder();
        builder.addCarStorageInfo(carStorage.getCars())
                .addCatamaranStorageInfo(catamaranStorage.getCatamarans());

        Report report = builder.build();

        String content = report.content();
        assertTrue(content.contains("Склад автомобилей:"));
        assertTrue(content.contains("Склад катамаранов:"));
    }
}
