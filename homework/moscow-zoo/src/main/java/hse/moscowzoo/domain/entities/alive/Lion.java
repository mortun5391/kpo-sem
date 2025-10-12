package hse.moscowzoo.domain.entities.alive;

import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий льва в зоопарке.
 * Лев является хищником, который может иметь гриву (у самцов).
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Lion extends Predator {
    @Getter
    private final boolean hasMane;
    @Getter
    private final double maneLength;
    private static final int DEFAULT_FOOD_CONSUMPTION = 15;

    /**
     * Конструктор для создания льва.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя льва
     * @param hasMane наличие гривы у льва
     * @param maneLength длина гривы в метрах (если есть)
     */
    public Lion(int inventoryNumber, String name, boolean hasMane, double maneLength) {
        super(inventoryNumber, name, DEFAULT_FOOD_CONSUMPTION); 
        this.hasMane = hasMane;
        this.maneLength = hasMane ? maneLength : 0;
    }
}