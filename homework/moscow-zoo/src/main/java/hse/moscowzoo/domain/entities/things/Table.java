package hse.moscowzoo.domain.entities.things;

/**
 * Класс, представляющий стол в зоопарке.
 * Используется для различных административных и хозяйственных нужд.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class Table extends Thing{
    /**
     * Конструктор для создания стола.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name название стола
     */
    public Table(int inventoryNumber, String name) {
        super(inventoryNumber, name);
    }
}