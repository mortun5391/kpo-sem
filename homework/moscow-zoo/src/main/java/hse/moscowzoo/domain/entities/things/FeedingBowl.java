package hse.moscowzoo.domain.entities.things;

import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий миску для кормления животных.
 * Миска может быть автоматической или ручной, имеет определенную вместимость.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class FeedingBowl extends Thing {
    @Getter
    private final double capacity;
    @Getter
    private final boolean isAutomatic;

    /**
     * Конструктор для создания миски для кормления.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name название миски
     * @param capacity вместимость миски в литрах
     * @param isAutomatic является ли миска автоматической
     */
    public FeedingBowl(int inventoryNumber, String name, double capacity, boolean isAutomatic) {
        super(inventoryNumber, name);
        this.capacity = capacity;
        this.isAutomatic = isAutomatic;
    }
}