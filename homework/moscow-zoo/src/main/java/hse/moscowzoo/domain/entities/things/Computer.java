package hse.moscowzoo.domain.entities.things;

/**
 * Класс, представляющий компьютер в зоопарке.
 * Используется для административных и управленческих задач.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class Computer extends Thing{
    /**
     * Конструктор для создания компьютера.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name название компьютера
     */
    public Computer(int inventoryNumber, String name) {
        super(inventoryNumber, name);
    }

}