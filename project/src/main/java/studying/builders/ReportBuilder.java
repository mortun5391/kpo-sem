package studying.builders;


import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import studying.domains.Car;
import studying.domains.Catamaran;
import studying.domains.Customer;
import studying.domains.Report;

/**
 * Класс для составления отчета о работе системы.
 */
public class ReportBuilder {

    /**
     * Пример форматированной даты - 2025-02-10.
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private StringBuilder content = new StringBuilder();

    /**
     * Метод для добавления информации о покупателях в отчет.
     *
     * @param customers покупатели
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addCustomers(List<Customer> customers) {
        content.append("Покупатели:");
        customers.forEach(customer -> content.append(String.format(" - %s", customer)));
        content.append("\n");

        return this;
    }

    /**
     * Метод для добавления информации о действиях в системе в отчет.
     *
     * @param operation операция в системе
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addOperation(String operation) {
        content.append(String.format("Операция: %s", operation));
        content.append(System.lineSeparator());
        return this;
    }

    /**
     * Метод для добавления информации о автомобилях в отчет.
     *
     * @param cars автомобили
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addCars(List<Car> cars) {
        content.append("Автомобили:");
        cars.forEach(car -> content.append(String.format(" - %s", car)));
        content.append("\n");

        return this;
    }

    /**
     * Метод для добавления информации о катамаранах в отчет.
     *
     * @param catamarans катамараны
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addCatamarans(List<Catamaran> catamarans) {
        content.append("Катамараны:");
        catamarans.forEach(catamaran -> content.append(String.format(" - %s", catamaran)));
        content.append("\n");

        return this;
    }

    /**
     * Метод для добавления информации о текущем состоянии склада автомобилей в отчет.
     *
     * @param cars склад автомобилей
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addCarStorageInfo(List<Car> cars) {
        content.append("Склад автомобилей:");
        cars.forEach(car -> content.append(String.format(" - %s", car)));
        content.append("\n");

        return this;
    }

    /**
     * Метод для добавления информации о текущем состоянии склада катамаранов в отчет.
     *
     * @param catamarans склад катамаранов
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addCatamaranStorageInfo(List<Catamaran> catamarans) {
        content.append("Склад катамаранов:");
        catamarans.forEach(catamaran -> content.append(String.format(" - %s", catamaran)));
        content.append("\n");

        return this;
    }

    /**
     * Метод для добавления полной информации о складах в отчет.
     *
     * @param cars склад автомобилей
     * @param catamarans склад катамаранов
     * @return {@link ReportBuilder} для дальнейшего составления отчета
     */
    public ReportBuilder addStorageInfo(List<Car> cars,   List<Catamaran> catamarans) {
        return this.addCarStorageInfo(cars)
                .addCatamaranStorageInfo(catamarans);
    }

    /**
     * Метод получения итогового отчета о системе.
     *
     * @return {@link Report} отчет о системе
     */
    public Report build() {
        return new Report(String.format("Отчет за %s", ZonedDateTime.now().format(DATE_TIME_FORMATTER)),
                content.toString());
    }
}