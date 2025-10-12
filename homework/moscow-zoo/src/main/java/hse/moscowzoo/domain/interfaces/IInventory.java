package hse.moscowzoo.domain.interfaces;

/**
 * Интерфейс для объектов инвентаря в зоопарке.
 * Определяет базовые методы для идентификации и наименования объектов.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface IInventory {
    /**
     * Возвращает уникальный инвентарный номер объекта.
     *
     * @return инвентарный номер объекта
     */
    int getInventoryNumber();

    /**
     * Возвращает название объекта.
     *
     * @return название объекта
     */
    String getName();
}